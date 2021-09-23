package com.ecom.starshop.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.R;
import com.ecom.starshop.model.ModelProduct;

import java.util.List;

import static androidx.databinding.library.baseAdapters.BR.productOffer;

public class AdaptorProductOfferView extends RecyclerView.Adapter<AdaptorProductOfferView.ViewHolder> {

    private List<ModelProduct.ProductOffer> arrayList;

    public AdaptorProductOfferView( List<ModelProduct.ProductOffer> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_show_offer, parent, false);
        return new ViewHolder( viewDataBinding );
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptorProductOfferView.ViewHolder holder, int position) {
        holder.viewDataBinding.setVariable( productOffer, arrayList.get( position ));
        holder.viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;
        public ViewHolder(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
    }
}
