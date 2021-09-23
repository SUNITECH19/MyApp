package com.ecom.starshop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.ecom.starshop.R;
import com.ecom.starshop.helper.Listener;

public class FragmentHome extends MyFragment {

    private static FragmentHome fragmentHome;

    public FragmentHome(Listener.FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    public static FragmentHome getInstance(Listener.FragmentListener fragmentListener){
        if (fragmentHome == null) {
            fragmentHome = new FragmentHome(fragmentListener);
        }
        return fragmentHome;
    }

    private Listener.FragmentListener fragmentListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    private void popUpOption(){
        PopupMenu popupMenu = new PopupMenu( getContext(), getView() );
        popupMenu.getMenuInflater().inflate( R.menu.menu_item_popup, popupMenu.getMenu() );
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

}