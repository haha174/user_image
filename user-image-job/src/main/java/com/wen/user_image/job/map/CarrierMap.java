package com.wen.user_image.job.map;

import com.wen.tools.domain.utils.CarrierUtils;
import com.wen.tools.domain.utils.DataResponse;
import com.wen.tools.log.utils.LogUtil;
import com.wen.user_image.common.entity.CarrierInfo;
import com.wen.user_image.job.utils.HBaseUtils;
import org.apache.flink.api.common.functions.MapFunction;

public class CarrierMap  implements MapFunction<String,CarrierInfo> {

    @Override
    public CarrierInfo map(String s) throws Exception {
        String[] userInfoArray=s.split(",");
        String userId=userInfoArray[0];
        String userName=userInfoArray[1];
        String userSex=userInfoArray[3];
        String userPhone=userInfoArray[4];
        String userEmail=userInfoArray[5];
        String userAge=userInfoArray[6];
        String userType=userInfoArray[7]; // 0 pc 1 移动端 2 小程序
        String carrierName= CarrierUtils.getCarrierNameByTel(userPhone);
        String tableName="user_info";
        String rowKey=userId;
        String familyName="info";
        String column="carrier_name";
        try{
            DataResponse dataResponse= HBaseUtils.putData(tableName,rowKey,familyName,column,carrierName);
            if(dataResponse.ifSuccess()){
                LogUtil.getCoreLog().info("tableName:{},rowKey:{},familyName:{},column:{},carrierName:{} put success"+tableName,rowKey,familyName,column,carrierName);
            }else{
                LogUtil.getCoreLog().error("tableName:{},rowKey:{},familyName:{},column:{},carrierName:{} put error"+tableName,rowKey,familyName,column,carrierName);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getCoreLog().error(e);
            LogUtil.getCoreLog().error("tableName:{},rowKey:{},familyName:{},column:{},carrierName:{} put error"+tableName,rowKey,familyName,column,carrierName);
            throw new Exception(e);
        }
        CarrierInfo carrierInfo=new CarrierInfo();
        carrierInfo.setCarrierName(carrierName);
        carrierInfo.setCarrierPhone(userPhone);
        carrierInfo.setCarrierCount(1L);
        carrierInfo.setGroupField("carrierName="+carrierName);
        return carrierInfo;
    }
}
