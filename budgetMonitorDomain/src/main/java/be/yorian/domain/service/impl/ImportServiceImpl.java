package be.yorian.domain.service.impl;

import be.yorian.domain.entity.ImportTransactionsResponse;
import be.yorian.domain.entity.Transaction;
import be.yorian.domain.helper.ImportResponseHelper;
import be.yorian.domain.mapper.TransactionMapper;
import be.yorian.domain.repository.CommentRepository;
import be.yorian.domain.repository.TransactionRepository;
import be.yorian.domain.service.ImportService;
import be.yorian.transactionAdapterBNP.adapter.TransactionAdapter;
import be.yorian.transactionAdapterBNP.adapter.TransactionAdapterFactory;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {

    private final TransactionRepository transactionRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ImportServiceImpl(TransactionRepository transactionRepository,
                             CommentRepository commentRepository) {
        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ImportTransactionsResponse handleImport(MultipartFile file) {
        TransactionAdapter transactionAdapter = TransactionAdapterFactory.createTransactionAdapter();
        List<TransactionDTO> transactionDtos = new ArrayList<>();
        if (null != file.getOriginalFilename()) {
            File convFile = new File(file.getOriginalFilename());
            try {
                convFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(file.getBytes());
                fos.close(); //IOUtils.closeQuietly(fos);

            } catch (IOException e) {
                convFile = null;
            }
            transactionDtos = transactionAdapter.convertPdfFile(convFile);
        }
        List<Transaction> transactions = transactionDtos.stream().map(TransactionMapper::mapTransaction).toList();
        ImportResponseHelper helper = new ImportResponseHelper(transactionRepository, commentRepository, transactions);

        return helper.createImportResponse();
    }
}