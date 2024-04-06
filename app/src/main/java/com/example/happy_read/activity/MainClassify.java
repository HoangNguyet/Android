package com.example.happy_read.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    database database;
    EditText edt;
    ListView lv;
    Button tieuthuyet, thieuNhi, hanhDong, phieuLuu, kinhDi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_classify);

        database = new database(this);
        lv = findViewById(R.id.listviewNew);
        tieuthuyet = findViewById(R.id.tieu_thuyet);
        thieuNhi = findViewById(R.id.thieu_nhi);
        hanhDong = findViewById(R.id.hanh_dong);
        phieuLuu = findViewById(R.id.phieu_lieu);
        kinhDi = findViewById(R.id.kinh_di);



        AnhXa();

        //Bat su kien click item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
            }
        });

        tieuthuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToTieuThuyet(v);
            }
        });
        thieuNhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToThieuNhi(v);
            }
        });
        hanhDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHanhDong(v);
            }
        });
        phieuLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPhieuLuu(v);
            }
        });
        kinhDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToKinhDi(v);
            }
        });

    }

    //Phuong thuc lay du lieu gan vao ListView

    private void AnhXa(){
        TruyenArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        lv = findViewById(R.id.listviewNew);
        Cursor cursor2 = database.getData2();
        while (cursor2.moveToNext()) {
            String id = cursor2.getColumnName(0);
            String tentruyen = cursor2.getString(1);
            String noidung = cursor2.getString(2);
            String anh = cursor2.getString(7);
            TruyenArrayList.add(new Story(id, tentruyen, noidung, anh,null));
            arrayList.add(new Story(id, tentruyen, noidung, anh,null));
            adapterTruyen = new StoryAdapter(getApplicationContext(),TruyenArrayList);
            lv.setAdapter(adapterTruyen);
        }
        cursor2.moveToFirst();
        cursor2.close();
    }

    public void goToTieuThuyet(View view){
        Intent intent = new Intent(MainClassify.this, MainTieuThuyet.class);
        startActivity(intent);
    }
    public void goToThieuNhi(View view){
        Intent intent = new Intent(MainClassify.this, MainThieuNhi.class);
        startActivity(intent);
    }
    public void goToHanhDong(View view){
        Intent intent = new Intent(MainClassify.this, MainHanhDong.class);
        startActivity(intent);
    }
    public void goToPhieuLuu(View view){
        Intent intent = new Intent(MainClassify.this, MainPhieuLuu.class);
        startActivity(intent);
    }
    public void goToKinhDi(View view){
        Intent intent = new Intent(MainClassify.this, MainKinhDi.class);
        startActivity(intent);
    }

}
