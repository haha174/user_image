package com.wen.user_image.job.map;

import com.wen.user_image.common.entity.LogisticInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Random;

public class LogisticMap implements MapFunction<String,LogisticInfo> {

    @Override
    public LogisticInfo map(String s) throws Exception {
        if(StringUtils.isBlank(s)){
            return null;
        }
        Random random=new Random(10);
        String[] logisticArray=s.split(",");
        LogisticInfo logisticInfo=new LogisticInfo();
        logisticInfo.setVariable1(logisticArray[0]);
        logisticInfo.setVariable2(logisticArray[1]);
        logisticInfo.setVariable3(logisticArray[2]);
        logisticInfo.setLabel(logisticArray[3]);
        logisticInfo.setGroupField("logistic=="+random.nextInt());
        return logisticInfo;
    }
}
