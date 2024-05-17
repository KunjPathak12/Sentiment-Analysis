package Impl;

import Interfaces.TextExtractor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextExtractorImpl implements TextExtractor {

    @Override
    public Pattern patternMatcher(String matcherString) {
        Pattern pattern  = Pattern.compile("<" + matcherString + ">(.+?)</" + matcherString + ">", Pattern.DOTALL);
        return pattern;
    }

    @Override
    public String splitFileByTitle(String File) {

        String titleFile = "";
        Pattern pattern = patternMatcher("TITLE");
        Matcher matcher = pattern.matcher(File);
        while(matcher.find()){
            titleFile+=(matcher.group(1).replace("&lt;", "").replace(">", "").trim());
        }
//        for (String i : titleFile) {
            if( titleFile.contains("&lt;")){
                titleFile+= titleFile.split("&lt;")[0];
            }
            else if(titleFile.contains(">")){
                titleFile+= titleFile.split(">")[0];
            }
//        }

        return titleFile;
    }

    @Override
    public String splitFileByBody(String File) {

        String bodyFile = "";
        Pattern pattern = patternMatcher("BODY");
        Matcher matcher = pattern.matcher(File);
        while(matcher.find()){
            bodyFile+=(matcher.group(1).replace("&#3;", "").replace("&lt;", "").replace(">", "").trim().replace("Reuter", "").replace("REUTER", ""));

        }
        return bodyFile;
    }

    @Override
    public ArrayList <String> mappedData(String File) {
        ArrayList<String> Data = new ArrayList<>();
        Pattern pattern = Pattern.compile("<REUTERS.*?>(.*?)</REUTERS>");
        Matcher matcher = pattern.matcher(File);

        while(matcher.find()){
            Data.add("Title: "+splitFileByTitle(matcher.group(1))+System.lineSeparator()+"Body: "+splitFileByBody(matcher.group(1))+System.lineSeparator());
//            Data.add();
        }
//        System.out.println(Data.size());
        return Data;
    }


}
