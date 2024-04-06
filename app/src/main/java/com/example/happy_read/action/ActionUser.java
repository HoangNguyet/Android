package com.example.happy_read.action;

import static com.example.happy_read.database.database.COLUMN_USERS_BIRTHDAY;
import static com.example.happy_read.database.database.COLUMN_USERS_FULLNAME;
import static com.example.happy_read.database.database.COLUMN_USERS_GENDER;
import static com.example.happy_read.database.database.COLUMN_USERS_IMAGE;
import static com.example.happy_read.database.database.COLUMN_USERS_NAME;
import static com.example.happy_read.database.database.TABLE_USERS;
import static com.example.happy_read.until.until.UpdateStatment;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.happy_read.database.database;
import com.example.happy_read.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ActionUser {
    protected String UpdateInDb(database db, User user){
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?,%s = ?, %s = ?  WHERE %s = ?", TABLE_USERS, COLUMN_USERS_FULLNAME, COLUMN_USERS_IMAGE, COLUMN_USERS_GENDER, COLUMN_USERS_BIRTHDAY, COLUMN_USERS_NAME);
        SQLiteStatement statement = db.getWritableDatabase().compileStatement(sql);
        UpdateStatment(user.GetFullName(),1,statement);
        UpdateStatment(user.GetImagePath(),2,statement);
        UpdateStatment(user.GetGender(),3,statement);
        UpdateStatment(user.GetBirthDay("yyyy-MM-dd"),4,statement);
        UpdateStatment(user.GetName(),5,statement);
        try {
            statement.execute();
            return "Cập nhật thông tin thành công";
        }
        catch (SQLException e){
            Log.d("sqleroor", Objects.requireNonNull(e.getMessage()));
            return "Cập nhật thông tin thất bại";
        }
        finally {
            db.close();
        }
    }
    protected  static User GetUserById(database db, String id){
        //userName is key
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_USERS,COLUMN_USERS_NAME);
        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{id})){
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String userName = cursor.getString(0);
                    String passWord = cursor.getString(1);
                    String email = cursor.getString(2);
                    String fullName = cursor.getString(3);
                    String role = cursor.getString(4);
                    String imagePath = cursor.getString(5);
                    String gender = cursor.getString(6);
                    Date birthDay = cursor.getString(7) != null? new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(7)):null;
                    //public User(String _userName, String _password, String _email, String _fullName, String _role, String _imagePath, String _gender, Date _birthDay)
                    return new User(userName,passWord,email,fullName,role,imagePath,gender,birthDay);
                }
            }
            else{
//                Log.d("C","B");
                return null;
            }
        }
        catch (Exception ex){
            Log.d("ERRR", Objects.requireNonNull(ex.getMessage()));
        }
        finally {
            db.close();
        }
        return null;
    }
}
