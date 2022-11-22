package com.ssafy.sonmogaji.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.ssafy.sonmogaji.model.dto.SigneeDto;
import com.ssafy.sonmogaji.model.dto.TransactionDto;
import com.ssafy.sonmogaji.model.entity.room.*;
import com.ssafy.sonmogaji.model.service.ApachePOIServiceImpl;
import com.ssafy.sonmogaji.model.service.Base64ToFile;
import com.ssafy.sonmogaji.model.service.TransactionServiceImpl;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.web3j.abi.datatypes.Bool;
import org.web3j.crypto.Hash;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Log4j2
@CrossOrigin("*")
public class MessageController {

    // 메세지를 보내는 양식을 지정해둔 template
    // 이걸 사용하면 편하게 메세지를 클라이언트쪽으로 보낼 수 있음.
    @Autowired
    private final SimpMessagingTemplate template;
    @Autowired
    private RoomList roomList;

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private ApachePOIServiceImpl apachePOIService;


    @MessageMapping(value = "/memorandum/join")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void join(joinFormat message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {
//        log.info(message.getSenderNickName() + " join");
        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.getParticipants().size() < 1) {
            r.setHostSessionId(headerAccessor.getSessionId());
        }
        if (r.isStart()) {
            message.setRoomCode(message.getRoomCode());
            message.setMessage("no" + message.getRoomCode());
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/join/" + message.getRoomCode(), message);
        } else {
            log.info(message.getSenderNickName() + " join into " + r.getRoomCode());
            log.info(headerAccessor.getSessionId());
            r.addParticipant(message.getSenderNickName(), headerAccessor.getSessionId());
            MemorandumState state=r.getMemorandumState();
//            HashMap<String,Boolean>[] agreearray=state.getAgree();
            List<Map<String, Boolean>> agreelist = state.getAgree();
//            HashMap<String, Boolean> agree = r.getMemorandumState().getAgree().get(0);
//            log.info(agreelist.get(0));
            agreelist.get(0).put(headerAccessor.getSessionId(),false);
            agreelist.get(1).put(headerAccessor.getSessionId(),false);
            agreelist.get(2).put(headerAccessor.getSessionId(),false);
            agreelist.get(3).put(headerAccessor.getSessionId(),false);
            agreelist.get(4).put(headerAccessor.getSessionId(),false);
            agreelist.get(5).put(headerAccessor.getSessionId(),false);
            agreelist.get(6).put(headerAccessor.getSessionId(),false);

            r.getMemorandumState().getSign().put(headerAccessor.getSessionId(), "");
            r.getMemorandumState().getSignState().put(message.getSenderNickName(),false);
            message.setRoomCode(message.getRoomCode());
            message.setSignState(r.getMemorandumState().getSignState());
//            message.setMemorandumState(r.getMemorandumState());
            message.setMessage("ok");
            template.convertAndSend("/sub/memorandum/join/" + message.getRoomCode(), message);
        }
    }

    @MessageMapping(value = "/memorandum/action")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void action(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {
        log.info(message.getSenderNickName() + " action");

        template.convertAndSend("/sub/memorandum/action/" + message.getRoomCode(), message);

    }

    @MessageMapping(value = "/memorandum/start")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void start(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.startRoom(headerAccessor.getSessionId())) {
            log.info(message.getRoomCode() + " start");
            message.setMessage("start");
            message.setSignState(r.getMemorandumState().getSignState());

            message.setMemorandumState(r.getMemorandumState());

            template.convertAndSend("/sub/memorandum/start/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/start/" + message.getRoomCode(), message);
        }
    }


    @MessageMapping(value = "/memorandum/vote")
    public void vote(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.getHostSessionId().equals(headerAccessor.getSessionId())) {
            message.setMessage("you are host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/vote/" + message.getRoomCode(), message);

        } else {
//            HashMap<String, Boolean> agree = r.getMemorandumState().getAgree().get(Integer.parseInt(message.getMessage()));
//            agree.put(headerAccessor.getSessionId(), !agree.get(headerAccessor.getSessionId()));
            int index=Integer.parseInt(message.getMessage());
            Map<String, Boolean> agree = r.getMemorandumState().getAgree().get(index);
            agree.put(headerAccessor.getSessionId(), !agree.get(headerAccessor.getSessionId()));
            message.setMemorandumState(r.getMemorandumState());
            message.setMessage(Integer.toString(index+1));
            template.convertAndSend("/sub/memorandum/vote/" + message.getRoomCode(), message);
        }

    }

    @MessageMapping(value = "/memorandum/next")
    public void next(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (!r.getHostSessionId().equals(headerAccessor.getSessionId())) {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/next/" + message.getRoomCode(), message);

        } else {
//            HashMap<String, Boolean> agree = r.getMemorandumState().getAgree().get(Integer.parseInt(message.getMessage()));
//            agree.put(headerAccessor.getSessionId(), !agree.get(headerAccessor.getSessionId()));

            //Map<String, Boolean> agree = r.getMemorandumState().getAgree().get(Integer.parseInt(message.getMessage()));
            //agree.put(headerAccessor.getSessionId(), !agree.get(headerAccessor.getSessionId()));
            if(Integer.parseInt(message.getMessage())==5){
                Map<String,Boolean> sstate= r.getMemorandumState().getSignState();
                boolean flag=true;
                for( Map.Entry<String, Boolean> elem : sstate.entrySet() ){
                    if(!elem.getValue()){
                        message.setMessage("sign not done");
                        template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/next/" + message.getRoomCode(), message);
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    int index=Integer.parseInt(message.getMessage());
                    message.setMessage(Integer.toString(index+1));
                    template.convertAndSend("/sub/memorandum/next/" + message.getRoomCode(), message);
                }
            }
            else{
                int index= Integer.parseInt(message.getMessage())-1;
                Map<String, Boolean> agreemap = r.getMemorandumState().getAgree().get(index);
                int participantCount=r.getParticipants().size();
                int count=0;
                for( Map.Entry<String, Boolean> elem : agreemap.entrySet() ){
                    if(elem.getValue()){
                        count++;
                    }
                }

                if(count==(participantCount-1)){
                    message.setMemorandumState(r.getMemorandumState());
                    message.setMessage(Integer.toString(index+2));
                    template.convertAndSend("/sub/memorandum/next/" + message.getRoomCode(), message);
                }
                else{
                    message.setMessage("not agreed");
                    template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/next/" + message.getRoomCode(), message);

                }
            }



        }

    }

    @MessageMapping(value = "/memorandum/content")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void content(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.startRoom(headerAccessor.getSessionId())) {
            r.getMemorandumState().setTitle(message.getTitle());
            r.getMemorandumState().setContent(message.getContent());

            message.setMessage("ok");
            message.setMemorandumState(r.getMemorandumState());
            template.convertAndSend("/sub/memorandum/content/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/content/" + message.getRoomCode(), message);
        }
    }
    @MessageMapping(value = "/memorandum/secret")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void secret(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.startRoom(headerAccessor.getSessionId())) {
            r.getMemorandumState().setSecret(message.isSecret());

            message.setMessage("ok");
            message.setMemorandumState(r.getMemorandumState());
            template.convertAndSend("/sub/memorandum/secret/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/secret/" + message.getRoomCode(), message);
        }
    }
    @MessageMapping(value = "/memorandum/memorysecret")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void memorysecret(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.startRoom(headerAccessor.getSessionId())) {
            r.getMemorandumState().setMemorySecret((message.isMemorySecret()));


            message.setMessage("ok");
//            message.setMemorandumState(r.getMemorandumState());
            template.convertAndSend("/sub/memorandum/memorysecret/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/memorysecret/" + message.getRoomCode(), message);
        }
    }
    @MessageMapping(value = "/memorandum/memoryimage")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void memoryimage(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.startRoom(headerAccessor.getSessionId())) {
            r.getMemorandumState().setMemoryImage(message.getMemoryImage());


            message.setMessage("ok");

//            message.setMemorandumState(r.getMemorandumState());
            template.convertAndSend("/sub/memorandum/memoryimage/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/memoryimage/" + message.getRoomCode(), message);
        }
    }
    @MessageMapping(value = "/memorandum/expire")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void expire(MemorandumAction message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException, IOException {
        File f=new File("abcde.docx");
        log.info(f.createNewFile());
        log.info(f.getAbsolutePath());
        log.info(f.getPath());
        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        if (r.startRoom(headerAccessor.getSessionId())) {

//            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
//            r.getMemorandumState().setExpire(dformat.parse(message.getExpire()));
            r.getMemorandumState().setExpire(message.getExpire());
            message.setMessage("ok");
            message.setMemorandumState(r.getMemorandumState());
            template.convertAndSend("/sub/memorandum/expire/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/expire/" + message.getRoomCode(), message);
        }
    }
    @MessageMapping(value = "/memorandum/sign")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void sign(signFormat message, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {
        log.info("sign sent");
        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
    r.getMemorandumState().getSign().put(headerAccessor.getSessionId(),message.getSign());
    r.getMemorandumState().getSignState().put(message.getSenderNickName(),true);
            message.setMessage("ok");
            message.setSignState(r.getMemorandumState().getSignState());
            template.convertAndSend("/sub/memorandum/sign/" + message.getRoomCode(), message);

    }

    @MessageMapping(value = "/memorandum/preview")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void preview(previewFormat message, SimpMessageHeaderAccessor headerAccessor) throws Exception {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        MemorandumState mstate=r.getMemorandumState();
        if (r.startRoom(headerAccessor.getSessionId())) {
            // 추억이미지 s3 업로드
            final String[] base64Array = r.getMemorandumState().getMemoryImage().split(",");
            String dataUir, data;
            if (base64Array.length > 1) {
                dataUir = base64Array[0];
                data = base64Array[1];
            } else {
                //Build according to the specific file you represent
                dataUir = "data:image/jpg;base64";
                data = base64Array[0];
            }

            MultipartFile multipartFile = new Base64ToFile(data, dataUir);
            String memoryfileUrl=transactionService.uploadFile(multipartFile);


            // 추억이미지 s3 업로드 끝

            //각서 이미지 생성
                //dto 생성
            TransactionDto tdto=new TransactionDto();
            Map<String,String> signmap = r.getMemorandumState().getSign();

            List<SigneeDto> sList = new LinkedList<>();
            for( Map.Entry<String, String> elem : signmap.entrySet() ){
                SigneeDto sDto = new SigneeDto();
                String name = r.findParticipantBySessionId(elem.getKey());

                sDto.setSigneeName(name);
                sDto.setSignBase64(elem.getValue());
                sList.add(sDto);
                log.info("sign : "+sDto.getSigneeName() + " "+ sDto.getSignBase64());
            }


            tdto.setTxTitle(mstate.getTitle());
            tdto.setTxContent(mstate.getContent());
            tdto.setTxCreateDate(LocalDate.now());
            tdto.setTxExpDate(mstate.getExpire());
            tdto.setTxIsSecret(mstate.isSecret());
            tdto.setImageIsSecret(mstate.isMemorySecret());
            tdto.setSignees(sList);
            tdto.setImageUrl(memoryfileUrl);
                //dto 생성 끝


            BufferedImage bimage= apachePOIService.createPreview(tdto,headerAccessor.getSessionId());
            //각서 이미지 생성끝

            //각서 이미지 base64로 다시 변환
            String b64img=Base64ToFile.encodeToString(bimage,"png");
            r.getMemorandumState().setMemorandumPreview(b64img);
            message.setMessage("ok");
            message.setPreview(b64img);
            message.setTdto(tdto);

            template.convertAndSend("/sub/memorandum/preview/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/preview/" + message.getRoomCode(), message);
        }
    }

    @MessageMapping(value = "/memorandum/txhash")
    // headerAccessor는 소켓서버의 주인ID를 확인하기 위해서 사용
    public void txhash(txFormat message, SimpMessageHeaderAccessor headerAccessor) throws Exception {

        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
//        MemorandumState mstate=r.getMemorandumState();
        if (r.getHostSessionId().equals(headerAccessor.getSessionId())) {

            String preview = r.getMemorandumState().getMemorandumPreview();

            MultipartFile mf = new Base64ToFile(preview , "data:image/png;");


            String filename=apachePOIService.createImg(mf,message.getTxHash());

            // 작성 완료된 각서 db에 저장

            TransactionDto transactionDto = message.getTdto();
            transactionDto.setTxAddress(message.getTxHash());
            transactionService.writeTransaction(transactionDto, null);


            log.info(filename);
            //mf 를 다시 base64로
            File steganofied = new File(filename);
            byte[] fileContent = FileUtils.readFileToByteArray(steganofied);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            message.setMessage("ok");
            message.setMemorandumFinal(encodedString);

            template.convertAndSend("/sub/memorandum/txhash/" + message.getRoomCode(), message);
        } else {
            message.setMessage("you are not host");
            template.convertAndSendToUser(headerAccessor.getSessionId(), "/sub/memorandum/txhash/" + message.getRoomCode(), message);
        }
    }
    @MessageMapping("/chat/message")
    public void message(chatFormat message, SimpMessageHeaderAccessor headerAccessor) {
        Room r = roomList.findRoomByRoomCode(message.getRoomCode());
        r.addChatMessage(message.getSender(), message.getMessage());
        log.info(message.getSender() + ": " + message.getMessage() + " , roomCode: " + message.getRoomCode());
        template.convertAndSend("/sub/chat/message/" + message.getRoomCode(), message);
    }


    //소켓 끊김 감지
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("disconnect : " + sessionId);
        log.info(roomList.deleteParticipant(sessionId));

    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class chatFormat {
    private String roomCode;
    private String sender;
    private String message;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class chatLogFormat {
    private String roomId;
    private String senderNickName;
    private List<ChatMessage> chatLog;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class signFormat {
    private Map<String,Boolean> signState;
    private String message;
    private String sign;
    private String senderNickName;
    private String roomCode;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class joinFormat {
    private String message;
    private String senderNickName;
    private String roomCode;
    private Map<String,Boolean> signState;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class  previewFormat {
    private String message;
    private String preview;
    private String senderNickName;
    private String roomCode;
    private TransactionDto tdto;
}
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class  txFormat {
    private String message;
    private String senderNickName;
    private String txHash;
    private String roomCode;
    private TransactionDto tdto;
    private String memorandumFinal;
}
