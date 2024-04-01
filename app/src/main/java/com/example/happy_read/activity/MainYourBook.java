package com.example.happy_read.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.adapter.StoryAdapter;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Story;

import java.util.ArrayList;

public class MainYourBook extends AppCompatActivity {
    ArrayList<Story> TruyenArrayList;
    StoryAdapter adapterTruyen;
    database database;
    ListView lv;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_your_book);

        database = new database(this);
        lv = findViewById(R.id.listviewNew);

        AnhXa();

    }


    // Thêm sự kiện click cho icon
    public void onIcOutClicked(View view) {
        // Tạo Intent để chuyển đến trang Home (MainActivity)
        Intent intent = new Intent(this, MainHome.class);
        startActivity(intent);
        // Kết thúc hoạt động hiện tại
        finish();
    }


    private void AnhXa(){
        TruyenArrayList = new ArrayList<>();
        // Lấy dữ liệu từ database
        Cursor cursor = database.getFavoriteStoriesWithImage();
        while (cursor.moveToNext()) {
            // Get the column index for title and image
            int titleIndex = cursor.getColumnIndex(database.COLUMN_STORIES_TITLE);
            int imageIndex = cursor.getColumnIndex(database.COLUMN_STORIES_IMAGE);

            // Check if the column indexes are valid
            if (titleIndex != -1 && imageIndex != -1) {
                // Retrieve title and image using column indexes
                String title = cursor.getString(titleIndex);
                String image = cursor.getString(imageIndex);
                // Create a Story object and add it to the ArrayList
                TruyenArrayList.add(new Story(0, title, "", image));
            } else {
                Log.e("MainYourBook", "Invalid column index for title or image");
            }
        }
        cursor.close();

        // Khởi tạo adapter và đặt adapter cho ListView
        adapterTruyen = new StoryAdapter(getApplicationContext(), TruyenArrayList);
        lv.setAdapter(adapterTruyen);
        }
    }


