package com.example.happy_read.action;

import static com.example.happy_read.database.database.COLUMN_RATINGS_COMMENT;
import static com.example.happy_read.database.database.COLUMN_RATINGS_ISFAVORITE;
import static com.example.happy_read.database.database.COLUMN_RATINGS_RATING;
import static com.example.happy_read.database.database.COLUMN_RATINGS_STORY_ID;
import static com.example.happy_read.database.database.COLUMN_RATINGS_USER_NAME;
import static com.example.happy_read.database.database.COLUMN_USERS_BIRTHDAY;
import static com.example.happy_read.database.database.COLUMN_USERS_FULLNAME;
import static com.example.happy_read.database.database.COLUMN_USERS_GENDER;
import static com.example.happy_read.database.database.COLUMN_USERS_IMAGE;
import static com.example.happy_read.database.database.COLUMN_USERS_NAME;
import static com.example.happy_read.database.database.TABLE_RATINGS;
import static com.example.happy_read.database.database.TABLE_USERS;
import static com.example.happy_read.until.until.UpdateStatment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;
import com.example.happy_read.model.Story;
import com.example.happy_read.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ActionUser {
    protected String UpdateInDb(database db, User user){
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?,%s = ?, %s = ?  WHERE %s = ?", TABLE_USERS, COLUMN_USERS_FULLNAME, COLUMN_USERS_IMAGE, COLUMN_USERS_GENDER, COLUMN_USERS_BIRTHDAY, COLUMN_USERS_NAME);
        SQLiteStatement statement = db.getWritableDatabase().compileStatement(sql);
        UpdateStatment(user.GetFullName(),1,statement);
        UpdateStatment(user.GetImagePath(),2,statement);
        UpdateStatment(user.GetGender(),3,statement);
        UpdateStatment(user.GetBirthDay("yyyy-MM-dd"),4,statement);
        UpdateStatment(user.GetName(),5,statement);
        try {
            statement.execute();
            return "Cập nhật thông tin thành công";
        }
        catch (SQLException e){
            Log.d("sqleroor", Objects.requireNonNull(e.getMessage()));
            return "Cập nhật thông tin thất bại";
        }
        finally {
            db.close();
        }
    }
    protected  static User GetUserById(database db, String id){
        //userName is key
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_USERS,COLUMN_USERS_NAME);
        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{id})){
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String userName = cursor.getString(0);
                    String passWord = cursor.getString(1);
                    String email = cursor.getString(2);
                    String fullName = cursor.getString(3);
                    String role = cursor.getString(4);
                    String imagePath = cursor.getString(5);
                    String gender = cursor.getString(6);
                    Log.d("asdsadsadsa",String.valueOf(cursor.getString(7).isEmpty()));
                    Date birthDay = cursor.getString(7).isEmpty()?null: new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(7));
                    //public User(String _userName, String _password, String _email, String _fullName, String _role, String _imagePath, String _gender, Date _birthDay)
                    return new User(userName,passWord,email,fullName,role,imagePath,gender,birthDay);
                }
            }
            else{
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
    //Đây có một vấn đề là user có thể tự insert chính nó vậy tại sao hành động insert books và hành động insert một rating nên thuộc về user hay nên thuộc về chính nó
    //Giải quyết vấn đề bời vì user là người trải nghiệm nên bản thân nó là do chính người dùng thực tế hay cách khác là người dùng là ngươi sử dụng chức năng, hay là người dung ở đây là sản phẩm đâu tiên của và hành động insert chính nó được viết trong chính đối tượng ảo này luôn
    //Vấn đề insert book, update books , insert rating update rating nó phải nằm ở đây nhưng bởi vì bây giờ sự phức tạp của code đã lớn hơn nên sẽ để bản thân nó tự sửa chính nó
    //Vấn đề cần thiết của việc lập kế hoạch:0)
    protected static Rating GetRatingByUSerNameAndBook(User user, database db, Story story){
        String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",TABLE_RATINGS,COLUMN_RATINGS_USER_NAME,COLUMN_RATINGS_STORY_ID);
        Log.d("query",query);
        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{user.GetName(),story.getId()})){
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    Log.d("adasdasdsas",cursor.getString(1));
                    return Rating.GetRatting(db,cursor);
                }
            }
            else{
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

    //USer action with rating update khi chỉ có 1 sự thay đổi thật sự
    protected void UpdateRating(database db,User user,Rating rating){
        String query = String.format("UPDATE ratings SET %s = ?,%s = ?, %s = ? where id = ?",COLUMN_RATINGS_RATING,COLUMN_RATINGS_ISFAVORITE,COLUMN_RATINGS_COMMENT);
        try{
            SQLiteStatement statement = db.getWritableDatabase().compileStatement(query);
            UpdateStatment(String.valueOf(rating.GetRatting()),1,statement);
            UpdateStatment(rating.GetIsFavorite()==null?null:rating.GetIsFavorite()?"1":"0",2,statement);
            UpdateStatment(rating.GetComment(),3,statement);
            UpdateStatment(rating.GetId(),4,statement);
            statement.execute();
        }catch (Exception ex){
            Log.d("EroorUpdate",ex.getMessage());
        }
        finally {
            db.close();
        }
    }
    //Insert action with rating
    protected boolean InsertRating(database database, User user, Rating rating){
        try {
            SQLiteDatabase db = database.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_RATINGS_USER_NAME, user.GetName());
            values.put(COLUMN_RATINGS_STORY_ID, rating.GetStoryId());
            values.put(COLUMN_RATINGS_RATING, rating.GetRatting());
            values.put(COLUMN_RATINGS_COMMENT, rating.GetComment());
            values.put(COLUMN_RATINGS_ISFAVORITE, rating.GetIsFavorite() == null ? null : rating.GetIsFavorite() ? "1" : "0");
            long result = db.insert(database.TABLE_RATINGS, null, values);
            if(result!=-1){
                return true;
            }
            return false;
        }
        catch (Exception ex){
            Log.d("EroorInsert",ex.getMessage());
        }
        finally {
            database.close();
        }
        return  false;
    }
}
