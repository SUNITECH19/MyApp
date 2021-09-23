package com.ecom.starshop.fragment.addproduct;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.ecom.starshop.R;
import com.ecom.starshop.databinding.FragmentDialogAddWeightBinding;
import com.ecom.starshop.helper.AddProductListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class FragmentAddWeight extends DialogFragment {
    private final String TAG = "FragmentAddWeight";
    public static final int TYPE_WEIGHT = 1;
    public static final int TYPE_SIZE = 2;

    public FragmentAddWeight(int requestCode, AddProductListener addProductListener) {
        this.addProductListener = addProductListener;
        if (requestCode != -1) {
            this.requestCode = requestCode;
        }
    }

    private int requestCode = TYPE_WEIGHT;
    private AddProductListener addProductListener;
    private FragmentDialogAddWeightBinding addWeightBinding;

    /**
     * This list will be loaded from Database and Stored into local DB at the time of login..,
     * Whenever User Comes in this page/Dialog, List data will be fetched according to requirement..
     */
    private List<String> optionList = new ArrayList<>();

    // Response...
    private String type = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addWeightBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_add_weight, container, false);

        onButtonAction(); 
        setSpinner(getListData(true));

        return addWeightBinding.getRoot();
    }

    private void onButtonAction() {
        // Close Dialog...
        addWeightBinding.imageButtonClose.setOnClickListener(v -> {
            this.dismiss();
        });

        // On Done Action
        addWeightBinding.buttonDone.setOnClickListener(v -> {
            //  Check and send response...
            if (TextUtils.isEmpty(addWeightBinding.editTextSize.getText())) {
                addWeightBinding.editTextSize.setError("Required");
                return;
            }
            if (type == null) {
                Snackbar.make(addWeightBinding.getRoot(), "Please Select Type!", Snackbar.LENGTH_LONG).show();
                return;
            }

            addProductListener.onAddSizeOrWeight(requestCode, addWeightBinding.editTextSize.getText() + type);
            this.dismiss();
        });

        // Radio Action...
        addWeightBinding.radioGroupOption.setOnCheckedChangeListener((group, checkedId) -> {
            setSpinner(getListData(addWeightBinding.radioButtonWeight.getId() == checkedId));
            if (addWeightBinding.radioButtonWeight.getId() == checkedId) {
                requestCode = TYPE_WEIGHT;
            } else {
                requestCode = TYPE_SIZE;
            }
        });

    }

    private void setSpinner(List<String> optionList) {
        type = null;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.layout_select_string_item_option_drop_down, optionList);

        addWeightBinding.spinnerSize.setAdapter(arrayAdapter);

        addWeightBinding.spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Nothing Select...
                    type = null;
                } else {
                    type = optionList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //TODO : Code for fetch the list...
    private List<String> getListData(boolean isWeight) {
        optionList.clear();
        optionList.add("Choose Type");
        if (isWeight) {
            optionList.add("KG");
            optionList.add("Gram");
            optionList.add("Lt");
            optionList.add("ml");
        } else {
            optionList.add("CM");
            optionList.add("KM");
            optionList.add("Meter");
            optionList.add("Inches");
            optionList.add("Foot");
        }
        return optionList;
    }


}
