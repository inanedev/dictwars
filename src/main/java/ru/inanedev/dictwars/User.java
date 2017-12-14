package ru.inanedev.dictwars;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String userAva;
 //   public String userLogo;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String userAva) {
        this.username = username;
        this.email = email;
        this.userAva=userAva;
    //    this.userLogo=userLogo;
    }

}