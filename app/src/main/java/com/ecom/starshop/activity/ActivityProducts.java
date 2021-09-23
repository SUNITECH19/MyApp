package com.ecom.starshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.ecom.starshop.R;
import com.ecom.starshop.databinding.ActivityProductsBinding;

import static com.ecom.starshop.helper.StaticMethods.hideKeyboard;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class ActivityProducts extends AppCompatActivity {

    private ActivityProductsBinding activityProductsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProductsBinding = DataBindingUtil.setContentView( this, R.layout.activity_products );
        setToolbar();
        onButtonAction();

    }

    private void setToolbar(){
        setSupportActionBar( activityProductsBinding.includeToolBar.appToolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void onButtonAction(){
        // On Search Action...
        activityProductsBinding.includeToolBar.editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard( activityProductsBinding.includeToolBar.editTextSearch );
                return true;
            }
            return false;
        });
        activityProductsBinding.includeToolBar.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    activityProductsBinding.includeToolBar.imageViewClearSearch.setVisibility(View.VISIBLE);
                }else{
                    activityProductsBinding.includeToolBar.imageViewClearSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityProductsBinding.includeToolBar.imageViewClearSearch.setOnClickListener( v -> {
            activityProductsBinding.includeToolBar.editTextSearch.setText("");
        });
    }


}