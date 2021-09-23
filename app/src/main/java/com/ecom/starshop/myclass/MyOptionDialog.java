package com.ecom.starshop.myclass;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.ecom.starshop.BR;
import com.ecom.starshop.R;
import com.ecom.starshop.helper.DialogListener;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class MyOptionDialog extends Dialog implements DialogListener {

    private Context context;
    private String title;
    private String subTitle;
    private String positiveButtonText;
    private String negativeButtonText;

    public MyOptionDialog(Context context, String title, String subTitle) {
        super(context);
        this.context = context;
        this.title = title;
        this.subTitle = subTitle;
    }

    public MyOptionDialog(Context context, String title, @Nullable String subTitle, @Nullable String positiveButtonText, @Nullable String negativeButtonText) {
        super(context);
        this.context = context;
        this.title = title;
        this.subTitle = subTitle;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
    }

    //----------------------------
    private MyOptionDialog myDialog;
    private DialogListener.OnClickListener actionListener;

    public void onCreateDialog(DialogListener.OnClickListener listener) {
        this.actionListener = listener;
        myDialog = this;
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setCancelable(false);

        ViewDataBinding dataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_dialog_two_options, null, false);
        myDialog.setContentView(dataBinding.getRoot());

        dataBinding.setVariable(BR.title, title);
        dataBinding.setVariable(BR.subTitle, subTitle);
        dataBinding.setVariable(BR.positiveBtnText, positiveButtonText != null ? positiveButtonText : getContext().getString(R.string.ok_button));
        dataBinding.setVariable(BR.negativeBtnText, negativeButtonText != null ? negativeButtonText : getContext().getString(R.string.cancel_button));
        dataBinding.setVariable(BR.dialogType, -1);
        dataBinding.setVariable(BR.dialogShowIcon, true);
        dataBinding.setVariable(BR.actionListener, this);

        // Set Width...
//        myDialog.getWindow().setGravity( Gravity.BOTTOM );
        myDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//        positiveBtn = dataBinding.getRoot().findViewById( R.id.dialogButtonPositive);
//        negativeBtn = dataBinding.getRoot().findViewById( R.id.dialogButtonNegative);

        dataBinding.executePendingBindings();
    }

    @Override
    public void onPositiveButtonClick() {
        if (actionListener != null)
            actionListener.onPositiveAction(this.myDialog, 1);
    }

    @Override
    public void onNegativeButtonClick() {
        if (actionListener != null)
            actionListener.onNegativeAction(this.myDialog, 1);
    }

    @Override
    public void setCloseAction(DialogListener.OnClickListener listener) {

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */