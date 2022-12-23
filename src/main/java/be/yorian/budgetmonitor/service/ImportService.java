package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.entity.ImportResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {

    ImportResponse handleImport(MultipartFile file);
}
