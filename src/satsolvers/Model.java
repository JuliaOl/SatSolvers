package satsolvers;

import jdd.sat.DimacsReader;
import jdd.sat.Solver;
import jdd.sat.gsat.GSATSolver;
import jdd.sat.gsat.WalkSATSolver;
import org.logicng.datastructures.Tristate;
import org.logicng.io.parsers.ParserException;
import org.logicng.io.parsers.PropositionalParser;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Formula;
import org.logicng.solvers.CleaneLing;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.SATSolver;

import java.io.IOException;

public class Model {


    final FormulaFactory f = new FormulaFactory();
    final PropositionalParser p = new PropositionalParser(f);
    //final PseudoBooleanParser p = new PseudoBooleanParser(f);
    final SATSolver miniSat = MiniSat.miniSat(f);
    final SATSolver cleaneLing = CleaneLing.full(f);
    Solver solver2 = new GSATSolver(2000);
    Solver walkSolver = new WalkSATSolver(2000, 0.02);
    DimacsReader dr;
    //String stringFormula = "p cnf 6 3\n" + "1 -2 3 0\n" + "2 4 5 0\n" + "4 6 0";
    //String stringFormula = "p cnf 3 1\n 1 -2 3 0";
    //String stringFormula = "p cnf 1 2\n 1 0 -1 0";
    //String stringFormula = "p cnf 2 3\n 1 0 -1 2 0 -2 0";

    public Boolean process(String in, String choice) throws ParserException {
        /*boolean isCNF = checkCNF(input);
        if (!isCNF)
            input = toCNF(input);*/
        String input = toCNF(in);
        System.out.println("input aaa: "+input);
        if (input.equals("false"))
            return false;
        else if (input.equals("true"))
            return true;
        else {
            System.out.println("jestem po return w process");

            Formula formula = p.parse(input);
            Tristate result;
            Boolean boolResult = false;
            // try {
            //     Formula formula = p.parse(input);
            //} catch (ParserException e) {
            //   e.printStackTrace();
            //}
            switch (choice) {
                case "MiniSAT":
                    miniSat.add(formula);
                    result = miniSat.sat();
                    if (result.toString().equals("TRUE")) boolResult = true;
                    else boolResult = false;
                    break;
                case "CleaneLing":
                    cleaneLing.add(formula);
                    result = cleaneLing.sat();
                    if (result.toString().equals("TRUE")) boolResult = true;
                    else boolResult = false;
                    break;
                case "GSATSolver":

                    setDimacs(formula);
                    solver2.setFormula(dr.getFormula());
                    int[] x = solver2.solve();
                /*System.out.println("tablica: ");
                if(x != null)
                    for(int j = 0; j < x.length; j++)
                        System.out.print("" + x[j]) ; System.out.println();*/
                    if (x != null) boolResult = true;
                    solver2.cleanup();
                    break;
                case "WalkSATSolver":
                    setDimacs(formula);
                    walkSolver.setFormula(dr.getFormula());
                    if (walkSolver.solve() != null) boolResult = true;
                    solver2.cleanup();
                    break;
                default:
                    break;
            }

            //if (result == null) {
            //  System.out.print("result == null !"); //ZMIENIC TO
            //return false;
            if (boolResult) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void setDimacs(Formula formula) {
        try {
            int literals = formula.variables().size();
            String dimacs = parseToDimacs.formulaToDimacs(formula.toString(), literals);
            System.out.println("Dimacs: " + dimacs);
            dr = new DimacsReader(dimacs, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkCNF(String input) {

        Boolean isCnf = false;
        try {
            final Formula formula = p.parse(input);
            final Formula cnf = formula.cnf();
            String in = input.replaceAll("\\s","");
            String compare1 = cnf.toString().replaceAll("\\s","");
            //System.out.print(in+ " "+compare1);
            if (compare1 == "$false" || compare1 == "$true")
                isCnf = true;
            else if (in.equals(compare1))
                isCnf = true;
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

            result = cnf.toString().replaceAll("\\$", "");
            //debug
            //final Formula formula = p.parse("A&A");
            //System.out.println(formula.toString());
            /*input = input.replaceAll("~", "!");
            System.out.println("input: "+ input);
            Expression<String> f = ExprParser.parse(input);
            Expression<String> cnf2 = RuleSet.toCNF(f);
            result = cnf2.toString();
            System.out.println("cnf2 : " + result);*/

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
            result = nnf.toString().replaceAll("\\$", "");
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}
