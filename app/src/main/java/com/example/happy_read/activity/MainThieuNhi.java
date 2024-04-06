package com.example.happy_read.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_read.R;
import com.example.happy_read.adapter.StoryAdapter;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Story;

import java.util.ArrayList;
import java.util.List;

public class MainThieuNhi extends AppCompatActivity {

    ListView lv;
    StoryAdapter adapter;
    ArrayList<Story> thieuNhiList;
    ArrayList<Story> arrayList;
    database db;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thieu_nhi);

        lv = findViewById(R.id.list_view);
        edt = findViewById(R.id.search);
        // Khởi tạo database
        db = new database(this);


        // Gọi phương thức để lấy dữ liệu và hiển thị lên RecyclerView
        AnhXa();
        //EditText search
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    public void onIcOutClicked(View view) {
        // Tạo Intent để chuyển đến trang Home (MainActivity)
        Intent intent = new Intent(this, MainClassify.class);
        startActivity(intent);
        // Kết thúc hoạt động hiện tại
        finish();
    }

    private void AnhXa() {
        thieuNhiList = new ArrayList<>();
        arrayList = new ArrayList<>();
        // Lấy dữ liệu từ database
        Cursor cursor = db.getDataByGenre("Thiếu nhi");
        while (cursor.moveToNext()) {
            // Get the column index for title and image
            int titleIndex = cursor.getColumnIndex(db.COLUMN_STORIES_TITLE);
            int imageIndex = cursor.getColumnIndex(db.COLUMN_STORIES_IMAGE);

            // Check if the column indexes are valid
            if (titleIndex != -1 && imageIndex != -1) {
                // Retrieve title and image using column indexes
                String title = cursor.getString(titleIndex);
                String image = cursor.getString(imageIndex);
                // Create a Story object and add it to the ArrayList
                thieuNhiList.add(new Story("0", title, "", image,null));
                arrayList.add(new Story("0", title, "", image,null));

            } else {
                Log.e("MainYourBook", "Invalid column index for title or image");
            }
        }
        cursor.close();

        // Khởi tạo adapter và đặt adapter cho ListView
        adapter = new StoryAdapter(getApplicationContext(), thieuNhiList);
        lv.setAdapter(adapter);
    }
    //    Search
    private void filter(String text){
        //xoa dl mang
        arrayList.clear();
        ArrayList<Story> filteredList = new ArrayList<>();
        for(Story item: thieuNhiList){
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                //them item vao filteredList
                filteredList.add(item);

                //them vao mang
                arrayList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }


}

