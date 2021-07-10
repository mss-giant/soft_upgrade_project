import java.sql.Statement;
import java.util.ArrayList;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

class  CSVtoDB{
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result = null;
    private String url;
    private String user;
    private String password;
    private String sql;
    private String table_name;
    private ArrayList<String> insert_data;
    private String[] columns;

    CSVtoDB(String url, String user, String password,String table_name, ArrayList<String> data, String[] columns) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.table_name = table_name;
        this.insert_data = data;
        this.columns = columns;

        connect_SQL();
    }

    public void connect_SQL() {
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            Class.forName("org.postgresql.Driver");
            statement = connection.createStatement();
            System.out.println("roading.....");
            //insert_query();
            select_query();
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
            System.out.println("success");
        }
    }

    public void insert_query() {;
        try {
            for(int i=0;i<insert_data.size();i++){
                statement.executeUpdate(insert_data.get(i));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                        System.out.print(result.getObject(i+1));
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
}
