/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nsp
 */
public class GaugecalibrationController implements Initializable {

//    private JFXTextField txtSTN1Hydro;
//    private JFXTextField txtHydrolic;
//    private JFXTextField txtSTN1Actual;
//    private JFXTextField txtHydrolicActual;
//    private JFXTextField txtSTN1Holding;
//    private JFXTextField txtHydrolicHolding;
    Thread ReadPressure, update;
    String hydro1pressure, hydro1pressureb,
            hydroactualcount, hydroactualcountb, holdingcorrection, hydrolicPressure, hydrolicActualCount;
    @FXML
    private JFXButton btnClose;
    private JFXTextField txtHydroHolding;
    @FXML
    private JFXButton btnHydrozeroA;
    @FXML
    private JFXTextField txtHydroHoldingA;
    @FXML
    private JFXButton btnHydroApplyA;
    @FXML
    private JFXTextField txtHydroA;
    @FXML
    private JFXTextField txtmaxActualA;
    @FXML
    private JFXTextField txtHydroB;
    @FXML
    private JFXButton btnHydrozeroB;
    @FXML
    private JFXTextField txtmaxActualB;
    @FXML
    private JFXTextField txtHydroHoldingB;
    @FXML
    private JFXButton btnHydroApplyB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

//        readGaugeCount();
//        updatevalue();
            read_pressure();
            txtHydroA.setEditable(false);
            txtmaxActualA.setEditable(false);
            txtHydroB.setEditable(false);
            txtmaxActualB.setEditable(false);
        } catch (IOException ex) {
            Logger.getLogger(GaugecalibrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void read_pressure() throws IOException {
        Process child = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/read_pressure.py");
        InputStream lsOut = child.getInputStream();
        InputStreamReader r = new InputStreamReader(lsOut);
        BufferedReader in = new BufferedReader(r);

        String line;
        String savedOutput = "";
        while ((line = in.readLine()) != null) {
            savedOutput = savedOutput + line;
        }
        child.destroy();
        try {
            System.out.println("savedOutput" + savedOutput);
            String[] split = savedOutput.split(",");
            System.out.println("1" + split[1]);
            System.out.println("2" + split[2]);

            double hpa = Double.parseDouble(split[0].replace("(", ""));
            System.out.println("hpa" + hpa);
            hydro1pressure = String.format("%.1f", hpa);
            System.out.println("hydro1pressure" + hydro1pressure);
            hydroactualcount = split[1];

            double hpb = Double.parseDouble(split[2]);
            hydro1pressureb = String.format("%.1f", hpb);
            hydroactualcountb = split[3].replace("(", "");

            System.out.println("split[0]" + split[0]);
            System.out.println("split[1]" + split[1]);
            System.out.println("split[2]" + split[2]);
            System.out.println("split[3]" + split[3]);
            txtmaxActualA.setText(split[1]);
            txtHydroA.setText(hydro1pressure);
            txtmaxActualB.setText(split[3].replace(")", ""));
            txtHydroB.setText(hydro1pressureb);
        } catch (Exception e) {
        }

    }

    public void readGaugeCount() {
        ReadPressure = new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GaugecalibrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        Process child = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/read_pressure.py");
                        InputStream lsOut = child.getInputStream();
                        InputStreamReader r = new InputStreamReader(lsOut);
                        BufferedReader in = new BufferedReader(r);

                        String line;
                        String savedOutput = "";
                        while ((line = in.readLine()) != null) {
                            savedOutput = savedOutput + "/" + line;
                        }
                        child.destroy();
                        String[] split = savedOutput.split("/");
                        hydroactualcount = split[1];
                        hydro1pressure = split[2];
                        hydroactualcountb = split[3];
                        hydro1pressureb = split[4];

                        System.out.println("split[0]" + split[0]);
                        System.out.println("split[1]" + split[1]);
                        System.out.println("split[2]" + split[2]);
                        System.out.println("split[3]" + split[3]);

                    } catch (IOException ex) {
                        Logger.getLogger(GaugecalibrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        };
        ReadPressure.start();

    }

    public void updatevalue() {
        update = new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GaugecalibrationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Platform.runLater(() -> {
                        txtHydroA.setText(hydro1pressure);
                        txtmaxActualA.setText(hydroactualcount);
                        txtHydroB.setText(hydro1pressureb);
                        txtmaxActualB.setText(hydroactualcountb);

                    });
                }
            }

        };
        update.start();
    }

    @FXML
    private void btnHydrozeroAAction(ActionEvent event) throws IOException, InterruptedException {
        Process child = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 0 1");
        child.waitFor();
//        Process child1 = Runtime.getRuntime().exec("python /opt/hydro_projects/scuff_1079/python_plc/write_plc.py F8:140 " + scale_val);
//        child1.waitFor();
        Thread.sleep(10);
        Process child2 = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool    .py 15 0 0");
        child2.waitFor();
        read_pressure();

    }

    @FXML
    private void btnHydroApplyAAction(ActionEvent event) throws IOException, InterruptedException {
        String scale_val = txtHydroHoldingA.getText();
        System.out.println("scale_val" + scale_val);

        Process child1 = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_tag_int.py 60 0 " + scale_val);
        child1.waitFor();
        System.out.println("python D:/SCADA_soft/E1024/python_plc/write_tag_int.py 60 0 " + scale_val);
        Thread.sleep(20);

        Process child = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 1 1");
        child.waitFor();
        System.out.println("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 1 1");
        Thread.sleep(20);

        Process child2 = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 1 0");
        child2.waitFor();
        System.out.println("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 1 0");

        read_pressure();

//        ToolKit.tagWrite("B3:50/6", "1");
//       
//        ToolKit.tagWrite("F8:140", scale_val);
//        ToolKit.tagWrite("B3:50/6", "0");
    }

    @FXML
    private void btnCloseAction(ActionEvent event) {

//        if (Session.get("screen").equals("AdminScreen")) {
//            try {
//                TestScreenController.catStage.close();
//            } catch (Exception e) {
//            }
//        } else if (Session.get("screen").equals("AlarmScreen")) {
//            try {
//                TestScreenController.catStage.close();
//            } catch (Exception e) {
//            }
//        } else if (Session.get("screen").equals("ReportScreen")) {
//            try {
//                ReportScreenController.catStage.close();
//            } catch (Exception e) {
//            }
//        } else if (Session.get("screen").equals("AirPugingScreen")) {
//            try {
//                TestScreenController.catStage.close();
//            } catch (Exception e) {
//            }
//        } else 
        if (Session.get("screen").equals("TestScreen")) {
            try {
                TestScreenController.catStage.close();
            } catch (Exception e) {
            }
        } else {
            try {
                TestScreenController.catStage.close();
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void btnHydrozeroReles(MouseEvent event) {
    }

    @FXML
    private void btnHydrozeroPress(MouseEvent event) {
    }

    @FXML
    private void btnHydrozeroBAction(ActionEvent event) throws IOException, InterruptedException {
        Process child = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 2 1");
        child.waitFor();
//        Process child1 = Runtime.getRuntime().exec("python /opt/hydro_projects/scuff_1079/python_plc/write_plc.py F8:140 " + scale_val);
//        child1.waitFor();

        Process child2 = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 2 0");
        child2.waitFor();
        read_pressure();

    }

    @FXML
    private void btnHydroApplyBAction(ActionEvent event) throws IOException, InterruptedException {
        String scale_val = txtHydroHoldingB.getText();
        Process child1 = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_tag_int.py 62 0 " + scale_val);
        child1.waitFor();

        Thread.sleep(20);

        Process child = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 3 1");
        child.waitFor();

        Thread.sleep(20);
        Process child2 = Runtime.getRuntime().exec("python D:/SCADA_soft/E1024/python_plc/write_plc_bool.py 15 3 0");
        child2.waitFor();

        read_pressure();

    }

}
