package AddBtnWindow;

import CtrlBetweenUiAndDB.FieldCheck;
import CtrlBetweenUiAndDB.InsertIntoListView;
import MainClass.Main;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBtnController implements Initializable {
    Main m;
    FieldCheck fieldCheck;
    @FXML
    private JFXDatePicker jfxDatePicker;
    @FXML
    private JFXTextArea taskArea;
    @FXML
    private JFXTextArea desArea;
    @FXML
    private JFXTextArea duHour;
    @FXML
    private JFXTextArea duMin;
    @FXML
    private JFXTextArea duSec;
    @FXML
    private JFXTimePicker jfxTimePicker;
    @FXML
    private JFXComboBox alarmOption;
    @FXML
    private JFXComboBox<String> listOption;

    ObservableList<String>observableList= FXCollections.observableArrayList("None","At the start","At the end");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        jfxDatePicker.setValue(null);
        jfxTimePicker.setValue(null);
        alarmOption.setValue("None");
        alarmOption.setItems(observableList);
        try {
            InsertIntoListView.insert(listOption);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void addAnotherBtnControl() throws SQLException {
        String date;
        try {
            date = jfxDatePicker.getValue().toString();
        } catch (NullPointerException e) {
            date = "";
        }
        fieldCheck.checkAndInsert("Temp","",1,taskArea,desArea,duHour,duMin,
                duSec,jfxTimePicker,date,alarmOption,listOption);
    }
    @FXML
    private void confirmBtnControl() throws IOException, SQLException {
        String date;

        try {
            try {
                date=jfxDatePicker.getValue().toString();
            }
            catch (NullPointerException e){
                date="";
            }
            CheckDate.checkingDate(date);
            if (fieldCheck.checkAndInsert("Temp", "", 1, taskArea, desArea, duHour, duMin,
                    duSec, jfxTimePicker, date, alarmOption, listOption)) {
                m.scheduleConforming(date);
            }


        }
        catch (DateException e){
            jfxDatePicker.setValue(null);
            jfxDatePicker.setPromptText(e.toString());
        }
    }
    @FXML
    private void cancelBtnControl() throws IOException, SQLException {
        m.scheduleCancelling();
    }
}