package com.example.happy_read.database;
import android.content.Context;
import android.database.Cursor;
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
                    COLUMN_STORIES_IMAGE + " TEXT NOT NULL, " +
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
                    COLUMN_RATINGS_RATING + " REAL, " +
                    COLUMN_RATINGS_COMMENT + " TEXT, " +
                    COLUMN_RATINGS_ISFAVORITE + " INTEGER CHECK (" + COLUMN_RATINGS_ISFAVORITE + " IN (0,1)), " +
                    "CONSTRAINT fk_user FOREIGN KEY ("+ COLUMN_RATINGS_USER_NAME +") REFERENCES "+ TABLE_USERS + "("+ COLUMN_USERS_NAME +")," +
                    "CONSTRAINT fk_story FOREIGN KEY ("+ COLUMN_RATINGS_STORY_ID +") REFERENCES "+ TABLE_STORIES + "("+ COLUMN_STORIES_ID +")," +
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
            "'Thiếu nhi', " + //Genre
            "'Doraemon là series manga Nhật Bản được sáng tác bởi họa sĩ Fujiko F. Fujio dành cho thiếu nhi vào năm 1969. Tác phẩm kể về một chú mèo máy từ thế kỉ 22 với những bảo bối thần kì trở về quá khứ giúp cậu bé Nobita cải thiện cuộc sống'," + //Description
            "'2023-10-24', " + //Create_at
            "'2023-11-14', " + //Update_at
            "'drawable/doraemon.jpg', " + //ImageInterView
            "3101, " +//View//UserId
            "'admin')";//UserId
    public static final String sql3 = "INSERT INTO stories VALUES (null," +
            "'Conan', " +
            "'Đang giờ cao điểm buổi sáng, con tàu E531 lèn chặt hành khách đi làm, đi học lao vun vút với tốc độ chóng mặt 130km/giờ để đưa tất cả vào tới trung tâm thành phố.\n" +
            "\n" +
            "Người đàn ông trung niên ngồi ở phía cuối tàu đang chăm chú xem ti vi trên điện thoại, bỗng thốt lên:\n" +
            "\n" +
            "- Này!- Chìa màn hình điện thoại về phía người bạn đang chậm rãi đọc tạp chí ở ghế đá bên cạnh, ông ta nói - Thám tử Kudo Shinichi đã phá xong vụ án sát hại tác giả truyện tranh nổi tiếng rồi!\n" +
            "\n" +
            "Người đàn ông đọc tạp chí liền quay sang nhìn vào màn hình điện thoại, nét mặt tỏ vẻ thán phục. Do người đàn ông nói quá lớn, nên một số vị khách mang theo điện thoại di động có chức năng xem ti vi cũng lập tức lôi điện thoại từ trong cặp, trong túi áo ra thao tác...\n" +
            "\n" +
            "- Trời! Đẹp trai dễ sợ! - Một trong số những nữ sinh mặc đồng phục trường trung học Teitan đứng lẫn trong đám hành khách đông đúc gần cửa ra vào thốt lên ngưỡng mộ. Cô giơ cao chiếc điện thoại di động của mình và đám bạn gái cùng trường ngay lập tức xúm vào màn hình đang chiếu cận cảnh gương mặt của Kudo Shinichi.\n" +
            "\n" +
            "Nhưng đám nam sinh thì lại nhìn cảnh đó với nét mặt đầy ghen tức:\n" +
            "- Hắn có gì hay ho mà lúc, nào cũng được các cô em thần tượng thế nhỉ?!\n" +
            "\n" +
            "Đám con gái chẳng thèm để ý tới thái độ của lũ bạn trai mà vẫn tiếp tục nhìn như muốn nuốt chửng cái màn hình điện thoại, vì không muốn bỏ lỡ một cử chỉ nhỏ nào của Shinichi.\n" +
            "\n" +
            "Trên màn hình của chiếc điện thoại, Shinichi đang bị bao vây bởi một đám nhà báo, phóng viên.\n" +
            "\n" +
            "- Cậu Kudo, cậu lại lập thêm một chiến công nữa rồi!\n" +
            "- Chỉ vừa mới tháng trước, cậu đã lật tẩy một công ty chuyên nhập lậu.\n" +
            "- Quả thực cậu đúng là đại cao thủ, xứng danh Thám tử thiên tài!\n" +
            "\n" +
            "Đám phóng viên mỗi người một câu đua nhau tán thưởng Shinichi, nhưng nhân vật chính thì lại tỏ vẻ hơi khó chịu.\n" +
            "\n" +
            "-Xin lỗi, tới giờ học của tôi rồi! - Cậu vừa nói vừa cố gắng gạt bọn họ ra để tiến vào trong ngôi trường đề tên \"Trường trung học phổ thông Teitan\".\n" +
            "\n" +
            "Nhưng đám phóng viên nhanh chóng túa ra cản đường, vì nếu để nhân vật của họ thoát vào bên trong trường thì họ sẽ không thể moi thêm tin được nữa.\n" +
            "\n" +
            "- Chỉ một câu thôi, cậu Kudo!\n" +
            "- Đúng! Đúng! Chúng tôi sẽ không làm mất thời giờ của cậu đâu.\n" +
            "\n" +
            "Trong đám nhà báo còn có người chắp tay lạy cậu.\n" +
            "Shinichi đành phải đứng lại, buông tiếng thở dài:\n" +
            "\n" +
            "- Thôi được rồi, mong các vị ngắn gọn cho!\n" +
            "- Được rồi! Vấn đề liên quan đến tên tội phạm của vụ án sát hại tác giả truyện tranh nổi tiếng lần này. Tôi rất bất ngờ khi biết hắn lại chính là tên nhân viên đưa bánh pzza. Và động cơ chỉ là bị tác giả kia từ chối ký nhận bánh? - Có lẽ vì Shinichi vẫn đang là học sinh trung học nên một tay phóng viên trẻ cầm chiếc micro của kênh truyền hình NNT sấn sổ tiến về phía cậu hỏi với vẻ sỗ sàng.\n" +
            "\n" +
            "Nét mặt có vẻ hơi tức giận, Shinichi đáp:\n" +
            "- Tôi đã khẳng định ngay từ đầu với quý vị hắn là hung thủ rồi mà!\n" +
            "\n" +
            "Tay phóng viên giải thích thêm:\n" +
            "- Vâng, cậu có nói vậy, nhưng có tin từ cảnh sát cho hay là họ đang nghi ngờ sát thủ chính là trợ lý của tác giả nổi tiếng đó nên...\n" +
            "- Quan hệ giữa tác giả đó và người trợ lý chắc chắn là rất tốt!\n" +
            "- Vậy ư? Nhưng tại sao...? - Một phóng viên trung niên từ nãy vẫn đứng sau Shinichi gạt những người khác và len lên phía trước, chìa micro về phía Shinichi và hỏi.\n" +
            "- Tôi tin chắc là như vậy, sau khi được xem những tác phẩm của ông tác giả bị sát hại.\n" +
            "- Ồ...\n" +
            "\n" +
            "Nghe Shinichi nói, đám phóng viên nhà báo lập tức lấy bút tốc ký không bỏ sót một câu một chữ nào của cậu.\n" +
            "- Thông thường khi sáng tác truyện tanh, tác giả chỉ vẽ nhân vật, còn trợ lý sẽ đảm nhận phần bối cảnh và các chi tiết nhỏ. Tôi đã được xem bản gốc của các tác phẩm của ông ấy, và chú ý tìm hiểu quan sát rất kỹ càng. Dù ở bất cứ ô hình nào thì bối cảnh và các chi tiết nhỏ cũng được vẽ một cách rất hoàn hảo,chỉn chu và ăn khớp nhau. Nhờ các trang vẽ như vậy mà nhân vật chính của tác giả cũng như toàn bộ câu chuyên mới có sức hấp dẫn đủ để làm nên một tác phẩm thành công đến thế. Tôi nghĩ rằng nếu người trợ lý đó không tôn trọng tác giả, không yêu tác phẩm và nhân vật chính từ tận trái tim thì anh ta không thể làm tốt như vậy được.\n" +
            "- Đúng là vậy! - Đám phóng viên tán thưởng sâu sắc.\n" +
            "- Bối cảnh mà người trợ lý vẽ vào hôm ông họa sĩ bị sát hại cũng không hề thay đổi so với mọi ngày, vẫn rất hoàn hảo. Nếu anh ta đang có ý định sát hại ông họa sĩ ấy thì chắc chắn bản thảo sẽ phải xuất hiện ít nhiều dấu hiệu rối loạn hoặc khủng hoảng đúng không ạ?\n" +
            "- Đúng là vậy đấy! Trong vụ này, thanh tra Megure của đội điều tra số 1, sở cảnh sát cũng phải cảm ơn cậu Kudo lắm đó. Nếu không có sự hợp tác của cuậ Kudo thì họ đã bắt nhầm người trợ lý là điều chắc chắn.\n" +
            "\n" +
            "Câu nói của nữ phóng viên trẻ làm Shinichi nhớ lại gương mặt tròn vo với bộ ria mép của viên thanh tra Megure. Từ sáng sớm ông ta đã gọi điện và không ngớt lời cảm ơn cậu.\n" +
            "- Với tư cách là một thám tử, tôi chỉ làm công việc đúng phận sự của mình, không có gì to tát cả. Nếu vụ án này được thần tượng Sherlock Holmes của tôi giải quyết, thì chắc chỉ cần một nửa thời gian và công sức là có thể phá án xong rồi. Thôi xin phép quý vị ở đây! - Nói xong Shinichi nhanh chóng rút vào trong trường.\n" +
            "- Ôi! Xin hỏi cậu thêm một câu nữa thôi! - Sau lưng Shinichi, các phóng viên thi nhau nằn nì đòi phỏng vấn thêm, nhưng cậu đã nhanh chân thoát vào khuôn viên của trường.\n" +
            "- Mời quý khán giả tiếp tục theo dõi chương trình bình luận tại trường quay. - Màn hình ti vi của chiếc di động chuyển sang cận cảnh gương mặt người dẫn chương trình bình luận có mái tóc bạch kim được tạo kiểu rẽ ngôi 3-7. Và thế là vẻ háo hức chăm chú trên gương mặt đám nữ sinh nãy giờ vẫn ngấu nghiến cái màn hình di động cũng lập tức tắt ngấm.\n" +
            "- Cái cậu Kudo này đúng là mỗi ngày một tài ba hơn đấy nhỉ! - Người đàn ông trung niên theo dõi màn hình ti vi đầu tiên vừa thán phục vừa gấp điện thoại lại.\n" +
            "- Ờ, cậu ấy mà trưởng thành thì còn nhiều vụ hay ho được khám phá đây... Chúng ta cứ chờ xem! Người bạn ngồi bên cạnh gật đầu tán thưởng theo.\n" +
            "\n" +
            "Những hành khách còn lại trên chuyến tàu hầu hết cũng đã tắt màn hình điện thoại di động, chỉ còn một người khách mặc áo khoác đứng trong góc toa tàu là vẫn nhìn chằm chằm vào bức ảnh và đoạn tiểu sử của Kudo Shinichi mà người dẫn chương trình bình luận đang vừa giới thiệu vừa tán dương. Bằng một giọng nhỏ, âm sắc hằn học, anh ta rít lên:\n" +
            "- Kudo Shinichi, mi cứ đắc thắng đi, nhưng mi sẽ nhanh chóng nếm mùi đau khổ vì sự vô dụng của mi, mi sẽ phải chết một cách thảm khốc...\n" +
            "\n" +
            "Giọng của hắn rất nhỏ nên mọi hành khách trong toa tàu không thể nào nghe thấy những lời dị thường ấy',"+
            "'Hành động', " +
            "'Câu chuyện xoay quanh Kudo Shinichi, một thám tử học sinh tài ba bị teo nhỏ thành một đứa bé sau khi bị Tổ Chức Áo Đen đầu độc. Lấy tên Edogawa Conan, Shinichi sống cùng Ran Mori, người bạn thanh mai trúc mã của mình, và hỗ trợ cha cô, thám tử tư Mori Kogoro, phá án trong khi bí mật tìm kiếm thuốc giải.'," + //Description
            "'2023-09-10', " +
            "'2023-09-25', " +
            "'drawable/conan.jpg', " +
            "1500, " +
            "'admin')";

    private static final String sql4 = "INSERT INTO stories VALUES (null," +
            "'One Piece', " +
            "'Đông Hải một tòa tên là Boa tiểu trấn bên trên.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Một tên nam tử chính tại lộ thiên quảng trường, mang theo một đám hài tử tại tập chống đẩy - hít đất.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Karl ca ca, ngươi vì cái gì lợi hại như vậy a, mỗi ngày trôi qua có thể làm hơn một ngàn chống đẩy.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Karl ca ca, chúng ta cũng muốn giống ngài lợi hại, dạng này liền có thể bảo hộ Boa trấn!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Karl ca ca, ta tốt tuyên ngươi. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Các ngươi nếu như giống như ta mỗi ngày rèn luyện lời nói, khẳng định cũng có thể đánh bại hải tặc trở thành tiểu trấn Hero!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl mang trên mặt suất khí tiếu dung, hoàn toàn không có bất kỳ cái gì giá đỡ.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Chung quanh tiểu hài ở chỗ này líu ríu, Karl cũng vui vẻ nhìn thấy bọn hắn như thế ưa thích bản thân.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đột nhiên, một từ điện tử hợp thành thanh âm, tại Karl trong đầu vang lên.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Keng! Tân thủ nhiệm vụ ba triệu chống đẩy đã hoàn thành, Thần cấp lựa chọn hệ thống chính thức mở ra!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nghe được hệ thống mở ra thanh âm, Karl dừng lại rèn luyện, lần nữa nở nụ cười, để Boa trấn nữ hài phương tâm đều hòa tan.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Xuyên qua 5 năm, rốt cục hoàn thành nhiệm vụ này!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Không sai.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl cũng không phải là dân bản địa, mà là một tên người xuyên việt.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng hắn năm năm trước xuyên qua thời điểm, liền phiêu lưu trên biển cả.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nếu như không phải Boa trấn người cứu hắn, chỉ sợ hắn sẽ trở thành thảm nhất người xuyên việt, bắt đầu liền bị biển cả chết đuối.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cùng với những cái khác người xuyên việt, Karl sau khi xuyên việt cũng tương tự thu hoạch được hệ thống.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng là hệ thống lại nói cho hắn biết, nhất định phải hoàn thành tân thủ thí luyện lựa chọn nhiệm vụ, mới có thể chính thức mở ra Thần cấp lựa chọn hệ thống.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cho nên Karl lựa chọn ba triệu chống đẩy, hao tổn thì 5 năm mới hoàn thành nhiệm vụ này.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Mặc dù nói còn có cái khác hai lựa chọn, theo thứ tự là trọng sinh trở thành nữ hài, cùng ngẫu nhiên mất đến một hạng thân thể công năng. . .\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cho nên căn bản không được chọn. . .\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tốt tại ba triệu chống đẩy, cũng cường kiện Karl thể phách, để hắn trợ giúp Boa trấn đánh lui không có treo giải thưởng hải tặc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Từ đó về sau, Karl tại trong trấn liền là đại hồng nhân.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đồng thời Karl tại mấy năm này cũng giải được, nơi này chính là thế giới One Piece, cái này khiến Karl kinh hãi cùng lúc, rèn luyện càng thêm chăm chỉ!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tại dạng này một tràn ngập quái vật thế giới, nếu như không có nhất định thực lực, ngay cả tự vệ đều làm không được!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Tiếp xuống liền để ta nghiên cứu một chút, cái hệ thống này làm như thế nào dùng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thời gian dài như vậy mới mở ra hệ thống, hẳn là dùng rất tốt!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nói xong Karl mở ra hệ thống bảng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng là cái hệ thống này trừ người giao diện thuộc tính về sau, cũng không có những chức năng khác, cái này khiến Karl có chút kinh ngạc, cho nên hắn chỉ có thể mở ra bản thân giao diện thuộc tính lại nói.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Giao diện thuộc tính:\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tính danh: Karl\n" +
            "\n" +
            "\n" +
            "\n" +
            "Lực lượng: F+\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhanh nhẹn: f -\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tinh thần: G+\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thể lực: E -\n" +
            "\n" +
            "\n" +
            "\n" +
            "Năng lực: Quân Thể Quyền (G )\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ghi chú: Phổ thông trưởng thành nam tính thuộc tính là G -!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl quất quất khóe miệng, hắn không nghĩ tới tự rèn luyện nhiều năm như vậy, thuộc tính vậy mà cũng liền so phổ thông trưởng thành nam tính, cao một chút mà thôi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Về phần Quân Thể Quyền, con này là hắn kiếp trước học qua một điểm phòng thân kỹ xảo mà thôi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Người tới đây mau, bến cảng xuất hiện một chân gãy người, chính tại bến cảng nháo sự, nhanh đi qua hổ trợ a!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đột nhiên, thành trấn tự vệ binh người hô một tiếng, Boa trấn tất cả trưởng thành nam tính, nhao nhao cầm lấy nông cụ xông lên đến.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Loại chuyện này Karl đã tập mãi thành thói quen.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Dù sao luôn có gây chuyện thị phi hải tặc đến tìm sự tình.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng Boa trấn chỗ vắng vẻ, có treo giải thưởng hải tặc căn bản khinh thường đến loại địa phương nhỏ này.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cho nên bọn hắn sinh hoạt cũng coi như an nhàn.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl cứ như vậy chậm rãi đi qua đến, nhìn thấy một quen thuộc bóng người màu vàng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thế nhưng là đột nhiên, hệ thống thanh âm vang lên, để hắn vì đó sững sờ.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Keng! Phát hiện sắp chết Sư Tử Vàng, Thần cấp lựa chọn hệ thống đã phát động!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tuyển hạng một: Hiệp trợ Sư Tử Vàng cướp đoạt tiểu trấn, thu hoạch được Sư Tử Vàng thưởng thức, trở thành hắn thủ hạ được tin tưởng nhất cùng đồ đệ!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tuyển hạng hai: Bỏ mặc không quan tâm, để hắn cùng tiểu trấn cư dân tự sinh tự diệt, thu hoạch được xưng hào lạnh lùng vô tình, xưng hào thuộc tính là tự thân toàn thuộc tính gia tăng hai trăm phần trăm!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tuyển hạng ba: Đánh giết Sư Tử Vàng, ngẫu nhiên thu hoạch được Sư Tử Vàng trên thân bộ phận năng lực!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Vậy mà lại là Sư Tử Vàng Shiki!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl không nghĩ tới, tạo thành bến cảng rối loạn người, vậy mà lại là như thế một truyền thuyết nhân vật.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng là hệ thống phía trên cũng viết, Sư Tử Vàng đã sắp gặp tử vong, hiện mang theo chỗ bến cảng, hẳn là cũng chỉ là tại làm giãy dụa tự cứu mà thôi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Đây cũng là ta một cái cơ hội!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl phi thường rõ ràng, mấy tuyển hạng trọng yếu bực nào!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng là không đợi hắn làm ra quyết định, bến cảng bên kia lần nữa bộc phát rối loạn.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Hắn rõ ràng nhìn thấy, có thật nhiều Boa trấn cư dân, bị một cỗ cường đại khí lưu cuốn lên, sau đó rơi trên mặt đất.\n" +
            "\n" +
            "\n" +
            "\n" +
            "rất hiển nhiên là Sư Tử Vàng trái cây năng lực!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Khụ khụ, các ngươi nơi này phế vật đều cho lão tử nghe, lão tử thế nhưng là Sư Tử Vàng Shiki, là mảnh này trên biển đại hải tặc!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ta hiện tại mệnh lệnh các ngươi, tìm cho ta tốt nhất thuốc, thuyền tốt nhất, còn có tốt nhất thợ sửa thuyền!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Nếu không lời nói, khục, khụ khụ, nếu không các ngươi tất cả mọi người muốn chết!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Sư Tử Vàng nói chuyện đứt quãng, thì không thì ho khan hai tiếng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thậm chí mỗi một lần ho khan, đều sẽ ho ra máu tươi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Điều này đại biểu lấy hắn đã là cường nỗ chi chưa, căn bản chống đỡ không bao lâu!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đồng thời Karl cũng nghe đến hắn lời nói.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cho nên không chút do dự lựa chọn ba!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Keng! Chúc mừng chủ kí sinh thu hoạch được Sư Tử Vàng bộ phận năng lực!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cảm thụ được trên tay lưu động khí lưu, chính là Fuwa Fuwa no Mi năng lực, Karl khóe miệng khẽ nhếch.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nếu không phải là xuyên qua mới bắt đầu là những cư dân này cứu mình, ta đã sớm chết!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl ở trong lòng nhắc tới một câu, sau đó sải bước đi đến đến.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Tất cả mọi người tránh ra cho ta, hải tặc giao cho ta xử lý!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Là Karl đại nhân!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Karl đại nhân đến, nhất định có thể tốt tốt giáo huấn hắn!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Karl ngươi cẩn thận một chút a, gia hỏa này rất quỷ dị, hắn vậy mà có thể đem người thổi bay, thật thật đáng sợ!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Chung quanh cư dân, bởi vì Sư Tử Vàng hung ác biểu lộ, còn có hắn vừa rồi lộ cái kia một tay bị hù dọa, căn bản không dám đến gần.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng là Karl rất rõ ràng, này thì Sư Tử Vàng, bất quá là cường nỗ chi chưa mà thôi, không chịu nổi một kích!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Sư Tử Vàng Shiki, trong truyền thuyết đại hải tặc, vì thoát đi Impel Down thậm chí không tiếc tự đoạn hai chân, vậy mà bây giờ lại rơi xuống lần này ruộng đồng, thật sự là đáng thương!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl trong mắt tràn ngập xem thường, mặc dù nói Sư Tử Vàng là bên trên thời đại đại hải tặc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng hắn hiện tại đã sắp gặp tử vong, hoàn toàn không có bất kỳ cái gì uy hiếp!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Nếu không phải là cái kia nhóc mũ rơm? Ta có thể rơi xuống lần này ruộng đồng?\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Khụ khụ, tiểu tử đã ngươi nhận biết ta, vậy thì nhanh lên cứu ta đừng nói nhảm! Chỉ cần ngươi có thể cứu ta, về sau ngươi chính là Phi Không Hải Tặc Đoàn phó thuyền trưởng!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Thật rất mê người. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl cười cười, đi đến Sư Tử Vàng trước mặt, cầm lấy Kogarashi cùng Oto.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ngươi muốn làm gì!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Sư Tử Vàng trừng lớn hai mắt, hai tay vung lên.\n" +
            "\n" +
            "\n" +
            "\n" +
            "To lớn Tatsumaki đằng không mà lên!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Karl thì là cười cười, đồng dạng phóng thích đến từ Sư Tử Vàng trên thân năng lực!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Kogarashi cùng Oto không trở ngại chút nào xuyên qua Sư Tử Vàng thân thể!\n'," +
            "'Phiêu lưu', " +
            "'One Piece là một bộ manga và anime dài tập được sáng tác bởi Eiichiro Oda. Câu chuyện xoay quanh cuộc phiêu lưu của Monkey D. Luffy, một thanh niên cướp biển với chiếc mũ rơm có khả năng co giãn cơ thể như cao su sau khi vô tình ăn trái ác quỷ. '," + //Description
            "'2023-08-05', " +
            "'2023-08-20', " +
            "'drawable/one_piece.jpg', " +
            "2500, " +
            "'admin')";

    private static final String sql5 = "INSERT INTO stories VALUES (null," +
            "'Naruto', " +
            "'Konoha 128 năm, cách Thủy Quốc trăm dặm một toà đảo nhỏ vô danh.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Sơn động nơi sâu xa, ánh nến u ám.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Trên giường đá, nằm một cái tóc trắng loang lổ, mặt mũi nhăn nheo nam nhân, hắn sinh mệnh sắp đi tới phần cuối.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nếu không là cặp kia màu xanh biếc con ngươi, còn có hai lúm đồng tiền đối xứng ba đạo chòm râu, ai cũng sẽ không tin tưởng, hắn là năm đó Đệ thất Hokage, Uzumaki Naruto!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đời này của hắn, ở trong sự ngột ngạt phấn khởi, đi về phía huy hoàng, cuối cùng quy về thảm đạm.\n" +
            "\n" +
            "\n" +
            "\n" +
            "12 tuổi Ninja trường học tốt nghiệp, trở thành Konoha hạ nhẫn.\n" +
            "\n" +
            "\n" +
            "\n" +
            "16 tuổi đánh bại Pain, trở thành Konoha anh hùng, cùng năm tham gia lần thứ bốn Nhẫn giới đại chiến, cùng Uchiha Sasuke liên thủ phong ấn Kaguya, vì là Nhẫn giới mang đến hòa bình.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thế nhưng, Nhẫn giới hạo kiếp, cũng không có theo phong ấn Kaguya mà kết thúc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ở cái kia sau khi, Kinshiki, Momoshiki, Urashiki, Isshiki. . . Lần lượt gợi ra chiến sự!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ở cùng Otsutsuki Isshiki quyết đấu bên trong, Naruto mất đi Cửu Vĩ, Sasuke mất đi Rinegan.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bất đắc dĩ, bọn họ đem Nhẫn giới tương lai, giao phó Boruto làm đại biểu thế hệ mới.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Boruto không phụ sự mong đợi của mọi người, rèn luyện trưởng thành sau, dựa vào Jogan cùng Momoshiki Otsutsuki lực lượng, một lần siêu việt thời điểm toàn thịnh phụ thân, hóa giải nhiều lần Nhẫn giới nguy cơ, vì là Nhẫn giới mang đến mới phồn vinh.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nhưng này phồn vinh, theo Otsutsuki Shinshiki kéo tới, biến thành tro bụi!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Shinshiki có mang tính áp đảo sức mạnh, so với đã từng Kaguya, còn cường đại hơn mấy lần!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Trận chiến đó, Nhẫn giới tập kết toàn bộ sức mạnh đón đánh, cuối cùng khốc liệt kết cuộc!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bạn thân, thê tử, hài tử, đồng bạn. . . Tất cả chết trận!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bọn họ trước khi chết kêu gào, ở sau này vô số ngày đêm, vang vọng ở Naruto đầu óc, dằn vặt hắn thống khổ tâm linh!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Naruto, chúng ta sức mạnh, không có tác dụng. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Naruto *kun, thỉnh sống tiếp, xin tha thứ ta, không thể tiếp tục cùng ngươi tiếp tục đi. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Phụ thân, ta đã. . . Tận lực!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Đệ thất đại nhân, thỉnh mau mau lui lại! Ngài là chúng ta hy vọng cuối cùng! ! !\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đại chiến cuối cùng, chiến đến lực kiệt Naruto, bị Vân Ẩn chúng dùng bí thuật đưa đi, làm hắn sau khi tỉnh lại, kẻ địch đã rời đi, lưu lại hóa thành đất khô cằn Nhẫn giới đại địa!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto quỳ xuống đất khóc lóc đau khổ, chảy khô nước mắt, bắt đầu lưu vong cuộc đời.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Hắn nghĩ tới làm những gì, có thể tuổi xế triều hắn, từ lâu lực bất tòng tâm!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tầm thường đến nay, rốt cục muốn cáo biệt cái thế giới này.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Giá cắm nến bên, bày ra một con tạo hình cổ xưa rùa đen, đó là Otsutsuki bộ tộc, tên là Lê thời gian qua lại như con thoi bảo cụ, do năm đó Urashiki mang đến, sau nhiều lần trằn trọc, đến Naruto trong tay.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Chỉ là này Lê, đã hỏng rồi, không chỉ mất đi xuyên qua thời gian công hiệu, thậm chí ngay cả mở miệng nói chuyện đều không làm được.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto lão đến không bạn, này Lê là hắn duy nhất có thể nói hết nội tâm đối tượng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Nột, ngươi biết không, A Quy, ta này một đời lớn nhất sai lầm, chính là đánh giá thấp bộ tộc kia sức mạnh. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ở cùng Otsutsuki giao chiến bên trong, Naruto biết rồi tàn khốc chân tướng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ở khoảng cách Nhẫn giới tinh cầu cực kỳ xa xôi trong trời sao ngoài vũ trụ, tên là Takamagahara tinh cầu, chính là Otsutsuki bộ tộc khởi nguồn chi địa!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Takamagahara lên, so với Kaguya còn cường đại hơn tồn tại, chỗ nào cũng có!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Năm đó chúng ta đồng lòng hợp lực, thành công phong ấn Kaguya, sau khi đánh bại Momoshiki, ta liền vào trước là chủ cho rằng, kẻ địch có điều là trình độ đó mà thôi. . . Ta quá ngây thơ, càng cho rằng chỉ cần mọi người đồng lòng hợp lực, liền có thể đánh bại những kia thiên ngoại chi địch.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Chúng ta nhân loại nha, từ vừa mới bắt đầu liền thua ở hàng bắt đầu lên, sự thực chứng minh, Lục Đạo Tiên Nhân con đường, cũng không phải là hoàn toàn chính xác, năm đó Kaguya e ngại sự tình, tất cả đều ứng nghiệm, chung quy là chúng ta quá thiển cận. . . Khụ khụ khụ!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nói đến đây, Naruto ồ ồ ho khan lên, trước mắt tầm mắt, càng thêm mơ hồ.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ánh nến ở tắt biên giới nhào tạo, thời gian, đã không nhiều.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Lúc này, yên lặng nhiều năm Lê, càng mở miệng nói chuyện: \"Nếu như có thể làm lại, ngươi sẽ tìm bọn họ báo thù sao?\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto chỉ nói là chính mình trước khi chết xuất hiện nghe nhầm, cũng không thèm để ý.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ánh mắt của hắn bình tĩnh, lời nói tang thương: \"Báo thù, chỉ có thể thu nhận căm hận mắc xích, ta sẽ không đi tới con đường kia, nhưng nếu nếu thật có thể một lần nữa đã đến, ta không tiếc bất cứ giá nào, cũng muốn chiếm được đủ để chống lại những tên kia đắc lực lượng, chỉ có như vậy, mới có thể chân chính bảo vệ ta người trọng yếu nhất nhóm. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Lê trầm mặc chốc lát: \"Không tiếc bất cứ giá nào, này không giống như là ngươi có thể nói ra a.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto con mắt, chậm rãi nhắm lại, khóe miệng hiện lên thê lương mỉm cười: \"Cái kia hồn nhiên ngây thơ, chỉ có thể không nói lý tưởng Uzumaki Naruto, từ lúc nước mất nhà tan ngày ấy, cũng đã chết đây. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Lê trầm mặc rất lâu: \"Có lẽ. . . Ta có thể thỏa mãn tâm nguyện của ngươi.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto hắn lẩm bẩm: \"Ngươi có thế để cho ta trở lại quá khứ sao?\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Lê nói: \"Chỉ truyền tống linh hồn là có thể, có điều là một chuyến, ngươi đem sẽ không về được nữa, vĩnh viễn lưu ở thời đại kia, dù vậy, ngươi cũng đồng ý sao?\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto cười nói: \"Loại này ai cũng không ở thế giới, ta còn về tới làm cái gì đây. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Lê nói: \"Cái kia đưa ngươi tay, duỗi đến đây đi.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto dùng hắn cuối cùng khí lực, run rẩy đưa tay tới. . . Xèo! Trong cơ thể hắn còn sót lại cuối cùng một tia Chakra, bị Lê hấp thu lấy!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Vù!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bảo cụ khởi động!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto linh hồn, rời khỏi thân thể, theo dòng lũ thời gian, trở lại quá khứ!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Xèo!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ánh nến tắt. . .\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cái này thời gian Naruto, vĩnh viễn nhắm hai mắt lại.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Răng rắc! Răng rắc!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Vết nứt trải rộng Lê thân thể, nó sắp triệt để phá huỷ. . .\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ta ở thời gian lữ hành bên trong, chứng kiến vô số, Uzumaki Naruto, nếu như có ai có thể ngăn cản bộ tộc kia hung ác, cũng chỉ có ngươi, Đông Sơn tái khởi đi, ở một cái không còn sớm cũng không muộn thế giới tuyến lên. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto cảm giác mình ý thức ở đường hầm trung hạ trầm. . . Làm hắn mở mắt lần nữa thời điểm, ở một cái màu xám phong kín không gian bên trong!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Trong tay, chính nắm một quyển sách, đó là Jiraiya sáng tác ( kiên cường nghị lực nhẫn truyền )!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Trước mắt hắn cơ quan xe bên trong, là gầy trơ xương Nagato, Konan đứng ở một bên!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Xảy ra chuyện gì, đây là mộng à! ?\n" +
            "\n" +
            "\n" +
            "\n" +
            "Giờ khắc này, Naruto cả người đều là lờ mờ trạng thái. . . Hắn dùng sức bấm một cái chân của mình, đau đớn nhường hắn kinh giác.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Này không phải là mộng! !\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nagato dùng bi thương giọng điệu, kể ra: \"Đổi nhân vật chính, liền biến thành cái khác cố sự. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ta là sư huynh, là sư xuất đồng môn, cộng mộ tôn sư người, ta trước nói qua, chúng ta nên có thể hiểu nhau.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Tuy rằng cái kia nguyên bản chỉ là đang nói đùa.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ngươi thực sự là cái khó mà tin nổi gia hỏa.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Không khỏi nhường ta nghĩ tới nguyên lai ta. . . !\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Nagato. . .\" Khác thường ngữ khí, nhường Konan ý thức được cái gì.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ta không thể tin tưởng Jiraiya.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Không, ta cũng không thể nào tin nổi chính ta. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Thế nhưng. . . Ta linh cảm đến, ngươi ta đi con đường, sẽ có khác biệt tương lai!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ta liền thử tin tưởng ngươi đi. . . Uzumaki Naruto!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tiếng nói rơi. . . Nagato đem hai tay, từ sền sệt cố định mang bên trong rút ra, kết ra cái kia chấn động pháp ấn!\n" +
            "\n" +
            "\n" +
            "\n" +
            "—— ngoại đạo Luân Hồi Thiên Sinh Chi Thuật!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Konan kinh ngạc thốt lên: \"Nagato. . . Ngươi!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nagato kiên quyết nói: \"Konan, đủ, ta có mới lựa chọn! Đã từng bị ta từ bỏ lựa chọn!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto bỗng nhiên phục hồi tinh thần lại: \"Đây là. . . ! ?\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Konan cho rằng Naruto biểu đạt với trước mắt thuật pháp nghi vấn, toại nói: \"Nắm giữ Rinegan người, không chỉ có thể điều khiển Pain Lục Đạo, truyền thuyết còn có thể thoát ly sinh tử luân hồi thế giới, Nagato đồng lực, là điều khiển sinh tử thuật, người thứ bảy Pain!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ta biết. . .\" Naruto nắm chặt bàn tay, hắn biết là, chính mình trước khi chết nghe được âm thanh, cũng không phải là ảo giác, mà là Lê lời nói!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ta đã trở về, ta thật sự trở về, ta linh hồn trở lại đánh bại Pain sau khi, cùng Nagato đàm phán thời khắc!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Kích động tâm hầu như muốn nhảy ra cuống họng, tay run rẩy không chỗ sắp đặt, bìa sách lên, ngâm đầy hắn mồ hôi!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Trước mắt Naruto, vẫn là Naruto, nhưng linh hồn nhưng là từ tương lai trở về Naruto!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đối với những này, Konan đương nhiên không thể nào biết, nàng lo lắng mà nhìn Nagato: Lấy Nagato hiện tại Chakra sử dụng thuật này, lập tức liền sẽ. . . Không tiếc làm đến nước này, cũng phải giúp trợ đứa nhỏ này.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nàng ngược lại nhìn về phía Naruto, nội tâm cảm thán: Thay đổi Nagato, khó mà tin nổi hài tử.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ngoại đạo. . . ! !\" Nagato kết ấn tay rung động, bạo mồ hôi từ hắn đá lởm chởm thân thể thượng lưu chảy, sức sống cùng Chakra cực hạn tiêu hao, nhường hắn Uzumaki bộ tộc cái kia mang tính tiêu chí biểu trưng Tóc Đỏ, đều biến thành thê lương màu trắng!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thi thuật xong xuôi. . . Nagato kết ấn tay vô lực buông xuống: \"Tạm thời. . . Xem như là bé nhỏ không đáng kể chuộc tội.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Rì rào tốc!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Giấu ở Naruto trong quần áo con sên nhỏ bò đi ra, đối với Naruto nói: \"Người trong thôn đang không ngừng phục sinh. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"A. . . Ta rõ ràng.\" Hết thảy trước mắt, đều là Naruto năm đó thiết thân trải qua, dấu ấn ở ký ức nơi sâu xa nhất!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Chỉ là. . . Tâm tình, đã tuyệt nhiên không giống!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nagato hơi thở mong manh kể ra cuối cùng lời nói.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Chiến tranh là nương theo song phương tử thương.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Càng là trọng yếu người tử vong, càng khó lấy tiếp thu.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Chính mình cảm thấy trọng yếu người kỳ thực có thể không cần chết.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Đặc biệt là không biết chiến tranh các ngươi này thế hệ, càng là không có cách nào. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Muốn tìm ra tử vong ý nghĩa.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Kết quả chỉ có đau đớn, cùng với không biết nên tới đâu phát tiết căm hận.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Như giun dế như thế tử vong. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Vĩnh viễn kéo dài cừu hận, cùng vĩnh viễn không bao giờ khỏi hẳn đau đớn. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Vậy thì là chiến tranh.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Naruto, cái này cũng là ngươi tiếp đó, phải đi đối mặt đồ vật. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Xem ra sứ mạng của ta chấm dứt ở đây, Naruto. . . Nếu như là ngươi, nói không chắc thật sự có thể.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ào ào ào. . . Do thuật pháp ngụy trang thành đại thụ rải rác thành trang giấy.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto mi mắt cúi thấp xuống, đem khuôn mặt chôn sâu ở trong bóng tối.\n" +
            "\n" +
            "\n" +
            "\n" +
            "A, ta rõ ràng, sư huynh. . . Ngươi hôm nay nói tới tất cả, ở ngày sau ta đều trải qua, hơn nữa, ghi lòng tạc dạ!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ngươi đau đớn. . . Ta so với khi đó cảm thụ càng thêm rõ ràng!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Vì lẽ đó, ta lần này trở về, nhất định phải thay đổi cái kia tất cả!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nagato nhếch miệng lên: \"Bất luận là sách vẫn là ngươi, đều cảm giác đây là có người vì ta an bài xong, không, này mới chính thức là thần sắp xếp à. . .\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Konan vẻ mặt chán nản duỗi ra tay áo bào. . . Bạch! Trang giấy cuốn về Nagato di thể.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bạch!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\". . .\" Naruto hít sâu một hơi, đột nhiên, hắn đem trên vai con sên nhỏ nắm lên, bàn tay dùng sức một nắm.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đùng!\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Nha! !\" Con sên nhỏ. . . Biến mất không còn tăm hơi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tình cảnh này, ra ngoài Konan dự liệu: \"Naruto, ngươi?\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Naruto trầm giọng nói: \"Sau đó đối thoại, không thích hợp khiến người nghe thấy, Konan sư tỷ, vì cái này Ninja thế giới tương lai, ta muốn kế thừa Nagato Rinegan!\"'," +
            "'Phiêu lưu', " +
            "'Naruto là một bộ manga và anime nổi tiếng được sáng tác bởi Masashi Kishimoto. Câu chuyện xoay quanh Uzumaki Naruto, một ninja trẻ tuổi luôn bị xa lánh và hắt hủi bởi dân làng vì mang trong mình Cửu Vĩ Hồ, một con cáo chín đuôi hung dữ đã tấn công làng Lá trong quá khứ.'," + //Description
            "'2023-07-15', " +
            "'2023-07-30', " +
            "'drawable/naruto.webp', " +
            "2000, " +
            "'admin')";

    private static final String sql6 = "INSERT INTO stories VALUES (null, " +
            "'Dragon Ball', " +
            "'là 2015 niên thượng thành phố một cái cách đấu thi đấu thể thao loại trò chơi, trò chơi này đối với Long Châu mê mà nói là một cái khiến người ta muốn ngừng mà không được trò chơi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bên trong ngươi có thể sáng tạo năm khác biệt chủng tộc nhân vật, người địa cầu, người Xayda, người Namek, Ma nhân, Fide bộ tộc, hơn nữa ngươi còn có thể sáng tạo nhiều chức nghiệp, tuy là chỉ có năm chức nghiệp, thế nhưng người địa cầu, người Xayda cùng Ma nhân là phân giới tính, hơn nữa giới tính bất đồng năng lực cũng theo bất đồng. Người Namek cùng Fide bộ tộc là không có có phân biệt giới tính , người Namek là dựa vào trong miệng thổ đản sinh sôi nảy nở hậu đại, mà Fide bộ tộc là dựa vào vô tính sinh sôi nẩy nở (ta chỉ muốn biết, bọn họ rốt cuộc là làm sao vô tính sinh sôi nẩy nở, chính mình **(C Ao ) chính mình sao. )\n" +
            "\n" +
            "\n" +
            "\n" +
            "Một vị thanh niên thân cao một Mễ Bát nhiều, hình thể thon dài, tới lúc gấp rút vội vàng đi về phía trước, hắn gọi Pieca.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca là một gã 27 tuổi giáo viên thể dục, ở trường bị học sinh trở thành Pikachu lão sư, vì thế nhiều lần bị học sinh khiến cho không muốn không muốn . Hắn vẫn một gã trung thực Long Châu Fan S, bởi khi còn bé mê luyến , cho là mình miễn là học giỏi võ thuật có thể cùng Sôn Gôku giống nhau lên trời xuống đất, một quyền đánh bể một viên hành tinh, cho nên tiểu học trong lúc báo Taekwondo học tập, sau lại lại nghe theo bạn tốt ý kiến bỏ qua Taekwondo, gia nhập Tiệt Quyền Đạo. Pieca là một cái hiếu học người, Tiệt Quyền Đạo ý chính là \"Dĩ vô pháp vì có pháp, lấy vô hạn vì hữu hạn\" nếu như dùng võ Hiệp trong tiểu thuyết võ công mà nói, Tiệt Quyền Đạo thì tương đương với .\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bởi vì từ nhỏ đúc luyện, cho nên Pieca cái này nhân loại mặc quần áo cùng người bình thường hình thể không sai biệt lắm, thế nhưng trên người của hắn bắp thịt lại tràn đầy sức bật.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca sau khi tốt nghiệp đại học, người nhà nâng cửa sau để hắn tiến nhập thành phố Nhất Trung làm một gã vinh quang nhân dân giáo sư, mặc dù là giáo thể dục .\n" +
            "\n" +
            "\n" +
            "\n" +
            "Bởi vì Pieca cái này nhân loại ngoại hình ánh mặt trời, nhiều năm đúc luyện để hắn góc cạnh rõ ràng, ở trường học bị các yêu thích, cho nên cả người ở trường học nổi tiếng cũng là rất cao.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Hôm nay lại PC bưng lên ban bố (chú: Đã phá giải . ), biết được tin tức này, hắn một cái tiểu đội vội vội vàng vàng mở cùng với chính mình xe xài rồi hướng trong nhà chạy băng băng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đẩy cửa phòng ra, trong phòng phổ thông, trên tường dán mấy Trương Long trong châu Sôn Gôku biến thành Super Saiyan bộ dạng, còn có một Trương Lý con rắn sắm vai Trần Chân một cước đá nát năm khối mộc bản áp-phích, còn lại nên cái gì cũng không có.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Tấm tắc... Rốt cuộc có thể chơi, để ta đợi thời gian dài như vậy, sớm biết liền mua PS . \" Pieca lầm bầm nói, PS không phải hắn không muốn mua, chỉ là hắn bây giờ tiền lương một bộ phận lớn được với giao phụ mẫu, tuyên bố vì hắn tương lai lấy vợ sinh con dùng, còn dư lại chỉ đủ hắn xe có rèm che nỗ lực lên phí cùng mình sinh hoạt phí, hắn Computer đều là mình nhịn ăn nhịn xài, phí hết thời gian dài mới(chỉ có) để dành được một Đài Trung các loại(chờ) phối trí máy vi tính tiền.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca nhìn mây ở trên kế tiếp tốc độ, mỗi giây 2 M, xem ra còn phải lại chờ hai giờ, \"Xem một chút tiểu thuyết a !. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca mở điện thoại di động lên ở trên khởi điểm Hệ Thống, đăng nhập đi vào mở ra chính mình đang ở truy canh mấy quyển tiểu thuyết. Nhìn xong, phát hiện mới(chỉ có) qua mấy phút, \"Á đù, thời gian làm sao sống làm sao chậm. \" Pieca nhìn cái nào kế tiếp tốc độ, cả người đều nóng nảy không muốn không muốn .\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ta nên làm những thứ gì đâu?\" Pieca nhanh chóng run rẩy lấy chân, nhàm chán nghĩ, thuận tay đốt lên một điếu thuốc, lung tung không có mục đích ngồi ở Computer ghế a ! Tạp ba đập hút hương yên.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Rốt cuộc, dài dòng hai giờ quá khứ, Pieca mở ra kế tiếp xong áp súc văn kiện giáp, chừng mười phút đồng hồ 8 cái nhiềuG trò chơi liền giải áp hoàn thành.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Hắc hắc...\" Pieca nhìn giải áp tốt trò chơi cười ngây ngô đứng lên, thế nhưng động tác trên tay lại tuyệt không chậm, tìm ra trò chơi đồ tiêu mở ra trò chơi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Một hồi quen thuộc giai điệu vờn quanh ở Pieca bên trong phòng ngủ.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Hai phút phía sau, Pieca dùng Sôn Gôku SS 1 cùng SS 3 hình thái phân biệt đánh bại Fide, Cell, MaBư. Đánh xong phía sau lại tiến hành rồi một lần đầu phim khúc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thế nhưng Pieca nhưng không có chú ý tới vốn là có điểm mờ tối bầu trời trong nháy mắt biến thành mây đen rậm rạp, điện Thiểm Lôi minh, một cái như tia chớp sinh vật lại ô Vân Trung xoay quanh, trên mặt đất người chỉ là cho rằng bầu trời sôi trào là một đạo thiểm điện mà thôi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Computer giao diện biến thành bạch sắc, màn ảnh máy vi tính cũng xuất hiện một cái khung đối thoại, Thần Long thanh âm hùng hậu nói ra:\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Là có người đem ngươi từ xa xôi địa phương triệu hoán mà đến...\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Dũng cảm chiến sĩ a! Tiến lên đây!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thần Long nói xong mấy câu nói đó đi thẳng đến tuyển trạch nhân vật giao diện, Pieca liền còn lại vai tuồng giới thiệu cũng không nhìn trực tiếp lựa chọn người Xayda, \"Chơi cái trò chơi này nhất định phải chọn người Xayda , những nhân vật khác đến lúc đó lại nói. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Ngay sau đó là nhân vật thiết định, hắn đem chính mình nhân vật thiết định hơi đẹp trai khí kiểu tóc thiết định thành Vegetto kiểu tóc (chú: Các vị biểu lưu ý, bên trong nguyên sang kiểu tóc quá khó coi, các ngươi tựu xem như đánh mụn vá đi. ) phục sức chỉnh thể là màu lam, trừ quần áo ra bên trong lưng là màu đen, giầy cùng Gôku giầy không sai biệt lắm.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Sau đó Pieca nổi lênKAKA SHI tên (dùng Trung văn đọc lên tới chính là Kakashi lạp! Còn có Kakashi chân diện mục lộ ra rồi! ! Một đoạn này chính là ác cảo, về sau vẫn sẽ dùng Pieca . )\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca điểm kích xác định, bên tai truyền đến một đạo thanh âm hùng hậu: \"Nguyện vọng của ngươi đã thực hiện. \" những lời này nói xong, Pieca thân thể hóa thành một đạo hào quang năm màu hướng chân trời vọt tới.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Làm Pieca lần nữa mở con mắt phát hiện mình đến rồi một cái không biết địa phương, bốn phía có chút Trung quốc cổ kiến trúc, còn có một chút khoa học viễn tưởng kiến trúc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca mờ mịt nhìn chung quanh, vừa nghiêng đầu, miệng há đại dường như thấy được kinh khủng đồ đạc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cái này con mẹ nó không phải Thần Long sao? Đây là chuyện gì xảy ra? Pieca trong lòng nhanh chóng lướt qua một cái ý niệm trong đầu.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Chào tạm biệt...\" Thần Long nhìn chằm chằm trước mắt triệu hoán mà đến chiến sĩ, nhíu mày một cái, trong nháy mắt hóa thành một vệt sáng biến mất ở phía chân trời.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Cộc cộc cộc...\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca nghe phía sau mình có người hướng về chào tạm biệt đi tới, nghiêng đầu qua chỗ khác thấy một cái thanh niên tóc tím, người xuyên một bộ áo gió, cõng ở sau lưng một thanh kiếm.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thanh niên tóc tím đối với Pieca nói ra: \"Ta muốn, ngươi nhất định cực kỳ kinh ngạc a !, ta như thế này lại hướng ngươi giải thích, hiện tại...\" Tay trái cầm đeo ở sau lưng trên chuôi kiếm, chậm rãi đem bạt kiếm ra, \"Trước hết để cho ta nhìn ngươi một chút thực lực...\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ấy da da!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thanh niên tóc tím nổi giận gầm lên một tiếng, cầm kiếm ra hướng về Pieca phóng đi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Cái gì, Uy, ngươi ở đây làm cái gì, dừng tay!\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thanh niên tóc tím rất nhanh chém ra một kiếm, một kiếm này đem Pieca áo khoác phá vỡ, Pieca cúi đầu thấy y phục của mình, Y phục này là... không đợi Pieca phản ứng kịp, thanh niên tóc tím kiếm thứ hai ngay sau đó đánh tới.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca vô ý thức hướng về sau nhảy một bước, trước mắt thanh niên tóc tím đem kiếm lại lần nữa bỏ vào Kiếm Hạp bên trong. Làm ra công kích thức mở đầu.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Uy, ngươi ở đây làm cái gì, vì sao công kích ta, ngươi biết mới vừa một kiếm kia suýt chút nữa thì mạng của ta sao?\" Pieca hướng về phía thanh niên tóc tím rống giận.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thanh niên tóc tím mắt lạnh nhìn Pieca: \"Ta nói, trước hết để cho ta nhìn ngươi một chút thực lực. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nói xong lần nữa nhằm phía Pieca, Pieca còn chưa kịp phản ứng, thanh niên tóc tím một quyền đánh vào Pieca trên mặt, đem Pieca đánh bay.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca trên mặt đất mượt mà lộn vài vòng, quỳ rạp trên mặt đất, cắn răng, hai tay chống chấm đất, chậm rãi đứng lên.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Ách. \" Pieca đem khóe miệng chảy xuống một tia tiên huyết lau, Xem ta thực lực, cũng không cần dưới ác như vậy đắc thủ a !!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Pieca đã đem cái này thế giới làm rõ ràng, thì ra hắn xuyên việt đến rồi Long Châu siêu trong vũ trụ , hơn nữa dường như còn trở thành chính mình thiết lập người, cùng mình tự xây bất đồng duy nhất là bên hông còn có một cái đuôi.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Xem ra ta trở thành người Xayda , nguyên bản ta ngược lại thật ra rất muốn chính mình trở thành người Xayda, thế nhưng ta càng muốn cùng mình gia nhân ở cùng nhau, mặc dù là không lý tưởng, ta cũng nguyện ý, nếu là Thần Long để cho ta xuyên việt đến nơi đây. Như vậy, ta tập tề bảy viên Long Châu, hướng Thần Long ước nguyện, hẳn là có thể trở lại chính mình thế giới a !!Pieca nghĩ tới đây, trong mắt vẻ kiên nghị càng ngày càng dày đặc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"Tốt, ngươi muốn xem thực lực ta, ta sẽ nhường ngươi xem. Như vậy, đến đây đi...\"', " +
            "'Hành động',"+
            "'Câu chuyện kể về hành trình của Son Goku (hay Goku), một cậu bé Saiyan sở hữu sức mạnh siêu phàm, cùng những người bạn chiến đấu chống lại kẻ thù hung ác, bảo vệ Trái Đất và tìm kiếm những viên ngọc rồng huyền bí.'," + //Description
            "'2023-06-20', " +
            "'2023-07-05', " +
            "'drawable/dragon_ball.jpg', " +
            "1800, " +
            "'admin')";

    private static final String sql7 = "INSERT INTO stories VALUES (null, " +
            "'Attack on Titan', " +
            "'\" Tch.\" Trong bóng đêm dài đằng đẵng, thiếu nữ xinh đẹp lơ lửng trên không trung, cô nhăn mày dùng tay xoa xoa đầu mình.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Mái tóc màu đỏ thẫm thật dài phớt nhẹ qua vai, đôi mắt màu đồng nhẹ mở ra, không gian đen tối xung quanh ngay lập tức đập vào mắt cô:\" Chỗ quỷ nào đây? \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" A, kích động ghê, cuối cùng cô cũng đến rồi! \" Tiếng nói vui sướng vang lên, bóng tối sâu thẳm đùng một cái liền biến thành một khung trời bảy sắc cầu vồng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Katherine tặc lưỡi...\n" +
            "\n" +
            "\n" +
            "\n" +
            "Khoa trương!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Khoa trương quá rồi đấy!\n" +
            "\n" +
            "\n" +
            "\n" +
            "Từ xa, có một con vật đi đến, vảy trên người nó sáng lấp lánh, vây và đuôi được trang hoàng vô cùng xinh đẹp.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thiếu nữ giật giật khóe mắt, nhìn nó...thật giống một con cá lồng đèn...\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cá lồng đèn rất nhanh liền bơi không khí tới chỗ cô, trên mặt cá không giấu khỏi nét vui mừng và hạnh phúc, Katherine có cảm giác, hình như nó đang đợi cô chết rồi đến đây đúng không?\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Ngươi là thủy quái à? \" Cô chọt chọt vào vây cá của nó.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Con vật vô cùng thích thú cọ cọ lên tay cô, nhìn đến những vết chai sần kia, nó lập tức tỏ vẻ ghét bỏ tránh né:\" Ta là thú linh của cô nha. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Thú linh? \" Thiếu nữ có chút hứng thú lên tiếng, ánh mắt không khỏi âm thầm thêm vào ý cười khinh miệt:\" Ngươi mà cũng đáng làm thú linh? \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" A...cái con nhóc nhà ngươi, thế mà lại xem thường gia đây, có tin gia đánh ngươi không? \" Cá lồng đèn nhìn thấy ý cười trong mắt cô, thanh âm không khỏi cao thêm mấy phần:\" Ta xuất thân từ Viêm gia, có thể xem như là những thú linh mạnh mẽ nhất ở đây! \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Vậy à, Viêm gia gì đó nuôi ngươi không phải tốn rất nhiều công phu sao, nuôi tới vây vảy đều sáng bóng như vậy. \" Trong mắt đều tràn đầy ý cười, cô khẽ nói.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"...Baba ngươi là trời sinh thú linh! Có thể nói chuyện, còn có thêm khả năng riêng biệt, ai cần người nuôi dưỡng chứ! \" Cá lồng đèn tức giận phản bác, lập tức ra vẻ cao thượng:\" Ngươi xem ngươi xem, có phải gia ngươi rất đẹp trai hay không? \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Katherine có chút không hiểu nổi con cá trước mặt mình, cả gia và baba của cô nó đều muốn đảm nhiệm à? Mặt mũi thật lớn nha.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Không nhịn được sự tò mò dâng lên trong lòng, thiếu nữ mỉm cười tươi tắn:\" Ngươi nói xem, đây là chỗ nào? Ta chết rồi đúng không? \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Ngươi thì quả thực đã chết, còn đây là trang viên của Viêm gia, hiện tại cho dù ngươi không còn sống, nhưng sát nghiệp quá nặng, cần giải thêm một kiếp nữa. \" Nó không nhanh không chậm giải thích tình hình.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Chết rồi không phải nên đi đầu thai sao? \" Cô thường nghe mấy hàng xóm bát quái xung quanh nói như thế.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cá lồng đèn quăng cho Katherine một ánh mắt xem thường, hừ nhẹ một tiếng:\" Sát nghiệp của ngươi quá nặng, vẫn có thể đi đầu thai, nhưng chắc chắn sẽ đi vào súc sinh đạo nha. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Một hơi này khiến thiếu nữ hoàn toàn nín nghẹn...\n" +
            "\n" +
            "\n" +
            "\n" +
            "Haha, đây chắc có lẽ là nghiệp quật trong truyền thuyết, nghiệp nặng quá giờ không đi đầu thai được luôn.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Vậy bây giờ ta phải làm sao? \" Thiếu nữ bất đắc dĩ xoa thái dương, sau khi tiếp thu được hết thảy mọi truyện xảy ra, cô khẽ hỏi lại.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Đương nhiên là đi đến một thế giới khác để giải trừ sát nghiệp cho ngươi, dùng một cách gϊếŧ khác để thay thế.\" Cá lồng đèn nguy hiểm nhe răng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Gϊếŧ động vật sao?\" Thiếu nữ có vẻ không quá vui với việc này, gϊếŧ người bình thường đã khiến cô ghét như vậy, gϊếŧ động vật? Chậc chậc, không biết bản thân sẽ có cảm giác gì.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Ai da, sao ngươi lại có thể lạm sát thú như thế, không tốt không tốt. \" Cá lồng đèn nham hiểm híp mắt, nghiêm túc sửa chữa:\"...Đó là vì dân trừ hại, sau này ngươi sẽ biết. Còn bây giờ, chúng ta phải đi báo danh với quản gia ở nơi này.\"\n" +
            "\n" +
            "\"...Trước đó có thể nhìn thế giới thực sau khi ta chết sẽ như thế nào không? \" Katherine hơi đưa mắt về phía nó.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cá lồng đèn có chút không hiểu nhìn cô, chết rồi mà còn muốn xem làm gì, chỉ khiến bản thân thêm luyến tiếc.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Dù cho suy nghĩ như vậy nhưng cuối cùng nó vẫn gật đầu, coi như tặng quà gặp mặt đi:\" Được, theo ta. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Nói đoạn, cá lồng đèn lắc lắc cái vây ở trong không khí bơi đi, nếu để ý kĩ, hình như nó còn ngâm nga một ca khúc nào đó.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thiếu nữ nhìn con cá biết bơi này thì bỗng có cảm giác bản thân đang nằm mơ....\n" +
            "\n" +
            "\n" +
            "\n" +
            "...Không đúng, phải là biết bay.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Cá lồng đèn không để ý tới ánh mắt đăm chiêu ở đằng sau, tiếp tục lắc đuôi đi tới một cái gương chiếu thật lớn:\" Nói tên ngươi.\"\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Katherine Aliane. \" Thanh âm của cô gái vang lên trong không gian tĩnh mịch.\n" +
            "\n" +
            "Trong tấm gương, một loạt các hình ảnh bắt đầu hiện lên.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đoàn người mặc đồ màu đỏ nâu đi ra từ khu rừng, ở giữa bọn họ, có một thiếu niên xinh đẹp cực kì nổi bật, trên tay anh là một cô gái, mái tóc màu đỏ thẫm, đôi mắt nhắm nghiền.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Khung cảnh đặc biệt tang thương, nhưng trên khuôn mặt thiếu niên vẫn không biểu lộ bất kì khuông bật cảm xúc nào, anh vẫn ôm lấy cô gái trong lòng, tay vững vàng có lực, đi một đoạn dài mà vẫn không hề mất sức.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Katherine nhìn qua tấm gương, cô vươn tay muốn chạm vào gương mặt như điêu khắc kia, nhưng một dòng cảm xúc lạnh băng chạy từ ngón tay lên người:\" Alance... tại sao lại phản bội ta. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "Hình ảnh trong gương vẫn diễn ra, thiếu niên nói gì đó với đám người xung quanh, sau đó anh ta liền quay lại khu rừng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Đứng trước một hồ nước lạnh lẽo, ánh mắt thiếu niên trở nên âm trầm, anh ta khuỵu một chân, buông tay thả người trong lòng xuống:\" Vĩnh biệt... Katherine.\"\n" +
            "\n" +
            "Cơ thể của cô gái dần dần chìm vào nước hồ lạnh lẽo, đến khi không còn thấy thân ảnh kia nữa, thiếu niên mới mang ánh mắt âm trầm rời đi, cảm ơn em đã giúp đỡ tôi trong thời gian qua.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\"...Được rồi, chúng ta đi thôi. \" Hình ảnh trong gương dần mơ hồ đi, khi bóng lưng thiếu niên khuất dần trong tán cây, cũng là lúc, thân ảnh cô gái đối diện với tấm gương lúc này dần trở nên hư ảo, rồi biến mất...\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "...\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Ta là Tam U, dòng tộc Cẩm Lý*.\" Cá lồng đèn đối với Katherine giới thiệu, rồi dẫn cô tới trước mặt một người nào đó:\" Báo họ tên, ngày sinh, nơi sinh ra, quốc tịch của ngươi. \"\n" +
            "\n" +
            "\n" +
            "\n" +
            "( *Cá chép, loài mang lại nhiều may mắn, có khả năng hóa rồng.)\n" +
            "\n" +
            "\n" +
            "\n" +
            "\" Katherine Aliane, sinh thần là 24/10. \" Cô thuần thục trả lời, sau đó liền bị đưa tới một cái tầng mây vô cùng mềm mại.\n" +
            "\n" +
            "\" Nhắm mắt. \" Tam U kêu lên một tiếng.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Thiếu nữ dùng động tác đơn giản nhắm hai mắt, ai, một đời đội trưởng vệ binh oai phong lẫm liệt của cô, thoáng cái liền chỉ là mây mù.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Một số tham vọng... cứ thế liền biến mất.', " +
            "'Kinh dị', " +
            "'Attack on Titan, còn được biết đến với tựa tiếng Việt là Đại Chiến Titan, là một bộ manga và anime Nhật Bản hậu tận thế, đen tối lấy bối cảnh thế giới nơi loài người sống trong những bức tường thành khổng lồ để chống lại Titan, những sinh vật khổng lồ hung dữ chuyên ăn thịt người.'," + //Description
            "'2023-05-10', " +
            "'2023-05-25', " +
            "'drawable/attack_on_titan.jpg', " +
            "2200, " +
            "'admin')";
    private static final String sql11 = "INSERT INTO stories VALUES (null, " +
            "'Đứa trẻ trên bầu trời xanh', " +
            "'Chúng tôi đi xuống phố, tay trong tay, chẳng có gì phải vội. Totoca đang dạy tôi về cuộc sống. Và điều đó khiến tôi thực sự hạnh phúc, được anh trai nắm tay và dạy nhiều điều. Nhưng là dạy về những điều thuộc thế giới bên ngoài. Bởi vì nhà, tôi học hỏi bằng cách tự mày mò khám phá và tự làm, tôi mắc lỗi nhiều và vì mắc lỗi, tôi thường bị ăn đòn. Cho tới tận trước đây không lâu, tôi vẫn chưa bị ai đánh bao giờ. Nhưng rồi mọi người nghe được chuyện này, chuyện nọ và bắt đầu nói tôi là quỷ, là quái vật, là tiểu yêu tóc hung. Tôi không muốn biết về điều đó. Nếu không phải đang ở bên ngoài thì tôi đã bắt đầu hát. Hát thú vị lắm. Ngoài ca hát, Totoca còn biết làm điều khác nữa: anh có thể huýt sáo. Nhưng dù cố gắng đến mức nào tôi cũng chẳng thể bắt chước anh huýt sáo được, chẳng âm thanh nào bật ra cả. Để an ủi tôi, anh bảo chuyện đó cũng bình thường thôi, rằng tôi chưa có cái miệng của người huýt sáo. Nhưng vì không thể hát thành lời, tôi hát thầm ở trong lòng. Thoạt đầu điều đó thật kỳ cục, nhưng về sau tôi cảm thấy hát như thế thực sự thú vị. Và lúc này, tôi đang nhớ lại một bài hát mẹ thường hát khi tôi còn bé xíu. Mẹ đứng bên chậu giặt, mảnh khăn buộc quanh đầu để che nắng. Với chiếc tạp dề ôm quanh thắt lưng, mẹ đứng đó hết giờ này đến giờ khác, dầm tay trong nước, khiến xà phòng nổi cơ man nào là bọt. Sau đó mẹ vắt kiệt nước khỏi quần áo và đem ra dây, phơi lên, kẹp lại và kéo chiếc dây phơi lên cao. Mẹ làm thế với tất cả quần áo. Mẹ giặt thuê cho nhà bác sĩ Faulhaber để kiếm thêm chút ít giúp trang trải chi phí sinh hoạt. Mẹ cao, gầy, nhưng rất đẹp. Da mẹ rám nắng, tóc thẳng và đen. Khi mẹ không cột tóc lên, tóc mẹ xõa đến tận thắt lưng. Nhưng tuyệt nhất là khi mẹ hát, và tôi hay quanh quẩn bên mẹ học lỏm.\n" +
            "\n" +
            "Chàng thủy thủ ơi, chàng thủy thủ ơi Chàng thủy thủ của nỗi buồn ơi Vì chàng Ngày mai em sẽ chết…\n" +
            "\n" +
            "Những con sóng xô bờ Quăng mình lên bờ cát Chàng ra khơi rồi Chàng thủy thủ ơi…\n" +
            "\n" +
            "Tình yêu của chàng Chẳng được nổi một ngày Tàu nhổ neo rồi Tàu ra khơi…\n" +
            "\n" +
            "Những con sóng xô bờ…\n" +
            "\n" +
            "Bài hát đó luôn khiến lòng tôi dâng trào một nỗi buồn khó hiểu.\n" +
            "\n" +
            "Totoca giật tay tôi. Tôi bừng tỉnh. “Sao thế Zezé?” “Không sao ạ. Em đang hát thôi mà.” “Hát ư?” “Vâng.” “Vậy chắc anh điếc rồi.”\n" +
            "\n" +
            "Anh ấy không biết con người ta có thể hát thầm ư? Tôi im lặng. Nếu anh không biết thì tôi cũng chẳng định dạy anh đâu.\n" +
            "\n" +
            "Chúng tôi đã tới lề đường quốc lộ Rio-São Paulo. Trên đường quốc lộ, có đủ mọi loại xe. Xe tải, xe con, xe ngựa, xe đạp.\n" +
            "\n" +
            "“Này,Zezé, chuyện quan trọng đây này. Trước hết chúng ta phải quan sát thật kỹ một chiều, sau đó nhìn chiều kia. Bây giờ sang đường nào.”\n" +
            "\n" +
            "Chúng tôi chạy sang đường.\n" +
            "\n" +
            "“Em có sợ không?”\n" +
            "\n" +
            "Tôi sợ, nhưng tôi lắc đầu.\n" +
            "\n" +
            "“Chúng mình cùng làm lại nào. Sau đó anh muốn xem em đã biết cách hay chưa.”\n" +
            "\n" +
            "Chúng tôi chạy trở lại phía bên kia đường. “Bây giờ em sang đường đi. Đừng ngập ngừng, bởi vì em lớn rồi mà.”\n" +
            "\n" +
            "Tim tôi đập nhanh hơn.\n" +
            "\n" +
            "“Nào. Sang đi.”\n" +
            "\n" +
            "Tôi chạy sang đường, gần như nín thở. Tôi đợi một chút và anh ra hiệu cho tôi quay trở về chỗ anh.\n" +
            "\n" +
            "“Lần đầu mà làm được thế là tốt đấy. Nhưng em đã quên một điều. Em phải nhìn cả hai phía xem có xe đang chạy tới không. Không phải lúc nào anh cũng có mặt ở đây để ra hiệu cho em đâu. Chúng ta sẽ thực hành thêm trên đường về nhà nhé. Nhưng giờ thì đi thôi, vì anh muốn chỉ cho em một thứ.”\n" +
            "\n" +
            "\n" +
            "Anh cầm tay tôi và chúng tôi lại bắt đầu bước đi, thong thả. Tôi không cách nào thôi nghĩ về một cuộc trò chuyện cách đây ít lâu.\n" +
            "\n" +
            "“Totoca.” “Gì cơ?”\n" +
            "\n" +
            "“Anh đã cảm nhận được tuổi chín chắn chưa?” “Thứ vớ vẩn gì vậy?”\n" +
            "\n" +
            "“Bác Edmundo nói đấy. Bác nói em khôn trước tuổi và rằng chẳng bao lâu nữa em sẽ đến tuổi chín chắn. Nhưng em chẳng cảm thấy có gì khác cả.”\n" +
            "\n" +
            "“Bác Edmundo bị ngớ ngẩn đấy. Bác ấy luôn nhồi những thứ vớ vẩn vào đầu em.”\n" +
            "\n" +
            "“Bác ấy không ngớ ngẩn đâu. Bác ấy khôn ngoan đấy chứ. Và khi lớn lên em muốn trở nên khôn ngoan, thành nhà thơ và thắt nơ bướm. Một ngày nào đó em sẽ được chụp ảnh chân dung thắt nơ hẳn hoi.”\n" +
            "\n" +
            "“Tại sao lại thắt nơ?”\n" +
            "\n" +
            "“Bởi vì anh không thể trở thành nhà thơ nếu không thắt nơ. Khi bác Edmundo cho em xem ảnh các nhà thơ trong cuốn tạp chí đó, em thấy ai cũng thắt nơ cả.”\n" +
            "\n" +
            "“Zezé, em phải thôi đi, đừng tin mọi điều bác ấy nói với em nữa. Bác Edmundo hơi biêng biêng đấy. Bác ấy còn hơi xạo nữa.”\n" +
            "\n" +
            "“Bác ấy là đồ chó đẻ ư?”\n" +
            "\n" +
            "“Em đã bị tát lệch mặt vì nói bậy quá nhiều mà chưa chừa à! Bác Edmundo không như vậy. Anh nói biêng biệng cơ mà. Nghĩa là hơi hâm hâm.”\n" +
            "\n" +
            "“Anh bảo bác ấy là kẻ nói xạo mà.”\n" +
            "\n" +
            "“Đó là hai chuyện hoàn toàn khác.”\n" +
            "\n" +
            "“Không, có khác gì đâu. Hôm nọ cha kể về ông Labonne với bác Severino, bạn chơi bài của cha, và cha nói, “Lão chó đẻ ấy là một kẻ nói xạo trời đánh thánh vật.” Và chẳng ai tát chả lệch mặt cả.”\n" +
            "\n" +
            "“Người lớn nói thế thì không sao.”\n" +
            "\n" +
            "Chúng tôi không nói gì trong vài phút.\n" +
            "\n" +
            "“Bác Edmundo không… Mà biêng biêng có nghĩa là gì, anh Totoca?”\n" +
            "\n" +
            "Anh giơ ngón tay chỉ lên đầu, quay quay ngón tay vẽ thành những vòng tròn trong không khí.\n" +
            "\n" +
            "\n" +
            "“Không, bác ấy không phải vậy. Bác ấy tốt lắm. Bác ấy dạy em nhiều điều, và chỉ đánh em có mỗi một lần, lại còn chẳng đánh mạnh nữa.”\n" +
            "\n" +
            "Totoca giật mình.\n" +
            "\n" +
            "“Bác ấy đánh em à? Khi nào?”\n" +
            "\n" +
            "“Khi em hư thật và bị Gloria tống đến nhà bà. Bác ấy muốn đọc báo nhưng không tìm thấy kính. Bác tìm hết chỗ cao đến chỗ thấp và nổi điên thực sự. Bác hỏi bà kính của bác ở đâu nhưng bà không biết. Hai người lục tung cả nhà lên để tìm. Thế rồi em nói em biết kính của bác ở đâu và nếu bác cho em ít tiền để mua bi thì em sẽ nói cho bác biết. Bác đi đến chỗ để áo khoác và lấy ra một ít tiền.\n" +
            "\n" +
            "“Mang kính ra đây thì bác đưa tiền cho mày.” “Em đến chỗ rương đựng quần áo lấy cái kính ra. Bác nói, Ra là mày, đồ ranh con!” Bác phát vào lưng em và cất béng tiền đi.”\n" +
            "\n" +
            "Totoca cười phá lên.\n" +
            "\n" +
            "“Em đến đó để khỏi bị ăn đòn ở nhà thế mà lại bị đánh ở đó. Đi nhanh chân lên một chút, nếu không chúng ta sẽ chẳng bao giờ đến nơi được đâu.”\n" +
            "\n" +
            "Tôi vẫn đang nghĩ về bác Edmundo. “Totoca, trẻ con nghỉ hưu phải không?” “Gì cơ?”\n" +
            "\n" +
            "\n" +
            "“Bác Edmundo chẳng làm gì cả mà vẫn có tiền. Bác không làm việc, nhưng Tòa Thị chính vẫn trả tiền cho bác hằng tháng.”\n" +
            "\n" +
            "“Thì sao?”\n" +
            "\n" +
            "“À thì, trẻ con chẳng làm gì. Trẻ con ăn, ngủ và được cha mẹ cho tiền.” “Nghỉ hưu khác chứ, Zezé. Người nghỉ hưu đã làm việc trong một thời gian\n" +
            "\n" +
            "dài rồi, tóc đã ngả bạc, chân chậm mắt mờ như bác Edmundo rồi. Nhưng đừng nghĩ về những thứ khó nhằn ấy nữa. Nếu em muốn học nhiều điều từ bác ấy thì tốt thôi. Nhưng khi đi với anh thì không nhé. Hãy cư xử như những đứa con trai khác. Em thậm chí có thể chửi thề, nhưng đừng nhét đầy đầu những thứ khó nhằn. Nếu không anh sẽ không đi chơi với em nữa đâu.”\n" +
            "\n" +
            "Tôi xị mặt xuống và không muốn nói chuyện nữa. Tôi cũng chẳng muốn hát. Chú chim nhỏ ca hát bên trong tôi đã bay mất rồi.\n" +
            "\n" +
            "Chúng tôi dừng lại và Totoca chỉ tay về phía ngôi nhà. “Nó đấy. Thích không?”\n" +
            "\n" +
            "Đó là một ngôi nhà bình thường. Màu trắng với những ô cửa sổ màu xanh dương. Toàn bộ cửa nẻo đều đóng im ỉm và im ắng như tờ.\n" +
            "\n" +
            "“Có ạ. Nhưng tại sao chúng ta phải chuyển đến đây chứ?” “Thay đổi cũng tốt mà.”\n" +
            "\n" +
            "Chúng tôi đứng trông qua hàng rào, nhìn cây xoài phía bên này và cây me ở phía bên kia.\n" +
            "\n" +
            "“Em là đứa hay chạy lăng xăng, nhưng em không biết nhà mình đang có chuyện gì đâu. Cha thất nghiệp rồi, đúng không? Đã sáu tháng kể từ khi cha choảng nhau với ông Scottfield và bị đuổi việc. Em có biết bây giờ Lala đang làm việc ở nhà máy không? Còn mẹ thì sắp đi làm ở thành phố, trong xưởng English Mill đấy, biết không? Giờ thì em biết rồi đấy, đồ ngốc ạ. Ở cái nhà mới này, ta sẽ tiết kiệm được tiền thuê nhà… Cái nhà kia thì cha nợ tám tháng tiền thuê nhà rồi. Em còn quá nhỏ nên không phải lo lắng về những chuyện buồn như thế. Nhưng anh sẽ phải giúp thật lực, xắn tay vào hỗ trợ việc nhà.”\n" +
            "\n" +
            "Anh đứng im lặng một lát.\n" +
            "\n" +
            "“Totoca, họ sẽ mang con báo đen và hai con sư tử cái tới đây chứ?” “Dĩ nhiên. Và lão gia nhân này sẽ phải tháo dỡ cái chuồng gà.” Anh nhìn tôi bằng ánh mắt thương xót, trìu mến.\n" +
            "\n" +
            "“Anh chính là người sẽ dỡ vườn thú và lắp ghép nó lại ở đây.”\n" +
            "\n" +
            "\n" +
            "Tôi thở phào. Bởi vì nếu không tôi sẽ phải kiếm thứ gì đó mới mẻ để chơi với em trai út Luis của tôi.\n" +
            "\n" +
            "\n" +
            "“Em đã thấy anh là người bạn như thế nào của em rồi, đúng không Zezé? Này, em sẽ chẳng mất mát gì nếu nói cho anh biết em đã làm chuyện đó như thế nào…”\n" +
            "\n" +
            "“Totoca, em thề là em không biết. Em thực sự không biết.” “Em nói dối. Ai đó đã dạy em học.”\n" +
            "\n" +
            "“Em chẳng học gì cả. Chẳng ai dạy em hết. Trừ phi quỷ sứ dạy em trong giấc ngủ. Jandira nói quỷ sứ là cha đỡ đầu của em.”\n" +
            "\n" +
            "Totoca bối rối. Anh thậm chí còn cốc đầu tôi mấy cái, cố bắt tôi phải nói cho anh biết. Nhưng tôi không biết mình đã làm được chuyện đó như thế nào.\n" +
            "\n" +
            "“Chẳng ai tự học cái đó được.”\n" +
            "\n" +
            "Nhưng anh không biết phải nói gì bởi vì chẳng ai thực sự nhìn thấy người nào dạy tôi học gì hết. Đó là một bí ẩn.\n" +
            "\n" +
            "Tôi nhớ lại chuyện xảy ra một tuần trước. Nó đã khiến cả nhà tôi xôn xao.\n" +
            "\n" +
            "Chuyện bắt đầu tại nhà bà tôi, khi tôi ngồi cạnh bác Edmundo đang đọc báo.\n" +
            "\n" +
            "“Bác ơi.”\n" +
            "\n" +
            "“Gì vậy, con trai?”\n" +
            "\n" +
            "Bác kéo cặp kính xuống chóp mũi, như cái cách mọi người lớn thường làm khi họ đã già.\n" +
            "\n" +
            "“Mấy tuổi bác học đọc?”\n" +
            "\n" +
            "Khoảng tầm sáu, bảy tuổi.”\n" +
            "\n" +
            "““Trẻ năm tuổi có thể học đọc không bác?”\n" +
            "\n" +
            "“Bác nghĩ là có. Nhưng chẳng ai thích dạy chúng đâu bởi vì chúng còn quá nhỏ.”\n" +
            "\n" +
            "“Bác học đọc như thế nào ạ?”\n" +
            "\n" +
            "“Như mọi người khác thôi, với những người đọc mẫu. Đọc bờ a baº.” “Ai cũng phải học đọc theo cách đó ạ?”\n" +
            "\n" +
            "“Theo bác biết thì ai cũng học như thế cả.” “Tất cả mọi người ư?” Bác nhìn tôi, ngạc nhiên.\n" +
            "\n" +
            "“Này,Zezé, đó là cách mọi người học đọc. Bây giờ hãy để yên cho bác đọc nốt. Ra sân sau mà tìm ổi đi.”\n" +
            "\n" +
            "Bác đẩy kính lên và cố tập trung đọc. Nhưng tôi không chịu đi.\n" +
            "\n" +
            "“Tiếc quá!”\n" +
            "\n" +
            "Nghe thấy tiếng cảm thán chân thật ấy, bác lại kéo kính xuống thấp. “Trời đất quỷ thần ơi. Cháu định ngồi dai như đỉa đây đấy à?”\n" +
            "\n" +
            "“Chỉ là cháu đã đi cả một quãng đường xa như thế tới đây để nói với bác một điều.”\n" +
            "\n" +
            "“Thôi được rồi, vậy thì nói đi.”\n" +
            "\n" +
            "“Không. Không phải vậy. Trước hết cháu cần biết ngày lĩnh lương hưu tới đây của bác là ngày nào.”\n" +
            "\n" +
            "“Ngày kia, bác nói, vừa nhìn tôi vẻ dò xét vừa cười tủm tỉm.\n" +
            "\n" +
            "“Ngày kia là thứ mấy hả bác?”\n" +
            "\n" +
            "“Thứ Sáu.”\n" +
            "\n" +
            "“Ừm, thứ Sáu bác có thể mang từ thành phố về cho cháu một con Vua Bạc không ạ?”\n" +
            "\n" +
            "“Nói từ từ xem nào,Zezé. Vua Bạc là gì?”\n" +
            "\n" +
            "“Là con ngựa trắng nhỏ cháu nhìn thấy trong rạp chiếu bóng. Chủ của nó là Fred Thompson. Nó là ngựa đã được thuần dưỡng rồi.”\n" +
            "\n" +
            "“Cháu muốn bác mang về cho cháu một con ngựa nhỏ kéo xe ư?”\n" +
            "\n" +
            "\n" +
            "“Không ạ. Cháu muốn con ngựa kiểu có đầu gỗ và bộ dây cương cơ. Ngựa kiểu bác gắn cái đuôi vào rồi chạy vòng quanh ấy ạ. Cháu cần phải luyện tập vì sau này cháu sẽ đóng phim.”\n" +
            "\n" +
            "Bác cười phá lên.\n" +
            "\n" +
            "“Bác hiểu rồi. Và nếu bác kiếm nó cho cháu, thì bác đượC gì nào?” “Cháu sẽ làm một việc gì đó cho bác ạ.” Cháu thơm bác một cái nhé?”\n" +
            "\n" +
            "“ “Cháu không thích thơm thiếc lắm đâu ạ.” “Vậy thì ôm nhé?”\n" +
            "\n" +
            "Tôi nhìn bác Edmundo và thật lòng cảm thấy tội nghiệp cho bác. Con chim nhỏ bên trong tôi nói một điều gì đó. Và tôi nhớ lại một chuyện tôi đã nghe người ta nói rất nhiều lần, rằng bác Edmundo sống ly thân với vợ và họ có năm đứa con. Nhưng bác sống một mình, đi lại chậm chạp lắm… Có lẽ bác đi chậm vì nhớ các con chăng? Con bác chẳng bao giờ đến thăm bác.\n" +
            "\n" +
            "Tôi đi vòng qua bàn và ôm bác thật chặt. Tôi cảm thấy đám tóc bạc của bác cọ vào trán mình. Tóc bác mềm thật đấy.\n" +
            "\n" +
            "“Đây không phải vì con ngựa đâu ạ. Cháu sẽ làm cho bác một việc khác Cơ. Cháu sẽ đọc.”\n" +
            "\n" +
            "“Gì cơ,Zezé? Cháu biết đọc ư? Ai dạy cháu?” “Chẳng ai cả.” “Cháu nói dối.”\n" +
            "\n" +
            "Tôi đi ra, và nói vọng vào từ ngoài cửa,“Thứ Sáu bác hãy mang ngựa về cho cháu và bác sẽ thấy cháu có biết đọc hay không?”\n" +
            "\n" +
            "Sau đó, khi đêm xuống và chị Jandira đã thắp đèn dầu lên bởi vì công ty điện đã cắt điện nhà chúng tôi do chưa thanh toán hóa đơn, tôi đứng nhón chân\n" +
            "\n" +
            "ngắm “ngôi sao” ấy. Đó là bức tranh vẽ trên một mảnh giấy, hình một ngôi sao với lời nguyện cầu ở bên dưới mong ngôi sao phù hộ cho nhà chúng tôi.\n" +
            "\n" +
            "“Jandira, chị nhấc em lên được không? Em sẽ đọc những chữ kia.” “Nói phét đủ rồi đấy,Zezé. Chị đang bận.”\n" +
            "\n" +
            "“Cứ nhấc em lên một tí thôi rồi em sẽ cho chị xem. “Này,Zezé, nếu định giở trò gì thì mày liệu hồn.” Chị nhấc tôi lên phía sau cánh cửa.\n" +
            "\n" +
            "“Nào, đọc đi. Chị đang muốn xem đây.”\n" +
            "\n" +
            "Vậy là tôi đọc, đọc thật. Tôi đọc câu cầu nguyện, cầu Chúa phù hộ cho gia đình chúng tôi và bảo vệ chúng tôi khỏi những linh hồn hiểm ác.\n" +
            "\n" +
            "Jandira đặt tôi xuống. Miệng chị há hốc.\n" +
            "\n" +
            "“Zezé, mày đã thuộc lòng câu đó phải không? Mày lừa chị phải không?” “Jandira, em thề. Em có thể đọc mọi thứ.”\n" +
            "\n" +
            "“Chẳng ai không học mà lại biết đọc cả. Là bác Edmundo dạy mày phải không? Hay bà?”\n" +
            "\n" +
            "“Chẳng ai cả.”\n" +
            "\n" +
            "Chị chạy đi lấy một tờ báo và tôi đọc không sai chữ nào. Chị kêu ré lên và gọi Glória. Gloria trở nên lo lắng và đi gọi Alaíde. Trong vòng mười phút, hàng xóm của chúng tôi đã xúm lại để xem chuyện lạ.\n" +
            "\n" +
            "Đó chính là chuyện Totoca muốn tôi nói cho anh biết. “Bác ấy đã dạy em và hứa cho em con ngựa nếu em học được.”\n" +
            "\n" +
            "“Không phải vậy.”\n" +
            "\n" +
            "“Anh sẽ hỏi bác ấy.”\n" +
            "\n" +
            "“Anh đi mà hỏi. Em không biết giải thích chuyện đó như thế nào, Totoca ạ. Nếu biết, em đã nói với anh.”\n" +
            "\n" +
            "“Vậy thì đi thôi. Rồi mày sẽ thấy. Khi mày cần gì đó…”\n" +
            "\n" +
            "\n" +
            "Anh giận dữ chộp tay tôi và bắt đầu lôi tôi về nhà. Rồi anh nghĩ ra một cách để trả thù.\n" +
            "\n" +
            "“Đáng đời mày chưa! Mày học quá sớm, đồ ngốc ạ. Giờ thì mày sẽ phải bắt đầu đến trường vào tháng Hai.” Đó là ý tưởng của Jandira. Nhờ thế nhà tôi sẽ yên bình suốt cả buổi sáng, còn tôi thì sẽ biết thế nào là lễ độ.\n" +
            "\n" +
            "“Lại học cách sang đường đi. Đừng tưởng khi mày đi học thì anh sẽ là vú em của mày, lần nào cũng đưa mày sang đường nhé. Nếu mày thông minh như vậy, thì mày cũng có thể học đượC việc này.”\n" +
            "\n" +
            "“Đây, ngựa đây. Giờ thì đọc cái này xem nào.”\n" +
            "\n" +
            "Bác mở tờ báo ra và chỉ cho tôi một câu trong một mẩu quảng cáo thuốc chữa bệnh.\n" +
            "\n" +
            "“Thuốc có trong tất cả các hiệu thuốc và các cửa hàng dượC uy tín,” tôi đọc.\n" +
            "\n" +
            "Bác Edmundo chạy ra gọi bà từ sân sau vào.\n" +
            "\n" +
            "“Mẹ ơi. Nó thậm chí đọc đúng cả từ hiệu thuốc này.” Cả hai người bắt đầu đố tôi đọc những câu khác và tôi đọc được hết.\n" +
            "\n" +
            "Bà bắt đầu lẩm bẩm gì đó tôi nghe không hiểu.\n" +
            "\n" +
            "Bác Edmundo đưa cho tôi con ngựa và tôi lại ôm bác. Sau đó bác nâng cằm tôi và nói bằng giọng ngập ngừng, “Cháu rồi sẽ tiến xa, khỉ con ạ. Không phải ngẫu nhiên tên cháu lại là José. Cháu sẽ là mặt trời và các vì sao sẽ sáng lấp lánh quanh cháu.”\n" +
            "\n" +
            "Tôi không hiểu bác nói gì và tự hỏi liệu có đúng là bác hơi biêng biệng hay không.\n" +
            "\n" +
            "“Cháu không hiểu được chuyện đó đâu. Đó là câu chuyện về Joseph1. Khi nào cháu lớn hơn một chút, bác sẽ kể cho cháu nghe.”\n" +
            "\n" +
            "Tôi mê mẩn các câu chuyện. Chuyện càng khó hiểu thì tôi càng thích.\n" +
            "\n" +
            "Tôi vỗ vỗ con ngựa nhỏ của mình một lúc lâu rồi ngước lên nhìn bác Edmundo, nói, “Bác có nghĩ tuần tới cháu sẽ lớn hơn một chút không, bác?”\n" +
            "\n', " +
            "'Tiểu thuyết', " +
            "'Review: Tác phẩm kể về những câu chuyện tuổi ấu thơ của tác giả, qua lăng kính ngây thơ của một đứa trẻ, nơi đó có một cậu bé đang trên hành trình khám phá nổi đau và tình yêu thương '," + //Description
            "'2019-08-21', " +
            "'2023-05-18', " +
            "'drawable/mstories.jpg', " +
            "2200, " +
            "'ninh')";

    private static final String sql8 = "INSERT INTO ratings VALUES (null,'admin', 1, 5, 'Truyện rất hay!', 1)";
    private static final String sql9 = "INSERT INTO ratings VALUES (null,'admin', 2, 4, 'Cốt truyện hấp dẫn!', 0)";
    private static final String sql10 = "INSERT INTO ratings VALUES (null,'admin'   , 3, 3, 'Truyện khá thú vị.', 1)";

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
        db.execSQL(sql11);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }

    //Lay tat ca truyen
    public Cursor getData1(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_STORIES, null);
        return res;
    }

    //Lay ra trueyn co view > 2000
    public Cursor getData2(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + TABLE_STORIES + " WHERE " + COLUMN_STORIES_VIEWS + " > ?";
        String[] selectionArgs = { "2000" };
        Cursor res = db.rawQuery(sqlQuery, selectionArgs);
        return res;
    }



    //Lay ra truyen duoc yeu thich

    public Cursor getFavoriteStoriesWithImage(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+TABLE_STORIES+"."+COLUMN_STORIES_ID+", " + TABLE_STORIES + "." + COLUMN_STORIES_TITLE + ", " + TABLE_STORIES + "." + COLUMN_STORIES_IMAGE +
                " FROM " + TABLE_RATINGS +
                " INNER JOIN " + TABLE_STORIES +
                " ON " + TABLE_RATINGS + "." + COLUMN_RATINGS_STORY_ID + " = " + TABLE_STORIES + "." + COLUMN_STORIES_ID +
                " WHERE " + COLUMN_RATINGS_ISFAVORITE + " = 1" + " AND "+ TABLE_RATINGS +"."+ COLUMN_STORIES_USERS_NAME +" = '"+userName+"'";
        return db.rawQuery(query, null);
    }


    //Lay ra truyen co the loai la Tieu Thuyet
    public Cursor getDataByGenre(String genre) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STORIES + " WHERE " + COLUMN_STORIES_GENRE + " = ?";
        String[] selectionArgs = { genre};
        return db.rawQuery(query, selectionArgs);
    }
    public Cursor getBookYourWrite(String userName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_STORIES + " WHERE " + COLUMN_STORIES_USERS_NAME + " = ?";
        String[] selectionArgs = { userName};
        return db.rawQuery(query, selectionArgs);
    }
}
