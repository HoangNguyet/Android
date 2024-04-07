package com.example.happy_read.action;

import static com.example.happy_read.database.database.COLUMN_RATINGS_COMMENT;
import static com.example.happy_read.database.database.COLUMN_RATINGS_ID;
import static com.example.happy_read.database.database.COLUMN_RATINGS_ISFAVORITE;
import static com.example.happy_read.database.database.COLUMN_RATINGS_RATING;
import static com.example.happy_read.database.database.TABLE_RATINGS;
import static com.example.happy_read.until.until.UpdateStatment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;

import java.util.List;
import java.util.Objects;

public class ActionRating {
    //Mình có 3 bảng user stories với bảng rating tạo một cái lớp cho mỗi bảng
//    Phân tích về hành vi của cách đối tượng
//    Với user nó có thể tự tạo ra chính nó user có thể tạo ra rating cõ thể tạo ra story
//    Rating
//    Books nó có hành vi là có thể lấy ra các rating của nó
    //Make database


    //Nó có thê lấy thông tin của chính nó hoặc user và book cũng có thể lấy nhiều nó nên ở đây lấy 1 cái nó sẽ thuộc về bản thân rating
//    protected static Rating GetRattingById(String id, database db,String ColumName){
//        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_RATINGS,ColumName);
//        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{id})){
//            if(cursor.getCount()>0){
//                while (cursor.moveToNext()){
//                    return Rating.GetRatting(db,cursor);
//                }
//            }
//            else{
//                return null;
//            }
//        }
//        catch (Exception ex){
//            Log.d("ERRR", Objects.requireNonNull(ex.getMessage()));
//        }
//        finally {
//            db.close();
//        }
//        return null;
//    }
    protected static Rating GetRatting(database db,Cursor cursor){
        String id = cursor.getString(0);
        String userName = cursor.getString(1);
        String storyId = cursor.getString(2);
        String ratting = cursor.getString(3);
        String comment = cursor.getString(4);
        Boolean isFavorite = cursor.getString(5)==null?null:cursor.getString(5).equals("1");
        Log.d("Check",String.valueOf(isFavorite));
        //Lay user tu userName
        return new Rating(id,storyId,userName,Integer.parseInt(ratting),comment,isFavorite,db);
    }
    //Có vẽ logic đang có vấn đề rating có thể tự tạo một rating mới có vẽ không hợp lý
    //Thay vào đấy việc tạo một rating mới là một hành động của user ?

    //Vì vấn đề về phức tạp code lớn dần nên đây ta sẽ viếp tiếp các phương thức update insert thuộc về bản thân ratting
    // Update ratting
    //Tao mot khuon mau cho viec update boi vi update rating nay la update khong dong thoi
}
