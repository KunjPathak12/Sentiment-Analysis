package Impl;

import Interfaces.FileOperations;

import java.io.*;
import java.util.ArrayList;

public class FileOperationsImpl implements FileOperations {

    private ArrayList<String> readFile(String filePath){
        File file = new File(filePath);
        ArrayList<String> wordList = new ArrayList<>();
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String word = "";
            while((word = readFile.readLine()) != null)
            {
                    wordList.add(word);
            }
            readFile.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return wordList;
    }

    public boolean mkFile (String path){
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private String writeToCsvUtils(String path, String content){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
            bufferedWriter.write(content+System.lineSeparator());
            bufferedWriter.close();
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  content;
    }

    @Override
    public void writeToCsv(String path, String content){
        File file = new File(path);
        if (!file.exists()) mkFile(path);
        writeToCsvUtils(path, content);
    }

    @Override
    public ArrayList<String> getWordArray(String filePath){
        return readFile(filePath);
    }
}
