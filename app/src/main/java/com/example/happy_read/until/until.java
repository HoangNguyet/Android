package com.example.happy_read.until;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy_read.activity.UserProfileActivity;
import com.example.happy_read.activity.UserProfileEditActivity;

import java.io.IOException;
import java.io.InputStream;

public class until {
    static int REQUEST_CODE_IMAGE = 1;
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        String path = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        try (Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            Log.e("Error", "Error retrieving real path from URI", e);
        }
        return path;
    }
    public static void UpdateStatment(String value,int index,SQLiteStatement statement){
        if(value == null){
            statement.bindNull(index);
        }
        else{
            statement.bindString(index,value);
        }
    }
    //Chuyen sang dang la 4.3m 4.3k
    public static String FormatCount(long count){
        DecimalFormat df = new DecimalFormat("#.1");
        String c;
        if(count<=999){
            c  = String.valueOf(count);
        }
        else{
            if(count<=999999){
                //999,9K
                c = String.format("%sK",  df.format((count / 1000)));
            }
            else{
                c = String.format("%sK",  df.format((count / 1000000)));
            }
        }
        return c;
    }
    //Mo thu vien
    public static void OpenLibrary(AppCompatActivity activity){
        Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageIntent.setType("image/*"); // Filter
        startActivityForResult(activity,imageIntent, REQUEST_CODE_IMAGE,null);
    }
    //Gan anh vao imageView
    public static void SetImageViewInLibrary(AppCompatActivity activity, int requestCode, int resultCode, Intent data, ImageView _imageAvatar){
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            // User selected an image
            Uri selectedImageUri = data.getData();
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImageUri);
                _imageAvatar.setImageBitmap(selectedImage);
            } catch (Exception e) {
                Log.d("Error", "Error Converts Image to Bit maop");
            }
        }
    }
    //Chuyen anh sang bitmap
    public static Bitmap getBitmap(AppCompatActivity activity,String path) throws IOException {
        AssetManager manager = activity.getAssets();
        InputStream stream = manager.open(path);
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        return bitmap;
    }

    //Open dateTimePicker and update textView
    public static void initDatePicker(AppCompatActivity activity, TextView _textDate) {
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
        DatePickerDialog.OnDateSetListener dataSetListener = (view, year1, month1, dayOfMonth) -> UpdateTextDate(dayOfMonth, month1 + 1, year1,_textDate);
        datePicker = new DatePickerDialog(activity, AlertDialog.THEME_HOLO_DARK, dataSetListener, year, month, day);
        datePicker.show();
    }
    private static void UpdateTextDate(int day, int month, int year,TextView _textDate) {
        _textDate.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    // Chuyen doi activity
    public static void SwitchPage(AppCompatActivity Befor, Class<?> Next){
        Intent intent = new Intent(Befor,Next);
        startActivity(Befor,intent,null);
        Befor.finish();
    }

}
