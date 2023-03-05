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
        while(true){
            System.out.println("Welcome to the academics management portal IIT ROPAR!");
            Scanner input = new Scanner(System.in);
            String c="";

            int fs=0,fi=0;
student stu=new student();
instructor ins=new instructor();
admin x=new admin();
            String query="select * from student where token='logged in';";

            try {
                stmt= conn.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                while(rs.next()){
                    stu.user=true;
                    stu.user_id=rs.getString(1);
                    stu.batch_id=rs.getString(3);
                    stu.credits=rs.getInt(7);
                    fs++;
                    c="1";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if(fs==0){
                query="select * from instructor where token='logged in';";
                try {
                    stmt= conn.createStatement();
                    ResultSet rs=stmt.executeQuery(query);
                    while(rs.next()){
                        ins.user=true;
                        ins.user_id=rs.getString(1);
                        fi++;
                        c="2";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


if(fs==0 && fi==0){
    System.out.println("Enter your role");
    System.out.println("0. to exit ");
    System.out.println("1. student");
    System.out.println("2. instructor");
    System.out.println("3. admin");
c=input.nextLine();
//    System.out.println("f");
    if(c.equals("0")){break;}
}


            switch (c){

                case "1":

                    if(fs==0){
                        String email="",password="";
                        while(true) {
                            System.out.println("enter your email");
                            email = input.nextLine();
                            System.out.println("enter your password");
                            password = input.nextLine();
                            if(stu.login(email,password))break;
                            else{
                                System.out.println("wrong credentials");
                                System.out.println("press any key to continue");
                                input.nextLine();
                            }

                        }
                    }
                    while(stu.user){
                        System.out.println("Press \n0. to logout \n1. to view profile\n2. to update profile\n3. to view the offered courses\n4. to add course\n5. to delete Course\n6. to view your courses\n7. to view grades\n8. to view your cgpa\n9. to check graduation  ");
                        String r="";

                        r=input.nextLine();
                        switch (r){
                            case "0":
                                stu.logout();
                                break;
                            case "1":{
                                stu.viewprofile();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;

                            }
                            case "2": {

                                String name="",password="",phone_number;
                                System.out.println("enter name to update");
                                name=input.nextLine();
                                System.out.println("enter phone number to update");
                                phone_number=input.nextLine();
                                System.out.println("enter password to update");
                                password=input.nextLine();
stu.updateprofile(name,password,phone_number);
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "3": while(true){
                                String f = stu.offeredCourses();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "4": {

                                while(true){
                                    String course_id;
                                    System.out.println("enter the course_id or 0 to exit");
                                    course_id = input.nextLine();
                                    if (course_id.equals("0")) {
                                        break;
                                    }
                                    stu.addCourse(course_id);
                                    System.out.println("press any key to continue");
                                    input.nextLine();

                                }
                            }
                            case "5": while(true){
                                String course_id;
                                System.out.println("enter course_id to delete or 0 to exit");
                                course_id=input.nextLine();
                                if(course_id.equals("0")){
                                    break;
                                }
                                stu.deleteCourse(course_id);
                            }
                            case "6": {
                                stu.mycourses();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "7": {
                                stu.showGrades();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "8": {
                                double f = stu.getcgpa();
                                System.out.println(f);
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "9": {
                                if(stu.gradcheck())
                                    System.out.println("eligible for graduation");
                                    else
                                    System.out.println("not eligible for graduation");
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }

                            default:System.out.println("please follow the instructions");
                                break;
                        }
                    }

                    break;
                case "2":
                    if(fi==0){
                        while(true){
                            String email="",password="";
                            System.out.println("enter your email");
                            email=input.nextLine();
                            System.out.println("enter your password");
                            password=input.nextLine();
                            if(ins.login(email,password))break;
                            else{
                                System.out.println("wrong credentials");
                                System.out.println("press any key to continue");
                                input.nextLine();
                            }

                        }

                    }
                    while(ins.user){
                        System.out.println("Press \n0. to logout \n1. to view profile\n2. to update profile\n3. to view the course catalog\n4. to add course\n5. to delete Course\n6. to view your courses\n7. to view grades of all students\n8. to view enrollment requests\n9.to approve or disapprove enrollment requests\n10. to submit grades  ");
                        String r="";
                        r=input.nextLine();
                        switch (r){
                            case "0": ins.logout();
                                break;
                            case "1": {
                                String resp = ins.viewprofile();
                                System.out.println(resp);
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "2": {
                                String name="",password="",phone_number;
                                System.out.println("enter name to update");
                                name=input.nextLine();
                                System.out.println("enter phone number to update");
                                phone_number=input.nextLine();
                                System.out.println("enter password to update");
                                password=input.nextLine();
                                if(ins.updateprofile(name,password,phone_number))
                                System.out.println("profile updated successfully");
                                else System.out.println("some error occurred");
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "3": ins.offeredCourses();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            case "4":
                            while(true)
                            {
                                String course_id;
                                System.out.println("enter the course_id or 0 to exit");
                                course_id=input.nextLine();
                                if(course_id.equals("0")){
                                    break;
                                }
                                String cgpa_limit;
                                System.out.println("set the cgpa limit for this course");
                                cgpa_limit=input.nextLine();
                                ins.addCourse(course_id,cgpa_limit);

                            }
                                break;
                            case "5":
                                while(true){
                                    String course_id;
                                    System.out.println("enter course_id to delete or 0 to exit");
                                    course_id=input.nextLine();

                                    if(course_id.equals("0")){
                                        break;
                                    }
                                    ins.deleteCourse(course_id);

                                }
                                break;
                            case "6": {
                                String resp = ins.mycourses();
                                System.out.println(resp);
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            }
                            case "7":ins.showGrades();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            case "8":{String resp=ins.enrollmentRequests();
                            System.out.println(resp);
                            System.out.println("press any key to continue");
                            input.nextLine();
                                break;}
                            case "9":{
                                while(true){
                                    String course_id,student_id;
                                    System.out.println("enter course_id or 0 to exit");
                                    course_id=input.nextLine();
                                    if(course_id.equals("0")){
                                        break;
                                    }
                                    System.out.println("enter Student_id ");
                                    student_id=input.nextLine();
                                    String resp;
                                    System.out.println("press 1 to approve and 2 to disapprove");
                                    resp=input.nextLine();
                                    ins.approveordissaprove(course_id,student_id,resp);
                                }
break;
                            }
                            case "10":{

                                ins.submitgrades("grades");
                                System.out.println("press any key to continue");
                                input.nextLine();
                            }
                            default:System.out.println("please follow the instructions");
                                break;
                        }
                    }

                    break;
                case "3":
                    while(true){
                        String username="",password="";
                        System.out.println("enter the username");
                        username=input.nextLine();
                        System.out.println("enter the password");
                        password=input.nextLine();
                        boolean f=x.login(username,password);
                        if(f)break;
                        else System.out.println("wrong credentials");
                    }

                    int flag=1;
                    while(flag==1){
                        System.out.println("Press \n0. to logout \n1. to add a new batch\n2. to add a new course\n3. to add or update a curriculum\n4. to start sem\n5. to end sem\n6. to add course to course_catalog\n7. to view grades of all students\n8. to add users\n9. to generate student transcripts \n10. to view transcript\n11. to view courses\n12. to view users ");
                        String r="";

                        r=input.nextLine();
                        switch (r){
                            case "0":
                                flag=0;
                                x.logout();
                                break;
                            case "1":{
                                while(true){
                                    String batch_id="",year="",dep_id="";

                                    System.out.println("enter batch id");
                                    batch_id=input.nextLine();
                                    System.out.println("enter year");
                                    year=input.nextLine();
                                    System.out.println("enter department id");
                                    dep_id=input.nextLine();
                                    x.addbatch(batch_id,year,dep_id);
                                    System.out.println("press 0 for exit and 1 to continue");
                                    if(input.nextLine().equals("0")){
                                        break;
                                    }
                                }

                                break;
                            }
                            case "2": {
                                while(true){
                                    String course_id="",course_name="",dep_id="",l,t,p,cc;
                                    System.out.println("Enter course id");
                                    course_id=input.nextLine();
                                    System.out.println("Enter course name");
                                    course_name=input.nextLine();
                                    System.out.println("Enter dep_id");
                                    dep_id=input.nextLine();
                                    System.out.println("Enter number of lectures per week");
                                    l=input.nextLine();
                                    System.out.println("Enter number of tutorials per week");
                                    t=input.nextLine();
                                    System.out.println("Enter course practicals per week");
                                    p=input.nextLine();
                                    System.out.println("Enter course credits");
                                    cc=input.nextLine();
                                    List<String> prereq=new ArrayList<String>();
                                    while (true){
                                        String pre;
                                        System.out.println("enter the course code of the prerequisite course of the course "+course_id+" or 0 to exit");
                                        pre=input.nextLine();
                                        if(pre.equals("0"))break;
                                        prereq.add(pre);
                                    }
                                    x.addcourse(course_id,course_name,dep_id,l,t,p,cc,prereq);
                                    System.out.println("press 0 for exit and 1 to continue");
                                    if(input.nextLine().equals("0")){
                                        break;
                                    }
                                }
                                break;

                            }
                            case "3":
                            {
                                while (true){
                                    String course_id="",course_type,batch_id;
                                    System.out.println("enter the course id or enter 0 to quit");
                                    course_id=input.nextLine();
                                    if(course_id.equals("0")){
                                        break;
                                    }
                                    System.out.println("enter the batch_id ");
                                    batch_id=input.nextLine();
                                    System.out.println("enter the course type");
                                    course_type=input.nextLine();
                                    x.addcurriculum(course_id,course_type,batch_id);
                                }
                                break;

                            }

                            case "4": {
                                String academic_year,semester;
                                System.out.println("enter the academic year");
                                academic_year=input.nextLine();
                                System.out.println("enter the semester monsoon/winter");
                                semester=input.nextLine();
                               String resp= x.startsem(academic_year,semester);
                                System.out.println(resp);
                                if(!resp.equals("a sem is already running")) {
                                    System.out.println("please add course to the current sem's course catalog");
                                }
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;

                            }
                            case "5":
                                x.endsem();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            case "6": while(true){
                                String course_id;
                                System.out.println("enter course code or enter 0 to exit");
                                course_id=input.nextLine();
                                if(course_id.equals("0")){
                                    break;
                                }
                                x.updatecoursecatalog(course_id);
                            }
                            case "7":
                                while(true){
                                    String student_id="0";
                                    System.out.println("enter student_id or 0 to exit");
                                    student_id=input.nextLine();
                                    if(student_id.equals("0"))break;
                                    x.showGrades(student_id);
                                    System.out.println("press any key to continue");
                                    input.nextLine();
                                }
                                break;
                            case "8":
                                int f=1;
                                while (f==1){
                                    System.out.println("press \n0 to exit\n1 to add student\n2 to add instructor");
                                    String role=input.nextLine();
                                    List<String> data=new ArrayList<String>();
                                    switch(role){
                                        case "0": f=1;
                                        break;
                                        case "1":
                                        {
                                            String student_name="",batch_id="",phone_number="";
                                            System.out.println("enter name of the student");
                                            student_name=input.nextLine();
                                            System.out.println("enter batch_id of the student");
                                            batch_id=input.nextLine();
                                            System.out.println("enter phone number of the student");
                                            phone_number=input.nextLine();
                                            data.add(student_name);
                                            data.add(batch_id);
                                            data.add(phone_number);
                                            x.adduser("1",data);
                                        }
                                        break;
                                        case "2":
                                        {
                                            String instructor_name="",dep_id="",phone_number="";
                                            System.out.println("enter name of the student");
                                            instructor_name=input.nextLine();
                                            System.out.println("enter batch_id of the student");
                                            dep_id=input.nextLine();
                                            System.out.println("enter phone number of the student");
                                            phone_number=input.nextLine();
                                            data.add(instructor_name);
                                            data.add(dep_id);
                                            data.add(phone_number);
                                            x.adduser("2",data);
                                        }
                                        break;
                                        default: System.out.println("enter valid role");
                                    }
                                }
                                break;
                            case "9":{
                                String student_id="";
                                System.out.println("enter the student_id");
                                student_id=input.nextLine();
                                boolean ffff=x.generatetranscript(student_id);
                                ffff&=x.submittranscript(student_id);
                                if(ffff)System.out.println("generated successfully");
                                else System.out.println("somme error occured");

                                System.out.println("press any key to continue");
                                input.nextLine();
                            }
                                break;
                            case "10":
                                String student_id="";
                                System.out.println("enter student id");
                                student_id=input.nextLine();
                                x.viewtranscript(student_id);
                            break;
                            case "11":x.viewcourses();
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            case "12":
                                String rol="0";
                                System.out.println("enter 1 for student and 2 for instructor");
                                rol=input.nextLine();
                                x.viewusers(rol);
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            default:System.out.println("please follow the instructions");
                                break;
                        }
                    }

                    break;
                default:
                    System.out.println("invalid role");
                    break;
            }

        }

        }

}