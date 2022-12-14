package com.ssafy.sonmogaji.model.entity.room;


import jnr.a64asm.Mem;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class MemorandumState {
    private List<Map<String,Boolean>> agree=new ArrayList<>();
//    private List<HashMap<String,Boolean>> agree;
    private String title;
    private String content;
    private boolean secret;
    private LocalDate expire;
    private String memoryImage;
    private boolean memorySecret;
    private HashMap<String,String> sign;
    private HashMap<String,Boolean> signState;

    private String memorandumPreview; //프리뷰 이미지
//
    public MemorandumState(){
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        Map<String, Boolean> map2 = new HashMap<String, Boolean>();
        Map<String, Boolean> map3 = new HashMap<String, Boolean>();
        Map<String, Boolean> map4 = new HashMap<String, Boolean>();
        Map<String, Boolean> map5 = new HashMap<String, Boolean>();
        Map<String, Boolean> map6 = new HashMap<String, Boolean>();
        Map<String, Boolean> map7 = new HashMap<String, Boolean>();
        this.agree.add(map);
        this.agree.add(map2);
        this.agree.add(map3);
        this.agree.add(map4);
        this.agree.add(map5);
        this.agree.add(map6);
        this.agree.add(map7);
        this.title = "";
        this.content = "";
        this.secret = false;
        this.expire = LocalDate.now();
        this.memoryImage = "";
        this.memorySecret = false;
        this.sign = new HashMap<String,String>();
        this.signState=new HashMap<>();
        this.memorandumPreview="";

    }

}
