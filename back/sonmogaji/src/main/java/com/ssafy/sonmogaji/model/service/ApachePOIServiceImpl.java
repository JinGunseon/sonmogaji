package com.ssafy.sonmogaji.model.service;
import com.amazonaws.services.s3.AmazonS3;
import com.spire.doc.Document;
import com.spire.doc.documents.ImageType;
import com.ssafy.sonmogaji.model.dto.TransactionDto;
import com.ssafy.sonmogaji.util.Base64ToImgDecoder;
import com.ssafy.sonmogaji.util.Steganographer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
@Service
@Log4j2
@RequiredArgsConstructor
public class ApachePOIServiceImpl implements ApachePOIService{
    //    private static final String PATH = "classpath:/static/";
    private final Base64ToImgDecoder base64ToImgDecoder;
    private final Steganographer steganographer;
    private final AmazonS3 amazonS3;
    @Override
    public BufferedImage createPreview(TransactionDto transactionDto,String sessionId) throws Exception {
        String sample =  File.separator+ "app" +File.separator + "memorandom.docx";
        FileOutputStream fos = null;

        // 각서 원본 docx 파일 생성
        try {
            // 각서 샘플파일 복사하기
            File file = new File(sample);
//            File file = new File(resource.getFilename());
            File newFile = new File( File.separator+ "app" +File.separator +sessionId+"memorandom_preview.docx");

            Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            XWPFDocument doc = new XWPFDocument(new FileInputStream(newFile));
            // 본문 입력하기
            for(XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if(runs != null) {
                    for(XWPFRun r : runs) {
                        String text = r.getText(0);
                        if(text != null && text.contains("제목:")) {
                            text = text.replace("제목:", transactionDto.getTxTitle());
//                            text = text.concat(transactionDto.getTxTitle());
                            r.setText(text, 0);
                        }
                        if(text != null && text.contains("내용:")) {

                            StringTokenizer st = new StringTokenizer(transactionDto.getTxContent());
                            StringBuilder sb = new StringBuilder();
                            while(st.hasMoreElements()) {
                                sb.append(st.nextToken()).append("\n");
                            }

                            text = text.replace("내용:", sb);


//                            text = text.replace("내용", transactionDto.getTxContent());
//                            text = text.concat(transactionDto.getTxContent());
                            r.setText(text, 0);
                        }

                        if(text != null && text.contains("만료일:")) {

                            if(transactionDto.getTxExpDate() == null) {
                                text = text.replace("만료일:", "만료일: " + "무기한");
                            } else {
                                text = text.replace("만료일:", "만료일: " + transactionDto.getTxExpDate().toString());
                            }
                            r.setText(text, 0);
                        }
                        if(text != null && text.contains("날짜")) {
                            text = text.replace("날짜", transactionDto.getTxCreateDate().toString());
//                            text = text.concat(transactionDto.getTxCreateDate().toString());
                            r.setText(text, 0);
                        }
                    }
                }
            }
            XWPFTable table = null;
            // 테이블 요소 구하기
            Iterator<IBodyElement> docElementsIterator = doc.getBodyElementsIterator();
            while(docElementsIterator.hasNext()) {
                IBodyElement docElement = docElementsIterator.next();
                if("TABLE".equalsIgnoreCase(docElement.getElementType().name())) {
                    List<XWPFTable> xwpfTableList = docElement.getBody().getTables();
                    table = xwpfTableList.get(0);
                }
            }
            for(int i = 0; i<transactionDto.getSignees().size() -2 ; i++) {
                table.createRow();
            }
            for(int i = 0; i < transactionDto.getSignees().size(); i++ ) {
                XWPFTableRow row = table.getRow(i);
                row.getCell(0).setText(transactionDto.getSignees().get(i).getSigneeName());
                // 사인 이미지 넣기
                String data = transactionDto.getSignees().get(i).getSignBase64().split(",")[1];
                byte[] imgBytes = DatatypeConverter.parseBase64Binary(data);
                BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imgBytes));
                InputStream is = new ByteArrayInputStream(imgBytes);
                int imgType = XWPFDocument.PICTURE_TYPE_PNG;
                String imgFileName = transactionDto.getSignees().get(i).getSigneeName();
                int width = 100;
                int height = 50;
                XWPFParagraph paragraph = doc.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.addPicture(is, imgType, imgFileName,Units.toEMU(width), Units.toEMU(height));
                row.getCell(1).setParagraph(paragraph);
                if(paragraph != null) {
                    doc.removeBodyElement(doc.getPosOfParagraph(paragraph));
                }
            }
            fos = new FileOutputStream(new File(File.separator+ "app" +File.separator +sessionId+"memorandom_preview.docx"));
            doc.write(fos);
            if(fos != null) fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 각서 이미지로 변환하기
        File file = new File(File.separator+ "app" +File.separator +sessionId+"memorandom_preview.docx");

        Document document = new Document();
        document.loadFromFile(File.separator+ "app" +File.separator +sessionId+"memorandom_preview.docx");

        BufferedImage image = document.saveToImages(0, ImageType.Bitmap);

        File imgFile = new File(File.separator+ "app" +File.separator +sessionId+"Preview.PNG");
        ImageIO.write(image, "PNG", imgFile);
        return image;
    }
    @Override
    public String createImg(MultipartFile file, String transactionAddress) throws IOException {
        File preview = new File(file.getOriginalFilename());
        file.transferTo(preview);
        String str=steganographer.encode(preview, transactionAddress);
        return str;
    }
}
