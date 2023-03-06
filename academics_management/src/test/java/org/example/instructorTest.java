package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class instructorTest {
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
instructor x=new instructor();
    @BeforeAll
    @Test
    void login() {
        assertTrue(x.login("HS0@iitrpr.ac.in","1234"));
    }
    @AfterAll
    @Test
    void logout() {
        x.logout();
        assertFalse(x.user);
    }

    @Test
    void viewprofile() {
        assertNotEquals(x.viewprofile(),"error");
    }

    @Test
    void updateprofile() {
        assertTrue(x.updateprofile("dummy","1234","9428730301"));
    }

    @Test
    void addCourse() {
        admin y=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){
            assertFalse(x.addCourse("CS301","3"));
        }
        else{
            Semester_management.endsem();
        }
        Semester_management.startsem("2030","winter");
        y.updatecoursecatalog("CS301");
        assertTrue(x.addCourse("CS301","3"));
        instructor z=new instructor();
        assertFalse(z.addCourse("CS301","3"));
        assertTrue(x.deleteCourse("CS301"));
        Semester_management.endsem();
    }

    @Test
    void offeredCourses() {
        admin y=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){
              assertFalse(x.offeredCourses());
        }
        else{
             Semester_management.endsem();
        }
        Semester_management.startsem("2030","winter");
        y.updatecoursecatalog("CS301");
        assertTrue(x.offeredCourses());
        Semester_management.endsem();
    }

    @Test
    void mycourses() {
        admin y=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){
            assertEquals(x.mycourses(),"error");
        }
        else{
            // when user has no offered courses
           Semester_management.endsem();
        }
Semester_management.startsem("2030","winter");
        y.updatecoursecatalog("DM111");
        assertEquals(x.mycourses(),"you have no offered courses");
        assertTrue(x.addCourse("DM111","3"));
        assertNotEquals(x.mycourses(),"you have no offered courses");
        assertTrue(x.deleteCourse("DM111"));
Semester_management.endsem();
    }

    @Test
    void deleteCourse() {
        admin y=new admin();

        if(Semester_management.viewsemester().equals("no sem is running")){
            assertFalse(x.deleteCourse("DM111"));
        }
        else{
            Semester_management.endsem();

        }
Semester_management.startsem("2030","monsoon");
        y.updatecoursecatalog("DM111");
        assertTrue(x.addCourse("DM111","3"));
        assertTrue(x.deleteCourse("DM111"));
        Semester_management.endsem();
    }

    @Test
    void showGrades() {
        try {
            stmt= conn.createStatement();
            stmt.executeUpdate("insert into grades(student_id,instructor_id,course_id,grade,semester,academic_year) values ('2020csb0','HS0','CS301','A','winter','2020');");
            assertTrue(x.showGrades());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertTrue(x.showGrades());
    }

    @Test
    void enrollmentRequests() {
        admin y=new admin();

        if(Semester_management.viewsemester().equals("no sem is running")){
            assertEquals(x.enrollmentRequests(),"error");
        }
        else{
           Semester_management.endsem();
            }
        Semester_management.startsem("2030","winter");
        y.updatecoursecatalog("DM111");
        x.addCourse("DM111","3");
        assertEquals(x.enrollmentRequests(),"no enrollment requests yet");
        student z=new student();
        assertTrue(z.login("2020csb0@iitrpr.ac.in","iitropar"));
        z.addCourse("DM111");
        assertNotEquals(x.enrollmentRequests(),"no enrollment requests yet");
        assertTrue(z.logout());
        assertTrue(x.deleteCourse("DM111"));
        Semester_management.endsem();
    }

    @Test
    void acceptorreject(){
        admin y=new admin();

        if(Semester_management.viewsemester().equals("no sem is running")){
            assertFalse(x.approveordissaprove("DM111","2020csb2","1"));
            assertFalse(x.approveordissaprove("DM111","2020csb2","2"));
        }
        else{
            Semester_management.endsem();
        }
        Semester_management.startsem("2030","winter");
        y.updatecoursecatalog("DM111");
        x.addCourse("DM111","3");
        student z=new student();
        assertTrue(z.login("2020csb0@iitrpr.ac.in","iitropar"));
        z.addCourse("DM111");
        assertTrue(x.approveordissaprove("DM111","2020csb2","1"));
        assertTrue(x.approveordissaprove("DM111","2020csb2","2"));
        assertTrue(z.logout());
        assertTrue(x.deleteCourse("DM111"));
        Semester_management.endsem();
    }

    @Test
    void submitgrades() {
        admin y=new admin();
        if(Semester_management.viewsemester().equals("no sem is running")){
            assertFalse(x.submitgrades("grades"));
        }
        else{
//checked all the corner cases using different input in files
            Semester_management.endsem();

        }
        Semester_management.startsem("2020","monsoon");
        y.updatecoursecatalog("CS301");
        x.addCourse("CS301","0");
        student a=new student();
        student b=new student();
a.login("2020csb0@iitrpr.ac.in","iitropar");
a.addCourse("CS301");
a.logout();
        b.login("2020eeb0@iitrpr.ac.in","iitropar");
        b.addCourse("CS301");
        b.logout();
        assertTrue(x.approveordissaprove("CS301","2020csb0","1"));
        assertTrue(x.approveordissaprove("CS301","2020eeb0","1"));
        try {
            stmt=conn.createStatement();
            String query="delete from grades where instructor_id='HS0';";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//checking different corner cases
        assertFalse(x.submitgrades("gradeswithoneless"));
        assertFalse(x.submitgrades("gradeswithonemore"));
//
        assertTrue(x.submitgrades("grades"));

        Semester_management.endsem();

        try {
            stmt=conn.createStatement();
            String query="delete from grades where instructor_id='HS0';";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}