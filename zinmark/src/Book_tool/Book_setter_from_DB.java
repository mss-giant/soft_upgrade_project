package src.Book_tool;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;





public class Book_setter_from_DB {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result = null;
    private String url;
    private String user;
    private String password;
    private String sql;

    private int serial_next_number;


    private Map<Integer,MyBook> books = new HashMap<Integer, MyBook>();
    private Map<Integer,String> word_list = new HashMap<Integer,String>();


    public Book_setter_from_DB(){
        
    }

    public Map<Integer,MyBook> get_books(){
        return this.books;
    }

    public Map<Integer,String> get_words_list(){
        return this.word_list;
    }

    //MyBook設定関連
    public void set_book_info(int id, String book_name,String my_page){
        if(!books.containsKey(id)){
            MyBook book = new MyBook(id, book_name, my_page);
            books.put(id, book);
        }
    }

    public void set_reference_book(int id, int reference_book_id){
        books.get(id).set_reference_book(books.get(reference_book_id));
    }

    public void set_book_has_word(int id, String word){
        books.get(id).set_word(word);
    }

    public void set_words_list(int id, String word){
        word_list.put(id, word);
    }

    public void serch_reference_to_me(){
        for(MyBook me : books.values()){
            for(MyBook other : books.values()){
                if(!me.equals(other)){
                    for(MyBook other_refere_book : other.get_reference_book()){
                        if(other_refere_book.equals(me)){
                            me.set_referenced_to_me(other);
                        }
                    }
                }
            }
        }
    }

    public int get_next_book_num(){
        return this.serial_next_number;
    }


    // データベース関連
    public void set_database_info(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void connect_and_select_data(String table_name) {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            Class.forName("org.postgresql.Driver");
            statement = connection.createStatement();
            //System.out.println("roading.....");


            select_data_controller(table_name);


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
            //System.out.println("success");
        }
    }

    public void select_data_controller(String table_name){
        if(table_name.equals("url")){
            scandb_book_info(table_name);
        }
        if(table_name.equals("link")){
            scandb_reference_book(table_name);
        }
        if(table_name.equals("location")){
            scandb_book_has_word();
        }
        if(table_name.equals("word")){
            scandb_word_list();
        }
        if(table_name.equals("serial")){
            scandb_next_serial_number();
        }
    }


    //old data include method below, there is comment
    public void scandb_book_info(String table_name) {
        sql = " SELECT * from " + table_name +";";

        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                set_book_info(result.getInt("id"),result.getString("name"), result.getString("url"));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
  
    public void scandb_reference_book(String table_name){
        sql = " SELECT * from " + table_name +";";

        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                set_reference_book(result.getInt("source"), result.getInt("target") );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void scandb_book_has_word(){
        sql = "select l.\"url_id\", w.\"word\" from location l, word w where l.\"word_id\"=w.id;";


        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                set_book_has_word(result.getInt("url_id"), result.getString("word") );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
    }

    public void scandb_word_list(){
        sql = "select * from word;";
        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                set_words_list(result.getInt("id"), result.getString("word"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void scandb_next_serial_number(){
        sql = "select setval('url_id_seq',(select max(id) from url)) as nownum;";
        //sql = "select max(id) from url;";
        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                this.serial_next_number = result.getInt("nownum") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void test_disp_book_info(){
        for(Integer id : books.keySet()){
            System.out.println("id : " + books.get(id).get_book_id()+" name : "+ books.get(id).get_book_name()+" url : "+books.get(id).get_my_page());
        }
    }

    public void test_disp_reference_book(){
        for(Integer id : books.keySet()){
            for(MyBook target : books.get(id).get_reference_book()){
                System.out.println(books.get(id).get_book_id() + " "+target.get_book_id());
            }
        }
    }
    
    public void test_disp_book_has_word(){
        for(Integer id : books.keySet()){
            for(String word : books.get(id).get_book_has_word_list()){
                System.out.println(books.get(id).get_book_id() + " "+ word);
            }
        }
    }

    public void test_disp_reference_to_me(){
        for(Integer id : books.keySet()){
            System.out.print(books.get(id).get_my_page() + " <<- ");
            for(MyBook other : books.get(id).get_refernced_to_me()){
                System.out.print(other.get_my_page()+" ");
            }
            System.out.println();
        }
    }


}
