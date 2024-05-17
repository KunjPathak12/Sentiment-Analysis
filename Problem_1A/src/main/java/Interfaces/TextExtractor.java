package Interfaces;

import java.util.ArrayList;
import java.util.regex.Pattern;

public interface TextExtractor {

    public Pattern patternMatcher (String matcherString);
    public String splitFileByTitle(String File);
    public String splitFileByBody(String File);
    public ArrayList<String> mappedData(String File);
}
