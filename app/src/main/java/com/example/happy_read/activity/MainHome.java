package com.example.happy_read.activity;

import static com.example.happy_read.until.Log._USER_NAME;

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


    Button btnClassify, btnYourbook, btnHome, btnProfile;
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
        btnProfile = findViewById(R.id.menu_toi);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            //Neu nhu chua dang nhap thi phai dang nhap con da dang nhap thi hien thi giao dien nguoi dung
            @Override
            public void onClick(View v) {
                if(_USER_NAME == null){
                    Intent intent = new Intent(MainHome.this,MainDangNhap.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainHome.this,UserProfileActivity.class);
                    startActivity(intent);
                }
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

        //Hien thi thong tin khi nguoi dung đăng truyện thành công
        Toast.makeText(this, getIntent().getStringExtra("InsertStory"), Toast.LENGTH_SHORT).show();

    }

    private void AnhXa() {
        database = new database(this);
        viewFlipper = findViewById(R.id.viewflipper);
        lv = findViewById(R.id.listviewNew);
        lv.setPadding(0,0,0,100);
        edt = findViewById(R.id.search);
        TruyenArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        Cursor cursor1 = database.getData1();
        while (cursor1.moveToNext()) {
            String id = cursor1.getString(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(7);
            TruyenArrayList.add(new Story(id, tentruyen, noidung, anh,null));
            arrayList.add(new Story(id, tentruyen, noidung, anh,null));
        }
        adapterTruyen = new StoryAdapter(getApplicationContext(),TruyenArrayList,this);
        lv.setAdapter(adapterTruyen);
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
        mangquangcao.add("https://i.pinimg.com/originals/36/29/73/36297393c68d558e6138b823256691c3.gif");
        mangquangcao.add("https://cdn.popsww.com/blog/sites/2/2021/11/top-truyen-dam-my.jpg");
        mangquangcao.add("https://www.vietnamfineart.com.vn/wp-content/uploads/2023/03/30-truyen-tranh-ngon-tinh-tong-tai-hay-nhat-moi-thoi-dai-7.jpg");
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdCb_u6pA7nCcvnsFTELAHLE_Hy8EeRmb67sXR5-pMWA&s");
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
