package org.example;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class adminTest {

    admin x=new admin();

    @BeforeAll
    @Test
    void login() {
        assertFalse(x.login("afmin","iitropar"));

        assertFalse(x.login("admin","iitropaf")
        );
         assertTrue(x.login("admin","iitropar"));
    }
    @AfterAll
    @Test
    void logout() {
        x.logout();
        assertFalse(x.user);
    }

    @Test
    void addbatch() {
        boolean f=x.addbatch("2010csb","2010","CS");
        assertTrue(f);
        x.deletebatch("2010csb");
    }

    @Test
    void addcourse() {
        List<String> prereq=new ArrayList<String>();
        // adding a prereq-less course
        boolean f=x.addcourse("DM100","dummy_course","CS","3","3","3","3",prereq);

        prereq.add("DM100");
        // when the course har prereqs
        boolean f2=x.addcourse("DM101","dummy_course","CS","3","3","3","3",prereq);
        assertTrue(f);
        assertTrue(f2);
        x.deletecourse("DM100");
        x.deletecourse("DM101");

    }

    @Test
    void addcurriculum() {
        boolean f= x.addcurriculum("CS200","core","2020mcb");
        assertTrue(f);
        x.deletefromcurriculum("CS200","2020mcb");
    }

    @Test
    void showGrades() {
//          String grades=x.showGrades();
        boolean f=x.showGrades("2020csb0");
        assertTrue(f);
    }
//
    @Test
    void adduser() {
        List<String> data=new ArrayList<String>();
        data.add("dummy");
        data.add("2020csb");
        data.add("9327223367");
          String f=x.adduser("1",data);
        List<String> data2=new ArrayList<String>();
        data2.add("dummy");
        data2.add("CS");
        data2.add("9327223367");
        String f2=x.adduser("2",data2);
        assertNotEquals(f,"failed");
        assertNotEquals(f2,"failed");
       x.deleteuser("1",f);
       x.deleteuser("2",f2);
    }

    @Test
    void startsem(){
        String resp=x.viewsemester();
         String f=x.startsem("2026","monsoon");
//         System.out.println(resp);
         if(resp.equals("no sem is running")){
             assertNotEquals(f,"a sem is already running");
             boolean b=x.endsem();
             assertEquals(b,true);
         }
         else{
             assertEquals(f,"a sem is already running");
         }
    }

    @Test
    void endsem() {
        String resp=x.viewsemester();
        boolean f=x.endsem();
//         System.out.println(resp);
        if(resp.equals("no sem is running")){
         assertEquals(f,false);
        }
        else{
            assertEquals(f,true);
        }
    }

    @Test
    void updatecoursecatalog() {
      if(admin.viewsemester().equals("no sem is running")){
          assertFalse(x.updatecoursecatalog("CS301"));
          x.startsem("2030","f");
          assertTrue(x.updatecoursecatalog("CS301"));
          assertTrue(x.deletefromcoursecatalog("CS301"));
          x.endsem();
      }
      else{
          assertTrue(x.updatecoursecatalog("CS301"));
          assertTrue(x.deletefromcoursecatalog("CS301"));
      }
    }


    @Test
    void generateandsubmitransscript() {
        assertTrue(x.generatetranscript("2020csb0"));

boolean f=x.submittranscript("2020csb0");
assertEquals(f,true);
assertEquals(x.deletetranscript("2020csb0"),true);
    }

    @Test
    void viewtranscript() {
        assertTrue(x.generatetranscript("2020csb0"));
        boolean f=x.submittranscript("2020csb0");
        assertEquals(f,true);
          f=x.viewtranscript("2020csb0");
        assertEquals(f,true);

        assertEquals(x.deletetranscript("2020csb0"),true);

    }

    @Test
    void viewcourses() {
         assertEquals(x.viewcourses(),true);
    }

    @Test
    void viewusers() {
        assertEquals(x.viewusers("1"),true);
        assertEquals(x.viewusers("2"),true);
    }
}