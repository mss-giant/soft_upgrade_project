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
        //update_book_value();
        //update_user_value();
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


    //update cal
    public void update_book_value() {
        book_only_rec.set_books(all_books);
        book_only_rec.cal_loop();
        book_only_rec.set_serch_word(serch_word_list);
        book_only_rec.cal_word_score();
        book_only_rec.set_book_word_score_0_1();
        // book_only_rec.display();
    }

    //update cal
    public void update_user_value() {
        user_only_rec.set_users(all_users);
        //user_only_rec.set_number_of_books(book_setter.get_next_book_num() - 1);
        ArrayList<Integer> book_ids = new ArrayList<Integer>();
        book_ids.addAll(all_books.keySet());
        Collections.sort(book_ids);
        user_only_rec.set_books(book_ids);
        user_only_rec.cal_Similar_score();
        user_only_rec.set_friend_of_friend();
        //user_only_rec.cal_target_rec_book_score(target_user_id);
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
                break;
            }
            if (scan_command.get_display_user_info_call()) {
                display_user_info();               
            }
            if (scan_command.get_display_book_info_call()) {
                display_book_info();
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
                System.out.println("add user call");
            }
            if (scan_command.get_add_user_friend_call()) {
                System.out.println("add user friend call");
            }
            if (scan_command.get_add_user_book_score_call()) {
                System.out.println("add user book score call");
            }
            if (scan_command.get_add_book_call()) {
                System.out.println("add book call");
            }
            if (scan_command.get_add_book_word_call()) {
                System.out.println("add book word call");
            }
            if (scan_command.get_update_user_name_call()) {

            }
            if (scan_command.get_update_user_book_score_call()) {

            }
            if (scan_command.get_update_book_name_call()) {

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


    public void display_user_info(){
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id")); 
        if(all_users.containsKey(user_id)){
            all_users.get(user_id).disp_user_information();
        }
        else{
            System.out.println("Not found User");
        }
    }

    public void display_book_info(){
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        if(all_books.containsKey(book_id)){
            all_books.get(book_id).disp_book_information();
        }
        else{
            System.out.println("Not found Book");
        }
    }

    public void rec_user(){
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        if(all_users.containsKey(user_id)){
            target_user_id=user_id;
            user_only_rec.cal_target_rec_book_score(target_user_id);
            all_users.get(user_id).disp_rec_book_score_sort();
        }
        else{
            System.out.println("Not found user");
        }
    }

    public void rec_user_book(){
        int user_id = Integer.parseInt(scan_command.get_command_data().get("user_id"));
        int book_id = Integer.parseInt(scan_command.get_command_data().get("book_id"));
        if(all_users.containsKey(user_id)){
            if(all_books.containsKey(book_id)){
                target_user_id = user_id;
                user_only_rec.cal_target_rec_book_score(target_user_id);
                all_users.get(user_id).disp_rec_book_score(book_id);
            }
            else{
                System.out.println("Not found book");
            }
        }
        else{
            System.out.println("Not found user");
        }
    }

    public void rec_book(){
        book_only_rec.display();
    }





}
