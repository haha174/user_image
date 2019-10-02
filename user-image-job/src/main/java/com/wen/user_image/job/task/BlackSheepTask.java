package com.wen.user_image.job.task;

import com.wen.user_image.job.entity.BlackSheepEntity;
import com.wen.user_image.job.entity.CarrierInfo;
import com.wen.user_image.job.map.BlackSheepMap;
import com.wen.user_image.job.map.CarrierMap;
import com.wen.user_image.job.reduce.BlackSheepReduce;
import com.wen.user_image.job.reduce.CarrierReduce;
import com.wen.user_image.job.utils.DateUtils;
import com.wen.user_image.job.utils.HBaseUtils;
import com.wen.user_image.job.utils.MongoUtils;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Collector;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述用户败家指数的task
 */
public class BlackSheepTask {
    public static void main(String[] args) throws Exception {
        final ParameterTool parameterTool = ParameterTool.fromArgs(args);
        // set up the execution environment
        final ExecutionEnvironment executionEnvironment = ExecutionEnvironment.getExecutionEnvironment();
        // make parameters available in the web interface
        executionEnvironment.getConfig().setGlobalJobParameters(parameterTool);
        // get input data
        DataSet<String> text = executionEnvironment.readTextFile(parameterTool.get("input"));
        DataSet<BlackSheepEntity> mapResult = text.map(new BlackSheepMap());
        DataSet<BlackSheepEntity> reduceResult = mapResult.groupBy("groupField").reduce(new BlackSheepReduce());
        try {
            List<BlackSheepEntity> listResult = reduceResult.collect();
            for (BlackSheepEntity blackSheepEntity : listResult) {
                String userId = blackSheepEntity.getUserId();
                List<BlackSheepEntity> blackSheepEntityList = blackSheepEntity.getBlackSheepList();
                Collections.sort(blackSheepEntityList, new Comparator<BlackSheepEntity>() {
                    @Override
                    public int compare(BlackSheepEntity o1, BlackSheepEntity o2) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hhmmss");
                        try {
                            Date time1 = dateFormat.parse(o1.getCreateTime());
                            Date time2 = dateFormat.parse(o2.getCreateTime());
                            return time1.compareTo(time2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });

                BlackSheepEntity before = null;
                Map<Integer,Integer> frequencymap = new HashMap<Integer,Integer>();
                double maxamount = 0d;
                double sum = 0d;
                for(BlackSheepEntity baiJiaInfoinner:blackSheepEntityList){
                    if(before==null){
                        before = baiJiaInfoinner;
                        continue;
                    }
                    //计算购买的频率
                    String beforetime = before.getCreateTime();
                    String endstime = baiJiaInfoinner.getCreateTime();
                    int days = DateUtils.getDaysBetweenbyStartAndend(beforetime,endstime,"yyyyMMdd hhmmss");
                    int brefore = frequencymap.get(days)==null?0:frequencymap.get(days);
                    frequencymap.put(days,brefore+1);

                    //计算最大金额
                    String totalamountstring = baiJiaInfoinner.getTotalAmt();
                    Double totalamout = Double.valueOf(totalamountstring);
                    if(totalamout>maxamount){
                        maxamount = totalamout;
                    }

                    //计算平均值
                    sum += totalamout;

                    before = baiJiaInfoinner;
                }
                double avramount = sum/blackSheepEntityList.size();
                int totaldays = 0;
                Set<Map.Entry<Integer,Integer>> set = frequencymap.entrySet();
                for(Map.Entry<Integer,Integer> entry :set){
                    Integer frequencydays = entry.getKey();
                    Integer count = entry.getValue();
                    totaldays += frequencydays*count;
                }
                int avrdays = totaldays/blackSheepEntityList.size();//平均天数

                //败家指数 = 支付金额平均值*0.3、最大支付金额*0.3、下单频率*0.4
                //支付金额平均值30分（0-20 5 20-60 10 60-100 20 100-150 30 150-200 40 200-250 60 250-350 70 350-450 80 450-600 90 600以上 100  ）
                // 最大支付金额30分（0-20 5 20-60 10 60-200 30 200-500 60 500-700 80 700 100）
                // 下单平率30分 （0-5 100 5-10 90 10-30 70 30-60 60 60-80 40 80-100 20 100以上的 10）
                int avraoumtsoce = 0;
                if(avramount>=0 && avramount < 20){
                    avraoumtsoce = 5;
                }else if (avramount>=20 && avramount < 60){
                    avraoumtsoce = 10;
                }else if (avramount>=60 && avramount < 100){
                    avraoumtsoce = 20;
                }else if (avramount>=100 && avramount < 150){
                    avraoumtsoce = 30;
                }else if (avramount>=150 && avramount < 200){
                    avraoumtsoce = 40;
                }else if (avramount>=200 && avramount < 250){
                    avraoumtsoce = 60;
                }else if (avramount>=250 && avramount < 350){
                    avraoumtsoce = 70;
                }else if (avramount>=350 && avramount < 450){
                    avraoumtsoce = 80;
                }else if (avramount>=450 && avramount < 600){
                    avraoumtsoce = 90;
                }else if (avramount>=600){
                    avraoumtsoce = 100;
                }

                int maxaoumtscore = 0;
                if(maxamount>=0 && maxamount < 20){
                    maxaoumtscore = 5;
                }else if (maxamount>=20 && maxamount < 60){
                    maxaoumtscore = 10;
                }else if (maxamount>=60 && maxamount < 200){
                    maxaoumtscore = 30;
                }else if (maxamount>=200 &&maxamount < 500){
                    maxaoumtscore = 60;
                }else if (maxamount>=500 && maxamount < 700){
                    maxaoumtscore = 80;
                }else if (maxamount>=700){
                    maxaoumtscore = 100;
                }

                // 下单平率30分 （0-5 100 5-10 90 10-30 70 30-60 60 60-80 40 80-100 20 100以上的 10）
                int avrdaysscore = 0;
                if(avrdays>=0 && avrdays < 5){
                    avrdaysscore = 100;
                }else if (avramount>=5 && avramount < 10){
                    avrdaysscore = 90;
                }else if (avramount>=10 && avramount < 30){
                    avrdaysscore = 70;
                }else if (avramount>=30 && avramount < 60){
                    avrdaysscore = 60;
                }else if (avramount>=60 && avramount < 80){
                    avrdaysscore = 40;
                }else if (avramount>=80 && avramount < 100){
                    avrdaysscore = 20;
                }else if (avramount>=100){
                    avrdaysscore = 10;
                }
                double totalscore = (avraoumtsoce/100)*30+(maxaoumtscore/100)*30+(avrdaysscore/100)*40;

                String tablename = "user_image";
                String rowkey = userId;
                String famliyname = "info";
                String colum = "black_sheep_score";
                HBaseUtils.putData(tablename,rowkey,famliyname,colum,totalscore+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
