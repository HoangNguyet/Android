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
import java.util.Objects;

public class ActionRating {
    //Make database
    //Update rating
    protected String UpdateRatingInDb(Rating rating, database db){
        String sql = String.format("UPDATE %s SET $s = ? ,%s = ?, %s = ?  WHERE %s = ?", TABLE_RATINGS,COLUMN_RATINGS_RATING, COLUMN_RATINGS_ISFAVORITE,COLUMN_RATINGS_COMMENT, COLUMN_RATINGS_ID);
        SQLiteStatement statement = db.getWritableDatabase().compileStatement(sql);
        UpdateStatment(String.valueOf(rating.GetRatting()),1,statement);
        UpdateStatment(rating.GetIsFavorite()?"1":"0",2,statement);
        UpdateStatment(rating.GetComment(),3,statement);
        UpdateStatment(rating.GetId(),4,statement);
        try {
            statement.execute();
            return "Cập nhật đánh giá thành công";
        }
        catch (SQLException e){
            Log.d("sqleroor", Objects.requireNonNull(e.getMessage()));
            return "Cập nhật đánh giá thất bại";
        }
        finally {
            db.close();
        }
    }
    protected static Rating GetRattingById(String id, database db){
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_RATINGS,COLUMN_RATINGS_ID);
        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{id})){
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    return Rating.GetRatting(db,cursor);
                }
            }
            else{
//                Log.d("C","B");
                return null;
            }
        }
        catch (Exception ex){
            Log.d("ERRR", Objects.requireNonNull(ex.getMessage()));
        }
        finally {
            db.close();
        }
        return null;
    }
    protected static Rating GetRatting(database db,Cursor cursor){
        String id = cursor.getString(0);
        String userName = cursor.getString(1);
        String storyId = cursor.getString(2);
        String ratting = cursor.getString(3);
        String comment = cursor.getString(4);
        Boolean isFavorite = cursor.getString(5).equals("1");
        //Lay user tu userName
        return new Rating(id,storyId,userName,Integer.parseInt(ratting),comment,isFavorite,db);
    }
    protected void InsertRating(Rating rating,database database){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(database.COLUMN_RATINGS_USER_NAME, rating.GetUserName());
        values.put(database.COLUMN_RATINGS_STORY_ID, rating.GetStoryId());
        values.put(database.COLUMN_RATINGS_RATING, rating.GetRatting());
        values.put(database.COLUMN_RATINGS_COMMENT, rating.GetComment());
        values.put(database.COLUMN_RATINGS_ISFAVORITE, rating.GetIsFavorite()?"1":"0");
        db.insert(database.TABLE_RATINGS, null, values);
        db.close();
    }
}
