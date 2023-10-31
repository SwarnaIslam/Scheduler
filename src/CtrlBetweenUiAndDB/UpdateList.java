package CtrlBetweenUiAndDB;

import DatabaseControl.ConnectDatabase;

import java.sql.*;

public class UpdateList {
    public static void renameList(String listName,String prevName) throws SQLException {
        Connection con= ConnectDatabase.connection();
        Statement stmt = con.createStatement();
        try{
            String sql="UPDATE ListCatalogue\n"+
                    "SET Name="+"\""+listName+"\""+
                    " WHERE Name="+"\""+prevName+"\"";
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            System.out.println(e+"");
        }
        finally {
            stmt.close();
            con.close();
        }

    }
    public static void listUpInTable(String listName,String prevName)throws SQLException{
        String[] dates=new String[1000];
        int i=0;

        Connection con = ConnectDatabase.connection();
        Statement stmt= con.createStatement();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql="SELECT* FROM Date";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();

            while (rs.next()){
                String str=rs.getString("days");
                dates[i++]=str;
            }
        }
        catch (SQLException e){
            System.out.println(e+"");
        }
        finally {
            ps.close();
            rs.close();
        }
        i--;
        while (i>=0) {
            String tableName = dates[i];
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;
            try {
                String sql="UPDATE "+tableName+" SET List="+"\""+listName+"\""+" WHERE List="+"\""+prevName+"\"";
                stmt.executeUpdate(sql);
            }
            catch (SQLException e){
                System.out.println(e+"");
            }
            i--;

        }
    }
}
