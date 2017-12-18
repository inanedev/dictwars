package ru.inanedev.dictwars;

/**
 * Created by safronov on 08.12.2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GameViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ZXZX-GameViewHolder";
    public ImageView usergame_competitorAva;
    public TextView usergame_competitorName;
    public TextView usergame_LettersFirst;
    public TextView usergame_LettersLast;
    public TextView usergame_WhoTurn;

    public TextView usergame_LastWord;
    private DatabaseReference mDatabase;
    int id;

    public GameViewHolder(View itemView) {
        super(itemView);
        usergame_competitorAva = itemView.findViewById(R.id.game_competitor_ava);
        usergame_competitorName = itemView.findViewById(R.id.game_competitor_name);
        usergame_LettersFirst = itemView.findViewById(R.id.game_letter_first);
        usergame_LettersLast = itemView.findViewById(R.id.game_letter_last);
        usergame_LastWord = itemView.findViewById(R.id.game_letter_lastword_lastword);
        usergame_WhoTurn = itemView.findViewById(R.id.game_WhoTurn);



    }
    //ava_man

    public void bindToUserGame(UserGame UserGame) {
        Log.d(TAG, "bindToUsergame");

        id = usergame_competitorAva
                .getContext()
                .getResources()
                .getIdentifier("ava_woman", "drawable", usergame_competitorAva.getContext().getPackageName());

        Log.d(TAG, "id before" + id);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(UserGame.Competitor).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");
                if (dataSnapshot.exists()==true) {
                    // System.out.println(TAG + " -->" + dataSnapshot.getValue());
                    usergame_competitorName.setText(dataSnapshot.child("username").getValue().toString());
                   // cUserEmail.setText(dataSnapshot.child("email").getValue().toString());
                    String resPath = dataSnapshot.child("userAva").getValue().toString();
                    Log.d(TAG, "respath -> " + resPath);
                    if (resPath != null)  {
                        id = usergame_competitorAva.getContext()
                                .getResources()
                                .getIdentifier(resPath, "drawable", usergame_competitorAva.getContext().getPackageName());
                        Log.d(TAG, "id in procedure" + id);
                        usergame_competitorAva.setImageResource(id);
                    }

                } else Log.d(TAG, "datasnap is null");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d(TAG, "id after" + id);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(UserGame.WhoTurn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  Log.d(TAG, "onDataChange");
                if (dataSnapshot.exists()==true) {

                    usergame_WhoTurn.setText(dataSnapshot.child("username").getValue().toString());

                } else Log.d(TAG, "datasnap is null");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //usergame_competitorName.setText(UserGame.WhoTurn);
        usergame_LettersFirst.setText(UserGame.LetterFirst);
        usergame_LettersLast.setText(UserGame.LetterLast);
        usergame_LastWord.setText(UserGame.LastWord);
        usergame_WhoTurn.setText(UserGame.WhoTurn);
/*
        if (UserGame.CompetitorAva != null) {
          id = usergame_competitorAva.getContext().getResources().getIdentifier(UserGame.CompetitorAva, "drawable", usergame_competitorAva.getContext().getPackageName());
        }
        */





    }
}