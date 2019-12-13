package com.example.authenticate_retrofit.model;

public class Student {
    private String name_en;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name_en='" + name_en + '\'' +
                '}';
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public Student(String name_en) {
        this.name_en = name_en;
    }
}
