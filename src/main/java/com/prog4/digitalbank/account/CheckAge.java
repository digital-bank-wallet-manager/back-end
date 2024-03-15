package com.prog4.digitalbank.account;

import java.time.LocalDate;
import java.util.Date;

public class CheckAge {
    public static int calculateAge (Date birthDate){
        String string = birthDate.toString();
        LocalDate date = LocalDate.parse(string);
        int yearBirth = date.getYear();
        LocalDate currentDate = LocalDate.now();
        int actualYear = currentDate.getYear();

        return  actualYear - yearBirth;
    }
}
