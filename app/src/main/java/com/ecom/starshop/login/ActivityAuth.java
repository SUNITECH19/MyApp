package com.ecom.starshop.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ecom.starshop.R;
import com.ecom.starshop.helper.StaticMethods;

import static com.ecom.starshop.helper.Constant.AUTH_REQUEST_SIGN_IN;
import static com.ecom.starshop.helper.Constant.AUTH_REQUEST_SIGN_UP;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class ActivityAuth extends AppCompatActivity implements AuthListener{

    public static int AUTH_CODE = AUTH_REQUEST_SIGN_IN;

    private FrameLayout frameLayoutAuth;
    public static FragmentManager fragmentManager;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        progressDialog = StaticMethods.getProgressDialog(this, "Please Wait...");
        frameLayoutAuth = findViewById(R.id.frame_layout_auth);
        fragmentManager = getSupportFragmentManager();

        setFrameLayout();

    }

    private void setFrameLayout(){
        switch (AUTH_CODE){
            case AUTH_REQUEST_SIGN_IN:
                setFrameLayoutAuth(new FragmentSignIn(this));
                break;
            case AUTH_REQUEST_SIGN_UP:
                setFrameLayoutAuth(new FragmentSignUp(this));
                break;
            default:
                break;
        }
    }

    private void setFrameLayoutAuth(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayoutAuth.getId(), fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void resetFragment(Fragment fragment){
        for(int i = fragmentManager.getBackStackEntryCount(); i>=0; i--){
            fragmentManager.popBackStack();
        }
        frameLayoutAuth.removeAllViews();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_from_right, R.anim.slide_out_from_left );
        fragmentTransaction.replace(frameLayoutAuth.getId(), fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showNextFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_from_right, R.anim.slide_out_from_left, R.anim.slide_from_left, R.anim.slide_out_from_right );
        fragmentTransaction.replace(frameLayoutAuth.getId(), fragment);
        fragmentTransaction.addToBackStack( null );
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed(@Nullable String msg) {
        onBackPressed();
    }


    @Override
    public void showDialog() {
        if (progressDialog != null){
            progressDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        StaticMethods.showCustomToast(this, msg);
    }

}