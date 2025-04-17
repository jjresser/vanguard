package com.example.demo.dto;

import com.example.demo.Utils.General;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameNo() {
        return gameNo;
    }

    public void setGameNo(int gameNo) {
        this.gameNo = gameNo;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Timestamp getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Timestamp dateOfSale) {
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
