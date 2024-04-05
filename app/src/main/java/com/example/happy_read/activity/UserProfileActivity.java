package com.example.happy_read.activity;

import static com.example.happy_read.until.until.SwitchPage;
import static com.example.happy_read.until.until.getBitmap;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.happy_read.action.ActionUser;
import com.example.happy_read.database.database;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.model.User;

import java.io.IOException;
import java.io.InputStream;

public class UserProfileActivity extends AppCompatActivity {
//    Hiển thị thông tin người dùng và truyện mà họ đã đọc và yêu thích

    //Test with dynamic data
    //test
    String _userName = "ninh";
    User _use;
    ImageView imageAvatar;
    TextView fullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database myDatabase = new database(UserProfileActivity.this);
        _use = User.GetUserByIdA(myDatabase,_userName);
        imageAvatar = (ImageView) findViewById(R.id.imgAvatar);
        fullName = (TextView) findViewById(R.id.fullName);
        if(_use != null){
            Log.d("Mess","a");
            if(_use.GetImagePath()==null) {
                try {
                    Bitmap bitmap = getBitmap(this,"avatar/img.png");
                    imageAvatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                //Gan anh tu local vao imagview
                Bitmap imageBitMap = BitmapFactory.decodeFile(_use.GetImagePath());
                imageAvatar.setImageBitmap(imageBitMap);

            }
            fullName.setText(_use.GetFullName());
        }
        else{
        }
    }
    public void EditProFile(View v){
        SwitchPage(this, UserProfileEditActivity.class);
    }
    public void FavoriteBooks(View v){
        //Open Your FavoriteBook
    }
    public void ChangeYourPassword(View v){
        //Open activity Change Your password
    }
    public void WriteSomething(View v){
        //Open activity Write Books
    }
    public void Logout(View v){
        //Logout
    }
}
