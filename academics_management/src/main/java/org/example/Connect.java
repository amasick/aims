/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Connect {

    public static Connection ConnectDB() {

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/aims",
                            "postgres", "shyam2672002");
            System.out.println("Opened database successfully");
            return conn;

        } catch (Exception e) {
            System.out.println("not connected");
          System.out.println(e);
            return null;

        }

    }
}