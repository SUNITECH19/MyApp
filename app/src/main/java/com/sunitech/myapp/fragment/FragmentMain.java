package com.sunitech.myapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sunitech.myapp.R;
import com.sunitech.myapp.helper.FragmentListener;

import static com.sunitech.myapp.helper.Constant.DIRECTION_LEFT;
import static com.sunitech.myapp.helper.Constant.DIRECTION_RIGHT;

public class FragmentMain extends Fragment implements
        BottomNavigationView.OnNavigationItemSelectedListener{
    private static FragmentMain fragmentMain;

    public FragmentMain(FragmentListener fragmentListener) {
        // Required empty public constructor
        this.fragmentListener = fragmentListener;
    }

    public static FragmentMain getInstance( FragmentListener fragmentListener ){
        if (fragmentMain == null){
            fragmentMain = new FragmentMain( fragmentListener );
        }
        return fragmentMain;
    }

    private int currentDirection = DIRECTION_LEFT;
    private int crrFragmentNo = 0;
    private int crrFragmentID = -1;
    // Variables..
    private FrameLayout frameLayoutMain;
    private BottomNavigationView bottomNavigationView;
    private static FragmentManager fragmentManagerHome;
    private FragmentListener fragmentListener;

    private Fragment selectedFragment = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        frameLayoutMain = view.findViewById(R.id.frame_layout_main );
        bottomNavigationView = view.findViewById(R.id.bottom_nav_bar );

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (fragmentManagerHome == null || fragmentManagerHome.isDestroyed()){
            fragmentManagerHome = getActivity().getSupportFragmentManager();
            setFragmentMain( R.id.bottom_nav_home);
        }else if (selectedFragment != null){
            setFragmentMain( crrFragmentID);
        }

        return view;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (crrFragmentID != item.getItemId()){
            crrFragmentID = item.getItemId();
        }else {
            return false;
        }

        int fragmentNo = 0;
        if (item.getItemId() == R.id.bottom_nav_home){
            selectedFragment = FragmentHome.getInstance( fragmentListener );
            fragmentNo = 0;
        }
        else if( item.getItemId() == R.id.bottom_nav_booking ){
            selectedFragment = FragmentHome.getInstance( fragmentListener );
            fragmentNo = 1;
        }
        else if( item.getItemId() == R.id.bottom_nav_account ){
            selectedFragment = FragmentHome.getInstance( fragmentListener );
            fragmentNo = 3;
        }

        replaceFragment( selectedFragment, getDirection( fragmentNo ) );
        return true;
    }

    private void setFragmentMain(int itemID){
        crrFragmentID = itemID;
        crrFragmentNo = 0;
        switch (itemID){
            case R.id.bottom_nav_home:
//                selectedFragment = new FragmentHome();
                selectedFragment = FragmentHome.getInstance( fragmentListener );
                break;
            case R.id.bottom_nav_booking:
//                selectedFragment = new Fragment2();
                break;
            case R.id.bottom_nav_account:
//                selectedFragment = new FragmentAccount();
                break;
        }
        setFragment();
    }

    private int getDirection( int nextFragmentNo){
        if ( nextFragmentNo > crrFragmentNo ){
            currentDirection = DIRECTION_RIGHT;
        }else{
            currentDirection = DIRECTION_LEFT;
        }
        crrFragmentNo = nextFragmentNo;
        return currentDirection;
    }

    public void setFragment( ) {
        FragmentTransaction fragmentTransaction = fragmentManagerHome.beginTransaction();
        fragmentTransaction.add(frameLayoutMain.getId(), selectedFragment );
        fragmentTransaction.commit();
    }

    private void replaceFragment( Fragment  fragment, int animationDirection ){
        FragmentTransaction fragmentTransaction = fragmentManagerHome.beginTransaction();
        if (animationDirection == DIRECTION_LEFT){
            fragmentTransaction.setCustomAnimations( R.anim.slide_from_left, R.anim.slide_out_from_right  );
        }else{
            fragmentTransaction.setCustomAnimations( R.anim.slide_from_right, R.anim.slide_out_from_left  );
        }
        fragmentTransaction.replace( frameLayoutMain.getId(), fragment );
        fragmentTransaction.commit();
    }


}