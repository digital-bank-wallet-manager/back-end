package com.prog4.digitalbank.category;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Category {
    private int id;
    private String name;
    private String type;
}
