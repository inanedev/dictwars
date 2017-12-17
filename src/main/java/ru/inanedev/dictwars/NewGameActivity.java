package ru.inanedev.dictwars;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.Random;

/**
 * Created by safronov on 14.12.2017.
 */

public class NewGameActivity extends BaseActivity {
    private static final String TAG = "ZXZX-NewGameActivity";
    private static final String player1 = "lFvBvp8f6VeTWZgl5rDqr6r6k0d2";
    private static final String player2 = "zmwEIPzGLhcIt3J3wbymH7PQiam1";

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Users, UserViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private String mUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = findViewById(R.id.user_list);
        mUser = FirebaseAuth.getInstance().getUid();

       // mRecycler.setHasFixedSize(true);
        Log.d(TAG, "current user --> "+mUser);
        //Log.d(TAG, "this --> "+ NewGameActivity.this.toString());
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);

        Query userQuery = mDatabase.child("users");

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(userQuery, Users.class)
                .build();

        Log.d(TAG, "QUERY->"+userQuery.toString());

        mAdapter = new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                Log.d(TAG, "onCreateViewHolder -> " + viewGroup.getContext().toString());
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new UserViewHolder(inflater.inflate(R.layout.item_users, viewGroup, false));
               // mAdapter.getItemCount();

            }

            @Override
            protected void onBindViewHolder(UserViewHolder viewHolder, int position, final Users model) {
                final DatabaseReference userRef = getRef(position);
                //Log.d(TAG, "onBindViewHolder" + viewHolder.getItemViewType());
                // Set click listener for the whole post view
                final String userKey = userRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        //TODO: Start A game with selected user

                        //Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                        //intent.putExtra(GameDetailActivity.EXTRA_POST_KEY, postKey);
                        //startActivity(intent);
                       // Log.d(TAG, "userRef -> "+ userKey);

                      //   Log.d(TAG, "onCreate. Start Push");
        String GameKey = mDatabase.push().getKey();
        //Log.d(TAG, "onCreate. Start player1 AVA");

        int rnd = new Random().nextInt(2);
       // Log.d(TAG, "Randomiser" + rnd);

        String WhoTurn;
        if (rnd==0) {WhoTurn=mUser;} else {WhoTurn=userKey;}

        //mDatabase.child("UserGame").child(mUser).child(GameKey).child("CompetitorAva").setValue("ava_woman");
        mDatabase.child("UserGame").child(mUser).child(GameKey).child("LastWord").setValue("ПриродА");
        mDatabase.child("UserGame").child(mUser).child(GameKey).child("LetterFirst").setValue("П");
        mDatabase.child("UserGame").child(mUser).child(GameKey).child("LetterLast").setValue("А");
        mDatabase.child("UserGame").child(mUser).child(GameKey).child("WhoTurn").setValue(WhoTurn);
        mDatabase.child("UserGame").child(mUser).child(GameKey).child("Competitor").setValue(userKey);

        //Log.d(TAG, "onCreate. Start player2 AVA");

        //mDatabase.child("UserGame").child(userKey).child(GameKey).child("CompetitorAva").setValue("ava_man");
        mDatabase.child("UserGame").child(userKey).child(GameKey).child("LastWord").setValue("ПриродА");
        mDatabase.child("UserGame").child(userKey).child(GameKey).child("LetterFirst").setValue("П");
        mDatabase.child("UserGame").child(userKey).child(GameKey).child("LetterLast").setValue("А");
        mDatabase.child("UserGame").child(userKey).child(GameKey).child("WhoTurn").setValue(WhoTurn);
        mDatabase.child("UserGame").child(userKey).child(GameKey).child("Competitor").setValue(mUser);

        //Log.d(TAG, "onCreate. Start Games WHO TURN");
        mDatabase.child("Games").child(GameKey).child("WhoTurn").setValue(WhoTurn);

        finish();
                    }
                });
                Log.d(TAG, mUser +"____"+ userKey);
                if (mUser.toString() != userKey.toString() ) {
                    viewHolder.bindToUser(model);
                }

            }
        };

        //Log.d(TAG, "onCreate. Before setAdapter");
        mRecycler.setAdapter(mAdapter);
        //Log.d(TAG, "onCreate. After setAdapter");

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "onStart");
        if (mAdapter != null) {
            mAdapter.startListening();
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
