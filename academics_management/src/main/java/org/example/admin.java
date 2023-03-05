package org.example;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.List;
import java.util.Scanner;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class admin {
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
   static Scanner input = new Scanner(System.in);

public boolean user=false;
    public  boolean login(String username,String password){

//            System.out.println(username + " "+ password);
            if(username.equals("admin") && password.equals("iitropar")){
                try {


                    FileWriter fileWritter = new FileWriter("log.txt",true);
                    BufferedWriter out = new BufferedWriter(fileWritter);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String time= dtf.format(now);



                    String query="admin  logged in on "+ time +"\n";
                    // Writing on output stream
                    out.write(query);
                    System.out.println("logged in successfully");

                    // Closing the connection
                    out.close();
                }

                // Catch block to handle the exceptions
                catch (IOException e) {

                    // Display message when exception occurs
                    System.out.println("exception occurred" + e);
                    return false;
                }

            }
            else{
                return false;
            }
        user=true;
        return true;
    }
public  void logout(){
    try {


        FileWriter fileWritter = new FileWriter("log.txt",true);
        BufferedWriter out = new BufferedWriter(fileWritter);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time= dtf.format(now);



        String query="admin  logged out on "+ time +"\n";
        // Writing on output stream
        out.write(query);
        System.out.println("logged out successfully");
user=false;
        // Closing the connection
        out.close();
    }

    // Catch block to handle the exceptions
    catch (IOException e) {

        // Display message when exception occurs
        System.out.println("exception occurred" + e);
    }

}
    public  boolean addbatch(String batch_id,String year,String dep_id){


            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO batch(id,year,dep_id) VALUES('" + batch_id + "'," + "'"+year+"'," + "'"+dep_id+ "');";
                System.out.println(query);
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                   System.out.println(e);
                   return false;
                }
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }

return true;
    }
public boolean deletebatch(String batch_id){
        String query="delete from batch where id='"+batch_id+"';";
    try {
        stmt= conn.createStatement();
        stmt.executeUpdate(query);
        return true;
    } catch (SQLException e) {
        System.out.println(e);
        return false;
    }
}
    public  boolean addcourse(String course_id, String course_name, String dep_id, String l, String t, String p, String c, List<String> prereq){

            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO course(id,name,dep_id,l,t,p,c) VALUES('" + course_id + "'," + "'"+course_name+"'," + "'"+dep_id+ "',"+l+","+t+","+p+","+c+");";
//                System.out.println(query);

                try {
                    stmt.executeUpdate(query);
                    for(String pre:prereq){
                        query="insert into prereq(course_id,prereq_id) values('"+course_id+"','"+pre+"');";
                        stmt.executeUpdate(query);
                    }

                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }
return true;

    }
    public boolean deletecourse(String course_id){
        String query="delete from course where id='"+course_id+"';";
        try {
            stmt= conn.createStatement();
            String q="delete from prereq where course_id='"+course_id+"' or prereq_id='"+course_id+"';";
                    stmt.executeUpdate(q);
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public  boolean addcurriculum(String course_id,String course_type,String batch_id){

            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO ug_curriculum(course_id,batch_id,course_type) VALUES('" + course_id + "'," + "'"+batch_id+"'," + "'"+course_type+"');";
//                System.out.println(query);
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }
            } catch (SQLException e) {
                System.out.println(e);
                return false;

            }
            return true;
    }
public boolean deletefromcurriculum(String course_id,String batch_id){
    String query="delete from ug_curriculum where course_id='"+course_id+"' and batch_id='"+batch_id+"';";
    try {
        stmt= conn.createStatement();
        stmt.executeUpdate(query);
        return true;
    } catch (SQLException e) {
        System.out.println(e);
        return false;
    }
}
    public boolean showGrades(String student_id){
        try {
            stmt=conn.createStatement();
            String query = "select * from grades where student_id='"+student_id+"';";
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
                            responseQuery += "      course_id ---> ";
                        if (i == 3)
                            responseQuery += "      Grade ---> ";
                        if (i == 4)
                            responseQuery += "      semester ---> ";
                        if (i == 5)
                            responseQuery += "      academic year ---> ";

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
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public String  adduser(String role,List<String> data){
String id;
            switch (role){
                case "1":
                {     String name="",batch_id="",phone_number="",password;
                    int h=0;
           for (String s:data) {
              if(h==0)name=s;
              if(h==1)batch_id=s;
              if(h==2)phone_number=s;
              h++;
           }
           if(batch_id.equals("")) {
           System.out.println("please enter valid data");
           return "failed";
           }
                    try {
                        stmt=conn.createStatement();
                    try {
                        ResultSet rs;
                        ResultSetMetaData rsmd;
                        String query="select count(*) from student where batch_id='"+batch_id+"';";
                        rs= stmt.executeQuery(query);
                        rsmd=rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        String responseQuery="";
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {


                                String columnValue = rs.getString(i);
                                // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                                responseQuery += columnValue;
                            }

                        }
                      id=batch_id+responseQuery;
                        query="insert into student(id,name,batch_id,email,password,phone_number,credits,token) values('"+id+"','"+name+"','"+batch_id+"','"+id+"@iitrpr.ac.in','"+ "iitropar','"+phone_number+"',0,'');";
                         stmt.executeUpdate(query);

                    } catch (SQLException e) {
                        System.out.println(e);
                        return "failed";
                    }
            } catch (SQLException e) {
                        System.out.println(e);
                        return "failed";
            }
                    break;}
                case "2":
                {     String name="",dep_id="",phone_number="",password;
                    int h=0;
                    for (String s:data) {
                        if(h==0)name=s;
                        if(h==1)dep_id=s;
                        if(h==2)phone_number=s;
                        h++;
                    }
                    if(dep_id.equals("")) {
                        System.out.println("please enter valid data");
                        return "failed";
                    }
                    try {
                        stmt=conn.createStatement();
                        try {
                            ResultSet rs;
                            ResultSetMetaData rsmd;
                            String query="select count(*) from instructor where dep_id='"+dep_id+"';";
                            rs= stmt.executeQuery(query);
                            rsmd=rs.getMetaData();
                            int columnsNumber = rsmd.getColumnCount();
                            String responseQuery="";
                            while (rs.next()) {
                                for (int i = 1; i <= columnsNumber; i++) {


                                    String columnValue = rs.getString(i);
                                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                                    responseQuery += columnValue;
                                }

                            }
                            id=dep_id+responseQuery;
                            query="insert into instructor(id,name,dep_id,email,password,phone_number,token) values('"+id+"','"+name+"','"+dep_id+"','"+id+"@iitrpr.ac.in','"+ "iitropar','"+phone_number+"','');";
                            stmt.executeUpdate(query);

                        } catch (SQLException e) {
                            System.out.println(e);
                            return "failed";
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                        return "failed";
                    }
                    break;}
                default:System.out.println("please follow the instructions");
                return "failed";

            }

        return id;
    }

    public boolean deleteuser(String role,String id){
        String query="";
        if(role=="1")
         query="delete from student where id='"+id+"';";
        else if(role=="2")
            query="delete from instructor where id='"+id+"';";


        try {
            stmt= conn.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    public String startsem(String academic_year,String semester){
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
                "semester VARCHAR(10)\n" +
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



        try {
            stmt=conn.createStatement();
            try {

                stmt.execute(s3);
                stmt.execute(s4);
                stmt.execute(s5);
                stmt.execute(s7);

String query="insert into semester(academic_year,semester) values('"+academic_year+"','"+semester+"');";
               stmt.executeUpdate(query);
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
    public  boolean endsem(){
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

    public boolean updatecoursecatalog(String course_id){
        try {
            ResultSet rs;
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select *from semester");


        } catch (SQLException e) {
            System.out.println("sem is not yet started");

            return false;
        }

            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO course_catalog(course_id) VALUES('" + course_id + "');" ;
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }

return true;
    }
    public boolean deletefromcoursecatalog(String course_id){
        String q="delete from course_catalog where course_id='"+course_id+"';";
        try {

            stmt= conn.createStatement();
            stmt.executeUpdate(q);

        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
    public boolean generatetranscript(String student_id){
        String query="select course.name, grades.course_id, course.c, grades.grade,  grades.instructor_id, grades.academic_year ,grades.semester from grades, course where grades.course_id=course.id; ";
        String responseQuery="";

        try {

            try {
                stmt= conn.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                ResultSetMetaData rsmd;
                rsmd=rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        if (i == 1)
                            responseQuery += "course_name ---> ";
                        if (i == 2)
                            responseQuery += "      course_id ---> ";
                        if (i == 3)
                            responseQuery += "      credits_earned ---> ";
                        if (i == 4)
                            responseQuery += "      grade ---> ";
                        if (i == 5)
                            responseQuery += "      instructor_id ---> ";
                        if (i == 6)
                            responseQuery += "      academic_year ---> ";
                        if (i == 7)
                            responseQuery += "      semester ---> ";
                        String columnValue = rs.getString(i);
                        responseQuery += columnValue+" ";
System.out.println(responseQuery);
                        // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    responseQuery+="\n";

                }
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }
            BufferedWriter out = new BufferedWriter(
                    new FileWriter("src/main/resources/"+student_id+"_transcript.txt"));
            // Writing on output stream
            out.write(responseQuery);
            // Closing the connection
            out.close();
        }

        // Catch block to handle the exceptions
        catch (IOException e) {

            // Display message when exception occurs
            System.out.println("exception occurred" + e);
            return false;
        }

        return true;
    }
    public boolean submittranscript(String student_id){


        File file = new File("src/main/resources/"+student_id+"_transcript.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO transcript VALUES (?, ?)");
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }

        try {
            ps.setString(1, student_id);
            ps.setBinaryStream(2, fis, file.length());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }

        try {
            fis.close();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }
return true;

    }

    public boolean deletetranscript(String student_id){
        String q="delete from transcript where student_id='"+student_id+"';";
        try {
            stmt= conn.createStatement();
            stmt.executeUpdate(q);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }

        return true;
    }

    public boolean viewtranscript(String student_id){
      String transcript="";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT transcript FROM transcript WHERE student_id = ?");
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }

        try {
            ps.setString(1, student_id);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }
        if (rs != null) {
            while (true) {
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
//                    throw new RuntimeException(e);
                    System.out.println(e);
                    return false;
                }

                try {
                    byte[] imgBytes = rs.getBytes(1);
                    transcript=new String(imgBytes);
                } catch (SQLException e) {
//                    throw new RuntimeException(e);
                    System.out.println(e);
                    return false;
                }
                // use the data in some way here
            }
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
                return false;
//                throw new RuntimeException(e);
            }
        }
        else{
            System.out.println("no transcript for this student");
            return false;
        }
        try {
            ps.close();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
//            throw new RuntimeException(e);
        }
System.out.println(transcript);
      return true;
    }

    public boolean viewcourses(){
        String query="select * from course;";

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
                        responseQuery += "course_id ---> ";
                    if (i == 2)
                        responseQuery += "name ---> ";
                    if (i ==3)
                        responseQuery += "dep_id ---> ";
                    if (i ==4)
                        responseQuery += "l ---> ";
                    if (i == 5)
                        responseQuery += "t ---> ";
                    if (i == 6)
                        responseQuery += "p---> ";
                    if (i == 7)
                        responseQuery += "c---> ";

                    String columnValue = rs.getString(i);

                    responseQuery += columnValue+" ";
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                responseQuery+="\n";
            }
            System.out.println(responseQuery);

        } catch (SQLException e) {
            System.out.println("no courses to show");
            return false;
//            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean viewusers(String c){

        String query="select * from student;";
        if(c.equals("1")){
            try {
                stmt= conn.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                ResultSetMetaData rsmd;
                rsmd=rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String responseQuery="";
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        if (i == 1)
                            responseQuery += "student_id ---> ";
                        if (i == 2)
                            responseQuery += "name ---> ";
                        if (i ==3)
                            responseQuery += "batch_id ---> ";
                        if (i ==4)
                            responseQuery += "email ---> ";
                        if (i == 5) continue;
                        if (i == 6)
                            responseQuery += "phone_number---> ";


                        String columnValue = rs.getString(i);

                        responseQuery += columnValue+"          ";
                        // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    responseQuery+="\n\n";
                }
                System.out.println(responseQuery);
            } catch (SQLException e) {
//                throw new RuntimeException(e);
                System.out.println(e);
                return false;
            }


        }
        else if(c.equals("2")){
            try {
                query="select * from instructor;";
                stmt= conn.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                ResultSetMetaData rsmd;
                rsmd=rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String responseQuery="";
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        if (i == 1)
                            responseQuery += "instructor_id ---> ";
                        if (i == 2)
                            responseQuery += "name ---> ";
                        if (i ==3)
                            responseQuery += "email ---> ";
                        if (i ==4)
                            responseQuery += "dep_id ---> ";
                        if (i == 5) continue;
                        if (i == 6)
                            responseQuery += "phone_number---> ";


                        String columnValue = rs.getString(i);

                        responseQuery += columnValue+"      ";
                        // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    responseQuery+="\n\n";
                }
                System.out.println(responseQuery);
            } catch (SQLException e) {
//                throw new RuntimeException(e);
                System.out.println(e);
                return false;
            }
        }
        else{
            System.out.println("you entered incorrect role");
        }
      return true;
    }

}
