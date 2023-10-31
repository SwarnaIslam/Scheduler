package AllTaskWindow;

import AddTaskToSchedule.AddTask;
import CtrlBetweenUiAndDB.TimeFormatter;
import DatabaseControl.ConnectDatabase;
import DatabaseControl.DeleteRow;
import EditBtnWindow.SetAndGet;
import MainClass.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AllTaskController implements Initializable {

    DeleteRow deleteRow;
    public static String tblName;
    public static String qry;

    @FXML
    private JFXListView jfxListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Connection con = ConnectDatabase.connection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT *FROM Date ORDER BY days";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String days=rs.getString("days");
                jfxListView.getItems().addAll(days);


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                ps.close();
                rs.close();
                con.close();
            } catch (SQLException|NullPointerException throwables) {
                throwables.printStackTrace();
            }

        }
    }
    @FXML
    private void addTask() throws SQLException, IOException {
        try {
            AddTask.addingTask(jfxListView.getSelectionModel().getSelectedItem().toString());
        }
        catch (NullPointerException e){
            System.out.println(e+"AllTaskController");
        }
    }
    @FXML
    private JFXListView<HBoxCell> unCheckedList = new JFXListView<>();
    @FXML
    private JFXListView<HBoxCell> checkedList = new JFXListView<>();

    @FXML
    private void displaySchedule() throws SQLException {
        String tableName;
        try {
            tableName = jfxListView.getSelectionModel().getSelectedItem().toString();
            unCheckedList.getItems().clear();
            checkedList.getItems().clear();

            Connection con = ConnectDatabase.connection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            String date = tableName;
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;

            try {
                String sql = "SELECT *FROM " + tableName+" ORDER BY Time";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String Task = rs.getString("Task");
                    String Time = rs.getString("Time");
                    String DuHour=rs.getString("DuHour");
                    String DuMin=rs.getString("DuMin");
                    String DuSec=rs.getString("DuSec");
                    String State = rs.getString("State");
                    if (State.compareTo("Checked") != 0) {
                        Image delImg = new Image("/AllTaskWindow/Image/trash.png");

                        ImageView delImgView = new ImageView(delImg);
                        delImgView.setFitHeight(20);
                        delImgView.setFitWidth(20);

                        Image editImg = new Image("/AllTaskWindow/Image/edit.png");

                        ImageView editImgView = new ImageView(editImg);
                        editImgView.setFitHeight(20);
                        editImgView.setFitWidth(20);

                        HBoxCell hBoxCell = new HBoxCell(Task, TimeFormatter.timeFormatting(Time,DuHour,DuMin,DuSec), editImgView, delImgView);
                        hBoxCell.setAlignment(Pos.CENTER);
                        hBoxCell.setSpacing(30);
                        hBoxCell.setId(date + Time);

                        unCheckedList.getItems().add(hBoxCell);

                    }
                    else {
                        HBoxCell hBoxCell = new HBoxCell(Task, date);
                        hBoxCell.setAlignment(Pos.CENTER);
                        hBoxCell.setSpacing(30);
                        hBoxCell.setId(date + Time);

                        checkedList.getItems().add(hBoxCell);
                    }
                }
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            finally {
                {
                    ps.close();
                    rs.close();
                    con.close();
                }
            }

        }
        catch (NullPointerException e){

        }

    }


    public class HBoxCell extends HBox {
        Main m;
        Label task = new Label();
        Label time=new Label();
        Label checkedTask=new Label();
        Label checkedDate=new Label();
        JFXButton editBtn=new JFXButton();
        JFXButton delBtn = new JFXButton();
        JFXCheckBox checkBox=new JFXCheckBox();

        HBoxCell(String labelText,String label2text, ImageView editImage,ImageView delImage) {
            super();

            task.setText(labelText);
            time.setText(label2text);
            task.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(task, Priority.ALWAYS);

            delBtn.setGraphic(delImage);
            editBtn.setGraphic(editImage);

            delBtn.setOnMouseClicked(e->{
                int index = unCheckedList.getSelectionModel().getSelectedIndex();

                if (index >= 0) {
                    String del= unCheckedList.getSelectionModel().getSelectedItem().getId();
                    unCheckedList.getItems().remove(index);

                    String tableName=del.substring(0,10);
                    String query=del.substring(10);

                    try {
                        deleteRow.delRow(tableName,"Time","\""+query+"\"");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            editBtn.setOnMouseClicked(e->{
                int index = unCheckedList.getSelectionModel().getSelectedIndex();

                if(index>=0){
                    try {
                        String edit= unCheckedList.getSelectionModel().getSelectedItem().getId();
                        String tableName=edit.substring(0,10);
                        String query=edit.substring(10);
                        tblName=tableName;
                        qry=query;
                        SetAndGet.setTableName(tblName);
                        SetAndGet.setQuery(qry);
                        m.editBtnWindow();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            checkBox.setOnAction(e->{
                int index = unCheckedList.getSelectionModel().getSelectedIndex();

                if(index>=0){
                    try {
                        String edit= unCheckedList.getSelectionModel().getSelectedItem().getId();
                        String tableName=edit.substring(0,10);
                        String query=edit.substring(10);
                        tblName=tableName;
                        qry=query;
                        char[] dateChar = tableName.toCharArray();
                        dateChar[4] = '_';
                        dateChar[7] = '_';
                        tableName = String.valueOf(dateChar);
                        tableName="d"+tableName;
                        Connection con=ConnectDatabase.connection();
                        Statement stmt = con.createStatement();
                        try {
                            String sql="UPDATE "+tableName
                                    +" SET State=\"Checked\""
                                    +" WHERE Time="+"\""+query+"\"";
                            stmt.executeUpdate(sql);
                            System.out.println("Updated table in given database...");
                            unCheckedList.getItems().remove(index);
                        }
                        catch (SQLException sqlexception){
                            System.out.println(sqlexception+"AllTaskCon");
                        }
                        finally {
                            {
                                stmt.close();
                                con.close();
                            }
                        }
                    } catch (Exception exception) {
                        System.out.println(exception+"AllTaskCon");
                    }

                }

            });
            this.getChildren().addAll(checkBox,task,time, editBtn,delBtn);
        }
        HBoxCell(String checkText, String checkDateText){
            super();
            checkedTask.setText(checkText);
            checkedDate.setText(checkDateText);
            checkedTask.setMaxWidth(Double.MAX_VALUE);
            checkBox.setSelected(true);

            HBox.setHgrow(checkedTask, Priority.ALWAYS);
            checkBox.setOnAction(e->{
                int index = checkedList.getSelectionModel().getSelectedIndex();

                if (index >= 0) {
                    try {
                        String edit = checkedList.getSelectionModel().getSelectedItem().getId();
                        String tableName = edit.substring(0, 10);
                        String query = edit.substring(10);
                        tblName = tableName;
                        qry = query;
                        char[] dateChar = tableName.toCharArray();
                        dateChar[4] = '_';
                        dateChar[7] = '_';
                        tableName = String.valueOf(dateChar);
                        tableName="d"+tableName;
                        Connection con = ConnectDatabase.connection();
                        Statement stmt;

                        stmt = con.createStatement();
                        try {
                            String sql = "UPDATE " + tableName
                                    + " SET State=\"Unchecked\""
                                    + " WHERE Time=" + "\"" + query + "\"";

                            stmt.executeUpdate(sql);
                            checkedList.getItems().remove(index);
                        } catch (SQLException sqlexception) {
                            System.out.println(e + "");
                        } finally {
                            {
                                stmt.close();
                                con.close();
                            }
                        }
                    }
                    catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            });

            this.getChildren().addAll(checkBox,checkedTask,checkedDate);
        }

    }
}
