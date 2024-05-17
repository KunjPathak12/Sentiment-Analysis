package Impl;

import Interfaces.*;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SentiMentUtilsImpl implements SentimentUtils {

    private ArrayList<String> titleExtractor(ArrayList<String> cleandText){

        ArrayList<String> titleList = new ArrayList<>();
        for(String i: cleandText){
            i = i.split(System.lineSeparator())[0];
            if(i.contains("Title: ")){
                i = i.replace("Title: ","");
                if(!i.isEmpty() || !i.isBlank()) titleList.add(i);
            }
        }

        return titleList;
    }

    private ArrayList<String>getTitles(String uri){
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("ReuterDb");
        MongoCollection<Document> collection = database.getCollection("Reuter's Data");
        FindIterable<Document> docList = collection.find();
        ArrayList<String> titleList = new ArrayList<>();
        for(Document document: docList){
            titleList.add(document.get("Title").toString());
        }
       return titleList;
    }

    @Override
    public boolean SentimentalAnalysis(String negativeWordFile, String positiveWordFile){
        try{
            FileOperations fops = new FileOperationsImpl();
            ArrayList<String> negativeWords = fops.getWordArray("negative-words.txt");
            ArrayList<String> positiveWords = fops.getWordArray("positive-words.txt");
            HashMap<String, Integer> titleSentiment = new HashMap<String, Integer>();
            String csvPath = "Problem_3"+ File.separator+"SentimentAnalysis.csv";
            int index = 1;
            SentimentTypes stype = SentimentTypes.Neutral;
            fops.writeToCsv(csvPath,"News#,"+"\t"+"Title Content,"+"\t"+"Score,"+"\t"+"Match,"+"\t"+"Polarity");
            for (String i: getTitles("mongodb+srv://kunj:kunj12345@assignment.4ogqk51.mongodb.net/")){
                int counter = 0;

                ArrayList<String> matchedWords = new ArrayList<>();
                List<String> eachTitle = Arrays.asList(i.toLowerCase().replace(",","").split(" "));
                for(String j: eachTitle){
                    if (positiveWords.contains(j)){
                        matchedWords.add(j);
                        counter++;
                    }
                    else if(negativeWords.contains(j)) {
                        matchedWords.add(j);
                        counter--;
                    }
                }

                titleSentiment.put(i, counter);
                stype = (counter > 0) ? SentimentTypes.Positive : SentimentTypes.Negative;
                if(counter == 0)stype = SentimentTypes.Neutral;
                String data = index + ",\t"+ i.replace(",", " ") + ",\t" + titleSentiment.get(i) + ",\t" + matchedWords.toString().replace(",", " ") + ",\t" + stype;
                index++;
                fops.writeToCsv(csvPath,data);

            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
