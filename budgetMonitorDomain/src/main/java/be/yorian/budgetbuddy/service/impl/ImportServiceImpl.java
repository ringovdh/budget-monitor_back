package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.adapter.database.repository.TransactionEntityRepository;
import be.yorian.budgetbuddy.helper.ImportResponseHelper;
import be.yorian.budgetbuddy.mapper.TransactionMapper;
import be.yorian.budgetbuddy.model.ImportTransactionsResponse;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.service.ImportService;
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

    private final TransactionEntityRepository transactionRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ImportServiceImpl(TransactionEntityRepository transactionRepository,
                             CommentRepository commentRepository) {
        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ImportTransactionsResponse handleImport(MultipartFile file) {
        TransactionAdapter transactionAdapter = TransactionAdapterFactory.createTransactionAdapter();
        List<TransactionDTO> transactionDtos = new ArrayList<>();
        if (null != file.getOriginalFilename()) {
            File convFile = new File("temp/"+file.getOriginalFilename());
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
        List<TransactionEntity> transactionEntities = transactionDtos.stream().map(TransactionMapper::mapTransaction).toList();
        ImportResponseHelper helper = new ImportResponseHelper(transactionRepository, commentRepository, transactionEntities);

        return helper.createImportResponse();
    }
}
