package CtrlBetweenUiAndDB;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class ApplyDoesDayExceed implements DoesExceedDay{
    @Override
    public boolean testExceeding(String timePicker, String duHour, String duMin, String duSec) {
        double hour;
        double min;
        double sec;
        try {
            hour=Integer.parseInt(duHour);
        }
        catch (Exception e){
            hour=0;
        }
        try {
            min=Integer.parseInt(duMin);
        }
        catch (Exception e){
            min=0;
        }
        try {
            sec=Integer.parseInt(duSec);
        }
        catch (Exception e){
            sec=0;
        }
        String startHour=timePicker.substring(0,2);
        String startMin=timePicker.substring(3);

        double bSecond=Integer.parseInt(startHour)*3600+Integer.parseInt(startMin)*60;
        double eSecond=hour*3600+min*60+sec+bSecond;
        double limit=23*3600+59*60+59;
        if(eSecond<=limit){
            return false;
        }
        if(hour==0&&min==0&&sec==0){
            return false;
        }
        return true;
    }
}
