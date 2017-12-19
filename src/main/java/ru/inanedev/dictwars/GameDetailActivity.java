package ru.inanedev.dictwars;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by safronov on 13.12.2017.
 */

public class GameDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ZXZX-GameDetailActivity";
    public static final String EXTRA_GAME_KEY = "game_key";
    private String mGameKey;
    private DatabaseReference mGameReference;

    char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъsьэюя".toCharArray();
    int[] aWeight = new int[] {1,3,1,3,2,1,3,5,5,1,4,
                               2,2,2,1,1,2,1,1,1,2,10,
                               5,5,5,8,10,10,4,3,8,8,3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        int rnd = new Random().nextInt(alphabet.length);
        int i;
        for (i=0; i<alphabet.length; i++) {
            Log.d(TAG, "Буквы словаря " + i + " --> " +alphabet[i] + " aWeight:"+aWeight[i]);
        }


        // Get post key from intent
        mGameKey = getIntent().getStringExtra(EXTRA_GAME_KEY);
        Log.d(TAG, mGameKey);

        if (mGameKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        mGameReference = FirebaseDatabase.getInstance().getReference()
                .child("Games").child(mGameKey);


       // mUsedWordsReference = FirebaseDatabase.getInstance().getReference()
       //         .child("post-comments").child(mPostKey);
/*
        // Initialize Views
        mAuthorView = findViewById(R.id.post_author);
        mTitleView = findViewById(R.id.post_title);
        mBodyView = findViewById(R.id.post_body);
        mCommentField = findViewById(R.id.field_comment_text);
        mCommentButton = findViewById(R.id.button_post_comment);
        mCommentsRecycler = findViewById(R.id.recycler_comments);

        mCommentButton.setOnClickListener(this);
        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));
*/


    }


    @Override
    public void onClick(View view) {

    }
}
