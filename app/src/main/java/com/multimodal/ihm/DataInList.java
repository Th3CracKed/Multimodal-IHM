package com.multimodal.ihm;

import android.media.Image;

public class DataInList {

    String photo;
    String firstName;
    String lastName;
    String number;

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