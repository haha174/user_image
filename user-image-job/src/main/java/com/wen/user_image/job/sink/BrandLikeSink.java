package com.wen.user_image.job.sink;

import com.wen.user_image.common.entity.BrandLike;
import com.wen.user_image.job.utils.MongoUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.bson.Document;

public class BrandLikeSink implements SinkFunction<BrandLike> {
    @Override
    public void invoke(BrandLike value, Context context) throws Exception {
        String brand = value.getBrand();
        long count = value.getCount();
        Document doc = MongoUtils.queryForDoc("brand_like_statics","user_image_portrait",brand);
        if(doc == null){
            doc = new Document();
            doc.put("info",brand);
            doc.put("count",count);
        }else{
            Long countpre = doc.getLong("count");
            Long total = countpre+count;
            doc.put("count",total);
        }
        MongoUtils.saveOrUpdateMongo("brand_like_statics","user_image_portrait",doc);
    }
}
