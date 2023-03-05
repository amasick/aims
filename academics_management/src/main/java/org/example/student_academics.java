package org.example;

public interface student_academics extends user {
    public boolean addCourse(String s);
    public boolean deleteCourse(String s);
    public boolean updateprofile(String a,String b,String c);
    public boolean viewprofile();
    public String showGrades();
}
