package com.example.demo.controller;

import com.example.demo.dto.GameSale;
import com.example.demo.repository.GameSaleRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/getGameSales")
public class GameSaleController {

    @Autowired
    private GameSaleRepository repository;

    // 1. Get all sales
    // http://localhost:8080/getGameSales?page=5&size=100
    @GetMapping
    public ResponseEntity<List<GameSale>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return ResponseEntity.ok(repository.getAllSales(page,size));
    }

    // 2. Filter by date range
    // http://localhost:8080/getGameSales/byDate?fromDate=2025-04-01&toDate=2025-04-02&page=5&size=150
    @GetMapping("/byDate")
    public List<GameSale> getSalesByDateRange(
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-01")
            )
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-20")
            )
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return repository.getSalesByDateRange(fromDate, toDate , page , size);
    }

    // 3. Filter by price (less than or more than)
    // http://localhost:8080/getGameSales/byPrice?price=50&operator=<&page=5&size=100
    @GetMapping("/byPrice")
    public List<GameSale> getSalesByPrice(
            @RequestParam("price") BigDecimal price,
            @Parameter(
                    description = "< or >",
                    schema = @Schema(type = "string", example = "<")
            )
            @RequestParam("operator") String operator, // expected values: "<" or ">"
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return repository.getSalesByPriceFilter(price, operator, page , size);
    }
}
