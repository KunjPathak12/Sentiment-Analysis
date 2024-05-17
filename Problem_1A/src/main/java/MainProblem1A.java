import Impl.InsertToMongoImpl;
import Impl.ReutReaderImpl;
import Interfaces.InsertToMongo;
import Interfaces.ReutReader;

public class MainProblem1A {
    public static void main(String[] args) {
        ReutReader reader = new ReutReaderImpl();
        InsertToMongo insert = new InsertToMongoImpl();
        String fileName = "reut2-009.sgm";
        String file = reader.readFile(fileName);
        String uri = "mongodb+srv://kunj:kunj12345@assignment.4ogqk51.mongodb.net/" ;
        System.out.println(insert.insertData(file,uri));


    }
}
