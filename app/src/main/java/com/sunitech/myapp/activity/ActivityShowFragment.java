package com.sunitech.myapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sunitech.myapp.R;
import com.sunitech.myapp.helper.FragmentListener;
import com.sunitech.myapp.helper.Listener;
import com.sunitech.myapp.others.StaticMethods;

import static com.sunitech.myapp.helper.Constant.FRAGMENT_CHANGE_PASSWORD;
import static com.sunitech.myapp.helper.Constant.FRAGMENT_ID;
import static com.sunitech.myapp.others.StaticMethods.showCustomToast;

public class ActivityShowFragment extends AppCompatActivity implements FragmentListener, Listener.FragmentChangeListener {

    private int fragmentId = -1;

    private FragmentManager fragmentManager;
    private FrameLayout frameLayoutShowFragment;

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_show_fragment);

        setToolbar();
        frameLayoutShowFragment = findViewById(R.id.frame_layout_show_fragment);

        fragmentId = getIntent().getIntExtra( FRAGMENT_ID, -1);

        fragmentManager = getSupportFragmentManager();
        dialog = StaticMethods.getProgressDialog( this, "Please Wait...");
//        setFrameLayoutShowFragment();

    }

    private void setToolbar(){
        Toolbar toolbar = findViewById( R.id.appToolbar );
        setSupportActionBar( toolbar );
        try {
            getSupportActionBar().setDisplayShowTitleEnabled( true );
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Panditji");
        }catch (NullPointerException ignored){ }
    }

    private void setFrameLayoutShowFragment( ){
        switch (fragmentId){
            case FRAGMENT_CHANGE_PASSWORD:
//                setFragment( new FragmentChangePassword( this, 7999597410L ));
                getSupportActionBar().setTitle("Password Change");
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void showDialog() {
        if (dialog!= null){
            dialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (dialog!= null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        showCustomToast( this, msg );
    }

    @Override
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameLayoutShowFragment.getId(), fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setNextFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.slide_from_right, R.anim.slide_out_from_left, R.anim.slide_from_left, R.anim.slide_out_from_right );
        fragmentTransaction.replace(frameLayoutShowFragment.getId(), fragment);
        fragmentTransaction.addToBackStack( null );
        fragmentTransaction.commit();
    }


}
