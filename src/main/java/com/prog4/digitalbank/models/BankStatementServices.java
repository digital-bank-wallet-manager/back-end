package com.prog4.digitalbank.models;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
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
        Font font = new Font(Font.FontFamily.HELVETICA, 10);
        PdfPTable table = new PdfPTable(6);

        table.addCell(new PdfPCell(new Paragraph("date",font)));
        table.addCell(new PdfPCell(new Paragraph("transaction ref",font)));
        table.addCell(new PdfPCell(new Paragraph("operation",font)));
        table.addCell(new PdfPCell(new Paragraph("amount",font)));
        table.addCell(new PdfPCell(new Paragraph("balance",font)));
        table.addCell(new PdfPCell(new Paragraph("pattern",font)));

        for (BankStatement bankStatement : bankStatements){
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getDate().toString(),font)));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getTransactionRef(),font)));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getOperation(),font)));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getAmount().toString(),font)));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getBalance().toString(),font)));
            table.addCell(new PdfPCell(new Paragraph(bankStatement.getPattern(),font)));
        }

        document.add(table);
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
