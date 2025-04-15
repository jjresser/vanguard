package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class sqlUpdate {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updatesql(){

    }
}
