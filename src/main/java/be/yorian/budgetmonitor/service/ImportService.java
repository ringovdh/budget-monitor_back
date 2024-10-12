package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.dto.ImportTransactionsResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {

    ImportTransactionsResponse handleImport(MultipartFile file);
}
