package com.example.happy_read.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;

import java.util.List;

public class MainComment extends AppCompatActivity {
    TextView textViewStar;
    TextView textViewHeart;
    @Override
    protected void onCreate (Bundle comment) {
        super.onCreate(comment);
        setContentView(R.layout.activity_comment);
        database db = new database(MainComment.this);
        //Test with books id is 1
//        List<Rating> ratings = Rating.getRatting("2",db);
//        textViewStar = (TextView) findViewById(R.id.textViewStar);
//        textViewHeart = (TextView) findViewById(R.id.textViewHeart);
//        LoadData(ratings);
    }
    public void LoadData(List<Rating> ratings){
        //Phuong thuc GetMediaStartAndCountHeart tra ve so luot danh gia sao trung binh va so luot tym
//        String [] StartAndHeart = Rating.GetMediumStarAndCountHeart(ratings);
//        textViewStar.setText(StartAndHeart[0]);
//        textViewHeart.setText(StartAndHeart[1]);
    }

}
