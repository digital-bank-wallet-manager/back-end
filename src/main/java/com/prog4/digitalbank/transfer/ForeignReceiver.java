package com.prog4.digitalbank.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.sql.Date;

@AllArgsConstructor
@Getter
@Setter

public class ForeignReceiver {
    private String receiverAccount;
    private Double amount;
    private String reason;
    private Date effectiveDate;
    private int subCategory;
}
