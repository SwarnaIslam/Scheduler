package HomeBtnWindow;

import AddTaskToSchedule.AddTask;
import AlarmControl.ControlAlarm;
import CtrlBetweenUiAndDB.TimeFormatter;
import DatabaseControl.ConnectDatabase;
import DatabaseControl.CreateTable;
import DatabaseControl.DeleteRow;
import DatabaseControl.DoesTableExist;
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

public class HomeController implements Initializable {
    Main m;
    RenameWeekBtn renameWeekBtn;
    CreateTable create;
    DeleteRow deleteRow;
    public static String btnId;
    public static String tblName;
    public static String qry;
    @FXML
    private JFXButton button1;
    @FXML
    private JFXButton button2;
    @FXML
    private JFXButton button3;
    @FXML
    private JFXButton button4;
    @FXML
    private JFXButton button5;
    @FXML
    private JFXButton button6;
    @FXML
    private JFXButton button7;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            renameWeekBtn.rename(button1,button2,button3,button4,button5,button6,button7);
            Connection con=ConnectDatabase.connection();
            if(DoesTableExist.tableExistsSQL(con,button1.getId())) {
                con.close();
                ControlAlarm.controlAlarm(button1.getId());
            }
            else {
                con.close();
            }
            Btn1Action();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddBtn() throws IOException, SQLException {
        create.createTable("Temp");
        m.showAddBtnWindow();
    }
    @FXML
    private void Btn1Action() throws SQLException {
        btnId =button1.getId();
        displaySchedule();
    }

    @FXML
    private void Btn2Action() throws SQLException {
        btnId =button2.getId();
        displaySchedule();
    }
    @FXML
    private void Btn3Action() throws SQLException {
        btnId =button3.getId();
        displaySchedule();
    }
    @FXML
    private void Btn4Action() throws SQLException {
        btnId =button4.getId();
        displaySchedule();
    }
    @FXML
    private void Btn5Action() throws SQLException {
        btnId =button5.getId();
        displaySchedule();
    }
    @FXML
    private void Btn6Action() throws SQLException {
        btnId =button6.getId();
        displaySchedule();
    }
    @FXML
    private void Btn7Action() throws SQLException {
        btnId =button7.getId();
        displaySchedule();
    }
    @FXML
    private void addTask() throws IOException, SQLException {
        AddTask.addingTask(btnId);
    }
    @FXML
    private JFXListView<CheckedHB>checkedList=new JFXListView<>();

    @FXML
    private JFXListView<HBoxCell> listView = new JFXListView<>();

    public class CheckedHB extends HBox{
        Label taskLbl =new Label();
        JFXCheckBox checkedBox =new JFXCheckBox();
        CheckedHB(String checkedText){
            super();
            taskLbl.setText(checkedText);
            taskLbl.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(taskLbl, Priority.ALWAYS);

            checkedBox.setSelected(true);

            checkedBox.setOnAction(e->{
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
                        Statement stmt ;

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
            this.getChildren().addAll(checkedBox, taskLbl);
        }
    }
    @FXML
    private void displaySchedule() throws SQLException {

        listView.getItems().clear();
        checkedList.getItems().clear();

        Connection con = ConnectDatabase.connection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (DoesTableExist.tableExistsSQL(con, btnId)) {
            try {
                String tableName=btnId;

                char[] dateChar = tableName.toCharArray();
                dateChar[4] = '_';
                dateChar[7] = '_';
                tableName = String.valueOf(dateChar);
                tableName="d"+tableName;

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
                        hBoxCell.setId(btnId + Time);

                        listView.getItems().add(hBoxCell);
                        System.out.println(Task);
                    }
                    else{
                        CheckedHB checkedHBCell = new CheckedHB(Task);
                        checkedHBCell.setAlignment(Pos.CENTER);
                        checkedHBCell.setSpacing(30);
                        checkedHBCell.setId(btnId + Time);

                        checkedList.getItems().add(checkedHBCell);
                        //System.out.println("DisplayCheckedList");
                    }

                }
            } catch(SQLException throwables){
                throwables.printStackTrace();
            } finally{
                {
                    ps.close();
                    rs.close();
                    con.close();
                }
            }

        }
    }
    public class HBoxCell extends HBox {
        Main m;
        Label task = new Label();
        Label time=new Label();
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
                int index = listView.getSelectionModel().getSelectedIndex();

                if (index >= 0) {
                    String del=listView.getSelectionModel().getSelectedItem().getId();
                    listView.getItems().remove(index);

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
                int index = listView.getSelectionModel().getSelectedIndex();

                if(index>=0){
                    try {
                        String edit=listView.getSelectionModel().getSelectedItem().getId();
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
                int index = listView.getSelectionModel().getSelectedIndex();

                if(index>=0){
                    try {
                        String edit=listView.getSelectionModel().getSelectedItem().getId();
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
                            listView.getItems().remove(index);
                        }
                        catch (SQLException sqlexception){
                            System.out.println(e+"");
                        }
                        finally {
                            {
                                stmt.close();
                                con.close();
                            }
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }

            });
            this.getChildren().addAll(checkBox,task,time, editBtn,delBtn);
        }

    }
}
