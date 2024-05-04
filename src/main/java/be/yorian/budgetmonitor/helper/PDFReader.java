package be.yorian.budgetmonitor.helper;

import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.exception.TextParserException;
import be.yorian.budgetmonitor.exception.TransactionAdapterException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFReader {

    private final MultipartFile originalFile;

    public PDFReader(MultipartFile file) {
        this.originalFile = file;
    }

    public List<Transaction> parsePDFToTransactions()  {
        String text = createTextFromPDF();
        return convertToTransactions(text);
    }

    private List<Transaction> convertToTransactions(String text) {
        TextToTransactionParser textToTransactionParser = new TextToTransactionParser(text);
        try {
            return textToTransactionParser.filterTransactionsFromText();
        } catch (TextParserException e) {
            throw new TransactionAdapterException("Something went wrong: Convert to transactions", e);
        }
    }

    private String createTextFromPDF() {
        File pdfFile = parseOriginalFile();
        PdfToTextParser pdfToTextParser = new PdfToTextParser(pdfFile);
        try {
            return pdfToTextParser.createTextFromPDF();
        } catch (IOException e) {
            throw new TransactionAdapterException("Something went wrong: Parse to text", e);
        }
    }

    private File parseOriginalFile() {
        File pdfFile = new File(System.getProperty("java.io.tmpdir")+"/targetFile.tmp");
        try {
            this.originalFile.transferTo(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfFile;
    }

}
