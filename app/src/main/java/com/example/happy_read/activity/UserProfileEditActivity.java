package com.example.happy_read.activity;

import static com.example.happy_read.until.UserDatabase.GetUserById;
import static com.example.happy_read.until.UserDatabase.UpdateUser;
import static com.example.happy_read.until.until.getRealPathFromUri;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_read.database.database;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.R;
import com.example.happy_read.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//Hello my name is Ninh and nice too meet you
public class UserProfileEditActivity extends AppCompatActivity {
    TextView _textDate;
    private User _user;
    TextView _email;
    EditText _fullName;
    ImageView _imageAvatar;
    RadioButton _male;
    RadioButton _female;
    int REQUEST_CODE_IMAGE = 1;
    Uri selectedImageUri = null;

    //Test with id static
    String _userName = "ninh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database db = new database(UserProfileEditActivity.this);
        _user = GetUserById(db,_userName);

        _textDate = (TextView) findViewById(R.id.textDate);
        _textDate.setKeyListener(null);

        _imageAvatar = (ImageView) findViewById(R.id.ImageAvatar);
        _email = (TextView) findViewById(R.id.editEmail);
        _fullName = (EditText) findViewById(R.id.editFullName);
        _male = (RadioButton) findViewById(R.id.genderMale);
        _female = (RadioButton) findViewById(R.id.genderFemale);

        //Text Focus and display DateTimePicker
        _textDate.setOnClickListener(v -> initDatePicker());
        _textDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                initDatePicker();
            }
        });
        updateProfile();

        //Event click and change your avtar
        _imageAvatar.setOnClickListener(this::ChangeAvatar);

    }

    //Set data in DatePicker and show DataTimePickerDiaglog
    private void initDatePicker() {
        int day = 31;
        int month = 00;
        int year = 2003;
        DatePickerDialog datePicker;
        if (!_textDate.getText().toString().isEmpty()) {
            String[] date = _textDate.getText().toString().trim().split("/");
            day = Integer.parseInt(date[0]);
            month = Integer.parseInt(date[1]) - 1;
            year = Integer.parseInt(date[2]);
        }
        DatePickerDialog.OnDateSetListener dataSetListener = (view, year1, month1, dayOfMonth) -> UpdateTextDate(dayOfMonth, month1 + 1, year1);
        datePicker = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, dataSetListener, year, month, day);
        datePicker.show();
    }

    private void UpdateTextDate(int day, int month, int year) {
        _textDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    public void ChangeAvatar(View view) {
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
            selectedImageUri = data.getData();
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                _imageAvatar.setImageBitmap(selectedImage);
            } catch (Exception e) {
                Log.d("Error", "Error Converts Image to Bit maop");
            }
        }
    }

    //Save user change all profile
    public void SaveProFile(View view) throws Exception {
        String imagePath = _user.GetImagePath();
        Date birthDay = null;
        String gender = null;
        String ImagePath = null;

        if (selectedImageUri != null) {
            ImagePath = getRealPathFromUri(this, selectedImageUri);
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
        Log.d("eassad",gender);
        try {
            _user.UpdateInformation(_fullName.getText().toString(), ImagePath, gender, birthDay);
            database db = new database(UserProfileEditActivity.this);
            String mess = UpdateUser(db,_user);
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
                Log.d("eafja",_user.GetGender());
                if (_user.isMale()) {
                    _male.setChecked(true);
                } else {
                    _female.setChecked(true);
                }
            }
            if (_user.GetBirthDay("dd/MM/yyyy") != null) {
                _textDate.setText(_user.GetBirthDay("dd/MM/yyyy"));
            }
            if(_user.GetImagePath()==null) {
                try {
                    Bitmap bitmap = getBitmap("avatar/img.png");
                    _imageAvatar.setImageBitmap(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                //Gan anh tu local vao imagview
                Bitmap imageBitMap = BitmapFactory.decodeFile(_user.GetImagePath());
                _imageAvatar.setImageBitmap(imageBitMap);
            }
        }
    }

    public Bitmap getBitmap(String path) throws IOException {
        AssetManager manager = getAssets();
        InputStream stream = manager.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return bitmap;
    }
}