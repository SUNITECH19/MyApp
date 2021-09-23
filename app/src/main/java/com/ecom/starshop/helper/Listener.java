package com.ecom.starshop.helper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ecom.starshop.model.ModelBanner;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public interface Listener {

    interface DialogFragmentListener{
        void onDismiss();
    }

    interface FragmentListener {
        void showDialog();
        void dismissDialog();
        void showToast(String msg);
    }

    interface FragmentChangeListener {
        void setFragment(Fragment fragment);
        void setNextFragment(Fragment fragment);
        void onBackPressed();
    }

    interface AppFragmentChangeListener extends FragmentChangeListener{
        void resetFragment(Fragment fragment);
    }

    // Activity Listener-------
    interface ActivityListener extends FragmentListener{
        void onBackPressed();
    }

// ------------- Manage Home Section ---------------------------------------------------------------
    interface ManageHomeListener extends ActivityListener, OnUploadBannerListener{
        void onAddSectionQuery(int addSectionCode );
    }

    interface SectionMenuActionListener{
        void onUpdateTitle( boolean isSuccess, @Nullable String newTitle );
        void onDelete( boolean isSuccess );
        void onUpdateVisibility( boolean isSuccess );
    }

    //--------------- Add Section Listener ------------
    interface OnUploadBannerListener{
        void onAddBanner(boolean isSuccess, ModelBanner modelBanner);
    }


    //---------- Update Listener -----------------------------------------------------------------------------------------------------------------
    interface OnUpdateTitleListener{
        void onUpdateResponse( boolean isSuccess, @Nullable String updateMessage );
    }

}
