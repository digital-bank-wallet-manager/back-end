package com.prog4.digitalbank.idGenretor;

import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class IdGenerator {
    private static final String string= "abcdefghijklmnopqrstuvwxyz";
    private static final String number = "0123456789";

    private static int length = 1;
    private  static final String combine = string  + number;
    private static SecureRandom secureRandom = new SecureRandom();

    public IdGenerator(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }


    public static String generateId (int times){
        StringBuilder stringBuilder = new StringBuilder(length*times);
        for (int i = 0; i < length*times; i++) {
            int charIndex = secureRandom.nextInt(combine.length());
            stringBuilder.append(combine.charAt(charIndex));
        }

        return stringBuilder.toString();
    }

    private static String generalLoop (int times){
        StringBuilder stringBuilder = new StringBuilder(length*times);
        for (int i = 0; i < length*times; i++) {
            int charIndex = secureRandom.nextInt(number.length());
            stringBuilder.append(number.charAt(charIndex));
        }
        return stringBuilder.toString();
    }

    public static String generateAccountNumber (){
        String ref = "XXX-MG";
        String code = generalLoop(3);
        String refCustomer = generalLoop(6);
        String regionCode = generalLoop(2);
        return ref+code+"-"+refCustomer+"-"+regionCode;
    }


    public static String generateTransferRef (){
        String ref = "TFR-";
        String id= generalLoop(6);
        return ref+id;
    }

    public static String generateTransactionRef (){
        String ref = "TRS-";
        String id= generalLoop(6);
        return ref+id;
    }




}



