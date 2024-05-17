package Interfaces;

import java.util.ArrayList;

public interface FileOperations {
    public ArrayList<String> getWordArray(String filePath);
    public void writeToCsv(String path, String content);
}
