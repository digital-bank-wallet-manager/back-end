package com.prog4.digitalbank.expense;

import lombok.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Expense {
    private String id ;
    private Double amount ;
    private Timestamp dateTime;
    private String accountId;

    public Expense(String id) {
        this.id = id;
    }
}
