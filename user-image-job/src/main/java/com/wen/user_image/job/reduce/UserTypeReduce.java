package com.wen.user_image.job.reduce;

import com.wen.user_image.common.entity.UserTypeInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * Created by li on 2019/1/6.
 */
public class UserTypeReduce implements ReduceFunction<UserTypeInfo> {

    @Override
    public UserTypeInfo reduce(UserTypeInfo useTypeInfo, UserTypeInfo t1) throws Exception {
        useTypeInfo.setCount(useTypeInfo.getCount()+t1.getCount());
        return useTypeInfo;
    }
}
