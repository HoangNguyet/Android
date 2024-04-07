package com.example.happy_read.activity;

import android.os.Bundle;
import android.service.quicksettings.Tile;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;

import org.json.JSONObject;

public class ConTent extends AppCompatActivity {
    TextView textTitle,textGenre,textContent;
    @Override
    protected void onCreate (Bundle comment) {
        super.onCreate(comment);
        setContentView(R.layout.activity_content);
        textTitle = findViewById(R.id.titleStory);
        textGenre = findViewById(R.id.storyGenre);
        textContent = findViewById(R.id.storyContent);
        JSONObject story;
        try {
            story = new JSONObject(getIntent().getStringExtra("data"));
            textTitle.setText("Thể loại: "+story.getString("Title"));
            textGenre.setText(story.getString("Genre"));
            textContent.setText(story.getString("Content"));
        }
        catch (Exception e){}
    }

}
