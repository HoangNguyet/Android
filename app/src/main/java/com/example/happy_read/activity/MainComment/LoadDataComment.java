package com.example.happy_read.activity.MainComment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.adapter.RatingAdapter;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;
import com.example.happy_read.model.Story;
import com.example.happy_read.until.until;
import com.squareup.picasso.Picasso;

public class LoadDataComment {
    static String love = "drawable/heart.png";
    static String unLove = "drawable/heart1.png";
    public static void LoadStaticData(TextView textTitle, TextView textAuthor, TextView textGenre, TextView textCreateAt, TextView textReview, ImageView imageViewDescription, Story story, AppCompatActivity activity){
        //Phuong thuc GetMediaStartAndCountHeart tra ve so luot danh gia sao trung binh va so luot tym
        textTitle.setText(story.getTitle());
        textAuthor.setText("Tác giả: "+story.GetAuthor());
        textGenre.setText("Thể loại: "+story.getGenre());
        textCreateAt.setText("Ngày phát hành: "+story.getCreatedAt("dd/MM/yyyy"));
        textReview.setText("Review: " +story.getDescription());
        int imageResId = until.GetImageResId(story.getImagePathDes(),activity.getApplicationContext());
        if(imageResId!=0) {
            Picasso.get().load(imageResId).into(imageViewDescription);
        }
        else{
            Bitmap imageBitMap = BitmapFactory.decodeFile(story.getImagePathDes());
            imageViewDescription.setImageBitmap(imageBitMap);
        }
    }
    public static void LoadDataDynamic(Rating myRating, ImageButton imageButtonHeart, ListView listViewComment, TextView textViewStar, TextView textViewHeart, TextView textViewView, RatingBar ratingBar, Story story, database db, AppCompatActivity activity) {
        story.SetRatings(db);
        if (myRating != null) {
            if(myRating.GetRatting()!=-1.0f){
                ratingBar.setRating(myRating.GetRatting());
            }
            imageButtonHeart.setImageResource(until.GetImageResId(myRating.GetIsFavorite() ? love : unLove, activity));
        }
        RatingAdapter ratingAdapter = new RatingAdapter(activity.getApplicationContext(), story.getRatingsHaveCommentConTent());
        listViewComment.setAdapter(ratingAdapter);
        String[] StartAndHeart = story.GetMediumStarAndCountHeartA();
        textViewStar.setText(StartAndHeart[0]);
        textViewHeart.setText(StartAndHeart[1]);
        textViewView.setText(story.getView());
//        ratingBar.setRating(myRating.GetRatting());
    }
}
