package com.example.happy_read.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.provider.MediaStore;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;

//Hello my name is Ninh and nice too meet you
public class UserProfileEditActivity extends AppCompatActivity {
    TextView _textDate;
    ImageView _imageAvatar;
    int REQUEST_CODE_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        _textDate = (TextView) findViewById(R.id.textDate);
        _textDate.setKeyListener(null);
        _imageAvatar = (ImageView)findViewById(R.id.ImageAvatar);

        //Text Focus and display DateTimePicker
        _textDate.setOnClickListener(v -> initDatePicker());
        _textDate.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                initDatePicker();
            }
        });

        //Event click and change your avtar
        _imageAvatar.setOnClickListener(this::ChangeAvatar);

    }
    //Set data in DatePicker and show DataTimePickerDiaglog
    private void  initDatePicker() {
        String[] date = _textDate.getText().toString().trim().split("/");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) - 1;
        int year = Integer.parseInt(date[2]);
        DatePickerDialog.OnDateSetListener dataSetListener = (view, year1, month1, dayOfMonth) -> UpdateText(dayOfMonth, month1 +1, year1);
        DatePickerDialog datePicker = new DatePickerDialog(this,AlertDialog.THEME_HOLO_DARK,dataSetListener,year,month,day);
        datePicker.show();
    }
    private void UpdateText(int day,int month,int year){
        _textDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
    public void ChangeAvatar(View view){
        Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageIntent.setType("image/*"); // Filter
        startActivityForResult(imageIntent, REQUEST_CODE_IMAGE);
    }

    //Chage Avatar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            // User selected an image
            Uri selectedImageUri = data.getData();
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                _imageAvatar.setImageBitmap(selectedImage);
            } catch (Exception e) {
                Log.d("Error","Error Converts Image to Bit maop");
            }
        }
    }

    //Save user change all profile
    public void SaveProFile(View view){

    }
}
