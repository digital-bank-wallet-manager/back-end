package com.prog4.digitalbank.transfer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> Prod

@AllArgsConstructor
@RestController
public class TransferController {
    private TransferServices transferServices;

    @PostMapping("/transfer/foreign")
<<<<<<< HEAD
    public String foreignTransfer (@RequestBody ForeignTransferRequest foreignTransferRequest) throws SQLException {
        return transferServices.foreignTransferOperation(
                foreignTransferRequest.getTransfer(),
                foreignTransferRequest.getForeignReceivers());
=======
    public String foreignTransfer (@RequestBody ForeingTransferRequest foreingTransferRequest) throws SQLException {
        return transferServices.foreignTransferOperation(
                foreingTransferRequest.getTransfer(),
                foreingTransferRequest.getForeignReceivers());
>>>>>>> Prod
    }


    @PostMapping("/transfer/local")
    public String insideTransfer (@RequestBody LocalTransferRequest localTransferRequest) throws SQLException {
        return transferServices.localTransferOperation(
                localTransferRequest.getTransfer(),
                localTransferRequest.getLocalReceivers()
        );
    }

}
