package com.uady.proyectofinal;

/**
 * Created by jorge on 9/05/17.
 */
public class RegistrationData {
    private static RegistrationData ourInstance = new RegistrationData();

    public static RegistrationData getInstance() {
        return ourInstance;
    }

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String photo;
    private String birthday;

    private RegistrationData() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
