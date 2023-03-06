package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class admin_menu {
    static Scanner input = new Scanner(System.in);
    static admin x = new admin();

    public static boolean handlelogin() {
        while (true) {
            String username = "", password = "";
            System.out.println("enter the username or 0 to exit");
            username = input.nextLine();
            if (username.equals("0")) return false;
            System.out.println("enter the password");
            password = input.nextLine();
            boolean f = x.login(username, password);
            if (f) break;
            else System.out.println("wrong credentials");
        }
        return true;
    }

    public static void handlemenu() {
        while (true) {
            System.out.println("Press \n0. to logout \n1. to add a new batch\n2. to add a new course\n3. to add or update a curriculum\n4. to start sem\n5. to end sem\n6. to add course to course_catalog\n7. to view grades of all students\n8. to add users\n9. to generate student transcripts \n10. to view transcript\n11. to view courses\n12. to view users \n13. to open or close window for students \n14. to open or close window for instructor");
            String r = "";
            r = input.nextLine();
            switch (r) {
                case "0":
                    x.logout();
                    return;
                case "1": {
                    while (true) {
                        String batch_id = "", year = "", dep_id = "";
                        System.out.println("enter batch id");
                        batch_id = input.nextLine();
                        System.out.println("enter year");
                        year = input.nextLine();
                        System.out.println("enter department id");
                        dep_id = input.nextLine();
                        x.addbatch(batch_id, year, dep_id);
                        System.out.println("press 0 for exit and 1 to continue");
                        if (input.nextLine().equals("0")) {
                            break;
                        }
                    }

                    break;
                }
                case "2": {
                    while (true) {
                        String course_id = "", course_name = "", dep_id = "", l, t, p, cc;
                        System.out.println("Enter course id");
                        course_id = input.nextLine();
                        System.out.println("Enter course name");
                        course_name = input.nextLine();
                        System.out.println("Enter dep_id");
                        dep_id = input.nextLine();
                        System.out.println("Enter number of lectures per week");
                        l = input.nextLine();
                        System.out.println("Enter number of tutorials per week");
                        t = input.nextLine();
                        System.out.println("Enter course practicals per week");
                        p = input.nextLine();
                        System.out.println("Enter course credits");
                        cc = input.nextLine();
                        List<String> prereq = new ArrayList<String>();
                        while (true) {
                            String pre;
                            System.out.println("enter the course code of the prerequisite course of the course " + course_id + " or 0 to exit");
                            pre = input.nextLine();
                            if (pre.equals("0")) break;
                            prereq.add(pre);
                        }
                        x.addcourse(course_id, course_name, dep_id, l, t, p, cc, prereq);
                        System.out.println("press 0 for exit and 1 to continue");
                        if (input.nextLine().equals("0")) {
                            break;
                        }
                    }
                    break;

                }
                case "3": {
                    while (true) {
                        String course_id = "", course_type, batch_id;
                        System.out.println("enter the course id or enter 0 to quit");
                        course_id = input.nextLine();
                        if (course_id.equals("0")) {
                            break;
                        }
                        System.out.println("enter the batch_id ");
                        batch_id = input.nextLine();
                        System.out.println("enter the course type");
                        course_type = input.nextLine();
                        x.addcurriculum(course_id, course_type, batch_id);
                    }
                    break;

                }

                case "4": {
                    String academic_year, semester;
                    System.out.println("enter the academic year");
                    academic_year = input.nextLine();
                    System.out.println("enter the semester monsoon/winter");
                    semester = input.nextLine();
                    String resp = Semester_management.startsem(academic_year, semester);
                    System.out.println(resp);
                    if (!resp.equals("a sem is already running")) {
                        System.out.println("please add course to the current sem's course catalog");
                    }
                    System.out.println("press any key to continue");
                    input.nextLine();
                    break;

                }
                case "5":
                    Semester_management.endsem();
                    System.out.println("press any key to continue");
                    input.nextLine();
                    break;
                case "6":
                    while (true) {
                        String course_id;
                        System.out.println("enter course code or enter 0 to exit");
                        course_id = input.nextLine();
                        if (course_id.equals("0")) {
                            break;
                        }
                        x.updatecoursecatalog(course_id);
                    }
                case "7":
                    while (true) {
                        String student_id = "0";
                        System.out.println("enter student_id or 0 to exit");
                        student_id = input.nextLine();
                        if (student_id.equals("0")) break;
                        x.showGrades(student_id);
                        System.out.println("press any key to continue");
                        input.nextLine();
                    }
                    break;
                case "8":
                    int f = 1;
                    while (f == 1) {
                        System.out.println("press \n0 to exit\n1 to add student\n2 to add instructor");
                        String role = input.nextLine();
                        List<String> data = new ArrayList<String>();
                        switch (role) {
                            case "0":
                                f = 1;
                                break;
                            case "1": {
                                String student_name = "", batch_id = "", phone_number = "";
                                System.out.println("enter name of the student");
                                student_name = input.nextLine();
                                System.out.println("enter batch_id of the student");
                                batch_id = input.nextLine();
                                System.out.println("enter phone number of the student");
                                phone_number = input.nextLine();
                                data.add(student_name);
                                data.add(batch_id);
                                data.add(phone_number);
                                x.adduser("1", data);
                            }
                            break;
                            case "2": {
                                String instructor_name = "", dep_id = "", phone_number = "";
                                System.out.println("enter name of the instructor");
                                instructor_name = input.nextLine();
                                System.out.println("enter dep_id of the instructor");
                                dep_id = input.nextLine();
                                System.out.println("enter phone number of the instructor");
                                phone_number = input.nextLine();
                                data.add(instructor_name);
                                data.add(dep_id);
                                data.add(phone_number);
                                x.adduser("2", data);
                            }
                            break;
                            default:
                                System.out.println("enter valid role");
                        }
                    }
                    break;
                case "9": {
                    String student_id = "";
                    System.out.println("enter the student_id");
                    student_id = input.nextLine();
                    boolean ffff = x.generatetranscript(student_id);
                    ffff &= x.submittranscript(student_id);
                    if (ffff) System.out.println("generated successfully");
                    else System.out.println("somme error occured");

                    System.out.println("press any key to continue");
                    input.nextLine();
                }
                break;
                case "10":
                    String student_id = "";
                    System.out.println("enter student id");
                    student_id = input.nextLine();
                    x.viewtranscript(student_id);
                    break;
                case "11":
                    x.viewcourses();
                    System.out.println("press any key to continue");
                    input.nextLine();
                    break;
                case "12":
                    String rol = "0";
                    System.out.println("enter 1 for student and 2 for instructor");
                    rol = input.nextLine();
                    x.viewusers(rol);
                    System.out.println("press any key to continue");
                    input.nextLine();
                    break;
                case "13": {
                    String ff = "";
                    System.out.println("enter 1 to open and 2 to close ");
                    ff = input.nextLine();
                    if (ff.equals("1"))
                        Semester_management.openwindowforstudent();
                    else
                        Semester_management.closewindowforstudent();
                    System.out.println("press any key to continue");
                    input.nextLine();
                    break;
                }
                case "14": {
                    String ff = "";
                    System.out.println("enter 1 to open and 2 to close ");
                    ff = input.nextLine();
                    if (ff.equals("1"))
                        Semester_management.openwindowforinstructor();
                    else
                        Semester_management.closewindowforinstructor();
                    System.out.println("press any key to continue");
                    input.nextLine();
                    break;
                }

                default:
                    System.out.println("please follow the instructions");
                    break;
            }
        }

    }
}
