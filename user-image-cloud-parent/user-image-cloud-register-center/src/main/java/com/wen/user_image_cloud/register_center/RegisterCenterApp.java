package com.wen.user_image_cloud.register_center;

import com.wen.tools.log.utils.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterCenterApp {

    public static void main(String[] args) {
        SpringApplication.run( RegisterCenterApp.class, args );
        LogUtil.getCoreLog().info("start eureka successfully");
    }
}
