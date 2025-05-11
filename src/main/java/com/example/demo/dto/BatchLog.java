package com.example.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BatchLog {
    private String jsonContent;
    private Long threadId;
    private Long batchId;
    private String errMsg;
    private boolean success;
    private Timestamp timeStamp;

    public BatchLog(Long batchId,Long threadId , String jsonContent, boolean success , String errMsg,Timestamp timeStamp){
        this.batchId = batchId;
        this.threadId = threadId;
        this.jsonContent = jsonContent;
        this.success = success;
        this.errMsg = errMsg;
        this.timeStamp = timeStamp;
    }

}
