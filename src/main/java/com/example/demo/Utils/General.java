package com.example.demo.Utils;

import com.example.demo.dto.GameSale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class General {

    public static List<GameSale> csvReadFileByLineGameSale(String filePath) {
        List<GameSale> lsGameSale = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip header
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Do something with the line (e.g., print it)
                String[] fields = line.split(",");

                GameSale row = new GameSale(
                        Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]),
                        fields[2],
                        fields[3],
                        Integer.parseInt(fields[4]),
                        new BigDecimal(fields[5]),
                        new BigDecimal(fields[6]),
                        new BigDecimal(fields[7]),
                        General.dateTimeStrTotimestamp(fields[8])
                );
                lsGameSale.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lsGameSale;
    }

    public static Timestamp dateTimeStrTotimestamp(String dateTimeStr){
        //String dateTimeStr = "2025-04-01 10:15:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, formatter);
        return Timestamp.valueOf(localDateTime);

    }

    public static String timestampToString(Timestamp timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.toLocalDateTime().format(formatter);
    }

    public static void appendFileContent(String filePath,String contentToAppend){
        createFileIfNotExists(filePath);
        try {
            // Content to append
            contentToAppend = contentToAppend+"\n";
            // Append content to the file using streams
            Path path = Paths.get(filePath);
            Files.write(path, contentToAppend.getBytes(), StandardOpenOption.APPEND);

            //System.out.println("Content appended to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void appendFileContentListOfGameSale(String filePath, List<GameSale> dtoList) {
        createFileIfNotExists(filePath);
        StringBuilder contentBuilder = new StringBuilder();

        for (GameSale dto : dtoList) {
            contentBuilder.append(dto.toString()).append("\n");
        }

        try {
            Path path = Paths.get(filePath);
            Files.write(path, contentBuilder.toString().getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFileIfNotExists(String filePath) {
        Path path = Paths.get(filePath);

        try {
            // Ensure parent directories exist
            Files.createDirectories(path.getParent());

            // Create the file if it doesn't exist
            if (Files.notExists(path)) {
                Files.createFile(path);
               // System.out.println("File created: " + path.toAbsolutePath());
            }

        } catch (Exception e) {
            //System.err.println("Error creating file: " + filePath);
            e.printStackTrace();
        }
    }

    public static void emptyFile(String filePath){
        createFileIfNotExists(filePath);
        try {
            // Get the Path object for the file
            Path path = Paths.get(filePath);
            // Set the file content to empty by overwriting it
            Files.write(path, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
            //System.out.println("File content set to empty successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static double getRandomNumberUsingNextDouble(double min, double max) {
        Random random = new Random();
        return random.nextDouble(max - min) + min;
    }

    public static Timestamp getRandomTimestampRange(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start=null, end = null;
        try {
            // Parse the input date strings
             start = sdf.parse(startDate);
             end = sdf.parse(endDate);
        }catch(Exception e){
            System.err.println(e);
        }
        // Convert to milliseconds
        long startMillis = start.getTime();
        long endMillis = end.getTime();

        // Generate a random time between start and end
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);

        // Return as Timestamp
        return new Timestamp(randomMillis);
    }

    public static <T> List<List<T>> listOfbatches(List<T> batchListType, int batchSize) {
        int totalsize = batchListType.size();
        int runTimes = totalsize/batchSize;
        int remainder = totalsize - (runTimes*batchSize);
        if(remainder > 0){
            runTimes+=1;
        }
        List<List<T>> batches = new ArrayList<>();

        for(int i =0 ; i < runTimes ; i++){
            if( i == (runTimes -1) && remainder > 0 ){
                batches.add(batchListType.subList(i*batchSize,i*batchSize+remainder));
            }else{
                batches.add(batchListType.subList(i*batchSize,(i+1)*batchSize));
            }
        }
        return batches;
    }
}
