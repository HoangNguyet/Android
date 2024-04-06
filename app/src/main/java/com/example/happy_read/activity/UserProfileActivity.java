package com.example.happy_read.activity;

import static com.example.happy_read.until.until.SwitchPage;

import com.example.happy_read.database.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.model.User;
import com.example.happy_read.until.until;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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
            int resImageId = until.GetImageResId(_use.GetImagePath(),this);
            if(_use.IsImagePathNull()){
                Picasso.get().load(until.GetImageResId(_use.GetImagePath(),this)).into(imageAvatar);
            }
            else{
                Bitmap imageBitMap = BitmapFactory.decodeFile(_use.GetImagePath());
                imageAvatar.setImageBitmap(imageBitMap);
            }
            fullName.setText(_use.GetFullName());
        }
    }
    public void EditProFile(View v){
        SwitchPage(this,UserProfileEditActivity.class,null,null);
    }
    public void FavoriteBooks(View v){
        //Open Your FavoriteBook
    }
    public void ChangeYourPassword(View v){
        //Open activity Change Your password
    }
    public void WriteSomething(View v){
        SwitchPage(this,AddContent.class,null,null);
    }
    public void Logout(View v){
        //Logout
    }
}
