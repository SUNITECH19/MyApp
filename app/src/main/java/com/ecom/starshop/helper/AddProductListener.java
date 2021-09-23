package com.ecom.starshop.helper;

import androidx.annotation.NonNull;

import com.ecom.starshop.model.ModelProduct;

import java.util.List;

public interface AddProductListener {


    void onAddImageQuery( );
    void onRemoveImage(int position);
    void onUpdateColorList( List<ModelProduct.ProductColorItems> productColorItems );
    void onUpdateOfferList( List<ModelProduct.ProductOffer> productOffers );

    // Add Weight Or Size...
    void onAddSizeOrWeight(int addCode,@NonNull String value );

    // : ADD OFFER
    //  :- On Remove Offer Item
    //  :- On Change Offer Item
    interface OnAddOfferListener{
        void onItemRemove( int position );
    }
    // Action... Offer Item...
    interface OnOfferItemActionListener{
        void onRemoveItem( );
    }

    //  ADD Colors
    //  :- On Remove Colors Item
    //  :- On Change Colors Item
    interface OnAddColorsListener{

        void onAddUpdateImageQuery( int position );

        void onColorCodeAction( int position );

        void onColorItemRemove( int position );
    }
    // Action... Color Item...
    interface OnColorItemActionListener{
        void onAddImageClick();
        void onColorPickerClick();
        void onRemoveItem( );
    }




}
