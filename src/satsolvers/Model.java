package satsolvers;

import org.logicng.datastructures.Tristate;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Formula;
import org.logicng.solvers.CleaneLing;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;

public class Model {


    final FormulaFactory f = new FormulaFactory();
    final PropositionalParser p = new PropositionalParser(f);
    final SATSolver miniSat = MiniSat.miniSat(f);
    final SATSolver cleaneLing = CleaneLing.full(f);


    public Boolean process(String input, String choice) {
        //System.out.print(input);
        Formula formula = null;
        Tristate result = null;
        try {
            formula = p.parse(input);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        switch (choice) {
            case "MiniSAT" : miniSat.add(formula);
                result = miniSat.sat();
                break;
            case "CleaneLing" : cleaneLing.add(formula);
                result = cleaneLing.sat();
            default:
                break;
        }

        if (result == null) {
            System.out.print("result == null !");
            return false;
        } else if (result.toString().equals("TRUE")) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkCNF(String input) {

        Boolean isCnf = false;
        try {
            final Formula formula = p.parse(input);
            final Formula cnf = formula.cnf();
            final Formula nnf = formula.nnf();
            String in = input.replaceAll("\\s","");
            String compare1 = cnf.toString().replaceAll("\\s","");
            String compare2 = nnf.toString().replaceAll("\\s","");

            if (in.equals(compare1)) {
                isCnf = true;
            } else if (in.equals(compare2)) {
                isCnf = true;
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return isCnf;
    }

    public String toCNF(String input) {
        String result = "nie mogę przekształcić";
        try {
            final Formula formula = p.parse(input);
            final Formula cnf = formula.cnf();
            result = cnf.toString();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String toNNF(String input) {
        String result = "nie mogę przekształcić";
        try {
            final Formula formula = p.parse(input);
            final Formula nnf = formula.nnf();
            result = nnf.toString();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}
