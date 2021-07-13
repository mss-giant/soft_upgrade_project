public class test_user_set {
    public static void main(String[] args) {
        User_controller u_ctr = new User_controller();
        u_ctr.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        u_ctr.connect_and_select_data("user_name");
        u_ctr.connect_and_select_data("score");
        u_ctr.connect_and_select_data("friend");
        u_ctr.test_disp();
    }
}
