package ru.inanedev.dictwars;

/**
 * Created by safronov on 08.12.2017.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class UserGame {

    public String uid;
    public String GameId;
    public String WhoTurn;
    public String LetterFirst;
    public String LetterLast;
    public String LastWord;
    public String Competitor;


    public UserGame() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public UserGame(String uid, String GameId, String WhoTurn, String LetterFirst , String LetterLast ,String LastWord, String Competitor) {
        this.uid = uid;
        this.GameId = GameId;
        this.WhoTurn = WhoTurn;
        this.LetterFirst = LetterFirst;
        this.LetterLast = LetterLast;
        this.LastWord = LastWord;
        this.Competitor = Competitor;
    }

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
        result.put("Competitor", Competitor);
        return result;
    }
    // [END post_to_map]

}