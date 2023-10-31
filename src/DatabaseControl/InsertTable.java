package DatabaseControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTable {
    public static void insert(String tableName,String Task, String Description,String Time, String DuHour,String DuMin,
                              String DuSec,String Alarm,String State,String List) throws SQLException {
        Connection con=ConnectDatabase.connection();
        PreparedStatement ps;
        if(tableName.compareTo("Temp")!=0){
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;
        }
        try{
            String sql="INSERT INTO "+tableName+" (Task,Description,Time,DuHour,DuMin,DuSec,Alarm,State,List) VALUES(?,?,?,?,?,?,?,?,?)";
            ps=con.prepareStatement(sql);
            System.out.println(List);
            ps.setString(1,Task);
            ps.setString(2,Description);
            ps.setString(3,Time);
            ps.setString(4,DuHour);
            ps.setString(5,DuMin);
            ps.setString(6,DuSec);
            ps.setString(7,Alarm);
            ps.setString(8,State);
            ps.setString(9,List);
            ps.execute();
            System.out.println("Data has been inserted!");
        }
        catch (SQLException e){
            System.out.println(e+"Insert");
        }
        finally {
            con.close();

        }
    }
}
