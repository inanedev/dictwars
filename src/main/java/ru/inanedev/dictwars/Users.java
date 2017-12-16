package ru.inanedev.dictwars;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class Users {
    public String uid;
    public String username;
    public String email;
    public String userAva;
    public String userStatus;
    //   public String userLogo;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users (String uid , String username, String email, String userAva, String userStatus) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.userAva=userAva;
        this.userStatus=userStatus;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("email", email);
        result.put("userAva", userAva);
        result.put("userStatus", userStatus);
        return result;
    }
    // [END post_to_map]
}