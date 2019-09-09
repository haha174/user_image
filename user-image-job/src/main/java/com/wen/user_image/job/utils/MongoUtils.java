package com.wen.user_image.job.utils;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;


public class MongoUtils {

    private static MongoClient mongoClient = new MongoClient("192.168.80.134",27017);



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
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            mongocollection.insertOne(doc);
            try {
                System.out.println("come into saveOrUpdateMongo ---- insert---"+JSONObject.toJSONString(doc));
            }catch (Exception e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
