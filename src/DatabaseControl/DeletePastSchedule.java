package DatabaseControl;

import IncompleteTask.IncompleteDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

public class DeletePastSchedule {

    public static DeleteTable deleteTable;
    public static DeleteRow deleteRow;
    public static String[] dateStrArray =new String[1000];
    public static int i=0;

    public static void deletingTable()throws SQLException{
        Connection con=ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            String sql="SELECT* FROM Date";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();

            LocalDate localDate=LocalDate.now();

            while(rs.next()){

                String str=rs.getString("days");

                if(str.compareTo(localDate.toString())<0) {
                    dateStrArray[i] = str;
                    i++;
                }
            }
        }
        catch (SQLException e){
            System.out.println(e+"DeletePastSchedule");
        }
        finally {
            try{
                rs.close();
                ps.close();
                con.close();
            }
            catch (SQLException e){
                System.out.println(e+"");
            }
        }
        int j=i;
        while (j>0){
            IncompleteDB.fetchIncomplete(dateStrArray[j-1]);
            IncompleteDB.putIncomplete();
            deleteRow.delRow("Date","days","\""+dateStrArray[j-1]+"\"");
            j--;
        }
        while (i>0){
            try {
                deleteTable.delete(dateStrArray[i-1]);
                i--;
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println(e+"DeletePastSchedule");
            }
        }
    }
}
