package ListWindow;

import AllTaskWindow.AllTaskController;
import CtrlBetweenUiAndDB.FillDesField;
import CtrlBetweenUiAndDB.TimeFormatter;
import CtrlBetweenUiAndDB.UpdateList;
import DatabaseControl.ConnectDatabase;
import DatabaseControl.DeleteRow;
import EditBtnWindow.SetAndGet;
import MainClass.Main;
import MainWindow.MainController;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ListController extends MainController implements Initializable {
    public static String tblName;
    public static String qry;
    public static String listName;
    public String[] dates=new String[1000];
    public int j;
    @FXML
    private Label listNameLbl;
    @FXML
    private JFXComboBox action;
    @FXML
    private JFXTextField desField;
    @FXML
    private JFXListView<HBoxCell> unCheckedList =new JFXListView<HBoxCell>();
    @FXML
    private JFXListView<HBoxCell> checkedList =new JFXListView<HBoxCell>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listNameLbl.setText(getStr());
        listName=listNameLbl.getText();
        action.getItems().addAll("Rename","Delete");

        try {
            displayList();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    private void listActions(){
        action.getItems().addAll("Rename","Delete");
        try {
            String actionValue=action.getValue().toString();
            if (actionValue.compareTo("Rename") == 0) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setContentText("Enter Name:");

                dialog.showAndWait().ifPresent(text -> {
                    try {
                        UpdateList.renameList(text,listName);
                        UpdateList.listUpInTable(text,listName);
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                });
            }
            else{
                while (j>=0){
                    DeleteRow.delRow("ListCatalogue","Name","\""+listName+"\"");
                    DeleteRow.delRow(dates[j],"List","\""+listName+"\"" );
                    j--;
                }
            }
            action.getItems().clear();
        }
        catch (NullPointerException | SQLException event){

        }
    }
    @FXML
    private void showDescriptionOnClick() throws SQLException {
        try {
            String tableName=unCheckedList.getSelectionModel().getSelectedItem().getId().substring(0,10);
            String query=unCheckedList.getSelectionModel().getSelectedItem().getId().substring(10);
            desField.setText(FillDesField.fillDesField(tableName, query));
        }
        catch (NullPointerException e){
            System.out.println(e+"listController");
        }
    }
    public void displayList()throws SQLException{
        unCheckedList.getItems().clear();
        checkedList.getItems().clear();
        int i=0;

        Connection con = ConnectDatabase.connection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql="SELECT* FROM Date ORDER BY days";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();

            while (rs.next()){
                String str=rs.getString("days");
                dates[i++]=str;
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }

        System.out.println(i);
        i--;
        j=i;
        while (i>=0){
            String tableName=dates[i];
            char[] dateChar = tableName.toCharArray();
            dateChar[4] = '_';
            dateChar[7] = '_';
            tableName = String.valueOf(dateChar);
            tableName = "d" + tableName;

            try {
                String sql="SELECT* FROM "+tableName+" WHERE List="+"\""+listNameLbl.getText()+"\"";
                ps=con.prepareStatement(sql);
                rs=ps.executeQuery();
                while (rs.next()){
                    //System.out.println(tableName);
                    String Task=rs.getString("Task");
                    String Time=rs.getString("Time");
                    String DuHour=rs.getString("DuHour");
                    String DuMin=rs.getString("DuMin");
                    String DuSec=rs.getString("DuSec");
                    String State=rs.getString("State");

                    if(State.compareTo("Checked")!=0) {

                        Image delImg = new Image("/AllTaskWindow/Image/trash.png");

                        ImageView delImgView = new ImageView(delImg);
                        delImgView.setFitHeight(20);
                        delImgView.setFitWidth(20);

                        Image editImg = new Image("/AllTaskWindow/Image/edit.png");

                        ImageView editImgView = new ImageView(editImg);
                        editImgView.setFitHeight(20);
                        editImgView.setFitWidth(20);

                        HBoxCell hBoxCell = new HBoxCell(Task, TimeFormatter.timeFormatting(Time,DuHour,DuMin,DuSec),
                                dates[i], editImgView, delImgView);
                        hBoxCell.setAlignment(Pos.CENTER);
                        hBoxCell.setSpacing(30);
                        hBoxCell.setId(dates[i] + Time);
                        unCheckedList.getItems().add(hBoxCell);
                    }
                    else{
                        HBoxCell hBoxCell=new HBoxCell(Task,dates[i]);
                        hBoxCell.setAlignment(Pos.CENTER);
                        hBoxCell.setSpacing(30);
                        hBoxCell.setId(dates[i] + Time);

                        checkedList.getItems().add(hBoxCell);
                        System.out.println("DisplayCheckedList");
                    }

                }
            }
            catch (SQLException e){
                System.out.println(e);
            }
            finally {
                ps.close();
                rs.close();
            }
            i--;
        }
        con.close();
    }

    public class HBoxCell extends HBox {
        Main m;
        Label unCheckedTask = new Label();
        Label checkedTask=new Label();
        Label checkedDate=new Label();
        Label time=new Label();
        Label date=new Label();
        JFXButton editBtn=new JFXButton();
        JFXButton delBtn = new JFXButton();
        JFXCheckBox uncheckBox =new JFXCheckBox();
        JFXCheckBox checkBox=new JFXCheckBox();

        HBoxCell(String labelText, String label2text,String dateText, ImageView editImage, ImageView delImage) {
            super();

            unCheckedTask.setText(labelText);
            time.setText(label2text);
            date.setText(dateText);
            unCheckedTask.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(unCheckedTask, Priority.ALWAYS);

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
                        DeleteRow.delRow(tableName,"Time","\""+query+"\"");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    System.out.println(tableName+" "+query);
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
            uncheckBox.setOnAction(e->{
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
                        Connection con= ConnectDatabase.connection();
                        Statement stmt = con.createStatement();
                        try {
                            String sql="UPDATE "+tableName
                                    +" SET State=\"Checked\""
                                    +" WHERE Time="+"\""+query+"\"";
                            stmt.executeUpdate(sql);
                            unCheckedList.getItems().remove(index);
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
            this.getChildren().addAll(uncheckBox, unCheckedTask,time,date, editBtn,delBtn);
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
                        Statement stmt ;

                        stmt = con.createStatement();
                        try {
                            String sql = "UPDATE " + tableName
                                    + " SET State=\"Unchecked\""
                                    + " WHERE Time=" + "\"" + query + "\"";

                            stmt.executeUpdate(sql);
                            System.out.println("HomeCheckedList:Updated table in given database...");
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
