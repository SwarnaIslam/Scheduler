package MainWindow;

import CtrlBetweenUiAndDB.ListNameIntoDB;
import CtrlBetweenUiAndDB.InsertIntoListView;
import DatabaseControl.DoesTableExist;
import MainClass.Main;

import Search.SearchStage;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController extends DoesTableExist implements Initializable {

    Main m;
    public static String str;
    @FXML
    private JFXComboBox<String> listCombo;
    @FXML
    private Label weather;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String uRl = "https://www.accuweather.com/en/bd/dhaka/28143/current-weather/28143";
        try {
            Document doc = Jsoup.connect(uRl).get();
           weather.setText(doc.select("div.display-temp").text());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void actionOnListClick() throws SQLException {
        listCombo.getItems().clear();
        listCombo.getItems().add("Add List");
        InsertIntoListView.insert(listCombo);
    }

    @FXML
    private void actionOnListSelection(){
        str=listCombo.getValue();
        try {
            if (str.compareTo("Add List") == 0) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setContentText("Enter item");
                dialog.showAndWait().ifPresent(text -> {
                    try {
                        ListNameIntoDB.inserting(text);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
            }
            else{
                m.listShowing();
            }
        }
        catch (NullPointerException | IOException e){

        }
    }
    @FXML
    private void allScheduleBtn() throws IOException {
        m.allScheduleView();
    }
    @FXML
    private void homeBtn() throws IOException {
        m.homeView();
    }
   @FXML
   private void searchBtn()throws IOException{
       SearchStage.searchShowing();
   }
   @FXML
   private void incompleteList() throws IOException {
        m.showIncompleteList();
   }
    public static String getStr() {
        return str;
    }
}
