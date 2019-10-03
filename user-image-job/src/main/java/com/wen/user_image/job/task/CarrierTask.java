package com.wen.user_image.job.task;

import com.wen.user_image.common.entity.CarrierInfo;
import com.wen.user_image.common.entity.YearBase;
import com.wen.user_image.job.map.CarrierMap;
import com.wen.user_image.job.reduce.CarrierReduce;
import com.wen.user_image.job.utils.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

public class CarrierTask {
    public static void main(String[] args) throws Exception{
        final ParameterTool parameterTool=ParameterTool.fromArgs(args);
        // set up the execution environment
        final ExecutionEnvironment executionEnvironment=ExecutionEnvironment.getExecutionEnvironment();
        // make parameters available in the web interface
        executionEnvironment.getConfig().setGlobalJobParameters(parameterTool);
        // get input data
        DataSet<String> text=executionEnvironment.readTextFile(parameterTool.get("input"));
        DataSet<CarrierInfo> mapResult=text.map(new CarrierMap());
        DataSet<CarrierInfo> reduceResult=mapResult.groupBy("groupField").reduce(new CarrierReduce());
        try {
            List<CarrierInfo> resultList=reduceResult.collect();
            for(CarrierInfo carrierInfo:resultList){
                String carrierName = carrierInfo.getCarrierName();
                Long count = carrierInfo.getCarrierCount();
                Document doc = MongoUtils.queryForDoc("carrier_statics","user_image",carrierName);
                if(doc == null){
                    doc = new Document();
                    doc.put("carrierName",carrierName);
                    doc.put("count",count);
                }else{
                    Long countPre = doc.getLong("count");
                    Long total = countPre+count;
                    doc.put("count",total);
                }
                MongoUtils.saveOrUpdateMongo("carrier_statics","user_image",doc);
            }
            executionEnvironment.execute("carrier_analysis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
