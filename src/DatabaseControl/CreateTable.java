package DatabaseControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void createTable(String tableName) throws SQLException {
        if(tableName.compareTo("Temp")!=0) {
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;
        }
        Connection con=ConnectDatabase.connection();
        Statement stmt = con.createStatement();
        try{
            String sql = "CREATE TABLE " +tableName+" "+
                    "(Task TEXT not null, " +
                    " Description TEXT not null," +
                    "Time TEXT not null,"+
                    "DuHour TEXT not null,"+
                    "DuMin TEXT not null,"+
                    "DuSec TEXT not null,"+
                    "Alarm TEXT not null,"+
                    "State TEXT not null,"+
                    "List TEXT not null"+
                    ")";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
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
