package DatabaseControl;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTimePicker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTable {
    public static void update(JFXTextArea taskArea, JFXTextArea desArea, JFXTimePicker timePicker
            , JFXTextArea dh, JFXTextArea dm, JFXTextArea ds, JFXComboBox alarmOption,String State,String List,
                              String tableName, String query) throws SQLException {
        Connection con=ConnectDatabase.connection();
        Statement stmt = con.createStatement();
        char[] dateChar = tableName.toCharArray();
        dateChar[4] = '_';
        dateChar[7] = '_';
        tableName = String.valueOf(dateChar);
        tableName = "d" + tableName;
        try{
            System.out.println(query);
            String sql="UPDATE " +tableName+
                    " SET Task="+"\""+taskArea.getText()+"\""
                    +", Description=" +"\"" +desArea.getText() +"\""
                    + ", Time="+"\""+timePicker.getValue().toString() +"\""
                    +", DuHour="+"\""+dh.getText() +"\""
                    +", DuMin="+"\""+dm.getText() +"\""
                    +", DuSec="+"\""+ds.getText()+"\""
                    +", Alarm="+"\""+alarmOption.getValue().toString()+"\""
                    +", List="+"\""+List+"\""+
                    " WHERE Time="+"\""+query+"\"";

            stmt.executeUpdate(sql);
            System.out.println("Updated table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            {
                try{
                    stmt.close();
                    con.close();
                }
                catch (SQLException e){
                    System.out.println(e+"");
                }
            }
        }
    }
}
