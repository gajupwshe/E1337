/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Narpat Singh
 */
public class AdminScreenController implements Initializable {

    Thread insert_Admin, m_mode;
    String current_machine_mode, current_offline_mode;

//    Columns for user table start
    JFXTreeTableColumn<Users, String> UserName = new JFXTreeTableColumn<>("Username");
    JFXTreeTableColumn<Users, String> Name = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<Users, String> Qualification = new JFXTreeTableColumn<>("Qualification");
    JFXTreeTableColumn<Users, String> UserType = new JFXTreeTableColumn<>("User Type");
//    Columns for user table end

//    Columns for Gauge table start
    JFXTreeTableColumn<Users, String> GS = new JFXTreeTableColumn<>("Serial No");
    JFXTreeTableColumn<Users, String> GD = new JFXTreeTableColumn<>("Gauge Description");
    JFXTreeTableColumn<Users, String> GR = new JFXTreeTableColumn<>("Gauge Range");
    JFXTreeTableColumn<Users, String> CD = new JFXTreeTableColumn<>("Calibration Date");
    JFXTreeTableColumn<Users, String> CDD = new JFXTreeTableColumn<>("Calibration Due Date");
//    Columns for Gauge table end

//    Columns for Customer table start
    JFXTreeTableColumn<Users, String> customerCode = new JFXTreeTableColumn<>("Code");
    JFXTreeTableColumn<Users, String> customerName = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<Users, String> customerDescription = new JFXTreeTableColumn<>("Description");
//    Columns for Customer table end

//    Columns for Manufacturer table start
    JFXTreeTableColumn<ManufacturerData, String> manufacturerCode = new JFXTreeTableColumn<>("Code");
    JFXTreeTableColumn<ManufacturerData, String> manufacturerName = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<ManufacturerData, String> manufacturerDescription = new JFXTreeTableColumn<>("Description");
//    Columns for Manufacturer table end

//    Columns for Project table start
    JFXTreeTableColumn<ProjectData, String> projectCode = new JFXTreeTableColumn<>("Code");
    JFXTreeTableColumn<ProjectData, String> projectName = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<ProjectData, String> projectDescription = new JFXTreeTableColumn<>("Description");
//    Columns for Project table end

    ToolKit toolkit = new ToolKit();

    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();

    @FXML
    private Text txtMode;
    private Text txtDate;
    private ImageView imgEmergency;
    private JFXButton btnHome;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private HBox sectionFooter;
    @FXML
    private JFXTreeTableView<Users> tblUser;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXTextField txtQualification;
    @FXML
    private JFXComboBox<String> cmbUserType;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnAddUpdate;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXTextField txtSearchGauge;

    @FXML
    private JFXTreeTableView<Users> tblGauge;

    @FXML
    private JFXTextField txtGuageSerial;

    @FXML
    private JFXButton btnAddUpdateGauge;

    @FXML
    private JFXButton btnDeleteGauge;

    @FXML
    private JFXButton btnClearGauge;

    @FXML
    private JFXButton btnCloseMessageGauge;

    @FXML
    private HBox BoxMessageGauge;

    @FXML
    private Text txtMessageGuage;

    @FXML
    private JFXDatePicker txtCalibrationDate;

    @FXML
    private JFXDatePicker txtCalibrationDueDate;

    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXButton btnCloseMessage;
    @FXML
    private HBox BoxMessage;
    @FXML
    private Text txtMessage;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private Tab tabUser;
    @FXML
    private Tab tabGauge;
    @FXML
    private JFXComboBox<String> cmbGuageDescription;
    @FXML
    private JFXTextField txtGuageRange;

    private ImageView imgAuto;
    private ImageView imgManual;

    private JFXTextField txtSearchCustomer;

    private JFXTreeTableView<Users> tblCMPCustomer;

    private JFXTextField txtCustomerName;

    private JFXButton btnAddUpdateCustomer;

    private JFXButton btnClearCustomer;

    private HBox BoxMessageCustomer;

    private Text txtMessageCustomer;

    private JFXTextField txtCustomerDescription;

    private JFXTextField txtSearchManufaturer;

    private JFXTreeTableView<ManufacturerData> tblCMPManufaturer;

    private JFXTextField txtManufaturerName;

    private JFXButton btnAddUpdateManufaturer;

    private JFXButton btnClearManufaturer;

    private HBox BoxMessageManufaturer;

    private Text txtMessageManufaturer;

    private JFXTextField txtManufaturerDescription;

    private JFXTextField txtSearchProject;

    private JFXTreeTableView<ProjectData> tblCMPProject;

    private JFXTextField txtProjectName;

    private JFXButton btnAddUpdateProject;

    private JFXButton btnClearProject;

    private HBox BoxMessageProject;

    private Text txtMessageProject;

    private JFXTextField txtProjectDescription;
    private JFXTextField txtCustomerCode;
    private JFXTextField txtManufaturerCode;
    private JFXTextField txtProjectCode;
    private Text txtErrorCCode;
    private Text txtErrorCName;
    private Text txtErrorCDescription;
    private Text txtErrorMCode;
    private Text txtErrorMName;
    private Text txtErrorMDescription;
    private Text txtErrorPCode;
    private Text txtErrorPName;
    private Text txtErrorPDescription;

    private volatile boolean stop_mode = false;
    @FXML
    private Text txtdate;
    @FXML
    private JFXButton btnLogin;
    private JFXButton btnAirPurging;
    @FXML
    private JFXButton btnAlarm;
    @FXML
    private JFXButton btnInitial;

    private void machine_mode() {
//        stop_mode = false;
//        m_mode = new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(150);
//                    if (stop_mode) {
//                        break;
//                    }
//                    String display = "SELECT machine_mode,offline_online FROM admin_report ORDER BY admin_report_id DESC LIMIT 1";
//                    ResultSet rs = dh.getData(display, connect);
//                    if (rs.next()) {
//                        if (rs.getString("offline_online").equals(current_offline_mode)) {
//                            //System.out.println("Offline not changed");
//                        } else {
//                            switch (rs.getString("offline_online")) {
//                                case "0":
//                                    txtOffline.setText("Online");
//                                    current_offline_mode = "0";
//                                    break;
//                                case "1":
//                                    txtOffline.setText("Offline");
//                                    current_offline_mode = "1";
//                                    break;
//                                default:
//                                    txtOffline.setText("Something went wrong");
//                                    current_offline_mode = "0";
//                                    break;
//                            }
//                            //System.out.println("Offline changed");
//                        }
//                        if (stop_mode) {
//                            break;
//                        }
//                        if (rs.getString("machine_mode").equals(current_machine_mode)) {
////                                System.out.println("mode not changed");
//                        } else {
//                            machine_mode_value(rs.getString("machine_mode"));
////                                System.out.println("mode changed");
//                        }
//                    }
//
//                } catch (InterruptedException | SQLException e) {
//
//                }
//                if (stop_mode) {
//                    break;
//                }
//            }
//        }, "machineModeThread");
//        m_mode.setDaemon(true);
//        m_mode.start();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
       
        //Initialize intial screen: Start
        Thread Initialize_Initial = new Thread(() -> {
            Background_Processes.Initialize_Initial_Screen();
            System.out.println("Initialized");
        }, "initializeIntialScreenThread");
        Initialize_Initial.setDaemon(true);
        Initialize_Initial.start();
        //Initialize intial screen: End

//        imgManual.setVisible(false);
//        imgAuto.setVisible(false);
//        imgEmergency.setVisible(false);
        current_machine_mode = "45";
        current_offline_mode = "45";

//        btnHome.setFocusTraversable(false);
//        btnInitial.setFocusTraversable(false);
//        btnReport.setFocusTraversable(false);
        //Thread Call INSERT_PLC_DATA : start
//        Background_Processes.insert_plc_data("python include/insert_admin_report.py " + DatabaseHandler.DB_HOST + " " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " truncate_admin_report_sp insert_admin_report_sp", false, true);
        //Thread Call INSERT_PLC_DATA : End
        //Thread Call machineMode : start
        machine_mode();
        //Thread Call machineMode : start

//        Background_Processes.date_time(txtDate, false, false,"adminScreenDateTimeThread");
        //Tab1: User registration: Start
        Initial_User_registration();
        //Tab1: User registration: End

        //Tab2: Gauge Start: Start
        Initial_Guage_table();
        //Tab2: Gauge Start: End

        //Tab2: Master List: Start
//        intialCMP();
        //Tab2: Master List: End
    }

    private void intialCMP() {
        txtManufaturerCode.setDisable(true);
//        Customer Section start
        //VALIDATION
        txtErrorCCode.setVisible(false);
        txtErrorCName.setVisible(false);
        txtErrorCDescription.setVisible(false);
        add_listener_to_textfield(txtCustomerName, txtErrorCName);
        add_listener_to_textfield(txtCustomerDescription, txtErrorCDescription);

        btnAddUpdateCustomer.setText("Add New");
        txtMessageCustomer.setVisible(false);
        BoxMessageCustomer.setVisible(false);
        btnClearCustomer.setVisible(false);

        customerCode.setPrefWidth(150);
        customerCode.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().one);

        customerName.setPrefWidth(150);
        customerName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().two);

        customerDescription.setPrefWidth(200);
        customerDescription.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().three);

        txtSearchCustomer.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPCustomer.setPredicate((TreeItem<Users> user) -> {
                Boolean flag = user.getValue().one.getValue().contains(newValue) || user.getValue().two.getValue().contains(newValue) || user.getValue().three.getValue().contains(newValue);
                return flag;
            });
        });

        refreshTableCustomer();
//        Customer Section end

//        Manufacturer Section start
        //VALIDATION START
        txtErrorMCode.setVisible(false);
        txtErrorMName.setVisible(false);
        txtErrorMDescription.setVisible(false);
        add_listener_to_textfield(txtManufaturerName, txtErrorMName);
        add_listener_to_textfield(txtManufaturerDescription, txtErrorMDescription);
        //VALIDATION END
        btnAddUpdateManufaturer.setText("Add New");
        txtMessageManufaturer.setVisible(false);
        BoxMessageManufaturer.setVisible(false);
        btnClearManufaturer.setVisible(false);

        manufacturerCode.setPrefWidth(150);
        manufacturerCode.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().code);

        manufacturerName.setPrefWidth(150);
        manufacturerName.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().name);

        manufacturerDescription.setPrefWidth(200);
        manufacturerDescription.setCellValueFactory((TreeTableColumn.CellDataFeatures<ManufacturerData, String> param) -> param.getValue().getValue().description);

        txtSearchManufaturer.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPManufaturer.setPredicate((TreeItem<ManufacturerData> user) -> {
                Boolean flag = user.getValue().code.getValue().contains(newValue) || user.getValue().name.getValue().contains(newValue) || user.getValue().description.getValue().contains(newValue);
                return flag;
            });
        });

        refreshTableManufacturer();
//        Manufacturer Section end 

//        Project Section start
        //VALIDATION START
        txtErrorPCode.setVisible(false);
        txtErrorPName.setVisible(false);
        txtErrorPDescription.setVisible(false);
        add_listener_to_textfield(txtProjectName, txtErrorPName);
        add_listener_to_textfield(txtProjectDescription, txtErrorPDescription);
        //VALIDATION END
        btnAddUpdateProject.setText("Add New");
        txtMessageProject.setVisible(false);
        BoxMessageProject.setVisible(false);
        btnClearProject.setVisible(false);

        projectCode.setPrefWidth(150);
        projectCode.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().code);

        projectName.setPrefWidth(150);
        projectName.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().name);

        projectDescription.setPrefWidth(200);
        projectDescription.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProjectData, String> param) -> param.getValue().getValue().description);

        txtSearchProject.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblCMPProject.setPredicate((TreeItem<ProjectData> user) -> {
                Boolean flag = user.getValue().code.getValue().contains(newValue) || user.getValue().name.getValue().contains(newValue) || user.getValue().description.getValue().contains(newValue);
                return flag;
            });
        });
        refreshTableProject();
//        Project Section end
    }

    /*
    *
     */
    private void refreshTableCustomer() {
        btnAddUpdateCustomer.setText("Add New");
        btnClearCustomer.setVisible(false);

        ObservableList<Users> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM tbl_customer;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                users.add(new Users(rs.getString("customer_code"), rs.getString("customer_name"), rs.getString("customer_description")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<Users> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPCustomer.getColumns().setAll(customerCode, customerName, customerDescription);
        tblCMPCustomer.setRoot(root);
        tblCMPCustomer.setShowRoot(false);

        tblCMPCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String code = tblCMPCustomer.getSelectionModel().selectedItemProperty().get().getValue().one.get().replaceAll("'", "\\\\'");
                String q = "SELECT * FROM tbl_customer WHERE customer_code = '" + code + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtCustomerCode.setText(rs1.getString("customer_code"));
                        txtCustomerName.setText(rs1.getString("customer_name"));
                        txtCustomerDescription.setText(rs1.getString("customer_description"));
                    }
                    btnAddUpdateCustomer.setText("Update");
                    btnClearCustomer.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    /*
    *
     */
    private void refreshTableManufacturer() {
        btnAddUpdateManufaturer.setText("Add New");
        btnClearManufaturer.setVisible(false);

        ObservableList<ManufacturerData> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM tbl_manufacturer;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                users.add(new ManufacturerData(rs.getString("manufacturer_code"), rs.getString("manufacturer_name"), rs.getString("manufacturer_description")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<ManufacturerData> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPManufaturer.getColumns().setAll(manufacturerCode, manufacturerName, manufacturerDescription);
        tblCMPManufaturer.setRoot(root);
        tblCMPManufaturer.setShowRoot(false);

        tblCMPManufaturer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String code = tblCMPManufaturer.getSelectionModel().selectedItemProperty().get().getValue().code.get().replaceAll("'", "\\\\'");

                String q = "SELECT * FROM tbl_manufacturer WHERE manufacturer_code = '" + code + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtManufaturerCode.setText(rs1.getString("manufacturer_code"));
                        txtManufaturerName.setText(rs1.getString("manufacturer_name"));
                        txtManufaturerDescription.setText(rs1.getString("manufacturer_description"));
                    }
                    btnAddUpdateManufaturer.setText("Update");
                    btnClearManufaturer.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    /*
    *
     */
    private void refreshTableProject() {
        btnAddUpdateProject.setText("Add New");
        btnClearProject.setVisible(false);

        ObservableList<ProjectData> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM tbl_project;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                users.add(new ProjectData(rs.getString("project_code"), rs.getString("project_name"), rs.getString("project_description")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<ProjectData> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblCMPProject.getColumns().setAll(projectCode, projectName, projectDescription);
        tblCMPProject.setRoot(root);
        tblCMPProject.setShowRoot(false);

        tblCMPProject.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String code = tblCMPProject.getSelectionModel().selectedItemProperty().get().getValue().code.get().replaceAll("'", "\\\\'");
                String q = "SELECT * FROM tbl_project WHERE project_code = '" + code + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtProjectCode.setText(rs1.getString("project_code"));
                        txtProjectName.setText(rs1.getString("project_name"));
                        txtProjectDescription.setText(rs1.getString("project_description"));
                    }
                    btnAddUpdateProject.setText("Update");
                    btnClearProject.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    private void machine_mode_value(String mode) {
        switch (mode) {
            case "0":
                txtMode.setText("Auto Mode");
                current_machine_mode = "0";
                txtDate.setFill(Color.web("#0099FF"));
                imgEmergency.setVisible(false);
                imgManual.setVisible(false);
                imgAuto.setVisible(true);
                break;
            case "1":
                txtMode.setText("Manual Mode");
                current_machine_mode = "1";
                txtDate.setFill(Color.web("#0099FF"));
                imgEmergency.setVisible(false);
                imgManual.setVisible(true);
                imgAuto.setVisible(false);
                break;
            case "2":
                txtMode.setText("Emergency Mode");
                current_machine_mode = "2";
                txtDate.setFill(Color.web("#C32420"));
                imgEmergency.setVisible(true);
                imgManual.setVisible(false);
                imgAuto.setVisible(false);
                break;
            default:
                txtMode.setText("Something wrong");
                current_machine_mode = "0";
                txtDate.setFill(Color.web("#C32420"));
                imgEmergency.setVisible(true);
                imgManual.setVisible(false);
                imgAuto.setVisible(false);
                break;
        }
    }

    public void Initial_User_registration() {

        cmbUserType.getItems().add(0, "operator");
        cmbUserType.getItems().add(1, "admin");
        btnAddUpdate.setText("Add New");
        txtMessage.setVisible(false);
        BoxMessage.setVisible(false);
        btnCloseMessage.setVisible(false);
        btnClear.setVisible(false);

        UserName.setPrefWidth(150);
        UserName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().one);

        Name.setPrefWidth(200);
        Name.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().two);

        Qualification.setPrefWidth(250);
        Qualification.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().three);

        UserType.setPrefWidth(150);
        UserType.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().four);
        refresh_table_user_registration();

        txtSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblUser.setPredicate((TreeItem<Users> user) -> {
                Boolean flag = user.getValue().one.getValue().contains(newValue) || user.getValue().two.getValue().contains(newValue) || user.getValue().three.getValue().contains(newValue) || user.getValue().four.getValue().contains(newValue);
                return flag;
            });
        });
    }

    public void refresh_table_user_registration() {
        btnAddUpdate.setText("Add New");
        btnClear.setVisible(false);
        ObservableList<Users> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM User_data WHERE operator_id != 1;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                users.add(new Users(rs.getString("username"), rs.getString("name"), rs.getString("qualification"), rs.getString("user_type")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<Users> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        tblUser.getColumns().setAll(UserName, Name, Qualification, UserType);
        tblUser.setRoot(root);
        tblUser.setShowRoot(false);

        tblUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String username = tblUser.getSelectionModel().selectedItemProperty().get().getValue().one.get();
                String q = "SELECT * FROM User_data WHERE username = '" + username + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    if (rs1.next()) {
                        txtName.setText(rs1.getString("name"));
                        txtUserName.setText(rs1.getString("username"));
                        txtPassword.setText(rs1.getString("password"));
                        txtQualification.setText(rs1.getString("qualification"));
                        if (rs1.getString("user_type").equals("admin")) {
                            cmbUserType.getSelectionModel().select(1);
                        } else {
                            cmbUserType.getSelectionModel().select(0);
                        }
                    }
                    btnAddUpdate.setText("Update");
                    btnClear.setVisible(true);
                    txtUserName.setEditable(false);
                } catch (SQLException e) {
                }
            }
        });

    }

    public void Initial_Guage_table() {
        txtGuageRange.setEditable(false);
        String qu = "SELECT * FROM `gauge_description` GROUP BY `index` ASC";
        try {
            ResultSet rs = dh.getData(qu, connect);
            while (rs.next()) {
                cmbGuageDescription.getItems().add(Integer.parseInt(rs.getString("index")), rs.getString("gauge_description"));
            }
        } catch (NumberFormatException | SQLException e) {
        }
        btnAddUpdateGauge.setText("Add New");
        txtMessageGuage.setVisible(false);
        BoxMessageGauge.setVisible(false);
        btnCloseMessageGauge.setVisible(false);
        btnClearGauge.setVisible(false);

        GS.setPrefWidth(200);
        GS.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().one);

        GD.setPrefWidth(200);
        GD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().two);

        CD.setPrefWidth(150);
        CD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().three);

        CDD.setPrefWidth(150);
        CDD.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().four);

        GR.setPrefWidth(200);
        GR.setCellValueFactory((TreeTableColumn.CellDataFeatures<Users, String> param) -> param.getValue().getValue().five);

        refresh_table_Guage();

        txtSearchGauge.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            tblGauge.setPredicate((TreeItem<Users> guage) -> {
                Boolean flag = guage.getValue().one.getValue().contains(newValue) || guage.getValue().two.getValue().contains(newValue) || guage.getValue().three.getValue().contains(newValue) || guage.getValue().four.getValue().contains(newValue) || guage.getValue().five.getValue().contains(newValue);
                return flag;
            });
        });
    }

    private void refresh_table_Guage() {
        btnAddUpdateGauge.setText("Add New");
        btnClearGauge.setVisible(false);
        ObservableList<Users> gauge = FXCollections.observableArrayList();
        String query = "SELECT gda.*,gd.gauge_range FROM gauge_data gda LEFT JOIN gauge_description gd ON gd.gauge_description = gda.description;";

        try {
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {
                gauge.add(new Users(rs.getString("serial"), rs.getString("description"), rs.getString("c_date"), rs.getString("c_due_date"), rs.getString("gauge_range")));
            }

        } catch (SQLException e) {
        }

        final TreeItem<Users> root = new RecursiveTreeItem<>(gauge, RecursiveTreeObject::getChildren);
        tblGauge.getColumns().setAll(GS, GD, GR, CD, CDD);
        tblGauge.setRoot(root);
        tblGauge.setShowRoot(false);

        tblGauge.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String serial = tblGauge.getSelectionModel().selectedItemProperty().get().getValue().one.get();
                String q = "SELECT gda.*,gd.index,gd.gauge_range FROM gauge_data gda JOIN gauge_description gd ON gd.gauge_description = gda.description WHERE serial = '" + serial + "';";
                try {
                    ResultSet rs1 = dh.getData(q, connect);
                    txtCalibrationDate.setValue(null);
                    txtCalibrationDueDate.setValue(null);
                    if (rs1.next()) {
                        txtGuageSerial.setText(rs1.getString("serial"));
                        cmbGuageDescription.getSelectionModel().select(Integer.parseInt(rs1.getString("index")));
                        txtGuageRange.setText(rs1.getString("gauge_range"));
                        txtCalibrationDate.setValue(LocalDate.parse(rs1.getString("c_date")));
                        txtCalibrationDueDate.setValue(LocalDate.parse(rs1.getString("c_due_date")));
                    }
                    btnAddUpdateGauge.setText("Update");
                    btnClearGauge.setVisible(true);
                } catch (SQLException e) {
                }
            }
        });
    }

    public void dropbox(String screen, boolean Initial_check) {
        try {
            try {
//                Background_Processes.stop_plc_read();
            } catch (Exception e) {
                System.out.println("Exception while stoping insert_plc_thread_Admin_Screen : " + e.getMessage());
            }
            try {
//                Background_Processes.stop_date_time();
            } catch (Exception e) {
                System.out.println("Exception while stoping date_time_thread_Admin_Screen : " + e.getMessage());
            }
            stop_mode = true;
//            if (Initial_check) {
//                Background_Processes.Initialize_Initial_Screen();
//            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource(screen));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnHome);

        } catch (Exception ex) {
            Logger.getLogger(AdminScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnHomeAction(ActionEvent event) {
        
        
    }

    @FXML
    private void btnReportAction(ActionEvent event) {
        dropbox("Report.fxml", false);
    }

    @FXML
    private void btnAdminAction(ActionEvent event) {
//        dropbox("AdminScreen.fxml", false);
    }

    @FXML
    private void btnInitialAction(ActionEvent event) {
        dropbox("TestScreen.fxml", true);
    }

    @FXML
    private void btnAddUpdateAction(ActionEvent event) {
        String name = txtName.getText();
        String username = txtUserName.getText();
        String password = AES256.encrypt(txtPassword.getText());
        String qualification = txtQualification.getText();
        String usertype = cmbUserType.getSelectionModel().getSelectedItem();
        if (name == null || name.equals("") || username == null || username.equals("") || password == null || password.equals("") || qualification == null || qualification.equals("") || usertype == null || usertype.equals("")) {
            if (name == null || name.equals("")) {
                txtName.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtName.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (username == null || username.equals("")) {
                txtUserName.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (qualification == null || qualification.equals("")) {
                txtQualification.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (password == null || password.equals("")) {
                txtPassword.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            }
            if (usertype == null || usertype.equals("")) {
                cmbUserType.setUnFocusColor(Paint.valueOf("#FF0808"));
            } else {
                cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            }

        } else {
            cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            txtName.setUnFocusColor(Paint.valueOf("#000000"));
            txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
            txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
            if (btnAddUpdate.getText().equals("Add New")) {
                String sp = " insert_user_data_sp('" + username + "','" + password + "','" + name + "','" + qualification + "','" + usertype + "')";
                try {
                    if (dh.execute_sp(sp, connect) > 0) {
                        txtName.setText("");
                        txtPassword.setText("");
                        txtUserName.setText("");
                        txtQualification.setText("");
                        cmbUserType.getSelectionModel().clearSelection();
                        refresh_table_user_registration();
                        messageUser("User Added Successfully :)", "#90EE02");
                    } else {
                        messageUser("User not Added, Please check all paramenters !!!!", "#FF0808");
                    }
                } catch (SQLException e) {
                    messageUser(String.valueOf(e.getMessage()), "#FF0808");
                }
            } else {
                String sp = " update_user_data_sp('" + username + "','" + password + "','" + name + "','" + qualification + "','" + usertype + "')";
                try {
                    if (dh.execute_sp(sp, connect) > 0) {
                        txtName.setText("");
                        txtPassword.setText("");
                        txtUserName.setText("");
                        txtQualification.setText("");
                        cmbUserType.getSelectionModel().clearSelection();
                        refresh_table_user_registration();
                        btnAddUpdate.setText("Add New");
                        txtUserName.setEditable(true);
                        btnClear.setVisible(false);
                        messageUser("User Updated Successfully :)", "#90EE02");

                    } else {
                        messageUser("User not Updated, Please check all paramenters", "#FF0808");
                    }

                } catch (SQLException e) {
                    messageUser(String.valueOf(e.getMessage()), "#FF0808");
                }
            }
        }
    }

    @FXML
    private void btnDeleteAction(ActionEvent event) {
        String username = txtUserName.getText();
        if (username == null || username.equals("")) {
            txtUserName.setUnFocusColor(Paint.valueOf("#FF0808"));
            cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            txtName.setUnFocusColor(Paint.valueOf("#000000"));
            txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
        } else {
            cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
            txtName.setUnFocusColor(Paint.valueOf("#000000"));
            txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
            txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
            txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
            String sp = "DELETE FROM `User_data` WHERE `username` ='" + username + "';";
            try {
                if (dh.execute(sp, connect) > 0) {
                    txtName.setText("");
                    txtPassword.setText("");
                    txtUserName.setText("");
                    txtQualification.setText("");
                    cmbUserType.getSelectionModel().clearSelection();
                    txtUserName.setEditable(true);
                    btnClear.setVisible(false);
                    btnAddUpdate.setText("Add New");
                    messageUser("User Deleted Successfully :)", "#90EE02");
                    refresh_table_user_registration();
                } else {
                    messageUser("User not exist!!!!", "#FF0808");
                }
            } catch (SQLException e) {
                messageUser(String.valueOf(e.getMessage()), "#FF0808");
            }

        }
    }

    @FXML
    private void btnClearAction(ActionEvent event) {

        txtName.setText("");
        txtPassword.setText("");
        txtUserName.setText("");
        txtQualification.setText("");
        cmbUserType.getSelectionModel().clearSelection();
        cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
        txtName.setUnFocusColor(Paint.valueOf("#000000"));
        txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
        txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
        txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
        btnAddUpdate.setText("Add New");
        btnClear.setVisible(false);
        txtUserName.setEditable(true);
        refresh_table_user_registration();
    }

    @FXML
    private void btnCloseMessageAction(ActionEvent event) {
        txtMessage.setVisible(false);
        BoxMessage.setVisible(false);
        btnCloseMessage.setVisible(false);
    }

    @FXML
    private void tabUserAction(Event event) {
        refresh_table_user_registration();
        txtName.setText("");
        txtPassword.setText("");
        txtUserName.setText("");
        txtQualification.setText("");
        cmbUserType.getSelectionModel().clearSelection();
        cmbUserType.setUnFocusColor(Paint.valueOf("#000000"));
        txtName.setUnFocusColor(Paint.valueOf("#000000"));
        txtPassword.setUnFocusColor(Paint.valueOf("#000000"));
        txtQualification.setUnFocusColor(Paint.valueOf("#000000"));
        txtUserName.setUnFocusColor(Paint.valueOf("#000000"));
    }

    @FXML
    private void tabGaugeAction(Event event) {
        refresh_table_Guage();
        txtGuageSerial.setText("");
        txtGuageRange.setText("");
        cmbGuageDescription.getSelectionModel().clearSelection();
        txtCalibrationDate.getEditor().clear();
        txtCalibrationDueDate.getEditor().clear();
        btnClearGauge.setVisible(false);
        txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
        cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
    }

    @FXML
    void btnDeleteGaugeAction(ActionEvent event) {
        String serial = txtGuageSerial.getText();
        if (serial == null || serial.equals("")) {
            txtGuageSerial.setUnFocusColor(Paint.valueOf("#FF0808"));
            cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
        } else {
            txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
            cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
            String sp = "DELETE FROM `gauge_data` WHERE `serial` ='" + serial + "';";
            try {
                if (dh.execute(sp, connect) > 0) {
                    txtGuageSerial.setText("");
                    txtGuageRange.setText("");
                    cmbGuageDescription.getSelectionModel().clearSelection();
                    txtCalibrationDate.getEditor().clear();
                    txtCalibrationDueDate.getEditor().clear();
//                    txtGuageSerial.setEditable(true);
                    btnClearGauge.setVisible(false);
                    btnAddUpdateGauge.setText("Add New");
                    messageGuage("Gauge Deleted Successfully :)", "#90EE02");
                    refresh_table_Guage();
                } else {
                    messageGuage("Gauge not exist!!!!", "#FF0808");
                }
            } catch (Exception e) {
                messageGuage(String.valueOf(e.getMessage()), "#FF0808");
            }

        }
    }

    @FXML
    void btnCloseMessageGaugeAction(ActionEvent event) {
        txtMessageGuage.setVisible(false);
        BoxMessageGauge.setVisible(false);
        btnCloseMessageGauge.setVisible(false);
    }

    @FXML
    void btnClearGaugeAction(ActionEvent event) {
        txtGuageSerial.setText("");
        txtGuageRange.setText("");
        cmbGuageDescription.getSelectionModel().clearSelection();
        txtCalibrationDate.getEditor().clear();
        txtCalibrationDueDate.getEditor().clear();
        txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
        cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
        btnAddUpdateGauge.setText("Add New");
        btnClearGauge.setVisible(false);
//        txtGuageSerial.setEditable(true);
        refresh_table_Guage();
    }

    @FXML
    void btnAddUpdateGaugeAction(ActionEvent event) {
        String serial = txtGuageSerial.getText();
        String description = cmbGuageDescription.getSelectionModel().getSelectedItem();
        String date = String.valueOf(txtCalibrationDate.getValue());
        String duedate = String.valueOf(txtCalibrationDueDate.getValue());
        if (serial.equals("null") || serial.equals("") || description.equals("null") || description.equals("") || date.equals("null") || date.equals("") || duedate.equals("null") || duedate.equals("")) {
            messageGuage("All Felds are mendatory,Please provide appropriate data !!!!!", "#FF0808");

        } else {
            txtGuageSerial.setUnFocusColor(Paint.valueOf("#000000"));
            cmbGuageDescription.setUnFocusColor(Paint.valueOf("#000000"));
            if (btnAddUpdateGauge.getText().equals("Add New")) {
                String sp = " insert_gauge_data_sp('" + serial + "','" + description + "','" + date + "','" + duedate + "')";
                try {
                    if (dh.execute_sp(sp, connect) > 0) {
                        txtGuageSerial.setText("");
                        txtGuageRange.setText("");
                        cmbGuageDescription.getSelectionModel().clearSelection();
                        txtCalibrationDate.getEditor().clear();
                        txtCalibrationDueDate.getEditor().clear();
                        refresh_table_Guage();
                        messageGuage("Guage Added Successfully :)", "#90EE02");
                    } else {
                        messageGuage("Gauge not Added, Please check all paramenters !!!!", "#FF0808");
                    }
                } catch (SQLException e) {
                    messageGuage(String.valueOf(e.getMessage()), "#FF0808");
                }
            } else {
                String sp = " update_gauge_data_sp('" + serial + "','" + description + "','" + date + "','" + duedate + "')";
                try {
                    if (dh.execute_sp(sp, connect) > 0) {
                        txtGuageSerial.setText("");
                        txtGuageRange.setText("");
                        cmbGuageDescription.getSelectionModel().clearSelection();
                        txtCalibrationDate.getEditor().clear();
                        txtCalibrationDueDate.getEditor().clear();
                        refresh_table_Guage();
                        btnAddUpdateGauge.setText("Add New");
//                        txtGuageSerial.setEditable(true);
                        btnClearGauge.setVisible(false);
                        messageGuage("Gauge Updated Successfully :)", "#90EE02");

                    } else {
                        messageGuage("Gauge not Updated, Please check all paramenters", "#FF0808");
                    }

                } catch (SQLException e) {
                    messageGuage(String.valueOf(e.getMessage()), "#FF0808");
                }
            }
        }
    }

    @FXML
    private void cmbGuageDescriptionAction(ActionEvent event) {
        String desc = cmbGuageDescription.getSelectionModel().getSelectedItem();
        String query = "SELECT gauge_range FROM gauge_description WHERE gauge_description = '" + desc + "';";
        try {
            ResultSet rs = dh.getData(query, connect);
            if (rs.next()) {
                txtGuageRange.setText(rs.getString("gauge_range"));
            }
        } catch (Exception e) {
            messageGuage(String.valueOf(e.getMessage()), "#FF0808");
        }
    }

    private void btnSystemCheckAction(ActionEvent event) {
        dropbox("AlarmScreen.fxml", false);
    }

    private void btnAddUpdateCustomerAction(ActionEvent event) {
        String cName = txtCustomerName.getText().replaceAll("'", "\\\\'");
        String cDescription = txtCustomerDescription.getText().replaceAll("'", "\\\\'");
        if (cName == null || cName.equals("") || cDescription == null || cDescription.equals("")) {
            if (cName == null || cName.equals("")) {
                txtErrorCName.setVisible(true);
            } else {
                txtErrorCName.setVisible(false);
            }
            if (cDescription == null || cDescription.equals("")) {
                txtErrorCDescription.setVisible(true);
            } else {
                txtErrorCDescription.setVisible(false);
            }
        } else {
            if (btnAddUpdateCustomer.getText().equals("Add New")) {
                try {
                    //Check if user is exist
                    ResultSet check_rs = dh.getData("SELECT * FROM tbl_customer ORDER BY `customer_id` DESC LIMIT 1;", connect);
                    if (check_rs.next()) {
                        ResultSet get_prefix = dh.getData("SELECT code_format_prefix FROM tbl_code_format WHERE code_format_prefix_for = 'customer';", connect);
                        if (get_prefix.next()) {
                            String[] split = check_rs.getString("customer_code").split(get_prefix.getString("code_format_prefix"));
                            int cCodePre = Integer.parseInt(split[1]);
                            int cCodePost = cCodePre + 1;
                            String cCode = get_prefix.getString("code_format_prefix") + cCodePost;
                            int executed = dh.execute_sp(" insert_tbl_cutomer_sp('" + cCode + "','" + cCode + "','" + cDescription + "')", connect);
                            if (executed == 1) {
                                setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Customer Added Successfully :)", Colors.msgGreen);
                            } else {
                                setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                            }
                        }
                    } else {
                        ResultSet get_prefix = dh.getData("SELECT code_format_prefix FROM tbl_code_format WHERE code_format_prefix_for = 'customer';", connect);
                        if (get_prefix.next()) {
                            String prefix = get_prefix.getString("code_format_prefix");
                            String cCode = prefix + 1;
                            int executed = dh.execute_sp("insert_tbl_cutomer_sp('" + cCode + "','" + cCode + "','" + cDescription + "')", connect);
                            if (executed == 1) {
                                setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Customer Updated Successfully :)", Colors.msgGreen);
                            } else {
                                setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, ex.getMessage() + ", please contact Admin :)", Colors.msgRed);
                }
            } else {
                try {
                    String cCode = txtCustomerCode.getText().replaceAll("'", "\\\\'");
                    //Check if user is exist
                    int executed = dh.execute_sp("update_tbl_customer_sp('" + cCode + "','" + cName + "','" + cDescription + "')", connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Customer Added Successfully :)", Colors.msgGreen);
                    } else {
                        setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtCustomerCode, txtCustomerName, txtCustomerDescription, btnAddUpdateCustomer, btnClearCustomer);
            refreshTableCustomer();
            txtErrorCName.setVisible(false);
            txtErrorCDescription.setVisible(false);
        }
    }

    /*
    *@params txtMsg
    *@params msgBox
    *@params msg
    *@params Color_Code
     */
    private void setMessageForResult(Text txtMsg, HBox msgBox, String msg, String Color_Code) {
        txtMsg.setText(msg);
        msgBox.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        txtMsg.setVisible(true);
        msgBox.setVisible(true);
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent ev) -> {
            txtMsg.setVisible(false);
            msgBox.setVisible(false);
        }));
        timer.setCycleCount(1);
        timer.play();
    }

    /*
    *@params txtMsg
    *@params msgBox
    *@params msg
    *@params Color_Code
     */
    private void cleadAndAddNewBtnAction(JFXTextField txtCode, JFXTextField txtName, JFXTextField txtDescription, JFXButton btnAddUpdate, JFXButton btnClear) {
        btnAddUpdate.setText("Add New");
        txtCode.setText("");
        txtName.setText("");
        txtDescription.setText("");
        btnClear.setVisible(false);
    }

    private void btnDeleteCustomerAction(ActionEvent event) {
        String cCode = txtCustomerCode.getText().replaceAll("'", "\\\\'");
        if (cCode == null || cCode.equals("")) {
            setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
//            txtErrorMCode.setVisible(false);
            try {
                //Check if user is exist
                int executed = dh.execute_sp("delete_tbl_customer_sp('" + cCode + "')", connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Customer Removed Successfully :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageCustomer, BoxMessageCustomer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageCustomer, BoxMessageCustomer, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtCustomerCode, txtCustomerName, txtCustomerDescription, btnAddUpdateCustomer, btnClearCustomer);
            refreshTableCustomer();
            txtErrorCName.setVisible(false);
            txtErrorCDescription.setVisible(false);
        }
    }

    private void btnClearCustomerAction(ActionEvent event) {
        cleadAndAddNewBtnAction(txtCustomerCode, txtCustomerName, txtCustomerDescription, btnAddUpdateCustomer, btnClearCustomer);
        refreshTableCustomer();
    }

    private void btnAddUpdateManufaturerAction(ActionEvent event) {
        String mName = txtManufaturerName.getText().replaceAll("'", "\\\\'");
        String mDescription = txtManufaturerDescription.getText().replaceAll("'", "\\\\'");
        if (mName == null || mName.equals("") || mDescription == null || mDescription.equals("")) {
            if (mName == null || mName.equals("")) {
                txtErrorMName.setVisible(true);
            } else {
                txtErrorMName.setVisible(false);
            }
            if (mDescription == null || mDescription.equals("")) {
                txtErrorMDescription.setVisible(true);
            } else {
                txtErrorMDescription.setVisible(false);
            }
        } else {
            if (btnAddUpdateManufaturer.getText().equals("Add New")) {
                try {
                    //Check if user is exist
                    ResultSet check_rs = dh.getData("SELECT * FROM tbl_manufacturer ORDER BY `manufacturer_id` DESC LIMIT 1;", connect);
                    if (check_rs.next()) {
                        ResultSet get_prefix = dh.getData("SELECT code_format_prefix FROM tbl_code_format WHERE code_format_prefix_for = 'manufacturer';", connect);
                        if (get_prefix.next()) {
                            String[] split = check_rs.getString("manufacturer_code").split(get_prefix.getString("code_format_prefix"));
                            int mCodePre = Integer.parseInt(split[1]);
                            int mCodePost = mCodePre + 1;
                            String mCode = get_prefix.getString("code_format_prefix") + mCodePost;
                            int executed = dh.execute_sp("insert_tbl_manufacturer_sp('" + mCode + "','" + mName + "','" + mDescription + "')", connect);
                            if (executed == 1) {
                                setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Manufacturer Added Successfully :)", Colors.msgGreen);
                            } else {
                                setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                            }
                        }
                    } else {
                        ResultSet get_prefix = dh.getData("SELECT code_format_prefix FROM tbl_code_format WHERE code_format_prefix_for = 'manufacturer';", connect);
                        if (get_prefix.next()) {
                            String prefix = get_prefix.getString("code_format_prefix");
                            String mCode = prefix + 1;
                            int executed = dh.execute_sp("insert_tbl_manufacturer_sp('" + mCode + "','" + mName + "','" + mDescription + "')", connect);
                            if (executed == 1) {
                                setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Manufacturer Added Successfully :)", Colors.msgGreen);
                            } else {
                                setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }
            } else {
                try {
                    String mCode = txtManufaturerCode.getText().replaceAll("'", "\\\\'");
                    //Check if user is exist
                    int executed = dh.execute_sp("update_tbl_manufacturer_sp('" + mCode + "','" + mName + "','" + mDescription + "')", connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Manufacturer Updated Successfully :)", Colors.msgGreen);

                    } else {
                        setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, ex.getMessage(), Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtManufaturerCode, txtManufaturerName, txtManufaturerDescription, btnAddUpdateManufaturer, btnClearManufaturer);
            refreshTableManufacturer();
            txtErrorMName.setVisible(false);
            txtErrorMDescription.setVisible(false);
        }

    }

    private void btnDeleteManufaturerAction(ActionEvent event) {
        String mCode = txtManufaturerCode.getText().replaceAll("'", "\\\\'");
        if (mCode == null || mCode.equals("")) {
            setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
//            txtErrorMCode.setVisible(false);
            try {
                //Check if user is exist
                int executed = dh.execute_sp("delete_tbl_manufacturer_sp('" + mCode + "')", connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Manufacturer Removed Successfully :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtManufaturerCode, txtManufaturerName, txtManufaturerDescription, btnAddUpdateManufaturer, btnClearManufaturer);
            refreshTableManufacturer();
            txtErrorMName.setVisible(false);
            txtErrorMDescription.setVisible(false);
        }
    }

    private void btnClearManufaturerAction(ActionEvent event) {
        cleadAndAddNewBtnAction(txtManufaturerCode, txtManufaturerName, txtManufaturerDescription, btnAddUpdateManufaturer, btnClearCustomer);
        refreshTableManufacturer();
    }

    private void btnAddUpdateProjectAction(ActionEvent event) {
        String pName = txtProjectName.getText().replaceAll("'", "\\\\'");
        String pDescription = txtProjectDescription.getText().replaceAll("'", "\\\\'");
        if (pName == null || pName.equals("") || pDescription == null || pDescription.equals("")) {
            if (pName == null || pName.equals("")) {
                txtErrorPName.setVisible(true);
            } else {
                txtErrorPName.setVisible(false);
            }
            if (pDescription == null || pDescription.equals("")) {
                txtErrorPDescription.setVisible(true);
            } else {
                txtErrorPDescription.setVisible(false);
            }
        } else {
            if (btnAddUpdateProject.getText().equals("Add New")) {
                try {
                    //Check if user is exist
                    ResultSet check_rs = dh.getData("SELECT * FROM tbl_project ORDER BY `project_id` DESC LIMIT 1;", connect);
                    if (check_rs.next()) {
                        ResultSet get_prefix = dh.getData("SELECT code_format_prefix FROM tbl_code_format WHERE code_format_prefix_for = 'project';", connect);
                        if (get_prefix.next()) {
                            String[] split = check_rs.getString("project_code").split(get_prefix.getString("code_format_prefix"));
                            int pCodePre = Integer.parseInt(split[1]);
                            int pCodePost = pCodePre + 1;
                            String pCode = get_prefix.getString("code_format_prefix") + pCodePost;
                            int executed = dh.execute_sp("insert_tbl_project_sp('" + pCode + "','" + pName + "','" + pDescription + "')", connect);
                            if (executed == 1) {
                                setMessageForResult(txtMessageProject, BoxMessageProject, "Project Added Successfully :)", Colors.msgGreen);
                            } else {
                                setMessageForResult(txtMessageProject, BoxMessageProject, "Something went wrong, please contact Admin :)", Colors.msgRed);
                            }
                        }
                    } else {
                        ResultSet get_prefix = dh.getData("SELECT code_format_prefix FROM tbl_code_format WHERE code_format_prefix_for = 'project';", connect);
                        if (get_prefix.next()) {
                            String prefix = get_prefix.getString("code_format_prefix");
                            String pCode = prefix + 1;
                            int executed = dh.execute_sp("insert_tbl_project_sp('" + pCode + "','" + pName + "','" + pDescription + "')", connect);
                            if (executed == 1) {
                                setMessageForResult(txtMessageProject, BoxMessageProject, "Project Added Successfully :)", Colors.msgGreen);
                            } else {
                                setMessageForResult(txtMessageProject, BoxMessageProject, "Something went wrong, please contact Admin :)", Colors.msgRed);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    setMessageForResult(txtMessageProject, BoxMessageProject, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            } else {
                try {
                    String pCode = txtProjectCode.getText().replaceAll("'", "\\\\'");
                    //Check if user is exist
                    int executed = dh.execute_sp("update_tbl_project_sp('" + pCode + "','" + pName + "','" + pDescription + "')", connect);
                    if (executed == 1) {
                        setMessageForResult(txtMessageProject, BoxMessageProject, "Project Updated Successfully :)", Colors.msgGreen);

                    } else {
                        setMessageForResult(txtMessageProject, BoxMessageProject, "Something went wrong, please contact Admin :)", Colors.msgRed);
                    }

                } catch (SQLException ex) {
                    setMessageForResult(txtMessageManufaturer, BoxMessageManufaturer, ex.getMessage() + ", please contact Admin", Colors.msgRed);
                }
            }
            cleadAndAddNewBtnAction(txtProjectCode, txtProjectName, txtProjectDescription, btnAddUpdateProject, btnClearProject);
            refreshTableProject();
            txtErrorPName.setVisible(false);
            txtErrorPDescription.setVisible(false);
        }
    }

    private void btnDeleteProjectAction(ActionEvent event) {
        String pCode = txtProjectCode.getText().replaceAll("'", "\\\\'");
        if (pCode == null || pCode.equals("")) {
            setMessageForResult(txtMessageProject, BoxMessageProject, "No Data Selected, Try Again!!!", Colors.msgGreen);
//            txtErrorMCode.setVisible(true);
        } else {
            try {
                //Check if user is exist
                int executed = dh.execute_sp("delete_tbl_project_sp('" + pCode + "')", connect);
                if (executed == 1) {
                    setMessageForResult(txtMessageProject, BoxMessageProject, "Project Removed Successfully :)", Colors.msgGreen);
                } else {
                    setMessageForResult(txtMessageProject, BoxMessageProject, "Something went wrong, please contact Admin :)", Colors.msgRed);
                }

            } catch (SQLException ex) {
                setMessageForResult(txtMessageProject, BoxMessageProject, ex.getMessage(), Colors.msgRed);
            }
            cleadAndAddNewBtnAction(txtProjectCode, txtProjectName, txtProjectDescription, btnAddUpdateProject, btnClearProject);
            refreshTableProject();
            txtErrorPName.setVisible(false);
            txtErrorPDescription.setVisible(false);
        }
    }

    private void btnClearProjectAction(ActionEvent event
    ) {
        cleadAndAddNewBtnAction(txtProjectCode, txtProjectName, txtProjectDescription, btnAddUpdateProject, btnClearProject);
        refreshTableProject();
    }

   

    @FXML
    private void btnLoginAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }

    private void btnTestScreenAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }

    @FXML
    private void btnAlarmAction(ActionEvent event) {
        Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AlarmScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
    }


    class Users extends RecursiveTreeObject<Users> {

        StringProperty one;
        StringProperty two;
        StringProperty three;
        StringProperty four;
        StringProperty five;

        public Users(String one, String two, String three) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);
            this.three = new SimpleStringProperty(three);
        }

        public Users(String one, String two, String three, String four) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);
            this.three = new SimpleStringProperty(three);
            this.four = new SimpleStringProperty(four);
        }

        public Users(String one, String two, String three, String four, String five) {
            this.one = new SimpleStringProperty(one);
            this.two = new SimpleStringProperty(two);
            this.three = new SimpleStringProperty(three);
            this.four = new SimpleStringProperty(four);
            this.five = new SimpleStringProperty(five);
        }

    }

    class ManufacturerData extends RecursiveTreeObject<ManufacturerData> {

        StringProperty code;
        StringProperty name;
        StringProperty description;

        public ManufacturerData(String one, String two, String three) {
            this.code = new SimpleStringProperty(one);
            this.name = new SimpleStringProperty(two);
            this.description = new SimpleStringProperty(three);
        }

    }

    class ProjectData extends RecursiveTreeObject<ProjectData> {

        StringProperty code;
        StringProperty name;
        StringProperty description;

        public ProjectData(String one, String two, String three) {
            this.code = new SimpleStringProperty(one);
            this.name = new SimpleStringProperty(two);
            this.description = new SimpleStringProperty(three);
        }

    }

    public void messageGuage(String message, String Color_Code) {
        txtMessageGuage.setText(message);
        BoxMessageGauge.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        btnCloseMessageGauge.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        txtMessageGuage.setVisible(true);
        BoxMessageGauge.setVisible(true);
        btnCloseMessageGauge.setVisible(true);
    }

    public void messageUser(String message, String Color_Code) {
        txtMessage.setText(message);
        BoxMessage.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        btnCloseMessage.setStyle("-fx-background-color: derive(" + Color_Code + ",80%);");
        txtMessage.setVisible(true);
        BoxMessage.setVisible(true);
        btnCloseMessage.setVisible(true);
    }

    //VALIDATION CHECK
    private void add_listener_to_textfield(JFXTextField textField, Text text) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            check_empty_fields(text, newValue);
        });
    }

    private void check_empty_fields(Text field, String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                field.setVisible(true);
            } else {
                field.setVisible(false);
            }
        } else {
            field.setVisible(true);
        }
    }

}
