package com.ecom.starshop.adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.R;
import com.ecom.starshop.helper.SelectImageListener;

import java.util.List;

import static androidx.databinding.library.baseAdapters.BR.indexText;
import static com.ecom.starshop.BR.imageLink;

public class AdaptorSelectedImages extends RecyclerView.Adapter<AdaptorSelectedImages.ViewHolder> {

    private List<String> stringList;
    private boolean isEditModeOn = false;
    private SelectImageListener.EditImageListener editImageListener;

    public AdaptorSelectedImages(List<String> stringList) {
        this.stringList = stringList;
        this.isEditModeOn = false;
    }

    public AdaptorSelectedImages(List<String> stringList, boolean isEditModeOn, SelectImageListener.EditImageListener editImageListener) {
        this.stringList = stringList;
        this.isEditModeOn = isEditModeOn;
        this.editImageListener = editImageListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_selected_image, parent, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData( stringList.get(position), position );
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;
        public ViewHolder(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
        private void bindData(String link, int position){
            viewDataBinding.setVariable(imageLink, link);
            viewDataBinding.setVariable(indexText, String.valueOf(position + 1));
            if (isEditModeOn){
                viewDataBinding.getRoot().findViewById(R.id.imageViewCropped).setVisibility(View.VISIBLE);
            }else{
                viewDataBinding.getRoot().findViewById(R.id.imageViewCropped).setVisibility(View.GONE);
            }
            viewDataBinding.executePendingBindings();
            viewDataBinding.getRoot().findViewById(R.id.imageViewCropped).setOnClickListener(v -> {
                if (editImageListener!=null){
                    editImageListener.onQueryToCrop( link, position );
                }
            });
        }
    }


}
