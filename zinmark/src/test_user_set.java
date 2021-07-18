package src;

import src.User_tool.*;

public class test_user_set {
    public static void main(String[] args) {
        User_setter_from_DB u_ctr = new User_setter_from_DB();
        User_only_Recommendation u_rec = new User_only_Recommendation();
        u_ctr.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        u_ctr.connect_and_select_data("user_name");
        u_ctr.connect_and_select_data("score");
        u_ctr.connect_and_select_data("friend");
        u_ctr.connect_and_select_data("serial");

        
        u_rec.set_users(u_ctr.get_users());
        int target_user_id = 1;
        u_rec.set_number_of_books(5);
        u_rec.cal_Similar_score();
        u_rec.set_friend_of_friend();
        u_rec.cal_target_rec_book_score(target_user_id);
        u_rec.all_display(target_user_id);
        System.out.println(u_ctr.get_next_user_num());

        //u_ctr.test_disp();
    }
}
