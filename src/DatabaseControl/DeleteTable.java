package DatabaseControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteTable {
    public  static void delete(String table) throws SQLException {
        if(table.length()>4) {
            char[] dateChar = table.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            table = String.valueOf(dateChar);
            table = "d" + table;
        }
        Connection con=ConnectDatabase.connection();
        Statement stmt = con.createStatement();
        try{
            String sql = "DROP TABLE "+table;
            stmt.executeUpdate(sql);
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
                    System.out.println(e+"DeleteTable");
                }
            }
        }
    }
}
