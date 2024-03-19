package com.example.happy_read.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class database extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "mydatabase";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột của bảng user
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERS_ID = "id";
    public static final String COLUMN_USERS_NAME = "username";
    public static final String COLUMN_USERS_PASSWORD = "password";
    public static final String COLUMN_USERS_EMAIL = "email";
    public static final String COLUMN_USERS_FULLNAME = "fullname";
    public static final String COLUMN_USERS_ROLE = "userrole";
    public static final String COLUMN_USERS_IMAGE = "image";
    public static final String COLUMN_USERS_GENDER = "gender";
    public static final String COLUMN_USERS_BIRTHDAY = "birthday";

    //Tên bảng và các cột của bảng stories
    public static final String TABLE_STORIES = "stories";
    public static final String COLUMN_STORIES_ID = "id";
    public static final String COLUMN_STORIES_TITLE = "title";
    public static final String COLUMN_STORIES_CONTENT = "content";
    public static final String COLUMN_STORIES_GENRE = "genre"; // Thể loại
    public static final String COLUMN_STORIES_CREATED_AT = "created_at"; // tạo ngày
    public static final String COLUMN_STORIES_UPDATED_AT = "updated_at"; // Chỉnh sửa ngày
    public static final String COLUMN_STORIES_IMAGE = "image"; // ảnh
    public static final String COLUMN_STORIES_VIEWS = "views"; //số lượt xem
    public static final String COLUMN_STORIES_USERS_ID = "user_id";

    //Tên bảng và các cột của bảng ratings(đánh giá)
    public static final String TABLE_RATINGS = "ratings";
    public static final String COLUMN_RATINGS_ID = "id";
    public static final String COLUMN_RATINGS_USER_ID = "user_id";
    public static final String COLUMN_RATINGS_STORY_ID = "story_id";
    public static final String COLUMN_RATINGS_RATING = "rating"; // điểm đánh giá của người dùng
    public static final String COLUMN_RATINGS_COMMENT = "comment";
    public static final String COLUMN_RATINGS_ISFAVORITE = "isfavorite";

    //Tên bảng và các cột của bảng favorite
    public static final String TABLE_FAVORITE = "favorites";
    public static final String COLUMN_FAVORITE_ID = "id";
    public static final String COLUMN_FAVORITE_USER_ID = "user_id";
    public static final String COLUMN_FAVORITE_STORY_ID = "story_id";

    // Câu lệnh tạo bảng user
    private static final String SQL_CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERS_NAME + " TEXT NOT NULL, " +
                    COLUMN_USERS_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_USERS_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_USERS_FULLNAME + " TEXT NOT NULL, " +
                    COLUMN_USERS_ROLE + " TEXT NOT NULL, " +
                    COLUMN_USERS_IMAGE + " TEXT, " +
                    COLUMN_USERS_GENDER + " TEXT, " +
                    COLUMN_USERS_BIRTHDAY + " DATE" +
                    ")";

    // câu lệnh tạo bảng stories
    private static final String SQL_CREATE_TABLE_STORIES =
            "CREATE TABLE " + TABLE_STORIES + " (" +
                    COLUMN_STORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STORIES_TITLE + " TEXT NOT NULL, " +
                    COLUMN_STORIES_CONTENT + " TEXT NOT NULL, " +
                    COLUMN_STORIES_GENRE + " TEXT NOT NULL, " +
                    COLUMN_STORIES_CREATED_AT + " TEXT NOT NULL, " +
                    COLUMN_STORIES_UPDATED_AT + " TEXT NOT NULL, " +
                    COLUMN_STORIES_IMAGE + " TEXT, " +
                    COLUMN_STORIES_VIEWS + " INTEGER NOT NULL, " +
                    COLUMN_STORIES_USERS_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + COLUMN_STORIES_USERS_ID + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USERS_ID + ")" +
                    ")";

    //Câu lệnh tạo bảng ratings
    private static final String SQL_CREATE_TABLE_RATINGS =
            "CREATE TABLE " + TABLE_RATINGS + " (" +
                    COLUMN_RATINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RATINGS_USER_ID + " INTEGER NOT NULL, " +
                    COLUMN_RATINGS_STORY_ID + " INTEGER NOT NULL, " +
                    COLUMN_RATINGS_RATING + " INTEGER NOT NULL, " +
                    COLUMN_RATINGS_COMMENT + " TEXT NOT NULL, " +
                    COLUMN_RATINGS_ISFAVORITE + " INTEGER NOT NULL CHECK (" + COLUMN_RATINGS_ISFAVORITE + " IN (0,1)), " +
                    "FOREIGN KEY (" + COLUMN_RATINGS_USER_ID + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USERS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_RATINGS_STORY_ID + ") REFERENCES " +
                    TABLE_STORIES + "(" + COLUMN_STORIES_ID + ")" +
                    ")";

    //câu lệnh tạo bảng favorite
    private static final String SQL_CREATE_TABLE_FAVORITE =
            "CREATE TABLE " + TABLE_FAVORITE + " (" +
                    COLUMN_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FAVORITE_USER_ID + " INTEGER NOT NULL, " +
                    COLUMN_FAVORITE_STORY_ID + " INTEGER NOT NULL, " +
                    "FOREIGN KEY (" + COLUMN_FAVORITE_USER_ID + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USERS_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_FAVORITE_STORY_ID + ") REFERENCES " +
                    TABLE_STORIES + "(" + COLUMN_STORIES_ID + ")" +
                    ")";

    // Câu lệnh xóa bảng user
    private String sql1 = "INSERT INTO users (username, password, email, fullname, userrole)\n" +
            "VALUES ('admin', 'admin', 'admin@example.com', 'Hoàng Ánh Nguyệt', 'admin'),\n" +
            "('anh', 'anh', 'jane@example.com', 'Nguyễn Thị Ngọc Ánh', 'admin'),\n" +
            "('ninh', 'ninh', 'smith@example.com', 'Lê Khắc Ninh', 'user')";


    private String SQLQuery4 = "INSERT INTO truyen VALUES (null,'Doraemon', Mùa đông đã đến rồi trời lạnh buốt, Doraemon không có gì để ăn cả. Doraemon mặc áo vào rồi ra ngoài kiếm thức ăn. Nó đi mãi đi mãi cuối cùng cũng tìm được hai củ cải trắng. Doraemon reo lên:\n" +
            "\n" +
            "– Ôi, ở đây có hai củ cải trắng liền, mình thật là may mắn!\n" +
            "\n" +
            "Doraemon đói bụng, muốn ăn lắm rồi. Nhưng Doraemon lại nghĩ:\n" +
            "\n" +
            "– Ừm… trời lạnh thế này, chắc Nobita cũng không có cái gì để ăn đâu. Mình phải mang cho Nobita một củ mới được.\n" +
            "\n" +
            "Thế là Doraemon đi sang nhà bạn Nobita nhưng Nobita không có nhà nên Doraemon đặt củ cải lên bàn rồi đi về.\n" +
            "\n" +
            "Tình cờ, Nobita con đi chơi cũng tìm được một củ cải trắng nhưng nó chỉ ăn trước một nửa.\n" +
            "\n" +
            "Về đến nhà, lại thấy có một củ cải trắng ở trên bàn Nobita thèm ăn lắm, nhưng lại nghĩ:\n" +
            "\n" +
            "– Ôi trời lạnh thế này chắc Shizuka không có cái gì để ăn rồi, mình phải mang cho Shizuka mới được.\n" +
            "\n" +
            "Nobita con đến nhà Shizuka nhưng Shizuka lại đi vắng, Nobita bèn đặt củ cải ở trên bàn rồi về.\n" +
            "\n" +
            "Khi Shizuka về nhà, thấy củ cải ở trên bàn, Shizuka ngạc nhiên lắm.\n" +
            "\n" +
            "– Ồ, củ cải trắng ở đâu mà ngon vậy nhỉ. Xuỵt… thích quá. Nhưng chắc trời lạnh thế này, Doraemon cũng không có gì ăn đâu. Mình phải mang sang cho Doraemon mới được.\n" +
            "\n" +
            "Khi Shizuka đến thì Doraemon đang ngủ rất say. Khi tỉnh dậy Doraemon lại thấy trên bàn mình xuất hiện một củ cải trắng.Doraemon vui lắm nó chạy đi gọi các bạn:\n" +
            "\n" +
            "– Bạn Shizuka ơi, bạn Nobita ơi hãy đến nhà tôi, chúng ta cùng ăn củ cải trắng thơm ngon này.\n" +
            "\n" +
            "Thế là cuối cùng, củ cải trắng ấy được chia sẻ cho cả ba người bạn tốt bụng của chúng ta. Các bạn thấy đấy tấm lòng thơm thảo, sẵn sàng sẻ chia của các bạn ấy thật là đáng học tập phải không nào?\n" +
            "\n" +
            "Ý nghĩa câu chuyện: Khi cho đi bạn sẽ nhận lại được nhiều hơn những thứ mình có.','Thiếu nhi', '2023.10.24', '2023.11.14', '', 'drawable/doraemon.jpg',1)";


    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức được gọi khi cơ sở dữ liệu được tạo lần đầu tiên
    // Thực thi các câu lệnh tạo bảng để tạo cấu trúc cơ sở dữ liệu
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_STORIES);
        db.execSQL(SQL_CREATE_TABLE_RATINGS);
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }
}
