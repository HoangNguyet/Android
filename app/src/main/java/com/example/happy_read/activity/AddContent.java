package com.example.happy_read.activity;

import static com.example.happy_read.until.until.SwitchPage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.model.Story;

public class AddContent extends AppCompatActivity {

    //Test with usename = "admin" write this book
    String userName = "admin";
    EditText textTitle,textGenre,textContent;
    @Override
    protected void onCreate (Bundle comment) {
        super.onCreate(comment);
        setContentView(R.layout.activity_add_content);
        textTitle = findViewById(R.id.writeStoryTitle);
        textGenre = findViewById(R.id.writeStoryGenre);
        textContent = findViewById(R.id.writeStoryContent);
    }
    public void SendStory(View view){
        try{
            Story story = new Story(textTitle.getText().toString(),textGenre.getText().toString(),textContent.getText().toString(),userName);
            String data = story.ExtractDataInBooks();
            SwitchPage(this,MoTa.class,data,"data");
        }
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
