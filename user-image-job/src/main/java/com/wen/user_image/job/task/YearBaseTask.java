package com.wen.user_image.job.task;

import com.wen.user_image.common.entity.YearBase;
import com.wen.user_image.job.map.YearBaseMap;
import com.wen.user_image.job.reduce.YearBaseReduce;
import com.wen.user_image.job.utils.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

public class YearBaseTask {
    public static void main(String[] args) throws Exception{
        final ParameterTool parameterTool=ParameterTool.fromArgs(args);
        // set up the execution environment
        final ExecutionEnvironment executionEnvironment=ExecutionEnvironment.getExecutionEnvironment();
        // make parameters available in the web interface
        executionEnvironment.getConfig().setGlobalJobParameters(parameterTool);
        // get input data
        DataSet<String> text=executionEnvironment.readTextFile(parameterTool.get("input"));
        DataSet<YearBase> mapResult=text.map(new YearBaseMap());
        DataSet<YearBase> reduceResult=mapResult.groupBy("groupField").reduce(new YearBaseReduce());
        try {
            List<YearBase> resultList=reduceResult.collect();
            for(YearBase yearBase:resultList){
                String yearType = yearBase.getYearType();
                Long count = yearBase.getYearCount();
                Document doc = MongoUtils.queryForDoc("yearbase_statics","user_image",yearType);
                if(doc == null){
                    doc = new Document();
                    doc.put("info",yearType);
                    doc.put("count",count);
                }else{
                    Long countpre = doc.getLong("count");
                    Long total = countpre+count;
                    doc.put("count",total);
                }
                MongoUtils.saveOrUpdateMongo("yearbase_statics","user_image",doc);
            }
            executionEnvironment.execute("year base analy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
