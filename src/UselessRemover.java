import java.util.*;

/**
 * Created by Armaghan on 5/21/2018.
 */
public class UselessRemover {
    Vector<String[]> grammar;
    ArrayList<String> nonVarTerminals;

    public UselessRemover(Vector<String[]> grammar) {
        this.grammar = grammar;
        nonVarTerminals = new ArrayList<>();
        nonVarTerminals.add(grammar.get(0)[0]);
        for (int i = 1; i < grammar.size(); i++) {
            nonVarTerminals.add(grammar.get(i)[0]);
        }
        LinkedHashSet<String> lhs = new LinkedHashSet<String>();
        lhs.addAll(nonVarTerminals);
        nonVarTerminals.clear();
        nonVarTerminals.addAll(lhs);
    }

    public void firstStep() {
        ArrayList<String> oldV, newV;
        oldV = new ArrayList<>();
        newV = new ArrayList<>();

        for (int i = 0; i < grammar.size(); i++) {
            outer:
            for (int l = 1; l < grammar.get(i).length; l++) {
                for (int j = 0; j < nonVarTerminals.size(); j++) {
                    if (grammar.get(i)[l].equals(nonVarTerminals.get(j)))
                        break outer;
                    else if (l == (grammar.get(i).length - 1))
                        newV.add(grammar.get(i)[0]);
                }
            }
        }
        LinkedHashSet<String> lhs = new LinkedHashSet<String>();
        lhs.addAll(newV);
        newV.clear();
        newV.addAll(lhs);

        while (oldV.size() != newV.size()) {
            oldV = newV;
            newV=help(newV);
            lhs.addAll(newV);
            newV.clear();
            newV.addAll(lhs);
        }

        ArrayList<String> shouldRemove = removed(newV);
        for (int i = 0; i < grammar.size(); i++) {
            for (int j = 0; j < newV.size(); j++) {
                if (grammar.get(i)[0].equals(newV.get(j)))
                    break;
                else if (j == (newV.size() - 1)) {
                    grammar.remove(i);
                }
            }
        }
        for (int k = 0; k < shouldRemove.size(); k++) {
            for (int i = 0; i < grammar.size(); i++) {
                grammar.set(i, removeElements(grammar.get(i), shouldRemove.get(k)));
                if (grammar.get(i).length<2)
                    grammar.remove(i);
            }
        }
    }
    ArrayList<String> help(ArrayList<String> arr) {
        ArrayList<String> eliminated = removed(arr);
        ArrayList<String> output = arr;
        outer:
        for (int i = 0; i < grammar.size(); i++) {
            for (int j = 1; j < grammar.get(i).length; j++) {
                for (int k = 0; k < eliminated.size(); k++) {
                    if (grammar.get(i)[j].equals(eliminated.get(k)))
                        continue outer;
                }
            }
            output.add(grammar.get(i)[0]);
        }
        return output;
    }

    ArrayList<String> removed(ArrayList<String> arr) {
        ArrayList<String> rem = new ArrayList<>();
        outer:
        for (int i = 0; i < nonVarTerminals.size(); i++) {
            for (int j = 0; j < arr.size(); j++) {
                if (arr.get(j).equals(nonVarTerminals.get(i)))
                    continue outer;
            }
            rem.add(nonVarTerminals.get(i));
        }
        return rem;
    }

    public void secondStep() {
        ArrayList<String> oldV, newV;
        oldV = new ArrayList<>();
        newV = new ArrayList<>();
        newV.add("S");
        LinkedHashSet<String> lhs = new LinkedHashSet<String>();
        while (oldV.size() != newV.size()) {
            oldV = newV;
            newV=getNonVarTerminals(newV);
            lhs.addAll(newV);
            newV.clear();
            newV.addAll(lhs);
        }
        ArrayList<String> shouldRemove = removed(newV);
        for (int i = 0; i < grammar.size(); i++) {
            for (int j = 0; j < newV.size(); j++) {
                if (grammar.get(i)[0].equals(newV.get(j)))
                    break;
                else if (j == (newV.size() - 1)) {
                    grammar.remove(i);
                }
            }
        }

        for (int k = 0; k < shouldRemove.size(); k++) {
            for (int i = 0; i < grammar.size(); i++) {
                grammar.set(i, removeElements(grammar.get(i), shouldRemove.get(k)));
                if (grammar.get(i).length<2)
                    grammar.remove(i);
            }
        }
    }

    ArrayList<String> getNonVarTerminals(ArrayList<String> arr) {
        int size=arr.size();
        for (int i = 0; i < size; i++) {
            System.out.println(arr);
            for (int j = 0; j < grammar.size(); j++) {
                if (grammar.get(j)[0].equals(arr.get(i))){
                    for (int k = 1; k < grammar.get(j).length; k++) {
                        for (int l = 0; l < nonVarTerminals.size(); l++) {
                            if(nonVarTerminals.get(l).equals(grammar.get(j)[k])) {
                                arr.add(grammar.get(j)[k]);
                                break;
                            }
                        }
                    }
                }

            }
        }
        return arr;
    }

    public static String[] removeElements(String[] input, String deleteMe) {
        if (input != null) {
            List<String> list = new ArrayList<String>(Arrays.asList(input));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(deleteMe)) {
                    list.remove(i);
                }
            }
            return list.toArray(new String[0]);
        } else {
            return new String[0];
        }
    }
}