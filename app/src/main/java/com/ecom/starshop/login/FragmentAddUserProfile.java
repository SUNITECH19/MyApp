package com.ecom.starshop.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ecom.starshop.R;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class FragmentAddUserProfile extends Fragment {

    private AuthListener authListener;

    public FragmentAddUserProfile(AuthListener authListener) {
        // Required empty public constructor
        this.authListener = authListener;
    }

    private EditText editTextUserName;
    private EditText editPassword1;
    private EditText editPassword2;
    private Button buttonSaveContinue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user_profile, container, false);

        editTextUserName = view.findViewById(R.id.edit_text_user_name);
        editPassword1 = view.findViewById(R.id.edit_text_password1);
        editPassword2 = view.findViewById(R.id.edit_text_password2);
        buttonSaveContinue = view.findViewById(R.id.button_save_continue);

        return view;
    }
}