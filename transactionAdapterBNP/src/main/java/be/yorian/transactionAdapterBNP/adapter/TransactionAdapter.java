package be.yorian.transactionAdapterBNP.adapter;

import be.yorian.transactionAdapterBNP.dto.TransactionDTO;

import java.io.File;
import java.util.List;

public interface TransactionAdapter {

    List<TransactionDTO> convertPdfFile(File file);

}