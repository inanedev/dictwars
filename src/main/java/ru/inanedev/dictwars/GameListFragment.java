package ru.inanedev.dictwars;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

public abstract class GameListFragment extends Fragment {

    private static final String TAG = "ZXZX-GameListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<UserGame, GameViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public GameListFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_game_list, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = rootView.findViewById(R.id.game_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query gameQuery = getQuery(mDatabase);


        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<UserGame>()
                .setQuery(gameQuery, UserGame.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<UserGame, GameViewHolder>(options) {

            @Override
            public GameViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new GameViewHolder(inflater.inflate(R.layout.item_game, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(GameViewHolder viewHolder, int position, final UserGame model) {
                final DatabaseReference gameRef = getRef(position);

                // Set click listener for the whole post view
                final String gameKey = gameRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch GameDetailActivity
                        //TODO: INTENT A GAME
                        Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                        Log.d(TAG, "onClick: " + gameKey);
                        intent.putExtra(GameDetailActivity.EXTRA_GAME_KEY, gameKey);
                        startActivity(intent);
                    }
                });

                viewHolder.bindToUserGame(model);

            }
        };
        mRecycler.setAdapter(mAdapter);
    }

               // [START post_stars_transaction]

    // [END post_stars_transaction]


    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }


    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}