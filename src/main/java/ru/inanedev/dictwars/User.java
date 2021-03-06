package ru.inanedev.dictwars;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String userAva;
    public String userStatus;
 //   public String userLogo;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String userAva, String userStatus) {
        this.username = username;
        this.email = email;
        this.userAva=userAva;
        this.userStatus=userStatus;

    }
    /*
    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("GameId", GameId);
        result.put("WhoTurn", WhoTurn);
        result.put("LetterFirst", LetterFirst);
        result.put("LetterLast", LetterLast);
        result.put("LastWord", LastWord);
        result.put("CompetitorAva", CompetitorAva);
        return result;
    }*/
    // [END post_to_map]
}