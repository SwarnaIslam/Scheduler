package CtrlBetweenUiAndDB;

import DatabaseControl.ConnectDatabase;
import com.jfoenix.controls.JFXComboBox;

import java.sql.*;

public class InsertIntoListView {
    public static void insert(JFXComboBox<String> listCombo) throws SQLException {
        Connection con = ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            //listCombo.getItems().clear();
            String sql="SELECT DISTINCT Name FROM ListCatalogue WHERE Name!=\"Add List\"";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                String str=rs.getString("Name");
                listCombo.getItems().add(str);
            }
        }
        catch (SQLException e){

        }
        finally {
            try {
                ps.close();
                rs.close();
                con.close();
            }
            catch (Exception e){

            }
        }
    }
}
