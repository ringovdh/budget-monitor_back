package be.yorian.budgetmonitor.controller.impl;

import be.yorian.budgetmonitor.controller.ImportController;
import be.yorian.budgetmonitor.entity.ImportTransactionsResponse;
import be.yorian.budgetmonitor.response.CustomResponse;
import be.yorian.budgetmonitor.service.impl.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ImportControllerImpl implements ImportController {

    @Autowired
    ImportServiceImpl importService;

    @Override
    @PostMapping("/importTransactions")
    public ResponseEntity<CustomResponse> importTransactions(@RequestParam("file") MultipartFile file) {

        CustomResponse response = new CustomResponse();
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        Map<String, ImportTransactionsResponse> dataMap = new HashMap<>();
        dataMap.put("import", importService.handleImport(file));
        response.setData(dataMap);

        return ResponseEntity.ok().body(response);
    }
}
