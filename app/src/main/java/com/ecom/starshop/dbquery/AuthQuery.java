package com.ecom.starshop.dbquery;

import com.ecom.starshop.login.AuthListener;
import com.ecom.starshop.login.ForgetPassListener;

public class AuthQuery {


    // FORGET PASSWORD ---
    public static void queryToSendEmail(ForgetPassListener listener, String email){
        listener.onResendEmailResponse( false, "Code Not Found!" );
        // TODO : set Query...
    }

    public static void queryToSendOTP(AuthListener.SendOTPListener listener, String mobile){
        listener.onSendOtpResponse( false, "Not Found!");
    }

    public static void queryToVerifyOTP( AuthListener.SendOTPListener listener, String mobile, String OTP){
        listener.onVerifyOTPResponse( false, "Not Found!");
    }



}
