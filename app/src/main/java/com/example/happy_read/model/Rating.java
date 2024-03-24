package com.example.happy_read.model;

public class Rating {
    private String _id;
    private User _user;
    private Story _story;
    private int _ratting;
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

    public Boolean GEtIsFavorite() {
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
}
