package com.ecom.starshop.fragment.addproduct;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ecom.starshop.R;
import com.ecom.starshop.adaptor.AdaptorProductOfferView;
import com.ecom.starshop.adaptor.addproduct.AdaptorNewImages;
import com.ecom.starshop.databinding.FragmentDialogAddProductBinding;
import com.ecom.starshop.helper.AddProductListener;
import com.ecom.starshop.helper.DialogListener;
import com.ecom.starshop.helper.SelectImageListener;
import com.ecom.starshop.helper.StaticMethods;
import com.ecom.starshop.model.ModelProduct;
import com.ecom.starshop.myclass.DialogFragmentSelectImages;
import com.ecom.starshop.myclass.MyOptionDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import static com.ecom.starshop.helper.Constant.FRAGMENT_DIALOG;
import static com.ecom.starshop.helper.Constant.REQUEST_CODE_FOR_MULTI_IMAGE;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class DialogFragmentAddProduct extends DialogFragment implements SelectImageListener.OnSelectedImageResponseListener
        , AddProductListener {

    private final String TAG = "DialogFragAddProduct";

    public DialogFragmentAddProduct() {
        // Required em pty public constructor
    }

    public static DialogFragmentAddProduct getInstance(String param1, String param2) {
        DialogFragmentAddProduct fragment = new DialogFragmentAddProduct();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    // Private UI Variables..-----------------------------------------------------------------------
    private FragmentDialogAddProductBinding addProductBinding;

    private AdaptorNewImages adaptorNewImages;

    private AdaptorProductOfferView adaptorOffers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addProductBinding = DataBindingUtil.inflate( inflater, R.layout.fragment_dialog_add_product, container, false );

        initView();
        onButtonAction();


        return addProductBinding.getRoot();
    }


    // ----------- Product Variables ---------------------------------------------------------------
    private ModelProduct.ProductItem productItem = new ModelProduct.ProductItem();
    // TEMP:  Image List...
    private List<String> imageList = new ArrayList<>();
    // TEMP:  Colors : if Any Added...
    private List<ModelProduct.ProductColorItems> productColorItems = new ArrayList<>();
    // TEMP:  Offer List: if Any....
    private List<ModelProduct.ProductOffer> productOffers = new ArrayList<>();

    // ----- Init View -----------------------------------------------------------------------------
    private void initView( ){
        // Assign Adaptor..
        adaptorNewImages = new AdaptorNewImages( this, imageList );

        addProductBinding.recyclerViewImages.setAdapter( adaptorNewImages );

        // Assign Offer Adaptor...
        adaptorOffers = new AdaptorProductOfferView( productOffers );
        addProductBinding.recyclerViewOffers.setAdapter( adaptorOffers );

    }

    //-------- On Button Action  -------------------------------------------------------------------

    private void onButtonAction(){

        // On Add Color Action
        addProductBinding.buttonAddColors.setOnClickListener(v -> {
            // Update UI.
            showDialogToAddColor(
                    new FragmentAddColor( this, productColorItems )
            );
        });

        // On Add More Color Button Action...
        addProductBinding.tvUpdateColorsBtn.setOnClickListener( v -> {
            showDialogToAddColor(
                    new FragmentAddColor( this, productColorItems )
            );
        });

        // On Delete Color Button Action
        addProductBinding.imageButtonDeleteColors.setOnClickListener(v -> {
            // : Add Alert
            StaticMethods.getAlertDialog( getContext(), "Do you want to delete all colors?", null )
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        productColorItems.clear();
                        addProductBinding.layoutColors.setVisibility(View.GONE);
                        addProductBinding.buttonAddColors.setVisibility(View.VISIBLE);
                    }).setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
        });

        // On Action Offer Button..
        addProductBinding.buttonAddOffer.setOnClickListener( v -> {
            showDialogToAddColor(
                    new FragmentAddOffer( this, productOffers )
            );
        });
        // On Update/Add more Offer Click..
        addProductBinding.tvAddMoreOffersBtn.setOnClickListener( v -> {
            showDialogToAddColor(
                    new FragmentAddOffer( this, productOffers )
            );
        });

        // On Delete Offer Button Action
        addProductBinding.imageButtonDeleteOffers.setOnClickListener(v -> {
            // : Add Alert
            StaticMethods.getAlertDialog( getContext(), "Do you want to delete all Offers?", null )
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        productOffers.clear();
                        addProductBinding.layoutOffers.setVisibility(View.GONE);
                        addProductBinding.buttonAddOffer.setVisibility(View.VISIBLE);
                    }).setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
            }).show();
        });

        // On Add Size..
        addProductBinding.buttonAddSize.setOnClickListener(v -> {
            showDialogToAddColor(
                    new FragmentAddWeight( -1, this )
            );
        });
        // On Change Size..
        addProductBinding.tvChangeSizeBtn.setOnClickListener(v -> {
            showDialogToAddColor(
                    new FragmentAddWeight( -1, this )
            );
        });

        // On Delete Size Button Action
        addProductBinding.imageButtonDeleteSize.setOnClickListener(v -> {
            StaticMethods.getAlertDialog( getContext(),
                    "Do you want to delete " + addProductBinding.textViewSizeTitle.getText() + "?", null )
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        //  clear data...
                        productItem.setProductWeight("");
                        productItem.setProductSize("");

                        addProductBinding.layoutSize.setVisibility(View.GONE);
                        addProductBinding.buttonAddSize.setVisibility(View.VISIBLE);
                    }).setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            }).show();
        });

        // On Add Product Button Action....
        addProductBinding.buttonAddProduct.setOnClickListener(v -> onAddProductClick());

        // On Back Button Action....
        addProductBinding.imageButtonBack.setOnClickListener(v -> {
            MyOptionDialog myOptionDialog = new MyOptionDialog( getContext(), "Go Back?", "The data of this product will be clear!" );
            myOptionDialog.onCreateDialog( new DialogListener.OnClickListener() {
                @Override
                public void onPositiveAction(DialogListener dialog, int id) {
                    // On Positive Button Action...
                    dialog.dismiss();
                    DialogFragmentAddProduct.this.dismiss();
                }

                @Override
                public void onNegativeAction(DialogListener dialog, int id) {
                    // On Negative Button Action...
                    dialog.dismiss();
                }
            });
            myOptionDialog.show();
        } );

    }

    public void onAddProductClick(){
        if (isValidInfo()){
            // TODO: Next...
        }
    }

    private void showDialogToAddColor(DialogFragment dialogFragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
//        DialogFragment dialogFragment = new DialogFragmentSelectImages( REQUEST_CODE_FOR_MULTI_IMAGE,
//                8 - imageList.size(),
//                getContext(), this );
        dialogFragment.show( fragmentManager, FRAGMENT_DIALOG );
    }

    @Override
    public void onAddImageQuery() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        DialogFragmentSelectImages dialogFragment = new DialogFragmentSelectImages( REQUEST_CODE_FOR_MULTI_IMAGE,
                8 - imageList.size(),
                getContext(), this );
        dialogFragment.show( fragmentManager, FRAGMENT_DIALOG );
    }

    @Override
    public void onRemoveImage(int position) {
        imageList.remove( position );
        adaptorNewImages.notifyItemRemoved( position );

        adaptorNewImages.notifyDataSetChanged();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onUpdateColorList(List<ModelProduct.ProductColorItems> productColorItem) {
        //productColorItems
        if ( productColorItem.size()>0){
//            this.productColorItems.clear(); // We do not clear this list Coz We are pointing same list...

            // Update UI.
            addProductBinding.layoutColors.setVisibility(View.VISIBLE);
            addProductBinding.buttonAddColors.setVisibility( View.GONE );

//             Add List UI...
            addProductBinding.chipGroupColors.removeAllViews();

            for ( ModelProduct.ProductColorItems items : productColorItem ){

                Chip chip = new Chip( addProductBinding.chipGroupColors.getContext() );
                chip.setText( items.getColorName() );
                chip.setClickable(false);

                chip.setChipStrokeWidth( 4f );
                chip.setChipStrokeColor( ColorStateList.valueOf(Color.parseColor( items.getColorCode() )));

                chip.setChipIcon( getResources().getDrawable( R.drawable.back_design_round_fill ));
                chip.setChipIconTint( ColorStateList.valueOf(Color.parseColor( items.getColorCode() )));
                chip.setChipIconVisible( true );
//              chip.setChipDrawable( ChipDrawable.createFromResource( addProductBinding.chipGroupColors.getContext(), R.xml.standalone_chip) );

                // Add View...
                addProductBinding.chipGroupColors.addView( chip );

            }

        }
    }

    @Override
    public void onUpdateOfferList(List<ModelProduct.ProductOffer> productOffers) {
        if (productOffers != null && productOffers.size() > 0){
            addProductBinding.layoutOffers.setVisibility(View.VISIBLE);
            addProductBinding.buttonAddOffer.setVisibility(View.GONE);
            adaptorOffers.notifyDataSetChanged();
        }else{
            addProductBinding.layoutOffers.setVisibility(View.GONE);
            addProductBinding.buttonAddOffer.setVisibility(View.VISIBLE);
        }
    }

    // On Selected Image Response....
    @Override
    public void onSendResponse(@Nullable List<String> imageList, @Nullable String imageLink) {
        if (imageList != null && imageList.size()>0){
            this.imageList.addAll( imageList );
        }else if ( imageLink != null && !imageLink.equals("")){
            this.imageList.add( imageLink );
        }
        adaptorNewImages.notifyDataSetChanged();
    }

    @Override
    public void onAddSizeOrWeight(int addCode, @NonNull String value) {
//        Log.e( TAG, "Res : " + value );

        addProductBinding.layoutSize.setVisibility(View.VISIBLE);
        addProductBinding.buttonAddSize.setVisibility(View.GONE);

        addProductBinding.textViewSize.setText( value );

        if (addCode == FragmentAddWeight.TYPE_SIZE){
            addProductBinding.textViewSizeTitle.setText("Size");
            productItem.setProductSize( value ); // Add Data into Item
        }else{
            addProductBinding.textViewSizeTitle.setText("Weight");
            productItem.setProductWeight( value ); // Add Data into Item
        }
    }

    //---------- Other : UI And Helper Methods -----------------------------------------------------

    private boolean isValidInfo(){

        if (isEmpty( addProductBinding.inputProductName )){
            return false;
        }

        if (isEmpty( addProductBinding.inputProductPrice )){
            return false;
        }

        if (isEmpty( addProductBinding.inputProductMRP )){
            return false;
        }

        if (isEmpty( addProductBinding.inputProductStocks )){
            return false;
        }

        // TODO : Can Add More Check...

        return true;
    }

    private boolean isEmpty(TextInputEditText inputEditText){
        if (TextUtils.isEmpty( inputEditText.getText().toString() )){
            inputEditText.setTextInputLayoutFocusedRectEnabled( true );
            inputEditText.setError("Required Field!");
            return true;
        }
        return false;
    }


}