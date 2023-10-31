package CtrlBetweenUiAndDB;

import DatabaseControl.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FillDesField {
    public static String des;
    public static String fillDesField(String tableName,String query) throws SQLException {
        char[] dateChar = tableName.toCharArray();
        dateChar[4] = '_';
        dateChar[7] = '_';
        tableName = String.valueOf(dateChar);
        tableName="d"+tableName;
        Connection con= ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String sql="SELECT Description FROM "+tableName+" WHERE Time="+"\""+query+"\"";
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery();
            des=rs.getString("Description");

        }
        catch (SQLException e){
            System.out.println(e+" ");
        }
        finally {
            ps.close();
            rs.close();
            con.close();
        }

    return des;
    }
}
