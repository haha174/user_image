package com.wen.user_image.job.config;

public interface IConstantsTask {
    interface HBaseConf{
        String HBASE_ENV_PROPERTIES_FILE="HBaseConf.properties";
    }

    interface MongoDBConf{
        String MONGODB_ENV_PROPERTIES_FILE="MongoDBConf.properties";
        String MONGODB_HOST_NAME="mongodb.host";
        String MONGODB_PORT_NAME="mongodb.port";
    }
}
