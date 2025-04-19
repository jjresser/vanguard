package com.example.demo;

import com.example.demo.Utils.General;
import com.example.demo.dto.GameSale;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GenerateCsvFile {
    public static void main(String[] args) {
        String filePath = "C:/temp/vanguard/src/main/resources/import3.csv";
        int noOfRecords = 1000000;
        General.emptyFile(filePath);

        String headers = "id,game_no,game_name,game_code,type,cost_price,tax,sale_price,date_of_sale";
        General.appendFileContent(filePath,headers);
        List<GameSale> lsGameSale = new ArrayList<>();
        int tax = 9;
        for( int i =1 ; i <= noOfRecords ; i ++){
            double costPrice = General.getRandomNumberUsingNextDouble(1,100);
            //example csv row content = "1,5,Fortnite,FTN,1,50.00,4.50,54.50,2025-04-01 10:15:00.\n";
            lsGameSale.add( new GameSale(
                    i,
                    General.getRandomNumberUsingNextInt(1,101) ,
                    RandomStringUtils.randomAlphabetic(20),
                    RandomStringUtils.random(5,false,true),
                    General.getRandomNumberUsingNextInt(1,3),
                                new BigDecimal(String.format("%.2f", costPrice)),
                                new BigDecimal(String.valueOf(tax)),
                                new BigDecimal(String.format("%.2f", costPrice*(100+tax)/100)),
                                General.getRandomTimestampRange("2025-04-01 00:00:00","2025-04-30 23:59:59")
                )
            );
        }
        General.appendFileContentListOfGameSale(filePath,lsGameSale);
    }
}
