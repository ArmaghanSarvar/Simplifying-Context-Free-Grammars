import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class RF {
    Vector<String[]> grammar;
    RF(File file) {
        grammar = new Vector<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                grammar.add(sCurrentLine.split(""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}