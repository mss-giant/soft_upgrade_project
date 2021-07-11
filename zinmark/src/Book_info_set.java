import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;





class Book_info_set {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result = null;
    private String url;
    private String user;
    private String password;
    private String sql;


    private Map<Integer,MyBook> books = new HashMap<Integer, MyBook>();


    Book_info_set(){
        
    }

    public Map<Integer,MyBook> get_books(){
        return this.books;
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
    }


    //old data include method below, there is comment
    public void scandb_book_info(String table_name) {
        sql = " SELECT * from " + table_name +";";

        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                //for(int i=1;i<=result.getMetaData().getColumnCount();i++){
                //    //System.out.print(" "+result.getMetaData().getColumnLabel(i+1)+" : ");
                //    if(result.getMetaData().getColumnName(i).equals("id")){
                //        book_id = result.getInt(i);
                //        //System.out.print(int_data);
                //    }
                //    else if(result.getMetaData().getColumnName(i).equals("url")){
                //        my_page = result.getString(i);
                //        //System.out.print(string_data);
                //    }
                //    //System.out.print((result.getMetaData().getColumnLabel(i+1).equals(columns[i]))) ;
                //    //System.out.print(new String("id").hashCode());
                //    //System.out.format("%12s", result.getObject(i+1).getClass().getName());
                //}

                //System.out.println(result.getInt("id") + " "+ result.getString("url"));
                set_book_info(result.getInt("id"),new String(Character.toChars(result.getInt("id")+64)), result.getString("url"));
                
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
                set_reference_book(result.getInt("souce"), result.getInt("target") );
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
