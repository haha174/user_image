package com.wen.user_image.job.map;

import com.alibaba.fastjson.JSONObject;
import com.wen.tools.domain.utils.DataResponse;
import com.wen.user_image.common.entity.BrandLike;
import com.wen.user_image.common.entity.log.ScanProductLog;
import com.wen.user_image.job.kafka.KafkaEvent;
import com.wen.user_image.job.utils.HBaseUtils;
import com.wen.user_image.job.utils.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by li on 2019/1/6.
 */
public class BrandLikeMap implements FlatMapFunction<KafkaEvent, BrandLike> {

    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<BrandLike> collector) throws Exception {
            String data = kafkaEvent.getWord();
            ScanProductLog scanProductLog = JSONObject.parseObject(data, ScanProductLog.class);
            int userid = scanProductLog.getUserId();
            String brand = scanProductLog.getBrand();
            String tablename = "userflaginfo";
            String rowkey = userid+"";
            String famliyname = "userbehavior";
            String colum = "brandlist";//运营
            DataResponse<String> mapdata = HBaseUtils.getData(tablename,rowkey,famliyname,colum);
            Map<String,Long> map = new HashMap<String,Long>();
            if(StringUtils.isNotBlank(mapdata.getValue())){
                map = JSONObject.parseObject(mapdata.getValue(),Map.class);
            }
            //获取之前的品牌偏好
            String maxprebrand = MapUtils.getMaxFromMap(map);

            long prebarnd = map.get(brand)==null?0l:map.get(brand);
            map.put(brand,prebarnd+1);
            String finalstring = JSONObject.toJSONString(map);
            HBaseUtils.putData(tablename,rowkey,famliyname,colum,finalstring);

            String maxbrand = MapUtils.getMaxFromMap(map);
            if(StringUtils.isNotBlank(maxbrand)&&!maxprebrand.equals(maxbrand)){
                BrandLike brandLike = new BrandLike();
                brandLike.setBrand(maxprebrand);
                brandLike.setCount(-1l);
                brandLike.setGroupField("==brandLik=="+maxprebrand);
                collector.collect(brandLike);
            }

            BrandLike brandLike = new BrandLike();
            brandLike.setBrand(maxbrand);
            brandLike.setCount(1l);
            collector.collect(brandLike);
            brandLike.setGroupField("==brandLike=="+maxbrand);
            colum = "brandLike";
            HBaseUtils.putData(tablename,rowkey,famliyname,colum,maxbrand);

    }

}
