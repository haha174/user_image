package com.wen.user_image.job.sink;

import com.wen.user_image.common.entity.UserTypeInfo;
import com.wen.user_image.job.utils.MongoUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

/**
 * Created by li on 2019/1/6.
 */
public class UserTypeSink implements SinkFunction<UserTypeInfo> {
    @Override
    public void invoke(UserTypeInfo value, Context context) throws Exception {
        String usetype = value.getUserType();
        long count = value.getCount();
        Document doc = MongoUtils.queryForDoc("user_type_statics","user_image_portrait",usetype);
        if(doc == null){
            doc = new Document();
            doc.put("info",usetype);
            doc.put("count",count);
        }else{
            Long countpre = doc.getLong("count");
            Long total = countpre+count;
            doc.put("count",total);
        }
        MongoUtils.saveOrUpdateMongo("user_type_statics","user_image_portrait",doc);
    }
}
