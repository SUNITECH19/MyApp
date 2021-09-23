package com.ecom.starshop.helper;

import androidx.annotation.Nullable;

public interface DBListener {

    interface OnCheckDatabaseListener{
        void onCheckDatabaseSet(@Nullable String responseMsg, boolean isSet );
    }


}
