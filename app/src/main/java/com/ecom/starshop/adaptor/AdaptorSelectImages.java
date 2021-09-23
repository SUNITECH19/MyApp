package com.ecom.starshop.adaptor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.BR;
import com.ecom.starshop.R;
import com.ecom.starshop.helper.SelectImageListener;
import com.ecom.starshop.model.ModelImage;

import java.util.List;

import static com.ecom.starshop.BR.imageLink;
import static com.ecom.starshop.BR.isChecked;

public class AdaptorSelectImages extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isMultiSelectedOn = false;
    private int currentSelectedItem = -1;

    private List<ModelImage> imageList;
    private SelectImageListener selectImageListener;

    public AdaptorSelectImages(List<ModelImage> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewSelectImage = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_image_picker, parent, false);
        return new ViewHolderSelectImages(viewSelectImage);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderSelectImages) holder).bindData( position );
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolderSelectImages extends RecyclerView.ViewHolder{
        private ViewDataBinding viewDataBinding;

        public ViewHolderSelectImages(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        private void bindData(int position){
            viewDataBinding.setVariable( imageLink, imageList.get( position ).getImage() );
            viewDataBinding.setVariable( isChecked, imageList.get( position ).isSelected() );
            viewDataBinding.setVariable( BR.isMultiSelectedOn, isMultiSelectedOn );
            viewDataBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
//                onChangeSelectedImage( position );
                selectImageListener.onSelectedImage( position, !imageList.get( position ).isSelected() );
            });

            itemView.setOnLongClickListener(v -> {
//                onChangeSelectedImage( position );
                selectImageListener.onLongPressedItem( position, !imageList.get( position ).isSelected() );
                return true;
            });

        }

    }

    // ----------- Helper Methods---------------
    public void onItemClicked( SelectImageListener selectImageListener ){
        this.selectImageListener = selectImageListener;
    }

    public void setMultiSelected( boolean multiSelected){
        isMultiSelectedOn = multiSelected;
        for ( int ind = 0; ind < imageList.size() ; ind ++ ){
            imageList.get(ind).setSelected( false );
        }
        currentSelectedItem = -1;
        AdaptorSelectImages.this.notifyDataSetChanged();
    }


    private void onChangeSelectedImage( int position ){
        if (currentSelectedItem >= 0 && !isMultiSelectedOn){
            // Remove Last Selected Image...
            imageList.get( currentSelectedItem ).setSelected( false );
            AdaptorSelectImages.this.notifyItemChanged( currentSelectedItem );
        }
        imageList.get( position ).setSelected( !imageList.get( position ).isSelected() );
        AdaptorSelectImages.this.notifyItemChanged( position );
        currentSelectedItem = position;
    }


}
