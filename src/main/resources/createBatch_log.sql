CREATE TABLE batch_log (
    batchId BIGINT,
    threadId BIGINT,
    jsonContent LONGTEXT,
    success BOOLEAN,
    errorMsg TEXT,
    timeStamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
