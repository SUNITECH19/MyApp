package com.ecom.starshop.helper;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.List;

public interface SelectImageListener {

    void onLongPressedItem( int position, boolean isChecked);
    void onSelectedImage( int position, boolean isChecked );

    interface EditImageListener{
        void onQueryToCrop( String imageLink, int position );
        void onImageEditResponse(@Nullable  Uri imageUri, @Nullable Bitmap bitmap, int cropRequestCode );
    }

    interface OnSelectedImageResponseListener {

        void onSendResponse( @Nullable List<String> imageList, @Nullable String imageLink );

    }

}

