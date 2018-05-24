package satsolvers;

public class parseToDimacs {

    public parseToDimacs() {}

    public static String formulaToDimacs(String formula, int numberOfLetters){
        String subResult;
        formula = formula.replaceAll("\\s", "");
        formula = formula.replaceAll("\\(", "");
        formula = formula.replaceAll("\\)", "");
        formula = formula.replaceAll("\\|", "");
        String[] clauses = formula.split("&");
        int numberOfClauses = clauses.length;
        //p cnf 6 3
        subResult = "p cnf " + numberOfLetters + " " + numberOfClauses + "\n";
        String result = parseToDimacs(subResult,clauses);

        return result;
    }

    private static String parseToDimacs(String r, String[] clauses) {
        String result = r;
        char[] clause;
        for (int i=0; i<clauses.length;i++){
            clause = clauses[i].toCharArray();
            changeChars(clause);
            result = addToResult(result,clause);
        }
        return result = result.substring(0, result.length() - 1);

    }

    private static void changeChars(char[] clause) {
        char ch;
        int tmp;
        for (int i = 0; i<clause.length;i++){
            ch = clause[i];
            if (ch == '~') { ch = '-';	}
            else {
                tmp = (int)ch - (int)'A' + 1;
                ch = (char)(tmp + '0');
            }
            clause[i] = ch;
        }
    }

    private static String addToResult(String r, char[] clause) {

        String result = r;
        for(int i = 0; i<clause.length; i++){
            if (clause[i] =='-') {result += clause[i]; }
            else {result += clause[i] + " "; }
        }
        return result += "0 \n";
    }
}
