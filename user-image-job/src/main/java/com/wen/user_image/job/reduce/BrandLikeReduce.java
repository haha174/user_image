package com.wen.user_image.job.reduce;

import com.wen.user_image.common.entity.BrandLike;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * Created by li on 2019/1/6.
 */
public class BrandLikeReduce implements ReduceFunction<BrandLike> {
    @Override
    public BrandLike reduce(BrandLike brandLike, BrandLike t1) throws Exception {
        brandLike.setCount(brandLike.getCount()+t1.getCount());
        return brandLike;
    }
}
