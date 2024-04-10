package com.example.happy_read.activity;

import static com.example.happy_read.database.database.COLUMN_STORIES_ID;
import static com.example.happy_read.database.database.COLUMN_STORIES_IMAGE;
import static com.example.happy_read.database.database.COLUMN_STORIES_TITLE;
import static com.example.happy_read.until.Log._USER_NAME;

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
import java.util.List;

public class MainYourBook extends AppCompatActivity {
    ArrayList<Story> TruyenArrayList, listBookYourWrite;
    StoryAdapter adapterTruyen,adapterBookYourWrite;
    database database;
    ListView lv,booksYourWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_your_book);
        booksYourWrite = findViewById(R.id.listViewBookYourWrite);
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
        // Lấy dữ liệu từ database
        Cursor cursor = database.getFavoriteStoriesWithImage(_USER_NAME);
        TruyenArrayList = getStory(cursor);
        cursor.close();
        cursor = database.getBookYourWrite(_USER_NAME);
        listBookYourWrite = getStory(cursor);
        cursor.close();
        // Khởi tạo adapter và đặt adapter cho ListView
        adapterTruyen = new StoryAdapter(getApplicationContext(), TruyenArrayList,this);
        adapterBookYourWrite = new StoryAdapter(getApplicationContext(), listBookYourWrite,this);
        lv.setAdapter(adapterTruyen);
        booksYourWrite.setAdapter(adapterBookYourWrite);
    }
    public static ArrayList<Story> getStory(Cursor cursor){
        ArrayList<Story> TruyenArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            // Get the column index for title and image
            int idIndex = cursor.getColumnIndex(COLUMN_STORIES_ID);
            int titleIndex = cursor.getColumnIndex(COLUMN_STORIES_TITLE);
            int imageIndex = cursor.getColumnIndex(COLUMN_STORIES_IMAGE);

            // Check if the column indexes are valid
            if (titleIndex != -1 && imageIndex != -1 && idIndex !=-1) {
                // Retrieve title and image using column indexes
                String id = cursor.getString(idIndex);
                String title = cursor.getString(titleIndex);
                String image = cursor.getString(imageIndex);
                // Create a Story object and add it to the ArrayList
                TruyenArrayList.add(new Story(id, title, "", image,null));
            } else {
                Log.e("MainYourBook", "Invalid column index for title or image");
            }
        }
        return TruyenArrayList;
    }
}



