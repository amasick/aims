package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Semester_management {
    static String sem;
    static String year;
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
    public static String startsem(String academic_year,String semester){
        try {
            ResultSet rs;
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select *from semester;");
            System.out.println("A sem is already running");
//            System.out.println("press any key to continue");
//            input.nextLine();
            return "a sem is already running";
        } catch (SQLException e) {

        }

        String s3="CREATE TABLE course_catalog(\n" +
                "course_id VARCHAR(10),\n" +
                "PRIMARY KEY(course_id),\n" +
                "FOREIGN KEY (course_id) references course (id)\n" +
                ");";
        String s4="CREATE TABLE course_offering(\n" +
                "course_id VARCHAR(10),\n" +
                "cgpa_limit INTEGER,\n" +
                "instructor_id VARCHAR(10),\n" +
                "PRIMARY KEY(course_id),\n" +
                "FOREIGN KEY (course_id) references course_catalog (course_id),\n" +
                "FOREIGN KEY (instructor_id) references instructor (id)\n" +
                "\n" +
                ");";
        String s5="CREATE TABLE semester(\n" +
                "academic_year VARCHAR(10),\n" +
                "semester VARCHAR(10),\n" +
                "student_window int,\n" +
                "instructor_window int\n" +
                ");";
        String s7="CREATE TABLE registration_status(\n" +
                "course_id VARCHAR(10),\n" +
                "student_id VARCHAR(10),\n" +
                "instructor_id VARCHAR(10),\n" +
                "status VARCHAR(100),\n" +
                "FOREIGN KEY (course_id) references course_offering (course_id),\n" +
                "FOREIGN KEY (student_id) references student (id),\n" +
                "FOREIGN KEY (instructor_id) references instructor (id)\n" +
                ");";
        String s8="update student set credits=0";
String s9="update student set curr_sem=curr_sem+1;";


        try {
            stmt=conn.createStatement();
            try {

                stmt.execute(s3);
                stmt.execute(s4);
                stmt.execute(s5);
                stmt.execute(s7);
stmt.executeUpdate(s9);
                String query="insert into semester(academic_year,semester,student_window,instructor_window) values('"+academic_year+"','"+semester+"',1,1);";
                stmt.executeUpdate(query);
                sem=semester;
                year=academic_year;
                return academic_year+"-"+semester+" started successfully";
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "1";

    }

    public static String viewsemester(){
        String academic_year="",sem="";

        try {
            ResultSet rs;
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select *from semester;");
            while(rs.next()){
                academic_year=rs.getString(1);
                sem=rs.getString(2);
            }
            return academic_year+"-"+sem+" semester";
        } catch (SQLException e) {
            return "no sem is running";
        }

    }
    public static boolean endsem(){
        String academic_year="",sem="";
        try {
            ResultSet rs;
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select *from semester;");
            while(rs.next()){
                academic_year=rs.getString(1);
                sem=rs.getString(2);
            }

        } catch (SQLException e) {
            System.out.println("No sem is running");

            return false;
        }
        try {
            stmt.execute("drop table semester;");
            String s1="drop table course_catalog;";
            String s2="drop table course_offering;";
            String s6="drop table registration_status;";
            stmt.execute(s6);
            stmt.execute(s2);
            stmt.execute(s1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println( academic_year+"-"+sem+" ended successfully");
        return true;
    }


    public static boolean openwindowforinstructor(){
        if(Semester_management.viewsemester().equals("no sem is running")){
            System.out.println("no sem is running");
            return false;
        }
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("update semester set instructor_window=1;");

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    public static boolean closewindowforinstructor(){
        if(Semester_management.viewsemester().equals("no sem is running")){
            System.out.println("no sem is running");
            return false;
        }
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("update semester set instructor_window=0;");

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

         return true;
    }

    public static boolean openwindowforstudent(){
        if(Semester_management.viewsemester().equals("no sem is running")){
            System.out.println("no sem is running");
            return false;
        }
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("update semester set instructor_window=1;");

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }
    public static boolean closewindowforstudent(){
        if(Semester_management.viewsemester().equals("no sem is running")){
            System.out.println("no sem is running");
            return false;
        }
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("update semester set instructor_window=0;");

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public static int is_student_window(){
        if(Semester_management.viewsemester().equals("no sem is running")){
            System.out.println("no sem is running");
            return 0;
        }
        int f=0;

        try {
            ResultSet rs;
            stmt=conn.createStatement();
            rs=stmt.executeQuery("select * from semester");
      while(rs.next())f=rs.getInt(3);
        } catch (SQLException e) {
            System.out.println("No sem is running");
            return 0;
        }
        return f;
    }

    public static int is_instructor_window(){
        if(Semester_management.viewsemester().equals("no sem is running")){
            System.out.println("no sem is running");
            return 0;
        }
        int f=0;

        try {
            ResultSet rs;
            stmt=conn.createStatement();
            rs=stmt.executeQuery("select * from semester");
            while(rs.next())f=rs.getInt(4);
        } catch (SQLException e) {
            System.out.println("No sem is running");
            return 0;
        }
        return f;
    }

}
