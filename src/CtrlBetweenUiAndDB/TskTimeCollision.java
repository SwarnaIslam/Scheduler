package CtrlBetweenUiAndDB;

import DatabaseControl.ConnectDatabase;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TskTimeCollision {
    public static boolean checkTaskTime(String tableName, String queryTime, String editingTime,
                                        String duHour, String duMin, String duSec,int flag) throws SQLException {
        if(tableName.compareTo("Temp")!=0){
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;
        }
        System.out.println(tableName);
        Connection con = ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String sql="";
            if(flag==1){
                sql = "SELECT* FROM " + tableName+" WHERE State!=\"Checked\"";
                ps=con.prepareStatement(sql);
            }
            if(flag==2) {
                sql = "SELECT* FROM " + tableName + " WHERE Time!=? AND State!=\"Checked\"";
                ps=con.prepareStatement(sql);
                ps.setString(1,queryTime);
            }
            rs= ps.executeQuery();
            while(rs.next()){

                String Time=rs.getString("Time");
                String DuHour=rs.getString("DuHour");
                String DuMin=rs.getString("DuMin");
                String DuSec= rs.getString("DuSec");
                System.out.println(queryTime);

                String strtHour=Time.substring(0,2);
                String strtMin=Time.substring(3);

                double bSecond=Integer.parseInt(strtHour)*3600+Integer.parseInt(strtMin)*60;
                double eSecond=Integer.parseInt(DuHour)*3600+Integer.parseInt(DuMin)*60+Integer.parseInt(DuSec)+bSecond;

                strtHour=editingTime.substring(0,2);
                strtMin=editingTime.substring(3);

                double ebSecond=Integer.parseInt(strtHour)*3600+Integer.parseInt(strtMin)*60;
                double eeSecond=Integer.parseInt(duHour)*3600+Integer.parseInt(duMin)*60+Integer.parseInt(duSec)+ebSecond;
                System.out.println(ebSecond+" "+eeSecond);

                if((ebSecond==bSecond&&eeSecond>bSecond)||(ebSecond<eSecond&&eeSecond==eSecond)||(ebSecond<bSecond&&eeSecond>eSecond)
                ||(ebSecond>bSecond&&eeSecond<eSecond)||(ebSecond>=bSecond&&eeSecond>eSecond&&ebSecond<eSecond)||
                        (ebSecond<bSecond&&eeSecond<=eSecond&&eeSecond>bSecond)){

                    System.out.println("Collision:"+Time);

                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Collision");

                    ButtonType buttonType=new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);

                    a.setContentText("You have set a task");
                    a.getButtonTypes().setAll(buttonType);

                    a.show();
                    return false;
                }
            }
        }
        catch (SQLException e){
            System.out.println(e+"TskTimeCol");
        }
        finally {
            {
                ps.close();
                rs.close();
                con.close();
            }
        }
        return true;
    }
}
