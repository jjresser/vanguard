package com.example.demo.controller;

import com.example.demo.Dto.DailySalesSummary;
import com.example.demo.repository.GameSalesSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/getTotalSales")
public class GameSalesSummaryController {

    @Autowired
    private GameSalesSummaryRepository repository;

    // 1. Daily count of games sold
    @GetMapping("/count")
    public List<DailySalesSummary> getDailyGameCounts(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return repository.getGameCounts(from, to);
    }

    // 2. Daily total sales (all games)
    @GetMapping("/sales")
    public List<DailySalesSummary> getDailySales(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return repository.getTotalSales(from, to);
    }

    // 3. Daily total sales for a specific game_no
    @GetMapping("/salesByGameNo")
    public List<DailySalesSummary> getSalesByGameNo(
            @RequestParam("gameNo") int gameNo,
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return repository.getTotalSalesByGameNo(gameNo, from, to);
    }
}
