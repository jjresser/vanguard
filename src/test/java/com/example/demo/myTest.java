package com.example.demo;

import org.junit.jupiter.api.Test;

public class myTest {
    @Test
    void t(){
        int totalsize = 2;
        int batchSize = 5000;
        int runTimes = totalsize/batchSize;
        if(totalsize - (runTimes*batchSize) > 0){
            runTimes+=1;
        }
        System.out.println(runTimes);
    }
}
