package com.ecom.starshop.adaptor.addproduct;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.R;
import com.ecom.starshop.helper.AddProductListener;
import com.ecom.starshop.model.ModelProduct;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static com.ecom.starshop.BR.index;
import static com.ecom.starshop.BR.offerListener;
import static com.ecom.starshop.BR.offerModel;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_DELIVERY_DISCOUNT;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_DISCOUNT;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_FREE_DELIVERY;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_FREE_GIFT;
import static com.ecom.starshop.helper.Constant.OFFER_CODE_VOUCHER_CODE;

public class AdaptorAddOffer extends RecyclerView.Adapter<AdaptorAddOffer.ViewHolder> {

    private List<ModelProduct.ProductOffer> productOfferList;
    private AddProductListener.OnAddOfferListener parentListener;
    private Context context;

    public AdaptorAddOffer(Context context, List<ModelProduct.ProductOffer> productOfferList, AddProductListener.OnAddOfferListener parentListener) {
        this.context = context;
        this.productOfferList = productOfferList;
        this.parentListener = parentListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_add_offer, parent, false);
        ViewHolder holder =  new ViewHolder(viewDataBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptorAddOffer.ViewHolder holder, int position) {
        holder.setViewDataBinding(position);
    }

    @Override
    public int getItemCount() {
        return productOfferList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AddProductListener.OnOfferItemActionListener {
        private ViewDataBinding viewDataBinding;
        private MaterialAutoCompleteTextView spinner;

        private TextInputLayout layoutBenefit;
        private TextInputEditText editTextBenefit;

        public ViewHolder(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;

            spinner = viewDataBinding.getRoot().findViewById( R.id.autoCompleteSelectType );
            layoutBenefit = viewDataBinding.getRoot().findViewById( R.id.inputLayoutOfferBenefit );
            editTextBenefit = viewDataBinding.getRoot().findViewById( R.id.inputOfferBenefit );
        }

        private void setViewDataBinding(int position) {
            ModelProduct.ProductOffer item = productOfferList.get(position);

            viewDataBinding.setVariable(offerListener, this);
            viewDataBinding.setVariable(offerModel, item);
            viewDataBinding.setVariable(index, String.valueOf(1 + position));

            setTypeAdaptor( position );

            viewDataBinding.executePendingBindings();
        }

        private void setTypeAdaptor( int index ) {

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.layout_select_string_item_option_drop_down,
                    context.getResources().getStringArray(R.array.list_offer_types));

            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemClickListener((parent, view, position, id) -> {
                // 1.Discount, 2.Free Delivery, 3.Delivery Discount, 4.Voucher Code, 5.Gift
                switch (position) {
                    case 0:
                        productOfferList.get(index).setOfferType(OFFER_CODE_DISCOUNT);
                        setLayoutBenefit( "Discount Amount", InputType.TYPE_CLASS_NUMBER );
                        break;
                    case 1:
                        productOfferList.get(index).setOfferType(OFFER_CODE_FREE_DELIVERY);
                        layoutBenefit.setVisibility( View.GONE );
                        break;
                    case 2:
                        productOfferList.get(index).setOfferType(OFFER_CODE_DELIVERY_DISCOUNT);
                        setLayoutBenefit( "Discount Amount", InputType.TYPE_CLASS_NUMBER );
                        break;
                    case 3:
                        productOfferList.get(index).setOfferType(OFFER_CODE_VOUCHER_CODE);
                        setLayoutBenefit( "Voucher Code", InputType.TYPE_CLASS_TEXT );
                        break;
                    case 4:
                        productOfferList.get(index).setOfferType(OFFER_CODE_FREE_GIFT);
                        layoutBenefit.setVisibility( View.GONE );
                        break;
                    default:
                        productOfferList.get(index).setOfferType( -1 );
                }
            });

            // if already selected..!
            if ( productOfferList.get(index).getOfferType() > -1 ){
                spinner.setListSelection( productOfferList.get(index).getOfferType() - 1 );
            }

        }

        private void setLayoutBenefit( String hint, int inputType ){
            layoutBenefit.setVisibility( View.VISIBLE );
            layoutBenefit.setHint( hint );
            editTextBenefit.setInputType( inputType );
        }

        @Override
        public void onRemoveItem() {
            parentListener.onItemRemove(getLayoutPosition());
        }

        /**
         public boolean isValidData(){

         ModelProduct.ProductOffer items = productOfferList.get(getLayoutPosition());

         //            Log.e(TAG, "LIST : Color Name : " + items.getOfferTitle() + " Stock : " + items.getOfferBenefit() + " Code : " + items.getOfferType());

         if (items.getOfferType() <= 0){
         return false;
         }

         if (items.getOfferTitle() == null || items.getOfferTitle().equals("")){
         return false;
         }

         if ( !isValidBenefit( items.getOfferType(), items.getOfferBenefit() )){
         layoutBenefit.setError("Required!");
         layoutBenefit.requestFocus();
         return false;
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

         */

    }

    /**
     // Reference To Focus..
     private List<ViewHolder> holderList = new ArrayList<>();

     public boolean isValidData(){
     for (ViewHolder holder : holderList){
     if (!holder.isValidData()){
     return false;
     }
     }
     return true;
     }
     */


}
