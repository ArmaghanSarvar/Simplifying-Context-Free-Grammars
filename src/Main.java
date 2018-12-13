import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Armaghan on 5/20/2018.
 */
public class Main {
    public Vector<String> grammar = new Vector<>();
    private String fileName;

    public Main(String fileName) throws IOException {
        this.fileName = fileName;
        LambdaRemover lr = new LambdaRemover(this.getGrammar());
        UnitRemover ur = new UnitRemover(lr.file);
        RF rf = new RF(ur.outfile);
        UselessRemover uselessR = new UselessRemover(rf.grammar);
        uselessR.firstStep();
        uselessR.secondStep();
        new FW(rf.grammar);
    }

    public static void main(String[] args) throws IOException {
         new Main("test.txt");
    }

    private Vector<String> getGrammar() {
        try {
            Scanner fileread = new Scanner(new File(fileName));
            do {
                grammar.add(fileread.nextLine());
            } while (fileread.hasNext());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return grammar;
    }
}