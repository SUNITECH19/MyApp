package com.ecom.starshop.myclass;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.ecom.starshop.R;
import com.ecom.starshop.databinding.DialogFragmentCropImageBinding;
import com.ecom.starshop.helper.SelectImageListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static com.ecom.starshop.helper.Constant.DEGREE_0;
import static com.ecom.starshop.helper.Constant.DEGREE_180;
import static com.ecom.starshop.helper.Constant.DEGREE_270;
import static com.ecom.starshop.helper.Constant.DEGREE_90;
import static com.theartofdev.edmodo.cropper.CropImageView.CropShape.OVAL;
import static com.theartofdev.edmodo.cropper.CropImageView.CropShape.RECTANGLE;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class DialogFragmentCropImage extends DialogFragment implements CropImageView.OnCropImageCompleteListener, CropImageView.OnSetImageUriCompleteListener, PopupMenu.OnMenuItemClickListener {

    private SelectImageListener.EditImageListener editImageListener;
    private int cropRequestCode;
    // We use Uri to Crop Image.
    private Uri imageUri;
    // These other two way to work with.
    /*
    private String imageLink;
    private Bitmap imageBitmap;
     */

    public DialogFragmentCropImage(SelectImageListener.EditImageListener editImageListener, int cropRequestCode, Uri imageUri) {
        this.editImageListener = editImageListener;
        this.cropRequestCode = cropRequestCode;
        this.imageUri = imageUri;
    }

    private DialogFragmentCropImageBinding cropImageBinding;
    private CropImageView cropImageView;
    private PopupMenu popupMenu;
    private int crrDegree = 0;
//    private boolean isOverCrop = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cropImageBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_crop_image, container, false);
        cropImageView = cropImageBinding.cropperImageView;

        cropImageBinding.cropperImageView.setOnSetImageUriCompleteListener(this);
        cropImageBinding.cropperImageView.setOnCropImageCompleteListener(this);

        cropImageBinding.cropperImageView.setImageUriAsync(imageUri);
        setPopUpOptions(getContext(), cropImageBinding.imageButtonOptions );

        onButtonAction();
        return cropImageBinding.getRoot();
    }

    private void setPopUpOptions(Context context, View view) {
        popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_crop_options, popupMenu.getMenu());
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ) {
            popupMenu.setForceShowIcon(true);
        }
        popupMenu.setOnMenuItemClickListener(this);
    }

    private void onButtonAction() {
        // On Close Action
        cropImageBinding.imageButtonClose.setOnClickListener(v -> {
            this.dismiss();
        });

        // on Crop Done Clicked...
        cropImageBinding.imageButtonDone.setOnClickListener(v -> {
            cropImageView.getCroppedImageAsync();
        });

        // rotate Click...
        cropImageBinding.imageButtonRotate.setOnClickListener(v -> {
            switch (crrDegree) {
                case DEGREE_0:
                case DEGREE_90:
                case DEGREE_180:
                    crrDegree = crrDegree + DEGREE_90;
                    break;
                default:
                    crrDegree = DEGREE_0;
                    break;
            }
            cropImageView.setRotatedDegrees(crrDegree);
        });

        // Flip Image Ver...
        cropImageBinding.imageButtonFlipVr.setOnClickListener(v -> {
            cropImageView.setFlippedVertically(!cropImageView.isFlippedVertically());
        });

        // Flip Image Hor ...
        cropImageBinding.imageButtonFlipHr.setOnClickListener(v -> {
            cropImageView.setFlippedHorizontally(!cropImageView.isFlippedHorizontally());
        });

        // Flip Image Hor ...
        cropImageBinding.imageButtonOptions.setOnClickListener(v -> {
            if (popupMenu != null)
                popupMenu.show();
        });

    }

    // ------ Response --------------------------------------------------------------

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {

    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        handleCropResult(result);
    }

    private void handleCropResult(CropImageView.CropResult result) {
        if (result.getError() == null) {
            Bitmap bitmap = result.getBitmap();
//            if ( bitmap != null && isOverCrop){
//                bitmap = CropImage.toOvalBitmap( bitmap );
//            }
            editImageListener.onImageEditResponse(result.getUri(), bitmap, cropRequestCode);
            this.dismiss();
        } else {
            Log.e("AIC", "Failed to crop image", result.getError());
            Toast.makeText(
                    getActivity(),
                    "Image crop failed: " + result.getError().getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        /* We are not Use Circle Crop Image : Coz it give us Bitmap res*/
//        if (item.getItemId() == R.id.menu_crop_circle) {
//            cropImageView.setCropShape(OVAL);
//            cropImageView.setAspectRatio( 1, 1 );
//            cropImageView.setFixedAspectRatio( true );
////            isOverCrop = true;
//            return true;
//        } else
        if (item.getItemId() == R.id.menu_crop_square) {
            cropImageView.setCropShape(RECTANGLE);
            cropImageView.setAspectRatio( 1, 1 );
            cropImageView.setFixedAspectRatio( true );
            return true;
        } else if (item.getItemId() == R.id.menu_crop_3_2_ratio) {
            cropImageView.setCropShape(RECTANGLE);
            cropImageView.setAspectRatio( 3, 2 );
            cropImageView.setFixedAspectRatio( true );
            return true;
        } else if (item.getItemId() == R.id.menu_crop_16_9_ratio) {
            cropImageView.setCropShape(RECTANGLE);
            cropImageView.setAspectRatio( 16, 9 );
            cropImageView.setFixedAspectRatio( true );
            return true;
        } else if (item.getItemId() == R.id.menu_crop_free) {
            cropImageView.setCropShape(RECTANGLE);
            cropImageView.setFixedAspectRatio( false );
            return true;
        }
        return true;
    }


}

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */