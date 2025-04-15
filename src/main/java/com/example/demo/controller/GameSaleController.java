package com.example.demo.controller;

import com.example.demo.Dto.GameSale;
import com.example.demo.repository.GameSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    @GetMapping
    public List<GameSale> getAllSales() {
        return repository.getAllSales();
    }

    // 2. Filter by date range
    @GetMapping("/byDate")
    public List<GameSale> getSalesByDateRange(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        return repository.getSalesByDateRange(fromDate, toDate);
    }

    // 3. Filter by price (less than or more than)
    @GetMapping("/byPrice")
    public List<GameSale> getSalesByPrice(
            @RequestParam("price") BigDecimal price,
            @RequestParam("operator") String operator // expected values: "<" or ">"
    ) {
        return repository.getSalesByPriceFilter(price, operator);
    }
}
