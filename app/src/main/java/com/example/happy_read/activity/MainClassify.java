package com.example.happy_read.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.adapter.StoryAdapter;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Story;

import java.util.ArrayList;

public class MainClassify extends AppCompatActivity {
    ArrayList<Story> TruyenArrayList;
    ArrayList<Story> arrayList;
    StoryAdapter adapterTruyen;
    com.example.happy_read.database.database database;
    EditText edt;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_classify);

        database = new database(this);
        lv = findViewById(R.id.listviewNew);


        AnhXa();

        //Bat su kien click item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainHome.this, MainContent.class);
//                String tent = arrayList.get(position).getTitle();
//                String noidungt = arrayList.get(position).getContent();
//                intent.putExtra("tentruyen", tent);
//                intent.putExtra("noidung", noidungt);
//                startActivity(intent);
            }
        });
    }
    private void filter(String text){
        //xoa dl mang
        arrayList.clear();
        ArrayList<Story> filteredList = new ArrayList<>();
        for(Story item: TruyenArrayList){
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                //them item vao filteredList
                filteredList.add(item);

                //them vao mang
                arrayList.add(item);
            }
        }
        adapterTruyen.filterList(filteredList);
    }

    //Phuong thuc lay du lieu gan vao ListView

    private void AnhXa(){
        TruyenArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        lv = findViewById(R.id.listviewNew);
        Cursor cursor2 = database.getData2();
        while (cursor2.moveToNext()) {
            int id = cursor2.getInt(0);
            String tentruyen = cursor2.getString(1);
            String noidung = cursor2.getString(2);
            String anh = cursor2.getString(6);
            TruyenArrayList.add(new Story(id, tentruyen, noidung, anh));
            arrayList.add(new Story(id, tentruyen, noidung, anh));
            adapterTruyen = new StoryAdapter(getApplicationContext(),TruyenArrayList);
            lv.setAdapter(adapterTruyen);
        }
        cursor2.moveToFirst();
        cursor2.close();
    }
}
