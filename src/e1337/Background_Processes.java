/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
/**
 *
 * @author nsp
 */
public class Background_Processes {
    
     public static volatile boolean pause_insert, run_insert, stop_time, break_time;
    public static Thread insert, timeThread;
    
    //INSERT_INIT DATA CLASS
    public static void InsInitialize_Initial_Screen() throws IOException, InterruptedException{
    
            System.out.println("python include/insert_initial_init.py" + DatabaseHandler.DB_HOST + " " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " truncate_Initial_init insert_initial_init_sp");
            
            //excecute insert_initial_init_sp 
            
            Runtime run = Runtime.getRuntime();
            Process child = run.exec("python include/insert_initial_init.py" + DatabaseHandler.DB_HOST + " " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " truncate_Initial_init insert_initial_init_sp");
            
            child.waitFor();
    }
    
    public static void Initialize_Initial_Screen() {
        try {
            System.out.println("python python E:\\E1232\\python_plc\\insert_initial_init.py " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " truncate_Initial_init insert_initial_init_sp");
            String cmd ="python E:\\E1232\\python_plc\\insert_initial_init.py root hydro E1232 truncate_Initial_init insert_initial_init_sp";
            Process child = Runtime.getRuntime().exec(cmd);
//            Runtime run = Runtime.getRuntime();
//            Process child = run.exec("python include/insert_initial_init.py " + DatabaseHandler.DB_USER + " " + DatabaseHandler.DB_PASS + " " + DatabaseHandler.DB_NAME + " truncate_Initial_init insert_initial_init_sp");
            child.waitFor();
        } catch (IOException | InterruptedException e) {
        }
    }
    
    //date and time
     public static void date_time(Text txtDate, boolean stop, boolean bre, String threadName) {
        stop_time = stop;
        break_time = bre;
        timeThread = new Thread(() -> {
//            while (!stop_time) {
                try {
                    Date dateInstance = new Date();
                    txtDate.setText("Date : " + (dateInstance.getDate() + "/" + (dateInstance.getMonth() + 1) + "/" + (dateInstance.getYear() + 1900)));

                    if (!break_time) {
                    } else {
//                        break;
                    }
                    Thread.sleep(900);
                } catch (Exception exc) {
                    System.out.println("Exception in Date_time : " + exc.getMessage());
                }
//            }
        }, threadName);
        timeThread.setDaemon(true);
        timeThread.start();

    }
     
     public static void insert_plc_data(String python_file_url, boolean pause, boolean run) {
        System.out.println("Thread started insert : " + python_file_url);
        pause_insert = pause;
        run_insert = run;
        try {
            insert = new Thread(() -> {
                while (run_insert) {
                    try {
                        Thread.sleep(10);
                        
                        if (!pause_insert) {
//                            System.out.println("python_file_url : "+python_file_url);
                            Process child = Runtime.getRuntime().exec(python_file_url);
                            child.waitFor();

                            if (!run_insert) {
//                                    
                                break;
                            }
                            if (!run_insert) {
//                                   
                                break;
                            }
                        } else {
                            if (!run_insert) {
//                                    
                                break;
                            }
                            

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, "INSERT_PLC_THREAD");
            insert.setDaemon(true);
            insert.start();
        } catch (Exception e) {
        }
    }
     
     
     //stop plc read data thread
     public static void stop_plc_read() {
        try {
            run_insert = false;
            insert.stop();
        } catch (Exception e) {
            System.out.println("Exception in stoping background process insertPlcThread : " + e.getMessage());
        }
    }//stop plc read date&time thread
     
      public static void stop_date_time() {
        stop_time = true;
        break_time = true;
        try {
            timeThread.stop();
        } catch (Exception e) {
        }
    }
      
       public static void pause_plc_read() {
        pause_insert = true;
    }
    
}
