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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.happy_read.R;
import com.example.happy_read.adapter.StoryAdapter;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Story;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class MainHome extends AppCompatActivity {
    ViewFlipper viewFlipper;

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
        AnhXa();
        ActionViewFlipper();

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

    }

    private void AnhXa() {
        database = new database(this);
        viewFlipper = findViewById(R.id.viewflipper);
        lv = findViewById(R.id.listviewNew);
        edt = findViewById(R.id.search);
        TruyenArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
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


//    Search
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

    //Quang cao
    private void ActionViewFlipper(){
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://i.pinimg.com/736x/69/34/85/6934858d4b97a0b31daf13e541c7487c.jpg");
        mangquangcao.add("https://khasasco.com.vn/wp-content/uploads/2022/05/hinh-chibi-cute-de-ve-21.jpg");
        mangquangcao.add("https://img5.thuthuatphanmem.vn/uploads/2021/12/28/hinh-chibi-nu-don-gian-de-ve_091935661.jpg");
        mangquangcao.add("https://haycafe.vn/wp-content/uploads/2022/03/Hinh-ve-chibi-anime.jpg");
        for(int i = 0; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
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

}
