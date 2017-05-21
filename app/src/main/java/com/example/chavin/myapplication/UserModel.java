package com.example.chavin.myapplication;

/**
 * Created by Chavin on 5/5/2017.
 */

public class UserModel {
        String name;
        String email;
        String password;
        String dob;
        String gender;
        String alarm;
        String alarm1;
        String alarm2;
        String date;

    public void setAlarm1(String alarm1) {
        this.alarm1 = alarm1;
    }

    public void setAlarm2(String alarm2) {
        this.alarm2 = alarm2;
    }



    public String getAlarm1() {
        return alarm1;
    }

    public String getAlarm2() {
        return alarm2;
    }



    public String getRetEmail() {
        return retEmail;
    }

    String retEmail;
        String retName;

    public void setRetName(String retName) {
        this.retName = retName;
    }

    public String getRetName() {
        return retName;
    }



    public void setRetEmail(String retEmail) {
        this.retEmail = retEmail;
    }




    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAlarm(String alarm){
        this.alarm = alarm;
    }
    public String getAlarm(){
        return alarm;
    }

    public void setDate(String date){ this.date = date;}
    public String getDate(){ return date;}
    public UserModel(){

    }

}
