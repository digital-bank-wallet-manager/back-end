package com.prog4.digitalbank.methods;

import java.sql.Date;
import java.time.LocalDate;

import static java.sql.Date.valueOf;


public class CheckDateValidy {
    public static boolean checkDateValidity(Date date , int minDay){
        LocalDate referenceDate = date.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        LocalDate minDate = currentDate.plusDays(minDay);
        return referenceDate.isAfter(minDate);
    }

    public static Date addDayToDate (Date date , int day){
       LocalDate finalDate =  date.toLocalDate().plusDays(day);
       return valueOf(finalDate);
    }

}
