package ru.inanedev.dictwars;

/**
 * Created by safronov on 08.12.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class GameViewHolder extends RecyclerView.ViewHolder {
    public ImageView competitorAva;
    public TextView competitorName;
    public TextView gameLettersFirst;
    public TextView gameLettersLast;

    public TextView authorView;

    public TextView bodyView;

    public GameViewHolder(View itemView) {
        super(itemView);
        competitorAva = itemView.findViewById(R.id.game_competitor_ava);
        competitorName = itemView.findViewById(R.id.game_competitor_name);
        gameLettersFirst = itemView.findViewById(R.id.game_letter_first);
        gameLettersLast = itemView.findViewById(R.id.game_letter_last);



        bodyView = itemView.findViewById(R.id.post_body);
    }

    public void bindToPost(UserGame UserGame, View.OnClickListener starClickListener) {
        titleView.setText(UserGame.GameId);
        authorView.setText(UserGame.WhoTurn);

        bodyView.setText(UserGame.LastWord);


    }
}