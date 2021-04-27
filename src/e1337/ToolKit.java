package e1337;

import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ToolKit {

    public static String readpy(String url) {
        try {
            System.out.println("Command Excecuting : " + url);
            Process child = Runtime.getRuntime().exec(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(child.getInputStream()));
            String line = "";
            String savedOutput = "";
            while ((line = reader.readLine()) != null) {
                savedOutput = savedOutput + "/" + line;
            }
            child.waitFor();
            return savedOutput;
        } catch (IOException | InterruptedException e) {
        }

        return null;
    }

 

        public static String tagRead(String tag) {
        //update values
        try {
            BufferedReader in = null;
            String cmd = "python /opt/hydro_project/scuff_1079/python_plc/read_plc.py";  // Set shell command
            Process child = Runtime.getRuntime().exec(cmd + " " + tag);
            InputStream lsOut = child.getInputStream();
            InputStreamReader r = new InputStreamReader(lsOut);
            in = new BufferedReader(r);

            // read the child process' output
            String line;
            String savedOutput = "";
            while ((line = in.readLine()) != null) {
                savedOutput = savedOutput + line;
                System.out.print(savedOutput);
            }

//            System.out.println("output = " + savedOutput);
            boolean condition = tag.equals("B3:0/9");
            if (condition) {
//                System.out.println("Output inside if = " + savedOutput);
                return "" + savedOutput;
            } else {
                try {
                    if (savedOutput.equals("False") || savedOutput.equals("True")) {
                        return savedOutput;
                    } else {
                        double value = Double.parseDouble(savedOutput);
                        DecimalFormat df = new DecimalFormat("0");

                        return df.format(value);
                    }
                } catch (NumberFormatException e) {
                }
            }

        } catch (IOException e) {
            System.out.println("Read tag Exceptio");
        }
        return null;
    }

    public static void tagWrite(String tag, String value) {
        try {
            System.out.println("running command");
            String cmd = "python /opt/hydro_project/scuff_1079/python_plc/write_plc.py " + tag + " " + value;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
            System.out.println("cmd id = " + cmd);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void tagWriteFloat(String tag, String value) {
        try {
            System.out.println("running command");
            String cmd = "python include/write_plc.py " + tag + " " + value;
            Process child = Runtime.getRuntime().exec(cmd);
            child.waitFor();
            System.out.println("cmd id = " + cmd);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void strtagWrite(String tag, String value) {
        //update values
        System.out.println("TAG AND VALUe :" + tag + "AND : " + value);
        try {
            String cmd = "python include/str_write_plc.py";  // Set shell command
            Process child = Runtime.getRuntime().exec(cmd + " " + tag + " " + value);
            child.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Printed");
        }
    }

//    public static void madefadeinTrinsition(AnchorPane anchorPane) {
//        FadeTransition ft = new FadeTransition();
//        ft.setDuration(Duration.millis(700));
//        ft.setNode(anchorPane);
//        ft.setFromValue(0);
//        ft.setToValue(1);
//        ft.play();
//    }

//    public static void madefadeoutTrinsition(AnchorPane anchorPane) {
//        FadeTransition ft = new FadeTransition();
//        ft.setDuration(Duration.millis(500));
//        ft.setNode(anchorPane);
//        ft.setFromValue(1);
//        ft.setToValue(0);
//        ft.setOnFinished((event) -> {
//            unloadScreen(anchorPane);
//        });
//        ft.play();
//    }

    public static void loadScreen(Parent parent) throws IOException {

//        Platform.runLater(() -> {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
//        stage.getIcons().add(new Image(E1232.class.getResourceAsStream("images/app_Logo.png")));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setOnCloseRequest((event) -> {
            System.exit(0);
        });
        stage.show();
//        });

    }
    
    public static void loadScreenhome(Parent parent) throws IOException {

//        Platform.runLater(() -> {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(E1337.class.getResourceAsStream("images/app_Logo.png")));
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(600);
        stage.setHeight(450);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setOnCloseRequest((event) -> {
            System.exit(0);
        });
        stage.show();
//        });

    }

    public static void unloadScreen(Node anyComponentFromScreenToUnload) {
        ((Stage) anyComponentFromScreenToUnload.getScene().getWindow()).close();
    }

    public static void grabFocusTo(Node componentToGrabFocusOn) {
        Platform.runLater(() -> {
            componentToGrabFocusOn.requestFocus();
        });
    }

    public static boolean isMyResultSetEmpty(ResultSet rs) throws SQLException {
        return (!rs.isBeforeFirst() && rs.getRow() == 0);
    }

    public static void validateNumberField(JFXTextField textField) {
        if (textField.getText().matches("\\d*")) {
        } else {
            textField.setText(textField.getText().replaceAll("[^\\d]", ""));
        }
    }

    public static void validateNumberFieldDecimal(JFXTextField textField) {
        if (textField.getText().matches("\\d*([\\.]\\d*)?")) {
        } else {
            textField.setText(textField.getText().replaceAll("[^\\d\\.]", ""));
        }
    }

    public static boolean isNull(String value) {
        if (value != null && !value.isEmpty()) {
            if (value.equals("null") || value.equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static void excecuteScript(String cmd) {
        try {
            System.out.println("Excuting...." + cmd);
            Process child = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
        }
    }

}
