package DB_Setting_tool;

import java.sql.Statement;
import java.util.ArrayList;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class  CSVtoDB{
    private Connection connection = null;   //データベースとの通信で使用
    private Statement statement = null;     //投げるクエリを管理
    private ResultSet result = null;        //データベースから帰ってきたデータをセットとして持っている
    private String url;                     //データベースのurl
    private String user;                    //データベースの管理者
    private String password;                //データベースのパスワード
    private String sql;                     //クエリ
    private String table_name;              //テーブルの名前
    private ArrayList<String> insert_data;  //ファイルがスキャンしたデータをクエリにしたもの
    private String[] columns;               //オリジナルのカラム　テストのみ


    //宣言時に以下のものを受け取る　基本以下がないと通信できない
    public CSVtoDB(String url, String user, String password,String table_name, ArrayList<String> data, String[] columns) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.table_name = table_name;
        this.insert_data = data;
        this.columns = columns;
    }


    //通信を確立　INSERT SELECT DELETEで処理がわかれる
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


    //どの処理が呼ばれたか確認する　ここで次に呼ぶものを管理
    public void query_controller(String query){
        if(query.equals("insert")){
            insert_query();
        }
        if(query.equals("select")){
            select_query();
        }
        if(query.equals("delete")){
            delete_table_data();
        }
    }

    //データを入れる
    public void insert_query() {;
        try {
            for(int i=0;i<insert_data.size();i++){
                statement.executeUpdate(insert_data.get(i));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    //テスト時のみ使用
    public void select_query() {
        sql = " SELECT * from " + table_name +";";
        int count=1;
        for(int i=0;i<columns.length;i++){
                System.out.format("%12s", columns[i]);
            
        }

        try {
            result = statement.executeQuery(sql);
            String s[] = new String[columns.length];
            System.out.println();
            while (result.next()) {
                for(int i=0;i<columns.length;i++){
                    System.out.print(" "+result.getMetaData().getColumnLabel(i+1)+" : ");
                    if(result.getMetaData().getColumnClassName(i+1).equals(Integer.class.getName())){
                        int int_data = result.getInt(i+1);
                        System.out.print(int_data);
                    }
                    else if(result.getMetaData().getColumnName(i+1).equals(String.class.getName())){
                        String string_data = result.getString(i+1);
                        System.out.print(string_data);
                    }
                    else{
                        System.out.print(result.getObject(i+1).toString());
                    }
                    //System.out.print((result.getMetaData().getColumnLabel(i+1).equals(columns[i]))) ;
                    //System.out.print(new String("id").hashCode());
                    //System.out.format("%12s", result.getObject(i+1).getClass().getName());
                }
                System.out.println();
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //テーブルのデータを消す
    public void delete_table_data(){
        sql = "delete from "+ table_name+";";
        try {
            statement.executeUpdate(sql);    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
