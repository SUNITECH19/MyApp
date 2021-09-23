package com.ecom.starshop.adaptor.addproduct;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.ecom.starshop.R;
import com.ecom.starshop.helper.AddProductListener;

import java.util.List;

import static com.ecom.starshop.BR.imageLink;
import static com.ecom.starshop.BR.indexText;

public class AdaptorNewImages extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_IMAGE = 0;
    private final int VIEW_TYPE_ADD_NEW = 1;

    private AddProductListener addProductListener;
    private List<String> imageList;

    public AdaptorNewImages(AddProductListener addProductListener, List<String> imageList) {
        this.addProductListener = addProductListener;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if ( viewType == VIEW_TYPE_IMAGE ){
            ViewDataBinding viewCategory = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_selected_image, parent, false);
            return new ViewHolderImage(viewCategory);
        }else {
            ViewDataBinding viewCategory = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_item_add_image_text_button, parent, false);
            return new ViewHolderAddNew(viewCategory);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof  ViewHolderImage){
            ((ViewHolderImage) holder).setData( position );
        }else {
            // Query To Add New...
            ((ViewHolderAddNew)holder).itemView.setOnClickListener( v -> addProductListener.onAddImageQuery() );
        }
    }

    @Override
    public int getItemCount() {
        if (imageList.size() < 8){ // Allow Maximum 8 Images...
            return imageList.size() + 1;
        }else {
            return imageList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ( position < imageList.size() ){
            return VIEW_TYPE_IMAGE;
        }else{
            return VIEW_TYPE_ADD_NEW;
        }
    }


    // ViewHolder ImageView --------------------------------
    private class ViewHolderImage extends RecyclerView.ViewHolder{
        private ViewDataBinding viewCategory;

        private ImageButton imageButton;

        public ViewHolderImage(@NonNull ViewDataBinding viewCategory) {
            super(viewCategory.getRoot());
            this.viewCategory = viewCategory;

            imageButton = viewCategory.getRoot().findViewById(R.id.imageViewCropped);

        }

        private void setData( int position ){
            imageButton.setImageResource(R.drawable.ic_baseline_close_24);

            viewCategory.setVariable( imageLink, imageList.get(position) );
            viewCategory.setVariable( indexText, String.valueOf( position + 1 ));
            viewCategory.executePendingBindings();

            imageButton.setOnClickListener( v -> {
                // Remove Image...
                addProductListener.onRemoveImage( position );
            });
        }

    }

    // ViewHolder Add New  --------------------------------

    private class ViewHolderAddNew extends RecyclerView.ViewHolder{
        private ViewDataBinding viewCategory;
        public ViewHolderAddNew(@NonNull ViewDataBinding viewCategory) {
            super(viewCategory.getRoot());
            this.viewCategory = viewCategory;
        }
    }


}
