package com.example.happy_read.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "mydatabase";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột của bảng user
    public static final String TABLE_USERS = "users";
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
    public static final String COLUMN_STORIES_DESCRIPTION = "description";
    public static final String COLUMN_STORIES_CONTENT = "content";
    public static final String COLUMN_STORIES_GENRE = "genre"; // Thể loại
    public static final String COLUMN_STORIES_CREATED_AT = "created_at"; // tạo ngày
    public static final String COLUMN_STORIES_UPDATED_AT = "updated_at"; // Chỉnh sửa ngày
    public static final String COLUMN_STORIES_IMAGE = "image"; // ảnh
    public static final String COLUMN_STORIES_VIEWS = "views"; //số lượt xem
    public static final String COLUMN_STORIES_USERS_NAME = "user_name";

    //Tên bảng và các cột của bảng ratings(đánh giá)
    public static final String TABLE_RATINGS = "ratings";
    public static final String COLUMN_RATINGS_ID = "id";
    public static final String COLUMN_RATINGS_USER_NAME = "user_name";
    public static final String COLUMN_RATINGS_STORY_ID = "story_id";
    public static final String COLUMN_RATINGS_RATING = "rating"; // điểm đánh giá của người dùng
    public static final String COLUMN_RATINGS_COMMENT = "comment";
    public static final String COLUMN_RATINGS_ISFAVORITE = "isfavorite";

    // Câu lệnh tạo bảng user
    private static final String SQL_CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USERS_NAME + " TEXT PRIMARY KEY NOT NULL, " +
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
                    COLUMN_STORIES_DESCRIPTION + " TEXT NOT NULL, " +
                    COLUMN_STORIES_CREATED_AT + " TEXT NOT NULL, " +
                    COLUMN_STORIES_UPDATED_AT + " TEXT NOT NULL, " +
                    COLUMN_STORIES_IMAGE + " TEXT, " +
                    COLUMN_STORIES_VIEWS + " INTEGER NOT NULL, " +
                    COLUMN_STORIES_USERS_NAME + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + COLUMN_STORIES_USERS_NAME + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USERS_NAME + ")" +
                    ")";

    //Câu lệnh tạo bảng ratings
    //one user have one ratting belong to book = >
    private static final String SQL_CREATE_TABLE_RATINGS =
            "CREATE TABLE " + TABLE_RATINGS + " (" +
                    COLUMN_RATINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RATINGS_USER_NAME + " TEXT NOT NULL, " +
                    COLUMN_RATINGS_STORY_ID + " INTEGER NOT NULL, " +
                    COLUMN_RATINGS_RATING + " INTEGER NOT NULL, " +
                    COLUMN_RATINGS_COMMENT + " TEXT NOT NULL, " +
                    COLUMN_RATINGS_ISFAVORITE + " INTEGER NOT NULL CHECK (" + COLUMN_RATINGS_ISFAVORITE + " IN (0,1)), " +
                    "CONSTRAINT UC_Unique_Rating  UNIQUE (" + COLUMN_RATINGS_USER_NAME + ", " + COLUMN_RATINGS_STORY_ID + ")" +
                    ")";

    // Câu lệnh xóa bảng user
    private static final String sql1 = "INSERT INTO users (username, password, email, fullname, userrole, image, gender, birthday)\n" +
            "VALUES ('admin', 'admin', 'admin@example.com', 'Hoàng Ánh Nguyệt', 'admin', null, 'female', '2003-01-31'),\n" +
            "('anh', 'anh', 'jane@example.com', 'Nguyễn Thị Ngọc Ánh', 'admin', null, 'female', '2003-01-31'),\n" +
            "('ninh', 'ninh', 'smith@example.com', 'Lê Khắc Ninh', 'user', null, 'male', '2003-01-14')";


    private static final String sql2 = "INSERT INTO stories VALUES (null," +
            "'Doraemon', " +//Tittle
            "'Mùa đông đã đến rồi trời lạnh buốt, Doraemon không có gì để ăn cả." +
            " Doraemon mặc áo vào rồi ra ngoài kiếm thức ăn. Nó đi mãi đi mãi cuối cùng cũng tìm được hai củ cải trắng. " +
            "Doraemon reo lên: Ôi, ở đây có hai củ cải trắng liền, mình thật là may mắn! Doraemon đói bụng, muốn ăn lắm rồi. " +
            "Nhưng Doraemon lại nghĩ: Ừm… trời lạnh thế này, chắc Nobita cũng không có cái gì để ăn đâu." +
            " Mình phải mang cho Nobita một củ mới được. Thế là Doraemon đi sang nhà bạn Nobita nhưng Nobita không có nhà nên Doraemon đặt củ cải lên bàn rồi đi về. " +
            "Tình cờ, Nobita con đi chơi cũng tìm được một củ cải trắng nhưng nó chỉ ăn trước một nửa." +
            " Về đến nhà, lại thấy có một củ cải trắng ở trên bàn Nobita thèm ăn lắm, nhưng lại nghĩ:" +
            " Ôi trời lạnh thế này chắc Shizuka không có cái gì để ăn rồi, mình phải mang cho Shizuka mới được." +
            " Nobita con đến nhà Shizuka nhưng Shizuka lại đi vắng, Nobita bèn đặt củ cải ở trên bàn rồi về. " +
            "Khi Shizuka về nhà, thấy củ cải ở trên bàn, Shizuka ngạc nhiên lắm. Ồ, củ cải trắng ở đâu mà ngon vậy nhỉ." +
            " Xuỵt… thích quá. Nhưng chắc trời lạnh thế này, Doraemon cũng không có gì ăn đâu. Mình phải mang sang cho Doraemon mới được. " +
            "Khi Shizuka đến thì Doraemon đang ngủ rất say. Khi tỉnh dậy Doraemon lại thấy trên bàn mình xuất hiện một củ cải trắng." +
            " Doraemon vui lắm nó chạy đi gọi các bạn: Bạn Shizuka ơi, bạn Nobita ơi hãy đến nhà tôi, chúng ta cùng ăn củ cải trắng thơm ngon này. " +
            "Thế là cuối cùng, củ cải trắng ấy được chia sẻ cho cả ba người bạn tốt bụng của chúng ta. Các bạn thấy đấy tấm lòng thơm thảo," +
            " sẵn sàng sẻ chia của các bạn ấy thật là đáng học tập phải không nào?', " +//Contet
            "'Câu chuyện kể về chú mèo máy doremon '," + //Description
            "'Thiếu nhi', " + //Genre
            "'2023-10-24', " + //Create_at
            "'2023-11-14', " + //Update_at
            "'drawable/doraemon.jpg', " + //ImageInterView
            "3101, " +//View//UserId
            "'admin')";//UserId
    private static final String sql3 = "INSERT INTO stories VALUES (null," +
            "'Conan', " +
            "'Conan là một nhân vật trong truyện tranh nổi tiếng của Nhật Bản. Cậu bé này được biết đến với tên thật là Shinichi Kudo, " +
            "một học sinh cấp 3 tài năng và nổi tiếng trong việc phá án. " +
            "Trong một lần tình cờ, Shinichi bị biến thành một cậu bé nhỏ tuổi hơn sau khi bị độc thuốc của tổ chức Áo Đen." +
            " Với sự trợ giúp của bác sĩ Agasa, Shinichi sống dưới danh tính Conan Edogawa và tiếp tục phá án để tìm ra tung tích của tổ chức Áo Đen.', " +
            "'................. '," + //Description
            "'Hành động', " +
            "'2023-09-10', " +
            "'2023-09-25', " +
            "'drawable/conan.jpg', " +
            "1500, " +
            "'admin')";

    private static final String sql4 = "INSERT INTO stories VALUES (null," +
            "'One Piece', " +
            "'One Piece là một bộ truyện tranh nổi tiếng của tác giả Eiichiro Oda." +
            " Câu chuyện kể về cuộc hành trình của Monkey D. Luffy - một cậu bé trẻ đam mê trở thành Vua Hải Tặc và tìm kiếm kho báu One Piece để trở thành Vua Hải Tặc." +
            " Cùng với băng hải tặc Mũ Rơm của mình, Luffy vượt qua nhiều thử thách và gặp gỡ nhiều nhân vật đầy màu sắc trên đường đi.', " +
            "'................. '," + //Description
            "'Phiêu lưu', " +
            "'2023-08-05', " +
            "'2023-08-20', " +
            "'drawable/one_piece.jpg', " +
            "2500, " +
            "'admin')";

    private static final String sql5 = "INSERT INTO stories VALUES (null," +
            "'Naruto', " +
            "'Naruto là một bộ truyện tranh kinh điển của tác giả Masashi Kishimoto. " +
            "Câu chuyện xoay quanh cuộc phiêu lưu của Naruto Uzumaki - một cậu bé có ước mơ trở thành Hokage, người lãnh đạo của làng ninja. " +
            "Trong hành trình của mình, Naruto phải đối mặt với nhiều thử thách và học được nhiều bài học quý giá về tình bạn, lòng dũng cảm và sự kiên nhẫn.', " +
            "'................. '," + //Description
            "'Hành động, phiêu lưu', " +
            "'2023-07-15', " +
            "'2023-07-30', " +
            "'drawable/naruto.jpg', " +
            "2000, " +
            "'admin')";

    private static final String sql6 = "INSERT INTO stories VALUES (null, " +
            "'Dragon Ball', " +
            "'Dragon Ball là một trong những bộ truyện tranh huyền thoại của Nhật Bản, sáng tạo bởi Akira Toriyama. " +
            "Câu chuyện xoay quanh cuộc hành trình của Goku - một chiến binh Siêu Saya và các đồng đội của mình trong việc tìm kiếm các viên ngọc rồng và " +
            "chiến đấu chống lại các kẻ thù đáng sợ như Frieza, Cell và Buu để bảo vệ trái đất.', " +
            "'................. '," + //Description
            "'Hành động, võ thuật', " +
            "'2023-06-20', " +
            "'2023-07-05', " +
            "'drawable/dragon_ball.jpg', " +
            "1800, " +
            "'admin')";

    private static final String sql7 = "INSERT INTO stories VALUES (null, " +
            "'Attack on Titan', " +
            "'Attack on Titan (tên tiếng Nhật: Shingeki no Kyojin) là một bộ truyện tranh nổi tiếng của tác giả Hajime Isayama. " +
            "Câu chuyện diễn ra trong một thế giới nơi con người sống trong những thành trì khổng lồ để tránh khỏi cuộc tấn công " +
            "của những sinh vật khổng lồ có tên là Titan. Eren Yeager và những người bạn của cậu gia nhập quân đội để chiến đấu chống lại Titan và " +
            "khám phá bí mật về thế giới của họ.', " +
            "'................. '," + //Description
            "'Hành động, kinh dị', " +
            "'2023-05-10', " +
            "'2023-05-25', " +
            "'drawable/attack_on_titan.jpg', " +
            "2200, " +
            "'admin')";

    private static final String sql8 = "INSERT INTO ratings VALUES (null,1, 1, 5, 'Truyện rất hay!', 1)";
    private static final String sql9 = "INSERT INTO ratings VALUES (null,2, 2, 4, 'Cốt truyện hấp dẫn!', 0)";
    private static final String sql10 = "INSERT INTO ratings VALUES (null,3   , 3, 3, 'Truyện khá thú vị.', 1)";

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
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);
        db.execSQL(sql10);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }
}

