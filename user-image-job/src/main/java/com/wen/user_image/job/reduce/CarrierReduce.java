package com.wen.user_image.job.reduce;

import com.wen.user_image.job.entity.CarrierInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

public class CarrierReduce implements ReduceFunction<CarrierInfo> {
    @Override
    public CarrierInfo reduce(CarrierInfo t1, CarrierInfo t2) throws Exception {
        t1.setCarrierCount(t1.getCarrierCount()+t2.getCarrierCount());
        return t1;
    }
}
