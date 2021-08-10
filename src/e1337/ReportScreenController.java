/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import static javafx.scene.input.DataFormat.HTML;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * FXML Controller class
 *
 * @author nsp
 */
public class ReportScreenController implements Initializable {

    @FXML
    private WebView webReport;

    /**
     * Initializes the controller class.
     */
    WebEngine webEngine;
    @FXML
    private Text txtdate;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnReport;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnInitial;

    
    @FXML
    private JFXButton btnAlarm;
    @FXML
    private Text txtMode;
    @FXML
    private Text lableTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String user_type = Session.get("user_type");
        System.out.println("user_type" + user_type);
        if (user_type.equals("admin")) {
            System.out.println("YES........");
            btnAdmin.setVisible(true);

        } else {
            System.out.println("NO........");
            btnAdmin.setVisible(false);
        }
        String user_name = Session.get("user");
        
        Session.set("screen", "ReportScreen");

        date_time();
        webEngine = webReport.getEngine();
        webEngine.load("http://localhost/Report_1337/index.php?db_user=root&db_pass=hydro&db_name=e1337");
        webEngine.setJavaScriptEnabled(true);
        System.out.println("http://localhost/Report_1337/index.php?db_user=root&db_pass=hydro&db_name=e1337");
        //Adding Custom Context menu - Start
        webReport.setContextMenuEnabled(false);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem reload = new MenuItem("Reload");
        reload.setOnAction(e -> webReport.getEngine().reload());
        MenuItem goBack = new MenuItem("Go back");
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    webReport.getEngine().getHistory().go(-1);
                } catch (IndexOutOfBoundsException exc) {
                    Dialog.showForSometime("", "You are on last page", "", 550, 2);
                }
            }
        });
        contextMenu.getItems().addAll(reload, goBack);
        //Adding Custom Context menu - End

        webReport.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webReport, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });

//        //Initialize intial screen
//        Thread Initialize_Initial = new Thread(() -> {
//            Background_Processes.Initialize_Initial_Screen();
//            System.out.println("Initialized");
//        }, "Initialize_Initial_Screen");
//        Initialize_Initial.setDaemon(true);
//        Initialize_Initial.start();

//        
        webReport.getEngine().setOnAlert((WebEvent<String> arg0) -> {
            System.out.println("pressed");
            System.out.println(webEngine.locationProperty().getValue());
            String[] pdf_split = arg0.getData().split("_split_");
            System.out.println("pdf_split :" +pdf_split[0]);
            if (pdf_split[0].equals("PDF SAVED SUCCESSFULLY")) {
                try {
                    System.out.println(" saved");
                    Dialog.showAndWait(arg0.getData());
                } catch (Exception ex) {
                    Logger.getLogger(ReportScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (arg0.getData().equals("testing test no")) {
                String[] split = webEngine.locationProperty().getValue().split("test_no=");
                String[] split2 = split[1].split("&");
                System.out.println(split2[0]);
                String testNo = split2[0];
              
            }

        });
        if (Session.get("mt").equals("16MT")) {
            lableTitle.setText("VALVE TESTING UNIT - 16MT");
        } else if (Session.get("mt").equals("90MT")) {
            lableTitle.setText("VALVE TESTING UNIT - 90MT");
        }
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

    @FXML
    private void btnLoginAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {

                time.purge();
                time.cancel();
                date.cancel();
                Background_Processes.stop_plc_read();
            } catch (Exception e) {
            }
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }

    @FXML
    private void btnReportAction(ActionEvent event) {
    }

    @FXML
    private void btnAdminAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                try {

                    time.purge();
                    time.cancel();
                    date.cancel();
                    Background_Processes.stop_plc_read();
                } catch (Exception e) {
                }
                Parent root = FXMLLoader.load(getClass().getResource("AdminScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);

    }

    @FXML
    private void btnInitialAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                try {

                    time.purge();
                    time.cancel();
                    date.cancel();
                    Background_Processes.stop_plc_read();
                } catch (Exception e) {
                }
                Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }
    public static Stage catStage;

    @FXML
    private void btnAlarmAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                try {

                    time.purge();
                    time.cancel();
                    date.cancel();
                    Background_Processes.stop_plc_read();
                } catch (Exception e) {
                }
                Parent root = FXMLLoader.load(getClass().getResource("AlarmScreen.fxml"));
                ToolKit.loadScreen(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ToolKit.unloadScreen(btnLogin);
    }
}
