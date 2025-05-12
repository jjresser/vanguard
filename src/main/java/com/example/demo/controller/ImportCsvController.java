package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.Utils.JsonKit;
import com.example.demo.service.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/csv")
public class ImportCsvController {

    @Autowired
    private CsvImportService csvImportService;

    // http://localhost:8080/csv/delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> delDbRecords(){
        return ResponseEntity.ok("delete : "+csvImportService.deleteDbGameSaleTableData());
    }

    // http://localhost:8080/csv/import
    @PostMapping(path = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importCsv(@ModelAttribute("request") @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        long startTime = System.nanoTime();

        try {
            ConcurrentHashMap<String,Object> result = csvImportService.importCsv(file.getInputStream());
            double durationInSeconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
            //return ResponseEntity.ok("Imported " + count + " records successfully. Execution time: " + durationInSeconds + " seconds");
            return ResponseEntity.ok(JsonKit.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CSV import failed: " + e.getMessage());
        }
    }

    @GetMapping(path = "/id/{num}")
    @ResponseBody
    public ResponseEntity<String> getId(@PathVariable int id){
        JSONObject jsonObject = csvImportService.getJSONObjectFromQuery("select game_no from game_sales where id = ?");
        return ResponseEntity.ok("");
    }
}
