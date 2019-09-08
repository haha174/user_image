package com.wen.user_image.task.map;

import com.wen.tools.domain.utils.DataResponse;
import com.wen.tools.log.utils.LogUtil;
import com.wen.user_image.task.entity.YearBase;
import com.wen.user_image.task.utils.DateUtils;
import com.wen.user_image.task.utils.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;
import sun.rmi.runtime.Log;

public class YearBaseMap implements MapFunction<String, YearBase> {
    public YearBase map(String s) throws Exception {
        if(StringUtils.isBlank(s)){
            return null;
        }
        String[] userInfoArray=s.split(",");
        String userId=userInfoArray[0];
        String userName=userInfoArray[1];
        String userSex=userInfoArray[3];
        String userPhone=userInfoArray[4];
        String userEmail=userInfoArray[5];
        String userAge=userInfoArray[6];
        String userType=userInfoArray[7]; // 0 pc 1 移动端 2 小程序
        int bornYear=DateUtils.getBornYearByAge(userAge);
        String yearBaseType= DateUtils.getYearTypeByAge(bornYear);
        String tableName="user_info";
        String rowKey=userId;
        String familyName="info";
        String column="year_base";
        try{
           DataResponse dataResponse= HBaseUtils.putData(tableName,rowKey,familyName,column,yearBaseType);
           if(dataResponse.isSuccess()){
               LogUtil.getCoreLog().info("tableName:{},rowKey:{},familyName:{},column:{},yearBaseType:{} put success"+tableName,rowKey,familyName,column,yearBaseType);
           }else{
               LogUtil.getCoreLog().error("tableName:{},rowKey:{},familyName:{},column:{},yearBaseType:{} put error"+tableName,rowKey,familyName,column,yearBaseType);
           }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getCoreLog().error(e);
            LogUtil.getCoreLog().error("tableName:{},rowKey:{},familyName:{},column:{},yearBaseType:{} put error"+tableName,rowKey,familyName,column,yearBaseType);
            throw new Exception(e);
        }
        YearBase yearBase=new YearBase();
        yearBase.setBornYear(bornYear);
        yearBase.setYearType(yearBaseType);
        yearBase.setYearCount(11);
        yearBase.setGroupField("yearBase=="+yearBaseType);
        return yearBase;
    }
}
