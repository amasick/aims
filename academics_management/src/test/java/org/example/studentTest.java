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
        assertTrue(stu.login("2020csb0@iitrpr.ac.in","iitropar"));

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
     void offeredcourses(){
        admin y=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){
            assertEquals(stu.offeredCourses(),"no courses offered yet");

        }
        else{
            Semester_management.endsem();
        }

        Semester_management.startsem("2020","monsoon");
        y.updatecoursecatalog("CS301");
        assertNotEquals(stu.offeredCourses(),"no course is offered yet");
        Semester_management.endsem();
     }
    @Test
    void addCoursewhencgpalessthanlimit() {

        instructor y=new instructor();
        admin z=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){

        }else{
            Semester_management.endsem();
        }
        Semester_management.startsem("2020","monsoon");
        z.updatecoursecatalog("DM111");
        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM111","5");
       // when more than two sems but cgpa less than 5
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS301','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertFalse(stu.addCourse("DM111"));
        y.logout();
        Semester_management.endsem();
        try {
         stmt.executeUpdate("delete from grades where student_id='2020csb0'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    void addcoursewhenprereqisnotcomplete() {

        instructor y=new instructor();
        admin z=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){

        }else{
            Semester_management.endsem();
        }
        Semester_management.startsem("2020","monsoon");
        List<String> data=new ArrayList<String>();
        data.add("DM111");
        z.addcourse("DM112","dummy2","CS","3","3","3","3",data);
        z.updatecoursecatalog("DM112");

        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM112","1");
        try {
            stmt=conn.createStatement();
//            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS301','A','winter','2019')");
//            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertFalse(stu.addCourse("DM112"));
        Semester_management.endsem();
        z.deletecourse("DM112");
        y.logout();

    }

    @Test
    void addcoursewhenallconstraintsaresatisfied() {

        instructor y=new instructor();
        admin z=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){

        }else{
            Semester_management.endsem();
        }
        Semester_management.startsem("2020","monsoon");
        List<String> data=new ArrayList<String>();
        data.add("DM111");
        z.addcourse("DM112","dummy2","CS","3","3","3","3",data);
        z.updatecoursecatalog("DM112");

        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM112","1");
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','DM111','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertTrue(stu.addCourse("DM112"));
        Semester_management.endsem();
        z.deletecourse("DM112");
        y.logout();
        try {
            stmt.executeUpdate("delete from grades where student_id='2020csb0'");
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
        if(Semester_management.viewsemester().equals("no sem is running")){

        }else{
            Semester_management.endsem();
        }
        Semester_management.startsem("2020","monsoon");
        z.updatecoursecatalog("DM111");

        y.login("HS0@iitrpr.ac.in","1234");
        y.addCourse("DM111","1");

        assertFalse(stu.addCourse("DM111"));
        Semester_management.endsem();
        y.logout();
    stu.credits=f;
    }
    @Test
    void mycourses() {

if(Semester_management.viewsemester().equals("no sem is running")){
   assertEquals(stu.mycourses(),"error");
}
else{
    assertNotEquals(stu.mycourses(),"error");
}
    }

    @Test
    void deleteCourse() {
        if(Semester_management.viewsemester().equals("no sem is running")){
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
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','DM111','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertNotEquals(stu.showGrades(),"no grades to show yet");
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("delete from grades where student_id='2020csb0'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getcgpa() {
        assertEquals(stu.getcgpa(), 0.0);
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS301','A','winter','2019')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS302','A','monsoon','2019')");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals(stu.getcgpa(),2.857142857142857);
        try {
            stmt=conn.createStatement();
            stmt.executeUpdate("delete from grades where student_id='2020csb0'");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void gradcheck() {
        try {
            stmt= conn.createStatement();
            stmt.executeUpdate("delete from ug_curriculum");
            stmt.executeUpdate("delete from grades where student_id='2020csb0';");

        } catch (SQLException e) {
            System.out.println(e);
        }
        //when core courses not completed
        admin y=new admin();
           admin x=new admin();

        try {
            y.addcurriculum("CS456","core","2020csb");
            stmt = conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS456','A','winter','2020')");

        } catch (SQLException e) {
            System.out.println(e);
        }
        //when elective completed is less than two
        assertEquals(stu.gradcheck(),"you have not completed 2  electives");

        try {
            stmt = conn.createStatement();
            y.addcurriculum("EE345","elective","2020csb");
            y.addcurriculum("HS104","elective","2020csb");

            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','EE345','A','winter','2020')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','HS104','A','winter','2020')");


        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(stu.gradcheck(),"you have not completed capstone yet");
// when total core credits less than 70
        System.out.println("ggs");
        try {
            stmt = conn.createStatement();
            y.addcurriculum("CP302","capstone","2020csb");
            y.addcurriculum("CP303","capstone","2020csb");

            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CP302','A','winter','2020')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CP303','A','winter','2020')");


        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(stu.gradcheck(),"program core credits less than 70");

        // when total engineering credits less than 30
        try {
            stmt = conn.createStatement();
            y.addcurriculum("CS301","core","2020csb");

            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS301','A','winter','2020')");

        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(stu.gradcheck(),"engineering core credits less than 30");

        // when total elective credits less than 35
        try {
            stmt = conn.createStatement();
            y.addcurriculum("GE444","general engineering","2020csb");

            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','GE444','A','winter','2020')");

        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(stu.gradcheck(),"open elective credits less than 35");

        // when total elective credits less than 35
        try {
            stmt = conn.createStatement();
            y.addcurriculum("GE108","elective","2020csb");

            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','GE108','A','winter','2020')");

        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(stu.gradcheck(),"total credits less than 140");

        // when all conditions satisfied
        try {
            stmt = conn.createStatement();
            y.addcurriculum("CS505","program elective","2020csb");

            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS505','A','winter','2020')");

        } catch (SQLException e) {
            System.out.println(e);
        }
        assertEquals(stu.gradcheck(),"eligible for graduation");


        try {
            stmt.executeUpdate("delete from grades where student_id='2020csb0';");

        } catch (SQLException e) {
            System.out.println(e);
        }

    }


    @Test
    void islessthantwo() {
        assertTrue(stu.islessthantwo());
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','DM111','A','winter','2010')");
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','DM111','A','winter','2011')");

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("f");
        }
        assertFalse(stu.islessthantwo());

        try {
            stmt.executeUpdate("delete from grades where student_id='2020csb0'");

        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("ff");
        }

    }
}