package com.example.authenticate_retrofit.model;

public class Student {
    private String name_en;
    private String sex;
    private String dob;

    @Override
    public String toString() {
        return "Student{" +
                "name_en='" + name_en + '\'' +
                ", sex='" + sex + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Student() {
    }

    public Student(String name_en, String sex, String dob) {
        this.name_en = name_en;
        this.sex = sex;
        this.dob = dob;
    }
}
