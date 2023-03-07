package org.example;

import java.util.Scanner;

import java.io.*;
import java.sql.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
public class instructor implements instructor_academics{
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
    static Scanner input = new Scanner(System.in);
    public static String user_id="";
    public static String token="'logged in'";

      public static boolean user=false;
    public boolean login(String email,String password){


            String query="select * from instructor where email='"+email+"' and password='"+password+"';";
            try {
                stmt=conn.createStatement();
                ResultSet rs;

                rs=stmt.executeQuery(query);
                int f=0;
                while(rs.next()){
                    f++;
                    user_id=rs.getString(1);
                }
                if(f==0){
                 return false;
                }
                else{
                    user=true;
                    query="update instructor set token="+token+" where id='"+user_id+"';";
                    stmt.executeUpdate(query);
                    System.out.println("logged in successfully");
               Write_toLog.write("instructor",user_id,"logged in");

                    // Catch block to handle the exceptions


                }


            } catch (SQLException e) {
                System.out.println(e);
return false;
            }

        return true;
    }
    public boolean logout(){
        if(!user){
            System.out.println("no user is logged in");
            return false;
        }
        user=false;
        String query="update instructor set token='logged out' where id='"+user_id+"';";
        try {

            stmt= conn.createStatement();
            stmt.executeUpdate(query);
            Write_toLog.write("instructor",user_id,"logged out");


        } catch (SQLException e) {
            System.out.println("exception occurred" + e);
            return false;
        }
        return true;
    }

    public String viewprofile()
    {

        String query="select * from instructor where id='"+user_id+"';";

        try {
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            ResultSetMetaData rsmd;
            rsmd=rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String responseQuery="";
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {

                    if (i == 1)
                        responseQuery += "id ---> ";
                    if (i == 2)
                        responseQuery += "      name ---> ";
                    if (i == 3)
                        responseQuery += "      email ---> ";
                    if (i == 4)
                        responseQuery += "      dep_id ---> ";

                    if (i == 6)
                        responseQuery += "      phone_number ---> ";

                    if (i > 1 && i!=5 && i!=7)
                        responseQuery = responseQuery + " ";
                    if(i!=5 && i!=7) {
                        String columnValue = rs.getString(i);
                        responseQuery += columnValue;

                    }
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }

            }
         return responseQuery;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return "error";
        }
    }

    public boolean updateprofile(String name,String password,String phone_number){



        String query =" update instructor set name='"+name+"',phone_number='"+phone_number+"',password='"+password+"' where id='"+user_id+"';";

        try {
            stmt=conn.createStatement();
            stmt.executeUpdate(query);


        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }
return true;

    }

    public boolean addCourse(String course_id,String cgpa_limit){

        if(Semester_management.is_instructor_window()==0){
            System.out.println("you cannot add courses as window is closed");
            return false;
        }

            try {
                stmt= conn.createStatement();
                String query="select * from course_catalog where course_id='"+course_id+"';";

                try {
                    ResultSet rs;

               int d=0;
                    rs=stmt.executeQuery(query);
                    while(rs.next())d++;
                    if(d==0)
                    {
                        System.out.println("no such course in the course catalog");
                        return false;

                    }
                }
                catch (SQLException e){
                    System.out.println("no such course in the course catalog");
                    return false;
                }

                String query2="select * from course_offering where course_id='"+course_id+"';";
                try {
                    ResultSet rs;
                    rs=stmt.executeQuery(query2);
                    String f="";
                    while(rs.next()) {
                       f = rs.getString(1);
                    }
                    if(f.equals(""))
                    {
                        query="insert into course_offering(course_id,cgpa_limit,instructor_id) values ('"+course_id+"',"+cgpa_limit+",'"+user_id+"');";
                        stmt.executeUpdate(query);
                        System.out.println(" course offered successfully");
                    }
                    else{

                        System.out.println("Course already offered by someone else");
                        return false;
                    }
                }
                catch (SQLException e){
                    System.out.println(e);
                    return false;
                }


            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }
return true;

    }

    public boolean offeredCourses(){


        String query="select * from course_catalog;";

        try {
            stmt= conn.createStatement();
            try {
                ResultSet rs=stmt.executeQuery(query);
                ResultSetMetaData rsmd;
                rsmd=rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String responseQuery="";
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        if (i == 1)
                            responseQuery += "course_id ---> ";
                            String columnValue = rs.getString(i);
                            responseQuery += columnValue;
                        // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
responseQuery+="\n";
                }
                System.out.println(responseQuery);

            }
            catch (SQLException ee){
                System.out.println("semester has not started yet");

                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
public String mycourses(){

        String query="select * from course_offering where instructor_id='"+user_id+"';";

    try {
        stmt= conn.createStatement();
        ResultSet rs=stmt.executeQuery(query);
        ResultSetMetaData rsmd;
        rsmd=rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String responseQuery="";
        int f=0;
        while (rs.next()) {
            f++;
            for (int i = 1; i <= columnsNumber; i++) {

                if (i == 1)
                    responseQuery += "course_id ---> ";
                if (i == 2)
                    responseQuery += "cgpa_limit ---> ";
                if (i == 3)
                    responseQuery += "instructor_id ---> ";

                String columnValue = rs.getString(i);
                responseQuery += columnValue+" ";
                // System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            responseQuery+="\n";
        }
        if(f==0){
         return "you have no offered courses";
        }
       return responseQuery;
    } catch (SQLException e) {
        System.out.println(e);
        return "error";
    }

}
    public boolean deleteCourse(String course_id)
    {
        if(Semester_management.is_instructor_window()==0){
            System.out.println("you cannot drop courses as window is closed");
            return false;
        }
            String query="delete from course_offering where course_id='"+course_id+"' and instructor_id='"+user_id+"';";
            try {
                stmt=conn.createStatement();
                String q="delete from registration_status where course_id='"+course_id+"';";
                stmt.executeUpdate(q);
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println("you have not offered this course yet");
//                throw new RuntimeException(e);
                return false;
            }

return true;
    }


    public boolean showGrades()
    {

        try {
            stmt=conn.createStatement();
            String query = "select * from grades;";
            try {
                ResultSet rs;
                ResultSetMetaData rsmd;
                rs= stmt.executeQuery(query);
                rsmd=rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String responseQuery="";
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        if (i == 1)
                            responseQuery += "student_id ---> ";
                        if (i == 2)
                            responseQuery += "      instructor_id ---> ";
                        if (i == 3)
                            responseQuery += "      course_id ---> ";
                        if (i == 4)
                            responseQuery += "      grade ---> ";
                        if (i == 5)
                            responseQuery += "      semester ---> ";
                        if (i == 6)
                            responseQuery += "      academic_year ---> ";

                        if (i > 1)
                            responseQuery = responseQuery + " ";
                        String columnValue = rs.getString(i);
                        // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        responseQuery += columnValue;
                    }
                    responseQuery = responseQuery + "\n";

                }
                System.out.println(responseQuery);

            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public String enrollmentRequests()
    {

String query="select * from registration_status where instructor_id='"+user_id+"' and status='pending instructor approval';";

        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            ResultSetMetaData rsmd=rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String responseQuery="";
            int f=0;
            while (rs.next()) {
                f++;
                for (int i = 1; i <= columnsNumber; i++) {

                    if (i == 1)
                        responseQuery += "course_id ---> ";
                    if (i == 2)
                        responseQuery += "      student_id ---> ";


                    if (i > 1)
                        responseQuery = responseQuery + "       ";
                    String columnValue = rs.getString(i);
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    responseQuery += columnValue;
                }
                responseQuery = responseQuery + "\n\n";

            }
            if(f==0){
                return "no enrollment requests yet";
            }
            return responseQuery;


        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return "error";
        }
    }
public boolean approveordissaprove(String course_id,String student_id,String resp){
        String query;
    try {
        stmt= conn.createStatement();
        if(resp.equals("1")){
            query="update registration_status set status='approved by the instructor' where course_id='"+course_id+"' and student_id='"+student_id+"';";
            stmt.executeUpdate(query);
        }
        else{
            query="update registration_status set status='rejected by the instructor' where course_id='"+course_id+"' and student_id='"+student_id+"';";
            stmt.executeUpdate(query);
        }
    } catch (SQLException e) {
//        throw new RuntimeException(e);
        System.out.println(e);
        return false;
    }
    return true;

}
    public boolean submitgrades(String filename){
        String csvFilePath="src/main/resources/"+filename+".csv";

        String cd="";
        String sql = "INSERT INTO grades (student_id,instructor_id, course_id, grade, semester, academic_year) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e);
//            throw new RuntimeException(e);
            return false;
        }

        BufferedReader lineReader = null;
        try {
            lineReader = new BufferedReader(new FileReader(csvFilePath));
        } catch (FileNotFoundException e) {
            System.out.println(e);
//            throw new RuntimeException(e);
            return false;
        }
        String lineText = null;

        int count = 0;

        try {
            lineReader.readLine(); // skip header line
        } catch (IOException e) {
            System.out.println(e);
//            throw new RuntimeException(e);
            return false;
        }
        while (true) {
            try {
                if (!((lineText = lineReader.readLine()) != null)) break;
            } catch (IOException e) {

                System.out.println(e);
//            throw new RuntimeException(e);
                return false;
            }
            String[] data = lineText.split(",");
            if(data.length!=5){
                 System.out.println("Some lines were buggy");
                 continue;
            }
            String student_id = data[0];
            String course_id = data[1];

            cd=course_id;
            String grade = data[2];
            String semester = data[3];
            String academic_year = data[4] ;

            try{

                statement.setString(1, student_id);
                statement.setString(2, user_id);

                statement.setString(3, course_id);

                statement.setString(4, grade);

                statement.setString(5, semester);

                statement.setString(6, academic_year);
            }
            catch (Exception e){
                System.out.println(e);
                return false;
            }

            try {
                statement.execute();
            } catch (SQLException e) {
                System.out.println(e);
//            throw new RuntimeException(e);
                return false;
            }
count++;
        }
        if(count==0){
            System.out.println("please enter some data in the file");

            return false;
        }

        try {
            lineReader.close();
        } catch (IOException e) {
            return false;
        }

        String query="select student_id from registration_status where instructor_id='"+user_id+"' and course_id='"+cd+"' and status='approved by the instructor';";
        String query2="select student_id from grades where instructor_id='"+user_id+"' and course_id='"+cd+"';";
        String query3="delete from grades where instructor_id='"+user_id+"' and course_id='"+cd+"';";



        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            int f1=0,f2=0;
            while(rs.next())f1++;
            rs=stmt.executeQuery(query2);
            while(rs.next())f2++;
            System.out.println(f2 + " " + f1);
            int d=f2-f1;
            System.out.println(d);

            if(d>0){
                System.out.println("grade file has more grade entries than students registered for the course");
                stmt.executeUpdate(query3);
                return false;
            }
            if(d<0){
                System.out.println("grade file has les grade entries than students registered for the course");
                stmt.executeUpdate(query3);
                return false;
            }

        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }


        System.out.println("grades submitted successfully");

        // execute the remaining queries
return true;
    }

}
