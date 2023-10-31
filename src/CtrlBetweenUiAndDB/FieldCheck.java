package CtrlBetweenUiAndDB;

import DatabaseControl.InsertTable;
import DatabaseControl.UpdateTable;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;

public class FieldCheck extends ApplyDoesDayExceed{
    public static InsertTable insertTable;
    public static FieldCheck fieldCheck=new FieldCheck();
    public static boolean checkAndInsert(String tblName, String query, int flag, JFXTextArea taskArea, JFXTextArea desArea, JFXTextArea duHour,
                                         JFXTextArea duMin, JFXTextArea duSec, JFXTimePicker jfxTimePicker, String jfxDate,
                                         JFXComboBox alarmOption, JFXComboBox<String> listOption)throws SQLException {
        String task = taskArea.getText();
        String des = desArea.getText();
        String hourArea = duHour.getText();
        String minArea = duMin.getText();
        String secArea = duSec.getText();
        String time;
        String list="";

        try {
            time = jfxTimePicker.getValue().toString();
        } catch (NullPointerException e) {
            time = "";
        }
        try {
            list = listOption.getValue().toString();
        } catch (Exception e) {
            list = "";
        }

        if ((hourArea.length() != 0 || minArea.length() != 0 || secArea.length() != 0)
                && task.length() != 0 && time.length() != 0 && jfxDate.length() != 0) {

            if (hourArea.length() == 0) duHour.setText("0");
            if (minArea.length() == 0) duMin.setText("0");
            if (secArea.length() == 0) duSec.setText("0");
            if (des.length() == 0) desArea.setText("...");

            //flag=1 means insert
            //flag=2 means update

            if (TskTimeCollision.checkTaskTime(tblName, query, time, duHour.getText(), duMin.getText(), duSec.getText(), flag)
                    &&!fieldCheck.testExceeding(time,hourArea,minArea,secArea)&&flag==1) {

                insertTable.insert(tblName,taskArea.getText(), desArea.getText(), jfxTimePicker.getValue().toString(),
                        duHour.getText(), duMin.getText(), duSec.getText(), alarmOption.getValue().toString(),
                        "Unchecked", list);

                taskArea.setPromptText("Task...");
                jfxTimePicker.setPromptText(null);
                taskArea.setText("");
                desArea.setText("");
                duSec.setText("");
                duMin.setText("");
                duHour.setText("");
                alarmOption.setValue("None");
                jfxTimePicker.setValue(null);
                return true;
            }
            else if(TskTimeCollision.checkTaskTime(tblName, query, time, duHour.getText(), duMin.getText(), duSec.getText(), flag)
                    &&!fieldCheck.testExceeding(time,hourArea,minArea,secArea)
                    &&flag==2){

                UpdateTable.update(taskArea,desArea,jfxTimePicker,duHour,duMin,duSec,
                        alarmOption,"Unchecked",list,tblName,query);
                return true;
            }
            else if(fieldCheck.testExceeding(time,hourArea,minArea,secArea)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Alert");

                ButtonType buttonType=new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);

                a.setContentText("24 Hour exceeded!");
                a.getButtonTypes().setAll(buttonType);

                a.show();
                return false;
            }
            else return false;
        }

        else {
            if (task.length() == 0) {
                taskArea.setPromptText("You must set a task");
            }
            if (time.length() == 0) {
                jfxTimePicker.setPromptText("You must set a time");
            }
            return false;
        }
    }
}
