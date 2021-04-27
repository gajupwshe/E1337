/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e1337;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author
 */
public class DatabaseHandler {

    public static String DB_NAME = "e1337";
    public static String DB_USER="root";
//    public static String DB_USER = "root";
    public static String DB_PASS="hydro";
//    public static String DB_PASS = "ims2000";

    public static String DB_HOST = "localhost";

    Connection MakeConnection() {

        try {
            Connection conn;
            //driver and connection
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME + "?" + "user=" + DB_USER + "&password=" + DB_PASS);
            return conn;
        } catch (Exception e) {
        }
        return null;
    }

    //execute query
    public int execute(String query, Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        if (st.executeUpdate(query) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void executeUpdate(Connection connection, String query) {
        try {
            System.out.println(query);
            Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //execute stored procedure
    public int execute_sp(String sp, Connection conn) throws SQLException {

        CallableStatement st_sp = conn.prepareCall("{call" + sp + "}");
        if (st_sp.executeUpdate() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    //get data 
    public ResultSet getData(String query, Connection conn) throws SQLException {

        Statement st = conn.createStatement();
        return st.executeQuery(query);
    }

    //get data from stored procedure
    public ResultSet getData_sp(String sp, Connection conn) throws SQLException {

        CallableStatement st_sp = conn.prepareCall("{call" + sp + "}");
        st_sp.execute();
        return st_sp.getResultSet();
    }

}
