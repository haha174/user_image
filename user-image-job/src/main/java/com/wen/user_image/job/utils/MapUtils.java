package com.wen.user_image.job.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by li on 2019/1/6.
 */
public class MapUtils {

    public static String getMaxFromMap(Map<String,Long> dataMap){
        if(dataMap.isEmpty()){
                return  null;
        }
        TreeMap<Long,String> map = new TreeMap<Long, String>(new Comparator<Long>() {
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });
        Set<Map.Entry<String,Long>> set = dataMap.entrySet();
        for(Map.Entry<String,Long> entry :set){
            String key = entry.getKey();
            Long value = entry.getValue();
            map.put(value,key);
        }
        return map.get(map.firstKey());
    }
}
