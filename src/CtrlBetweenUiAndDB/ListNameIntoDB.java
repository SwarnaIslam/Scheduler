package CtrlBetweenUiAndDB;

import DatabaseControl.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ListNameIntoDB {
    public static void inserting(String listName) throws SQLException {
        Connection con= ConnectDatabase.connection();
        PreparedStatement ps;
        try{
            String sql="INSERT INTO ListCatalogue(Name) VALUES(?)";
            ps=con.prepareStatement(sql);
            ps.setString(1,listName);
            ps.execute();
        }
        catch (SQLException e){

        }
        finally {
            con.close();
        }

    }
}
