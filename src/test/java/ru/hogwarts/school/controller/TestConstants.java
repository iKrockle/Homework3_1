package ru.hogwarts.school.controller;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;
import java.util.List;

public class TestConstants {
    public static final long facultyIdConst = 1L;
    public static final String facultyNameConst = "Faculty name";
    public static final String facultyNewNameConst = "Faculty new name";
    public static final String facultyColorConst = "Faculty color";
    public static final Faculty facultyConst = new Faculty(
            facultyIdConst,
            facultyNameConst,
            facultyColorConst
    );

    public static final long studentIdConst = 1L;
    public static final String studentNameConst = "Student name";
    public static final String studentNewNameConst = "Student new name";
    public static final Integer studentAgeConst = 13;
    public static final Student studentConst = new Student(
            studentIdConst,
            studentNameConst,
            studentAgeConst,
            facultyConst
    );
    public static final Student studentConst2 = new Student(
            studentIdConst,
            studentNameConst,
            studentAgeConst+1,
            facultyConst
    );
    public static List<Faculty> facultyListConst = Collections.singletonList(facultyConst);
    public static List<Student> studentListConst = Collections.singletonList(studentConst);
}
