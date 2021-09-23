package com.ecom.starshop.login.introduce;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecom.starshop.R;

import static com.ecom.starshop.login.introduce.SharedPrefManager.SHARED_PREF_INTRO_ID;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class FragmentIntroduceApp extends Fragment {

    public FragmentIntroduceApp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduce_app, container, false);
    }

    protected void onFinishFragment() {
        // the next time the user launches the app.
        SharedPreferences.Editor sharedPreferencesEditor =
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        sharedPreferencesEditor.putBoolean( SHARED_PREF_INTRO_ID, true);
        sharedPreferencesEditor.apply();
    }



}