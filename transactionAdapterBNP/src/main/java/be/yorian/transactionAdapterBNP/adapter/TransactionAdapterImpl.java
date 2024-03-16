package be.yorian.transactionAdapterBNP.adapter;

import be.yorian.transactionAdapterBNP.parser.PdfToTextParser;
import be.yorian.transactionAdapterBNP.parser.TextToTransactionParser;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;
import be.yorian.transactionAdapterBNP.exception.TextParserException;
import be.yorian.transactionAdapterBNP.exception.TransactionAdapterException;

import java.io.File;
import java.io.IOException;
import java.util.List;

class TransactionAdapterImpl implements TransactionAdapter {

    @Override
    public List<TransactionDTO> convertPdfFile(File file) {
        String text = parseToText(file);
        return convertToTransactions(text);
    }

    private List<TransactionDTO> convertToTransactions(String text) {
        TextToTransactionParser textToTransactionParser = new TextToTransactionParser(text);
        try {
            return textToTransactionParser.filterTransactionsFromText();
        } catch (TextParserException e) {
            throw new TransactionAdapterException("Something went wrong: Convert to transactions", e);
        }
    }

    private String parseToText(File file) {
        PdfToTextParser pdfToTextParser = new PdfToTextParser(file);
        try {
            return pdfToTextParser.createTextFromPDF();
        } catch (IOException e) {
            throw new TransactionAdapterException("Something went wrong: Parse to text", e);
        }
    }

}
