package AddTaskToSchedule;

import CtrlBetweenUiAndDB.FieldCheck;
import CtrlBetweenUiAndDB.InsertIntoListView;
import DatabaseControl.ConnectDatabase;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddTaskController extends AddTask implements Initializable {
    @FXML
    private JFXTimePicker jfxTimePicker;
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
    private JFXComboBox alarm;
    @FXML
    private JFXComboBox<String> list;
    ObservableList observableList= FXCollections.observableArrayList("None","At the start","At the end");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InsertIntoListView.insert(list);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        alarm.setValue("None");
        alarm.setItems(observableList);
    }
    @FXML
    private void okBtnAction() throws SQLException {
        String date=getTableName();
        if(FieldCheck.checkAndInsert(getTableName(),"",1,taskArea,desArea,
                duHour,duMin,duSec,jfxTimePicker,date,alarm,list)) {
            Connection con= ConnectDatabase.connection();
            PreparedStatement ps=null;
            try{
                if(!getDoesExist()) {
                    String sql = "INSERT INTO Date(days) VALUES(?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, date);
                    ps.execute();
                    System.out.println("Data has been inserted!");
                    setDoesExist(true);
                }
            }
            catch (SQLException e){
                System.out.println(e+"ScheduleConform");
            }
            finally {
                try {
                    ps.close();
                }
                catch (NullPointerException e){
                    System.out.println();
                }
                con.close();
            }
        }
    }
}
