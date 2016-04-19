//package com.medlinker.dentist.uac.provider;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.Maps;
//import com.mongodb.DB;
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by mazb on 2016/4/13.
// */
//public class TestMongoDb {
//
//    public static void main(String[] args) {
//        MongoClient mongoClient = getMongoDBClient();
//        for (String name : mongoClient.getDatabaseNames()) {
//            System.out.println("dbName: " + name);
//        }
//
//        DB db = mongoClient.getDB("dentist");
//
////        db.
//        System.out.println();
//
//        Map<Object, Object> map = new HashMap<Object, Object>() {{
//            put(1, 2);
//            put(2, 3);
//            put(4, 5);
//            put(6, 7);
//        }};
//
//        System.out.println(JSON.toJSON(map));
//    }
//
//    private static MongoClient getMongoDBClient() {
//        MongoClient mongoClient = new MongoClient("59.151.9.165", 27017);
//        return mongoClient;
//    }
//}
