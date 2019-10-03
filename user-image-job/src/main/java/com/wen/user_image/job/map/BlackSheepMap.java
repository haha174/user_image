package com.wen.user_image.job.map;

import com.wen.user_image.common.entity.BlackSheepEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户败家指数的map
 */
public class BlackSheepMap   implements MapFunction<String, BlackSheepEntity> {

    @Override
    public BlackSheepEntity map(String s) throws Exception {
        if ( StringUtils.isBlank(s) ){
            throw new RuntimeException("string is null");
        }

        String[] blackSheepArray=s.split(",");
        BlackSheepEntity blackSheepEntity=new BlackSheepEntity();


        /**
         * 用户Id
         */
        blackSheepEntity.setUserId(blackSheepArray[0]);
        /**
         * 支付金额
         */
        blackSheepEntity.setCreateTime(blackSheepArray[9]);

        blackSheepEntity.setAmt(blackSheepArray[1]) ;
        /**
         * 支付方式
         */
        blackSheepEntity.setPayType(blackSheepArray[2]) ;
        /**
         * 支付时间
         */
        blackSheepEntity.setPayTime(blackSheepArray[4]);
        /**
         * 支付状态
         */
        blackSheepEntity.setPayStatus(blackSheepArray[5]);//0、未支付 1、已支付 2、已退款
        blackSheepEntity.setCouponAmt(blackSheepArray[6]);
        /**
         *
         */
        blackSheepEntity.setTotalAmt(blackSheepArray[7]);
        /**
         *
         */
        blackSheepEntity.setRefundAmt(blackSheepArray[8]);
        /**
         * 创建时间
         */
        String groupField="blackSheep="+blackSheepEntity.getUserId();
        List<BlackSheepEntity> blackSheeplist=new ArrayList<>();
        blackSheeplist.add(blackSheepEntity);
        blackSheepEntity.setBlackSheepList(blackSheeplist);
        blackSheepEntity.setGroupField(groupField);
        return blackSheepEntity;
    }
}
