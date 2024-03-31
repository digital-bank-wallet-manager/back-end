package com.prog4.digitalbank.models.expensePovisioningSum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ExpenseSum {
    private Double  expense_total_sum;
    private String category_name;
}
