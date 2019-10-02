package com.wen.user_image.job.reduce;

import com.wen.user_image.job.entity.BlackSheepEntity;
import org.apache.flink.api.common.functions.ReduceFunction;

public class BlackSheepReduce  implements ReduceFunction<BlackSheepEntity> {


    @Override
    public BlackSheepEntity reduce(BlackSheepEntity blackSheepEntity, BlackSheepEntity t1) throws Exception {
        blackSheepEntity.getBlackSheepList().addAll(t1.getBlackSheepList());
        return blackSheepEntity;
    }
}