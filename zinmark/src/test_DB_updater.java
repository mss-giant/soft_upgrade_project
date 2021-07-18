package src;
import src.DB_update_tool.*;



public class test_DB_updater {
    public static void main(String[] args) {
        System.out.println("roading....");
        DB_updater dbupdate = new DB_updater("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        dbupdate.create_sql_add_user("sato");
        dbupdate.create_sql_add_user_book_score(1, 3, 2.0);
        dbupdate.create_sql_add_user_friend(3, 8);
        dbupdate.create_sql_add_book("mask", "hoho");
        dbupdate.create_sql_add_book_word(3, 1);
        dbupdate.create_sql_update_user_name(3, "nini");
        dbupdate.create_sql_update_user_book_score(1, 3, 4.9);
        dbupdate.create_sql_update_book_name(3, "new name");
        dbupdate.create_sql_delete_user(1);
        dbupdate.create_sql_delete_book(2);
        dbupdate.db_update_go();
        //dbupdate.test_disp();
    }
}
