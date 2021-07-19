package src.User_tool;

import java.util.ArrayList;
import java.util.Map;

public class User_only_Recommendation {
    private Map<Integer,User> users;
    private Book_Recommendation book_rec;

    private int target_user_id;
    //private int number_of_books;

    private UnionFindTree_EX union_ex;

    private ArrayList<Integer> book_ids;



    public User_only_Recommendation(){
    }

    public void set_users(Map<Integer,User> users){
        this.users = users;
    }

    //public void set_number_of_books(int number_of_books){
    //    this.number_of_books = number_of_books;
    //}

    public void set_books(ArrayList<Integer> book_ids){
        this.book_ids = book_ids;
    }

    public void cal_Similar_score(){
        for(User u1 : users.values()){
            for(User u2 : users.values()){
                if(!u1.equals(u2)){
                    SimilarScore similar_score = new SimilarScore();
                    similar_score.set_users(u1, u2);
                    u1.set_similar_opponent_score(u2, similar_score.get_similar_score());
                }
            }
        }
    }

    public void set_friend_of_friend(){
        union_ex = new UnionFindTree_EX();

        for(User u : users.values()){
            union_ex.Make_Set(u);
        }

        for(User u_me : users.values()){
            for(User other : u_me.get_User_friends().keySet()){
                if(u_me.check_User_friends(other)){
                    union_ex.Union(u_me, other);
                }
            }
        }


        for(User u1 : users.values()){
            for(User u2 : users.values()){
                if(!u1.equals(u2)){
                    if(union_ex.judge_friends(u1, u2)){
                        u1.set_User_friends(u2, true);
                        u2.set_User_friends(u1, true);
                    }
                }
            }
        }
    }

    public void cal_target_rec_book_score(int target_user_id){
        book_rec = new Book_Recommendation();
        book_rec.set_users(users);
        //book_rec.set_number_of_books(number_of_books);
        //System.out.println("OK");
        book_rec.set_books(book_ids);
        book_rec.set_targetuser(users.get(target_user_id));
        book_rec.cal_rec_score();
        users.get(target_user_id).set_rec_book_score(book_rec);
        
    }

    public void all_display(int target_user_id){
        System.out.println();
        //System.out.print("    Score");
        System.out.format("%15s", "Score");
        for(Integer i : book_ids){
            System.out.print("  B"+(i)+"  ");
        }
        System.out.println();
        
        for(Integer id : users.keySet()){
            users.get(id).disp_book_score(users.get(target_user_id), this.book_ids);
        }        
        users.get(target_user_id).disp_rec_book_score_sort();
    }










}
