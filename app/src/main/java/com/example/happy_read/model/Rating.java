package com.example.happy_read.model;


import static com.example.happy_read.database.database.COLUMN_RATINGS_ISFAVORITE;
import static com.example.happy_read.database.database.COLUMN_RATINGS_RATING;

import android.database.Cursor;

import com.example.happy_read.action.ActionRating;
import com.example.happy_read.database.database;

public class Rating extends ActionRating {
    private String _id;
    private String _bookId;
    private User _user;
    private float _ratting = -1.0f;
    private String _comment;
    private boolean _isFavorite = false;
    private String _storyId;
    public User GetUser() {
        return _user;
    }
    public String GetBookId(){
        return _bookId;
    }
    public String GetUserName(){
        return _user.GetName();
    }
    public String GetStoryId(){
        return  _storyId;
    }
    public float GetRatting() {
        return _ratting;
        }

    public String GetId(){return  _id;}
    public String GetComment() {
        if(_comment == null){
            return  null;
        }
        return _comment;
    }
    //neu nhu ma thich thi tra ve true khong thi tra ve false
    public boolean GetIsFavorite() {
        return _isFavorite;
    }

    public void SetRating(float _ratting) {
        this._ratting = _ratting;
    }

    public void SetIsFavorite(boolean _isFavorite) {
        this._isFavorite = _isFavorite;
    }
    public void SetComment(String comment){_comment = comment;}
    //Insert
    public Rating(User user, float ratting, String comment, boolean isFavorite,String storyId) {
        _user = user;
        _ratting = ratting;
        _comment = comment;
        _isFavorite = isFavorite;
        _storyId = storyId;
    }
    //Select
    public Rating(String id,String bookId, String userName,float ratting,String comment, boolean isFavorite,database db ){
        this._bookId = bookId;
        _id = id;
        _user = User.GetUserByIdA(db,userName);
        _ratting = ratting;
        _comment = comment;
        _isFavorite = isFavorite;
    }
    public Rating(User user, String storyId){
        _user= user;
        _storyId = storyId;
    }
    @Override
    public String toString(){
        return String.format("Hello this is ratting have %sAnd comment is %s", _user.GetName(), GetComment());
    }
    public static Rating GetRatting(Cursor cursor, database db){
        return GetRatting(db,cursor);
    }
//    public static Rating GetRatingById(String id,database db){return GetRattingById(id,db);}
}
