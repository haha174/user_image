package com.wen.user_image.job.reduce;

import com.wen.user_image.common.entity.YearBase;
import org.apache.flink.api.common.functions.ReduceFunction;

public class YearBaseReduce implements ReduceFunction<YearBase> {
    @Override
    public YearBase reduce(YearBase t1, YearBase t2) throws Exception {
        t1.setYearCount(t1.getYearCount()+t2.getYearCount());
        return t1;
    }
}
