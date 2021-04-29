/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.RED;
import static javax.management.remote.JMXConnectorFactory.connect;

/**
 * FXML Controller class
 *
 * @author nsp
 */
public class LoginController implements Initializable {

    @FXML
    private FontAwesomeIconView fntlock;
    @FXML
    private FontAwesomeIconView fntunlock;
    @FXML
    private JFXTextField txtUser;
    @FXML
    private JFXPasswordField txtPass;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton brnExit;
    @FXML
    private FontAwesomeIconView fntuser;
    private static final String ip = "localhost";
    private static String port = "3306";
    private static String database = "e1337";
    private static String user = "root";
    private static String pass = "hydro";
    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();
    @FXML
    private JFXComboBox<String> cmbMachineUnit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fntunlock.setVisible(false);
        cmbMachineUnit.getItems().addAll("16MT", "90MT");
//        export();
    }

    @FXML
    private void txtPassAction(ActionEvent event) throws SQLException {
        login();
    }

    @FXML
    private void btnLoginAction(ActionEvent event) throws SQLException {
        login();
    }

    @FXML
    private void btnExitAction(ActionEvent event) {
        System.exit(0);
    }

    public static void export() {

        Date date = new Date();

        String strDateFormat = "dd-MM-yy";

        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);

        String formattedDate = dateFormat.format(date);

//        System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);
        String filename = formattedDate + ".sql";

//        String path = "/mnt/s0248/data_storage/Database_BackUp/" + filename + "";
        String path = "D:/back up/Database_BackUp/" + filename + "";
//        System.out.println(path);
        String[] attachFiles = new String[1];
//        attachFiles[0] = "/mnt/s0248/data_storage/Database_BackUp/" + filename + "";
        attachFiles[0] = "D:/back up/Database_BackUp/" + filename + "";
//        String path_database[] = 

        File f = new File(path);
        System.out.println(f.getName());
        if (f.exists()) {

            System.out.println("Aleready took");
//            MailDatabase.emailReport("DataBase", "Please find Attachment", attachFiles,connection);

//            stopThreads();
            return;

        }

        String dumpCommand = "C:/wamp64/bin/mysql/mysql5.7.26/bin/mysqldump " + database + " -h " + ip + " -u " + user + " -p" + pass;
        Runtime rt = Runtime.getRuntime();
        File test = new File(path);
        PrintStream ps;

        try {
            Process child = rt.exec(dumpCommand);
            ps = new PrintStream(test);
            InputStream in = child.getInputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                ps.write(ch);
                System.out.write(ch); //to view it by console
            }

            InputStream err = child.getErrorStream();
            while ((ch = err.read()) != -1) {
                System.out.write(ch);
            }
            //MailDatabase.emailReport("DataBase s0249", "Please find Attachment of the Database", attachFiles, connection);

//            stopThreads();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void login() throws SQLException {
        String mt = cmbMachineUnit.getSelectionModel().getSelectedItem();
        if (ToolKit.isNull(mt)) {
            Dialog.showAndWait("Please Select Machine ");
        } else {
            
            Session.set("mt",mt);
//         String username = txtUser.getText();
            String password = AES256.encrypt(txtPass.getText());
            String username = txtUser.getText();
//        String password = txtPass.getText();
            String query = "SELECT * FROM user_data WHERE username = '" + username + "' AND password = '" + password + "';";

            ResultSet rs = dh.getData(query, connect);
            if (rs.next()) {

                try {
                    //                long start = System.currentTimeMillis();
                    String user_type = rs.getString("user_type");
                    InetAddress geek = InetAddress.getByName("192.168.20.243");
//                System.out.println("Sending Ping Request to 192.168.0.1");
                    if (geek.isReachable(500)) {
                        System.out.println("Host is reachable");
                        Platform.runLater(() -> {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("TestScreen.fxml"));
                                ToolKit.loadScreen(root);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        ToolKit.unloadScreen(btnLogin);
                    } else {
                        System.out.println("Sorry ! We can't reach to this host");
                        Dialog.showAndWait("PLC IS NOT CONNECTED..Please Check the Ip Address/Connection");
                    }

//                long stop = System.currentTimeMillis();
//                System.out.println("Load Iniit "+(stop-start));
                    Session.set("user", username);
                    Session.set("user_type", user_type);
                    if (user_type.equals("admin")) {
                        Session.set("catAccess", "granted");
                    } else {
                        Session.set("catAccess", "not granted");
                    }
                } catch (UnknownHostException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                fntlock.setFill(RED);
                fntuser.setFill(RED);
                txtPass.setFocusColor(RED);
                txtUser.setFocusColor(RED);
                txtUser.setUnFocusColor(RED);
                txtPass.setUnFocusColor(RED);
                fntlock.setVisible(true);
                fntunlock.setVisible(false);
            }
        }
    }

    @FXML
    private void cmbMachineUnitAction(ActionEvent event) {
        String mt = cmbMachineUnit.getSelectionModel().getSelectedItem();
        Session.set("mt",mt);
    }

}
