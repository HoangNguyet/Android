package com.example.happy_read.activity.MainComment;

import static com.example.happy_read.activity.MainComment.LoadDataComment.LoadDataDynamic;
import static com.example.happy_read.activity.MainComment.LoadDataComment.LoadStaticData;
import static com.example.happy_read.until.Log._USER_NAME;
import static com.example.happy_read.until.until.SwitchPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.activity.ConTent;
import com.example.happy_read.activity.MainDangNhap;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;
import com.example.happy_read.model.Story;
import com.example.happy_read.model.User;

public class MainComment extends AppCompatActivity {
    TextView textViewStar,textViewHeart,textViewView,textTitle,textAuthor,textGenre,textCreateAt,textReview;
    ImageView imageViewDescription;
    EditText writeComment;
    ListView listViewComment;
    ImageButton imageButtonHeart,sendComment;
    Story story;
    Rating myRating;
    AlertDialog dialog,diaglogComment;
    String story_id;
    float rateValue;
    User user;
    RatingBar ratebar;
    database db = new database(this);
    @Override
    protected void onCreate (Bundle comment) {
        super.onCreate(comment);
        setContentView(R.layout.activity_comment);
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
        imageButtonHeart = findViewById(R.id.imageButtonHeart);
        story_id = getIntent().getStringExtra("storyId");
        story = Story.GetStoryById(story_id,db);
        if(_USER_NAME != null) {
            user = new User(_USER_NAME);
            myRating = user.GetRatingByUserNameAndBook(db, story);
        }

        AlertDialog.Builder commenBuild = new AlertDialog.Builder(this);
        View commentView = getLayoutInflater().inflate(R.layout.activity_box_comment,null);
        commenBuild.setView(commentView);
        sendComment = commentView.findViewById(R.id.sendComment);
        writeComment = commentView.findViewById(R.id.writeComment);
        diaglogComment = commenBuild.create();
        //set su kien cho viec comment
        //send comment have bug
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coment = writeComment.getText().toString();
                if(coment.isEmpty()){}
                else{
                    if(myRating == null){
                        myRating = new Rating(user,story_id);
                        myRating.SetComment(coment);
                        user.InsertRating(db,myRating);
                        myRating = user.GetRatingByUserNameAndBook(db,story);
                    }
                    else{
                        myRating.SetComment(coment);
                        user.UpdateRating(db,myRating);
                    }
                    LoadDataDynamic(myRating,imageButtonHeart,listViewComment,textViewStar,textViewHeart,textViewView,ratebar,writeComment,story,db,MainComment.this);
                }
                diaglogComment.dismiss();
            }
        });

        AlertDialog.Builder mBuild = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_rating,null);
        ratebar = (RatingBar)mView.findViewById(R.id.ratingBar);
        //Rating
        ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = rating;
            }
        });

        Button btnSubmit=(Button)mView.findViewById(R.id.btnSubRating);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bug here :))
                if(myRating == null){
                    myRating = new Rating(user,story_id);
                    myRating.SetRating(rateValue);
                    user.InsertRating(db,myRating);
                    myRating = user.GetRatingByUserNameAndBook(db,story);
                }
                else{
                    myRating.SetRating(rateValue);
                    user.UpdateRating(db,myRating);
                }
                LoadDataDynamic(myRating,imageButtonHeart,listViewComment,textViewStar,textViewHeart,textViewView,ratebar,writeComment,story,db,MainComment.this);
                dialog.dismiss();
            }
        });
        mBuild.setView(mView);
        dialog=mBuild.create();
        LoadStaticData(textTitle,textAuthor,textGenre,textCreateAt,textReview,imageViewDescription,story,this);
        LoadDataDynamic(myRating,imageButtonHeart,listViewComment,textViewStar,textViewHeart,textViewView,ratebar,writeComment,story,db,this);
        //End Rating
    }
    public void ReadBooks(View view){
        SwitchPage(this, ConTent.class,story.ExtractDataInBooks(),"data");
        //KHi ma nguoi dung an vao doc truyen tang len 1
        story.UpdateView(db);
    }
    public void ButtonLove(View view){
        if(_USER_NAME != null) {
            user = new User(_USER_NAME);
            myRating = user.GetRatingByUserNameAndBook(db,story);
        }
        //NEu nhu dang nhap hay dang nhap de thicch truyen
        if(user != null) {
            //Thag nay chua like di thi insert like moi
            if (myRating == null) {
                myRating = new Rating(user, story.getId());
                myRating.SetIsFavorite(true);
                user.InsertRating(db, myRating);
                //Becase you dont have key id this here it is auto increment you should get it after update
                myRating = user.GetRatingByUserNameAndBook(db, story);
            }
            //Neu nhu da like roi thi chi can cap nhat lai
            else {
                myRating.SetIsFavorite(!myRating.GetIsFavorite());
                user.UpdateRating(db, myRating);
            }
            LoadDataDynamic(myRating,imageButtonHeart,listViewComment,textViewStar,textViewHeart,textViewView,ratebar,writeComment,story,db,this);
        }
        else{
            Intent intent = new Intent(MainComment.this, MainDangNhap.class);
            startActivity(intent);
        }
    }
    public void RatingStory(View view){
        if(_USER_NAME != null) {
            user = new User(_USER_NAME);
            myRating = user.GetRatingByUserNameAndBook(db,story);
        }
        if(user!=null) {
            dialog.show();
        }
        else{
            Intent intent = new Intent(MainComment.this,MainDangNhap.class);
            startActivity(intent);
        }
    }
    public void OpenDiaglogComment(View view){
        if(_USER_NAME != null) {
            user = new User(_USER_NAME);
            myRating = user.GetRatingByUserNameAndBook(db,story);
        }
        if(user!=null) {
            diaglogComment.show();
        }
        else{
            Intent intent = new Intent(MainComment.this,MainDangNhap.class);
            startActivity(intent);
        }
    }
}

