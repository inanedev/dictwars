package ru.inanedev.dictwars;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Random;

/**
 * Created by safronov on 13.12.2017.
 */

public class GameDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ZXZX-GameDetail";
    public static final String EXTRA_GAME_KEY = "game_key";
    public static final int MY_WORD = 0;
    public static final int OPP_WORD = 1;

    private TextView usedWord;
    private Button btnGO;

    private String mGameKey;
    private DatabaseReference mGameReference;

    char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъsьэюя".toCharArray();
    int[] aWeight = new int[]{1, 3, 1, 3, 2, 1, 3, 5, 5, 1, 4,
            2, 2, 2, 1, 1, 2, 1, 1, 1, 2, 10,
            5, 5, 5, 8, 10, 10, 4, 3, 8, 8, 3};
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<UsedWords, UsedWordsViewHolder> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        mRecycler = findViewById(R.id.usedword_list);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        usedWord = findViewById(R.id.user_word);
        btnGO = findViewById(R.id.button_user_put_word);
        btnGO.setOnClickListener(this);


//        int i;
//        for (i = 0; i < alphabet.length; i++) {
//            Log.d(TAG, "Буквы словаря " + i + " --> " + alphabet[i] + " aWeight:" + aWeight[i]);
 //       }


        // Get post key from intent
        mGameKey = getIntent().getStringExtra(EXTRA_GAME_KEY);

        Log.d(TAG, mGameKey);

        if (mGameKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        // Initialize Database
        mGameReference = FirebaseDatabase.getInstance().getReference()
                .child("Games").child(mGameKey);

        Query usedwordsQuery = mGameReference.child("UsedWords");

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<UsedWords>()
                .setQuery(usedwordsQuery, UsedWords.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<UsedWords, UsedWordsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(UsedWordsViewHolder viewHolder, int position, UsedWords model) {
                Log.d(TAG, "onBindViewHolder");
                viewHolder.bindToUsedWords(model);
            }

            @Override
            public UsedWordsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

                LayoutInflater inflater;

                switch (viewType) {
                    case MY_WORD:
                        Log.d(TAG, "onCreateViewHolder -> MY_WORD");
                         inflater = LayoutInflater.from(viewGroup.getContext());
                        return new UsedWordsViewHolder(inflater.inflate(R.layout.item_usedwords_mine, viewGroup, false));
                    case OPP_WORD:
                        Log.d(TAG, "onCreateViewHolder -> OPP_WORD");
                         inflater = LayoutInflater.from(viewGroup.getContext());
                        return new UsedWordsViewHolder(inflater.inflate(R.layout.item_usedwords_competitor, viewGroup, false));
                }

               // mAdapter.getItemCount();
                Log.d(TAG, "Before Return Null");
                return null;
            }

            // determine which layout to use for the row
            @Override
            public int getItemViewType(int position) {
                UsedWords model = getItem(position);

                Log.d(TAG, "GetItemViewType");


                if (getUid().toString().equals(model.userId.toString())) {
                    Log.d(TAG, MY_WORD+ " --> " + model.userId + "|" + getUid());
                    return MY_WORD;
                } else {
                    Log.d(TAG, OPP_WORD+ " --> " + model.userId + "|" + getUid());
                    return OPP_WORD;}

            }


        };
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick");
        UsedWords mWords = new UsedWords();
        String usedWord_key;
    switch (view.getId()) {
        case R.id.button_user_put_word:
            Log.d(TAG, "Push put word " + usedWord.getText());
            if (usedWord.getText().length()>0) {
                mWords.userId=getUid();
                mWords.UsedWord=usedWord.getText().toString();
                mWords.userAva=getUserAva();
                mWords.WordScore=getWordScore(usedWord.getText().toString());

                usedWord_key= mGameReference.child("UsedWords").push().getKey();

                Log.d(TAG, mWords.toMap().toString());
                mGameReference.child("UsedWords").child(usedWord_key).setValue(mWords);

            }
        }
    }

    private String getWordScore(String s) {
        String wordScore;
        Random rnd = new Random();
        wordScore = "["+rnd.nextInt(100)+"]";
        return wordScore;
    }

    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart");
        if (mAdapter != null) {
            showProgressDialog();
            mAdapter.startListening();
            hideProgressDialog();
            // Log.d(TAG, "ADAPTER ->"+mAdapter.getItemCount());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }
}

