package com.ecom.starshop.fragment.addproduct;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;
import com.ecom.starshop.R;
import com.ecom.starshop.adaptor.addproduct.AdaptorAddColor;
import com.ecom.starshop.databinding.FragmentDialogAddColorBinding;
import com.ecom.starshop.helper.AddProductListener;
import com.ecom.starshop.helper.SelectImageListener;
import com.ecom.starshop.model.ModelProduct;
import com.ecom.starshop.myclass.DialogFragmentSelectImages;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ecom.starshop.helper.Constant.FRAGMENT_DIALOG;
import static com.ecom.starshop.helper.Constant.REQUEST_CODE_FOR_MULTI_IMAGE;

public class FragmentAddColor extends DialogFragment implements AddProductListener.OnAddColorsListener
        , SelectImageListener.OnSelectedImageResponseListener {

    private final String TAG = "FragmentAddColor";

    public FragmentAddColor(AddProductListener addProductListener, List<ModelProduct.ProductColorItems> productColorItems) {
        // Required empty public constructor
        this.addProductListener = addProductListener;
        this.productColorItems = productColorItems;
    }

    private AddProductListener addProductListener;

    private List<ModelProduct.ProductColorItems> productColorItems;
    private AdaptorAddColor adaptorAddColor;

    private FragmentDialogAddColorBinding addColorBinding;
    // TEMP Variables...
    private int tempIndex = -1;

    /// Color Picker...
    private ColorPickerDialog colorPickerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addColorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_add_color, container, false);

        if (productColorItems.size() == 0) {
            productColorItems.add(new ModelProduct.ProductColorItems());
        }

        colorPickerDialog = ColorPickerDialog.createColorPickerDialog(getContext());

        adaptorAddColor = new AdaptorAddColor(this, productColorItems);
        addColorBinding.recyclerViewColors.setAdapter(adaptorAddColor);
        adaptorAddColor.notifyDataSetChanged();

        onButtonAction();

        return addColorBinding.getRoot();
    }

    private void onButtonAction() {
        addColorBinding.imageButtonBack.setOnClickListener(v -> {
            this.dismiss();
        });

        addColorBinding.buttonDone.setOnClickListener(v -> {
            //  : Check..?
            if (isListDataFilled()) {
                addProductListener.onUpdateColorList(productColorItems);
                this.dismiss();
            }
        });

        addColorBinding.buttonAddMore.setOnClickListener(v -> {
            ModelProduct.ProductColorItems items = productColorItems.get(productColorItems.size() - 1);
            if (items.getColorName() == null || items.getColorName().equals("")) {
                addColorBinding.recyclerViewColors.getLayoutManager()
                        .getChildAt(productColorItems.size() - 1).requestFocus();
                showSnack("Please Fill Color Name !");
                return;
            } else if (items.getColorStocks() == null || items.getColorStocks().equals("")) {
                addColorBinding.recyclerViewColors.getLayoutManager()
                        .getChildAt(productColorItems.size() - 1).requestFocus();
                showSnack("Please Fill Stocks of this Color!");
                return;
            }
            productColorItems.add(new ModelProduct.ProductColorItems());
            adaptorAddColor.notifyItemInserted(productColorItems.size() - 1);
        });

    }

    private boolean isListDataFilled() {
        if (productColorItems.size() == 0) {
            showSnack("Please add color!");
            return false;
        }

        for (int ind = 0; ind < productColorItems.size(); ind++) {
            ModelProduct.ProductColorItems items = productColorItems.get(ind);

            Log.e(TAG, "LIST : Color Name : " + items.getColorName() + " Stock : " + items.getColorStocks() + " Code : " + items.getColorCode());

            if ((items.getColorName() == null || items.getColorName().equals("")) ||
                    (items.getColorStocks() == null || items.getColorStocks().equals(""))) {
                View view = addColorBinding.recyclerViewColors.getLayoutManager().getChildAt(ind);
                if (view != null)
                    view.requestFocus();
                showSnack("Please fill mandatory field!");
                return false;
            }

        }
        return true;
    }

    private void showSnack(String msg) {
        Snackbar.make(addColorBinding.getRoot(), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAddUpdateImageQuery(int position) {
        tempIndex = position;
        //  Show Dialog to add Images...
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        DialogFragmentSelectImages dialogFragment = new DialogFragmentSelectImages(REQUEST_CODE_FOR_MULTI_IMAGE,
                6,
                getContext(), this);
        dialogFragment.show(fragmentManager, FRAGMENT_DIALOG);
    }

    @Override
    public void onColorCodeAction(int position) {
        tempIndex = position;

        colorPickerDialog.setOnColorPickedListener((color, hexVal) -> {
            //Your code here
            productColorItems.get(position).setColorCode(hexVal.toUpperCase());
            adaptorAddColor.notifyItemChanged(position);
        });

        colorPickerDialog.setHexaDecimalTextColor(R.color.colorPrimary);
        colorPickerDialog.show();

    }

    @Override
    public void onColorItemRemove(int position) {
        productColorItems.remove( position );
        if (position > 0){
            adaptorAddColor.notifyItemRemoved( position );
        }else{
            adaptorAddColor.notifyDataSetChanged();
        }
    }

    @Override
    public void onSendResponse(@Nullable List<String> imageList, @Nullable String imageLink) {
        if (imageList != null && imageList.size() > 0) {
            if (tempIndex > -1) {
                productColorItems.get(tempIndex).setColorImages(imageList);
            }
        } else if (imageLink != null && !imageLink.equals("")) {
            if (tempIndex > -1) {
                productColorItems.get(tempIndex).setColorImages(Collections.singletonList(imageLink));
            }
        }
        adaptorAddColor.notifyItemChanged(tempIndex);
        tempIndex = -1;
    }


}