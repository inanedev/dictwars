package ru.inanedev.dictwars;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by safronov on 14.12.2017.
 */

public class NewGameActivity extends BaseActivity{
    private static final String TAG = "NewGameActivity";
    private static final String player1 = "lFvBvp8f6VeTWZgl5rDqr6r6k0d2";
    private static final String player2 = "zmwEIPzGLhcIt3J3wbymH7PQiam1";

    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]
        Log.d(TAG, "onCreate. Start Push");
        String key = mDatabase.push().getKey();
        Log.d(TAG, "onCreate. Start player1 AVA");
        mDatabase.child("UserGame").child(player1).child(key).child("CompetitorAva").setValue("ava_woman");
        mDatabase.child("UserGame").child(player1).child(key).child("LastWord").setValue("ПриродА");
        mDatabase.child("UserGame").child(player1).child(key).child("LetterFirst").setValue("П");
        mDatabase.child("UserGame").child(player1).child(key).child("LetterLast").setValue("А");
        mDatabase.child("UserGame").child(player1).child(key).child("WhoTurn").setValue(player2);

        Log.d(TAG, "onCreate. Start player2 AVA");
        mDatabase.child("UserGame").child(player2).child(key).child("CompetitorAva").setValue("ava_man");
        mDatabase.child("UserGame").child(player2).child(key).child("LastWord").setValue("ПриродА");
        mDatabase.child("UserGame").child(player2).child(key).child("LetterFirst").setValue("П");
        mDatabase.child("UserGame").child(player2).child(key).child("LetterLast").setValue("А");
        mDatabase.child("UserGame").child(player2).child(key).child("WhoTurn").setValue(player2);

        Log.d(TAG, "onCreate. Start Games WHO TURN");
        mDatabase.child("Games").child(key).child("WhoTurn").setValue(player2);

        finish();
    }


}
