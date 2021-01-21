package com.sunitech.myapp.login;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface AuthListener {

    void showDialog();
    void dismissDialog();

    void showToast(String msg);
    void resetFragment( Fragment fragment );
    void showNextFragment(Fragment fragment);
    void onBackPressed(@Nullable String msg);

    interface SendOTPListener{
        void onSendOtpResponse(boolean isSuccess, @Nullable String responseMsg);
        void onVerifyOTPResponse(boolean isSuccess, @Nullable String responseMsg);
    }

}
