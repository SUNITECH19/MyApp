package com.sunitech.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sunitech.myapp.activity.ActivityMain;
import com.sunitech.myapp.dbquery.DBRef;
import com.sunitech.myapp.helper.ConnectionListener;
import com.sunitech.myapp.login.ActivityAuth;

public class ActivityWelcome extends AppCompatActivity implements ConnectionListener {

    private TextView textViewNoConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textViewNoConnection = findViewById(R.id.text_view_no_internet);


        startActivity(new Intent(this, ActivityMain.class));
        finish();

        // Check Connection And Also if Connected Then Shift to Main Activity...!
//        onConnectChange( new ConnectionCheck( this ).isInternetConnected( this ));
//
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//        new MyFirebaseMessagingService().retrieveCurrentToken();
//        goToAuthActivity();

    }

    private void goToAuthActivity(){
//        startActivity(new Intent(ActivityWelcome.this, ActivityMain.class));
//        finish();

        //if user is already logged in openeing the profile activity
        if (DBRef.getCurrentUser() != null ) {
            Log.d("CHECK", "User is already Login");

            // TODO :  Now Check Whether Database is Set??

            startActivity(new Intent(this, ActivityMain.class));
            finish();
        }else{
            Log.d("CHECK", "User Not login");
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) { }
                public void onFinish() {
                    startActivity(new Intent(ActivityWelcome.this, ActivityAuth.class));
                    finish();
                }
            }.start();
        }

    }

    @Override
    public void onConnectChange(boolean isConnected) {
        if (isConnected){
            textViewNoConnection.setText("Connection is Back...");
            textViewNoConnection.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            new CountDownTimer(2500, 1000) {
                public void onTick(long millisUntilFinished) { }
                public void onFinish() {
                    textViewNoConnection.setVisibility(View.GONE);
                    goToAuthActivity();
                }
            }.start();
        }else {
            textViewNoConnection.setVisibility(View.VISIBLE);
            textViewNoConnection.setText("No Internet Connection!");
            textViewNoConnection.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
    }


}