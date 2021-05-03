/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.Section;
import java.awt.Toolkit;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.RED;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javax.management.remote.JMXConnectorFactory.connect;
import static javax.swing.Spring.width;
import org.python.apache.xerces.util.DOMUtil;
import static org.python.core.Py.True;
import org.python.modules.time.Time;

/**
 * FXML Controller class
 *
 * @author nsp
 */
public class TestScreenController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbTestType;
    private Text ttxErr_Valveclass;
    private Text txtErr_Valvetype;
    @FXML
    private JFXTextField txtHydroSetPressure;
    @FXML
    private JFXTextField txtHoldingTime;

//    int count_executer_status = 0;
//    boolean initial_start_trend = true;
    String current_overall_timer = "2";

    DatabaseHandler dh = new DatabaseHandler();

    Connection connect = dh.MakeConnection();
    Colors colors = new Colors();
    private JFXButton btnStart;
    @FXML
    private JFXComboBox<String> cmbValveType;
    @FXML
    private JFXComboBox<String> cmbValveClass;
    @FXML
    private JFXComboBox<String> cmbValveSize;
    @FXML
    private JFXComboBox<String> cmbTestStd;
    @FXML
    private JFXTextField txtValveSrNo;
    private JFXTextField txtType;

    String hydraulic, hydro, drain, stabi, tt, vt, vc, vs, hp, hold, vsn, type, bodyHeat, discHeat, noOfHole, pcd, docNo, allowableCount, tstd;
    String pernsno, vstd, remark = "";
    @FXML
    private JFXTextField txtCycleStatus;
    @FXML
    private JFXTextField txtHoldingTimer;
    @FXML
    private Gauge Gaugehydro;

    final ToggleGroup group_select = new ToggleGroup();
    final ToggleGroup group_select_time = new ToggleGroup();

    Colors color = new Colors();
    @FXML
    private Text textHydro;
//    private Text textTrend;
    private HBox hboxtrend;
    private HBox hboxTrendText;
    private VBox vboxStatus;

    String h_time, pressure_a, result, overall_time, bubble = "";
    @FXML
    private JFXTextField txtOverAllTime;

    private JFXTextField txtvType;
//    @FXML
//    private JFXTextField txtRoomTemp1;
    @FXML
    private Text txtdate;
    @FXML
    private JFXButton btnLogin;

    boolean you_can = true;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXRadioButton radiobar;
    @FXML
    private JFXRadioButton radiopsi;
    @FXML
    private Text txtb;
    @FXML
    private JFXButton btngauge;
    private JFXTextField txtTestStd;

    @FXML
    private JFXTextField txtvendr;
    @FXML
    private JFXComboBox<String> cmbPressuregage;
    @FXML
    private JFXTextField txtrange;

    @FXML
    private VBox vboxGauge;
    @FXML

    private JFXRadioButton radiokg;

    @FXML
    private JFXTextField txtHydraulicSetPressure;
    @FXML
    private JFXTextField txtStabilization;
    @FXML
    private JFXTextField txtDrainTime;

    @FXML
    private Text textHydro1;
    @FXML
    private Text txtMode;
    private Text txtOffline;

    String current_machine_mode = "33", mac_mode = "22", off_mode = "22", clampingActualPressure = "33", s_time = "0", d_d = "0", d_time = "0", pressure_b = "0", popUps = "0", bubble_counter = "0", cycl_status, current_offline_mode, ts, operator_name, st, ht, dt, hsp, csp, pro, cus, heat;
    Boolean first_pop_lock = false, second_pop_lock = false, third_pop_lock = false, you_can_change = true;
    DoubleProperty ClampingAct;
    DoubleProperty HydroActA;
    DoubleProperty HydroActB;
    int firstBindingGauge = 0;
    String start_pressure_a, start_pressure_b, start_pressure_c, start_pressure_d, start_pressure_e, stop_pressure_a, stop_pressure_b, stop_pressure_c, stop_pressure_d, stop_pressure_e, oat, al, current_stabilization_timer, current_holding_timer, current_drain_delay, current_drain_timer, holding_time, overall_time_end, max, green, invalid;
    int test_result_by_type_check = 0;
    private int gauge_clamping_red, gauge_hydro_red;
    @FXML
    private Gauge GaugeActualHydraulic;
    @FXML
    private JFXTextField txtResult;

//    @FXML
//    private JFXTextField txtHoldingTimer;
    @FXML
    private JFXTextField txtDrainTimer;
    @FXML
    private HBox hboxTest;
    @FXML
    private JFXComboBox<String> cmbTypeOfSealing;
    @FXML
    private Text txtunithydro;
    @FXML
    private Text txtunithydraulic;
    @FXML
    private JFXComboBox<String> cmbLeakageType;
    @FXML
    private JFXTextField txtAllowable;
    @FXML
    private Text txtinvalid;
    @FXML
    private JFXTextField txtAtcualBubble;
    @FXML
    private Text txtbubble;
    @FXML
    private JFXToggleButton toggleOnlineOffline;
    @FXML
    private Text txtOnlineOffline;
    @FXML
    private JFXComboBox<String> cmbModel;
    @FXML
    private JFXTextField txtBodyHeatno;
    @FXML
    private JFXTextField txtNoOfHole;
    @FXML
    private JFXTextField txtDiscHeatno;
    @FXML
    private JFXTextField txtDocNo;
    @FXML
    private JFXTextField txtPcd;
    @FXML
    private VBox vboxTxt;
    @FXML
    private JFXTextField txtStabilizationTimer;

    //Trend Chart Component
    private final int MAX_DATA_POINTS = 50;
    private int xSeriesData = 0;
    private final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
    private ExecutorService executor;
    private final ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();

    private final ConcurrentLinkedQueue<Number> dataQ3 = new ConcurrentLinkedQueue<>();
    private NumberAxis xAxis;

    private boolean start_trend = false;

    LineChart<Number, Number> lineChart;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private Text lableTitle;
    @FXML
    private JFXButton btnAlarm;
    @FXML
    private Text lableError;
    @FXML
    private HBox hboxError;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        radiobar.setToggleGroup(group_select);
        GaugeActualHydraulic.setVisible(false);
        drawer.setVisible(false);
        radiobar.setSelected(true);
        txtunithydraulic.setText("bar");
        txtunithydro.setText("bar");
        pu = "bar";
        radiokg.setToggleGroup(group_select);
        radiopsi.setToggleGroup(group_select);
        txtAllowable.setVisible(false);
        btngauge.setVisible(true);

        //set Editable false some testing parameter
        txtCycleStatus.setEditable(false);
        txtStabilizationTimer.setEditable(false);
        txtHoldingTimer.setEditable(false);
        txtDrainTimer.setEditable(false);
        txtOverAllTime.setEditable(false);
        txtResult.setEditable(false);
        txtAtcualBubble.setEditable(false);
        cmbPressuregage.setVisible(false);
        txtrange.setVisible(false);
        txtvendr.setVisible(false);
        cmbModel.setVisible(false);
        txtinvalid.setVisible(false);
        String user_type = Session.get("user_type");
        if (user_type.equals("admin")) {
            btnAdmin.setVisible(true);

        } else {
            btnAdmin.setVisible(false);
        }
        if (Session.get("mt").equals("16MT")) {
            lableTitle.setText("VALVE TESTING UNIT - 16MT");
        } else if (Session.get("mt").equals("90MT")) {
            lableTitle.setText("VALVE TESTING UNIT - 90MT");
        }

        date_time();
        Background_Processes.insert_plc_data("python E:\\E1337\\python_plc\\insert_init_test_tag.py", false, true);
        String user_name = Session.get("user");
        try {

            InitialDataLoad();

            cmbTypeOfSealing.getItems().addAll("Face Sealing");
            cmbTypeOfSealing.getSelectionModel().selectFirst();

            txtCycleStatus.setEditable(true);
            txtHoldingTimer.setEditable(true);

//            txtremark.setDisable(false);
            textHydro.setVisible(false);
//            hboxtrend.setVisible(false);
//            textTrend.setVisible(false);
            Gaugehydro.setVisible(false);
            textHydro1.setVisible(false);
//            hboxtrend.setVisible(false);
//            textTrend.setVisible(false);

        } catch (SQLException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        machine_mode();

    }
    String pu;
    int leak_no, cust_flag = 1, test_no = 1;

    private void date_time() {
        time.scheduleAtFixedRate(date, 0, 1000);
    }

    Timer time = new Timer();

    TimerTask date = new TimerTask() {
        @Override
        public void run() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            txtdate.setText("Date:" + dtf.format(now));

        }
    };

    public void InitialDataLoad() throws SQLException {
        //test type 
        String query = "SELECT test_type,write_value FROM test_type GROUP BY write_value";
        cmbTestType.getSelectionModel().clearSelection();
        cmbTestType.getItems().clear();
        try {
            int count = 0;
            ResultSet rs = dh.getData(query, connect);
            while (rs.next()) {

                cmbTestType.getItems().add(count, rs.getString("test_type"));
                count++;
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

        Initial_Dropdowns_Data();
        //VALVE CLASS SIZE
        System.out.println("cmbValveType.getSelectionModel().getSelectedItem()");
        valve_class(cmbValveType.getSelectionModel().getSelectedItem());

        //if previous data exist
        //setting data
        String query1 = "SELECT * FROM Initial_init ORDER BY ID DESC LIMIT 1;";

        try {
            ResultSet rs1 = dh.getData(query1, connect);

            while (rs1.next()) {
                cmbTestType.getSelectionModel().select(Integer.parseInt(rs1.getString("test_type")));
                System.out.println("--------------!!!!!!--------" + rs1.getString("leakage_type"));
                cmbLeakageType.getSelectionModel().select(Integer.parseInt(rs1.getString("leakage_type")));
                leak_no = Integer.parseInt(rs1.getString("leakage_type"));
                String vc_query = "SELECT valve_class FROM valve_class WHERE write_value = '" + rs1.getString("valve_class") + "';";

                ResultSet vc_rs = dh.getData(vc_query, connect);
                if (vc_rs.next()) {
                    cmbValveClass.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_class")));
                }
                String vs_query = "SELECT valve_size FROM valve_size WHERE write_value = '" + rs1.getString("valve_size") + "';";
                ResultSet vs_rs = dh.getData(vs_query, connect);
                if (vs_rs.next()) {
                    cmbValveSize.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_size")));
                }
//                cmbValveClass.getSelectionModel().select(Integer.parseInt(rs.getString("valve_class")));
//                cmbValveSize.getSelectionModel().select(Integer.parseInt(rs.getString("valve_size")));
                cmbValveType.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_type")));
                cmbTypeOfSealing.getSelectionModel().select(Integer.parseInt(rs1.getString("type_of_sealing")));
                cmbTestStd.getSelectionModel().select(Integer.parseInt(rs1.getString("test_standards")));
                valve_size(cmbValveType.getSelectionModel().getSelectedItem());
                cmbValveSize.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_size")));
                if (rs1.getString("test_standards").equals("3")) {
                    cust_flag = 0;
                    txtHydroSetPressure.setEditable(true);
                    txtHydraulicSetPressure.setEditable(true);
                    txtStabilization.setEditable(true);
                    txtHoldingTime.setEditable(true);
                    txtDrainTime.setEditable(true);
                } else {
                    txtHydroSetPressure.setEditable(false);
                    txtHydraulicSetPressure.setEditable(false);
                    txtStabilization.setEditable(false);
                    txtHoldingTime.setEditable(false);
                    txtDrainTime.setEditable(false);
                }
//                cmbValveStandards.getSelectionModel().select(Integer.parseInt(rs1.getString("valve_standards")));
                txtStabilization.setText(rs1.getString("stabilization_time"));
                txtHoldingTime.setText(rs1.getString("holding_time"));
//                txtDrainDelay.setText(rs.getString("drain_delay"));
                txtDrainTime.setText(rs1.getString("drain_time"));
                txtHydroSetPressure.setText(rs1.getString("hydro_set_pressure"));
                txtHydraulicSetPressure.setText(rs1.getString("hydraulic_set_pressure"));

                bar_psi_kg(rs1.getString("bar_psi_kgcm"));
                leakage_type();
            }

            ResultSet rs_data = dh.getData("SELECT * FROM valve_data ORDER BY id DESC LIMIT 1", connect);
            if (rs_data.next()) {
                txtValveSrNo.setText(rs_data.getString("vsn"));
                txtBodyHeatno.setText(rs_data.getString("bodyHeat"));
                txtDiscHeatno.setText(rs_data.getString("discHeat"));
                txtNoOfHole.setText(rs_data.getString("noOfHole"));
                txtPcd.setText(rs_data.getString("pcd"));
                txtDocNo.setText(rs_data.getString("docNo"));
                txtAllowable.setText(rs_data.getString("allowable_leakage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Initial_Dropdowns_Data() {
        String q = "SELECT vt.valve_type,vt.write_value AS type_write,tos.type_of_sealing,tos.write_value AS sealing_write,ts.test_standards,ts.write_value AS standard_write,vst.valve_standards,vst.write_value AS valve_standard_write FROM valve_type vt LEFT JOIN type_of_sealing tos ON tos.type_of_sealing_id = vt.valve_type_id LEFT JOIN test_standards ts ON ts.test_standards_id = vt.valve_type_id LEFT JOIN valve_standards vst ON vst.valve_standards_id = vt.valve_type_id;";
        try {
            System.out.println("q : " + q);
            ResultSet rs = dh.getData(q, connect);
            while (rs.next()) {
                if (rs.getString("valve_type") == null || rs.getString("valve_type").equals("")) {
                } else {
                    cmbValveType.getItems().add(rs.getString("valve_type"));
                    
                }
                if (rs.getString("type_of_sealing") == null || rs.getString("type_of_sealing").equals("")) {
                } else {
                    cmbTypeOfSealing.getItems().add(Integer.parseInt(rs.getString("sealing_write")), rs.getString("type_of_sealing"));
                }
//                
//                if (rs.getString("test_standards") == null || rs.getString("test_standards").equals("")) {
//                } else {
//                    cmbTestStd.getItems().add(Integer.parseInt(rs.getString("standard_write")), rs.getString("test_standards"));
//                }

            }

            ResultSet rs_std = dh.getData("SELECT test_standards,write_value FROM test_standards ORDER BY test_standards_id ASC", connect);
            while (rs_std.next()) {
                cmbTestStd.getItems().add(Integer.parseInt(rs_std.getString("write_value")), rs_std.getString("test_standards"));
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void valve_class(String Valve_Standard) throws SQLException {
        cmbValveClass.getSelectionModel().clearSelection();
//        cmbValveSize.getSelectionModel().clearSelection();
        cmbValveClass.getItems().clear();
//        cmbValveSize.getItems().clear();
        //Valve_class
        String vc = "SELECT vc.valve_class FROM valve_class vc;";
        System.out.println(vc);
        ResultSet rs_vc = dh.getData(vc, connect);
        while (rs_vc.next()) {
            if (rs_vc.getString("valve_class") == null || rs_vc.getString("valve_class").equals("")) {
            } else {
                cmbValveClass.getItems().add(rs_vc.getString("valve_class"));
            }
        }
    }

    private void valve_size(String Valve_Standard) {

        try {
            //Valve_size
            String vs = "SELECT vs.valve_size FROM valve_size vs WHERE vs.AVK_Model = '" + Valve_Standard + "';";
            System.out.println(vs);
            ResultSet rs_vs = dh.getData(vs, connect);
            while (rs_vs.next()) {
                if (rs_vs.getString("valve_size") == null || rs_vs.getString("valve_size").equals("")) {
                } else {

                    cmbValveSize.getItems().add(rs_vs.getString("valve_size"));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bar_psi_kg(String bar_psi_kg) {
        switch (bar_psi_kg) {
            case "0":
                radiobar.setSelected(true);
                pu = "bar";
                setPressureUnit("bar");
                break;
            case "1":
                radiopsi.setSelected(true);
                pu = "psi";
                setPressureUnit("psi");
                break;
            case "2":
                radiokg.setSelected(true);
                pu = "kg/sqcm";
                setPressureUnit("kg/sqcm");
                break;
//            case "3":
//
//                radioKpa.setSelected(true);
//                pu = "KPa";
//                setPressureUnit("kg/sqcm");
//                break;
//            case "4":
//                radioMpa.setSelected(true);
//                pu = "MPa";
//                setPressureUnit("kg/sqcm");
//                break;
//            case "5":
//                radioPascal.setSelected(true);
//                pu = "Pascal";
//                setPressureUnit("kg/sqcm");
//                break;
            default:
                radiobar.setSelected(true);
                pu = "bar";
                break;
        }
    }

    private void leakage_type() {
        String tst_type = cmbTestType.getSelectionModel().getSelectedItem();
        cmbLeakageType.getSelectionModel().clearSelection();
        cmbLeakageType.getItems().clear();
//        txtActualUnit.setVisible(false);
//        txtAllowableLeakage.setVisible(false);
        String q = "SELECT series,leakage_type,write_value_test_type FROM leakage_type WHERE test_type = '" + tst_type + "' GROUP BY series;";
        System.out.println("q: " + q);
        try {
            ResultSet rs = dh.getData(q, connect);
            String write_value = "0";
            while (rs.next()) {
                write_value = rs.getString("write_value_test_type");
                int index = Integer.parseInt(rs.getString("series"));
//                cmbLeakageType.getItems().add(index, rs.getString("leakage_type"));
                cmbLeakageType.getItems().add(rs.getString("leakage_type"));
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void setPressureUnit(String unit) {
        txtHydroSetPressure.setPromptText("Hydro Set Pressure(" + unit + ")");
        txtHydraulicSetPressure.setPromptText("Hydraulic Set Pressure(" + unit + ")");
    }

    private void disable_field() {
        cmbTestType.setDisable(true);
        cmbValveClass.setDisable(true);
        cmbValveSize.setDisable(true);
        cmbTestStd.setDisable(true);
        cmbValveType.setDisable(true);
        cmbTypeOfSealing.setDisable(true);
        cmbLeakageType.setDisable(true);
        txtValveSrNo.setEditable(false);
        txtBodyHeatno.setEditable(false);
        txtDiscHeatno.setEditable(false);
        txtNoOfHole.setEditable(false);
        txtPcd.setEditable(false);
        txtDocNo.setEditable(false);
        txtAllowable.setEditable(false);
        radiobar.setDisable(true);
        radiokg.setDisable(true);
        radiopsi.setDisable(true);

    }

    private void enable_field() {
        cmbTestType.setDisable(false);
        cmbValveClass.setDisable(false);
        cmbValveSize.setDisable(false);
        cmbTestStd.setDisable(false);
        cmbValveType.setDisable(false);
        cmbTypeOfSealing.setDisable(false);
        cmbLeakageType.setDisable(false);

        txtValveSrNo.setEditable(true);
        txtBodyHeatno.setEditable(true);
        txtDiscHeatno.setEditable(true);
        txtNoOfHole.setEditable(true);
        txtPcd.setEditable(true);
        txtDocNo.setEditable(true);
        txtAllowable.setEditable(true);

        radiobar.setDisable(true);
        radiokg.setDisable(true);
        radiopsi.setDisable(true);

    }

    private void is_empty() {

        tt = cmbTestType.getSelectionModel().getSelectedItem();
        vt = cmbValveType.getSelectionModel().getSelectedItem();
        vc = cmbValveClass.getSelectionModel().getSelectedItem();
        vs = cmbValveSize.getSelectionModel().getSelectedItem();
        tstd = cmbTestStd.getSelectionModel().getSelectedItem();
        vsn = txtValveSrNo.getText();
        bodyHeat = txtBodyHeatno.getText();
        discHeat = txtDiscHeatno.getText();
        noOfHole = txtNoOfHole.getText();
        pcd = txtPcd.getText();
        docNo = txtDocNo.getText();
        allowableCount = txtAllowable.getText();

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

    private void check_text_empty_fields(JFXTextField field, String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                field.setUnFocusColor(RED);
                field.setFocusColor(RED);
            } else {
            }
        } else {
            field.setUnFocusColor(RED);
            field.setFocusColor(RED);
        }
    }

    private void check_combo_empty_fields(JFXComboBox field, String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                field.setUnFocusColor(RED);
                field.setFocusColor(RED);
            }
        } else {
            field.setUnFocusColor(RED);
            field.setFocusColor(RED);
        }
    }

    //set empty fields on cycle compleat and cycle stop
    public void set_empty() {
        txtCycleStatus.setText("");
        txtHoldingTimer.setText("");

//        txtremark.setText("");
        txtOverAllTime.setText("");
    }

    private void machine_mode() {
        mode = new Thread(() -> {

            try {

                String display = "SELECT * FROM initialinitmain ORDER BY id DESC LIMIT 1";
                ResultSet rs;
                try {
                    rs = dh.getData(display, connect);
                    if (rs.next()) {
                        if (ToolKit.isNull(rs.getString("over_all_time_time"))) {
                            System.out.println("NULL overall_time");
                        } else {
                            current_overall_timer = rs.getString("over_all_time_time");
                        }
                        txtOverAllTime.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }

                while (true) {

                    try {
                        long start = System.currentTimeMillis();
                        //Sleeping thread for 250 miliseconds: Starts
                        try {
//                            Thread.sleep(250);
                            mode.sleep(100);
                        } catch (InterruptedException intex) {
                            intex.printStackTrace();
                            System.err.println("Interupt in mode thread : " + intex);
                        }
                        //Sleeping thread for 250 miliseconds: End

                        if (stop_mode) {
                            break;
                        }

                        rs = dh.getData(display, connect);
                        if (rs.next()) {
                            //Storing Value's of Machine Parameters: Start
                            if (ToolKit.isNull(rs.getString("machine_mode"))) {
                                System.out.println("NULL machine_mode");
                            } else {

//                                mac_mode=rs.getString("ma")
                                mac_mode = rs.getString("machine_mode");
                            }
                            if (ToolKit.isNull(rs.getString("offline_online"))) {
                                System.out.println("NULL offline_online");
                            } else {
                                off_mode = rs.getString("offline_online");
                            }
                            if (ToolKit.isNull(rs.getString("cycle_status"))) {
                                System.out.println("NULL cycle_status");
                            } else {
                                cycl_status = rs.getString("cycle_status");

                            }
                            if (ToolKit.isNull(rs.getString("stabilization_timer"))) {
                                System.out.println("NULL stabilization_timer");
                            } else {
                                s_time = rs.getString("stabilization_timer");
                            }
                            if (ToolKit.isNull(rs.getString("holding_timer"))) {
                                System.out.println("NULL holding_timer");
                            } else {
                                h_time = rs.getString("holding_timer");
                            }

                            if (ToolKit.isNull(rs.getString("drain_timer"))) {
                                System.out.println("NULL drain_timer");
                            } else {
                                d_time = rs.getString("drain_timer");
                            }
                            if (ToolKit.isNull(rs.getString("over_all_time_time"))) {
                                System.out.println("NULL overall_time");
                            } else {
                                overall_time = rs.getString("over_all_time_time");
                            }
                            if (ToolKit.isNull(rs.getString("hydraulic_actual_pressure"))) {
                                System.out.println("NULL hydraulic_actual_pressure");
                            } else {
//                                System.out.println("rs.getString(\"hydraulic_actual\")" + rs.getString("hydraulic_actual_pressure"));
                                clampingActualPressure = rs.getString("hydraulic_actual_pressure");
                            }
                            if (ToolKit.isNull(rs.getString("hydro_actual_a_pressure"))) {
                                System.out.println("NULL hydro_actual_a_pressure");
                            } else {
                                pressure_a = rs.getString("hydro_actual_a_pressure");
                            }
                            if (ToolKit.isNull(rs.getString("hydro_actual_b_pressure"))) {
                                System.out.println("NULL hydro_actual_b_pressure");
                            } else {
                                pressure_b = rs.getString("hydro_actual_b_pressure");
                            }
                            if (ToolKit.isNull(rs.getString("pop_ups"))) {
                                System.out.println("NULL pop_ups");
                            } else {
                                popUps = rs.getString("pop_ups");
                            }
                            if (ToolKit.isNull(rs.getString("result"))) {
                                System.out.println("NULL result");
                            } else {
                                result = rs.getString("result");
                            }
                            if (rs.getString("invalid").equals("1")) {
                                txtinvalid.setVisible(true);
                            } else {
                                txtinvalid.setVisible(false);
                            }

                            if (ToolKit.isNull(rs.getString("pop_ups"))) {
                                System.out.println("NULL pop_ups");
                            } else {
                                popUps = rs.getString("pop_ups");
                            }
                            if (ToolKit.isNull(rs.getString("bubble_count"))) {
                                System.out.println("NULL bubble_counter");
                            } else {
                                bubble_counter = rs.getString("bubble_count");
                            }

                            if (cust_flag == 1) {
//                                    if (ToolKit.isNull(rs.getString("hydraulic_pressure_set"))) {
//                                        System.out.println("NULL hydraulic_pressure_set");
//                                    } else {
                                hydraulic = rs.getString("hydraulic_pressure_set");
                                hydro = rs.getString("hydro_pressure_set");
                                drain = rs.getString("drain_timer_set");
                                stabi = rs.getString("stabilization_timer_set");
                                hold = rs.getString("holding_timer_set");
                                Platform.runLater(() -> {
                                    txtHydraulicSetPressure.setText(Float.toString(Math.round(Float.parseFloat(hydraulic))));
                                });
                                Platform.runLater(() -> {
                                    txtHydroSetPressure.setText(Float.toString(Math.round(Float.parseFloat(hydro))));
                                });

                                Platform.runLater(() -> {
                                    txtHoldingTime.setText(hold);
                                });
                                Platform.runLater(() -> {
                                    txtDrainTime.setText(drain);
                                });
                                Platform.runLater(() -> {
                                    txtStabilization.setText(stabi);
                                });

//                                    }
                            } else {

                            }

                            max = rs.getString("max_gauge");
                            green = rs.getString("green_gauge");
                            invalid = rs.getString("invalid");
                            if (invalid.equals("1")) {

                                Platform.runLater(() -> {
                                    txtinvalid.setVisible(true);
                                });
                            } else {
                                Platform.runLater(() -> {
                                    txtinvalid.setVisible(false);
                                });
                            }
//                            invalid = rs.getString("invalid");

//Storing Value's of Machine Parameters: End
                            try {

                                //Updating Gauge's Value: Start
                                double clampingActual = Double.parseDouble(clampingActualPressure);
                                double hydroA = Double.parseDouble(pressure_a);
                                double hydroB = Double.parseDouble(pressure_b);
                                ClampingAct = new SimpleDoubleProperty(clampingActual);
                                HydroActA = new SimpleDoubleProperty(hydroA);
                                HydroActB = new SimpleDoubleProperty(hydroB);
                                if (hydroB > 1) {
                                    HydroActA = new SimpleDoubleProperty(hydroB);
                                }
                                Platform.runLater(() -> {
                                    GaugeActualHydraulic.valueProperty().bind(ClampingAct);
                                    Gaugehydro.valueProperty().bind(HydroActA);
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("EXCEPTION IN SETTING VALUE OF GAUGES IN DATA_UPDATE_THREAD : " + e.getMessage());
                            }

                            try {

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

//break loop and stoping process: Start
                            if (stop_mode) {
                                break;
                            }
//break loop and stoping process: Start

                            try {
                                try {
                                    //Updating Operating Mode Value: Start
                                    if (mac_mode.equals(current_machine_mode)) {
                                    } else {
                                        mode(mac_mode);
                                    }
                                    //Updating Operating Mode Value: End
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                    System.out.println("EXCEPTION IN Updating Operating Mode DATA_UPDATE_THREAD : " + e.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                //getting first pop up start the cycle: Start
                                switch (popUps) {
                                    case "0":
                                        first_pop_lock = false;
                                        second_pop_lock = false;
                                        third_pop_lock = false;
                                        break;
                                    case "1":
                                        if (first_pop_lock) {
                                        } else {

                                            first_pop_lock = true;
                                            you_can_change = false;
                                            pop_up_start("Please Confirm Valve Type, Valve Class and Valve Size.", 450, 0, 4, "N7:9");
                                        }
                                        break;
                                    case "2":
                                        if (second_pop_lock) {
                                        } else {

                                            pop_up_timer("Start Hodling Timer", 300, 0, "N7:9");
                                            second_pop_lock = true;
//                                        start_pressure_a = pressure_a;
//                                        start_pressure_b = pressure_b;
                                        }
                                        break;
                                    case "3":
                                        if (third_pop_lock) {
                                        } else {
                                            stop_pressure_a = pressure_a;
                                            stop_pressure_b = pressure_b;
                                            holding_time = txtHoldingTimer.getText();

//                                        imgDrainDelay.setVisible(false);
                                            pop_up_timer("Start Drain Timer", 300, 0, "N7:9");
                                            third_pop_lock = true;
                                        }
                                        break;
                                    default:
                                        break;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("EXCEPTION IN Pop up Logic DATA_UPDATE_THREAD : " + e.getMessage());
                            }

                        }
                        long stop = System.currentTimeMillis();
                        long res = stop - start;

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Exception In Mode Thread : " + e);
//                            Thread.sleep(250);
                    }
                    if (stop_mode) {
                        break;
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, "Data_Update_Thread_Mode");
//        mode.setDaemon(true);
        mode.start();
    }

    private void mode(String mode) throws IOException, SQLException {

        switch (mode) {
            case "0":
                hboxError.setVisible(true);
                Platform.runLater(() -> {
                    txtMode.setText("Emergency Mode");
                    current_machine_mode = "0";
                    txtdate.setFill(Color.web("red"));
                    txtMode.setFill(Color.web("red"));
                });
                lableError.setText("Emergency Applied");
                break;
            case "1":
                hboxError.setVisible(true);

//                Background_Processes.insert_plc_once("python E:\\E1337\\python_plc\\insert_alarm_tags.py");
                String display = "SELECT * FROM alarm_tags ORDER BY alarm_tags_id DESC LIMIT 1";
                ResultSet rs = dh.getData(display, connect);
                if (rs.next()) {

                    if (rs.getString("90_side_pt").equals("0")) {
                        lableError.setText("90MT Machine Pressure Transmiter Disconnected");
                    } else {

                    }
                    if (rs.getString("oil_level").equals("0")) {
                        lableError.setText("Oil level is low");
                    } else {

                    }

                    if (rs.getString("16_side_pt").equals("0")) {
                        lableError.setText("16MT Machine Pressure Transmiter Disconnected");
                    } else {

                    }

                    if (rs.getString("90_side_hydraulic_pt").equals("0")) {
                        lableError.setText("90MT Machine Hydraulic Pressure Transmiter Disconnected");
                    } else {

                    }

                    if (rs.getString("16_side_hydraulic_pt").equals("0")) {
                        lableError.setText("16MT Machine Hydraulic Pressure Transmiter Disconnected");
                    } else {

                    }
                    if (rs.getString("hydraulic_motor").equals("0")) {
                        lableError.setText("Hydraulic Motor is OFF");
                    } else {

                    }

                    if (rs.getString("pre_fiill_motor").equals("0")) {
                        lableError.setText("Prefilling Motor is OFF");
                    } else {

                    }

                    if (rs.getString("drain_motor").equals("0")) {
                        lableError.setText("Drain Motor is OFF");
                    } else {

                    }

                }
                Platform.runLater(() -> {
                    txtMode.setText("Alarm Mode");
                    //                Platform.runLater(()->{
                    current_machine_mode = "1";
                    txtMode.setFill(Color.web("Red"));
                    txtdate.setFill(Color.web("Red"));
                });

                break;
            case "2":
                hboxError.setVisible(false);
                Platform.runLater(() -> {
                    txtMode.setText("Manual Mode");
                    current_machine_mode = "2";
                    txtdate.setFill(Color.web("#0099FF"));
                    txtMode.setFill(Color.web("#0099FF"));
                });
                break;
            case "3":
                hboxError.setVisible(false);
                Platform.runLater(() -> {
                    txtMode.setText("Auto Mode");
                    current_machine_mode = "3";
                    txtdate.setFill(Color.web("#0099FF"));
                    txtMode.setFill(Color.web("#0099FF"));

                });
                break;
            default:
                Platform.runLater(() -> {
                    txtMode.setText("Something wrong");
                    current_machine_mode = "0";
                    txtdate.setFill(Color.web("#C32420"));
                });

                break;
        }
    }

    private void offline_online(String off_on) {
        switch (off_on) {
            case "0":
                txtOffline.setText("Online");
                current_offline_mode = "0";
                break;
            case "1":
                txtOffline.setText("Offline");
                current_offline_mode = "1";
                break;
            default:
                txtOffline.setText("Something went wrong");
                current_offline_mode = "0";
                break;
        }
    }

    private void check_date_empty_fields(JFXDatePicker field, String value) {
        if (value != null && !value.isEmpty() && !field.isFocused()) {
            if (value.equals("null") || value.equals("")) {
                field.setStyle("-fx-border-width: 0");
                field.setStyle("-fx-border-color: #ed0739;");
            } else {
            }
        }
    }

    private void pop_up_start(String message, int width, int yes, int no, String tag) throws IOException, SQLException {

        if (invalid.equals("0")) {
            is_empty();
            if (ToolKit.isNull(tt)
                    //                        || ToolKit.isNull(lt)
                    || ToolKit.isNull(vt)
                    || ToolKit.isNull(vc)
                    || ToolKit.isNull(vs)
                    //                    || ToolKit.isNull(ts)
                    || ToolKit.isNull(tstd)
                    || ToolKit.isNull(vsn)
                    || ToolKit.isNull(bodyHeat)
                    || ToolKit.isNull(discHeat)
                    || ToolKit.isNull(noOfHole)
                    || ToolKit.isNull(pcd)
                    || ToolKit.isNull(docNo) //                    || ToolKit.isNull(allowableCount)
                    ) {

                check_text_empty_fields(txtValveSrNo, vsn);
                check_text_empty_fields(txtBodyHeatno, bodyHeat);
                check_text_empty_fields(txtDiscHeatno, discHeat);
                check_text_empty_fields(txtNoOfHole, noOfHole);
                check_text_empty_fields(txtPcd, pcd);
                check_text_empty_fields(txtDocNo, docNo);
                check_combo_empty_fields(cmbTestStd, tstd);
                check_combo_empty_fields(cmbTestType, tt);
                check_combo_empty_fields(cmbValveClass, vc);
                check_combo_empty_fields(cmbValveType, vt);
                check_combo_empty_fields(cmbValveSize, vs);

                Dialog.showForSometime("", "Please provide appropriate data", "Alert", 450, 10);
                dh.execute("UPDATE writedropdownplc set cycle_start='4' WHERE id='1' ", connect);

            } else {
                Platform.runLater(() -> {
                    Optional<ButtonType> option = Dialog.ConfirmationDialog("CONFIRMATION", message, width);
                    if (option.get() == ButtonType.YES) {
                        try {
                            disable_field();

                            cycleStatusThread();
                            you_can = false;
                            dh.execute("UPDATE writedropdownplc set cycle_start='0' WHERE id='1' ", connect);

                            String select_data = "SELECT * FROM `valve_data` ORDER BY id DESC LIMIT 1";
                            ResultSet rs_sele = dh.getData(select_data, connect);

                            if (rs_sele.next()) {
                                test_no = Integer.parseInt(rs_sele.getString("test_no"));
                                test_no++;
                                System.out.println("INSERT INTO `valve_data`( `test_no`, `test_type`, `valve_standards`, `hydro_set_pressure`, `holding_set`, `allowable_leakage`, `vsn`, `bodyHeat`, `discHeat`, `noOfHole`, `pcd`, `docNo`, `date`)VALUES ('" + test_no + "','" + tt + "','NA','" + txtHydroSetPressure.getText() + "','" + txtHoldingTime.getText() + "','" + allowableCount + "','" + vsn + "','" + bodyHeat + "','" + discHeat + "','" + noOfHole + "','" + pcd + "','" + docNo + "',NOW())");
                                dh.execute("INSERT INTO `valve_data`( `test_no`, `test_type`, `valve_standards`, `hydro_set_pressure`, `holding_set`, `allowable_leakage`, `vsn`, `bodyHeat`, `discHeat`, `noOfHole`, `pcd`, `docNo`, `date`)VALUES ('" + test_no + "','" + tt + "','NA','" + txtHydroSetPressure.getText() + "','" + txtHoldingTime.getText() + "','" + allowableCount + "','" + vsn + "','" + bodyHeat + "','" + discHeat + "','" + noOfHole + "','" + pcd + "','" + docNo + "',NOW())", connect);

                                //insert data into initia_vala_data
//                                String sp = " insert_test_init_sp('" + tt + "','NA','" + vt + "','" + vs + "','" + vc + "','" + cmbTypeOfSealing.getSelectionModel().getSelectedItem() + "','" + ts + "','" + st + "','" + ht + "','NA','" + dt + "','" + hsp + "','" + csp + "','" + pu + "','0','" + txtHydroSetPressure.getText() + "','" + txtHydraulicSetPressure.getText() + "','" + vsn + "','NA','NA','NA','" + pro + "','" + cust + "','" + operator_name + "','NA'," + test_no + ",'NA','NA','" + txtBodyHeatno.getText() + "','" + cmbPressuregage.getSelectionModel().getSelectedItem() + "','" + txtvendr.getText() + "','" + txtpono.getText() + "','" + txtjobno.getText() + "')";
//                                dh.execute_sp(sp, connect);
                            } else {
                                System.out.println("INSERT INTO `valve_data`( `test_no`, `test_type`, `valve_standards`, `hydro_set_pressure`, `holding_set`, `allowable_leakage`, `vsn`, `bodyHeat`, `discHeat`, `noOfHole`, `pcd`, `docNo`, `date`,`mt`)VALUES ('" + test_no + "','" + tt + "','NA','" + txtHydroSetPressure.getText() + "','" + txtHoldingTime.getText() + "','" + allowableCount + "','" + vsn + "','" + bodyHeat + "','" + discHeat + "','" + noOfHole + "','" + pcd + "','" + docNo + "',NOW()),'" + Session.get("mt") + "'");
                                dh.execute("INSERT INTO `valve_data`( `test_no`, `test_type`, `valve_standards`, `hydro_set_pressure`, `holding_set`, `allowable_leakage`, `vsn`, `bodyHeat`, `discHeat`, `noOfHole`, `pcd`, `docNo`, `date`,`mt`)VALUES ('" + test_no + "','" + tt + "','NA','" + txtHydroSetPressure.getText() + "','" + txtHoldingTime.getText() + "','" + allowableCount + "','" + vsn + "','" + bodyHeat + "','" + discHeat + "','" + noOfHole + "','" + pcd + "','" + docNo + "',NOW()),'" + Session.get("mt") + "'", connect);
                                //insert data into initia_vala_data
//                                String sp = " insert_test_init_sp('" + tt + "','NA','" + vt + "','" + vs + "','" + vc + "','" + cmbTypeOfSealing.getSelectionModel().getSelectedItem() + "','" + ts + "','" + st + "','" + ht + "','NA','" + dt + "','" + hsp + "','" + csp + "','" + pu + "','0','" + txtHydroSetPressure.getText() + "','" + txtHydraulicSetPressure.getText() + "','" + vsn + "','NA','NA','NA','" + pro + "','" + cust + "','" + operator_name + "','NA'," + test_no + ",'NA','NA','" + txtBodyHeatno.getText() + "','" + cmbPressuregage.getSelectionModel().getSelectedItem() + "','" + txtvendr.getText() + "','" + txtpono.getText() + "','" + txtjobno.getText() + "')";
//                                dh.execute_sp(sp, connect);
                            }
                            drawer.setVisible(false);
                            trend_initialize();
                            start_trend();
                            start_trend = true;
                            guage_initialize(0, Integer.parseInt(max), Integer.parseInt(green), pu);

                        } catch (SQLException ex) {
                            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (option.get() == ButtonType.NO) {
                        try {
                            dh.execute("UPDATE writedropdownplc set cycle_start='4' WHERE id='1' ", connect);
                        } catch (SQLException ex) {
                            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

            }
        } else {
            Dialog.showAndWait("INVALID SETTINGS.....");
        }

    }

    private void pop_up_timer(String message, int width, int ok, String tag) {
        Platform.runLater(() -> {
            Optional<ButtonType> option = Dialog.ConfirmationDialog_Single_button("CONFIRMATION", message, width);
            if (option.get() == ButtonType.OK) {
                try {
//
                    Thread.sleep(100);
                    if (message.equals("Start Hodling Timer")) {
                        dh.execute("UPDATE writedropdownplc set holding='0'", connect);
                        start_pressure_a = pressure_a;
                        start_pressure_b = pressure_b;
                        start_pressure_c = "0";
                        start_pressure_d = "0";
                        start_pressure_e = "0";
//                        leakageCheck();
//                        }
                    }
                    if (message.equals("Start Drain Timer")) {
                        dh.execute("UPDATE writedropdownplc set drain='0'", connect);
                    }
                    Session.set("Trend_Status", "Running");
                    check_pop_up();
                } catch (InterruptedException ex) {
                    System.err.println("Exception in Holding time pop up respond" + ex.getMessage());
                } catch (IOException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void check_pop_up() throws IOException {
        if (!"0".equals(popUps)) {
            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 18 0 0 ";
            System.out.println("cmd..." + cmd);
            Process child = Runtime.getRuntime().exec(cmd);
        }
    }
    volatile boolean stopCycleStatusThread;
    Thread cycleStatus;
    String current_cycle_status = "45";
    String pressure_a_side = "0";
    String pressure_b_side = "0";
    int delete_count = 0;
    int count_result = 0;
    int test_result_count = 0;
    boolean stop_pressure_get = true;

    private void cycleStatusThread() {

        stopCycleStatusThread = false;
        cycleStatus = new Thread(() -> {

            while (true) {
//                 Runtime.getRuntime().gc();
                try {
                    Thread.sleep(250);
                    try {
                        try {
                            //cycle status update

                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();

                            //Thread.interrupted();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("EXCEPTION IN CYCLE STATUS UPDATE cycleStatusThread : " + e.getLocalizedMessage());
                    }
                    try {
                        try {

                            if (ToolKit.isNull(overall_time)) {
                            } else {

                                if (overall_time.equals(current_overall_timer)) {
                                } else {
//                                                System.out.println("overall_time : " + overall_time);
                                    Platform.runLater(() -> {
                                        txtOverAllTime.setText(overall_time);
                                    });
                                    current_overall_timer = overall_time;

                                }
                            }

//Updating Overall Time Value: End
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();

                            System.out.println("EXCEPTION IN UPDATE OVERALL TIME DATA_UPDATE_THREAD : " + e.getMessage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        //getting All timers
                        switch (cycl_status) {
                            case "2":
                                try {
                                    if (s_time.equals(current_stabilization_timer)) {
                                    } else {
                                        try {
                                            Platform.runLater(() -> {
                                                txtStabilizationTimer.setText(s_time);
                                            });
                                            current_stabilization_timer = s_time;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "3":
                                try {
                                    if (h_time.equals(current_holding_timer)) {
                                    } else {
                                        try {
                                            Platform.runLater(() -> {
                                                txtHoldingTimer.setText(h_time);
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (h_time.equals(1) || h_time.equals(2) || h_time.equals(3) || h_time.equals(4) || h_time.equals(5)) {
                                            test_result_count = 0;
                                        }
                                        Platform.runLater(() -> {
                                            txtAtcualBubble.setText(bubble_counter);
                                        });
                                        try {
                                            pressure_a_side = new DecimalFormat("#").format(Double.parseDouble(pressure_a));
                                            pressure_b_side = new DecimalFormat("#").format(Double.parseDouble(pressure_b));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //Test_result_by_test_type

                                        if (test_result_by_type_check == 1) {
                                            String query = "INSERT INTO test_result_by_type (`valve_serial_no`,`test_no`,`test_type`,`hydro_pressure_a_side`,`hydro_pressure_b_side`,`date_time`,`test_count`) VALUES('" + vsn + "','" + test_no + "','" + tt + "','" + pressure_a_side + "','" + pressure_b_side + "',NOW(),'" + test_result_by_type_check + "')";
                                            try {
                                                dh.execute(query, connect);
                                            } catch (SQLException e) {
                                                System.out.println(String.valueOf("Test result by type:  " + e.getMessage()));
                                            }
                                        } else {
                                            if (delete_count == 0) {
                                                String delete_query = "DELETE FROM test_result_by_type WHERE valve_serial_no = '" + Session.get("Valve_Serial_No") + "' AND test_type = '" + Session.get("Test_Type") + "' AND test_no = '" + test_no + "';";
                                                try {
                                                    dh.execute(delete_query, connect);
                                                    delete_count++;
                                                } catch (SQLException e) {
                                                    System.out.println(String.valueOf("Test result by type:  " + e.getMessage()));
                                                }
                                            }
                                            String query = "INSERT INTO test_result_by_type (`valve_serial_no`,`test_no`,`test_type`,`hydro_pressure_a_side`,`hydro_pressure_b_side`,`date_time`,`test_count`) VALUES('" + vsn + "','" + test_no + "','" + tt + "','" + pressure_a_side + "','" + pressure_b_side + "',NOW(),'" + test_result_by_type_check + "')";
                                            try {
                                                dh.execute(query, connect);
                                            } catch (SQLException e) {
                                                System.out.println(String.valueOf("Test result by type:  " + e.getMessage()));
                                            }
                                        }
                                        current_holding_timer = h_time;
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
                            case "4":
                                try {

                                    if (d_time.equals("0") || d_time.equals("1") || d_time.equals("2")) {
                                        try {
                                            get_result(result);
                                            overall_time_end = txtOverAllTime.getText();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    if (d_time.equals(current_drain_timer)) {
                                    } else {
                                        try {
                                            Platform.runLater(() -> {
                                                txtDrainTimer.setText(d_time);
                                            });
                                            current_drain_timer = d_time;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    if (stop_pressure_get) {
//                                        if (Session.get("Test_Type").equals("HYDROSTATIC SEAT C SIDE")) {
//                                            stop_pressure_a = pressure_b;
//                                            stop_pressure_b = pressure_b;
//                                            stop_pressure_c = pressure_a;
//                                            stop_pressure_d = pressure_b;
//                                            stop_pressure_e = pressure_b;
//                                        } else if (Session.get("Test_Type").equals("HYDROSTATIC SEAT D SIDE")) {
//                                            stop_pressure_a = pressure_a;
//                                            stop_pressure_b = pressure_a;
//                                            stop_pressure_c = pressure_a;
//                                            stop_pressure_d = pressure_b;
//                                            stop_pressure_e = pressure_a;
//                                        } else if (Session.get("Test_Type").equals("HYDROSTATIC SEAT E SIDE")) {
//                                            stop_pressure_a = pressure_b;
//                                            stop_pressure_b = pressure_b;
//                                            stop_pressure_c = pressure_b;
//                                            stop_pressure_d = pressure_b;
//                                            stop_pressure_e = pressure_a;
//                                        } else {

                                        stop_pressure_a = pressure_a;
                                        stop_pressure_b = pressure_b;
                                        stop_pressure_c = "0";
                                        stop_pressure_d = "0";
                                        stop_pressure_e = "0";
                                        stop_pressure_get = false;
//                                        }
                                    }

//                                    if (d_d.equals(current_drain_delay)) {
//                                    } else {
//                                        try {
//                                            Platform.runLater(()->{
//                                            txtDrainDelay.setText(d_d);
//                                            });
//                                            current_drain_delay = d_d;
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;
//                            case "5":
//                                try {
//                                    if (d_time.equals(current_drain_timer)) {
//                                    } else {
//                                        try {
//                                            Platform.runLater(() -> {
//                                                txtDrainTimer.setText(d_time);
//                                            });
//                                            current_drain_timer = d_time;
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                                break;
                            case "5":
                                System.out.println("in case 5");
                                try {
                                    count_result = 0;
//                                    String lt = txtTypeOfLeakage.getText();
                                    String vt = cmbValveType.getSelectionModel().getSelectedItem();
                                    String vc = cmbValveClass.getSelectionModel().getSelectedItem();
                                    String vs = cmbValveSize.getSelectionModel().getSelectedItem();
                                    String hsp = txtHydroSetPressure.getText();
                                    String psu;
//                                    if (radiobar.isSelected()) {
//                                        psu = "bar";
//                                    } else if (radiopsi.isSelected()) {
//                                        psu = "psi";
//                                    } else {
//                                        psu = "kg/sqcm";
//                                    }

                                    if (test_result_count == 0) {
                                        String check_data = "SELECT test_result_id,test_no FROM test_result WHERE test_no = '" + test_no + "';";
                                        ResultSet check = dh.getData(check_data, connect);
                                        if (check.next()) {
                                            String delete_query = "DELETE FROM test_result WHERE test_no = '" + test_no + "';";
                                            try {
                                                dh.execute(delete_query, connect);
                                                delete_count++;
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                System.out.println(String.valueOf(e.getMessage()));
                                            }
                                        }
                                        Thread.sleep(15);

                                        String start_pressure_a_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_a));
                                        String start_pressure_b_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_b));
                                        String stop_pressure_a_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_a));
                                        String stop_pressure_b_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_b));
                                        // for dbb block
                                        String start_pressure_c_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_c));
                                        String start_pressure_d_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_d));
                                        String start_pressure_e_side = new DecimalFormat("#").format(Double.parseDouble(start_pressure_e));
                                        String stop_pressure_c_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_c));
                                        String stop_pressure_d_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_d));
                                        String stop_pressure_e_side = new DecimalFormat("#").format(Double.parseDouble(stop_pressure_e));
                                        String actLeak = " ";

                                        String query = "INSERT INTO test_result (`valve_serial_no`, `test_no`, `test_type`, `leakage_type`, `valve_type`,`valve_size`, `valve_class`, `actual_leakage`, `holding_time`,`over_all_time`, `hydro_set_pressure`,`start_pressure_a`,`start_pressure_b`,`stop_pressure_a`,`stop_pressure_b`, `pressure_unit`, `gauge_serial_no`, `guage_calibration_date`, `test_result`, `date_time`,`mt`) VALUES('" + vsn + "','" + test_no + "','" + cmbTestType.getSelectionModel().getSelectedItem() + "','" + cmbLeakageType.getSelectionModel().getSelectedItem() + "','" + vt + "','" + vs + "','" + vc + "','" + txtAtcualBubble.getText() + "','" + holding_time + "','" + overall_time_end + "','" + hsp + "','" + start_pressure_a_side + "','" + start_pressure_b_side + "','" + stop_pressure_a_side + "','" + stop_pressure_b_side + "','" + pu+ "','" + cmbPressuregage.getSelectionModel().getSelectedItem() + "','NA','" + result + "',NOW(),'" + Session.get("mt") + "')";
                                        System.out.println("query test result : " + query);
                                        try {
                                            dh.execute(query, connect);
                                            test_result_count++;
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                            System.out.println(String.valueOf("TEST RESULT : " + e.getMessage()));
                                        }

                                    }
//                                    screen_reopen();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                dropbox("TestScreen.fxml", false);
                                break;
                            default:
                                break;

                        }
                        if (cycl_status.equals(current_cycle_status)) {
                        } else {
                            try {
                                cycle_status(cycl_status);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        if (stopCycleStatusThread) {
                            cycleStatus.stop();
                            break;

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("EXCEPTION IN getting All timers cycleStatusThread : " + e.getLocalizedMessage());
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException intex) {
                            System.err.println("Interupt in cycleStatusThread Exception : " + intex);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Exception in cycleStatusThread: " + e.getMessage());
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException intex) {
                        System.err.println("Interupt in cycleStatusThread Exception : " + intex);
                    }
                }

                //to clear garbage data
            }

        }, "cycleStatusThread");
        cycleStatus.start();
//       
//                  updateTime=null;
//                  cycleStatus=null;

    }

    private void cycle_status(String status) {
        switch (status) {
            case "0":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("");
                });
//                txtCycleStatus.setText("");
                current_cycle_status = "0";
                break;
            case "1":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("CYCLE RUNNING");
                });
                current_cycle_status = "1";
                break;
            case "2":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("STABILIZATION TIMER RUNNING");
                });

                current_cycle_status = "2";
                break;
            case "3":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("HOLDING TIMER  RUNNING");
                });

                current_cycle_status = "3";
                break;
            case "4":
                Platform.runLater(() -> {
                    txtCycleStatus.setText("DRAIN TIMER RUNNING");
                });

                current_cycle_status = "4";
                break;
            case "5":
                enable_field();
                Platform.runLater(() -> {
                    txtCycleStatus.setText("CYCLE COMPLETE");
                });
                you_can_change = true;
                oat = txtOverAllTime.getText();

                empty_timers();

                System.out.println("start_trend LOW");

                current_cycle_status = "5";
                //cycle status stop
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                GaugeActualHydraulic.setVisible(false);
                Gaugehydro.setVisible(false);
                drawer.setVisible(false);
                start_trend = false;
                break;
            case "6":
                enable_field();
                System.out.println("force Stop");
                count_result = 0;
                Platform.runLater(() -> {
                    txtCycleStatus.setText("FORCE STOP");
                });
                empty_timers();
//                txtResult.setStyle("-fx-background-color: derive(#FFFFFF,100%); -fx-text-inner-color:black; -fx-font-size: 20px;");

                start_trend = false;
                you_can_change = true;
                System.out.println("start_trend LOW");
                current_cycle_status = "6";
                //cycle status stop
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                break;

//            case "7":
//                count_result = 0;
//                Platform.runLater(() -> {
//                    txtCycleStatus.setText("FORCE STOP");
//                });
////                empty_timers();
//                txtResult.setStyle("-fx-background-color: derive(#FFFFFF,100%); -fx-text-inner-color:black; -fx-font-size: 20px;");
//
//                start_trend = false;
//                you_can_change = true;
//                System.out.println("start_trend LOW");
//                current_cycle_status = "7";
//                break;
            case "7":
                enable_field();
                count_result = 0;
                Platform.runLater(() -> {
                    txtCycleStatus.setText("EMERGENCY");
                });
                you_can_change = true;
                empty_timers();
//                txtResult.setStyle("-fx-background-color: derive(#FFFFFF,100%); -fx-text-inner-color:black; -fx-font-size: 20px;");

                start_trend = false;
//                stop_read();
                System.out.println("start_trend LOW");
                current_cycle_status = "7";
                //cycle status stop
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                break;
            default:
                txtCycleStatus.setText("");
                you_can_change = true;
                current_cycle_status = "0";
                stopCycleStatusThread = true;
                try {
                    cycleStatus.stop();
                } catch (Exception e) {
                }
                break;
        }
    }

    private void empty_timers() {
        txtCycleStatus.setText("");
        txtStabilizationTimer.setText("");
        txtHoldingTimer.setText("");
        txtDrainTimer.setText("");
        txtOverAllTime.setText("");
        txtResult.setText("");
        txtAtcualBubble.setText("");
        test_result_by_type_check = 0;
    }

    private void get_result(String result) {
        if (result.equals("1")) {

            txtResult.setStyle(" -fx-text-inner-color:GREEN; -fx-font-size: 20px;");
            Platform.runLater(() -> {
                txtResult.setText("TEST OK");
            });
        } else {
            txtResult.setStyle(" -fx-text-inner-color:RED; -fx-font-size: 20px;");
            Platform.runLater(() -> {
                txtResult.setText("TEST NOT OK");
            });
        }

//            dh.execute("TRUNCATE TABLE all_tag_read", connect);
    }

//    private void pop_up_start_hold(String message, int width, int yes, int no) {
//        Platform.runLater(() -> {
//            Optional<ButtonType> option = Dialog.ConfirmationDialog("CONFIRMATION", message, width);
//            if (option.get() == ButtonType.YES) {
//                try {
////                    hold_flag = 1;
//                    //cycle Start.
//                    String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 105 1 1";
//                    System.out.println("cmd..." + cmd);
//                    Process child = Runtime.getRuntime().exec(cmd);
//                    child.waitFor();
//
//                    String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 105 1 0";
//                    Process child1 = Runtime.getRuntime().exec(cmd1);
//                    child1.waitFor();
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//
//    }
    NumberAxis yAxis;

    private void trend_initialize() throws IOException {
        //            AnchorPane trend = FXMLLoader.load(getClass().getResource("TrendScreen.fxml"));
        try {
            xAxis = new NumberAxis(0, 1000, 1);
            xAxis.setForceZeroInRange(true);
            xAxis.setAutoRanging(true);
            xAxis.setTickLabelsVisible(true);
            xAxis.setTickMarkVisible(true);
            xAxis.setMinorTickVisible(true);
            yAxis = new NumberAxis();

            // Create a LineChart
            lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
                // Override to remove symbols on each data point
                @Override
                protected void dataItemAdded(XYChart.Series<Number, Number> series, int itemIndex, XYChart.Data<Number, Number> item) {
                }
            };
//
            String test_type = cmbTestType.getSelectionModel().getSelectedItem();
            lineChart.setAnimated(false);
            lineChart.setTitle("");
            lineChart.getData().clear();
//            if (!lineChart.getData().isEmpty()) {
//                System.out.println("Remove Series");
//        lineChart.getData().remove(/*(lineChart.getData().size()-1)*/0);
//    }
            series1.getData().clear();
            series2.getData().clear();
            series3.getData().clear();
            series1.getData().removeAll();
            series2.getData().removeAll();
            series3.getData().removeAll();
            lineChart.setHorizontalGridLinesVisible(true);
            if (test_type.equals("HYDROSTATIC SHELL") || test_type.equals("BACK SEAT TEST")) {
                series1.setName("Hydro Pressure");
                series3.setName("Hydraulic Pressure");

                lineChart.getData().addAll(series1, series3);

            } else {
                // Set Name for Series
                series1.setName("Hydro Pressure A Side");
                series2.setName("Hydro Pressure B Side");
                series3.setName("Hydraulic Pressure");
                // Add Chart Series
                lineChart.getData().addAll(series1, series3, series2);
            }
            drawer.setMinWidth(400);
            drawer.setSidePane(lineChart);
            drawer.setOverLayVisible(false);

        } catch (Exception e) {
        }

    }
    int count_executer_status = 0;
    boolean initial_start_trend = true;

    private void start_trend() {
        //For UI updation
//        Platform.runLater(() -> {
        if (initial_start_trend) {
            initial_start_trend = false;
        } else {
            xSeriesData = 0;
            System.out.println("Clearing dataQue");
            dataQ1.clear();
            dataQ2.clear();
            dataQ3.clear();
//            Platform.runLater(() -> {
            series1.getData().clear();
            series2.getData().clear();
            series3.getData().clear();
            series1.getData().removeAll(dataQ1);
            series2.getData().removeAll(dataQ2);
            series3.getData().removeAll(dataQ3);

//            });
        }

        xAxis.setLowerBound(0);
        count_executer_status++;
        //upate ui
        Platform.runLater(() -> {
            //to clear garbage data

            System.out.println("Cleared dataQue");
            executor = Executors.newCachedThreadPool((Runnable r) -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });
            count_executer_status = 0;
            AddToQueue addToQueue = new AddToQueue();
            executor.execute(addToQueue);
            //-- Prepare Timeline
            prepareTimeline();
        });
//         Runtime.getRuntime().gc();
    }

    @FXML
    private void btnAlarmAction(ActionEvent event) {
        if (you_can_change) {
            try {
                stopCycleStatusThread = true;
                stop_mode = true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AlarmScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 2);
        }

    }

    private class AddToQueue implements Runnable {

        String query = "SELECT * FROM initialinitmain ORDER BY id DESC LIMIT 1";
        ResultSet rs;

        @Override
        public void run() {
//run after some time 
            Platform.runLater(() -> {
                try {
                    // add a item of random data to queue.
                    if (start_trend) {
                        rs = dh.getData(query, connect);
                        if (rs.next()) {
                            double dq1 = 0.0;
                            double dq2 = 0.0;
                            double dq3 = 0.0;
                            try {
                                dq1 = Double.parseDouble(rs.getString("hydro_actual_a_pressure"));
                                dq2 = Double.parseDouble(rs.getString("hydro_actual_b_pressure"));
                                dq3 = Double.parseDouble(rs.getString("hydraulic_actual_pressure"));
                            } catch (Exception e) {
                                System.err.println("This is an Error of Trend data where dq1 and dq2 defined: " + e.getMessage());
                            }
//                            System.out.println("dq1 : " + dq1);
                            dataQ1.add(dq1);
                            dataQ2.add(dq2);
                            dataQ3.add(dq3);
                        }

                        Thread.sleep(100);
                        //System.out.println("running");
                        executor.execute(this);
                    } else {
                        if (count_executer_status > 1) {
                            //System.out.println("Stopping");
                            executor.shutdown();
                        } else {

                        }

                    }

                } catch (Exception ex) {
                    System.err.println("This is an Error of Trend data in second try below dq1 and dq2 defined: " + ex.getMessage());

                    executor.shutdown();
                    //kill the thread
//                 Thread.interrupted();
                }
            });
        }

    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {

        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();

    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) {
            //-- add 20 numbers to the plot+
            if (dataQ1.isEmpty()) {
                break;
            }
//            Platform.runLater(() -> {
            series1.getData().add(new XYChart.Data<>(Integer.parseInt(current_overall_timer), dataQ1.remove()));
            series2.getData().add(new XYChart.Data<>(Integer.parseInt(current_overall_timer), dataQ2.remove()));
            series3.getData().add(new XYChart.Data<>(Integer.parseInt(current_overall_timer), dataQ3.remove()));
//            });
        }
//        // update
        Platform.runLater(() -> {
            xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
            xAxis.setUpperBound(xSeriesData - 1);

        });

    }

    private void btnBubbleAction(ActionEvent event) {

        ToolKit.tagWrite("B3:37/1", "1");
        ToolKit.tagWrite("B3:37/2", "1");
    }

    @FXML
    private void radiobarAction(ActionEvent event) throws IOException, InterruptedException, SQLException {
        pu = "bar";
        dh.execute("UPDATE writedropdownplc set unit='0'", connect);
        txtunithydraulic.setText("bar");
        txtunithydro.setText("bar");

    }

    @FXML
    private void radiopsiAction(ActionEvent event) throws IOException, InterruptedException, SQLException {

        dh.execute("UPDATE writedropdownplc set unit='1'", connect);
        txtunithydraulic.setText("psi");
        txtunithydro.setText("psi");
        pu = "psi";
    }

    @FXML
    private void radiokgAction(ActionEvent event) throws IOException, InterruptedException, SQLException {
        dh.execute("UPDATE writedropdownplc set unit='2'", connect);
        txtunithydraulic.setText("kg/sqcm");
        txtunithydro.setText("kg/sqcm");
        pu = "kg/sqcm";

    }
    public static Stage catStage;

    @FXML
    private void btngaugeAction(ActionEvent event) throws IOException {
        //Open Gauge calibration window
        Parent root = FXMLLoader.load(getClass().getResource("GaugeCalibration.fxml"));
        Platform.runLater(() -> {
//                        cmbTestStd.getSelectionModel().select(0);
            catStage = new Stage(StageStyle.UNDECORATED);
            catStage.setAlwaysOnTop(true);
            Scene scene = new Scene(root, 1330, 350);

            catStage.setScene(scene);
            catStage.show();
        });

    }

    @FXML
    private void cmbPressuregageAction(ActionEvent event) throws SQLException {
        String serial = cmbPressuregage.getSelectionModel().getSelectedItem();
        String range = "SELECT gauge_range FROM `gauge_description` WHERE gauge_description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "' ";
        System.out.println("range " + range);
        ResultSet rs_rang = dh.getData(range, connect);
        if (rs_rang.next()) {
            txtrange.setText(rs_rang.getString("gauge_range"));
        }
    }

    @FXML
    private void txtHydraulicSetPressureAction(ActionEvent event) throws InterruptedException {
        try {
            String test = txtHydraulicSetPressure.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc2.py 40 0 " + test;
            System.out.println("cmd_hydro : " + cmd);
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void txtStabilizationAction(ActionEvent event) throws InterruptedException {
        try {
            String s_time1 = txtStabilization.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_Dword.py 52 0 " + s_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtDrainTimeAction(ActionEvent event) throws InterruptedException {
        try {
            String d_time1 = txtStabilization.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_Dword.py 62 0 " + d_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cmbTypeOfSealingAction(ActionEvent event) throws SQLException {
        int index = cmbTypeOfSealing.getSelectionModel().getSelectedIndex();
        dh.execute("UPDATE writedropdownplc set typeSealing='" + index + "'", connect);
    }

    @FXML
    private void cmbLeakageTypeAction(ActionEvent event) throws IOException, InterruptedException {

    }

    @FXML
    private void txtAllowableAction(ActionEvent event) {
        try {
            ToolKit.validateNumberField(txtAllowable);
            String count = txtAllowable.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_word.py 104 0 " + count;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtheatnoKeyPressed(KeyEvent event) {
//        try {
//            ResultSet rs_p = dh.getData("SELECT serial FROM gauge_data WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'", connect);
//            System.out.println("SELECT serial FROM gauge_data WHERE description='" + cmbValveClass.getSelectionModel().getSelectedItem() + "'");
//            cmbPressuregage.getItems().clear();
//            while (rs_p.next()) {
//                cmbPressuregage.getItems().addAll(rs_p.getString("serial"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void cmbModelAction(ActionEvent event) {
    }

    @FXML
    private void txtStabilizationTimeAction(ActionEvent event) {

    }

    public void guage_initialize(int min, int max, int green, String unit) throws SQLException {
        Gaugehydro.clearAreas();
        Gaugehydro.clearTickMarkSections();
        Gaugehydro.clearTickLabelSections();
        Gaugehydro.clearSections();
        Gaugehydro.clearCustomTickLabels();
        textHydro.setVisible(true);
        textHydro1.setVisible(true);
        Gaugehydro.setVisible(true);

        GaugeActualHydraulic.clearAreas();
        GaugeActualHydraulic.clearTickMarkSections();
        GaugeActualHydraulic.clearTickLabelSections();
        GaugeActualHydraulic.clearSections();
        GaugeActualHydraulic.clearCustomTickLabels();
        GaugeActualHydraulic.setVisible(true);

        try {
            //Hydro gauge visible property
            Gaugehydro.sectionsVisibleProperty().set(true);  //Hydro gauge max value
            Gaugehydro.setMaxValue(max);

            //Hydro gauge major tick space
            if (max < 100) {
                if (max % 10 == 0) {
                    Gaugehydro.setMajorTickSpace(10);

                    Gaugehydro.setMinorTickSpace(5);

                } else {
                    if (max < 10) {
                    } else {
                        int space = max / 10;
                        Gaugehydro.setMajorTickSpace(max / space);
                        Gaugehydro.setMinorTickSpace(1);
                    }
                }
            } else if (max > 1500) {
                Gaugehydro.setMajorTickSpace(max / 1000);
                Gaugehydro.setMinorTickSpace(250);
            } else if (max > 1000 && max < 1500) {
                Gaugehydro.setMajorTickSpace(max / 200);
                Gaugehydro.setMinorTickSpace(50);
            } else if (max > 500 && max < 1000) {
                Gaugehydro.setMajorTickSpace(max / 100);
                Gaugehydro.setMinorTickSpace(25);
            } else if (max < 500 && max > 100) {
                Gaugehydro.setMajorTickSpace(max / 50);
                Gaugehydro.setMinorTickSpace(15);
            }
            //Hydro gauge green zone range
            Gaugehydro.addSection(new Section(0, green, color.GaugeGreen));

            //Hydro gauge red zone range
            Gaugehydro.addSection(new Section(green + 1, max, color.GaugeRed));

            switch (unit) {
                case "bar":
                    gauge_clamping_red = 420;
                    bar_psi(420, 600);
                    break;
                case "psi":
                    gauge_clamping_red = 6090;
                    bar_psi(6090, 8700);
                    break;
                case "kg/sqcm":
                    gauge_clamping_red = 425;
                    bar_psi(425, 620);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            System.out.println("Error in gauge initialization " + e.getMessage());
        }

    }

    public void bar_psi(int green, int max) {

        GaugeActualHydraulic.setMaxValue(max);
        if (max < 100) {
            if (max % 10 == 0) {
                Platform.runLater(() -> {
                    GaugeActualHydraulic.setMajorTickSpace(10);

                    GaugeActualHydraulic.setMinorTickSpace(5);

                });
            } else {
                int space = max / 10;
                Platform.runLater(() -> {
                    GaugeActualHydraulic.setMajorTickSpace(max / space);
                    ;
                });
            }
        } else if (max > 1000) {
            Platform.runLater(() -> {
                GaugeActualHydraulic.setMajorTickSpace(max / 1000);

                GaugeActualHydraulic.setMinorTickSpace(250);

            });
        } else {
            Platform.runLater(() -> {
                GaugeActualHydraulic.setMajorTickSpace(max / 100);

                GaugeActualHydraulic.setMinorTickSpace(50);

            });
        }

        //Clamping gauge visible properties
        GaugeActualHydraulic.sectionsVisibleProperty().set(true);

        Platform.runLater(() -> {
            GaugeActualHydraulic.addSection(new Section(0, green, colors.GaugeGreen));

            GaugeActualHydraulic.addSection(new Section(green + 1, max, colors.GaugeRed));

            GaugeActualHydraulic.needleColorProperty().setValue(colors.black);
        });
    }

    Thread mode;
    public static volatile boolean stop_mode = false;
    String over_all = "0";
    DoubleProperty HydroAct;
//    int firstBindingGauge = 0, hold_flag = 0, hold_flag_pop = 0;

    public String flag_test = "0";
    int check_drain = 0;

    private void screen_reopen() {
        try {

            mode.stop();
            cycleStatus.stop();
//            Thread.sleep(100);
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (IOException ex) {
                    Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ToolKit.unloadScreen(btnAdmin);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        });

    }

    private void btnStopAction(ActionEvent event) throws InterruptedException, IOException {
        String cmd = "python E:\\E1257\\python_plc\\write_plc_bool.py 1 7 1";
        Process child = Runtime.getRuntime().exec(cmd);
        child.waitFor();

        String cmd1 = "python E:\\E1257\\python_plc\\write_plc_bool.py 1 7 0";
        Process child1 = Runtime.getRuntime().exec(cmd1);
        child1.waitFor();

        set_empty();
        enable_field();
        btnStart.setDisable(false);
        Gaugehydro.clearSections();
        Gaugehydro.setVisible(false);
        start_trend = false;
        textHydro.setVisible(false);
        textHydro1.setVisible(false);
        hboxtrend.setVisible(false);
        stop_mode = true;
        you_can = true;
        System.out.println("Trend Low");
        txtCycleStatus.setText("");
        txtOverAllTime.setText("");
        txtHoldingTime.setText("");

//        txtremark.setText("");
        screen_reopen();
    }

    @FXML
    private void btnLoginAction(ActionEvent event) {
        if (you_can_change) {
            try {
                stopCycleStatusThread = true;
                stop_mode = true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 3);
        }
    }

    @FXML
    private void cmbValveTypeAction(ActionEvent event) throws SQLException {
        int index = cmbValveType.getSelectionModel().getSelectedIndex();
        System.out.println("UPDATE writedropdownplc set valveType='" + index + "'");
        dh.execute("UPDATE writedropdownplc set valveType='" + index + "'", connect);

        cmbValveSize.getItems().clear();
        System.out.println("SELECT valve_size FROM valve_size WHERE AVK_Model='" + cmbValveType.getSelectionModel().getSelectedItem() + "' Order BY valve_size_id ASC");
        ResultSet rs = dh.getData("SELECT valve_size FROM valve_size WHERE AVK_Model='" + cmbValveType.getSelectionModel().getSelectedItem() + "' Order BY valve_size_id ASC ", connect);

        while (rs.next()) {
            cmbValveSize.getItems().add(rs.getString("valve_size"));
        }
    }

    @FXML
    private void cmbValveClassAction(ActionEvent event) throws SQLException {
        int index = cmbValveClass.getSelectionModel().getSelectedIndex();

        dh.execute("UPDATE writedropdownplc set valveClass='" + index + "'", connect);
    }

    @FXML
    private void cmbValveSizeAction(ActionEvent event) throws SQLException {
        int index = cmbValveSize.getSelectionModel().getSelectedIndex();

        dh.execute("UPDATE writedropdownplc set valveSize='" + index + "'", connect);

    }

    @FXML
    private void cmbTestStdAction(ActionEvent event) throws SQLException {
        int index = cmbTestStd.getSelectionModel().getSelectedIndex();
        dh.execute("UPDATE writedropdownplc set testStd='" + index + "'", connect);
        int index1 = cmbTestStd.getSelectionModel().getSelectedIndex();
        if (cmbTestStd.getSelectionModel().getSelectedItem().equals("Customized")) {
            try {
                if (Session.get("catAccess").equals("granted")) {
                    cust_flag = 0;
                    txtHydroSetPressure.setEditable(true);
                    txtHydraulicSetPressure.setEditable(true);
                    txtStabilization.setEditable(true);
                    txtHoldingTime.setEditable(true);
                    txtDrainTime.setEditable(true);
                    dh.execute("UPDATE writedropdownplc set testStd='" + index + "'", connect);
                    txtHoldingTime.setEditable(true);
                    txtHydraulicSetPressure.setEditable(true);
                    txtHydroSetPressure.setEditable(true);
                } else {
                    Parent root = FXMLLoader.load(getClass().getResource("CategoryLogin.fxml"));
                    Platform.runLater(() -> {
                        cmbTestStd.getSelectionModel().select(0);
                        catStage = new Stage(StageStyle.UNDECORATED);
                        catStage.setAlwaysOnTop(true);
                        Scene scene = new Scene(root, 600, 250);

                        catStage.setScene(scene);
                        catStage.show();
                    });
                    Thread catThread = new Thread() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    if (Session.get("catAccess").equals("granted")) {
                                        cust_flag = 0;
                                        txtHydroSetPressure.setEditable(true);
                                        txtHydraulicSetPressure.setEditable(true);
                                        txtStabilization.setEditable(true);
                                        txtHoldingTime.setEditable(true);
                                        txtDrainTime.setEditable(true);
                                        System.out.println("granted");
                                        Platform.runLater(() -> {
                                            cmbTestStd.getSelectionModel().select(1);
                                        });
                                        break;
                                    } else if (Session.get("catAccess").equals("not granted")) {
                                        System.out.println("not granted");
                                        Platform.runLater(() -> {
                                            cmbTestStd.getSelectionModel().select(0);
                                        });
                                        break;
                                    } else {
                                        System.out.println("not");
                                        Thread.sleep(200);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    catThread.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            cust_flag = 1;
            dh.execute("UPDATE writedropdownplc set testStd='" + index + "'", connect);
            txtHoldingTime.setEditable(false);
            txtHydraulicSetPressure.setEditable(false);
            txtHydroSetPressure.setEditable(false);
        }
    }

    @FXML
    private void btnReportAction(ActionEvent event) {
        if (you_can_change) {
            try {
                stopCycleStatusThread = true;
                stop_mode = true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 2);
        }
    }

    @FXML
    private void btnAdminAction(ActionEvent event) {
        if (you_can_change) {
            try {
                stopCycleStatusThread = true;
                stop_mode = true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
            Platform.runLater(() -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                    ToolKit.loadScreen(root);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
        } else {
            Dialog.showForSometime("Alert", "Cycle is running you can;t Go!!!!", "alert", 450, 2);
        }
    }

    @FXML
    private void cmbTestTypeAction(ActionEvent event) throws InterruptedException, SQLException {
        int index = cmbTestType.getSelectionModel().getSelectedIndex();
        System.out.println("UPDATE writedropdownplc set testType='" + index + "'");
        dh.execute("UPDATE writedropdownplc set testType='" + index + "'", connect);
        leakage_type();

    }

    @FXML
    private void txtHydroSetPressureAction(ActionEvent event) throws InterruptedException {

        try {
            String test = txtHydroSetPressure.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc2.py 44 0 " + test;
            System.out.println("cmd_hydro : " + cmd);
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtHydroPressureKeyRelease(KeyEvent event) {

    }

    @FXML
    private void txtHoldingTimeAction(ActionEvent event) throws InterruptedException {
        try {
            String h_time1 = txtHoldingTime.getText();
            String cmd = "python E:\\E1257\\python_plc\\write_plc_Dword.py 56 0 " + h_time1;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(TestScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void txtHoldingTimeKeyRelease(KeyEvent event) {
        ToolKit.validateNumberField(txtHoldingTime);
        String hoding = txtHoldingTime.getText();

        if (hoding.equals("")) {

        } else {

        }
    }
}
