package org.example;
import java.util.Scanner;
import java.sql.*;
import java.sql.Connection;
import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class student implements student_academics{
     Connection conn = Connect.ConnectDB();
     Statement stmt = null;
     Scanner input = new Scanner(System.in);
    static public  String user_id="";

    public  String token="'logged in'";

   static  public  boolean user=false;
    static public  String batch_id="";
 static int credits=0;
    public boolean login(String email,String password){


            String query="select * from student where email='"+email+"' and password='"+password+"';";
            try {
                stmt=conn.createStatement();
                ResultSet rs;

                rs=stmt.executeQuery(query);

                int f=0;
                while(rs.next()){
                   f++;
                    user_id=rs.getString(1);
                    batch_id=rs.getString(3);
                    credits=rs.getInt(8);
                }

                if( f==0){
                   return false;
                }
                else{
                    user=true;
                    query="update student set token="+token+" where id='"+user_id+"';";
                    stmt.executeUpdate(query);
                    System.out.println("logged in successfully");
                    Write_toLog.write("student",user_id,"logged in");
                }

            } catch (SQLException e) {
                System.out.println(e);
              return false;
            }

        return true;
    }
    public boolean logout(){
        user=false;
        String query="update student set token="+"'logged out'"+" where id='"+user_id+"';";
//        System.out.println(query);
        try {

            stmt= conn.createStatement();
            stmt.executeUpdate(query);

            Write_toLog.write("student",user_id,"logged out");


        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }
        System.out.println("logged out successfully");
        return true;
    }
    public boolean viewprofile()
    {


        String query="select * from student where id='"+user_id+"';";

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
                        responseQuery += "      batch_id ---> ";
                    if (i == 4)
                        responseQuery += "      email ---> ";
                    if (i == 5)
                        continue;
                    if (i == 6)
                        responseQuery += "      phone_number ---> ";
                    if (i == 7)
                        responseQuery += "      curr_sem ---> ";
                    if (i == 8)
                        responseQuery += "      credits ---> ";

                    if(i==9)continue;
                        String columnValue = rs.getString(i);
                        responseQuery += columnValue+" ";

                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }

            }
            System.out.println(responseQuery);

        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }

        return true;

    }
    public boolean updateprofile(String name,String password,String phone_number){

        String query =" update student set name='"+name+"',phone_number='"+phone_number+"',password='"+password+"' where id='"+user_id+"';";

        try {
            stmt=conn.createStatement();
            stmt.executeUpdate(query);
            System.out.println("profile updated successfully");


        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
            return false;
        }
return true;
    }
    public boolean addCourse(String course_id){

if(Semester_management.is_student_window()==0){
    System.out.println("you cannot add courses as window is closed");
    return false;
}
            try {
                stmt= conn.createStatement();
                String query="select * from course_offering where course_id='"+course_id+"';";
double cgpa_limit=0.0;
String instructor_id="";
                try {
                    ResultSet rs;


                    rs=stmt.executeQuery(query);
int f=0;
while(rs.next()){f++;cgpa_limit=rs.getInt(2);instructor_id=rs.getString(3);}
if(f==0){System.out.println("no such course is offered");
    return false;}

                }
                catch (SQLException e){
                    System.out.println(e);

                    return false;
                }
                System.out.println(course_id);
if(islessthantwo()==false) {
    double cgpa = getcgpa();

    if (cgpa > 0 && cgpa < cgpa_limit) {
        System.out.println("you cannot take this course as your cgpa is less than required " + cgpa_limit);
        return false;
    }
}


query="select * from course where id='"+course_id+"';";
              try{
                  ResultSet rs= stmt.executeQuery(query);
                  int c=0;
                  while (rs.next()){
                      c=rs.getInt(7);
                  }
                  if(credits+c>24){
                      System.out.println("your credit limit has exceeded for this semester");
                      return false;
                  }
              }
              catch (SQLException e){
                  System.out.println(e);
                  return false;
              }



                try {


                   query="select * from prereq where course_id='"+course_id+"';";
                   ResultSet rs=stmt.executeQuery(query);
                   int flag=1;
                   while (rs.next()){
                       String prereq=rs.getString(2);
                       try{
                           stmt= conn.createStatement();
                           query="select * from grades where course_id='"+prereq+"' and student_id='"+user_id+"';";
                           ResultSet rs2=stmt.executeQuery(query);
                           String grade="";
                           while (rs2.next()){
                               grade=rs2.getString(3);
                           }
                           if(grade.equals("F") || grade.equals("")){
                               System.out.println("Complete the course "+prereq+" first to register this course");
                               flag=0;
                           }
                       }catch (SQLException e){
                           System.out.println(e);

                       }
                   }


if(flag==0){
    return false;

}
                        query="insert into registration_status(course_id,student_id,instructor_id,status) values ('"+course_id+"','"+user_id+"','"+instructor_id+"',"+"'pending instructor approval');";
                        stmt.executeUpdate(query);
                        System.out.println("Added course successfully");

                }
                catch (SQLException e){
//                    System.out.println(e);
                }

            } catch (SQLException e) {
//                throw new RuntimeException(e);
                System.out.println(e);
            }


        return true;
    }

    public String offeredCourses()  {

        String query="\n" +
                "select course_offering.course_id,course_offering.cgpa_limit,ug_curriculum.course_type,course_offering.instructor_id\n" +
                "from ug_curriculum,course_offering\n" +
                "where '"+batch_id+"'=ug_curriculum.batch_id and ug_curriculum.course_id=course_offering.course_id;";
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
                          responseQuery += "course_id ---> ";
                      if (i == 2)
                          responseQuery += "cgpa_limit ";
                      if (i == 3)
                          responseQuery += "course_type ---> ";
                      if (i == 4)
                          responseQuery += "instructor_id" +
                                  " ---> ";
                      String columnValue = rs.getString(i);
                      responseQuery += columnValue+" ";
                      // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                  }
                  responseQuery+="\n";
              }
            return responseQuery;

          }
          catch (SQLException e){
                 System.out.println(e);
                 return "no courses offered yet";
          }

    }
    public String mycourses(){
        String query="select * from registration_status where student_id='"+user_id+"';";
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
                        continue;
                    if (i == 3)
                        responseQuery += "instructor_id ---> ";
                    if (i == 4)
                        responseQuery += "status ---> ";

                    String columnValue = rs.getString(i);
                    responseQuery += columnValue+"      ";
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                responseQuery+="\n\n";
            }
            if(f==0){
                return "you have no courses";
            }
            return responseQuery;

        } catch (SQLException e) {
            System.out.println(e);
            return "error";
        }

    }
    public boolean deleteCourse(String course_id)
    {
        if(Semester_management.is_student_window()==0){
            System.out.println("you cannot drop courses as window is closed");
            return false;
        }
            String query="delete from registration_status where course_id='"+course_id+"' and student_id='"+user_id+"';";
            try {
                stmt=conn.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println("you have not added this course yet");
//                throw new RuntimeException(e);
                return false;
            }
        return true;
    }

    public String showGrades()
    {
             String query="select * from grades where student_id='"+user_id+"';";
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
                        responseQuery += "student_id ---> ";
                    if (i == 2)
                        responseQuery += "instructor_id ---> ";
                    if (i == 3)
                        responseQuery += "course_id ---> ";
                    if (i == 4)
                        responseQuery += "grade ---> ";
                    if (i == 5)
                        responseQuery += "semester ---> ";
                    if (i == 6)
                        responseQuery += "academic_year ---> ";

                    String columnValue = rs.getString(i);
                    responseQuery += columnValue+"          ";
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                responseQuery+="\n\n";
            }
            if(f==0){
                return "No grades to show yet" ;
            }
            return responseQuery;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public double getcgpa()
    {
         String query="select grades.grade,course.c from grades,course where student_id='"+user_id+"' and course.id=grades.course_id;";
         double cgpa=0.0;
        double creds=0;
        double points=0;
        int f=0;
        String cid="",grade="";

        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            while (rs.next()) {
                f++;

                 grade=rs.getString(1);
                 creds+=rs.getInt(2);

                switch (grade){
                    case "A":points+=10;break;
                    case "A-":points+=9;break;
                    case "B":points+=8;break;
                    case "B-":points+=7;break;
                    case "C":points+=6;break;
                    case "C-":points+=5;break;
                    case "D":points+=4;break;
                    case "E":points+=2;break;
                    case "F":points+=0;break;

                }
                System.out.println(creds +" " + points);
            }

            if(f==0){
                return 0.0;
            }
            else{
                cgpa=points/creds;
            }
        } catch (SQLException e) {
System.out.println(e);
            if(creds!=0.0 && f!=0){
            cgpa=points/creds;
//            System.out.println(cgpa);
            return cgpa;}
            return 0.0;
        }
        System.out.println(cgpa);
        return cgpa;
    }

    public String gradcheck(){
        // checking if all program cores are completed
String query="\n" +
        "select ug_curriculum.course_id\n" +
        "from ug_curriculum\n" +
        "where course_type='core' and batch_id='"+batch_id+"'\n" +
        "except\n" +
        "select grades.course_id\n" +
        "from grades\n" +
        "where grades.grade!='F' and grades.student_id='"+user_id+"';";
// if min 2 electives are completed
        String q1="select * from ug_curriculum, grades where ug_curriculum.course_id=grades.course_id and ug_curriculum.course_type='elective' and grades.grade!='F';";

        // if capstone is completed there will be 2 entries in grade table if the student completes 2 BTP in 2 sem
        String q2="select * from grades where (grades.course_id='CP302' or grades.course_id='CP303') and grades.grade!='F';";
        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);

            int f=0;
            String responsequery="";
            while (rs.next()){
                responsequery+=rs.getString(1)+"   ";
                f++;
            }
//            System.out.println(responsequery);

            if(f>0){
                return"you have not completed all core courses yet";
            }

            rs=stmt.executeQuery(q1);
            f=0;
            while(rs.next())f++;
            if(f<2) {
                return "you have not completed 2  electives";
            }
f=0;
            rs=stmt.executeQuery(q2);
            while(rs.next())f++;

            if(f!=2) {
                return "you have not completed capstone yet";
            }

        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
        }

        int program_credits=0,engineering_credits=0,elective_credits=0;
        String query1="select c from course,ug_curriculum,grades where course.id=grades.course_id and ug_curriculum.course_id=course.id and  (ug_curriculum.course_type='core' or ug_curriculum.course_type='program elective') and grades.grade!='F' and grades.student_id='"+user_id+"';";
        String query2="select c from course,ug_curriculum,grades where course.id=grades.course_id and ug_curriculum.course_id=course.id and  ug_curriculum.course_type='general engineering' and grades.grade!='F' and grades.student_id='"+user_id+"';";
        String query3="select c from course,ug_curriculum,grades where course.id=grades.course_id and ug_curriculum.course_id=course.id and  ug_curriculum.course_type='elective' and grades.grade!='F' and grades.student_id='"+user_id+"';";
        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query1);
            while(rs.next())program_credits+=rs.getInt(1);
             rs=stmt.executeQuery(query2);
            while(rs.next())engineering_credits+=rs.getInt(1);

            rs=stmt.executeQuery(query3);
            while(rs.next())elective_credits+=rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // total credits>120

        // program core credits >=60
        if(program_credits<70){
            return "program core credits less than 70";
        }
        // engineering core >=30

        if(engineering_credits<30){
            return "engineering core credits less than 30";
        }

        // electives >=30
        if(elective_credits<35){
            return  "open elective credits less than 35";
        }
System.out.println(program_credits+engineering_credits+elective_credits);
        if(program_credits+engineering_credits+elective_credits<140){
            return "total credits less than 140";
        }
return "eligible for graduation";
    }


    public  boolean islessthantwo (){
        String query="select count(*) from grades where student_id='"+user_id+"' GROUP BY academic_year,semester;";
        int f=0;
        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            while(rs.next()){
                f++;
            }
        } catch (SQLException e) {
            System.out.println(e);
//            throw new RuntimeException(e);
        }
        if(f<2)return true;
        else return false;
    }

}


