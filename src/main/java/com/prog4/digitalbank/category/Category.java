package com.prog4.digitalbank.category;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter@ToString@EqualsAndHashCode
public class Category {
    private int subCategoryId;
    private String category;
    private String subCategory;

}
