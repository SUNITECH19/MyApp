package com.ecom.starshop.helper;

public interface DialogListener {

    interface OnClickListener{
        void onPositiveAction( DialogListener dialog, int id );
        void onNegativeAction( DialogListener dialog, int id );
    }

    void onPositiveButtonClick( );
    void onNegativeButtonClick( );
    void setCloseAction( OnClickListener listener);

    void show();
    void dismiss();


}
