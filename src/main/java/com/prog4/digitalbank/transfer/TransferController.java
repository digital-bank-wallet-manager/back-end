package com.prog4.digitalbank.transfer;

import com.prog4.digitalbank.Messages;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

@AllArgsConstructor
@RestController
@RequestMapping("/transfer")
public class TransferController {
    private TransferServices transferServices;

    @PostMapping("/foreign")

    public Messages foreignTransfer (@RequestBody ForeignTransferRequest foreignTransferRequest) throws SQLException {
        return transferServices.foreignTransferOperation(
                foreignTransferRequest.getTransfer(),
                foreignTransferRequest.getForeignReceivers());
    }

    @PostMapping("/local")
    public Messages insideTransfer (@RequestBody LocalTransferRequest localTransferRequest) throws SQLException {
        return transferServices.localTransferOperation(
                localTransferRequest.getTransfer(),
                localTransferRequest.getLocalReceivers()
        );
    }

    @PutMapping("/cancel/{transferId}")
    public Messages cancelTransfer(@PathVariable String transferId){
        return transferServices.cancelTransfer(transferId);
    }

}
