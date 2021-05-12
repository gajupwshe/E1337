/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import java.util.Calendar;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author nsp
 */
public class Dialog {
    
    public static void showForSometime(String title, String message, String headerText, double width, int time) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.getDialogPane().setMinWidth(width);
            alert.setContentText(message);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
            stage.toFront();
            System.out.println("timeline Started -" + Calendar.getInstance().getTime());
            Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(time), (ActionEvent event) -> {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }));
            idlestage.setCycleCount(1);
            idlestage.play();

            alert.showAndWait();
        });
    }
    
//    public static Optional<ButtonType> ConfirmationDialog(String title, String message, double width) {
//
//        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.YES, ButtonType.NO);
//        alert.getDialogPane().setMinWidth(width);
////        alert.setTitle(title);
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.setAlwaysOnTop(true);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.toFront();
//        Optional<ButtonType> result = alert.showAndWait();
//
//        return result;
//    }
    
    public static Optional<ButtonType> ConfirmationDialog_Single_button(String title, String message, double width) {

        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.getDialogPane().setMinWidth(width);
//        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.toFront();
        Optional<ButtonType> result = alert.showAndWait();

        return result;
    }

    
    
    public static void showAndWait(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CAutomate");
            alert.setHeaderText("");

            alert.setContentText(message);

            alert.showAndWait();
        });
    }
}
