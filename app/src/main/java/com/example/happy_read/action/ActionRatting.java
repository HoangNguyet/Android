package com.example.happy_read.action;

import static com.example.happy_read.database.database.COLUMN_RATINGS_STORY_ID;
import static com.example.happy_read.database.database.COLUMN_USERS_NAME;
import static com.example.happy_read.database.database.TABLE_RATINGS;
import static com.example.happy_read.database.database.TABLE_USERS;

import android.app.people.PeopleManager;
import android.database.Cursor;
import android.util.Log;

import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;
import com.example.happy_read.model.Story;
import com.example.happy_read.model.User;
import com.example.happy_read.until.UserDatabase;
import com.example.happy_read.until.until;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
//Tu mot quyen sach ta co the lay ra danh sach cac comment nen cac hanh dong nay thuoc ve lop book
public class ActionRatting {
    //Lay du lieu tu ratting cua truyen co id la id
    public static List<Rating> getRatting(String book_id, database db){
        //Bien chua tat ca cac comment
        ArrayList<Rating> ratings = new ArrayList<>();
        //Cau lenh select lay tat ca cac rattings cua quyen sach co id la book_id
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_RATINGS,COLUMN_RATINGS_STORY_ID);
        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{book_id})){
//            //Neu ma co ban ghi thi ghi chuyen no sang mot object
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    //Lay du lieu tu db bat dau tu cot 0
                    String id = cursor.getString(0);
                    String userName = cursor.getString(1);
//                    String storyId = cursor.getString(2);
                    String ratting = cursor.getString(3);
                    String comment = cursor.getString(4);
                    Boolean isFavorite = cursor.getString(5).equals("1");
                    //Lay user tu userName
                    User user = UserDatabase.GetUserById(db,userName);
                    Rating rating = new Rating(id,user,Integer.parseInt(ratting),comment,isFavorite);
                    ratings.add(rating);
                }
                return ratings;
            }
            else{
                return ratings;
            }
        }
        catch (Exception ex){
            Log.d("ERRR", Objects.requireNonNull(ex.getMessage()));
        }
        finally {
            db.close();
        }
        return ratings;
    }
    //Tinh trung binh luot danh gia
    public static String[] GetMediumStarAndCountHeart(List<Rating> ratings){
        long mediumStart = 0;
        long peoPlesRating = 0;
        long count = 0;
        for (Rating r: ratings) {
            if(r.GetRatting()>=0){
                mediumStart += r.GetRatting();
                peoPlesRating +=1;
            }
            if(r.GetIsFavorite()){
                count+=1;
            }
        }
//       cai thu hai la so luot tym
//  dau tien la so luot danh gia sao
        return new String[]{
                String.valueOf(mediumStart/peoPlesRating),
                until.FormatCount(count)
        };
    }
}
