package com.example.happy_read.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.example.happy_read.R;
import com.example.happy_read.database.database;

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

                //Thực hiện truy vấn vào cơ sở dữ liệu để kiểm tra đăng nhập
                SQLiteDatabase db = database.getReadableDatabase(); // mo ket noi doc den co so du lieu, thao tac de lay mot doi tuong sqlite de thuc hien cac truy van
                String selection = "select * from "  + database.TABLE_USERS + " where " + database.COLUMN_USERS_NAME + " = ? and "
                        + database.COLUMN_USERS_PASSWORD + " =?";
                String[] selectionArgs = {username, password};
                Cursor cursor = db.rawQuery(selection, selectionArgs);
                if (cursor != null && cursor.moveToFirst()) {
                    //Đăng nhập thành công
                    Toast.makeText(MainDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    // Chuyển sang trang quên mật khẩu
                    Intent intent = new Intent(MainDangNhap.this, ChangePassWordActivity.class);
                    intent.putExtra("USERNAME", username); //Truyền tên người dùng qua intent
                    startActivity(intent);
                    finish(); // Kết thúc Activity hiện tại sau khi chuyển sang trang quên mật khẩu
                } else {
                    //Đăng nhập thất bại
                    Toast.makeText(MainDangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
                //Đóng cursor sau khi sử dụng xong
                if (cursor != null) {
                    cursor.close();
                }
            }
        });


        //xử lý sự kiện khi người dùng nhấn nút "Đăng ký"
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chuyen sang form dang ky
                Intent intent = new Intent(MainDangNhap.this, MainDangKy.class);
                startActivity(intent);
            }
        });

    }
}