package ru.inanedev.dictwars;

/**
 * Created by safronov on 08.12.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class GameViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public GameViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        bodyView = itemView.findViewById(R.id.post_body);
    }

    public void bindToPost(UserGame UserGame, View.OnClickListener starClickListener) {
        titleView.setText(UserGame.GameId);
        authorView.setText(UserGame.WhoTurn);

        bodyView.setText(UserGame.LastWord);

        starView.setOnClickListener(starClickListener);
    }
}