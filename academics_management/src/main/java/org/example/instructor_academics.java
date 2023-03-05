package org.example;

public interface instructor_academics extends user{
    public boolean addCourse(String s,String t);
    public boolean deleteCourse(String s);
    public boolean updateprofile(String a,String b,String c);
    public String viewprofile();
    public boolean showGrades();
}
