package com.ecom.starshop.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ecom.starshop.R;
import com.ecom.starshop.helper.StaticMethods;

import static com.ecom.starshop.helper.Constant.AUTH_REQUEST_SIGN_UP;
import static com.ecom.starshop.helper.Constant.KEY_EMAIL;
import static com.ecom.starshop.helper.Constant.KEY_PHONE;
import static com.ecom.starshop.helper.StaticMethods.isFieldNotEmpty;
import static com.ecom.starshop.helper.StaticMethods.isValidEmail;
import static com.ecom.starshop.helper.StaticMethods.isValidPhone;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class FragmentSignIn extends Fragment {

    private AuthListener authListener;

    public FragmentSignIn(AuthListener authListener) {
        this.authListener = authListener;
    }

    private EditText editTextPhone;
    private EditText editTextPassword;
    private TextView textViewForgotPasswordBtn;
    private TextView textViewRegisterBtn;
    private Button buttonSignIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        editTextPhone = view.findViewById(R.id.edit_text_phone);
        editTextPassword = view.findViewById(R.id.edit_text_password);
        textViewForgotPasswordBtn = view.findViewById(R.id.text_view_forgot_password);
        textViewRegisterBtn = view.findViewById(R.id.text_view_register_here);
        buttonSignIn = view.findViewById(R.id.button_sign_in);

        onButtonClicked();
        return view;
    }

    private void onButtonClicked(){
        // Sign In Button
        buttonSignIn.setOnClickListener(v -> {
            String keyEmailPhone = KEY_PHONE;
            String emailPhone = editTextPhone.getText().toString();
            if (StaticMethods.isInputNumbers( emailPhone )){
                if (!isValidPhone(editTextPhone)){
                    return;
                }
            }else{
                if (!isValidEmail( editTextPhone )){
                    return;
                }
                keyEmailPhone = KEY_EMAIL;
            }

            if (isValidPhone(editTextPhone) && isFieldNotEmpty(editTextPassword)){
                // TODO : Sign In
            }
        });
        // Forgot Password
        textViewForgotPasswordBtn.setOnClickListener(v -> {
            authListener.showNextFragment(new FragmentForgetPass(authListener));
        });

        // Register Or Sign Up
        textViewRegisterBtn.setOnClickListener(v -> {
            if (ActivityAuth.AUTH_CODE == AUTH_REQUEST_SIGN_UP) {
                authListener.onBackPressed(null);
            }else{
                authListener.showNextFragment(new FragmentSignUp(authListener));
            }
        });
    }



}