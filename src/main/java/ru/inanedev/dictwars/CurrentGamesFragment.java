package ru.inanedev.dictwars;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by safronov on 08.12.2017.
 */

public class CurrentGamesFragment extends GameListFragment  {
    private FirebaseAuth mAuth;

    public CurrentGamesFragment() {}

    private FirebaseUser mUser;
    private static final String TAG = "ZXZX-CurGamesFragment: ";
    @Override

    public Query getQuery(DatabaseReference databaseReference) {
        // [START currentGamesQuery]
        // All games in uId Child that are not finished yet.

        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();


        Query currentGamesQuery = databaseReference.child("UserGame").child(mUser.getUid()).orderByChild("isFinished").equalTo(false);
        // [END recent_posts_query]
        return currentGamesQuery;
    }
}

