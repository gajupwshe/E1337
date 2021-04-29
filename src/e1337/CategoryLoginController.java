/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nsp
 */
public class CategoryLoginController implements Initializable {

    @FXML
    private JFXTextField txtCatUser;
    @FXML
    private JFXPasswordField txtCatPass;
    @FXML
    private JFXButton btnCatGetAccess;
    @FXML
    private JFXButton btnCatClose;
    @FXML
    private Text txtMsg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtMsg.setVisible(false);
    }
    DatabaseHandler dh = new DatabaseHandler();
    Connection connect = dh.MakeConnection();

    @FXML
    private void btnCatGetAccessAction(ActionEvent event) {
        String username = txtCatUser.getText();
        String password = AES256.encrypt(txtCatPass.getText());
//        String password = txtCatPass.getText();
        String query = "SELECT * FROM User_data WHERE username = '" + username + "' AND password = '" + password + "';";
        try {
            ResultSet rs = dh.getData(query, connect);
            if (rs.next()) {
                if (rs.getString("user_type").equals("admin")) {
                    Session.set("catAccess", "granted");
                    TestScreenController.catStage.close();
                } else {
                    txtMsg.setText("!!!!!Invalid admin credential!!!!!");
                    txtMsg.setVisible(true);
                }
            }else{
                txtMsg.setText("!!!!!Invalid admin credential!!!!!");
                txtMsg.setVisible(true);
            }
        } catch (Exception e) {
        }
//        ISC.categorySelect("granted");
//        InitialScreenController.cmbTestStandards.getSelectionModel().select(1);
    }

    @FXML
    private void btnCatCloseAction(ActionEvent event) {
//        InitialScreenController.cmbTestStandards.getSelectionModel().select(0);
//        ISC.categorySelect("not granted");
        Session.set("catAccess", "not granted");
        TestScreenController.catStage.close();
    }

}
