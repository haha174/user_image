package com.wen.user_image.job.map;

import com.wen.tools.domain.utils.DataResponse;
import com.wen.tools.log.utils.LogUtil;
import com.wen.user_image.common.entity.YearBase;
import com.wen.user_image.job.utils.DateUtils;
import com.wen.user_image.job.utils.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

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
        String tableName="user_image";
        String rowKey=userId;
        String familyName="info";
        String column="year_base";
        try{
           DataResponse dataResponse= HBaseUtils.putData(tableName,rowKey,familyName,column,yearBaseType);
           if(dataResponse.ifSuccess()){
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
        yearBase.setYearCount(1L);
        yearBase.setGroupField("yearBase=="+yearBaseType);
        return yearBase;
    }
}
