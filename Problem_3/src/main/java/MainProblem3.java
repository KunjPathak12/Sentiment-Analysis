import Impl.SentiMentUtilsImpl;
import Interfaces.SentimentUtils;

public class MainProblem3 {
    public static void main(String[] args) {
        SentimentUtils obj = new SentiMentUtilsImpl();
        obj.SentimentalAnalysis("negative-words.txt","positive-words.txt");

    }

}
