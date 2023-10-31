package DatabaseControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RenameTable {
    public static void rename(String tableName) throws SQLException {
        Connection con=ConnectDatabase.connection();
        Statement stmt = con.createStatement();
        char[] dateChar = tableName.toCharArray();
        dateChar[4] = '_';
        dateChar[7] = '_';
        tableName = String.valueOf(dateChar);
        tableName="d"+tableName;
        try{
            String sql = "ALTER TABLE Temp \n" +
                         "RENAME TO "+tableName+";";
            stmt.executeUpdate(sql);
            System.out.println("Table renamed in given database...");
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
