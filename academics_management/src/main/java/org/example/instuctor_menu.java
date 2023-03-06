package org.example;
import java.util.Scanner;
public class instuctor_menu {
    static Scanner input = new Scanner(System.in);
    static instructor ins=new instructor();
    public static boolean handlelogin(){
        while(true){
            String email="",password="";
            System.out.println("enter your email or 0 to exit");
            email=input.nextLine();
            if(email.equals("0"))return false;
            System.out.println("enter your password");
            password=input.nextLine();
            if(ins.login(email,password))break;
            else{
                System.out.println("wrong credentials");
                System.out.println("press any key to continue");
                input.nextLine();
            }

        }
        return true;
    }
public static void handlemenu(){
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
}
}
