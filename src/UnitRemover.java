import java.io.*;
import java.util.Vector;

/**
 * Created by Armaghan on 5/21/2018.
 */
public class UnitRemover {
    private Vector<Vector<String>> mainVec;
    private Vector<Vector<String>> resultVec;
    private Vector<String> vars;
    private Vector<Boolean> used;
    private Vector<String> grammar;
    private File file;
    public File outfile;

    public UnitRemover(File f1) {
        mainVec = new Vector<>();
        resultVec = new Vector<>();
        grammar = new Vector<>();
        this.file = f1;
        outfile = new File("UnitRemoved.txt");
        vars = new Vector<>();
        used = new Vector<>();
        read();
        for (int i = 0; i < vars.size(); i++) {
            resultVec.add(new Vector<String>());
            used.add(Boolean.FALSE);
        }
        for (int i = 0; i < vars.size(); i++) {
            for (int j = 0; j < vars.size(); j++) {
                used.setElementAt(Boolean.FALSE, j);
            }
            unitRmv(i, i);
        }
        printRslt();
    }

    public void printRslt() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(outfile, true));
            for (int i = 0; i < vars.size(); i++) {
                for (int j = 0; j < resultVec.get(i).size(); j++) {
                    grammar.add(vars.get(i)+ resultVec.get(i).get(j));
                    writer.append(vars.get(i) + resultVec.get(i).get(j) + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unitRmv(int j, int k) {
        if (used.get(j) == Boolean.FALSE) {
            used.setElementAt(Boolean.TRUE, j);
            for (int i = 0; i < mainVec.get(j).size(); i++) {
                if (mainVec.get(j).get(i).length() == 1 && vars.contains(mainVec.get(j).get(i).substring(0, 1))) {
                    unitRmv(vars.indexOf(mainVec.get(j).get(i)), k);
                } else
                    resultVec.get(k).add(mainVec.get(j).get(i));
            }
        }
    }

    public void read() {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            line = bufferedReader.readLine();
            mainVec.add(new Vector<String>());
            mainVec.lastElement().add(line.substring(1));
            vars.add(line.substring(0, 1));
            while ((line = bufferedReader.readLine()) != null) {
                if (vars.contains(line.substring(0, 1)))
                    mainVec.get(vars.indexOf(line.substring(0, 1))).add(line.substring(1));
                else {
                    mainVec.add(new Vector<String>());
                    mainVec.lastElement().add(line.substring(1));
                    vars.add(line.substring(0, 1));
                }
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}