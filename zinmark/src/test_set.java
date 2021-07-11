public class test_set {
    public static void main(String[] args) {
        Book_info_set bis = new Book_info_set();
        Book_only_Recommend bos = new Book_only_Recommend();
        bis.set_database_info("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass");
        bis.connect_and_select_data("url"); 
        bis.connect_and_select_data("link"); 
        bis.connect_and_select_data("location");
        bis.serch_reference_to_me();
        
        bos.set_books(bis.get_books());
        bos.cal_loop();
        bos.set_serch_word("list queue bfs");
        bos.cal_word_score();
        bos.set_book_word_score_0_1();
        bos.display();
        //bis.test_disp_book_info();
        //bis.test_disp_reference_book();
        //bis.test_disp_book_has_word();
        
        //bis.test_disp_reference_to_me();


    }
}
