/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Gajanan
 */
public class AlarmScreenController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text txtdate;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnAlarm;
    @FXML
    private JFXButton btnHydraMotor;
    @FXML
    private JFXButton btnPreMotor;
    @FXML
    private JFXButton btnValveDrain;
    @FXML
    private JFXButton btnEmr;
    @FXML
    private JFXButton btnPresTrans16MT;
    @FXML
    private JFXButton btnPresTrans90Mt;
    @FXML
    private JFXButton btnHydraulic16MT;
    @FXML
    private JFXButton btnHydraulic90MT;
    @FXML
    private Text lableTitle;
    @FXML
    private Text txtMode;

    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();
    String current_machine_mode, previous_hydro_set_pressure, current_offline_mode, current_oil_level, current_air_inlet, current_temperature, current_5hp, current_2hp, current_m3, current_m4, current_m5, current_pt1, current_pt2, current_hm, current_pm, current_vd;
    @FXML
    private JFXButton btnInitialScreen;
    @FXML
    private JFXButton btnOilLevel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date_time();
        machine_mode();
        
    }    
    
    
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
    public static volatile boolean stop_mode = false;
    Thread mode;
    private void machine_mode() {
        System.out.println("in machine mode");
        stop_mode = false;
        mode = new Thread(() -> {
            while (true) {
                try {
                    
                    System.out.println("");
                    Thread.sleep(150);
                    if (stop_mode) {
                        break;
                    }

                    String display = "SELECT * FROM alarm_tags ORDER BY alarm_tags_id DESC LIMIT 1";
                    ResultSet rs = dh.getData(display, connect);
                    if (rs.next()) {

                        if (rs.getString("machine_mode").equals(current_machine_mode)) {
                        } else {
                             String value = rs.getString("machine_mode");
                            Platform.runLater(() -> {
                                mode(value, btnEmr, current_machine_mode);
                            });
                        }
                        if (stop_mode) {
                            break;
                        }

                        if (rs.getString("90_side_pt").equals(current_pt1)) {
                        } else {
                            String value = rs.getString("90_side_pt");
                            Platform.runLater(() -> {
                                pressure_status(value, btnPresTrans90Mt, current_pt1);
                                
                            });
                        }
                        if (rs.getString("oil_level").equals(current_pt1)) {
                        } else {
                            String value = rs.getString("oil_level");
                            Platform.runLater(() -> {
                                pressure_status(value, btnOilLevel, current_pt1);
                                
                            });
                        }
                        if (stop_mode) {
                            break;
                        }
                        if (rs.getString("16_side_pt").equals(current_pt2)) {
                        } else {
                            String value = rs.getString("16_side_pt");
                            Platform.runLater(() -> {
                                pressure_status(value, btnPresTrans16MT, current_pt2);
                            });
                        }
                        
                        if (rs.getString("90_side_hydraulic_pt").equals(current_pt1)) {
                        } else {
                            String value = rs.getString("90_side_hydraulic_pt");
                            Platform.runLater(() -> {
                                pressure_status(value, btnHydraulic90MT, current_pt1);
                                
                            });
                        }
                        if (stop_mode) {
                            break;
                        }
                        if (rs.getString("16_side_hydraulic_pt").equals(current_pt2)) {
                        } else {
                            String value = rs.getString("16_side_hydraulic_pt");
                            Platform.runLater(() -> {
                                pressure_status(value, btnHydraulic16MT, current_pt2);
                            });
                        }
                        if (rs.getString("hydraulic_motor").equals(current_hm)) {
                        } else {
                            String value = rs.getString("hydraulic_motor");
                            Platform.runLater(() -> {
                                motor_status(value, btnHydraMotor, current_hm);
                            });
                        }
                        if (stop_mode) {
                            break;
                        }
                        if (rs.getString("pre_fiill_motor").equals(current_pm)) {
                        } else {
                            String value = rs.getString("pre_fiill_motor");
                            Platform.runLater(() -> {
                                motor_status(value, btnPreMotor, current_pm);
                            });
                        }
                        if (stop_mode) {
                            break;
                        }
                        if (rs.getString("drain_motor").equals(current_vd)) {
                        } else {
                            String value = rs.getString("drain_motor");
                            Platform.runLater(() -> {
                                motor_status(value, btnValveDrain, current_vd);
                            });
                        }

                    }
                    
                    String truncat_query="TRUNCATE TABLE alarm_tags";
                    dh.execute(truncat_query, connect);
                } catch (InterruptedException | SQLException e) {

                }
                if (stop_mode) {
                    break;
                }
            }
        }, "machineModeThreadAlarmScreen");
        mode.setDaemon(true);
        mode.start();

    }

    private void mode(String status, JFXButton button, String value_changes) {
        switch (status) {
            case "0":
                button.setText("OFF");
                button.setStyle("-fx-background-color:#388e3c");
                value_changes = "0";
                break;
            case "1":
                button.setText("ON");
                button.setStyle("-fx-background-color:#ac0800;");
                value_changes = "1";
                break;

            default:
                value_changes = "1";
                break;
        }
    }
    
    
    private void pressure_status(String status, JFXButton button, String value_changes) {
        switch (status) {
            case "0":
                button.setText("DISCONNECTED");
                button.setStyle("-fx-background-color:#ac0800;");
                value_changes = "0";
                break;
            case "1":
                button.setText("CONNECTED");
                button.setStyle("-fx-background-color:#388e3c;");
                value_changes = "1";
                break;

            default:
                value_changes = "0";
                break;
        }
    }
    
    private void motor_status(String status, JFXButton button, String value_changes) {
        switch (status) {
            case "0":
                button.setText("OFF");
                button.setStyle("-fx-background-color:#4b636e;");
                value_changes = "0";
                break;
            case "1":
                button.setText("ON");
                button.setStyle("-fx-background-color:#388e3c;");
                value_changes = "1";
                break;
            case "2":
                button.setText("TRIP");
                button.setStyle("-fx-background-color:#ac0800;");
                value_changes = "2";
                break;
            default:
                value_changes = "0";
                break;
        }
    }
    @FXML
    private void btnLoginAction(ActionEvent event) {
         Platform.runLater(() -> {
              try {
//                stopCycleStatusThread=true;
                stop_mode=true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    ToolKit.loadScreen(root);
                     stop_mode=true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
    }


    @FXML
    private void btnReportAction(ActionEvent event) {
        Platform.runLater(() -> {
             try {
//                stopCycleStatusThread=true;
                stop_mode=true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
                    ToolKit.loadScreen(root);
                     stop_mode=true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
    }

    @FXML
    private void btnAdminAction(ActionEvent event) {
         Platform.runLater(() -> {
              try {
//                stopCycleStatusThread=true;
                stop_mode=true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                    ToolKit.loadScreen(root);
                     stop_mode=true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
    }


    @FXML
    private void btnAlarmAction(ActionEvent event) {
    }

    @FXML
    private void btnInitialScreenAction(ActionEvent event) {
         Platform.runLater(() -> {
              try {
                
                stop_mode=true;
                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
                try {
                       
                    Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                    ToolKit.loadScreen(root);
                  
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ToolKit.unloadScreen(btnLogin);
    }

    
}
