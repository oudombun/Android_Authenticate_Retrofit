package com.example.authenticate_retrofit.model;

public class Profile {
    String name;
    String photo;

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Profile(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }
}
