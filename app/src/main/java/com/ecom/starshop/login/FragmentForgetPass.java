package com.ecom.starshop.login;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecom.starshop.R;
import com.ecom.starshop.dbquery.AuthQuery;
import com.ecom.starshop.helper.GenericTextWatcher;
import com.ecom.starshop.helper.StaticMethods;

import static com.ecom.starshop.helper.Constant.AUTH_REQUEST_SIGN_IN;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class FragmentForgetPass extends Fragment implements
        ForgetPassListener
    , AuthListener.SendOTPListener {

    private AuthListener authListener;

    public FragmentForgetPass(AuthListener authListener) {
        this.authListener = authListener;
    }

    // Sign In With Phone
    private RelativeLayout layoutResetPassword;
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

    private TextView textViewGetEmail;
    // ------------------
    private String tempUserMobile;
    private String tempUserOTP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_pass, container, false);

        textViewGetEmail = view.findViewById(R.id.text_view_get_email_link);

        assignAttr(view);
        onButtonAction();

        return view;
    }

    private void assignAttr(View view){
        // Sign In With Phone
        layoutResetPassword = view.findViewById(R.id.rel_layout_reset_pass);
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
    }

    private void onButtonAction(){
        // Get Email
        textViewGetEmail.setOnClickListener( v -> {
            showEmailDialog();
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

        textViewResendOtp.setOnClickListener( v -> {
            // Query To Send OTP..
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
    }

    private void showEmailDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_reset_pass_with_email );
//        dialog.setCancelable( false );
        dialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        EditText editTextEmail = dialog.findViewById(R.id.edit_text_email);
        Button btnGetEmail = dialog.findViewById(R.id.button_get_email);

        btnGetEmail.setOnClickListener(v -> {
            if (StaticMethods.isValidEmail(editTextEmail)){
                authListener.showDialog();
                AuthQuery.queryToSendEmail( this, editTextEmail.getText().toString());
            }
        });

        dialog.show();
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

    @Override
    public void onResendEmailResponse(boolean isSuccess, String responseMsg) {
        authListener.dismissDialog();
        if (isSuccess){
            // TODO :
            ActivityAuth.AUTH_CODE = AUTH_REQUEST_SIGN_IN;
            authListener.resetFragment( null );
        }else{
            authListener.showToast(responseMsg);
        }
    }

    @Override
    public void onSendOtpResponse(boolean isSuccess, @Nullable String responseMsg) {
        authListener.dismissDialog();
        authListener.showToast(responseMsg);

        // if Success?
        textViewOtpMsg.setText("We have just send an OTP \n on +91-"+ editTextPhone.getText().toString());
        // TODO : Add request Method
        layoutPhoneSection.setVisibility(View.GONE);
        layoutOTPSection.setVisibility(View.VISIBLE);
        setTvSeconds();
        setOTPTextWatcher();

    }

    @Override
    public void onVerifyOTPResponse(boolean isSuccess, @Nullable String responseMsg) {
        // Check
        authListener.dismissDialog();
        authListener.showToast(responseMsg);
        // TODO : if Success -> Set Reset Fragment -> User Profile
//        if (isSuccess){
            // TODO : Design Reset Password Options.
//        }else{
//
//        }
    }


}