package com.ecom.starshop.myclass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ecom.starshop.R;
import com.ecom.starshop.databinding.FragmentDialogAddBannerBinding;
import com.ecom.starshop.dbquery.DBQuery;
import com.ecom.starshop.helper.DropDownItemSelectListener;
import com.ecom.starshop.helper.Listener;
import com.ecom.starshop.helper.SelectImageListener;
import com.ecom.starshop.model.ModelBanner;

import java.util.ArrayList;
import java.util.List;

import static com.ecom.starshop.helper.Constant.FRAGMENT_DIALOG;
import static com.ecom.starshop.helper.Constant.REQUEST_CODE_FOR_MULTI_IMAGE;
import static com.ecom.starshop.helper.Constant.REQUEST_CODE_FOR_SINGLE_IMAGE;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class DialogFragmentAddBanner extends DialogFragment implements Listener.OnUploadBannerListener
        , SelectImageListener.OnSelectedImageResponseListener  {

    public DialogFragmentAddBanner(Listener.ManageHomeListener manageHomeListener) {
        // Required empty public constructor
        this.manageHomeListener = manageHomeListener;
    }
    private Listener.ManageHomeListener manageHomeListener;

    private FragmentDialogAddBannerBinding addBannerBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addBannerBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_dialog_add_banner, container, false );
        if (getDialog()!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            getDialog().getWindow().setLayout(width, height);
        }
        this.setCancelable( false );
        // Set Default Layout....
        addBannerBinding.layoutButtons.setVisibility(View.GONE);
        addBannerBinding.inputLayoutSelectType.setVisibility(View.GONE);
        addBannerBinding.linearLayoutProductId.setVisibility(View.GONE);
        addBannerBinding.inputLayoutWebUrl.setVisibility(View.GONE);

        onButtonAction();

        return addBannerBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void onButtonAction() {
        // Close Dialog Action
        addBannerBinding.imageButtonBack.setOnClickListener(v -> {
            this.dismiss();
        });
        addBannerBinding.imageButtonClose.setOnClickListener(v -> {
            this.dismiss();
        });

        // Add Image Button...
        addBannerBinding.layoutAddImageBtn.setOnClickListener(v -> {
            //  Select Image From Gallery...
            showFragmentDialog();
        });
        // Change Image....
        addBannerBinding.buttonChangeImage.setOnClickListener(v -> {
            // Change Image...
            showFragmentDialog();
        });
        // Upload Image Action
        addBannerBinding.buttonUpload.setOnClickListener(v -> {
            // TODO : Upload Image...
            DBQuery.queryToAddBanner( this, null );
        });

        setBannerTypeSelect();
    }

    private void setBannerTypeSelect(){
//        List<String> cityList = new ArrayList<>();
//        cityList.add( "Product Promo");
//        cityList.add( "Website Promo");
//        cityList.add( "No Action");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( getContext(), R.layout.layout_select_string_item_option_drop_down,
                getResources().getStringArray(R.array.list_banner_types) );

        (addBannerBinding.autoCompleteSelectType).setAdapter( arrayAdapter );
        addBannerBinding.autoCompleteSelectType.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 0:
                    setGetActionView( View.VISIBLE, View.GONE );
                    break;
                case 1:
                    setGetActionView( View.GONE, View.VISIBLE );
                    break;
                case 2:
                    setGetActionView( View.GONE, View.GONE );
                    break;
            }
        });
    }
    private void setGetActionView(int productVisibility, int webVisibility){
        addBannerBinding.linearLayoutProductId.setVisibility( productVisibility );
        addBannerBinding.inputLayoutWebUrl.setVisibility( webVisibility );
    }

    // Dialog For select Image From Gallery
    private void showFragmentDialog(  ){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        DialogFragmentSelectImages dialogFragment = new DialogFragmentSelectImages( REQUEST_CODE_FOR_SINGLE_IMAGE, 1, getContext(), this );
        dialogFragment.show( fragmentManager, FRAGMENT_DIALOG );
    }

    @Override
    public void onAddBanner(boolean isSuccess, ModelBanner modelBanner) {
        if (isSuccess){
            manageHomeListener.onAddBanner(true, modelBanner );
        }else{
          // Something went wrong!
        }
    }

    @Override
    public void onSendResponse(@Nullable List<String> imageList, @Nullable String imageLink) {
        if (imageLink != null){
            addBannerBinding.layoutButtons.setVisibility(View.VISIBLE);
            addBannerBinding.inputLayoutSelectType.setVisibility(View.VISIBLE);
            addBannerBinding.layoutAddImageBtn.setVisibility(View.GONE);
            Glide.with( addBannerBinding.view ).load( imageLink ).apply( new RequestOptions().placeholder( R.drawable.logo_placeholder ))
                    .into( addBannerBinding.imageViewImage );
        }else{
            manageHomeListener.showToast( "Image not found!" );
        }
    }


}

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */