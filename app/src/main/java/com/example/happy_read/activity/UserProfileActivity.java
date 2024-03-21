package com.example.happy_read.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;

public class UserProfileActivity extends AppCompatActivity {
//    Hiển thị thông tin người dùng và truyện mà họ đã đọc và yêu thích


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void EditProFile(View v){
        Intent intent = new Intent(UserProfileActivity.this, UserProfileEditActivity.class);
        startActivity(intent);
    }
}
