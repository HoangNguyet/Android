package com.example.happy_read.activity;

import static com.example.happy_read.until.until.OpenLibrary;
import static com.example.happy_read.until.until.SwitchPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.happy_read.R;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Story;
import com.example.happy_read.until.until;
import org.json.JSONObject;

public class MoTa extends AppCompatActivity {
    ImageView imageDescriptionStory;
    EditText WriteDescription;
    Uri selectUri = null;
    @Override
    protected void onCreate (Bundle comment) {
        super.onCreate(comment);
        setContentView(R.layout.activity_mota);
        WriteDescription = findViewById(R.id.writeDescription);
        imageDescriptionStory = findViewById(R.id.imageDescriptionStory);
    }
    public void ChangeImageDescription(View view){
        OpenLibrary(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        until.SetImageViewInLibrary(this,requestCode,resultCode,data,imageDescriptionStory);
        selectUri = data.getData();
    }
    public void UpStory(View view){
        try {
            JSONObject data = new JSONObject(getIntent().getStringExtra("data"));
            String image = null;
            if(selectUri!=null){
                image = until.getRealPathFromUri(this,selectUri);
            }
//            String title, String description, String content, String genre, String imagePathDes,String userName
            Story story = new Story(data.getString("Title"),WriteDescription.getText().toString(),data.getString("Contet"),data.getString("Genre"),image,data.getString("UserName"));
            boolean result = story.InsertStory(new database(this));
            if(result){
                SwitchPage(this,MainHome.class,"Bạn đã đăng truyện thành công","InsertStory");
            }
            else{
                Toast.makeText(this, "Đăng truyện thất bại", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
