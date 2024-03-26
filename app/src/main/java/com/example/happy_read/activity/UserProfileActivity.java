package com.example.happy_read.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.example.happy_read.database.database;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.model.User;
import com.example.happy_read.until.UserDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
        _use = UserDatabase.GetUserById(myDatabase,_userName);
        Log.d("e",(_use == null)?"True":"false");


        imageAvatar = (ImageView) findViewById(R.id.imgAvatar);
        fullName = (TextView) findViewById(R.id.fullName);
        if(_use != null){
            Log.d("Mess","a");
            if(_use.GetImagePath()==null) {
                try {
                    Bitmap bitmap = getBitmap("avatar/img.png");
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
            Log.d("Mess1","a");
        }
    }
    public void EditProFile(View v){
        Intent intent = new Intent(UserProfileActivity.this, UserProfileEditActivity.class);
        startActivity(intent);
        finish();
    }
    public Bitmap getBitmap(String path) throws IOException {
        AssetManager manager = getAssets();
        InputStream stream = manager.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return bitmap;
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
