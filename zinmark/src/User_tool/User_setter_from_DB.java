package src.User_tool;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;




public class User_setter_from_DB {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result = null;
    private String url;
    private String user;
    private String password;
    private String sql;

    private int serial_next_number;


    private Map<Integer,User> users = new HashMap<Integer,User>();

    public User_setter_from_DB(){
    }


    //User info
    public Map<Integer,User> get_users(){
        return this.users;
    }

    public void set_User_info(int id, String name){
        if(!users.containsKey(id)){
            users.put(id, new User(id, name));
        }
    }

    //User book score
    public void set_User_bookscore(int user_id, int book_id, double score){
        users.get(user_id).set_bookscore(book_id, score);
    }



    public void set_User_friends(int user_a, int user_b){
        users.get(user_a).set_User_friends(users.get(user_b), true);
        users.get(user_b).set_User_friends(users.get(user_a), true);
    }


    //Data base
    public void set_database_info(String url, String user, String password){
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public int get_next_user_num(){
        return this.serial_next_number;
    }

    public void set_next_user_num(){
        this.serial_next_number++;
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
        if(table_name.equals("user_name")){
            scandb_User_info(table_name);
        }
        if(table_name.equals("score")){
            scandb_User_bookscore(table_name);
        }
        if(table_name.equals("friend")){
            scandb_User_friends(table_name);
        }
        if(table_name.equals("serial")){
            scandb_next_serial_number();
        }
    }


    public void scandb_User_info(String table_name) {
        sql = " SELECT * from " + table_name +";";

        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                set_User_info(result.getInt("id"), result.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void scandb_User_bookscore(String table_name) {
        sql = " SELECT * from " + table_name +";";

        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                set_User_bookscore(result.getInt("user_id"), result.getInt("book_id"), result.getDouble("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void scandb_User_friends(String table_name){
        sql = " SELECT * from " + table_name +";";

        try {
            result = statement.executeQuery(sql);
            while (result.next()) {
                set_User_friends(result.getInt("user_a"), result.getInt("user_b"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void scandb_next_serial_number(){
        sql = "select setval('user_name_id_seq',(select max(id) from user_name)) as nownum;";
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


    //check the connection sql

    public void test_disp(){
        for(User u : users.values()){
            System.out.println("id : "+u.get_id() + " User name : " +u.get_Name());
            for(Integer book_id : u.book_score.keySet()){
                System.out.println("book id : "+ book_id + " score : "+u.get_bookscore(book_id));
            }
        }
    }
















}
