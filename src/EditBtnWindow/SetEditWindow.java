package EditBtnWindow;

import CtrlBetweenUiAndDB.InsertIntoListView;
import DatabaseControl.ConnectDatabase;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class SetEditWindow {
    public static String List;
    public static void loading(JFXTextArea taskArea, JFXTextArea desArea, JFXTimePicker timePicker
            , JFXTextArea dh, JFXTextArea dm, JFXTextArea ds, JFXComboBox alarmOption,JFXComboBox<String>listOption,
                               String tableName, String query) throws SQLException{

        ObservableList<String> observableList= FXCollections.observableArrayList("None","At the start","At the end");
        char[] dateChar = tableName.toCharArray();
        dateChar[4] = '_';
        dateChar[7] = '_';
        tableName = String.valueOf(dateChar);
        tableName="d"+tableName;
        Connection con = ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {

            String sql="SELECT Task,Description,Time,DuHour,DuMin,DuSec,Alarm,List FROM "+tableName+" WHERE Time=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,query);
            rs= ps.executeQuery();

            String Task=rs.getString("Task");
            String Description=rs.getString("Description");
            String Time=rs.getString("Time");
            String DuHour=rs.getString("DuHour");
            String DuMin=rs.getString("DuMin");
            String DuSec=rs.getString("DuSec");
            String Alarm=rs.getString("Alarm");
            List=rs.getString("List");

            taskArea.setText(Task);
            desArea.setText(Description);

            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");

            java.sql.Time timeValue = new java.sql.Time(formatter.parse(Time).getTime());
            LocalTime localTime = timeValue.toLocalTime();

            timePicker.setValue(localTime);
            dh.setText(DuHour);
            dm.setText(DuMin);
            ds.setText(DuSec);
            alarmOption.setValue(Alarm);
            alarmOption.setItems(observableList);

//            try {
//                InsertIntoListView.insert(listOption);
//            } catch (SQLException exception) {
//                exception.printStackTrace();
//            }
            listOption.setValue(List);
        } catch (SQLException | ParseException throwables) {
            System.out.println(throwables+"SetEditCon");
        }
        finally {
            {
                ps.close();
                rs.close();
                con.close();
            }
        }
        try {
            InsertIntoListView.insert(listOption);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        listOption.setValue(List);
    }
}