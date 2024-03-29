package com.prog4.digitalbank.models;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@AllArgsConstructor
public class BankStatementServices {
    private BankStatementRepository bankStatementRepository;
    public List<BankStatement> bankStatements(String accountId , int month){
        return bankStatementRepository.bankStatements(accountId,month);
    }

    public byte[] generatePdf(String accountId , int month) throws DocumentException {
        List<BankStatement> bankStatements = bankStatements(accountId , month);
        Document document = new com.itextpdf.text.Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document , byteArrayOutputStream);
        document.open();

        PdfPTable table = new PdfPTable(6);

        table.addCell(new PdfPCell(new Paragraph("date")));
        table.addCell(new PdfPCell(new Paragraph("transaction ref")));
        table.addCell(new PdfPCell(new Paragraph("operation")));
        table.addCell(new PdfPCell(new Paragraph("amount")));
        table.addCell(new PdfPCell(new Paragraph("balance")));
        table.addCell(new PdfPCell(new Paragraph("pattern")));

        for (BankStatement bankStatement : bankStatements){
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getDate().toString())));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getTransactionRef())));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getOperation())));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getAmount().toString())));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getBalance().toString())));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getPattern())));
        }
        document.add(table);
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
