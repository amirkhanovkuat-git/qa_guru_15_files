package one.kz.model;

import java.util.List;

public class Student {

    public String name;
    public int age;
    public boolean isStudent;
    public String[] hobbies;
    public Passport passport;

    public static class Passport {
        public int number;
        public String issueDate;
    }
}
