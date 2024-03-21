package com.prog4.digitalbank.methods;

import java.sql.Timestamp;

public class InterestCalcul {
    private static int calculateDay(Timestamp timestamp1, Timestamp timestamp2){
        Long date1 = timestamp1.getTime();
        Long date2 = timestamp2.getTime();
        Long diff = Math.abs(date2 - date1);
        int day = (int) (diff / (1000 * 60 * 60 * 24));
        return day;
    }

    public static double interest (Timestamp timestamp1,
                            Timestamp timestamp2 ,
                            double interest1 ,
                            double interest2 ,
                            double amount){
        int dayTotal = calculateDay(timestamp1 , timestamp2);
        double interestAmount1 = amount * (interest1/100);
        double interestAmount2 = amount * (interest2/100);
        double total = 0.0;
        if (dayTotal <= 7){
            total = interestAmount1 * dayTotal;
        }
        if (dayTotal > 7) {
            total = (interestAmount1 * 7) + interestAmount2 * (dayTotal - 7);
        }
        return total;
    }

}

