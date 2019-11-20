package com.multimodal.ihm;

public class DataInList {

    private String photo;
    private String firstName;
    private String lastName;
    private String number;

    public DataInList(String photo, String firstName, String lastName, String number) {
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
    }

    public String getPhoto() {
        return photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }
}