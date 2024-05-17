package org.example;

import Impl.FileOperationsImpl;
import Impl.ReutReaderImpl;
import Impl.TextExtractorImpl;
import Interfaces.FileOperations;
import Interfaces.ReutReader;
import Interfaces.TextExtractor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        ReutReader reader = new ReutReaderImpl();
        TextExtractor textExtractor = new TextExtractorImpl();
        FileOperations fops = new FileOperationsImpl();
        ArrayList<String> stopWords = fops.getWordArray("stopwords.txt");
        SparkSession spark = SparkSession.builder().config("spark.some.config.option","some-value").config("spark.master", "local[*]").getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        JavaRDD<String> input = jsc.parallelize(textExtractor.mappedData(reader.readFile("reut2-009.sgm")));
        JavaRDD<String> words = input.flatMap(line -> Arrays.asList(line.split("\\s+")).iterator());
        JavaRDD<Map.Entry<String, Integer>> wordCounts = words
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b)
                .map(pair -> new AbstractMap.SimpleEntry<>(pair._1().replace("Body:","").replace("Title:",""), pair._2()));

        List<Map.Entry<String, Integer>> result = wordCounts.collect();
        HashMap<String, Integer> myHashMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : result) {
            if(stopWords.contains(entry.getKey().toLowerCase())) continue;
            myHashMap.put(entry.getKey(), entry.getValue());
        }
        ArrayList<String>mostFrequentWords = new ArrayList<>();
        ArrayList<String>leastFrequentWords = new ArrayList<>();
        int max = 1;
        int min=1;
        for(String i: myHashMap.keySet()){
            if(myHashMap.get(i)<=max) continue;
            if(i.equals("") ) continue;
            max = myHashMap.get(i);
        }
        for(String i: myHashMap.keySet()){
            if(myHashMap.get(i) == max) mostFrequentWords.add(i);
            if(myHashMap.get(i)==min) leastFrequentWords.add(i);
        }
        System.out.println("mostFrequentWords: "+ mostFrequentWords.toString()+ System.lineSeparator()+"Max VAl = "+max +System.lineSeparator()+"leastFrequentWords: "+ leastFrequentWords.toString()+ System.lineSeparator()+"min VAl = "+min);
    }

}
