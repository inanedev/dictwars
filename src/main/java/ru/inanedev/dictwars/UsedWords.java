package ru.inanedev.dictwars;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by safronov on 22.12.2017.
 */

public class UsedWords {
    public String userId;
    public String userAva;
    public String UsedWord;
    public String WordScore;

    public UsedWords() {
        // Default constructor required for calls to DataSnapshot.getValue(UsedWords.class)
    }

    public UsedWords (String userId , String userAva, String UsedWord, String WordScore) {
        this.userId = userId;
        this.WordScore = userAva;
        this.UsedWord = UsedWord;
        this.WordScore=WordScore;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("WordScore", WordScore);
        result.put("UsedWord", UsedWord);
        result.put("WordScore", WordScore);
        return result;
    }
}
