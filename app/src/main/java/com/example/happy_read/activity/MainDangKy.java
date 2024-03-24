package com.example.happy_read.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.RadioGroup;

import com.example.happy_read.R;
import com.example.happy_read.database.database;

public class MainDangKy extends AppCompatActivity {

    private EditText edusername, edpassword, edemail, edfullname, edimage, edbirthday;
    private  RadioGroup radiogender;
    private Button btndangky, btnback;
    private database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_ky);

        //Khởi tạo các thành phần trong giao diện
        edusername = findViewById(R.id.ed_username);
        edpassword = findViewById(R.id.ed_password);
        edemail = findViewById(R.id.ed_password);
        edfullname = findViewById(R.id.ed_fullname);
        edimage = findViewById(R.id.ed_image);
        edbirthday = findViewById(R.id.ed_birthday);
        radiogender = findViewById(R.id.radioGroupGender);
        btndangky = findViewById(R.id.btndangki);
        btnback = findViewById(R.id.btnback);
        database = new database(this);

        //Xử lý sự kiện khi người dùng bấm nút đăng ký
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy thông tin từ các trường edittext
                String username = edusername.getText().toString().trim();
                String password = edpassword.getText().toString().trim();
                String email = edemail.getText().toString().trim();
                String fullname = edfullname.getText().toString().trim();
                String image = edimage.getText().toString().trim();
                String birthday = edbirthday.getText().toString().trim();
                String gender = "";//lấy giá trị radio nếu có
                int selectedId = radiogender.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    gender = selectedRadioButton.getText().toString();
                }

                //Kiểm tra xem người dùng nhập đầy đủ thông tin chưa
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || fullname.isEmpty() || gender.isEmpty() || birthday.isEmpty()) {
                    Toast.makeText(MainDangKy.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm người dùng vào cơ dữ liệu
                addUserToDatabase(username, password, email, fullname, image, gender, birthday);
            }
        });

        //Xử lý xự kiện khi người dùng bấm nút trở lại
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi người dùng click vào trở lại sẽ trở về trang đăng nhập
                finish();
            }
        });
    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            finish();
        }

        // Phương thức thêm người dùng vào cơ sở dữ liệu
        private void addUserToDatabase(String username, String password, String email, String fullname, String image, String gender, String birthday) {
            // Mở kết nối đến cơ sở dữ liệu
            SQLiteDatabase db = database.getWritableDatabase();

            // Tạo đối tượng ContentValues để chứa các cặp key-value
            ContentValues values = new ContentValues();
            values.put(database.COLUMN_USERS_NAME, username);
            values.put(database.COLUMN_USERS_PASSWORD, password);
            values.put(database.COLUMN_USERS_EMAIL, email);
            values.put(database.COLUMN_USERS_FULLNAME, fullname);
            values.put(database.COLUMN_USERS_ROLE, "user");
            values.put(database.COLUMN_USERS_IMAGE, image);
            values.put(database.COLUMN_USERS_GENDER, gender);
            values.put(database.COLUMN_USERS_BIRTHDAY, birthday);

            // Thực hiện insert dữ liệu vào bảng USERS
            long newRowId = db.insert(database.TABLE_USERS, null, values);

            // Kiểm tra kết quả insert
            if (newRowId != -1) {
                // Insert thành công
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Insert thất bại
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }

            // Đóng kết nối đến cơ sở dữ liệu
            db.close();
        }
}