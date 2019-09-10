package com.wen.user_image.job.utils;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.wen.tools.domain.utils.ParameterUtils;
import com.wen.tools.log.utils.LogUtil;
import com.wen.user_image.job.config.IConstantsTask;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;


public class MongoUtils {

    private static  MongoClient mongoClient;
    static {
        try {
            ParameterUtils parameterUtils=  ParameterUtils.fromPropertiesFile(IConstantsTask.MongoDBConf.MONGODB_ENV_PROPERTIES_FILE);
            mongoClient=new MongoClient(parameterUtils.get(IConstantsTask.MongoDBConf.MONGODB_HOST_NAME),parameterUtils.getInt(IConstantsTask.MongoDBConf.MONGODB_PORT_NAME));
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.getCoreLog().error(e);
            throw new RuntimeException(e);
        }
    }

    public static Document queryForDoc(String tableName, String database,String info){
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection mongoCollection = mongoDatabase.getCollection(tableName);
        Document  doc = new Document();
        doc.put("info", info);
        FindIterable<Document> ite = mongoCollection.find(doc);
        MongoCursor<Document> mongocursor = ite.iterator();
        if(mongocursor.hasNext()){
            return mongocursor.next();
        }else{
            return null;
        }
    }


    public static void saveOrUpdateMongo(String tableName,String database,Document doc) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection<Document> mongocollection = mongoDatabase.getCollection(tableName);
        if(!doc.containsKey("_id")){
            ObjectId objectid = new ObjectId();
            doc.put("_id", objectid);
            mongocollection.insertOne(doc);
            return;
        }
        Document matchDocument = new Document();
        String objectid = doc.get("_id").toString();
        matchDocument.put("_id", new ObjectId(objectid));
        FindIterable<Document> findIterable =  mongocollection.find(matchDocument);
        if(findIterable.iterator().hasNext()){
            mongocollection.updateOne(matchDocument, new Document("$set",doc));
            try {
                System.out.println("come into saveOrUpdateMongo ---- update---"+ JSONObject.toJSONString(doc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            mongocollection.insertOne(doc);
            try {
                System.out.println("come into saveOrUpdateMongo ---- insert---"+JSONObject.toJSONString(doc));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
