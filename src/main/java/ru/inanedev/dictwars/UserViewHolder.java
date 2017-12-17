package ru.inanedev.dictwars;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by safronov on 15.12.2017.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ZXZX-UserViewHolder";
    public ImageView user_userava;
    public TextView user_username;
    public ImageView user_userstatus;


    public UserViewHolder(View itemView) {

        super(itemView);

        user_userava = itemView.findViewById(R.id.item_user_ava);
        user_username = itemView.findViewById(R.id.item_user_name);
        user_userstatus = itemView.findViewById(R.id.item_user_status);
}


    public void bindToUser(Users Users) {
        Log.d(TAG, "bindToUser ->" + Users.userStatus+" -- " + Users.email + " -- " +Users.username);
        int id_ava;
        int id_status;
        id_ava = user_userava
                .getContext()
                .getResources()
                .getIdentifier("ava_woman", "drawable", user_userava.getContext().getPackageName());


        id_status = user_userstatus
                .getContext()
                .getResources()
                .getIdentifier("user_online","drawable", user_userstatus.getContext().getPackageName());

        if (Users.userStatus != null) {
            id_status = user_userstatus.getContext().getResources().getIdentifier(Users.userStatus,"drawable", user_userstatus.getContext().getPackageName());
        }

        if (Users.userAva != null) {
            id_ava = user_userava.getContext().getResources().getIdentifier(Users.userAva, "drawable", user_userava.getContext().getPackageName());
        }

        user_username.setText(Users.username);
        user_userava.setImageResource(id_ava);
        user_userstatus.setImageResource(id_status);






    }
}