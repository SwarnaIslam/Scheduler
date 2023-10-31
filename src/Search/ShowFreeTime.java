package Search;

import DatabaseControl.ConnectDatabase;
import DatabaseControl.DoesTableExist;
import com.jfoenix.controls.JFXListView;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ShowFreeTime {
    public static long time=0;
    public static void showing(String tableName, JFXListView<String>listView) throws SQLException {
        listView.getItems().clear();
        Connection con= ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        if(DoesTableExist.tableExistsSQL(con,tableName)){
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;
            try{
                String sql="SELECT* FROM "+tableName+" WHERE State=\"Unchecked\" ORDER BY Time";
                ps=con.prepareStatement(sql);
                rs=ps.executeQuery();
                while(rs.next()){
                    String Time=rs.getString("Time");
                    String DuHour=rs.getString("DuHour");
                    String DuMin=rs.getString("DuMin");
                    String DuSec= rs.getString("DuSec");

                    String strtHour=Time.substring(0,2);
                    String strtMin=Time.substring(3);

                    long bSecond=Long.parseLong(strtHour)*3600+Long.parseLong(strtMin)*60;
                    long eSecond=Long.parseLong(DuHour)*3600+Long.parseLong(DuMin)*60+Long.parseLong(DuSec)+bSecond;

                    if (time != bSecond) {
                        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                        formatter.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));

                        String initialTime = formatter.format(new Time(time * 1000L));
                        String endTime = formatter.format(new Time(bSecond * 1000L));

                        String showTime = "From " + initialTime + " to " + endTime;

                        listView.getItems().add(showTime);
                        time=eSecond;
                    }
                    time=eSecond;
                }
                if(time<2*43200){
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                    formatter.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));

                    String initialTime = formatter.format(new Time(time * 1000L));
                    String endTime="11:59 PM";

                    String showTime="From "+initialTime+" to "+endTime;
                    listView.getItems().add(showTime);
                }
            }
            catch (SQLException e){
                System.out.println(e+"ShowFreeTime");
            }
            finally {
                ps.close();
                rs.close();
                con.close();
            }
        }
        else{
            listView.getItems().add("You haven't made any schedule on this date");
        }
        time=0;
    }
}
