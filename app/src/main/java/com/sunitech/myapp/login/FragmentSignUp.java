package com.sunitech.myapp.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunitech.myapp.R;
import com.sunitech.myapp.dbquery.AuthQuery;
import com.sunitech.myapp.helper.GenericTextWatcher;
import com.sunitech.myapp.others.StaticMethods;
import com.sunitech.myapp.restservice.Api;
import com.sunitech.myapp.restservice.response.SignUpResponse;

import org.w3c.dom.Text;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.sunitech.myapp.helper.Constant.AUTH_REQUEST_SIGN_IN;
import static com.sunitech.myapp.others.StaticMethods.isValidEmail;
import static com.sunitech.myapp.others.StaticMethods.isValidPassword;

public class FragmentSignUp extends Fragment implements AuthListener.SendOTPListener {

    private AuthListener authListener;

    public FragmentSignUp(AuthListener authListener) {
        this.authListener = authListener;
    }

    // Sign Up Option
    private RelativeLayout layoutSignUpOption;
    private TextView buttonSignUpWithPhone;
    private Button buttonSignUpWithEmail;
    private TextView textViewAlreadyRegister;
    // Sign In With Phone
    private RelativeLayout layoutSignUpWithPhone;
    private LinearLayout layoutPhoneSection;
    private EditText editTextPhone;
    private Button buttonGetOTP;

    private LinearLayout layoutOTPSection;
    private EditText editTextOTP1;
    private EditText editTextOTP2;
    private EditText editTextOTP3;
    private EditText editTextOTP4;
    private TextView textViewResendOtp;
    private TextView textViewOtpMsg;
    private TextView textViewChangeMobile;
    private Button buttonVerifyAndContinue;

    // Sign In With Email
    private RelativeLayout layoutSignUpWithEmail;
    private EditText editTextEmail;
    private EditText editTextPass1;
    private EditText editTextPass2;
    private Button buttonSignUp;
    // ------------------
    private String tempUserMobile;
    private String tempUserOTP;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        assignAttr(view);
        onButtonAction();

        return view;
    }

    private void assignAttr(View view){
        // Sign Up Option
        layoutSignUpOption = view.findViewById(R.id.rel_layout_sign_up_option);
        buttonSignUpWithPhone = view.findViewById(R.id.button_sign_up_with_phone);
        buttonSignUpWithEmail = view.findViewById(R.id.button_sign_up_with_email_pass);
        textViewAlreadyRegister = view.findViewById(R.id.text_view_log_in);
        // Sign In With Phone
        layoutSignUpWithPhone = view.findViewById(R.id.rel_layout_sign_up_with_phone);
        layoutPhoneSection = view.findViewById(R.id.lin_layout_phone_section);
        buttonGetOTP = view.findViewById(R.id.button_get_otp);

        layoutOTPSection = view.findViewById(R.id.lin_layout_otp_section);
        editTextPhone = view.findViewById(R.id.edit_text_phone);
        editTextOTP1 = view.findViewById(R.id.edit_text_otp_d1);
        editTextOTP2 = view.findViewById(R.id.edit_text_otp_d2);
        editTextOTP3 = view.findViewById(R.id.edit_text_otp_d3);
        editTextOTP4 = view.findViewById(R.id.edit_text_otp_d4);
        textViewResendOtp = view.findViewById(R.id.text_view_resend_otp);
        textViewOtpMsg = view.findViewById(R.id.text_view_otp_send_msg);
        textViewChangeMobile = view.findViewById(R.id.text_view_change_mobile);
        buttonVerifyAndContinue = view.findViewById(R.id.button_verify_continue);
        // Sign In With Email
        layoutSignUpWithEmail = view.findViewById(R.id.rel_layout_sign_up_with_email_pass);
        editTextEmail = view.findViewById(R.id.edit_text_email);
        editTextPass1 = view.findViewById(R.id.edit_text_password1);
        editTextPass2 = view.findViewById(R.id.edit_text_password2);
        buttonSignUp = view.findViewById(R.id.button_sign_up_email);
    }

    private void onButtonAction(){
        // Choose Sign Up Options..
        buttonSignUpWithEmail.setOnClickListener( v->{
            layoutSignUpOption.setVisibility(View.GONE);
            layoutSignUpWithPhone.setVisibility(View.GONE);
            layoutSignUpWithEmail.setVisibility(View.VISIBLE);
        });
        buttonSignUpWithPhone.setOnClickListener( v->{
            layoutSignUpOption.setVisibility(View.GONE);
            layoutSignUpWithPhone.setVisibility(View.VISIBLE);
            layoutSignUpWithEmail.setVisibility(View.GONE);
        });

        textViewAlreadyRegister.setOnClickListener(v -> {
            if (ActivityAuth.AUTH_CODE == AUTH_REQUEST_SIGN_IN) {
                authListener.onBackPressed(null);
            }else{
                authListener.showNextFragment(new FragmentSignIn(authListener));
            }
        });

        // Phone Action
        buttonGetOTP.setOnClickListener( v->{
            if (StaticMethods.isValidPhone(editTextPhone)){
                authListener.showDialog();
                tempUserMobile = editTextPhone.getText().toString();
                // Query To Send OTP..
                AuthQuery.queryToSendOTP(this, tempUserMobile );
            }
        });

        // Query To Resend OTP
        textViewResendOtp.setOnClickListener( v -> {
            AuthQuery.queryToSendOTP(this, tempUserMobile );
        });

        textViewChangeMobile.setOnClickListener(v -> {
            layoutPhoneSection.setVisibility(View.VISIBLE);
            layoutOTPSection.setVisibility(View.GONE);
            if (secThread!=null ){
                secThread.cancel();
            }
        });

        // Verify OTP
        buttonVerifyAndContinue.setOnClickListener(v->{
            String otp = getUserOTP();
            if (otp == null){
                authListener.showToast("Please Enter OTP!");
                return;
            }
            authListener.showDialog();
            //  : Process To Reset Password...
            AuthQuery.queryToVerifyOTP(this, tempUserMobile, otp);
        });

        // Email Action
        buttonSignUp.setOnClickListener( v->{
            if (!isValidEmail(editTextEmail) ){
                return;
            }
            if (!isValidPassword(editTextPass1, editTextPass2)){
                return;
            }

            // TODO : Add request Method --> Next Fragment

        });

    }

    private void setOTPTextWatcher(){
        editTextOTP1.requestFocus();
        editTextOTP1.setText("");
        editTextOTP2.setText("");
        editTextOTP3.setText("");
        editTextOTP4.setText("");
        editTextOTP1.addTextChangedListener(new GenericTextWatcher(getContext(), null, editTextOTP1, editTextOTP2));
        editTextOTP2.addTextChangedListener(new GenericTextWatcher(getContext(), editTextOTP1, editTextOTP2, editTextOTP3));
        editTextOTP3.addTextChangedListener(new GenericTextWatcher(getContext(), editTextOTP2, editTextOTP3, editTextOTP4));
        editTextOTP4.addTextChangedListener(new GenericTextWatcher(getContext(), editTextOTP3, editTextOTP4, null));
    }

    private String getUserOTP(){
        String otpD1 = editTextOTP1.getText().toString().trim();
        if (TextUtils.isEmpty(otpD1)){
            return null;
        }
        String otpD2 = editTextOTP2.getText().toString().trim();
         if (TextUtils.isEmpty(otpD2)){
            return null;
        }
        String otpD3 = editTextOTP3.getText().toString().trim();
         if (TextUtils.isEmpty(otpD3)){
            return null;
        }
        String otpD4 = editTextOTP4.getText().toString().trim();
         if (TextUtils.isEmpty(otpD4)){
            return null;
        }
         return otpD1 + otpD2 + otpD3 + otpD4;
    }
    // Resend Second Text...
    private CountDownTimer secThread;
    private void setTvSeconds(){
        textViewResendOtp.setEnabled( false );
        secThread = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                textViewResendOtp.setText( "Please wait.." + String.valueOf( millisUntilFinished / 1000  ) );
            }
            public void onFinish() {
                textViewResendOtp.setText("Resend OTP");
                textViewResendOtp.setEnabled(true);
            }
        };
        secThread.start();
    }


    private void signUp() {
        // display a progress dialog
        authListener.showDialog();

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        Api.getClient().registration(
                "", "", "",
                "email", new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        // TODO : Success
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // Failed
                    }
                });
    }

    @Override
    public void onSendOtpResponse(boolean isSuccess, @Nullable String responseMsg) {
        authListener.dismissDialog();
//        if ( isSuccess ){
            textViewOtpMsg.setText("We have just send an OTP \n on +91-"+ editTextPhone.getText().toString());
            // TODO : Add request Method
            layoutPhoneSection.setVisibility(View.GONE);
            layoutOTPSection.setVisibility(View.VISIBLE);
            setTvSeconds();
            setOTPTextWatcher();
//        }

    }

    @Override
    public void onVerifyOTPResponse(boolean isSuccess, @Nullable String responseMsg) {
        // Check
        authListener.dismissDialog();
        authListener.showToast(responseMsg);
        // TODO : if Success -> Set Reset Fragment -> User Profile
//        if (isSuccess){
        authListener.resetFragment(new FragmentAddUserProfile( authListener ));
//        }else{
//
//        }
    }



}