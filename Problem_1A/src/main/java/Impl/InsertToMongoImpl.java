package Impl;

import Interfaces.InsertToMongo;
import Interfaces.TextExtractor;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

public class InsertToMongoImpl implements InsertToMongo {
    @Override
    public String insertData(String file, String uri) {
        TextExtractor textExtractor = new TextExtractorImpl();
        try(MongoClient mongoClient = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("ReuterDb");
            MongoCollection<Document> collection = database.getCollection("Reuter's Data");
            try {
                for(String i: textExtractor.mappedData(file)){
                    String Title = "";
                    String Body = "";
                    if(i.contains("Title: ")){
                        Title = i.replace("Title: ", "").split(System.lineSeparator())[0];
                    }
                    if(i.contains("Body: ")){
                        Body = i.replace("Title: "+Title+System.lineSeparator(), "").replace("Body: ", "");
                    }
                    if(!Title.isEmpty() || !Body.isEmpty()){
                        InsertOneResult result = collection.insertOne(new Document()
                                .append("Title", Title)
                                .append("Body", Body));
                        System.out.println("Success! Inserted document id: " + result.getInsertedId());
                    }


                }
                return "Data Insertion Successfull!";

            }
            catch (MongoException me) {
                return "Unable to insert due to an error: " + me;
            }
        }
    }
}
