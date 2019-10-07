package com.wen.user_image.job.reduce;

import com.wen.user_image.common.entity.CarrierInfo;
import com.wen.user_image.common.entity.EmailInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

public class EmailReduce implements ReduceFunction<EmailInfo> {
    @Override
    public EmailInfo reduce(EmailInfo t1, EmailInfo t2) throws Exception {
        t1.setEmailCount(t1.getEmailCount()+t2.getEmailCount());
        return t1;
    }
}
