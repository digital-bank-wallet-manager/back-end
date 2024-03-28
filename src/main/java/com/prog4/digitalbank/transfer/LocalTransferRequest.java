package com.prog4.digitalbank.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@AllArgsConstructor
@ToString
@Getter
public class LocalTransferRequest {
    private Transfer transfer;
    private List<LocalReceiver> localReceivers;
}
