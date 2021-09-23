package com.ecom.starshop.myclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.ecom.starshop.R;
import com.ecom.starshop.adaptor.AdaptorSelectedImages;
import com.ecom.starshop.databinding.DialogFragmentSelectedImagesEditBinding;
import com.ecom.starshop.helper.SelectImageListener;
import com.ecom.starshop.helper.StaticMethods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class DialogFragmentEditImageOption extends DialogFragment implements SelectImageListener.EditImageListener {

    private Context context;
    private String singleImageLink;
    private ArrayList<String> selectedImageList;
    private SelectImageListener.OnSelectedImageResponseListener imageResponseListener;

    public DialogFragmentEditImageOption(Context context, SelectImageListener.OnSelectedImageResponseListener imageResponseListener, @Nullable String singleImageLink,@Nullable ArrayList<String> selectedImageList) {
        this.context = context;
        this.imageResponseListener = imageResponseListener;
        this.singleImageLink = singleImageLink;
        this.selectedImageList = selectedImageList;
    }

    private DialogFragmentSelectedImagesEditBinding selectedImagesEditBinding;
    private AdaptorSelectedImages adaptorSelectedImages;
    private Uri croppedImageUri = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        selectedImagesEditBinding = DataBindingUtil.inflate( inflater, R.layout.dialog_fragment_selected_images_edit, container, false );
        selectedImagesEditBinding.setImageLink( singleImageLink );
        selectedImagesEditBinding.executePendingBindings();

        if (selectedImageList != null){
            adaptorSelectedImages = new AdaptorSelectedImages( selectedImageList, true, this );
            selectedImagesEditBinding.recyclerViewSelectedImages.setAdapter(adaptorSelectedImages);
            adaptorSelectedImages.notifyDataSetChanged();
        }

        onButtonAction();

        return selectedImagesEditBinding.getRoot();
    }

    private void onButtonAction(){
        // Crop Image Action ...
        selectedImagesEditBinding.buttonCropImage.setOnClickListener(v -> {
            onQueryToCrop( singleImageLink, -1 );

        });

        // Back Button
        selectedImagesEditBinding.imageButtonBack.setOnClickListener( v -> {
            this.dismiss();
        });

        // Back Button
        selectedImagesEditBinding.buttonDone.setOnClickListener( v -> {
            this.dismiss();
            imageResponseListener.onSendResponse( selectedImageList, croppedImageUri!=null ? String.valueOf(croppedImageUri) : singleImageLink );
        });
    }

    // Show Dialog to Edit Images....
    private void showDialogEditImageOption( Uri imageUri, int position ){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("FRAGMENT_EDIT_IMAGE");
        if (prev != null) {
            ft.remove(prev);
        }
        DialogFragmentCropImage selectBank = new DialogFragmentCropImage( this, position, imageUri );
        selectBank.show( fragmentManager, "FRAGMENT_EDIT_IMAGE");
    }

    //--------------------- Override Methods ------------------------------------------------------

    @Override
    public void onQueryToCrop(String imageLink, int position) {
        Uri uri;
        if ( imageLink.endsWith( ".jpeg") || imageLink.endsWith( ".jpg" ) || imageLink.endsWith(".png" )){
            uri = Uri.fromFile( new File( imageLink ) );
        }else{
            uri = Uri.parse( imageLink );
        }
        showDialogEditImageOption( uri, position );
    }

    @Override
    public void onImageEditResponse( @Nullable Uri imageUri, @Nullable Bitmap bitmap, int cropRequestCode ) {
        if (imageUri == null)
            imageUri = bitmap!=null? StaticMethods.getImageUri( bitmap, context ) : null;

        if ( imageUri != null )
            if ( cropRequestCode >= 0 ){
                // set Image...
                selectedImageList.set( cropRequestCode, String.valueOf( imageUri ) );
                adaptorSelectedImages.notifyItemChanged( cropRequestCode );
            }else{
                // Set Cropped Image...
                Glide.with( context )
                        .load( imageUri )
                        .into(selectedImagesEditBinding.imageView);
                croppedImageUri = imageUri;
            }

        Log.e("CROP", "Cropped Success!");
    }

    //-----------------------------------
    private void startCompressImage(@NonNull Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.JPEG,35, stream);
        byte[] BYTE = stream.toByteArray();
        Bitmap newBitmap = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);

        String path = MediaStore.Images.Media.insertImage( context.getContentResolver(), newBitmap, "Title", null);

//        onAddSingle( requestCode, getImageUri(newBitmap).toString(), Uri.parse( path ) );
        Log.e("PATH", "Path + "+ path);
        singleImageLink = path;
//        selectedImagesEditBinding.executePendingBindings();
        Glide.with( context )
                .load(singleImageLink)
                .into(selectedImagesEditBinding.imageView);
    }

}

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */