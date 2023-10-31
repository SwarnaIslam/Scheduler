package AlarmControl;

import DatabaseControl.ConnectDatabase;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;


public class ControlAlarm {
    public static boolean r=false;
    public static long[] second=new long[1000];
    public static int j=0;
    public static void controlAlarm(String btn1Id) throws SQLException {
        String tableName=btn1Id;
        char[] dateChar = tableName.toCharArray();
        dateChar[4] = '_';
        dateChar[7] = '_';
        tableName = String.valueOf(dateChar);
        tableName = "d" + tableName;

        int i=0;
        Connection con= ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sql="SELECT* FROM "+tableName+" WHERE State=\"Unchecked\" ORDER BY Time";
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            while (rs.next()){
                String Time=rs.getString("Time");
                String Alarm=rs.getString("Alarm");
                String DuHour=rs.getString("DuHour");
                String DuMin=rs.getString("DuMin");
                String DuSec=rs.getString("DuSec");

                String strtHour=Time.substring(0,2);
                String strtMin=Time.substring(3);

                long milliSecond;
                long bSecond=Long.parseLong(strtHour)*3600+Long.parseLong(strtMin)*60;

                if(Alarm.compareTo("At the start")==0){
                    milliSecond=bSecond*1000;
                    second[i++]=milliSecond;
                }
                if(Alarm.compareTo("At the end")==0) {
                    milliSecond = Long.parseLong(DuHour) * 3600 + Long.parseLong(DuMin) * 60 + Long.parseLong(DuSec) + bSecond;
                    milliSecond=milliSecond*1000;
                    second[i++]=milliSecond;
                }
                j=i;
            }
        }
        catch (SQLException e){
            System.out.println(e+"");
        }
        finally {
            ps.close();
            rs.close();
            con.close();
        }



    }
    public static void runAlarm() throws IOException {

        int i=0;

        j--;
        while (i<=j){
            LocalTime localTime = LocalTime.now();

            int hour = localTime.getHour();
            int minute = localTime.getMinute();
            int seconds = localTime.getSecond();

            long currentMilliseconds=(seconds*1000)+(minute*1000*60)+(hour*1000*3600);
            long difference=second[i]-currentMilliseconds;

            System.out.println(difference);

            long start = System.currentTimeMillis();
            if(difference>0) {
                while (System.currentTimeMillis() - start < difference) {
                }
                File f = new File("D:\\OneDrive\\Education\\Teachers_course_task\\Raju sir_OOP\\projectSchedule\\Schedule\\src\\AlarmControl\\mixkit-children-happy-countdown-923.wav");
                Desktop dt = Desktop.getDesktop();
                dt.open(f);
            }

            i++;
        }

    }
}
