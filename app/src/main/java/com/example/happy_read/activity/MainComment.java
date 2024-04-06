package com.example.happy_read.activity;

import static com.example.happy_read.until.until.SwitchPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.adapter.RatingAdapter;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;
import com.example.happy_read.model.Story;
import com.example.happy_read.until.until;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainComment extends AppCompatActivity {
    TextView textViewStar,textViewHeart,textViewView,textTitle,textAuthor,textGenre,textCreateAt,textReview;
    ImageView imageViewDescription;
    ListView listViewComment;
    Story story;
    @Override
    protected void onCreate (Bundle comment) {
        super.onCreate(comment);
        setContentView(R.layout.activity_comment);
        database db = new database(MainComment.this);
        textViewStar = findViewById(R.id.textViewStar);
        textViewHeart = findViewById(R.id.textViewHeart);
        textViewView = findViewById(R.id.textViewView);
        textTitle = findViewById(R.id.textTitle);
        textAuthor = findViewById(R.id.textAuthor);
        textGenre = findViewById(R.id.textGenre);
        textCreateAt = findViewById(R.id.textCreateAt);
        textReview = findViewById(R.id.textReview);
        imageViewDescription = findViewById(R.id.imageDescription);
        listViewComment = findViewById(R.id.listViewComment);
        //test with book_id is 1
        story = Story.GetStoryById("1",db);
        LoadData(story);

//        LoadData(story);
    }
    public void LoadData(Story story){
        //Phuong thuc GetMediaStartAndCountHeart tra ve so luot danh gia sao trung binh va so luot tym
        String [] StartAndHeart = story.GetMediumStarAndCountHeartA();
        textViewStar.setText(StartAndHeart[0]);
        textViewHeart.setText(StartAndHeart[1]);
        textViewView.setText(story.getView());
        textTitle.setText(story.getTitle());
        textAuthor.setText("Tác giả: "+story.GetAuthor());
        textGenre.setText("Thể loại: "+story.getGenre());
        textCreateAt.setText("Ngày phát hành: "+story.getCreatedAt("dd/MM/yyyy"));
        textReview.setText("Review: " +story.getDescription());
        int imageResId = until.GetImageResId(story.getImagePathDes(),getApplicationContext());
        if(imageResId!=0) {
            Picasso.get().load(imageResId).into(imageViewDescription);
        }
        RatingAdapter ratingAdapter = new RatingAdapter(getApplicationContext(),story.GetRattings());
        listViewComment.setAdapter(ratingAdapter);
    }
    public void ReadBooks(View view){
        SwitchPage(this,ConTent.class,story.ExtractDataInBooks(),"data");
    }

}
