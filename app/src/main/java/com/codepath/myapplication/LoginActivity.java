package com.codepath.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    String userId;
    AccessToken t;
    String imageUrl;
    ImageView i;
    LoginButton loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // FACEBOOK LOGIN

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment

        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        t = loginResult.getAccessToken();
                        userId = t.getUserId();
                        imageUrl = "https://graph.facebook.com/" + userId + "/picture?type=square";
                        i = (ImageView) findViewById(R.id.profilePic);
                        Picasso.with(getBaseContext())
                                .load(imageUrl)
                                .into(i);

                        Intent i = new  Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("image", imageUrl);
                        startActivity(i);
                    }

                    @Override
                    public void onCancel() {
                        Log.d("d","dead");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("d","dead");
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }
}
