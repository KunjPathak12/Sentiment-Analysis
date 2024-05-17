package Impl;

import Interfaces.ReutReader;

import java.io.*;

public class ReutReaderImpl implements ReutReader {
    public String readFile(String filename){
        File file  = new File(filename);
        try{
            BufferedReader bufferedReader =  new BufferedReader(new FileReader(file));
            String str;
            String textFile = "";
            try{
                while((str = bufferedReader.readLine())!=null){
                    if(!str.isEmpty()){
                        textFile +=str;
                    }
                    
                }
                bufferedReader.close();
            }
            catch (IOException e) {
                e.getMessage();
                return "File End";
            }
            return textFile;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return "File not Present";
        } 
        
    }

}