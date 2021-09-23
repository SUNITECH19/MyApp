package com.ecom.starshop.myclass;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecom.starshop.R;
import com.ecom.starshop.databinding.FragmentPopUpDialogEditTitleBinding;
import com.ecom.starshop.dbquery.DBQuery;
import com.ecom.starshop.helper.Listener;
import com.ecom.starshop.helper.StaticMethods;
import com.ecom.starshop.model.temp.ModelUpdateItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class PopUpDialogEditTitle extends BottomSheetDialogFragment implements Listener.OnUpdateTitleListener {

    private Listener.SectionMenuActionListener listener;
    private ModelUpdateItem updateItem;

    public PopUpDialogEditTitle(Listener.SectionMenuActionListener listener,@Nullable ModelUpdateItem updateItem) {
        this.listener = listener;
        this.updateItem = updateItem;
    }

    private FragmentPopUpDialogEditTitleBinding popUpDialogEditTitleBinding;
    private ProgressDialog dialog;

    // Temp..
    private String tempVal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        popUpDialogEditTitleBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_pop_up_dialog_edit_title, container, false );

        tempVal = updateItem != null ? updateItem.getName() : "";

        popUpDialogEditTitleBinding.setText( tempVal );

        dialog = StaticMethods.getProgressDialog( getContext(), null );

        onButtonAction();

        return popUpDialogEditTitleBinding.getRoot();
    }

    private void onButtonAction(){
        popUpDialogEditTitleBinding.imageButtonClose.setOnClickListener(v -> {
            this.dismiss();
        });

        // Update Button Action....
        popUpDialogEditTitleBinding.buttonUpdate.setOnClickListener(v -> {
            // Check?
            if (TextUtils.isEmpty( popUpDialogEditTitleBinding.inputEditText.getText() )){
                popUpDialogEditTitleBinding.inputEditText.setError( getString( R.string.required_field ) );
                return;
            }

            popUpDialogEditTitleBinding.buttonUpdate.setEnabled( false );
            // Update...
            if (popUpDialogEditTitleBinding.inputEditText.getText().toString().trim().equals( tempVal )){
                // Dismiss...
                this.dismiss();
            }else{
                showDialog();
                /**  Query To Update... */
                tempVal = popUpDialogEditTitleBinding.inputEditText.getText().toString().trim();
                DBQuery.queryToChangeSectionTitle( this, updateItem.getParentID(), updateItem.getCurrentID(), tempVal );
            }
        });
    }


    @Override
    public void onUpdateResponse(boolean isSuccess, @Nullable String updateMessage) {
        dismissDialog();
        popUpDialogEditTitleBinding.buttonUpdate.setEnabled( true );
        if (isSuccess){
            listener.onUpdateTitle( true, tempVal );
            this.dismiss();
        }else {
            StaticMethods.showInfoSnake( getView(), updateMessage!=null? updateMessage : getString(R.string.something_went_wrong));
        }
    }

    private void showDialog(){
        if (dialog!=null && !dialog.isShowing())
            dialog.show();
    }

    private void dismissDialog(){
        if (dialog!=null && dialog.isShowing())
            dialog.dismiss();
    }

}

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */