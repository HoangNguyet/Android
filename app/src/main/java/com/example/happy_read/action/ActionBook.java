package com.example.happy_read.action;

import static com.example.happy_read.database.database.COLUMN_RATINGS_STORY_ID;
import static com.example.happy_read.database.database.COLUMN_STORIES_ID;
import static com.example.happy_read.database.database.TABLE_RATINGS;
import static com.example.happy_read.database.database.TABLE_STORIES;
import static com.example.happy_read.until.until.UpdateStatment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.example.happy_read.database.database;
import com.example.happy_read.model.Rating;
import com.example.happy_read.model.Story;
import com.example.happy_read.until.until;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//Tu mot quyen sach ta co the lay ra danh sach cac comment nen cac hanh dong nay thuoc ve lop book
public class ActionBook {
    //Lay du lieu tu ratting cua truyen co id la id
    //hành động lấy tất cả comment thuộc về cuốn sách bởi vì
    protected static List<Rating> getAllRattingByBook(String book_id, database db){
        //Bien chua tat ca cac comment
        ArrayList<Rating> ratings = new ArrayList<>();
        //Cau lenh select lay tat ca cac rattings cua quyen sach co id la book_id
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_RATINGS,COLUMN_RATINGS_STORY_ID);
        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{book_id})){
//            //Neu ma co ban ghi thi ghi chuyen no sang mot object
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    //Lay du lieu tu db bat dau tu cot 0
                    ratings.add(Rating.GetRatting(cursor,db));
                }
                return ratings;
            }
            else{
                return ratings;
            }
        }
        catch (Exception ex){
            //do some thing
        }
        finally {
            db.close();
        }
        return ratings;
    }
    //Filter rating have content comment
    protected static List<Rating> GetRatingsHaveComment(List<Rating> ratings){
        List<Rating> ratingsHaveComment = new ArrayList<>();
        for (Rating r: ratings) {
            if(r.GetComment() != null){
                ratingsHaveComment.add(r);
            }
        }
        return ratingsHaveComment;
    }
    //Tinh trung binh luot danh gia
    protected String[] GetMediumStarAndCountHeart(List<Rating> ratings){
        //Van de de bi tran bo nho
        float totalStart = 0;
        long peoPlesRating = 0;
        long count = 0;
        for (Rating r: ratings) {
            if(r.GetRatting()>=0){
                totalStart += r.GetRatting();
                peoPlesRating +=1;
            }
            if(r.GetIsFavorite()){
                count+=1;
            }
        }
//       cai thu hai la so luot tym
//  dau tien la so luot danh gia sao
        return new String[]{
                peoPlesRating == 0?"0":new DecimalFormat("#.1").format(totalStart/peoPlesRating),
                until.FormatCount(count)
        };
    }
    //Get Story by id
    //Một hành động của chính nó hoặc có thể một hành động nhiều khi người dùng có thê lấy tất cả các cuốn sách của mình đã viết
    protected static Story GetStorieyById(database db, String id){
        String query = String.format("SELECT * FROM %s WHERE %s = ?",TABLE_STORIES,COLUMN_STORIES_ID);
        try(Cursor cursor = db.getReadableDatabase().rawQuery(query,new String[]{id})){
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    String book_id = cursor.getString(0);
                    String title = cursor.getString(1);
                    String content = cursor.getString(2);
                    String gener = cursor.getString(3);
                    String description = cursor.getString(4);
                    Date createAt = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(5));
                    String image = cursor.getString(7);
                    int view = cursor.getInt(8);
                    String user_name = cursor.getString(9);
                    return new Story(book_id,title,description,createAt,content,gener,image,view,user_name, db);
                }
            }
            else{
//                Log.d("C","B");
                return null;
            }
        }
        catch (Exception ex){
            //dosomething
        }
        finally {
            db.close();
        }
        return null;
    }
    //trich xuat ten truyen the loai va noi dung de hien thi cho cai activity content de nguoi dung doc truyen
    protected String ExtractDataInBooks(Story story){
        JSONObject books = new JSONObject();
        try{
            books.put("UserName",story.GetUserName());
            books.put("Title",story.getTitle());
            books.put("Genre",story.getGenre());
            books.put("Contet",story.getContent());}
        catch (Exception ex){
            //do some thing
        }
        return books.toString();
    }
    //Hành động insert chính nó có vẽ không hợp lý mà phải
    protected Boolean InsertStory(Story story, database database){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_TITLE, story.getTitle());
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_CONTENT, story.getContent());
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_GENRE, story.getGenre());
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_DESCRIPTION, story.getDescription());
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_CREATED_AT, story.getCreatedAt("yyyy-MM-dd"));
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_UPDATED_AT, story.getUpdateAt("yyyy-MM-dd"));
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_IMAGE, story.getImagePathDes());
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_VIEWS, story.getView());
        values.put(com.example.happy_read.database.database.COLUMN_STORIES_USERS_NAME,story.GetUserName());
        long result = db.insert(TABLE_STORIES, null, values);
        return result != -1;
    }
    protected void UpdateView(database db,Story story){
        String query = "UPDATE stories SET views = views+1 where id = ?";
        try{
            SQLiteStatement statement = db.getWritableDatabase().compileStatement(query);
            UpdateStatment(story.getId(),1,statement);
            statement.execute();
        }catch (Exception ex){
            //do some thing
        }
        finally {
            db.close();
        }
    }
}
