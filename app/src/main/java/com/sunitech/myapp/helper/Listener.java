package com.sunitech.myapp.helper;

import androidx.fragment.app.Fragment;

public interface Listener {

    interface AppFragmentChangeListener{
        void setFragment(Fragment fragment);
        void setNextFragment(Fragment fragment);
        void resetFragment(Fragment fragment);
    }

    interface FragmentChangeListener {
        void setFragment(Fragment fragment);
        void setNextFragment(Fragment fragment);
//        void setBackFragment(Fragment fragment);
    }



}
