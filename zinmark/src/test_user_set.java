package src;

import src.User_tool.*;

import java.util.ArrayList;
import java.util.Collections;

import src.Book_tool.*;

public class test_user_set {
    public static void main(String[] args) {
        Book_setter_from_DB bis = new Book_setter_from_DB();
        bis.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        bis.connect_and_select_data("url"); 
        bis.connect_and_select_data("link"); 
        bis.connect_and_select_data("location");
        bis.connect_and_select_data("word");
        bis.connect_and_select_data("serial");
        bis.serch_reference_to_me();

        System.out.println("test");


        User_setter_from_DB u_ctr = new User_setter_from_DB();
        User_only_Recommendation u_rec = new User_only_Recommendation();
        u_ctr.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        u_ctr.connect_and_select_data("user_name");
        u_ctr.connect_and_select_data("score");
        u_ctr.connect_and_select_data("friend");
        u_ctr.connect_and_select_data("serial");

        ArrayList<Integer> book_ids = new ArrayList<Integer>();
        book_ids.addAll(bis.get_books().keySet());
        Collections.sort(book_ids);
        //for(Integer i : book_ids){
        //    System.out.println(i);
        //}
        
        u_rec.set_users(u_ctr.get_users());
        u_rec.set_books(book_ids);
        int target_user_id = 1;
        //u_rec.set_number_of_books(5);
        u_rec.cal_Similar_score();
        u_rec.set_friend_of_friend();
        u_rec.cal_target_rec_book_score(target_user_id);
        u_rec.all_display(target_user_id);
        System.out.println(u_ctr.get_next_user_num());

        //u_ctr.test_disp();
    }
}
