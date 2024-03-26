package com.example.happy_read.until;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class until {
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
}
