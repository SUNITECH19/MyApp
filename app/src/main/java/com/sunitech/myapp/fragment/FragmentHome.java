package com.sunitech.myapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunitech.myapp.R;
import com.sunitech.myapp.helper.FragmentListener;

public class FragmentHome extends Fragment {

    private static FragmentHome fragmentHome;

    public FragmentHome(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    public static FragmentHome getInstance(FragmentListener fragmentListener){
        if (fragmentHome == null) {
            fragmentHome = new FragmentHome(fragmentListener);
        }
        return fragmentHome;
    }

    private FragmentListener fragmentListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }


}