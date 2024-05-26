package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.model.ImportTransactionsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImportService {

    ImportTransactionsResponse handleImport(MultipartFile file);
}
