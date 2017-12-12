package ru.inanedev.dictwars;

import android.support.v4.app.Fragment;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by safronov on 08.12.2017.
 */

class CurrentGamesFragment extends GameListFragment  {
    public CurrentGamesFragment() {}
    private FirebaseUser mUser;
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys



        Query recentGamesQuery = databaseReference.child("user_games").child(mUser.getUid())
                .limitToFirst(20);
        // [END recent_posts_query]

        return recentGamesQuery;
    }
}
