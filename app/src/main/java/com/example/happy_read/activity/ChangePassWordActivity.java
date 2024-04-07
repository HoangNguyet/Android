package com.example.happy_read.activity;

import static com.example.happy_read.until.Log._USER_NAME;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.database.database;

public class ChangePassWordActivity extends AppCompatActivity {
    private Button btnSave, btnBack;
    private EditText edmkcu, edmk1, edmk2;
    private database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thaydoimatkhau);

        //Ánh xạ các thành phần trong giao diện
        edmkcu = findViewById(R.id.mk_cu);
        edmk1 = findViewById(R.id.mk_moi1);
        edmk2 = findViewById(R.id.mk_moi2);
        btnSave = findViewById(R.id.button);
        btnBack = findViewById(R.id.button2);
        database = new database(this);

        //Xử lý khi người dùng bấm nút lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkcu = edmkcu.getText().toString().trim();
                String mk1 = edmk1.getText().toString().trim();
                String mk2 = edmk2.getText().toString().trim();
                if(!mk1.equals(mk2)){
                    Toast.makeText(ChangePassWordActivity.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                }
                //Thực hiện cập nhật mật khẩu mới vào cơ sở dữ liệu
                else{
                    SQLiteDatabase db = database.getWritableDatabase();
                    String query = "UPDATE " + database.TABLE_USERS +
                            " SET " + database.COLUMN_USERS_PASSWORD + " = '" + mk1 +
                            "' WHERE " + database.COLUMN_USERS_NAME + " = '" + _USER_NAME + "' AND " +
                            database.COLUMN_USERS_PASSWORD + " = '" + mkcu + "'";
                    db.execSQL(query);
                    db.close();
                    Toast.makeText(ChangePassWordActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Xử lý sự kiện khi người dùng nhấn nút trở lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Quay lại activity trước đó
                Intent loginIntent = new Intent(ChangePassWordActivity.this, UserProfileActivity.class);
                startActivity(loginIntent);
                finish(); // kết thúc activity hiện tại
            }
        });
    }
}
