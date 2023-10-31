package EditBtnWindow;

import CtrlBetweenUiAndDB.FieldCheck;
import AllTaskWindow.AllTaskController;
import CtrlBetweenUiAndDB.TskTimeCollision;
import HomeBtnWindow.HomeController;
import ListWindow.ListController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditBtnController implements Initializable{
    @FXML
    private JFXTextArea taskArea;

    @FXML
    private JFXTextArea desArea;

    @FXML
    private JFXButton okBtn;

    @FXML
    private JFXTimePicker jfxTimePicker;

    @FXML
    private JFXTextArea duHour;

    @FXML
    private JFXTextArea duSec;

    @FXML
    private JFXTextArea duMin;
    @FXML
    private JFXComboBox alarmOption;
    @FXML
    private JFXComboBox<String>listOption;
    public static String tableName;
    public static String query;
    SetEditWindow setEditWindow;
    FieldCheck fieldCheck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableName=SetAndGet.getTableName();
        query=SetAndGet.getQuery();
        try {
            setEditWindow.loading(taskArea,desArea,jfxTimePicker,duHour,duMin,duSec,
                    alarmOption,listOption,tableName,query);
        } catch (SQLException|NullPointerException e) {
            e.printStackTrace();
        }

        System.out.println("Edit:"+tableName);
    }
    @FXML
    private void editOkBtn() throws SQLException {
        String date=tableName;
        fieldCheck.checkAndInsert(tableName,query,2,taskArea,desArea
                ,duHour,duMin,duSec,jfxTimePicker,date,alarmOption,listOption );

    }


}