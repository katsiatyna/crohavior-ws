package edu.upc.bip.batch;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import com.mongodb.spark.MongoSpark;
import org.bson.Document;
import static java.util.Arrays.asList;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.spark.MongoConnector;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.config.WriteConfig;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import com.mongodb.spark.sql.helpers.StructFields;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
public class MongoUtils {

    public static void main(String[] agrs) {

        SparkConf sc = new SparkConf()
                .setMaster("local")
                .setAppName("MongoSparkConnectorTour")
                .set("spark.mongodb.input.uri", "mongodb://katya:echo216@ds135798.mlab.com:35798/heroku_41659s43.heatmap")
                .set("spark.mongodb.output.uri", "mongodb://katya:echo216@ds135798.mlab.com:35798/heroku_41659s43.heatmap");

        JavaSparkContext jsc = new JavaSparkContext(sc); // Create a Java Spark Context

//        JavaRDD<Document> documents = jsc.parallelize(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).map
//                (new Function<Integer, Document>() {
//                    public Document call(final Integer i) throws Exception {
//                        return Document.parse("{_id: " + i+10 + ", data: " + "[{\"a\":39.981,\"c\":2,\"o\":116.343},{\"a\":39.964,\"c\":2,\"o\":116.396}]" + "}");
//                    }
//                });
//
//        MongoSpark.save(documents);


        JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
        System.out.println(rdd.count());
        System.out.println(rdd.first().toJson());

        JavaMongoRDD<Document> aggregatedRdd = rdd.withPipeline(singletonList(Document.parse("{ $match: { _id : { $gte : \"10s2008-10-23T02:53:25\" , $lte: \"10s2008-10-23T02:54:05\"} } }")));
        System.out.println(aggregatedRdd.count());
//        System.out.println(aggregatedRdd.first().toJson());

        List<Document> output = aggregatedRdd.collect();
        for (Document tuple : output) {
            System.out.println(tuple.toJson());

        }
    }

    public static List<String> getRecordRangeValues(String tableName, String startRowKey, String stopRowKey) throws IOException {
        List<String> result = new ArrayList<>();
        SparkConf sc = new SparkConf()
                .setMaster("local")
                .setAppName("MongoSparkConnectorTour")
                .set("spark.mongodb.input.uri", "mongodb://katya:echo216@ds135798.mlab.com:35798/heroku_41659s43.heatmap")
                .set("spark.mongodb.output.uri", "mongodb://katya:echo216@ds135798.mlab.com:35798/heroku_41659s43.heatmap");

        JavaSparkContext jsc = new JavaSparkContext(sc); // Create a Java Spark Context

//        JavaRDD<Document> documents = jsc.parallelize(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).map
//                (new Function<Integer, Document>() {
//                    public Document call(final Integer i) throws Exception {
//                        return Document.parse("{_id: " + i+10 + ", data: " + "[{\"a\":39.981,\"c\":2,\"o\":116.343},{\"a\":39.964,\"c\":2,\"o\":116.396}]" + "}");
//                    }
//                });
//
//        MongoSpark.save(documents);


        JavaMongoRDD<Document> rdd = MongoSpark.load(jsc);
        System.out.println(rdd.count());
        System.out.println(rdd.first().toJson());

        JavaMongoRDD<Document> aggregatedRdd = rdd.withPipeline(singletonList(Document.parse("{ $match: { _id : { $gte : \"10s2008-10-23T02:53:25\" , $lte: \"10s2008-10-23T02:54:05\"} } }")));
        System.out.println(aggregatedRdd.count());
//        System.out.println(aggregatedRdd.first().toJson());

        List<Document> output = aggregatedRdd.collect();
        for (Document tuple : output) {
            System.out.println(tuple.toJson());
            result.add(tuple.toJson());

        }
        return result;
    }

    public static List<String> getAllRowIDs(String tableName) throws IOException {
        return new ArrayList<>();
    }

}
