package ru.inanedev.dictwars;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by safronov on 22.12.2017.
 */

public class UsedWordsViewHolder extends RecyclerView.ViewHolder{

    public ImageView uw_userAva;
    public TextView uw_UsedWord;
    public TextView uw_WordScore;

    public UsedWordsViewHolder(View itemView) {
        super(itemView);

        uw_userAva = itemView.findViewById(R.id.item_game_user_ava);
        uw_UsedWord = itemView.findViewById(R.id.item_game_usedword);
        uw_WordScore = itemView.findViewById(R.id.item_game_word_score);

    }

    public void bindToUsedWords(UsedWords UsedWords) {
    int id_ava;
        id_ava = uw_userAva
                .getContext()
                .getResources()
                .getIdentifier("girl_1", "drawable", uw_userAva.getContext().getPackageName());
        if (UsedWords.userAva != null) {
            id_ava = uw_userAva.getContext().getResources().getIdentifier(UsedWords.userAva, "drawable", uw_userAva.getContext().getPackageName());
        }

        uw_userAva.setImageResource(id_ava);
        uw_UsedWord.setText(UsedWords.UsedWord);
        uw_WordScore.setText(UsedWords.WordScore);

    }
}
