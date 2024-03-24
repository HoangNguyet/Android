package com.example.happy_read.model;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private int _id;
    private String _name;
    private String _password;
    private String _email;
    private String _fullName;
    private String _role;//Defalut is user and i think we dont have table is admin in here :)))
    private String _imagePath;
    private  String _gender;
    private Date _birthDay;


    //Getter
    public int GetId() {
        return _id;
    }

    public String GetName() {
        return _name;
    }

    public String GetPassword() {
        return _password;
    }

    public String GetEmail() {
        return _email;
    }

    public String GetFullName() {
        return _fullName;
    }

    public String GetRole() {
        return _role;
    }

    public String GetImagePath() {
        return _imagePath;
    }
    public String GetRender() {
        return _gender;
    }

    public Date GetBirthDay() {
        return _birthDay;
    }


    //Setter
    //I dont want to user change role email and id
    public void SetName(String name) throws Exception {
        if(name.isEmpty()){
            throw new Exception("Tên không được để trống");
        }
        _name = name;
    }

    public void SetPassword(String password) throws Exception {
        if(password.isEmpty()){
            throw  new Exception("Mật khẩu không thể để trống");
        }
        _password = password;
    }

    public void SetFullName(String fullName) throws Exception {
        if(fullName.isEmpty()){
            throw new Exception("Missing FullName");
        }
        _fullName = fullName;
    }

    public void SetImagePath(String imagePath) {
        _imagePath = imagePath;
    }

    public void SetGender(String gender) throws Exception {
        if(gender.isEmpty()||gender.toLowerCase() != "nam" || gender.toLowerCase()!= "nữ"){
            throw new Exception("Giới tính không hợp lệ");
        }
        _gender = gender;
    }

    public void SetBirhDay(Date birthDay) {
        _birthDay = birthDay;
    }
    public void SetEmail(String email) throws Exception{
        String regexEmail = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            throw new Exception("Email không hợp lệ");
        }
        _email = email;
    }
    public User(String _name, String _password,String _email, String _role) throws  Exception{
        this._role = _role;
        SetName(_name);
        SetPassword(_password);
        SetName(_email);
    }
}
