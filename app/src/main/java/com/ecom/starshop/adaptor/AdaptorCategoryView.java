package com.ecom.starshop.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.R;
import com.ecom.starshop.model.ModelCategory;

import java.util.List;

import static androidx.databinding.library.baseAdapters.BR.imageLink;
import static androidx.databinding.library.baseAdapters.BR.title;

public class AdaptorCategoryView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ModelCategory> categoryList;
    int maxShow = -1;

    public AdaptorCategoryView(List<ModelCategory> categoryList, int maxShow) {
        this.categoryList = categoryList;
        this.maxShow = maxShow;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewCategory = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_product_category, parent, false);
        return new ViewHolderCategory(viewCategory);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderCategory)holder).setViewCategory( position );
    }

    @Override
    public int getItemCount() {
        // We are using same adaptor to full view as well as short view( Only 4 Category )
        ///Math.min(categoryList.size(), maxShow); //
        return  Math.min(categoryList.size(), maxShow);
    }


    //--------- View Holder ------------------------------------
    public class ViewHolderCategory extends RecyclerView.ViewHolder{
        ViewDataBinding viewCategory;

        public ViewHolderCategory(@NonNull ViewDataBinding viewCategory ) {
            super(viewCategory.getRoot());
            this.viewCategory = viewCategory;
        }

        private void setViewCategory( int position ){
            viewCategory.setVariable( imageLink, categoryList.get(position).getCatImageIcon() );
            viewCategory.setVariable( title, categoryList.get(position).getCatTitle() );
            viewCategory.executePendingBindings();
            // TODO : Click Action

            // TODO : Edit Option...

        }


    }


}
