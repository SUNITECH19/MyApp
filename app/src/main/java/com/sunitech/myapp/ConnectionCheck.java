package com.sunitech.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.sunitech.myapp.activity.ActivityMain;
import com.sunitech.myapp.helper.ConnectionListener;

import static com.sunitech.myapp.activity.ActivityMain.connectionListener;

public class ConnectionCheck extends BroadcastReceiver {

    public static boolean IS_CONNECT = false;

    public ConnectionCheck(ConnectionListener listener) {
        connectionListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (connectionListener != null)
            connectionListener.onConnectChange(isInternetConnected( context ));
        else{
            Log.e("ERROR", "NOT Found");
        }
    }

    public boolean isInternetConnected(Context context){
        boolean isConnected = false;
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Log.e("CON", "CON Back");
                isConnected = true;
            } else {
                Log.e("CON", "CON Lost");
                isConnected = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IS_CONNECT = isConnected;
            return isConnected;
        }
    }

}
