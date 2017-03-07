package com.health.vaccinefinder.DataBase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by smsgh on 07/03/2017.
 */


@Table(name="Users")
public class Users {


    @Column(name = "fullname")
    private String fullname;

    @Column(name = "username")
    private String username;


    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    private String dob;


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
