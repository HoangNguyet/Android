package com.example.happy_read.model;

import java.util.Date;

public class Story {
    private String _id;
    private String _title;
    private String _description;
    private String _content;
    private String _genre;
    private Date _createdAt;
    private Date _updateAt;
    private String _imagePathDes;
    private int _view;
    private User _user;

    //Getter
    public String GetId() {
        return _id;
    }

    public String GetTitle() {
        return _title;
    }

    public String GetDescription() {
        return _description;
    }

    public String GetContent() {
        return _content;
    }

    public String GetGenre() {
        return _genre;
    }

    public Date GetCreatedAt() {
        return _createdAt;
    }

    public Date GetUpdateAt() {
        return _updateAt;
    }

    public String GetImagePathDes() {
        return _imagePathDes;
    }

    public int GetView() {
        return _view;
    }

    public User GetUser() {
        return _user;
    }


    //Setter

    public void SetTtitle(String title) throws Exception {
        if(title.isEmpty()){
            throw new Exception("Tiêu đề không hợp lệ");
        }
        _title = title;
    }

    public void SetDescription(String description) throws Exception {
        if(description.isEmpty()){
            throw new Exception("Đặc tả không thể để trống");
        }
        _description = description;
    }

    public void SetContent(String content) throws Exception {
        if(content.isEmpty()){
            throw new Exception("Nội dung không để trống");
        }
        _content = content;
    }

    public void SetGenre(String genre) throws Exception {
        if(genre.isEmpty()){
            throw new Exception("Thể loại không để trống");
        }
        _genre = genre;
    }

    public void SetUpdateAt(Date updateAt) {
        _updateAt = updateAt;
    }

    public void SetImagePathDes(String imagePathDes) {
        _imagePathDes = imagePathDes;
    }

    public void UpdateView() {
        _view +=1;
    }

    public Story(String _title, String _description, String _content, String _genre, Date _createdAt, Date _updateAt, String _imagePathDes, User _user) throws Exception {
        this._id =  _id;
        SetTtitle(_title);
        SetDescription(_description);
        SetContent(_content);
        SetGenre(_genre);
        this._createdAt = _createdAt;
        SetUpdateAt(_updateAt);
        SetImagePathDes(_imagePathDes);
        _view = 0;
        this._user = _user;
    }

    public Story(String _id, String _title, String _description, String _content, String _genre, Date _createdAt, Date _updateAt, String _imagePathDes, int _view, User _user) {
        this._id = _id;
        this._title = _title;
        this._description = _description;
        this._content = _content;
        this._genre = _genre;
        this._createdAt = _createdAt;
        this._updateAt = _updateAt;
        this._imagePathDes = _imagePathDes;
        this._view = _view;
        this._user = _user;
    }
}
