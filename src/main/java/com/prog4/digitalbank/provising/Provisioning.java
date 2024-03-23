package com.prog4.digitalbank.provising;

import lombok.*;

import java.sql.Date;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Provisioning {
    private String id ;
    private Double amount ;
    private String reason ;
    private Date effectiveDate ;
    private Date recordDate ;
    private String accountId;

    public Provisioning(String id) {
        this.id = id;
    }
}
