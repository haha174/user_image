package com.wen.user_image.job.task;

import com.wen.user_image.common.entity.CarrierInfo;
import com.wen.user_image.common.entity.EmailInfo;
import com.wen.user_image.job.map.EmailMap;
import com.wen.user_image.job.reduce.EmailReduce;
import com.wen.user_image.job.utils.MongoUtils;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

public class EmailTask {
    public static void main(String[] args) throws Exception{
        final ParameterTool parameterTool=ParameterTool.fromArgs(args);
        // set up the execution environment
        final ExecutionEnvironment executionEnvironment=ExecutionEnvironment.getExecutionEnvironment();
        // make parameters available in the web interface
        executionEnvironment.getConfig().setGlobalJobParameters(parameterTool);
        // get input data
        DataSet<String> text=executionEnvironment.readTextFile(parameterTool.get("input"));
        DataSet<EmailInfo> mapResult=text.map(new EmailMap());
        DataSet<EmailInfo> reduceResult=mapResult.groupBy("groupField").reduce(new EmailReduce());
        try {
            List<EmailInfo> resultList=reduceResult.collect();
            for(EmailInfo emailInfo:resultList){
                String carrierName = emailInfo.getEmailCompanyName();
                Long count = emailInfo.getEmailCount();
                Document doc = MongoUtils.queryForDoc("carrier_statics","user_image",carrierName);
                if(doc == null){
                    doc = new Document();
                    doc.put("emailCompanyName",carrierName);
                    doc.put("count",count);
                }else{
                    Long countPre = doc.getLong("count");
                    Long total = countPre+count;
                    doc.put("count",total);
                }
                MongoUtils.saveOrUpdateMongo("email_statics","user_image",doc);
            }
            executionEnvironment.execute("email_analysis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
