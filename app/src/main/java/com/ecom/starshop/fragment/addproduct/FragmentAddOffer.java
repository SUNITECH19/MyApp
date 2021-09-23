package com.ecom.starshop.fragment.addproduct;

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
import android.widget.ArrayAdapter;

import com.ecom.starshop.R;
import com.ecom.starshop.adaptor.addproduct.AdaptorAddOffer;
import com.ecom.starshop.databinding.FragmentDialogAddColorBinding;
import com.ecom.starshop.databinding.FragmentDialogAddOfferBinding;
import com.ecom.starshop.helper.AddProductListener;
import com.ecom.starshop.model.ModelProduct;
import com.ecom.starshop.myclass.DialogFragmentSelectImages;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.ecom.starshop.helper.Constant.FRAGMENT_DIALOG;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_DELIVERY_DISCOUNT;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_DISCOUNT;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_VOUCHER_CODE;
import static com.ecom.starshop.helper.Constant.REQUEST_CODE_FOR_MULTI_IMAGE;

public class FragmentAddOffer extends DialogFragment implements AddProductListener.OnAddOfferListener {
    private final String TAG = this.getClass().getName();

    public FragmentAddOffer( AddProductListener addProductListener, List<ModelProduct.ProductOffer> productOffersList ) {
        this.productOffersList = productOffersList;
        this.addProductListener = addProductListener;
    }

    private List<ModelProduct.ProductOffer> productOffersList;
    private AddProductListener addProductListener;

    private FragmentDialogAddOfferBinding offerBinding;
    private AdaptorAddOffer adaptorAddOffer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        offerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_add_offer, container, false);

        if (productOffersList.size() == 0){
            productOffersList.add( new ModelProduct.ProductOffer() );
        }

        adaptorAddOffer = new AdaptorAddOffer( getContext(), productOffersList, this );
        offerBinding.recyclerViewOffers.setAdapter( adaptorAddOffer );
        adaptorAddOffer.notifyDataSetChanged();

        onButtonAction();

        return offerBinding.getRoot();
    }


    private void onButtonAction() {
        offerBinding.imageButtonBack.setOnClickListener(v -> {
            this.dismiss();
        });

        offerBinding.buttonDone.setOnClickListener(v -> {
            //  : Check..?
            if ( isListDataFilled() ) {
                addProductListener.onUpdateOfferList( productOffersList );
                this.dismiss();
            }
        });

        offerBinding.buttonAddMore.setOnClickListener(v -> {

            if (productOffersList.size() > 0){
                int index = productOffersList.size() - 1;
                ModelProduct.ProductOffer items = productOffersList.get( index );
                if ( items.getOfferType() <= 0 || !isValidBenefit( items.getOfferType(), items.getOfferBenefit() )){

                    offerBinding.recyclerViewOffers.scrollToPosition(index);
                    if (items.getOfferType() <= 0){
                        showSnack("Please Select Offer Type!");
                        return;
                    }
                    if ( !isValidBenefit( items.getOfferType(), items.getOfferBenefit() )){
                        showSnack("Please Fill Offer Benefit!");
                        return;
                    }
                    try {
//                        View view = offerBinding.recyclerViewOffers.findViewHolderForItemId( adaptorAddOffer.getItemId( index )).itemView;
                        View view = offerBinding.recyclerViewOffers.findViewHolderForLayoutPosition( index ).itemView;
                        view.requestFocus();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            productOffersList.add(new ModelProduct.ProductOffer());
            adaptorAddOffer.notifyItemInserted(productOffersList.size() - 1);
        });

    }


    private boolean isListDataFilled() {
        if (productOffersList.size() == 0) {
            showSnack("Please add color!");
            return false;
        }

        for (int ind = 0; ind < productOffersList.size(); ind++) {
            ModelProduct.ProductOffer items = productOffersList.get(ind);

            Log.e(TAG, "LIST : Color Name : " + items.getOfferTitle() + " Stock : " + items.getOfferBenefit() + " Code : " + items.getOfferType());

            offerBinding.recyclerViewOffers.scrollToPosition(ind);

            if (items.getOfferType() <= 0){
                showSnack("Please Select Offer Type!");
                return false;
            }

            if (items.getOfferTitle() == null || items.getOfferTitle().equals("")){
                showSnack("Please Enter Offer Title!");
                return false;
            }

            if ( !isValidBenefit( items.getOfferType(), items.getOfferBenefit() )){
                showSnack("Please Fill Offer Benefit!");
                return false;
            }
        }
        return true;
    }

    private boolean isValidBenefit( int type, @Nullable String benefit ){
        switch (type){
            case OFFER_CODE_DISCOUNT:
            case OFFER_CODE_DELIVERY_DISCOUNT:
            case OFFER_CODE_VOUCHER_CODE:
                return benefit != null && !benefit.equals("");
            default:
                return true;
        }
    }

    private void showSnack(String msg) {
        Snackbar.make(offerBinding.getRoot(), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemRemove(int position) {
        productOffersList.remove( position );
        if (position > 0){
            adaptorAddOffer.notifyItemRemoved( position );
        }else {
            adaptorAddOffer.notifyDataSetChanged();
        }
    }


}