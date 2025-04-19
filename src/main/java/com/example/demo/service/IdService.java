package com.example.demo.service;

import com.example.demo.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdService {

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @Autowired
    public IdService(SnowflakeIdGenerator snowflakeIdGenerator) {
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    public long generateUniqueId() {
        return snowflakeIdGenerator.nextId();
    }
}
