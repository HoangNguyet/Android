package com.example.happy_read.activity;

import static com.example.happy_read.until.Log._USER_NAME;
import static com.example.happy_read.until.until.getRealPathFromUri;
import static com.example.happy_read.until.until.initDatePicker;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_read.database.database;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.model.User;
import com.example.happy_read.until.until;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//Hello my name is Ninh and nice too meet you
public class UserProfileEditActivity extends AppCompatActivity {
    TextView _textDate,_email;
    User _user;
    EditText _fullName;
    ImageView _imageAvatar;
    RadioButton _male, _female;
    Uri selectedImageUri = null;
    //Test with id static

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database db = new database(UserProfileEditActivity.this);
        _user = User.GetUserByIdA(db,_USER_NAME);

        _textDate = (TextView) findViewById(R.id.textDate);
        _textDate.setKeyListener(null);

        _imageAvatar = (ImageView) findViewById(R.id.ImageAvatar);
        _email = (TextView) findViewById(R.id.editEmail);
        _fullName = (EditText) findViewById(R.id.editFullName);
        _male = (RadioButton) findViewById(R.id.genderMale);
        _female = (RadioButton) findViewById(R.id.genderFemale);

        //Text Focus and display DateTimePicker
        _textDate.setOnClickListener(v -> initDatePicker(this,_textDate));
        _textDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                initDatePicker(this,_textDate);
            }
        });
        updateProfile();

        //Event click and change your avtar
        _imageAvatar.setOnClickListener(this::ChangeAvatar);

    }

    //Set data in DatePicker and show DataTimePickerDiaglog

    public void ChangeAvatar(View view) {
        until.OpenLibrary(this);
    }

    //Chage Avatar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        until.SetImageViewInLibrary(this,requestCode,resultCode,data,_imageAvatar);
        selectedImageUri = data.getData();
    }

    //Save user change all profile
    public void SaveProFile(View view) throws Exception {
        Date birthDay = null;
        String gender = null;
        String ImagePath = null;
        if (selectedImageUri != null) {
            ImagePath = getRealPathFromUri(this, selectedImageUri);
        }
        else{
            ImagePath = _user.GetImagePath();
        }
        if (!_textDate.getText().toString().isEmpty()) {
            String[] birth = _textDate.getText().toString().trim().split("/");
            birthDay = new SimpleDateFormat("yyyy/MM/dd").parse(String.format("%s/%s/%s", birth[2], birth[1], birth[0]));
        }
        if (_female.isChecked()) {
            gender = "female";
        }
        if (_male.isChecked()) {
            gender = "male";
        }
        try {
            _user.UpdateInformation(_fullName.getText().toString(), ImagePath, gender, birthDay);
            database db = new database(UserProfileEditActivity.this);
            String mess = _user.UpdateUserInDb(db);
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            _fullName.setText(_user.GetFullName());
        }
    }


    private void updateProfile() {
        if (_user != null) {
            _email.setText(_user.GetEmail());
            _fullName.setText(_user.GetFullName());

//            Setgender and display in interface
            if (_user.GetGender() != null) {
                if (_user.isMale()) {
                    _male.setChecked(true);
                } else {
                    _female.setChecked(true);
                }
            }
            if (_user.GetBirthDay("dd/MM/yyyy") != null) {
                _textDate.setText(_user.GetBirthDay("dd/MM/yyyy"));
            }
            if(_user.IsImagePathNull()) {
                 Picasso.get().load(until.GetImageResId(_user.GetImagePath(),this)).into(_imageAvatar);
            }
            else{
                //Gan anh tu local vao imagview
                Bitmap imageBitMap = BitmapFactory.decodeFile(_user.GetImagePath());
                _imageAvatar.setImageBitmap(imageBitMap);
            }
        }
    }
}