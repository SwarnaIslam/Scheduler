package IncompleteTask;

import DatabaseControl.ConnectDatabase;

import java.sql.*;

public class IncompleteDB {
    public static String[] time=new String[1000];
    public static String[] task=new String[1000];
    public static String[] list=new String[1000];
    public static String[] description=new String[1000];
    public static int i=0;
    public static String date;
    public static void fetchIncomplete(String tableName) throws SQLException {
        date=tableName;
        char[] dateChar = tableName.toCharArray();
        dateChar[4] = '_';
        dateChar[7] = '_';
        tableName = String.valueOf(dateChar);
        tableName = "d" + tableName;

        Connection con= ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            String sql="SELECT* FROM "+tableName+" WHERE State=\"Unchecked\"";
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()&&i<1000){
                task[i]=rs.getString("Task");
                time[i]=rs.getString("Time");
                list[i]= rs.getString("List");
                description[i]=rs.getString("Description");
                i++;
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            ps.close();
            rs.close();
            con.close();
        }
        i--;
    }
    public static void putIncomplete()throws SQLException{
        while(i>=0) {
            Connection con = ConnectDatabase.connection();
            PreparedStatement ps = null;
            try {
                String sql = "INSERT INTO IncompleteTask(Task,Time,Description,List,Date) VALUES(?,?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, task[i]);
                ps.setString(2, time[i]);
                ps.setString(3, description[i]);
                ps.setString(4, list[i]);
                ps.setString(5, date);
                ps.execute();
            } catch (SQLException|NullPointerException e) {
                System.out.println(e+"");
            }
            finally {
                con.close();
            }
            i--;
        }
        i=0;
    }
}
