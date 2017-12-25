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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    private String mUserAva;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        mRecycler = findViewById(R.id.usedword_list);
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        usedWord = findViewById(R.id.user_word);
        btnGO = findViewById(R.id.button_user_put_word);
        btnGO.setOnClickListener(this);
        mUserAva=getUserAva();
        Log.d(TAG, "onCreate mUserAva->"+mUserAva);


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
            public void onDataChanged() {
                super.onDataChanged();
                mRecycler.smoothScrollToPosition(mAdapter.getItemCount());
            }

            @Override
            protected void onBindViewHolder(UsedWordsViewHolder viewHolder, int position, UsedWords model) {

                viewHolder.bindToUsedWords(model);
            }

            @Override
            public UsedWordsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

                LayoutInflater inflater;

                switch (viewType) {
                    case MY_WORD:
                         inflater = LayoutInflater.from(viewGroup.getContext());
                        return new UsedWordsViewHolder(inflater.inflate(R.layout.item_usedwords_mine, viewGroup, false));
                    case OPP_WORD:
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
                if (getUid().toString().equals(model.userId.toString())) {
                    return MY_WORD;
                } else {
                    return OPP_WORD;}

            }


        };
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View view) {
        UsedWords mWords = new UsedWords();
        String usedWord_key;
    switch (view.getId()) {
        case R.id.button_user_put_word:

            if (usedWord.getText().length()>0) {
                mWords.userId=getUid();
                mWords.UsedWord=usedWord.getText().toString();
                mWords.userAva=getUserAva();
                mWords.WordScore=getWordScore(usedWord.getText().toString());

                Log.d(TAG, "mWords: "+ mWords.userId +" | "
                        +mWords.userAva +" | "+ mWords.UsedWord +" | "+ mWords.WordScore);

                usedWord_key= mGameReference.child("UsedWords").push().getKey();

                mGameReference.child("UsedWords").child(usedWord_key).setValue(mWords);
                usedWord.setText("");
                usedWord.setEnabled(false);
            }
        }
    }

    private String getWordScore(String s) {
        String wordScore;
        Random rnd = new Random();
        wordScore = "["+rnd.nextInt(100)+"]";
        return wordScore;
    }

    public String getUserAva () {
        Log.d(TAG, "getUserAva() ->");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        mDatabase.child("users").child(getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()==true) {

                    currentUserAva = dataSnapshot.child("userAva").getValue().toString();
                    Log.d(TAG, dataSnapshot.child("userAva").getValue().toString());
                    Log.d(TAG, currentUserAva);
                }
                else Log.d(TAG, "datasnap is null");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                 Log.d(TAG, "getUserAva -> onCancelled");
            }
        });
        return currentUserAva;
    }

    protected void onStart() {
        super.onStart();
        mUserAva=getUserAva();
        if (mAdapter != null) {
            showProgressDialog();
            mAdapter.startListening();
            hideProgressDialog();

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

