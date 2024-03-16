package be.yorian.domain.service;

import be.yorian.domain.entity.ImportTransactionsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImportService {

    ImportTransactionsResponse handleImport(MultipartFile file);
}
