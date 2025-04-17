package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailySalesSummary {
    private LocalDate summaryDate;
    private int gameNo;
    private int totalGamesSold;
    private BigDecimal totalSales;

    public LocalDate getSummaryDate() {
        return summaryDate;
    }

    public void setSummaryDate(LocalDate summaryDate) {
        this.summaryDate = summaryDate;
    }

    public int getGameNo() {
        return gameNo;
    }

    public void setGameNo(int gameNo) {
        this.gameNo = gameNo;
    }

    public int getTotalGamesSold() {
        return totalGamesSold;
    }

    public void setTotalGamesSold(int totalGamesSold) {
        this.totalGamesSold = totalGamesSold;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
}
