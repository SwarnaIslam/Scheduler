package IncompleteTask;

import DatabaseControl.ConnectDatabase;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class IncompleteListController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            displayIncomplete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private JFXListView<HBoxCell>listView=new JFXListView<>();
    @FXML
    private JFXTextField desField;
    @FXML
    private void showDescription(){
        try {
            String date=listView.getSelectionModel().getSelectedItem().getId().substring(0,10);
            String time=listView.getSelectionModel().getSelectedItem().getId().substring(10);
            Connection con=ConnectDatabase.connection();
            PreparedStatement ps=null;
            ResultSet rs=null;
            try{
                String sql="SELECT Description FROM IncompleteTask WHERE Time="+"\""+time+"\""+
                        " AND Date="+"\""+date+"\"";
                ps=con.prepareStatement(sql);
                rs= ps.executeQuery();
                String des=rs.getString("Description");
                desField.setText(des);
            }
            catch (SQLException e){
                System.out.println(e+"inCom:showDes");
            }
            finally {
                ps.close();
                rs.close();
                con.close();
            }

        }
        catch (NullPointerException | SQLException e){
            System.out.println(e+"listController");
        }
    }

    public void displayIncomplete() throws SQLException {

        Connection con= ConnectDatabase.connection();
        PreparedStatement ps=null;
        ResultSet rs=null;

        try{
            String sql="SELECT* FROM IncompleteTask ORDER BY Date";
            ps= con.prepareStatement(sql);
            rs= ps.executeQuery();

            while (rs.next()){
                String task=rs.getString("Task");
                String time=rs.getString("Time");
                String date=rs.getString("Date");
                String list=rs.getString("List");

                String strtHour=time.substring(0,2);
                String strtMin=time.substring(3);

                long bSecond=Long.parseLong(strtHour)*3600+Long.parseLong(strtMin)*60;

                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
                formatter.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));

                String initialTime = formatter.format(new Time(bSecond * 1000L));

                HBoxCell hBoxCell=new HBoxCell(task,list,initialTime,date);
                hBoxCell.setAlignment(Pos.CENTER);
                hBoxCell.setSpacing(30);
                hBoxCell.setId(date+time);

                listView.getItems().add(hBoxCell);
            }
        }
        catch (SQLException e){
            System.out.println(e+"IncompleteListCon");
        }
        finally {
            ps.close();
            rs.close();
            con.close();
        }
    }

    public class HBoxCell extends HBox{
        Label task=new Label();
        Label time=new Label();
        Label list=new Label();
        Label date=new Label();
        JFXCheckBox checkBox=new JFXCheckBox();
        HBoxCell(String taskTxt,String listTxt,String timeTxt,String dateTxt)
        {
            super();
            task.setText(taskTxt);
            time.setText(timeTxt);
            list.setText(listTxt);
            date.setText(dateTxt);

            task.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(task, Priority.ALWAYS);

            checkBox.setOnAction(e->{
                int index=listView.getSelectionModel().getSelectedIndex();
                if(index>=0){
                    String id=listView.getSelectionModel().getSelectedItem().getId();
                    String date=id.substring(0,10);
                    String time=id.substring(10);

                    Connection con=ConnectDatabase.connection();
                    Statement stmt=null;
                    try {
                        stmt=con.createStatement();
                        String sql="DELETE FROM IncompleteTask WHERE Date="+"\""+date+"\""+" AND "+
                                "Time="+"\""+time+"\"";
                        stmt.executeUpdate(sql);

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    finally {
                        try {
                            stmt.close();
                            con.close();
                        }
                        catch (SQLException exception) {
                            exception.printStackTrace();
                        }

                    }
                    listView.getItems().remove(index);

                }
            });
            this.getChildren().addAll(checkBox,task,list,time,date);
        }
    }
}
