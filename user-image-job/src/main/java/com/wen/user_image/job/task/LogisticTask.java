package com.wen.user_image.job.task;

import com.wen.user_image.common.entity.LogisticInfo;
import com.wen.user_image.job.map.LogisticMap;
import com.wen.user_image.job.reduce.LogisticReduce;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.*;

/**
 * Created by li on 2019/1/6.
 */
public class LogisticTask {
    public static void main(String[] args) {
        final ParameterTool  params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));

        DataSet<LogisticInfo> mapresult = text.map(new LogisticMap());
        DataSet<ArrayList<Double>> reduceresutl = mapresult.groupBy("groupField").reduceGroup(new LogisticReduce());
        try {
            List<ArrayList<Double>> reusltlist = reduceresutl.collect();
            int groupsize  = reusltlist.size();
            Map<Integer,Double> summap = new TreeMap<Integer,Double>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            for(ArrayList<Double> array:reusltlist){

                for(int i=0;i<array.size();i++){
                    double pre = summap.get(i)==null?0d:summap.get(i);
                    summap.put(i,pre+array.get(i));
                }
            }
            ArrayList<Double> finalWeight = new ArrayList<Double>();
            Set<Map.Entry<Integer,Double>> set = summap.entrySet();
            for(Map.Entry<Integer,Double> mapentry :set){
                Integer key = mapentry.getKey();
                Double sumvalue = mapentry.getValue();
                double finalvalue = sumvalue/groupsize;
                finalWeight.add(finalvalue);
            }
            env.execute("LogisticTask analisys");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
