package com.sunitech.myapp.helper;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class GenericTextWatcher implements TextWatcher
{
    private View viewPreview;
    private View viewCrr;
    private View viewNext;
    private Context context;

    public GenericTextWatcher(Context context, View viewPreview, View viewCrr, View viewNext) {
        this.context = context;
        this.viewPreview = viewPreview;
        this.viewCrr = viewCrr;
        this.viewNext = viewNext;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        if (text.length()==1){
            if (viewNext != null){
                viewNext.requestFocus();
            }else if (context!=null){
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(viewCrr.getWindowToken(), KeyEvent.ACTION_DOWN);
            }
        }else if (viewPreview != null){
            viewPreview.requestFocus();
        }
//        switch(viewCrr.getId())
//        {
//            case R.id.edit_text_otp_d1:
//                if(text.length()==1)
//                    viewNext.requestFocus();
//                break;
//            case R.id.edit_text_otp_d2:
//                if(text.length()==1)
//                    viewNext.requestFocus();
//                else if(text.length()==0)
//                    viewNext.requestFocus();
//                break;
//            case R.id.edit_text_otp_d3:
//                if(text.length()==1)
//                    et4.requestFocus();
//                else if(text.length()==0)
//                    et2.requestFocus();
//                break;
//            case R.id.edit_text_otp_d3:
//                if(text.length()==0)
//                    et3.requestFocus();
//                break;
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        viewCrr.requestFocus();
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        onKeyEvert();
    }

    private void onKeyEvert(){
        viewCrr.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK){
                viewPreview.requestFocus();
            }
            return false;
        });
    }

}