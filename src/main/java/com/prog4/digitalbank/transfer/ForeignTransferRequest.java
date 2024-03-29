package com.prog4.digitalbank.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter@Setter
public class ForeignTransferRequest {
    private Transfer transfer;
    private List<ForeignReceiver> foreignReceivers;
}
