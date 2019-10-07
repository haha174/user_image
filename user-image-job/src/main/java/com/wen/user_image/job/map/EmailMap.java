package com.wen.user_image.job.map;

import com.wen.tools.domain.config.IConstantsDomain;
import com.wen.tools.domain.utils.CarrierUtils;
import com.wen.tools.domain.utils.DataResponse;
import com.wen.tools.domain.utils.EmailUtils;
import com.wen.tools.log.utils.LogUtil;
import com.wen.user_image.common.entity.EmailInfo;
import com.wen.user_image.job.utils.HBaseUtils;
import org.apache.flink.api.common.functions.MapFunction;

public class EmailMap implements MapFunction<String,EmailInfo> {

    @Override
    public EmailInfo map(String s) throws Exception {
        String[] userInfoArray=s.split(",");
        String userId=userInfoArray[0];
        String userName=userInfoArray[1];
        String userSex=userInfoArray[3];
        String userPhone=userInfoArray[4];
        String userEmail=userInfoArray[5];
        String userAge=userInfoArray[6];
        String userType=userInfoArray[7]; // 0 pc 1 移动端 2 小程序
        String emailCompanyName= EmailUtils.getEmailCompanyName(userEmail);
        String tableName="user_image";
        String rowKey=userId;
        String familyName="info";
        String column="email_company_name";
        try{
            DataResponse dataResponse= HBaseUtils.putData(tableName,rowKey,familyName,column,emailCompanyName);
            if(dataResponse.ifSuccess()){
                LogUtil.getCoreLog().info("tableName:{},rowKey:{},familyName:{},column:{},emailCompanyName:{} put success"+tableName,rowKey,familyName,column,emailCompanyName);
            }else{
                LogUtil.getCoreLog().error("tableName:{},rowKey:{},familyName:{},column:{},emailCompanyName:{} put error"+tableName,rowKey,familyName,column,emailCompanyName);
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getCoreLog().error(e);
            LogUtil.getCoreLog().error("tableName:{},rowKey:{},familyName:{},column:{},emailCompanyName:{} put error"+tableName,rowKey,familyName,column,emailCompanyName);
            throw new Exception(e);
        }
        EmailInfo emailInfo=new EmailInfo();
        emailInfo.setEmailCompanyName(emailCompanyName);
        emailInfo.setEmailCount(1L);
        emailInfo.setGroupField("emailCompanyName="+emailCompanyName);
        return emailInfo;
    }
}
