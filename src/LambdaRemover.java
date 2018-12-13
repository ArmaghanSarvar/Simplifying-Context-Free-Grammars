import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by Armaghan on 5/21/2018.
 */
public class LambdaRemover{
    private Vector<String> grammar;
    private Vector<String> newgrammar;
    private Vector<Variable> variables = new Vector<>();
    public File file;

    public LambdaRemover(Vector<String> grammar) {
        file = new File("LambdaRemoved.txt");
        this.grammar = grammar;
        for (int i = 0; i <grammar.size() ; i++) {
            Variable v = new Variable(' ');
            variables.add(v);
        }
        newgrammar = new Vector<>();
        readVars();
        checkNullable();
        removeNullable();
        print();
    }

    private void readVars() {
        for (int i = 0; i < grammar.size(); i++) {
            if (isNew(grammar.get(i).charAt(0))) {
                Variable v = new Variable(grammar.get(i).charAt(0));
                variables.set(i, v);
            }
        }
        System.out.println("size of var is" + variables.size());
    }

    private void checkNullable() {
        for (int i = 0; i < grammar.size(); i++) {
            Variable v = findVar(grammar.get(i).charAt(0));
            for (int j = 1; j < grammar.get(i).length(); j++) {
                if (grammar.get(i).charAt(j) == 'l'){
                    v.isNullable = true;
                }
                else if (Character.isUpperCase(grammar.get(i).charAt(j)) &&
                        variables.get(getIndex(grammar.get(i).charAt(j))).isNullable)
                    v.isNullable = true;
                }
            }
//        for (int i = 0; i <variables.size() ; i++) {
//            if (variables.get(i).isNullable)
//                System.out.println(variables.get(i).var);
//        }
    }

    private void removeNullable(){
        omitNs();
        for (int i = 0; i < grammar.size(); i++) {
            for (int j = 1; j < grammar.get(i).length(); j++) {
                if (isNew(grammar.get(i)))
                    newgrammar.add(grammar.get(i));

                if (findVar(grammar.get(i).charAt(j)).isNullable
                            && existsInList(grammar.get(i).charAt(0))) {
                    String s = removeCharAt(grammar.get(i),j);
                    if (s.length() >= 2 && isNew(s))
                        newgrammar.add(s);
                    }
                }
            }
    }

    void check(){
        for (int i = 0; i < grammar.size(); i++) {
            System.out.println("gr is"+ grammar.get(i));
        }
    }

    private void omitNs() {
        for (int i = 0; i < grammar.size(); i++) {
            for (int j = 1; j < grammar.get(i).length(); j++) {
                if (grammar.get(i).length() < 3 && grammar.get(i).charAt(j) == 'l') {
                    grammar.remove(grammar.get(i));
                }
            }
        }
    }

    public static String removeCharAt(String s, int pos) {
        StringBuffer buf = new StringBuffer( s.length() - 1 );
        buf.append( s.substring(0,pos) ).append( s.substring(pos+1) );
        return buf.toString();
    }

    private boolean existsInList(Character c){
        for (int i = 0; i < grammar.size(); i++) {
            if (grammar.get(i).charAt(0) == c)
                return true;
        }
        return false;
    }

    private Variable findVar(Character c){
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).var == c)
                return variables.get(i);
        }
        return new Variable(c);
    }

    private boolean isNew(Character c){
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).var == c)
                return false;
        }
        return true;
    }

    private boolean isNew(String s){
        for (int i = 0; i < newgrammar.size(); i++) {
            if (newgrammar.get(i).equals(s))
                return false;
        }
        return true;
    }

    private int getIndex(Character c){
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).var == c)
                return i;
        }
        return 0;
    }

    public void print() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            for (int i = 0; i < newgrammar.size(); i++) {
                for (int j = 0; j < newgrammar.get(i).length(); j++) {
                    writer.append(newgrammar.get(i).charAt(j));
                }
                writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}