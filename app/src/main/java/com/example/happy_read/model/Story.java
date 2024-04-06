package com.example.happy_read.model;

import static com.example.happy_read.until.until.FormatCount;

import com.example.happy_read.action.ActionBook;
import com.example.happy_read.database.database;
import com.example.happy_read.until.until;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//Ung dung chay tren mot luong duy nhat nen co ve nhu viec tung ra nhieu ngoai le van la mot van de an toan cho du lieu

public class Story extends ActionBook {
    private String id;
    private String title;
    private String description;
    private String content;
    private String genre;
    private Date createdAt;
    private List<Rating> ratings;
    private Date updateAt;
    private String imagePathDes;
    private int view;
    private User user;

    private String userName; // i want to insert new book and i just want useName
    public String GetUserName(){
        return userName;
    }
    public String getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String  getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int GetView(){
        return view;
    }
    public String getView() {
        return FormatCount(view);
    }
    public String getContent() {
        return content;
    }
    public String getCreatedAt(String format) {
        if( createdAt == null){
            return  null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(createdAt);
    }
    public List<Rating> GetRattings() {
        return ratings;
    }
    public String getGenre() {
        return  genre;
    }
    public String getImagePathDes() {
        return imagePathDes;
    }
    public String GetAuthor(){
        return user.GetFullName();
    }
    public String getUpdateAt(String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(updateAt);
    }
    public void setTitle(String title) throws Exception {
        if(title.isEmpty()){
            throw new Exception("Tên truyện không được để trống");
        }
        this.title = title;
    }
    public void setDescription(String description) throws Exception {
        if(description.isEmpty()){
            throw new Exception("Mô tả không được để trống");
        }
        if(description.split("").length >100){
            throw new Exception("Mô tả không lớn hơn 100 chữ");
        };
        this.description = description;
    }
    public void setContent(String content) throws Exception {
        if(content.isEmpty()){
            throw new Exception("Nội dung không được để trống");
        }
        this.content = content;
    }
    public void setGenre(String genre) throws Exception {
        if(genre.isEmpty()){
            throw new Exception("Thể loại không được để trống");
        }
        this.genre = genre;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public void setImagePathDes(String imagePathDes) throws Exception {
        if(imagePathDes==null){
            throw new Exception("Hãy chon ảnh mô tả cho sản phẩm trước khi đăng truyện");
        }
        this.imagePathDes = imagePathDes;
    }
    public void setView(int view) {
        this.view = view;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Story() {
    }

    public Story(String title, String imagePathDes) {
        this.title = title;
        this.imagePathDes = imagePathDes;
    }

    public Story(String title, String genre, String content,String userName) throws Exception{
        this.userName = userName;
        setTitle(title);
        setGenre(genre);
        setContent(content);
    }
    public Story(String id, String title, String description,Date createdAt, String content, String genre, String imagePathDes, int view ,String user_name, database db) {
        user = User.GetUserByIdA(db,user_name);
        ratings = getAllRattingByBook(id,db);
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.genre = genre;
        this.imagePathDes = imagePathDes;
        this.view = view;
        this.createdAt = createdAt;
    }
    public Story(String title, String description, String content, String genre, String imagePathDes,String userName) throws Exception {
        this.userName = userName;
        this.title = title;
        this.genre = genre;
        this.content = content;
        setDescription(description);
        setImagePathDes(imagePathDes);
        view = 0;
        this.createdAt = new Date();
        this.updateAt = new Date();
    }
    public Story(String id, String title, String content, String imagePathDes,String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imagePathDes = imagePathDes;
        this.userName = userName;
    }

    public Story(String id, String title, String genre, String imagePathDes, int view) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.imagePathDes = imagePathDes;
        this.view = view;
    }
    //Lay het rating cua quyen sach nay ra
    public List<Rating> getAllRattingByBook(database db){
        return getAllRattingByBook(id,db);
    }
    public String[] GetMediumStarAndCountHeartA(){
        return GetMediumStarAndCountHeart(Story.this.GetRattings());
    }
    public static Story GetStoryById(String id,database db){
        return GetStorieyById(db,id);
    }
    public String ExtractDataInBooks(){
        return ExtractDataInBooks(this);
    }
    public boolean InsertStory(database db){return InsertStory(this,db);}
}
