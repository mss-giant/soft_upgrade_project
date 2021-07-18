package src.DB_update_tool;



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

    public void db_update_go(){
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            Class.forName("org.postgresql.Driver");
            statement = connection.createStatement();
            //
            db_serial_set_max();


            for(int i=0;i<update_DB_querys.size();i++){
                statement.executeUpdate(update_DB_querys.get(i));
            }

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

    public void db_serial_set_max(){
        try {
            String reset1 = "SELECT setval(\'user_name_id_seq\',(select max(id) from user_name));";
            String reset2 = "SELECT setval(\'url_id_seq\',(select max(id) from url));";
            result = statement.executeQuery(reset1);
            result = statement.executeQuery(reset2);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 

    }


    public void create_sql_add_user(String user_name){
        String reset = "SELECT setval(\'user_name_id_seq\',(select max(id) from user_name));";
        String sql = "INSERT INTO user_name (name) VALUES "+"(\'"+user_name+"\');";
        //System.out.println(reset);
        //System.out.println(sql);
        //update_DB_querys.add(reset);
        update_DB_querys.add(sql);
    }

    public void create_sql_add_user_book_score(int user_id,int book_id,double new_score){
        String sql = "INSERT INTO score (user_id,book_id,score) VALUES "+"("+user_id+","+book_id+","+new_score+");";
        //System.out.println(sql);
        update_DB_querys.add(sql);
    }
    
    public void create_sql_add_user_friend(int user_a,int user_b){
        String sql = "INSERT INTO friend (user_a, user_b) values "+"("+user_a+","+user_b+");";
        //System.out.println(sql);
        update_DB_querys.add(sql);
    }

    public void create_sql_add_book(String book_name,String book_url){
        String reset = "SELECT setval(\'url_id_seq\',(select max(id) from url));";
        String sql1 = "INSERT INTO url (name,url) VALUES "+"(\'"+book_name+"\'"+","+"\'"+book_url+"\'"+");";
        //System.out.println(reset);
        //System.out.println(sql1);
        //update_DB_querys.add(reset);
        update_DB_querys.add(sql1);
    }

    public void create_sql_add_book_word(int book_id,int word_id){
        String sql = "INSERT INTO location (word_id, url_id) values "+"("+word_id+","+book_id+");";
        //System.out.println(sql);
        update_DB_querys.add(sql);
    }


    public void create_sql_update_user_name(int user_id, String new_user_name){
        String sql = "UPDATE user_name set name="+"\'"+new_user_name+"\'"+" where id="+user_id+";";
        //System.out.println(sql);
        update_DB_querys.add(sql);
    }

    public void create_sql_update_user_book_score(int user_id, int book_id, double new_score){
        String sql = "UPDATE score set score="+new_score+" where user_id="+user_id+" and book_id="+book_id+";";
        //System.out.println(sql);
        update_DB_querys.add(sql);
    }

    public void create_sql_update_book_name(int book_id,String new_book_name){
        String sql = "UPDATE url set name="+"\'"+new_book_name+"\'"+" where id="+book_id+";";
        //System.out.println(sql);
        update_DB_querys.add(sql);
    }




    public void create_sql_delete_user(int user_id){
        String sql_user_name = "DELETE from user_name where id="+user_id+";";
        String sql_friend = "DELETE from friend where user_a="+user_id+" or "+"user_b="+user_id+";";
        String sql_score = "DELETE from score where user_id="+user_id+";";
        //System.out.println(sql_user_name);
        //System.out.println(sql_friend);
        //System.out.println(sql_score);
        update_DB_querys.add(sql_user_name);
        update_DB_querys.add(sql_friend);
        update_DB_querys.add(sql_score);
    }

    public void create_sql_delete_book(int book_id){
        String sql_url = "DELETE from url where id="+book_id+";";
        String sql_location = "DELETE from location where url_id="+book_id+";";
        String sql_link = "DELETE from link where source="+book_id+" or "+"target="+book_id+";";
        //System.out.println(sql_url);
        //System.out.println(sql_location);
        //System.out.println(sql_link);
        update_DB_querys.add(sql_url);
        update_DB_querys.add(sql_location);
        update_DB_querys.add(sql_link);
    }

    public void test_disp(){
        for(String s : update_DB_querys){
            System.out.println(s);
        }
        System.out.println();
        for(int i=0;i<update_DB_querys.size();i++){
            System.out.println(update_DB_querys.get(i));
        }
    }



}
