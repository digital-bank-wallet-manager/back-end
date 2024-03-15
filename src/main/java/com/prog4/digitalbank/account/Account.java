package com.prog4.digitalbank.account;

import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Account {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private Boolean loanAuthorization ;
    private Double monthlyPay;
    private String accountRef;

    public Account(String firstName, String lastName, Date birthdate, Double monthlyPay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.monthlyPay = monthlyPay;
    }

    public Account(String id) {
        this.id = id;
    }
}
