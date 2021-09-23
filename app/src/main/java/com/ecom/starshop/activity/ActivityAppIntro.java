package com.ecom.starshop.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.ecom.starshop.ActivityWelcome;
import com.ecom.starshop.R;
import com.ecom.starshop.adaptor.AdaptorAppIntro;
import com.ecom.starshop.databinding.ActivityAppIntroBinding;
import com.ecom.starshop.dbquery.DBQuery;
import com.ecom.starshop.helper.DBListener;
import com.ecom.starshop.helper.IntroListener;
import com.ecom.starshop.model.ModelAppIntro;

import java.util.ArrayList;
import java.util.List;

import static com.ecom.starshop.helper.Constant.DATA_KEY_BOOLEAN;
import static com.ecom.starshop.login.introduce.SharedPrefManager.SHARED_PREF_INTRO_ID;

public class ActivityAppIntro extends AppCompatActivity implements IntroListener, DBListener.OnCheckDatabaseListener {
    private final String TAG = "ActivityAppIntro";

    private ActivityAppIntroBinding activityAppIntro;

    private boolean isDatabaseSet = false;
    private boolean isCheckedDatabase = false;

    private List<ModelAppIntro> introList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAppIntro = DataBindingUtil.setContentView( this, R.layout.activity_app_intro );
        activityAppIntro.setActionListener( this );
        activityAppIntro.executePendingBindings();

        // TODO : Add Intro Page...
        introList.add( new ModelAppIntro("Product Management", "You have control on your product, You can show, hide, update etc"
                , R.drawable.ic_outline_price_change_24, false ));
        introList.add( new ModelAppIntro("Order Management", "New order, confirm, out for delivery all information you will have."
                , R.drawable.ic_outline_dvr_product_list_24, false ));
        introList.add( new ModelAppIntro("User Feedback", "You can check customer feedback inside your app"
                , R.drawable.ic_baseline_person_24, false ));

        AdaptorAppIntro adaptorAppIntro = new AdaptorAppIntro(introList);
        activityAppIntro.viewPagerIntro.setAdapter(adaptorAppIntro);
        activityAppIntro.viewPagerIntro.addOnPageChangeListener(onPageChangeListener);

        addBottomDots( 0 );

        //  Now Check Whether Database is Set??
        DBQuery.checkIsDatabaseSet( this );
    }

    private ViewPager.OnPageChangeListener onPageChangeListener =
            new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // Ignore
                }

                @Override
                public void onPageSelected(int position) {
                    // Update UI of DOTs
                    addBottomDots( position );

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // Ignore
                }
            };

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[introList.size()];
        activityAppIntro.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor( getResources().getColor( R.color.colorGray ));
            activityAppIntro.layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor( R.color.colorAccent ));
    }

    private int getItem(int position){
        return activityAppIntro.viewPagerIntro.getCurrentItem() + position;
    }

    private void goToMainActivity(){
        if ( isDatabaseSet && isCheckedDatabase ){
            // Update SharedPreference....
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(this);
            sharedPreferences.edit().putBoolean(SHARED_PREF_INTRO_ID, true).apply();

            // Send User to Main Activity...
            startActivity(new Intent(this, ActivityMain.class));
            finish();
        }else {
            // Show UI to Database set...
            Intent intent = new Intent( this, ActivitySetDatabase.class);
            intent.putExtra( DATA_KEY_BOOLEAN, isCheckedDatabase );
            startActivity( intent );
        }
    }

    @Override
    public void onSkipClick() {
        goToMainActivity();
    }

    @Override
    public void onNextClick() {
        int current = getItem(+1);
        if (current < introList.size()) {
            // move to next screen
            activityAppIntro.viewPagerIntro.setCurrentItem(current);
        } else {
            goToMainActivity();
        }
    }

    @Override
    public void onCheckDatabaseSet(@Nullable String responseMsg, boolean isSet) {
        isCheckedDatabase = true;
        isDatabaseSet = isSet;
        Log.d( TAG, " Check Database : " + responseMsg );
    }


}