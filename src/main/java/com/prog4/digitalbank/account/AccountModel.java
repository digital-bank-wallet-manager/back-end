package com.prog4.digitalbank.account;

import lombok.*;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountModel {
    private String id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private float monthlyPay;
    private String AccountRef;

}
