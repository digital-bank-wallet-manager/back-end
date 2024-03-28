package com.prog4.digitalbank.transfer;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LocalReceiver {
    private String accountRef;
    private String firstName;
    private String LastName;
    private Double amount;
    private Date effectiveDate;
    private String reason;
    private int subCategoryId;
}
