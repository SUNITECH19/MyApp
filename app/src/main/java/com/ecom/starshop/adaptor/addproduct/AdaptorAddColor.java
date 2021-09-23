package com.ecom.starshop.adaptor.addproduct;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.R;
import com.ecom.starshop.helper.AddProductListener;
import com.ecom.starshop.helper.MyColor;
import com.ecom.starshop.model.ModelProduct;

import java.util.List;

import static com.ecom.starshop.BR.colorActionListener;
import static com.ecom.starshop.BR.colorModel;
import static com.ecom.starshop.BR.image1;
import static com.ecom.starshop.BR.image2;
import static com.ecom.starshop.BR.image3;


public class AdaptorAddColor extends RecyclerView.Adapter<AdaptorAddColor.ViewHolder> {

    private List<ModelProduct.ProductColorItems> productColorItems;
    private AddProductListener.OnAddColorsListener addColorsListener;

    public AdaptorAddColor(AddProductListener.OnAddColorsListener addColorsListener, List<ModelProduct.ProductColorItems> productColorItems) {
        this.addColorsListener = addColorsListener;
        this.productColorItems = productColorItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewCategory = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_add_color, parent, false);
        return new ViewHolder( viewCategory );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return productColorItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements AddProductListener.OnColorItemActionListener {
        private ViewDataBinding viewDataBinding;
        private AutoCompleteTextView colorName;

        public ViewHolder(@NonNull  ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            colorName = viewDataBinding.getRoot().findViewById(R.id.inputProductColorName);
        }

        private void setData( int position ){
            viewDataBinding.setVariable( colorActionListener, this );

            ModelProduct.ProductColorItems item = productColorItems.get( position );

            //  And Need To Add Live Data Update, So that If User will Change any value
            //  then in that case Data could be update automatically
            // Two - Way - Binding...
            viewDataBinding.setVariable( colorModel, item );

            if (item.getColorImages() != null){
                viewDataBinding.setVariable( image1, item.getColorImages().size() > 0 ? item.getColorImages().get(0) : "");
                viewDataBinding.setVariable( image2, item.getColorImages().size() > 1 ? item.getColorImages().get(1) : "");
                viewDataBinding.setVariable( image3, item.getColorImages().size() > 2 ? item.getColorImages().get(2) : "");
            }

            /// Auto Color Set....
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( viewDataBinding.getRoot().getContext(),
                    R.layout.layout_select_string_item_option_drop_down, MyColor.colorNameList() );

            (colorName).setAdapter( arrayAdapter );
            colorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     String colorCode = MyColor.getColorCode( parent.getSelectedItem().toString() );
                     item.setColorCode( colorCode );
                     viewDataBinding.executePendingBindings();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // ignore
                }
            });

            viewDataBinding.executePendingBindings();
        }

        @Override
        public void onAddImageClick() {
            addColorsListener.onAddUpdateImageQuery( getLayoutPosition() );
        }

        //  Add Color Dialog to Set Color
        @Override
        public void onColorPickerClick() {
            addColorsListener.onColorCodeAction( getLayoutPosition() );
        }

        @Override
        public void onRemoveItem() {
            addColorsListener.onColorItemRemove( getLayoutPosition() );
        }


    }


}
