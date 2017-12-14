package ru.inanedev.dictwars;

/**
 * Created by safronov on 08.12.2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class GameViewHolder extends RecyclerView.ViewHolder {
    public ImageView usergame_competitorAva;
    public TextView usergame_competitorName;
    public TextView usergame_LettersFirst;
    public TextView usergame_LettersLast;

    public TextView usergame_LastWord;

    public GameViewHolder(View itemView) {
        super(itemView);
        usergame_competitorAva = itemView.findViewById(R.id.game_competitor_ava);
        usergame_competitorName = itemView.findViewById(R.id.game_competitor_name);
        usergame_LettersFirst = itemView.findViewById(R.id.game_letter_first);
        usergame_LettersLast = itemView.findViewById(R.id.game_letter_last);
        usergame_LastWord = itemView.findViewById(R.id.game_letter_lastword_lastword);



    }
    //ava_man

    public void bindToUserGame(UserGame UserGame) {
        int id;
        id = usergame_competitorAva
                .getContext()
                .getResources()
                .getIdentifier("ava_woman", "drawable", usergame_competitorAva.getContext().getPackageName());

        usergame_competitorName.setText(UserGame.WhoTurn);
        usergame_LettersFirst.setText(UserGame.LetterFirst);
        usergame_LettersLast.setText(UserGame.LetterLast);
        usergame_LastWord.setText(UserGame.LastWord);

        if (UserGame.CompetitorAva != null) {
          id = usergame_competitorAva.getContext().getResources().getIdentifier(UserGame.CompetitorAva, "drawable", usergame_competitorAva.getContext().getPackageName());
        }
        usergame_competitorAva.setImageResource(id);




    }
}