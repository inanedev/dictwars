package ru.inanedev.dictwars;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUserActivity extends BaseActivity implements ViewSwitcher.ViewFactory {
    public static final String EXTRA_USER_MAIL = "userMail";
    private static final String TAG = "ZXZX-CreateUser:";

    private ImageSwitcher mImageSwitcher;

    int position = 0;
    private int[] mImageIds = {
            R.drawable.ava_man
            ,R.drawable.boy
            , R.drawable.boy_1
            , R.drawable.boy_2
            , R.drawable.boy_3
            , R.drawable.boy_4
            , R.drawable.boy_5
            , R.drawable.boy_6
            , R.drawable.boy_7
            , R.drawable.boy_8
            , R.drawable.boy_9
            , R.drawable.boy_10
            , R.drawable.boy_11
            , R.drawable.boy_12
            , R.drawable.boy_13
            , R.drawable.boy_14
            , R.drawable.boy_15
            , R.drawable.boy_16
            , R.drawable.boy_17
            , R.drawable.boy_18
            , R.drawable.boy_19
            , R.drawable.boy_20
            , R.drawable.boy_21
            , R.drawable.boy_22
            , R.drawable.ava_woman
            , R.drawable.girl
            , R.drawable.girl_1
            , R.drawable.girl_2
            , R.drawable.girl_3
            , R.drawable.girl_4
            , R.drawable.girl_5
            , R.drawable.girl_6
            , R.drawable.girl_7
            , R.drawable.girl_8
            , R.drawable.girl_9
            , R.drawable.girl_10
            , R.drawable.girl_11
            , R.drawable.girl_12
            , R.drawable.girl_13
            , R.drawable.girl_14
            , R.drawable.girl_15
            , R.drawable.girl_16
            , R.drawable.girl_17
            , R.drawable.girl_18
            , R.drawable.girl_19
            , R.drawable.girl_20
            , R.drawable.girl_21
            , R.drawable.girl_22
            };
    private EditText mUserName;
    private String mEmail;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        mImageSwitcher = findViewById(R.id.imageSwitcher);
        mUserName = findViewById(R.id.userCreate_username);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mImageSwitcher.setFactory(this);
        Animation inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(1000);
        Animation outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(1000);
        mImageSwitcher.setInAnimation(inAnimation);
        mImageSwitcher.setOutAnimation(outAnimation);
        mImageSwitcher.setImageResource(mImageIds[0]);
        mImageSwitcher.setBackgroundResource(0);
        mEmail = getIntent().getStringExtra(EXTRA_USER_MAIL);
    }

        //TODO: create user - ava + username



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonForward:
                setPositionNext();
                mImageSwitcher.setImageResource(mImageIds[position]);
                Log.d(TAG, "IMAGERESOURCE -> "
                        + mImageSwitcher.getResources().getResourceEntryName(mImageIds[position]).toString());
                break;
            case R.id.buttonPrev:
                setPositionPrev();
                mImageSwitcher.setImageResource(mImageIds[position]);
                Log.d(TAG, "IMAGERESOURCE -> "
                        + mImageSwitcher.getResources().getResourceEntryName(mImageIds[position]).toString());
                 break;
            case R.id.userCreate_create:
                    writeNewUser(getUid()
                            , mUserName.getText().toString()
                            , mEmail
                            , mImageSwitcher.getResources().getResourceEntryName(mImageIds[position]).toString() );


            default:
                break;
        }
    }

    private void writeNewUser(String userId, String name, String email, String ava) {
        User user = new User(name, email, ava, "user_online");
        //Log.d(TAG, "start new user activity");

        mDatabase.child("users").child(userId).setValue(user);
        startActivity(new Intent(CreateUserActivity.this, MainActivity.class));
        finish();

    }

    public void setPositionNext() {
        position++;
        if (position > mImageIds.length - 1) {
            position = 0;
        }
    }

    public void setPositionPrev() {
        position--;
        if (position < 0) {
            position = mImageIds.length - 1;
        }
    }

    @Override
    public View makeView() {

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(0xFF000000);
        return imageView;
        //return null;
    }
}
