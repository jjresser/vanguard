package com.example.demo.dto;

import java.sql.Timestamp;

public class BatchLog {
    private String jsonContent;
    private Long threadId;
    private Long batchId;
    private String errMsg;
    private boolean success;
    private Timestamp timeStamp;

    public BatchLog(){}
    public BatchLog(Long batchId,Long threadId , String jsonContent, boolean success , String errMsg,Timestamp timeStamp){
        this.batchId = batchId;
        this.threadId = threadId;
        this.jsonContent = jsonContent;
        this.success = success;
        this.errMsg = errMsg;
        this.timeStamp = timeStamp;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

}
