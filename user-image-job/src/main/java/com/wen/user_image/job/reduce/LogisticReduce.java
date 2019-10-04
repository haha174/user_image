package com.wen.user_image.job.reduce;

import com.wen.user_image.common.entity.LogisticInfo;
import com.wen.user_image.job.logstic.CreateDataSet;
import com.wen.user_image.job.logstic.Logistic;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by li on 2019/1/6.
 */
public class LogisticReduce implements GroupReduceFunction<LogisticInfo,ArrayList<Double>> {
    @Override
    public void reduce(Iterable<LogisticInfo> iterable, Collector<ArrayList<Double>> collector) throws Exception {
        Iterator<LogisticInfo> iterator = iterable.iterator();
        CreateDataSet trainingSet = new CreateDataSet();
        while(iterator.hasNext()){
            LogisticInfo logicInfo = iterator.next();
            String variable1 = logicInfo.getVariable1();
            String variable2 = logicInfo.getVariable2();
            String variable3 = logicInfo.getVariable3();
            String label = logicInfo.getLabel();


            ArrayList<String> as = new ArrayList<String>();
            as.add(variable1);
            as.add(variable2);
            as.add(variable3);

            trainingSet.data.add(as);
            trainingSet.labels.add(label);
        }
        ArrayList<Double> weights;
        weights = Logistic.gradAscent1(trainingSet, trainingSet.labels, 500);
        collector.collect(weights);
    }
}
