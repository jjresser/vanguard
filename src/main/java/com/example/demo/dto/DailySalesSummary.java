package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DailySalesSummary {
    private LocalDate summaryDate;
    private int gameNo;
    private int totalGamesSold;
    private BigDecimal totalSales;

}
