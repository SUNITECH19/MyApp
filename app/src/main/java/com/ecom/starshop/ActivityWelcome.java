package com.ecom.starshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ecom.starshop.activity.ActivityAppIntro;
import com.ecom.starshop.activity.ActivityMain;
import com.ecom.starshop.dbquery.DBRef;
import com.ecom.starshop.helper.ConnectionListener;
import com.ecom.starshop.login.ActivityAuth;

import static com.ecom.starshop.login.introduce.SharedPrefManager.SHARED_PREF_INTRO_ID;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class ActivityWelcome extends AppCompatActivity implements ConnectionListener {
    private final String TAG = "ActivityWelcome";

    private TextView textViewNoConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        textViewNoConnection = findViewById(R.id.textViewNoInternet);

        // Check Connection And Also if Connected Then Shift to Main Activity...!
//        onConnectChange( new ConnectionCheck( this ).isInternetConnected( this ));

        // TODO : Check Login ?..
        startActivity(new Intent(this, ActivityMain.class));
        finish();

//        checkUserLogin();

//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//        new MyFirebaseMessagingService().retrieveCurrentToken();
//

    }

    private void checkUserLogin(){
        //if user is already logged in opening the profile activity
        if (DBRef.getCurrentUser() != null ) {
            Log.d( TAG, "User is already Login");
            setIntroduceApp();

        }else{
            Log.d( TAG, "User Not login");
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) { }
                public void onFinish() {
                    startActivity(new Intent(ActivityWelcome.this, ActivityAuth.class));
                    finish();
                }
            }.start();
        }
    }

    private void setIntroduceApp(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // Check if we need to display
        if (!sharedPreferences.getBoolean( SHARED_PREF_INTRO_ID, false)) {
            // Send User to App Intro...
            Intent intent = new Intent(this, ActivityAppIntro.class);
            startActivity( intent );
        }else{
            // Send User To Home Activity...
            startActivity(new Intent(ActivityWelcome.this, ActivityMain.class));
            finish();
        }
    }

    @Override
    public void onConnectChange(boolean isConnected) {
        runOnUiThread( ()->{
            if (isConnected){
                textViewNoConnection.setText("Connection is Back...");
                textViewNoConnection.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                new CountDownTimer(2500, 1000) {
                    public void onTick(long millisUntilFinished) { }
                    public void onFinish() {
                        textViewNoConnection.setVisibility(View.GONE);
                        checkUserLogin();
                    }
                }.start();
            }else {
                textViewNoConnection.setVisibility(View.VISIBLE);
                textViewNoConnection.setText("No Internet Connection!");
                textViewNoConnection.setBackgroundColor(getResources().getColor(R.color.colorRed));
            }
        });
    }


}