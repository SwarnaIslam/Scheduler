package DatabaseControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteRow {
    public static void delRow(String tableName,String columnName,String query)throws SQLException{
        Connection con=ConnectDatabase.connection();
        Statement stmt=con.createStatement();
        if(tableName.compareTo("Date")!=0&&tableName.compareTo("ListCatalogue")!=0){
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;
        }
        try{
            String sql="DELETE FROM "+tableName+" "+
                    "WHERE "+columnName+"="+query;
            stmt.executeUpdate(sql);
        }
        catch (SQLException e){
            System.out.println(e+"DeleteRow");
        }
        finally {
            stmt.close();
            con.close();
        }

    }
}
