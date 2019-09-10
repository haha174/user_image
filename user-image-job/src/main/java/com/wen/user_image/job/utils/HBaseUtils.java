package com.wen.user_image.job.utils;

import com.wen.tools.domain.utils.DataResponse;
import com.wen.tools.domain.utils.ParameterUtils;
import com.wen.tools.log.utils.LogUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;

import com.wen.user_image.job.config.IConstantsTask;

public class HBaseUtils {
    private static Admin admin = null;
    private static Connection conn = null;

    static {
        try {
            LogUtil.getCoreLog().info("init hbase config");
            Configuration conf = HBaseConfiguration.create();
            Map<String, String> confMap = ParameterUtils.fromPropertiesFile(IConstantsTask.HBaseConf.HBASE_ENV_PROPERTIES_FILE).toMap();
            Iterator<Map.Entry<String, String>> ite = confMap.entrySet().iterator();
            while (ite.hasNext()) {
                Map.Entry<String, String> entry = ite.next();
                LogUtil.getCoreLog().info("confKey:{},confValue:{}", entry.getKey(), entry.getValue());
                conf.set(entry.getKey(), entry.getValue());
            }
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.getCoreLog().error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入数据，create "userflaginfo,"baseinfo"
     * create "tfidfdata,"baseinfo"
     */
    public static DataResponse put(String tableName, String rowKey, String familyName, Map<String, String> dataMap) throws IOException {
        DataResponse dataResponse = new DataResponse();
        try {
            Table table = conn.getTable(TableName.valueOf(tableName));
            // 将字符串转换成byte[]
            byte[] rowKeyBytes = Bytes.toBytes(rowKey);
            Put put = new Put(rowKeyBytes);
            if (dataMap != null) {
                Set<Map.Entry<String, String>> set = dataMap.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    put.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(key), Bytes.toBytes(value + ""));
                }
            }
            table.put(put);
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.getCoreLog().error("put tb:" + tableName + ",rowKey:" + rowKey + "error");
            LogUtil.getCoreLog().error(e);
            throw new IOException(e);
        }
        dataResponse.setStatusMsg("put tb:" + tableName + ",rowKey:" + rowKey + "success");
        LogUtil.getCoreLog().info("put tb:" + tableName + ",rowKey:" + rowKey + "success");
        return dataResponse;
    }

    /**
     * @param tableName
     * @param rowKey
     * @param familyName
     * @param column
     * @return
     * @throws Exception
     */
    public static DataResponse getData(String tableName, String rowKey, String familyName, String column) throws IOException {
        DataResponse dataResponse = new DataResponse();
        try {
            Table table = conn.getTable(TableName.valueOf(tableName));
            // 将字符串转换成byte[]
            byte[] rowKeyBytes = Bytes.toBytes(rowKey);
            Get get = new Get(rowKeyBytes);
            Result result = table.get(get);
            byte[] resultBytes = result.getValue(familyName.getBytes(), column.getBytes());
            if (resultBytes == null) {
                return dataResponse;
            }
            dataResponse.setValue(new String(resultBytes));
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.getCoreLog().error("get tb:" + tableName + ",rowKey:" + rowKey + "error");
            LogUtil.getCoreLog().error(e);
            throw new IOException(e);
        }
        dataResponse.setStatusMsg("get tb:" + tableName + ",rowKey:" + rowKey + "success");
        LogUtil.getCoreLog().info("get tb:" + tableName + ",rowKey:" + rowKey + "success");
        return dataResponse;
    }

    /**
     * @param tableName
     * @param rowKey
     * @param familyName
     * @param column
     * @param data
     * @return
     * @throws IOException
     */
    public static DataResponse putData(String tableName, String rowKey, String familyName, String column, String data) throws IOException {
        DataResponse dataResponse = new DataResponse();
        try {
            Table table = conn.getTable(TableName.valueOf(tableName));
            Put put = new Put(rowKey.getBytes());
            put.addColumn(familyName.getBytes(), column.getBytes(), data.getBytes());
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.getCoreLog().error("put data tb:" + tableName + ",rowKey:" + rowKey + "error");
            LogUtil.getCoreLog().error(e);
            throw new IOException(e);
        }
        dataResponse.setStatusMsg("put data tb:" + tableName + ",rowKey:" + rowKey + "success");
        LogUtil.getCoreLog().info("put data tb:" + tableName + ",rowKey:" + rowKey + "success");
        return dataResponse;
    }

    public DataResponse dropTable(String tableName) throws IOException {
        DataResponse dataResponse = new DataResponse();
        try {
            TableName tableNameHBase = TableName.valueOf(tableName);
            if (admin.tableExists(tableNameHBase)) {
                admin.disableTable(tableNameHBase);
                admin.deleteTable(tableNameHBase);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.getCoreLog().error("drop table:" + tableName + " error");
            LogUtil.getCoreLog().error(e);
            throw new IOException(e);
        }
        dataResponse.setStatusMsg("drop table:" + tableName + " success");
        LogUtil.getCoreLog().info("drop table:" + tableName + " success");
        return dataResponse;
    }

    /**
     * 待测试 目前还不清楚这个里面传入的值newBuilder
     *
     * @param tableName
     * @param familyName
     * @return
     * @throws IOException
     */
    public DataResponse createTable(String tableName, String familyName) throws IOException {
        DataResponse dataResponse = new DataResponse();
        try {
            TableName tableNameHBase = TableName.valueOf(tableName);
            if (!admin.tableExists(tableNameHBase)) {
                TableDescriptorBuilder tdb = TableDescriptorBuilder.newBuilder(tableNameHBase);
                ColumnFamilyDescriptorBuilder cdb = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(familyName));
                ColumnFamilyDescriptor cfd = cdb.build();
                tdb.setColumnFamily(cfd);
                TableDescriptor td = tdb.build();
                admin.createTable(td);
            } else {
                LogUtil.getCoreLog().info("table:" + tableName + " Exists do not need create");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.getCoreLog().error("create table:" + tableName + " error");
            LogUtil.getCoreLog().error(e);
            throw new IOException(e);
        }
        dataResponse.setStatusMsg("create table:" + tableName + " success");
        LogUtil.getCoreLog().info("create table:" + tableName + " success");
        return dataResponse;
    }
}
