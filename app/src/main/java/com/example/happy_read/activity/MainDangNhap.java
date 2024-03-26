package com.example.happy_read.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.example.happy_read.R;
import com.example.happy_read.database.database;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class MainDangNhap extends AppCompatActivity {

    private EditText edUserName, edPassword;
    private Button btndangnhap, btndangki;
    private database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_nhap);

        // Khởi tạo các thành phần trong giao diện
        edUserName = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        btndangki = findViewById(R.id.btn_dangki);
        btndangnhap = findViewById(R.id.btn_dangnhap);
        database = new database(this);
        SQLiteDatabase db = null;

        //xử lý sự kiện khi người dùng nhấn nút "Đăng nhập"
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUserName.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                //Kiểm tra xem người dùng đã nhập đầy đủ thông tin chưa
                if (username.isEmpty() || password.isEmpty()) {
                    Intent intent = new Intent(MainDangNhap.this,UserProfileActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainDangNhap.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //xử lý sự kiện khi người dùng nhấn nút "Đăng ký"

    }
}