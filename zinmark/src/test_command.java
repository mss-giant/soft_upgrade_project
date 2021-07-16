public class test_command {
    public static void main(String[] args) {
        Command_Scanner scan_cmd = new Command_Scanner();
        while(true){
            scan_cmd.scan_command();
            if(scan_cmd.get_exit_call()){
                break;
            }
            if(scan_cmd.get_display_user_info_call()){
                System.out.println("disp user info");
            }
            if(scan_cmd.get_display_book_info_call()){
                System.out.println("disp book info");
            }
            if(scan_cmd.get_rec_user_call()){
                System.out.println("rec user call");
            }
            if(scan_cmd.get_rec_user_book_call()){
                System.out.println("rec user book call");
            }
            if(scan_cmd.get_add_user_call()){
                System.out.println("add user call");
            }
            if(scan_cmd.get_add_user_book_score_call()){
                System.out.println("add user book score call");
            }
            if(scan_cmd.get_add_book_call()){
                System.out.println("add book call");
            }
            if(scan_cmd.get_update_user_name_call()){

            }
            if(scan_cmd.get_update_user_book_score_call()){

            }
            if(scan_cmd.get_update_book_name_call()){

            }
            if(scan_cmd.get_delete_user_call()){

            }
            if(scan_cmd.get_delete_book_call()){

            }
            if(scan_cmd.get_search_mode_call()){
                for(String s : scan_cmd.get_input_words()){
                    System.out.print(s+" ");
                }
                System.out.println();
            }
        
        
        }
    }
}
