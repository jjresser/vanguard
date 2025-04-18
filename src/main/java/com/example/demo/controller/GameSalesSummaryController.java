package com.example.demo.controller;

import com.example.demo.dto.DailySalesSummary;
import com.example.demo.repository.GameSalesSummaryRepository;
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

@RestController
@RequestMapping("/getTotalSales")
public class GameSalesSummaryController {

    @Autowired
    private GameSalesSummaryRepository repository;

    // 1. Daily count of games sold
    // http://localhost:8080/getTotalSales/count?fromDate=2025-04-01&toDate=2025-04-02
    @GetMapping("/count")
    public ResponseEntity<String> getDailyGameCounts(
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-01")
            )
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-21")
            )
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok("totalGameSold : " + repository.getGameCounts(from, to).stream().mapToInt(DailySalesSummary::getTotalGamesSold).sum());
    }

    // 2. Daily total sales (all games)
    // http://localhost:8080/getTotalSales/sales?fromDate=2025-04-01&toDate=2025-04-02
    @GetMapping("/sales")
    public ResponseEntity<String> getDailySales(
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-01")
            )
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-21")
            )
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok("totalSales : " + repository.getGameCounts(from, to).stream().map(DailySalesSummary::getTotalSales).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    // 3. Daily total sales for a specific game_no
    // http://localhost:8080/getTotalSales/salesByGameNo?gameNo=3&fromDate=2025-04-01&toDate=2025-04-02
    @GetMapping("/salesByGameNo")
    public ResponseEntity<String> getSalesByGameNo(
            @RequestParam("gameNo") int gameNo,
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-01")
            )
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @Parameter(
                    description = "date in format yyyy-MM-dd",
                    schema = @Schema(type = "string", format = "date", example = "2025-04-20")
            )
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok("totalSales : " +repository.getTotalSalesByGameNo(gameNo, from, to).stream().map(DailySalesSummary::getTotalSales).reduce(BigDecimal.ZERO,BigDecimal::add));
    }
}
