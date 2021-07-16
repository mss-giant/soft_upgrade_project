import java.sql.Statement;
import java.util.ArrayList;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_updater {
    private Connection connection = null; // データベースとの通信で使用
    private Statement statement = null; // 投げるクエリを管理
    private ResultSet result = null; // データベースから帰ってきたデータをセットとして持っている
    private String url; // データベースのurl
    private String user; // データベースの管理者
    private String password; // データベースのパスワード
    private String sql; // クエリ
    private String table_name; // テーブルの名前
    private ArrayList<String> update_DB_querys;

    public DB_updater(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.update_DB_querys = new ArrayList<String>();
    }

    // 通信を確立 INSERT SELECT DELETEで処理がわかれる
    public void connect_SQL(String kind_of_query) {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            Class.forName("org.postgresql.Driver");
            statement = connection.createStatement();

            query_controller(kind_of_query);

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (result != null) {
                    result.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    // どの処理が呼ばれたか確認する ここで次に呼ぶものを管理
    public void query_controller(String query) {
        if (query.equals("insert")) {
            // insert_query();
        }
        if (query.equals("select")) {
            // select_query();
        }
        if (query.equals("delete")) {
            // delete_table_data();
        }
    }

    public void create_sql_add_user(String user_name){
        String sql = "INSERT INTO user_name (name) VALUES "+"(\'"+user_name+"\');";
    }

    //ここからデータベースの変更
    public void create_sql_add_book(String book_name,String book_url){
        String sql1 = "INSERT INTO url (name,url) VALUES "+"(\'"+book_name+"\'"+","+"\'"+book_url+"\'"+");";
        System.out.println(sql1);
    }





}
