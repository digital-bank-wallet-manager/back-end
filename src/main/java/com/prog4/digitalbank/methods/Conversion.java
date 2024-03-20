package com.prog4.digitalbank.methods;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class Conversion {
    public static Timestamp DateToTimestamp (Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        int currentSecond = Calendar.getInstance().get(Calendar.SECOND);

        calendar.set(Calendar.HOUR_OF_DAY , currentHour);
        calendar.set(Calendar.MINUTE , currentMinute);
        calendar.set(Calendar.SECOND , currentSecond);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static String convertToSnakeCase(String string){
        StringBuilder snakeCase = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (Character.isUpperCase(c)) {
                snakeCase.append('_').append(Character.toLowerCase(c));
            } else {
                snakeCase.append(c);
            }
        }
        return snakeCase.toString();
    }

    public static String firstCharToLowercase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }
}
