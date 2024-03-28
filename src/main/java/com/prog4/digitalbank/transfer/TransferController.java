package com.prog4.digitalbank.transfer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;

@AllArgsConstructor
@RestController
public class TransferController {
    private TransferServices transferServices;

    @PostMapping("/transfer/foreign")
    public String foreignTransfer (@RequestBody ForeignTransferRequest foreignTransferRequest) throws SQLException {
        return transferServices.foreignTransferOperation(
                foreignTransferRequest.getTransfer(),
                foreignTransferRequest.getForeignReceivers());
    }


    @PostMapping("/transfer/local")
    public String insideTransfer (@RequestBody LocalTransferRequest localTransferRequest) throws SQLException {
        return transferServices.localTransferOperation(
                localTransferRequest.getTransfer(),
                localTransferRequest.getLocalReceivers()
        );
    }

}
