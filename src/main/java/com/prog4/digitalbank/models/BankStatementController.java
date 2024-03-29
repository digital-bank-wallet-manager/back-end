package com.prog4.digitalbank.models;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankStatementController {
    private BankStatementServices bankStatementServices;
    @GetMapping("/bankStatement/{accountId}/{month}")
    public List<BankStatement> bankStatements (@PathVariable String accountId ,@PathVariable int month){
        return bankStatementServices.bankStatements(accountId , month);
    }

    @SneakyThrows
    @GetMapping(value = "/download/{accountId}/{month}" , produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<ByteArrayResource> downloadPDF(@PathVariable String accountId ,@PathVariable int month) throws DocumentException {
        byte[] pdfBytes = bankStatementServices.generatePdf(accountId , month);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+accountId+".pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
