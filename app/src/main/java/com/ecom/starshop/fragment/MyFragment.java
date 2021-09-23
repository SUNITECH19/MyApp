package com.ecom.starshop.fragment;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.ecom.starshop.helper.Listener;

public class MyFragment extends Fragment implements Listener.FragmentListener {

    @Override
    public void showDialog() {
        Toast.makeText( getContext(), "Show Dialog", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissDialog() {
        Toast.makeText( getContext(), "Dismiss Dialog", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText( getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
