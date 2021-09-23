package com.ecom.starshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ecom.starshop.ConnectionCheck;
import com.ecom.starshop.R;
import com.ecom.starshop.databinding.ActivityMainBinding;
import com.ecom.starshop.fragment.addproduct.DialogFragmentAddProduct;
import com.ecom.starshop.helper.ConnectionListener;
import com.ecom.starshop.helper.DialogListener;
import com.ecom.starshop.helper.Listener;
import com.ecom.starshop.login.ActivityAuth;
import com.ecom.starshop.helper.StaticMethods;
import com.ecom.starshop.myclass.MyOptionDialog;

import static com.ecom.starshop.helper.Constant.AUTH_REQUEST_SIGN_IN;
import static com.ecom.starshop.helper.Constant.FRAGMENT_DIALOG;
import static com.ecom.starshop.helper.Constant.FRAGMENT_ID;
import static com.ecom.starshop.helper.StaticMethods.setConnectionListener;
import static com.ecom.starshop.helper.StaticMethods.showCustomToast;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class ActivityMain extends AppCompatActivity implements ConnectionListener
        , Listener.AppFragmentChangeListener
        , Listener.FragmentListener {

    // Welcome Activity....
    private TextView textViewNoConnection;
    private ConnectionCheck connectionCheck;
    public static ConnectionListener connectionListener;

    //    private Button buttonAuth;
    public static FragmentManager fragmentManagerMain;


    // Progress Dialog...
    private Dialog dialog;

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        activityMainBinding.executePendingBindings();

        // Assign Fragment Manager...
        fragmentManagerMain = getSupportFragmentManager();
        dialog = StaticMethods.getProgressDialog(this, "Please Wait...");
        // Assign Connection Listener
        setConnectionListener(this);

        textViewNoConnection = activityMainBinding.textViewNoInternet;
        connectionCheck = new ConnectionCheck(this, this);
        onConnectChange( connectionCheck.isInternetConnected( this ));

        setToolbar();
        onButtonClicked();

    }

    private void setToolbar(){
        activityMainBinding.includeToolBar.appToolbar.setTitleMargin( 100, 0, 0, 0);
        setSupportActionBar( activityMainBinding.includeToolBar.appToolbar );
        try {
            getSupportActionBar().setTitle("Admin Panel");
//            getSupportActionBar().setLogo(getResources().getDrawable( R.drawable.logo ));
        }catch (NullPointerException ignored){ }
    }

    private void gotoAuthActivity(){
        ActivityAuth.AUTH_CODE = AUTH_REQUEST_SIGN_IN;
        startActivity(new Intent(this, ActivityAuth.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // To Home ToolBar Options
        getMenuInflater().inflate( R.menu.menu_layout_home,menu);

        // Check First whether any item in cart or not...
        // if any item has in cart...
        MenuItem cartItem = menu.findItem( R.id.menu_notification_main );
        cartItem.setActionView( R.layout.layout_menu_notification_badge );
        TextView badgeCartCount = cartItem.getActionView().findViewById( R.id.tv_menu_badge_count );
//        if (cartItemModelList.size() > 0){
//            badgeCartCount.setVisibility( View.VISIBLE );
//            badgeCartCount.setText( String.valueOf( UserDataQuery.cartItemModelList.size() ) );
//        }else{
//            badgeCartCount.setVisibility( View.GONE );
//        }
        badgeCartCount.setVisibility( View.VISIBLE );
        badgeCartCount.setText("29");
        cartItem.getActionView().setOnClickListener(v -> {
            // GOTO : My cart
            startActivity( new Intent( ActivityMain.this, ActivityNotification.class ));
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.menu_my_profile ){
            //
        }else if( id == R.id.menu_settings ){
            //
        }else if( id == R.id.menu_log_out ){
            onLogOutAction();
        }
        return super.onOptionsItemSelected( item );
    }


//   ------------- Helping Methods   ---------------------------------------------------------------
    private void onButtonClicked(){
        activityMainBinding.linearLayoutMyProductsBtn.setOnClickListener(v -> {
//            Toast.makeText( this, "ActivityProducts ", Toast.LENGTH_SHORT).show();
            startActivity( new Intent( ActivityMain.this, ActivityProducts.class ));
        });
        activityMainBinding.linearLayoutManageHomePageBtn.setOnClickListener(v -> {
            startActivity( new Intent( ActivityMain.this, ActivityManageHome.class ));
        });

        // On Powered By Action
        activityMainBinding.linearLayoutPoweredBy.setOnClickListener( v -> onPoweredByClick() );
    }

    private void onLogOutAction(){
        MyOptionDialog myOptionDialog = new MyOptionDialog( this, "Log Out !", "Do you want to log out?" );
        myOptionDialog.onCreateDialog( new DialogListener.OnClickListener() {
            @Override
            public void onPositiveAction(DialogListener dialog, int id) {
                // On Positive Button Action...
                Log.e("DIALOG", "Positive...");
                dialog.dismiss();
                // Clean User info...
//                        SharedPrefManager.getInstance(ActivityMain.this).logout();
                // TODO : Clean User Data
//                        gotoAuthActivity();
            }

            @Override
            public void onNegativeAction(DialogListener dialog, int id) {
                // On Negative Button Action...
                Log.e("DIALOG", "Negative...");
                dialog.dismiss();
            }
        });
        myOptionDialog.show();

    }


//   ------------- Connection Change ---------------------------------------------------------------
    @Override
    public void onConnectChange(boolean isConnected) {
        runOnUiThread(() -> {
            if (isConnected){
                textViewNoConnection.setText("Connection is Back...");
                textViewNoConnection.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                new CountDownTimer(2500, 1000) {
                    public void onTick(long millisUntilFinished) { }
                    public void onFinish() {
                        textViewNoConnection.setVisibility(View.GONE);
                    }
                }.start();
            }else {
                textViewNoConnection.setVisibility(View.VISIBLE);
                textViewNoConnection.setText("No Internet Connection!");
                textViewNoConnection.setBackgroundColor(getResources().getColor(R.color.colorRed));
            }
        });
    }

//   ------------- Dialog, Toast, Snack ------------------------------------------------------------
    @Override
    public void showDialog() {
        if (dialog!=null){
            dialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        showCustomToast( this, msg );
    }

//   ------------- Fragment View -------------------------------------------------------------------

    @Override
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManagerMain.beginTransaction();
        fragmentTransaction.add( activityMainBinding.frameLayoutMainActivity.getId(), fragment );
        fragmentTransaction.commit();
    }

    @Override
    public void setNextFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManagerMain.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_from_right, R.anim.slide_out_from_left, R.anim.slide_from_left, R.anim.slide_out_from_right );
        fragmentTransaction.replace( activityMainBinding.frameLayoutMainActivity.getId(), fragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void resetFragment(Fragment fragment) {
        for(int i = fragmentManagerMain.getBackStackEntryCount(); i>=0; i--){
            fragmentManagerMain.popBackStack();
        }
        activityMainBinding.frameLayoutMainActivity.removeAllViews();
        FragmentTransaction fragmentTransaction = fragmentManagerMain.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_from_left, R.anim.slide_out_from_right  );
        fragmentTransaction.replace(activityMainBinding.frameLayoutMainActivity.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void setCommunicateFragment( int fragmentID){
        Intent intent = new Intent(ActivityMain.this, ActivityShowFragment.class);
        intent.putExtra( FRAGMENT_ID, fragmentID );
        this.startActivity( intent );
    }

    //-------------- Powered By --------------------------------------------------------------------
    private void onPoweredByClick(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        // Show Add Product Testing...
        DialogFragment dialogFragment = DialogFragmentAddProduct.getInstance( null, null );
        dialogFragment.show( fragmentManager, FRAGMENT_DIALOG );
    }

}