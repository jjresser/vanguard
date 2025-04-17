package com.example.demo.controller;

import com.example.demo.service.CsvImportService; // make sure this path matches your actual service package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
public class ImportCsvController {

    @Autowired
    private CsvImportService csvImportService;

    // http://localhost:8080/csv/delete
    @GetMapping("/delete")
    public ResponseEntity<String> delDbRecords(){
        return ResponseEntity.ok("delete : "+csvImportService.deleteDbGameSaleTableData());
    }

    // http://localhost:8080/csv/import
    @PostMapping("/import")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        long startTime = System.nanoTime();

        try {
            int count = csvImportService.importCsv(file.getInputStream());
            double durationInSeconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
            return ResponseEntity.ok("Imported " + count + " records successfully. Execution time: " + durationInSeconds + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CSV import failed: " + e.getMessage());
        }
    }
}
