package com.KoreaIT.java.AM.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
public class Util {
	/** 날짜 데이터 반환 함수 : return str */
    public static String getNowDateTimeStr() {
 
        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now();

        // 포맷팅
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // 2023-03-09 16:20:11

        return formatedNow;
    }
}