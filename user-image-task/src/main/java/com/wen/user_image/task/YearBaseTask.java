package com.wen.user_image.task;

import com.wen.user_image.task.entity.YearBase;
import com.wen.user_image.task.map.YearBaseMap;
import com.wen.user_image.task.reduce.YearBaseReduce;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.executiongraph.Execution;

import javax.xml.crypto.Data;
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
        DataSet mapResult=text.map(new YearBaseMap());
        DataSet reduceResult=mapResult.groupBy("groupField").reduce(new YearBaseReduce());
        List<YearBase> resultList=reduceResult.collect();

    }
}
