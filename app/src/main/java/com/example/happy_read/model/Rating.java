package com.example.happy_read.model;

import com.example.happy_read.action.ActionRatting;

public class Rating {
    private String _id;
    private User _user;
    private Story _story;
    private int _ratting = -1;
    private String _comment;
    private Boolean _isFavorite;


    public User GetUser() {
        return _user;
    }

    public Story GetStory() {
        return _story;
    }

    public int GetRatting() {
        return _ratting;
        }

    public String GetId(){return  _id;}
    public String GetComment() {
        return _comment;
    }
    //neu nhu ma thich thi tra ve true khong thi tra ve false
    public Boolean GetIsFavorite() {
        return _isFavorite;
    }

    public void set_ratting(int _ratting) {
        this._ratting = _ratting;
    }

    public void set_isFavorite(Boolean _isFavorite) {
        this._isFavorite = _isFavorite;
    }

    public Rating(User _user, Story _story, int _ratting, String _comment, Boolean _isFavorite) {
        this._user = _user;
        this._story = _story;
        this._ratting = _ratting;
        this._comment = _comment;
        this._isFavorite = _isFavorite;
    }

    public Rating(String _id, User _user, Story _story, int _ratting, String _comment, Boolean _isFavorite) {
        this._id = _id;
        this._user = _user;
        this._story = _story;
        this._ratting = _ratting;
        this._comment = _comment;
        this._isFavorite = _isFavorite;
    }
    public Rating(String id, User user,int ratting,String comment, Boolean isFavorite ){
        _id = id;
        _user = user;
        _ratting = ratting;
        _comment = comment;
        _isFavorite = isFavorite;
    }
    @Override
    public String toString(){
        return String.format("Hello this is ratting have %sAnd comment is %s", _user.GetName(), GetComment());
    }
}
