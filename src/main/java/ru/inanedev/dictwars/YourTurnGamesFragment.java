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

public class YourTurnGamesFragment extends GameListFragment  {
    private FirebaseAuth mAuth;

    public YourTurnGamesFragment() {}

    private FirebaseUser mUser;

    private static final String TAG = "ZXZX-YourTurnFragment: ";
    @Override

    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();


        Query yourTurnGamesQuery = databaseReference.child("UserGame").child(mUser.getUid()).orderByChild("WhoTurn").equalTo(mUser.getUid());
        // [END recent_posts_query]

        Log.d(TAG, "QueryResult: " +yourTurnGamesQuery.toString() );
        return yourTurnGamesQuery;
    }
}
