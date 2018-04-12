package satsolvers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.Random;


public class Controller {

    ObservableList<String> solversList = FXCollections.observableArrayList("MiniSAT",
            "Glucose", "SAT4J", "CleaneLing", "PBLib", "OpenWBO");

    @FXML
    private TextArea formula;
    @FXML
    private Button resetBtn;
    @FXML
    private Button startBtn;
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
    }

    @FXML
    private void zamknij(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showResult(ActionEvent event) {
        String input = formula.getText();
        Boolean b = model.process(input);

        Random random = new Random();
        boolean r = random.nextBoolean();
        if (r == true) {
            result.setText("Formuła jest spełnialna!");
            result.setFill(Color.GREEN);
        } else {
            result.setText("Formuła jest niespełnialna!");
            result.setFill(Color.RED);
        }
        lastFormula.setText(formula.getText());
        lastFormula.setStyle("-fx-text-fill: gray");
    }
}
