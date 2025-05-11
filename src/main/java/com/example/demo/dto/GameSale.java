package com.example.demo.dto;

import com.example.demo.Utils.General;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class GameSale {
    private int id;
    private int gameNo;
    private String gameName;
    private String gameCode;
    private int type;
    private BigDecimal costPrice;
    private BigDecimal tax;
    private BigDecimal salePrice;
    private Timestamp dateOfSale;

    public GameSale() {
    }

    public GameSale(
             int id,
             int gameNo,
             String gameName,
             String gameCode,
             int type,
             BigDecimal costPrice,
             BigDecimal tax,
             BigDecimal salePrice,
             Timestamp dateOfSale

    ){
        this.id = id;
        this.gameNo = gameNo;
        this.gameName = gameName;
        this.gameCode = gameCode;
        this.type = type;
        this.costPrice = costPrice;
        this.tax = tax;
        this.salePrice = salePrice;
        this.dateOfSale = dateOfSale;
    }

    @Override
    public String toString() {
        return String.join(",",String.valueOf(id),
                                       String.valueOf(gameNo),
                                       gameName,
                                       gameCode,
                                       String.valueOf(type),
                                       costPrice.toString(),
                                       tax.toString(),
                                       salePrice.toString(),
                                       General.timestampToString(dateOfSale)
        );
    }
// Getters and setters
}
