package com.ecom.starshop.myclass;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.ecom.starshop.R;
import com.ecom.starshop.databinding.DialogLayoutAddSectionBinding;
import com.ecom.starshop.helper.Listener;

import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_BANNER_AD;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_IMAGE_BANNER_SLIDER;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_PRODUCTS_GRID_4;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_PRODUCTS_HORIZONTAL;
/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class DialogFragmentAddSection extends DialogFragment {

    private final Listener.ManageHomeListener manageHomeListener;

    public DialogFragmentAddSection(Listener.ManageHomeListener manageHomeListener) {
        this.manageHomeListener = manageHomeListener;
    }

    private DialogLayoutAddSectionBinding addSectionBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        addSectionBinding = DataBindingUtil.inflate( inflater, R.layout.dialog_layout_add_section, container, false );

        if (getDialog()!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }
        setOnButtonAction();

        return addSectionBinding.getRoot();
    }

    private void setOnButtonAction(){
        // Back Button Action....
        addSectionBinding.imageButtonBack.setOnClickListener( v -> {
            this.dismiss();
        });
        // Add Banner Ad Section
        addSectionBinding.linearLayoutAddBannerSectionBtn.setOnClickListener(v -> {
            this.dismiss();
            manageHomeListener.onAddSectionQuery( LAYOUT_TYPE_BANNER_AD );
        });
        // Add Slider Section
        addSectionBinding.linearLayoutAddBannerSliderSectionBtn.setOnClickListener(v -> {
            this.dismiss();
            manageHomeListener.onAddSectionQuery( LAYOUT_TYPE_IMAGE_BANNER_SLIDER );
        });
        // Add Product Horizontal Section
        addSectionBinding.linearLayoutAddProductHrSectionBtn.setOnClickListener(v -> {
            this.dismiss();
            manageHomeListener.onAddSectionQuery( LAYOUT_TYPE_PRODUCTS_HORIZONTAL );
        });
        // Add Product Grid Section
        addSectionBinding.linearLayoutAddProductGridSectionBtn.setOnClickListener(v -> {
            this.dismiss();
            manageHomeListener.onAddSectionQuery( LAYOUT_TYPE_PRODUCTS_GRID_4 );
        });
    }

}

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */