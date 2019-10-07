package com.wen.user_image.job.map;

import com.alibaba.fastjson.JSONObject;
import com.wen.tools.domain.utils.DataResponse;
import com.wen.user_image.common.entity.UserTypeInfo;
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
public class UserTypeMap implements FlatMapFunction<KafkaEvent, UserTypeInfo> {

    @Override
    public void flatMap(KafkaEvent kafkaEvent, Collector<UserTypeInfo> collector) throws Exception {
            String data = kafkaEvent.getWord();
            ScanProductLog scanProductLog = JSONObject.parseObject(data, ScanProductLog.class);
            int userId = scanProductLog.getUserId();
            int usetype = scanProductLog.getUserType();////终端类型：0、pc端；1、移动端；2、小程序端
            String usetypename = usetype == 0?"pc端":usetype == 1?"移动端":"小程序端";
            String tablename = "user_image";
            String rowkey = userId+"";
            String familyName="info";
            String colum = "user_type_list";//运营
            DataResponse<String> mapData = HBaseUtils.getData(tablename,rowkey,familyName,colum);
            Map<String,Long> map = new HashMap<String,Long>();
            if(StringUtils.isNotBlank(mapData.getValue())){
                map = JSONObject.parseObject(mapData.getValue(),Map.class);
            }
            //获取之前的终端偏好
            String maxUserType = MapUtils.getMaxFromMap(map);

            long preUserType = map.get(usetypename)==null?0l:map.get(usetypename);
            map.put(usetypename,preUserType+1);
            String finalstring = JSONObject.toJSONString(map);
            HBaseUtils.putData(tablename,rowkey,familyName,colum,finalstring);

            String maxusetype = MapUtils.getMaxFromMap(map);
            if(StringUtils.isNotBlank(maxusetype)&&!maxUserType.equals(maxusetype)){
                UserTypeInfo useTypeInfo = new UserTypeInfo();
                useTypeInfo.setUserType(maxUserType);
                useTypeInfo.setCount(-1l);
                useTypeInfo.setGroupField("==usetypeinfo=="+maxUserType);
                collector.collect(useTypeInfo);
            }

            UserTypeInfo useTypeInfo = new UserTypeInfo();
            useTypeInfo.setUserType(maxUserType);
            useTypeInfo.setCount(1l);
            useTypeInfo.setGroupField("==user_type_info=="+maxUserType);
            collector.collect(useTypeInfo);
            colum = "user_type";
            HBaseUtils.putData(tablename,rowkey,familyName,colum,maxUserType);

    }

}
