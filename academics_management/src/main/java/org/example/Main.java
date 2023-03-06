package org.example;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {

    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the academics management portal IIT ROPAR!");
            Scanner input = new Scanner(System.in);
            String c = "";
            int fs = 0, fi = 0;
            student stu = new student();
            instructor ins = new instructor();
            String query = "select * from student where token='logged in';";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    stu.user = true;
                    stu.user_id = rs.getString(1);
                    stu.batch_id = rs.getString(3);
                    stu.credits = rs.getInt(7);
                    fs++;
                    c = "1";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (fs == 0) {
                query = "select * from instructor where token='logged in';";
                try {
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        ins.user = true;
                        ins.user_id = rs.getString(1);
                        fi++;
                        c = "2";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (fs == 0 && fi == 0) {
                System.out.println("Enter your role");
                System.out.println("0. to exit ");
                System.out.println("1. student");
                System.out.println("2. instructor");
                System.out.println("3. admin");
                c = input.nextLine();
//    System.out.println("f");
                if (c.equals("0")) {
                    break;
                }
            }
            switch (c) {

                case "1":
                    if (fs == 0) {
                        if (student_menu.handlelogin() == false) break;
                    }
                    student_menu.handlemenu();
                    break;
                case "2":
                    if (fi == 0) {
                        if (instuctor_menu.handlelogin() == false) break;
                    }
                    instuctor_menu.handlemenu();
                    break;
                case "3": {
                    if (admin_menu.handlelogin() == false) break;
                    admin_menu.handlemenu();
                    break;
                }

                default:
                    System.out.println("please enter valid role");
            }
        }
    }

}