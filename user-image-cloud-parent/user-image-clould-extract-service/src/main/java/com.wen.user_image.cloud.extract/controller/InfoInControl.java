package com.wen.user_image.cloud.extract.controller;

import com.alibaba.fastjson.JSONObject;
import com.wen.tools.domain.utils.BaseResponse;
import com.wen.tools.domain.utils.DataResponse;
import com.wen.user_image.common.entity.log.AttentionProductLog;
import com.wen.user_image.common.entity.log.BuyCartProductLog;
import com.wen.user_image.common.entity.log.CollectProductLog;
import com.wen.user_image.common.entity.log.ScanProductLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by li on 2019/1/6.
 */
@RestController
@RequestMapping("/infoLog")
public class InfoInControl {
    @Value(value="attentionProductLog")
    private  String attentionProductLogTopic;
    @Value(value="attentionProductLog")
    private  String buyCartProductLogTopic ;
    @Value(value="attentionProductLog")
    private  String collectProductLogTopic ;
    @Value(value="attentionProductLog")
    private  String scanProductLogTopic  ;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/helloWorld",method = RequestMethod.GET)
    public BaseResponse helloWorld(HttpServletRequest req){
        String ip =req.getRemoteAddr();
        DataResponse<String> baseResponse=new DataResponse<>();
        baseResponse.setValue("ip="+ip);
        return baseResponse;
    }

    /**
     * AttentionProductLog:{productid:productid....}
     BuyCartProductLog:{productid:productid....}
     CollectProductLog:{productid:productid....}
     ScanProductLog:{productid:productid....}
     * @param receviceLog
     * @param req
     * @return
     */
    @RequestMapping(value = "/receiveLog",method = RequestMethod.POST)
    public DataResponse hellowolrd(String receviceLog,HttpServletRequest req){
        if(StringUtils.isBlank(receviceLog)){
            return null;
        }
        String[] rearrays = receviceLog.split(":",2);
        String classname = rearrays[0];
        String data = rearrays[1];
        String resulmesage= "";

        if("AttentionProductLog".equals(classname)){
            AttentionProductLog attentionProductLog = JSONObject.parseObject(data,AttentionProductLog.class);
            resulmesage = JSONObject.toJSONString(attentionProductLog);
            kafkaTemplate.send(attentionProductLogTopic,resulmesage+"##1##"+new Date().getTime());
        }else if("BuyCartProductLog".equals(classname)){
            BuyCartProductLog buyCartProductLog = JSONObject.parseObject(data, BuyCartProductLog.class);
            resulmesage = JSONObject.toJSONString(buyCartProductLog);
            kafkaTemplate.send(buyCartProductLogTopic,resulmesage+"##1##"+new Date().getTime());
        }else if("CollectProductLog".equals(classname)){
            CollectProductLog collectProductLog = JSONObject.parseObject(data, CollectProductLog.class);
            resulmesage = JSONObject.toJSONString(collectProductLog);
            kafkaTemplate.send(collectProductLogTopic,resulmesage+"##1##"+new Date().getTime());
        }else if("ScanProductLog".equals(classname)){
            ScanProductLog scanProductLog = JSONObject.parseObject(data,ScanProductLog.class);
            resulmesage = JSONObject.toJSONString(scanProductLog);
            kafkaTemplate.send(scanProductLogTopic,resulmesage+"##1##"+new Date().getTime());
        }
        DataResponse<String> dataResponse=new DataResponse<>();
        dataResponse.setValue(resulmesage);
        return dataResponse;
    }
}
