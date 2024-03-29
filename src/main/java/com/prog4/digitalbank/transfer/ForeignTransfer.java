package com.prog4.digitalbank.transfer;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ForeignTransfer {
    private String id ;
    private String accountRef;

}
