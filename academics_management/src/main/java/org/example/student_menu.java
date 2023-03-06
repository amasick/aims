package org.example;

import java.util.Scanner;

public class student_menu {
    static Scanner input = new Scanner(System.in);
    static student stu =new student();
public static boolean handlelogin(){

    String email="",password="";
    while(true) {
        System.out.println("enter your email or 0 to exit");
        email = input.nextLine();
        if(email.equals("0"))return false;
        System.out.println("enter your password");
        password = input.nextLine();
        if(stu.login(email,password))break;
        else{
            System.out.println("wrong credentials");
            System.out.println("press any key to continue");
            input.nextLine();
        }

    }
    return true;
}

public static void handlemenu(){
    while(stu.user){
        System.out.println("Press \n0. to logout \n1. to view profile\n2. to update profile\n3. to view the offered courses\n4. to add course\n5. to delete Course\n6. to view your courses\n7. to view grades\n8. to view your cgpa\n9. to check graduation  ");
        String r="";

        r=input.nextLine();
        switch (r){
            case "0":
                stu.logout();
                return;
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
                String s=stu.gradcheck();
                System.out.println(s);
                System.out.println("press any key to continue");
                input.nextLine();
                break;
            }

            default:System.out.println("please follow the instructions");
                break;
        }
    }
}
}
