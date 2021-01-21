package com.sunitech.myapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.sunitech.myapp.ConnectionCheck;
import com.sunitech.myapp.R;
import com.sunitech.myapp.fragment.FragmentMain;
import com.sunitech.myapp.helper.ConnectionListener;
import com.sunitech.myapp.helper.FragmentListener;
import com.sunitech.myapp.helper.Listener;
import com.sunitech.myapp.login.ActivityAuth;
import com.sunitech.myapp.others.StaticMethods;

import static com.sunitech.myapp.helper.Constant.AUTH_REQUEST_SIGN_IN;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_ID;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_MAIN;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_SHOW_REVIEWS;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_SHOW_URL_ABOUT_US;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_SHOW_URL_CONTACT_US;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_SHOW_URL_HELP;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_SHOW_URL_LEGAL;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_SHOW_URL_PRIVACY_POLICY;
import static com.sunitech.myapp.others.StaticMethods.setConnectionListener;
import static com.sunitech.myapp.others.StaticMethods.showCustomToast;

public class ActivityMain extends AppCompatActivity implements ConnectionListener
        , Listener.AppFragmentChangeListener
        , NavigationView.OnNavigationItemSelectedListener
        , FragmentListener {

    // Welcome Activity....
    private TextView textView;
    private ConnectionCheck connectionCheck;
    public static ConnectionListener connectionListener;

    //    private Button buttonAuth;
    // ToolBar ----
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    // Nav Header ( Drawer )
    private TextView drawerUserName;
    private TextView drawerUserEmail;

    private FrameLayout frameLayoutMain;
    public static FragmentManager fragmentManagerMain;

    private Button buttonAuth;

    private static int CURRENT_FRAGMENT = FRAGMENT_MAIN;
    // Progress Dialog...
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        // Assign Fragment Manager...
        fragmentManagerMain = getSupportFragmentManager();
        dialog = StaticMethods.getProgressDialog(this, "Please Wait...");
        // Assign Connection Listener
        setConnectionListener(this);

        textView = findViewById(R.id.text_view_no_internet);
        connectionCheck = new ConnectionCheck(this);
        onConnectChange( connectionCheck.isInternetConnected( this ));
        connectionListener = this;

        buttonAuth = findViewById(R.id.button_auth);
        buttonAuth.setOnClickListener(v->{
            Log.e("AUTH", "Starting Auth Activity");
            startActivity(new Intent(ActivityMain.this, ActivityAuth.class));
        });

    }

    private void setToolbar(){
        toolbar = findViewById( R.id.appToolbar );
        drawer = findViewById( R.id.drawer_layout );
        navigationView = findViewById( R.id.nav_view_side );
        setSupportActionBar( toolbar );
        try {
            getSupportActionBar().setDisplayShowTitleEnabled( true );
            getSupportActionBar().setTitle("Panditji");
//            getSupportActionBar().setLogo(getResources().getDrawable( R.drawable.logo ));
        }catch (NullPointerException ignored){ }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar,
                R.string.nav_open , R.string.nav_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        // setNavigationItemSelectedListener()...
        navigationView.setNavigationItemSelectedListener( ActivityMain.this );
    }

    private void gotoAuthActivity(){
        ActivityAuth.AUTH_CODE = AUTH_REQUEST_SIGN_IN;
        startActivity(new Intent(this, ActivityAuth.class));
        finish();
    }

    private void setHomeFragment( ){
        CURRENT_FRAGMENT = FRAGMENT_MAIN;
        resetFragment( FragmentMain.getInstance( this ) );
//        resetFragment( new FragmentMain() );

        navigationView.getMenu().getItem( 0 ).setChecked( true );
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
//            gotoAuthActivity();
//        }else {
//            if (user == null)
//                user = SharedPrefManager.getInstance(this).getUser();
//            setDrawerInfo();
//        }
    }

    @Override
    public void onBackPressed() {
        if (drawer!=null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (CURRENT_FRAGMENT != FRAGMENT_MAIN) {
            setHomeFragment();
        }else {
            super.onBackPressed();
        }
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
        return super.onOptionsItemSelected(item);
    }

    private MenuItem prevMenuItem;
    int itemId = -1;
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
//        setMenuChecked( item );
        itemId = item.getItemId();
        if (itemId == R.id.menu_side_home){
            if (CURRENT_FRAGMENT != FRAGMENT_MAIN){
                setMenuChecked( item );
                setHomeFragment();
                prevMenuItem = item;
                return true;
            }
        }else
        if (itemId == R.id.menu_my_wallet){
            // TODO :
            return false;
        }else
        if (itemId == R.id.menu_my_review){
            //  My Reviews...
            Intent intent = new Intent( ActivityMain.this, ActivityShowFragment.class);
            intent.putExtra( FRAGMENT_ID, FRAGMENT_SHOW_REVIEWS );
            startActivity( intent );
            return false;
        } else
        if (itemId == R.id.menu_log_out){
            AlertDialog.Builder alertDialog = StaticMethods.getAlertDialog( ActivityMain.this,
                    "Log Out !", "Do you want to Log Out?");
            alertDialog.setCancelable( false );
            alertDialog
                    .setPositiveButton("YES", (dialog, which) -> {
                        // Clean User info...
//                        SharedPrefManager.getInstance(ActivityMain.this).logout();
                        // TODO : Clean User Data

//                        gotoAuthActivity();
                        dialog.dismiss();
                    })
                    .setNegativeButton("NO", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();

            setMenuChecked( null );
            return false;
        }else
        if (itemId == R.id.menu_about_us){
            setCommunicateFragment( FRAGMENT_SHOW_URL_ABOUT_US );
//            setMenuChecked( item );
            return false;
        }else
        if (itemId == R.id.menu_contact_us){
            setCommunicateFragment( FRAGMENT_SHOW_URL_CONTACT_US );
//            setMenuChecked( item );
            return false;
        }else
        if (itemId == R.id.menu_help){
            setCommunicateFragment( FRAGMENT_SHOW_URL_HELP );
//            setMenuChecked( item );
            return false;
        }else
        if (itemId == R.id.menu_privacy_policy){
            setCommunicateFragment( FRAGMENT_SHOW_URL_PRIVACY_POLICY );
//            setMenuChecked( item );
            return false;
        }else
        if (itemId == R.id.menu_legal){
            setCommunicateFragment( FRAGMENT_SHOW_URL_LEGAL );
//            setMenuChecked( item );
            return false;
        }else
        if (itemId == R.id.menu_share){
            Intent inviteIntent = new Intent( Intent.ACTION_SEND );
            inviteIntent.setType("text/plain");
            String body = "Hey,\n " +
                    "I am using \"" + getResources().getString(R.string.app_name) + "\" App from last few week. " +
                    "You can also join by using below link\n" +
                    "Link : https://clickpanditji.com \n Thank You \n";
            String subject = getResources().getString(R.string.app_name);
            inviteIntent.putExtra( Intent.EXTRA_SUBJECT,subject );
            inviteIntent.putExtra( Intent.EXTRA_TEXT, body );
            startActivity( Intent.createChooser( inviteIntent,"Share By..." ) );
            setMenuChecked( null );
            return false;
        }

        return false;
    }
    private void setMenuChecked(@Nullable MenuItem item){
        if ( item == null){
            return;
        }
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        item.setChecked(true);
        prevMenuItem = item;
    }

    @Override
    public void onConnectChange(boolean isConnected) {
        if (isConnected){
            textView.setText("Connection is Back...");
            textView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            new CountDownTimer(2500, 1000) {
                public void onTick(long millisUntilFinished) { }
                public void onFinish() {
                    textView.setVisibility(View.GONE);
                }
            }.start();
        }else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Internet Connection!");
            textView.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }
    }

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

    @Override
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManagerMain.beginTransaction();
        fragmentTransaction.add( frameLayoutMain.getId(), fragment );
        fragmentTransaction.commit();
    }

    @Override
    public void setNextFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManagerMain.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_from_right, R.anim.slide_out_from_left, R.anim.slide_from_left, R.anim.slide_out_from_right );
        fragmentTransaction.replace( frameLayoutMain.getId(), fragment );
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void resetFragment(Fragment fragment) {
        for(int i = fragmentManagerMain.getBackStackEntryCount(); i>=0; i--){
            fragmentManagerMain.popBackStack();
        }
        frameLayoutMain.removeAllViews();
        FragmentTransaction fragmentTransaction = fragmentManagerMain.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_from_left, R.anim.slide_out_from_right  );
        fragmentTransaction.replace(frameLayoutMain.getId(), fragment);
        fragmentTransaction.commit();
    }


    private void setCommunicateFragment( int fragmentID){
        Intent intent = new Intent(ActivityMain.this, ActivityShowFragment.class);
        intent.putExtra( FRAGMENT_ID, fragmentID );
        this.startActivity( intent );
    }


}