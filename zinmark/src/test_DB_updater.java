public class test_DB_updater {
    public static void main(String[] args) {
        System.out.println("roading....");
        DB_updater dbupdate = new DB_updater("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        dbupdate.create_sql_add_user("sato");
        dbupdate.create_sql_add_book("mask", "hoho");
    }
}
