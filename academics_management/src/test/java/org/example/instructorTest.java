package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class instructorTest {
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
        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(x.addCourse("CS301","3"));
        }
        else{
            y.endsem();
        }
        y.startsem("2030","winter");
        y.updatecoursecatalog("CS301");
        assertTrue(x.addCourse("CS301","3"));
        instructor z=new instructor();
        assertFalse(z.addCourse("CS301","3"));
        assertTrue(x.deleteCourse("CS301"));
        y.endsem();
    }

    @Test
    void offeredCourses() {
        admin y=new admin();
        if(admin.viewsemester().equals("no sem is running")){
              assertFalse(x.offeredCourses());
        }
        else{
             y.endsem();
        }
        y.startsem("2030","winter");
        y.updatecoursecatalog("CS301");
        assertTrue(x.offeredCourses());
        y.endsem();
    }

    @Test
    void mycourses() {
        admin y=new admin();
        if(admin.viewsemester().equals("no sem is running")){
            assertEquals(x.mycourses(),"error");
        }
        else{
            // when user has no offered courses
           y.endsem();
        }
y.startsem("2030","winter");
        y.updatecoursecatalog("DM111");
        assertEquals(x.mycourses(),"you have no offered courses");
        assertTrue(x.addCourse("DM111","3"));
        assertNotEquals(x.mycourses(),"you have no offered courses");
        assertTrue(x.deleteCourse("DM111"));
y.endsem();
    }

    @Test
    void deleteCourse() {
        admin y=new admin();

        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(x.deleteCourse("DM111"));
        }
        else{
            y.endsem();

        }
y.startsem("2030","monsoon");
        y.updatecoursecatalog("DM111");
        assertTrue(x.addCourse("DM111","3"));
        assertTrue(x.deleteCourse("DM111"));
        y.endsem();
    }

    @Test
    void showGrades() {
        assertTrue(x.showGrades());
    }

    @Test
    void enrollmentRequests() {
        admin y=new admin();

        if(admin.viewsemester().equals("no sem is running")){
            assertEquals(x.enrollmentRequests(),"error");
        }
        else{
           y.endsem();
            }
        y.startsem("2030","winter");
        y.updatecoursecatalog("DM111");
        x.addCourse("DM111","3");
        assertEquals(x.enrollmentRequests(),"no enrollment requests yet");
        student z=new student();
        assertTrue(z.login("2020csb0@iitrpr.ac.in","iitropar"));
        z.addCourse("DM111");
        assertNotEquals(x.enrollmentRequests(),"no enrollment requests yet");
        assertTrue(z.logout());
        assertTrue(x.deleteCourse("DM111"));
        y.endsem();
    }

    @Test
    void acceptorreject(){
        admin y=new admin();

        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(x.approveordissaprove("DM111","2020csb2","1"));
            assertFalse(x.approveordissaprove("DM111","2020csb2","2"));
        }
        else{
            y.endsem();
        }
        y.startsem("2030","winter");
        y.updatecoursecatalog("DM111");
        x.addCourse("DM111","3");
        student z=new student();
        assertTrue(z.login("2020csb0@iitrpr.ac.in","iitropar"));
        z.addCourse("DM111");
        assertTrue(x.approveordissaprove("DM111","2020csb2","1"));
        assertTrue(x.approveordissaprove("DM111","2020csb2","2"));
        assertTrue(z.logout());
        assertTrue(x.deleteCourse("DM111"));
        y.endsem();
    }

    @Test
    void submitgrades() {
        admin y=new admin();
        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(x.submitgrades("grades"));
        }
        else{
//checked all the corner cases using different input in files
            y.endsem();

        }
        assertFalse(x.submitgrades("gradeswithoneless"));
        assertFalse(x.submitgrades("gradeswithonemore"));

        y.startsem("2020","monsoon");
        assertTrue(x.submitgrades("grades"));
        y.endsem();
    }
}