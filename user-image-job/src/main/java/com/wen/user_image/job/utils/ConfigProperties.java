package com.wen.user_image.job.utils;


import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {

    public static Properties getProperties(String name) {
        Properties pro = new Properties();
        try {
            ClassLoader classLoader = ConfigProperties.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(name);
            pro.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
    }
}
