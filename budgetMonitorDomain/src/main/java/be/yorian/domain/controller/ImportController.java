package be.yorian.domain.controller;

import be.yorian.domain.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImportController {

    ResponseEntity<CustomResponse> importTransactions(MultipartFile file);
}
