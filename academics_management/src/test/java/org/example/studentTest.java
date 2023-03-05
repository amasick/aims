package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class studentTest {
    student stu=new student();
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
    @BeforeAll
    @Test
    void login() {
        assertTrue(stu.login("2020csb2@iitrpr.ac.in","iitropar"));

    }
    @AfterAll
    @Test
    void logout() {
        assertTrue(stu.logout());
    }

    @Test
    void viewprofile() {
        assertTrue(stu.viewprofile());
    }

    @Test
    void updateprofile() {
        assertTrue(stu.updateprofile("dummy","iitropar","1234567890"));
    }

    @Test
    void addCoursewhencgpalessthanlimit() {

        instructor y=new instructor();
        admin z=new admin();
        if(admin.viewsemester().equals("no sem is running")){

        }else{
            z.endsem();
        }
        z.startsem("2020","monsoon");
        z.updatecoursecatalog("DM111");
        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM111","5");
       // when more than two sems but cgpa less than 5
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS301','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertFalse(stu.addCourse("DM111"));
        y.logout();
        z.endsem();
        try {
         stmt.executeUpdate("delete from grades where student_id='2020csb2'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    void addcoursewhenprereqisnotcomplete() {

        instructor y=new instructor();
        admin z=new admin();
        if(admin.viewsemester().equals("no sem is running")){

        }else{
            z.endsem();
        }
        z.startsem("2020","monsoon");
        List<String> data=new ArrayList<String>();
        data.add("DM111");
        z.addcourse("DM112","dummy2","CS","3","3","3","3",data);
        z.updatecoursecatalog("DM112");

        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM112","1");
        try {
            stmt=conn.createStatement();
//            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS301','A','winter','2019')");
//            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertFalse(stu.addCourse("DM112"));
        z.endsem();
        z.deletecourse("DM112");
        y.logout();

    }

    @Test
    void addcoursewhenallconstraintsaresatisfied() {

        instructor y=new instructor();
        admin z=new admin();
        if(admin.viewsemester().equals("no sem is running")){

        }else{
            z.endsem();
        }
        z.startsem("2020","monsoon");
        List<String> data=new ArrayList<String>();
        data.add("DM111");
        z.addcourse("DM112","dummy2","CS","3","3","3","3",data);
        z.updatecoursecatalog("DM112");

        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM112","1");
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','DM111','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertTrue(stu.addCourse("DM112"));
        z.endsem();
        z.deletecourse("DM112");
        y.logout();
        try {
            stmt.executeUpdate("delete from grades where student_id='2020csb2'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void addcoursewhencreditlimitexceeded() {
        int f=stu.credits;
        stu.credits=23;
        instructor y=new instructor();
        admin z=new admin();
        if(admin.viewsemester().equals("no sem is running")){

        }else{
            z.endsem();
        }
        z.startsem("2020","monsoon");
        z.updatecoursecatalog("DM111");

        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM111","1");

        assertFalse(stu.addCourse("DM111"));
        z.endsem();
        y.logout();
    stu.credits=f;
    }
    @Test
    void mycourses() {
if(admin.viewsemester().equals("no sem is running")){
   assertEquals(stu.mycourses(),"error");
}
else{
    assertNotEquals(stu.mycourses(),"error");
}
    }

    @Test
    void deleteCourse() {
        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(stu.deleteCourse("DM111"));
        }
        else{
            instructor y=new instructor();
            y.login("HS0@iitrpr.ac.in","1234");
            y.addCourse("DM111","1");
            assertTrue(stu.addCourse("DM111"));
            assertTrue(stu.deleteCourse("DM111"));
        }
    }

    @Test
    void showGrades() {
        assertEquals(stu.showGrades(),"No grades to show yet");
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','DM111','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertNotEquals(stu.showGrades(),"no grades to show yet");
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("delete from grades where student_id='2020csb2'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getcgpa() {
        assertEquals(stu.getcgpa(), 0.0);
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS301','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(stu.getcgpa(),2.857142857142857);
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("delete from grades where student_id='2020csb2'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void gradcheck() {
        assertFalse(stu.gradcheck());
           admin x=new admin();
           x.addbatch("2010csb","2010","CS");
           x.addcurriculum("DM111","core","2010csb");

        List<String> data=new ArrayList<String>();
        data.add("dm");
        data.add("2010csb");
        data.add("9327223367");

        x.adduser("1",data);
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2010csb0','HS0','DM111','A','winter','2010')");

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("f");
        }
        student f=new student();
        f.login("2010csb0@iitrpr.ac.in","iitropar");
        assertTrue(f.gradcheck());
        f.logout();
        try {
            stmt.executeUpdate("delete from grades where student_id='2010csb0'");
            stmt.executeUpdate("delete from ug_curriculum where course_id='DM111'");
            x.deleteuser("1","2010csb0");
            stmt.executeUpdate("delete from batch where id='2010csb'");

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("ff");
        }

    }

    @Test
    void islessthantwo() {
        assertTrue(stu.islessthantwo());
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','DM111','A','winter','2010')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb2','HS0','DM111','A','winter','2011')");

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("f");
        }
        assertFalse(stu.islessthantwo());

        try {
            stmt.executeUpdate("delete from grades where student_id='2020csb2'");

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("ff");
        }

    }
}