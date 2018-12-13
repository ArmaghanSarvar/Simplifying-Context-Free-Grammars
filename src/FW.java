import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class FW {

    public FW(Vector<String[]> grammar) throws IOException {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("FinalResult.txt", true));
            for (int i = 0; i < grammar.size(); i++) {
                for (int j = 0; j < grammar.get(i).length; j++) {
                    writer.append(grammar.get(i)[j]);
                }
                writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}