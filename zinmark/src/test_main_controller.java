package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import src.Book_tool.*;
import src.User_tool.*;
import src.Command_tool.*;
import src.DB_update_tool.*;

public class test_main_controller {
    public static void main(String[] args) {
        test_system_go test = new test_system_go();
        System.out.println("See you");
    }
}

class test_system_go {
    private Book_setter_from_DB book_setter = new Book_setter_from_DB();
    private Book_only_Recommend book_only_rec = new Book_only_Recommend();
    private User_setter_from_DB user_setter = new User_setter_from_DB();
    private User_only_Recommendation user_only_rec = new User_only_Recommendation();
    private Command_Scanner scan_command = new Command_Scanner();
    private DB_updater db_update = new DB_updater();

    private Map<Integer, User> all_users = new HashMap<Integer, User>();
    private Map<Integer, MyBook> all_books = new HashMap<Integer, MyBook>();

    private int target_user_id = 1; // note : changing value with command input
    private ArrayList<String> serch_word_list = new ArrayList<String>();

    public test_system_go() {
        set_database_info();
        set_initial_data();
        command_listen_start();
        // update_book_value();
        // update_user_value();
    }

    // 1
    public void set_database_info() {
        book_setter.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        user_setter.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        db_update.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
    }

    // 2
    public void set_initial_data() {
        System.out.println("getting data from database....");
        book_setter.connect_and_select_data("url");
        book_setter.connect_and_select_data("link");
        book_setter.connect_and_select_data("location");
        book_setter.connect_and_select_data("word");
        book_setter.connect_and_select_data("serial");
        book_setter.serch_reference_to_me();
        user_setter.connect_and_select_data("user_name");
        user_setter.connect_and_select_data("score");
        user_setter.connect_and_select_data("friend");
        user_setter.connect_and_select_data("serial");

        this.all_books = book_setter.get_books();
        this.all_users = user_setter.get_users();
        book_only_rec.set_books(this.all_books);
        user_only_rec.set_users(this.all_users);
        user_only_rec.set_friend_of_friend();
        update_book_value();
        update_user_value();
        System.out.println("Completed!");
    }

    // update cal
    public void update_book_value() {
        book_only_rec.set_books(all_books);
        book_only_rec.cal_loop();
        book_only_rec.set_serch_word(serch_word_list);
        book_only_rec.cal_word_score();
        book_only_rec.set_book_word_score_0_1();
        // book_only_rec.display();
    }

    // update cal
    public void update_user_value() {
        user_only_rec.set_users(all_users);
        user_only_rec.reset_similar_recommend();
        // user_only_rec.set_number_of_books(book_setter.get_next_book_num() - 1);
        ArrayList<Integer> book_ids = new ArrayList<Integer>();
        book_ids.addAll(all_books.keySet());
        Collections.sort(book_ids);
        user_only_rec.set_books(book_ids);
        user_only_rec.cal_Similar_score();
        user_only_rec.set_friend_of_friend();
        // user_only_rec.cal_target_rec_book_score(target_user_id);
        // user_only_rec.all_display(target_user_id);
    }

    public void command_listen_start() {
        while (true) {
            for (int i = 0; i < 40; i++) {
                System.out.print("__");
            }
            System.out.println();

            scan_command.scan_command();
            if (scan_command.get_exit_call()) {
                System.out.println("Saving data .....");
                db_update.db_update_go();
                System.out.println("Saved !");
                break;
            }
            if (scan_command.get_display_user_info_call()) {
                display_user_info();
            }
            if (scan_command.get_display_book_info_call()) {
                display_book_info();
            }
            if (scan_command.get_display_user_list_call()) {
                display_user_list();
            }
            if (scan_command.get_display_book_list_call()) {
                display_book_list();
            }
            if (scan_command.get_display_word_list_call()) {
                display_word_list();
            }
            if (scan_command.get_rec_user_call()) {
                rec_user();
            }
            if (scan_command.get_rec_user_book_call()) {
                rec_user_book();
            }
            if (scan_command.get_rec_book_call()) {
                rec_book();
            }
            if (scan_command.get_add_user_call()) {
                add_user();
            }
            if (scan_command.get_add_user_friend_call()) {
                add_user_friend();
            }
            if (scan_command.get_add_user_book_score_call()) {
                add_user_book_score();
            }
            if (scan_command.get_add_book_call()) {
                add_book();
            }
            if (scan_command.get_add_book_word_call()) {
                add_book_word();
            }
            if (scan_command.get_update_user_name_call()) {
                update_user_name();
            }
            if (scan_command.get_update_user_book_score_call()) {
                update_user_book_score();
            }
            if (scan_command.get_update_book_name_call()) {
                update_book_name();
            }
            if (scan_command.get_delete_user_call()) {

            }
            if (scan_command.get_delete_book_call()) {

            }
            if (scan_command.get_search_mode_call()) {
                for (String s : scan_command.get_input_words()) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }

            for (int i = 0; i < 40; i++) {
                System.out.print("__");
            }
            System.out.println();
        }

    }

    public void display_user_info() {
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        if (all_users.containsKey(user_id)) {
            all_users.get(user_id).disp_user_information();
        } else {
            System.out.println("Not found User");
        }
    }

    public void display_book_info() {
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        if (all_books.containsKey(book_id)) {
            all_books.get(book_id).disp_book_information();
        } else {
            System.out.println("Not found Book");
        }
    }

    public void display_user_list() {
        System.out
                .println("+----------------------------------------------------------------------------------------+");
        for (Integer id : all_users.keySet()) {
            if (id % 5 == 0) {
                System.out.format("%3s", id.toString());
                System.out.print(" : ");
                System.out.format("%-10s", all_users.get(id).get_Name());
                System.out.println();
            } else {
                System.out.format("%3s", id.toString());
                System.out.print(" : ");
                System.out.format("%-10s", all_users.get(id).get_Name());
            }
        }
        System.out.println();
        System.out
                .println("+----------------------------------------------------------------------------------------+");
    }

    public void display_book_list() {
        System.out
                .println("+----------------------------------------------------------------------------------------+");
        for (Integer id : all_books.keySet()) {
            if (id % 5 == 0) {
                System.out.format("%3s", id.toString());
                System.out.print(" : ");
                System.out.format("%-10s", all_books.get(id).get_book_name());
                System.out.println();
            } else {
                System.out.format("%3s", id.toString());
                System.out.print(" : ");
                System.out.format("%-10s", all_books.get(id).get_book_name());
            }
        }
        System.out.println();
        System.out
                .println("+----------------------------------------------------------------------------------------+");
    }

    public void display_word_list() {
        Map<Integer, String> wordslist = book_setter.get_words_list();
        System.out
                .println("+----------------------------------------------------------------------------------------+");
        for (Integer id : wordslist.keySet()) {
            if (id % 5 == 0) {
                System.out.format("%3s", id.toString());
                System.out.print(" : ");
                System.out.format("%-10s", wordslist.get(id));
                System.out.println();
            } else {
                System.out.format("%3s", id.toString());
                System.out.print(" : ");
                System.out.format("%-10s", wordslist.get(id));
            }
        }
        System.out.println();
        System.out
                .println("+----------------------------------------------------------------------------------------+");
    }

    public void rec_user() {
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        if (all_users.containsKey(user_id)) {
            target_user_id = user_id;
            user_only_rec.cal_target_rec_book_score(target_user_id);
            all_users.get(user_id).disp_rec_book_score_sort();
            System.out.println(" \n add book score -->>");
            all_users.get(user_id).disp_add_rec_book_score_sort(all_books);
        } else {
            System.out.println("Not found user");
        }
    }

    public void rec_user_book() {
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        if (all_users.containsKey(user_id)) {
            if (all_books.containsKey(book_id)) {
                target_user_id = user_id;
                user_only_rec.cal_target_rec_book_score(target_user_id);
                all_users.get(user_id).disp_rec_book_score(book_id);
                System.out.println("\n add book score");
                all_users.get(user_id).disp_add_rec_book_score(book_id, all_books);
            } else {
                System.out.println("Not found book");
            }
        } else {
            System.out.println("Not found user");
        }
    }

    public void rec_book() {
        book_only_rec.display();
    }

    public void add_user() {
        String new_user_name = scan_command.get_command_data().get("user_name");
        int new_user_id = user_setter.get_next_user_num();
        all_users.put(new_user_id, new User(new_user_id, new_user_name));
        db_update.create_sql_add_user(new_user_name);
        user_setter.set_next_user_num();
        update_user_value();
        System.out.println("New User Name : " + new_user_name + "    New User ID : " + new_user_id);
    }

    public void add_user_friend() {
        int user_a_id = Integer.parseInt(scan_command.get_command_data().get("user_a_id"));
        int user_b_id = Integer.parseInt(scan_command.get_command_data().get("user_b_id"));
        if (all_users.containsKey(user_a_id) && all_users.containsKey(user_b_id)) {
            if (all_users.get(user_a_id).check_User_friends(all_users.get(user_b_id))) {
                System.out.println("They're already friends.");
            } else {
                all_users.get(user_a_id).set_User_friends(all_users.get(user_b_id), true);
                all_users.get(user_b_id).set_User_friends(all_users.get(user_a_id), true);
                db_update.create_sql_add_user_friend(user_a_id, user_b_id);
                System.out.println(all_users.get(user_a_id).get_Name() + " and " + all_users.get(user_b_id).get_Name()
                        + " became friends.");
                user_only_rec.set_friend_of_friend();
                update_user_value();
            }

        } else {
            System.out.println(" Not found user userID : " + user_a_id + " or userID : " + user_b_id);
        }

    }

    public void add_user_book_score() {
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        double score = Double.parseDouble(scan_command.get_command_data().get("score"));
        if (all_users.containsKey(user_id)) {
            if (all_books.containsKey(book_id)) {
                if (!all_users.get(user_id).check_have_book(book_id)) {
                    all_users.get(user_id).set_bookscore(book_id, score);
                    db_update.create_sql_add_user_book_score(user_id, book_id, score);
                    update_user_value();
                    System.out.println("User Name : " + all_users.get(user_id).get_Name() + "   Book ID : " + book_id
                            + "   score : " + score);
                } else {
                    System.out.println(all_users.get(user_id).get_Name() + " already evaluated this book. " + "Book ID "
                            + book_id);
                }
            } else {
                System.out.println("Not found book.");
            }
        } else {
            System.out.println("Not found user.");
        }
    }

    public void add_book() {
        String book_name = scan_command.get_command_data().get("book_name");
        String url = scan_command.get_command_data().get("url");
        int new_book_id = book_setter.get_next_book_num();
        all_books.put(new_book_id, new MyBook(new_book_id, book_name, url));
        db_update.create_sql_add_book(book_name, url);
        book_setter.set_next_book_num();
        update_book_value();
        update_user_value();
        System.out.println("New Book ID : " + new_book_id + "   Book Name : " + book_name + "   Book url : " + url);
    }

    public void add_book_word() {
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        int word_id = Integer.parseInt(scan_command.get_command_data().get("word_id"));
        if (all_books.containsKey(book_id)) {
            if (book_setter.get_words_list().containsKey(word_id)) {
                if (!all_books.get(book_id).check_has_word(book_setter.get_words_list().get(word_id))) {
                    all_books.get(book_id).set_word(book_setter.get_words_list().get(word_id));
                    db_update.create_sql_add_book_word(book_id, word_id);
                    System.out.println(all_books.get(book_id).get_book_name() + " : " + "["
                            + book_setter.get_words_list().get(word_id) + "]" + " added.");
                    update_book_value();
                } else {
                    System.out.println("The word is already in this book.");
                }
            } else {
                System.out.println("Not found wordID.");
            }
        } else {
            System.out.println("Not found book.");
        }
    }

    public void update_user_name() {
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        String user_name = scan_command.get_command_data().get("user_name");
        if (all_users.containsKey(user_id)) {
            all_users.get(user_id).update_info(user_id, user_name);
            db_update.create_sql_update_user_name(user_id, user_name);
            System.out.println("new Name : "+user_name);
        }
        else{
            System.out.println("Not found user");
        }

    }

    public void update_user_book_score(){
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        double score = Double.parseDouble(scan_command.get_command_data().get("score"));
        if(all_users.containsKey(user_id)){
            if(all_books.containsKey(book_id)){
                if(all_users.get(user_id).check_have_book(book_id)){
                    all_users.get(user_id).update_bookscore(book_id, score);
                    db_update.create_sql_update_user_book_score(user_id, book_id, score);
                    update_user_value();
                    System.out.println("updated.    user_id : "+user_id+"   book_id : "+book_id+"    score : "+score);
                }
                else{
                    System.out.println("user don't evaluated. first, please add score");
                }
            }
            else{
                System.out.println("Not found book");
            }
        }
        else{
            System.out.println("Not found user");
        }
    }

    public void update_book_name(){
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        String book_name = scan_command.get_command_data().get("book_name");
        if(all_books.containsKey(book_id)){
            all_books.get(book_id).replace_book_name(book_name);
            db_update.create_sql_update_book_name(book_id, book_name);
            System.out.println("updated.  new book name : "+book_name);
        }
        else{
            System.out.println("Not found book");
        }
    }



}
