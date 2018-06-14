package satsolvers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.logicng.io.parsers.ParserException;

import java.util.concurrent.TimeUnit;


public class Controller {
    //satellite
    //"A & ~(B | ~C)" nie cnf
    //A & ~B & C cnf

    ObservableList<String> solversList = FXCollections.observableArrayList("MiniSAT",
             "CleaneLing", "GSATSolver", "WalkSATSolver", "Glucose", "MiniCard");
    // "SAT4J"

    @FXML
    private TextArea formula;
    @FXML
    private Button resetBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button cnfBtn;
    @FXML
    private Button nnfBtn;
    @FXML
    private Button dnfBtn;
    @FXML
    private ChoiceBox solversBox;
    @FXML
    private MenuItem closeItem;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Text result;
    @FXML
    private TextField lastFormula;
    @FXML
    private Text lastFText;

    private Model model = new Model();


    @FXML
    private void initialize() {
        solversBox.setItems(solversList);
        solversBox.setValue("MiniSAT");
        lastFText.setFill(Color.GRAY);

    }

    @FXML
    private void reset(ActionEvent event) {
        formula.setText("");
        result.setText("");
    }

    @FXML
    private void zamknij(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void toNnf(ActionEvent event) {
        String input = formula.getText();
        String nnf = model.toNNF(input);
        formula.setText(input + "\n" + nnf);
        result.setText("");
    }

    @FXML
    private void toCnf(ActionEvent event) {
        String input = formula.getText();
        String cnf = model.toCNF(input);
        formula.setText(input + "\n" + cnf);
        result.setText("");
    }

    @FXML
    private void toDnf(ActionEvent event) {
        String input = formula.getText();
        String dnf = model.toDNF(input);
        formula.setText(input + "\n" + dnf);
        result.setText("");
    }

    @FXML
    private void showResult(ActionEvent event) {
        String input = formula.getText();
        //Boolean isRight = model.checkCNF(input);
        String choice = solversBox.getValue().toString();

        //if (isRight) {
        Boolean b = null;
        try {
            b = model.process(input, choice);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        if (b) {
                result.setText("Formuła jest spełnialna!");
                result.setFill(Color.GREEN);
            } else {
                result.setText("Formuła jest niespełnialna!");
                result.setFill(Color.RED);
            }
            lastFormula.setText(formula.getText());
            lastFormula.setStyle("-fx-text-fill: gray");
        /*} else {
            result.setText("Wpisz poprawną formułę w CNF!");
            result.setFill(Color.ORANGE);
        }*/
    }
}

