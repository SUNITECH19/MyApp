package com.ecom.starshop.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.R;
import com.ecom.starshop.model.ModelProduct;
import com.ecom.starshop.model.local.ModelProductList;

import java.util.ArrayList;
import java.util.List;
import static androidx.databinding.library.baseAdapters.BR.productName;
import static androidx.databinding.library.baseAdapters.BR.productPrice;
import static androidx.databinding.library.baseAdapters.BR.productStock;
import static com.ecom.starshop.BR.productImage;
import static com.ecom.starshop.helper.DataList.TESTING_IMAGE_LINK_1;
import static com.ecom.starshop.helper.DataList.rootListProducts;

public class AdaptorProductView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ModelProduct> productList = new ArrayList<>();

    public AdaptorProductView( String layoutID ) {
        for (ModelProductList list : rootListProducts){
            if (list.getListID().equals( layoutID )){
                this.productList = list.getProductList();
                break;
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding productView = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_product_square, parent, false);
        return new ViewHolderProduct(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderProduct) holder).setProductView( position );
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /// ---------- View Holder....
    private class ViewHolderProduct extends RecyclerView.ViewHolder{
        private ViewDataBinding productView;
        public ViewHolderProduct(@NonNull ViewDataBinding productView) {
            super(productView.getRoot());
            this.productView = productView;
        }

        private void setProductView( int position ){
            ModelProduct product = productList.get(position);
            ModelProduct.ProductItem productItem = product.getProductItems().get(0);

            productView.setVariable( productImage, TESTING_IMAGE_LINK_1 );
            productView.setVariable( productName, productItem.getProductName() );
            productView.setVariable( productPrice, productItem.getProductSellingPrice() );
//            productView.setVariable( productStock, productItem.getProductTotalStocks() );
            productView.setVariable( productStock, "22" );

            /**
             * We have ProductTotalStocks : When we don't have  multiple color of single variant product
             * Check Full Documentation in  #class ModelProduct.ProductItem
             */


        }



    }


}
