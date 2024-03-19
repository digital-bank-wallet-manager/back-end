package com.prog4.digitalbank.transfer;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ForeignTransfer {
    private String id ;
    private String accountRef;

}
