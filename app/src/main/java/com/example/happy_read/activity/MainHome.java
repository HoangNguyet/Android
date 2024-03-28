package com.example.happy_read.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.happy_read.R;
import com.example.happy_read.adapter.StoryAdapter;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Story;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class MainHome extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;


    ArrayList<Story> TruyenArrayList;
    ArrayList<Story> arrayList;
    StoryAdapter adapterTruyen;
    database database;
    EditText edt;
    ListView lv;

    Button btnClassify, btnYourbook, btnHome, btnProfile, btnDeXuat, btnNoiBat, btnMoiNhat;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        database = new database(this);
        lv = findViewById(R.id.listviewNew);
        edt = findViewById(R.id.search);



        //Anh xa va gan su kien cho nut "phan loai"
        btnClassify = findViewById(R.id.menu_phan_loai);
        btnClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToClassify(v);
            }
        });

        //Anh xa va gan su kien cho nut "phan loai"
        btnYourbook = findViewById(R.id.menu_tu_sach);
        btnYourbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBookYour(v);
            }
        });

        btnHome = findViewById(R.id.menu_trang_chu);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome(v);
            }
        });

        btnProfile = findViewById(R.id.menu_toi);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile(v);
            }
        });



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



    //Search
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
        Cursor cursor1 = database.getData1();
        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(6);
            TruyenArrayList.add(new Story(id, tentruyen, noidung, anh));
            arrayList.add(new Story(id, tentruyen, noidung, anh));
            adapterTruyen = new StoryAdapter(getApplicationContext(),TruyenArrayList);
            lv.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();
    }


    //Chuyển trang

    public void goToClassify(View view){
        Intent intent = new Intent(MainHome.this, MainClassify.class);
        startActivity(intent);
    }

    public void goToBookYour(View view){
        Intent intent = new Intent(MainHome.this, MainYourBook.class);
        startActivity(intent);
    }

    public void goToHome(View view){
        Intent intent = new Intent(MainHome.this, MainHome.class);
        startActivity(intent);
    }

    public void goToProfile(View view){
        Intent intent = new Intent(MainHome.this, MainProfile.class);
        startActivity(intent);
    }


}
