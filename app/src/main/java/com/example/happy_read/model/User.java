package com.example.happy_read.model;

import com.example.happy_read.action.ActionUser;
import com.example.happy_read.database.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends ActionUser {
    //_name is id
    private String _userName;
    private String _password;
    private String _email;
    private String _fullName = null;
    private String _role;//Defalut is user and i think we dont have table is admin in here :)))
    private String _imagePath = null;
    private  String _gender = null;
    private Date _birthDay = null;


    //Getter

    public String GetName() {
        return _userName;
    }

    public String GetPassword() {
        return _password;
    }

    public String GetEmail() {
        return _email;
    }

    public String GetFullName() {
        if(_fullName == null){
            return _userName;
        }
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

    public String GetGender() {
        if(_gender == null){
            return null;
        }
        return _gender.trim().toLowerCase();
    }

    public String GetBirthDay(String format) {
        if(_birthDay == null){
            return  null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(_birthDay);
    }


    //Setter
    //I dont want to user change role email and id
    private void SetName(String name) throws Exception {
        if(name.isEmpty()){
            throw new Exception("Tên không được để trống");
        }
        _userName = name;
    }

    public void SetPassword(String password) throws Exception {
        if(password.isEmpty()){
            throw  new Exception("Mật khẩu không thể để trống");
        }
        _password = password;
    }

    public void SetFullName(String fullName) throws Exception {
        if(fullName.isEmpty()){
            throw new Exception("Tên không được để trống");
        }
        _fullName = fullName;
    }

    public void SetImagePath(String imagePath) {
        _imagePath = imagePath;
    }
    public void SetGender(String gender) {
        _gender = gender;
    }

    public void SetBirhDay(Date birthDay) {
        if(birthDay == null){
            return;
        }
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
    private User(){}
    public User(String _userName, String _password,String _email, String _role) throws  Exception{
        this._role = _role;
        SetName(_userName);
        SetPassword(_password);
        SetEmail(_email);
    }

    public User(String _userName, String _password, String _email, String _fullName, String _role, String _imagePath, String _gender, Date _birthDay) throws Exception {
        SetName(_userName);
        SetPassword(_password);
        SetEmail(_email);
        SetFullName(_fullName);
        this._role = _role;
        SetImagePath(_imagePath);
        SetGender(_gender);
        SetBirhDay(_birthDay);
    }
    public void UpdateInformation(String _fullName, String _imagePath, String _gender, Date _birthDay) throws Exception{
        SetFullName(_fullName);
        SetImagePath(_imagePath);
        SetGender(_gender);
        SetBirhDay(_birthDay);
    }
    //Check user have male
    public boolean isMale(){
        return GetGender().equals("male");
    }
    public String UpdateUserInDb(database db){
        return super.UpdateInDb(db,this);
    }
    public static User GetUserByIdA(database db, String user_name){
        return new User().GetUserById(db,user_name);
    }
}
