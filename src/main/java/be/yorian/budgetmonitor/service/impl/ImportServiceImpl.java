package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.entity.ImportResponse;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.helper.ImportResponseHelper;
import be.yorian.budgetmonitor.helper.PDFReader;
import be.yorian.budgetmonitor.repository.CommentRepository;
import be.yorian.budgetmonitor.repository.TransactionRepository;
import be.yorian.budgetmonitor.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {

    private final TransactionRepository transactionRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ImportServiceImpl(TransactionRepository transactionRepository, CommentRepository commentRepository) {
        this.transactionRepository = transactionRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public ImportResponse handleImport(MultipartFile file) {

        PDFReader pdfReader = new PDFReader(file);
        List<Transaction> transactions = pdfReader.parsePDFToTransactions();
        ImportResponseHelper helper = new ImportResponseHelper(transactionRepository, commentRepository, transactions);

        return helper.createImportResponse();
    }
}
