package com.sunitech.myapp.helper;

import androidx.annotation.Nullable;

public interface DBListener {


    interface OnCheckDatabaseListener{
        void onCheckDatabaseSet(@Nullable String responseMsg, boolean isSet );
    }



}
