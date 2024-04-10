package com.example.happy_read.activity;

import static com.example.happy_read.until.until.SwitchPage;

import com.example.happy_read.database.database;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.happy_read.R;
import com.example.happy_read.model.User;
import com.example.happy_read.until.until;
import com.squareup.picasso.Picasso;
import static com.example.happy_read.until.Log._USER_NAME;

public class UserProfileActivity extends AppCompatActivity {
//    Hiển thị thông tin người dùng và truyện mà họ đã đọc và yêu thích

    User _use;
    ImageView imageAvatar;
    TextView fullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database myDatabase = new database(UserProfileActivity.this);;
        _use = User.GetUserByIdA(myDatabase,_USER_NAME);
        imageAvatar = (ImageView) findViewById(R.id.imgAvatar);
        fullName = (TextView) findViewById(R.id.fullName);
        if(_use != null){
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
        Intent intent = new Intent(this,MainYourBook.class);
        startActivity(intent);
    }
    public void ChangeYourPassword(View v){
        Intent intent = new Intent(this, ChangePassWordActivity.class);
        startActivity(intent);
        //Open activity Change Your password
    }
    public void WriteSomething(View v){
        SwitchPage(this,AddContent.class,null,null);
    }
    public void Logout(View v){
        _USER_NAME = null;
        Intent intent = new Intent(this,MainHome.class);
        startActivity(intent);
    }
}
