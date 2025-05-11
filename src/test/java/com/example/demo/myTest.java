package com.example.demo;

import com.example.demo.Utils.JsonKit;
import com.example.demo.dto.GameSale;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

    @Test
    void tt(){

        GameSale gs1 = new GameSale();
        GameSale gs2 = new GameSale();
        gs1.setId(1);
        gs2.setId(2);
        List<GameSale> lsGs = Arrays.asList(gs1,gs2);
        System.out.println(JsonKit.toJSONString(lsGs));
    }

}
