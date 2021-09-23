package com.ecom.starshop.myclass;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.ecom.starshop.R;
import com.ecom.starshop.adaptor.AdaptorSelectImages;
import com.ecom.starshop.adaptor.AdaptorSelectedImages;
import com.ecom.starshop.databinding.DialogFragmentSelectImagesBinding;
import com.ecom.starshop.helper.SelectImageListener;
import com.ecom.starshop.helper.StaticMethods;
import com.ecom.starshop.model.ModelImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.ecom.starshop.helper.Constant.GALLERY_CODE;
import static com.ecom.starshop.helper.Constant.REQUEST_CODE_FOR_MULTI_IMAGE;
import static com.ecom.starshop.helper.Constant.REQUEST_CODE_FOR_SINGLE_IMAGE;
import static com.ecom.starshop.helper.Constant.REQUEST_IMAGE_CAPTURE;
import static com.ecom.starshop.helper.StaticMethods.createImageFile;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class DialogFragmentSelectImages extends DialogFragment implements SelectImageListener.OnSelectedImageResponseListener {

    private int imageRequestCode = -1;
    private int maxAllow = 1;
    private Context context;
    private SelectImageListener.OnSelectedImageResponseListener parentListener;

    public DialogFragmentSelectImages(int imageRequestCode, int maxAllow, Context context, SelectImageListener.OnSelectedImageResponseListener parentListener) {
        this.imageRequestCode = imageRequestCode;
        this.maxAllow = maxAllow;
        this.context = context;
        this.parentListener = parentListener;
    }

    private List<ModelImage> imageList = new ArrayList<>();
    private ArrayList<String> selectedImageList = new ArrayList<>();

    private boolean isMultiSelectedOn = false;
    private String singleImageLink = null;
    private String[] projection = {MediaStore.MediaColumns.DATA};
    private String tempAbsolutePath = null;

    private AdaptorSelectImages adaptorSelectImagesOptions;
    private AdaptorSelectedImages adaptorSelectedImages;

    // Activity Result...
    private ActivityResultLauncher<Intent> launcherGallery;
    private ActivityResultLauncher<Intent> launcherCamera;
    private ActivityResultLauncher<String> launcherPermission;
    private ActivityResultLauncher<String> launcherPermissionCamera;

    private DialogFragmentSelectImagesBinding selectImagesBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        selectImagesBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_select_images, container, false);
        if (imageRequestCode == REQUEST_CODE_FOR_SINGLE_IMAGE) {
            selectImagesBinding.imageButtonMultiOption.setEnabled(false);
            selectImagesBinding.imageButtonMultiOption.setVisibility(View.INVISIBLE);
            isMultiSelectedOn = false;
            selectImagesBinding.toolBarTitle.setText("Select A Image");
        }

        adaptorSelectImagesOptions = new AdaptorSelectImages(imageList);
        selectImagesBinding.recyclerViewShowImages.setAdapter(adaptorSelectImagesOptions);

        adaptorSelectedImages = new AdaptorSelectedImages(selectedImageList);
        selectImagesBinding.recyclerViewSelectedImages.setAdapter(adaptorSelectedImages);

        onButtonAction();
        onActivityResult();

        if (isStoragePermissionGranted()) {
            getAllImages();
            setImageList();
        }

        return selectImagesBinding.getRoot();
    }

    private void onButtonAction() {
        // Back Button Clicked....
        selectImagesBinding.imageButtonBack.setOnClickListener(v -> {
            this.dismiss();
        });
        // On Done Button Action...
        selectImagesBinding.buttonNext.setOnClickListener(v -> {
            if (singleImageLink == null && selectedImageList.size() == 0) {
                showSnackBar("Please Select Any Image first!");
                return;
            }
            showDialogEditImageOption();
        });

        // Multi Select Option ON/OFF
        selectImagesBinding.imageButtonMultiOption.setOnClickListener(v -> {
            isMultiSelectedOn = !isMultiSelectedOn;
            adaptorSelectImagesOptions.setMultiSelected(isMultiSelectedOn);
            if (!isMultiSelectedOn) {
                singleImageLink = selectedImageList.size() > 0 ? selectedImageList.get(selectedImageList.size() - 1) : singleImageLink;
            }
            selectedImageList.clear();
            setButtonMultiSelectUI();
            setSelectedImageListUI(selectedImageList.size());
            setSelectedView(isMultiSelectedOn);
            adaptorSelectedImages.notifyDataSetChanged();
        });
        // Select Using Camera...
        selectImagesBinding.imageButtonCamera.setOnClickListener(v -> {
            if (isAllowedMore() && isCameraPermissionOk()) {
                takePictureReqToCamera();
            }
        });
        // Select From File....
        selectImagesBinding.imageButtonFile.setOnClickListener(v -> {
            if (isAllowedMore()) {
                if (imageRequestCode == REQUEST_CODE_FOR_SINGLE_IMAGE) {
                    queryToSelectImage();
                } else {
                    queryToSelectMultiImages();
                }
            }
        });

    }

    private void setSelectedView(boolean isMultiSelectedOn) {
        if (isMultiSelectedOn) {
            selectImagesBinding.recyclerViewSelectedImages.setVisibility(View.VISIBLE);
            if (selectedImageList.size() == 0) {
                selectImagesBinding.imageViewSelected.setVisibility(View.VISIBLE);
                loadSelectedImage();
            } else {
                selectImagesBinding.imageViewSelected.setVisibility(View.GONE);
            }
        } else {
            selectImagesBinding.recyclerViewSelectedImages.setVisibility(View.GONE);
            selectImagesBinding.imageViewSelected.setVisibility(View.VISIBLE);
        }
    }

    private void setSelectedImageListUI(int size) {
        if (size > 1) {
            selectImagesBinding.textViewShowText.setText(size + " Images Selected");
        } else if (size == 1) {
            selectImagesBinding.textViewShowText.setText(size + " Image Selected");
        } else {
            selectImagesBinding.textViewShowText.setText("");
        }
    }

    private void setButtonMultiSelectUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isMultiSelectedOn)
                selectImagesBinding.imageButtonMultiOption.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSecondary)));
            else
                selectImagesBinding.imageButtonMultiOption.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        }
    }

    private void loadSelectedImage() {
        Glide.with(context)
                .load(singleImageLink != null ? singleImageLink : "")
                .placeholder(R.drawable.ic_outline_image_gray_24)
                .into(selectImagesBinding.imageViewSelected);
    }


    // Show Dialog to Edit Images....
    private void showDialogEditImageOption() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("FRAGMENT_EDIT_IMAGE");
        if (prev != null) {
            ft.remove(prev);
        }
        DialogFragmentEditImageOption selectBank = new DialogFragmentEditImageOption(context, this, selectedImageList.size() > 0 ? null : singleImageLink, selectedImageList);
        selectBank.show(fragmentManager, "FRAGMENT_EDIT_IMAGE");
    }

    // Get Result of Image...
    /**
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Response From Gallery...
        if (requestCode == GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getClipData() != null) {
                        // Set MultiSelected On...
                        if (imageRequestCode == REQUEST_CODE_FOR_MULTI_IMAGE) {
                            isMultiSelectedOn = true;
                            adaptorSelectImagesOptions.setMultiSelected(true);
                            setButtonMultiSelectUI();
                        }


                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            if (isAllowedMore()) { // Check Allow ..?
                                getImageFilePath(uri);
                            }
                        }
                    } else {
                        Uri uri = data.getData();

                        if (isAllowedMore()) { // Check Allow ..?
                            getImageFilePath(uri);
                        }
                        // Query to Crop Image...
//                        startCropImageActivity(uri);
                    }
                } else {
                    showToast("Image not Found..!");
                }
            }
        }
        // Response from Camera....
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (tempAbsolutePath != null) {
                    if (isAllowedMore()) {
                        addImageFromFileAndCamera(tempAbsolutePath, true);
                    }
                } else {
                    Log.e("ABS_PATH", "null");
                }
            } else {
                Log.e("RESULT", "RESULT = " + resultCode);
            }
        }

    }
    */

    private void onActivityResult() {
        // Load Gallery Images...
        launcherGallery =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
//                            result.getResultCode();
                            Intent data = result.getData();

                            if (data != null) {
                                Log.e("Result", "Data : " + data.toString());
                                if (data.getClipData() != null) {
                                    // Set MultiSelected On...
                                    if (imageRequestCode == REQUEST_CODE_FOR_MULTI_IMAGE) {
                                        isMultiSelectedOn = true;
                                        adaptorSelectImagesOptions.setMultiSelected(true);
                                        setButtonMultiSelectUI();
                                    }


                                    ClipData mClipData = data.getClipData();
                                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                                        ClipData.Item item = mClipData.getItemAt(i);
                                        Uri uri = item.getUri();

                                        if (isAllowedMore()) { // Check Allow ..?
                                            getImageFilePath(uri);
                                        }
                                    }
                                } else {
                                    Uri uri = data.getData();

                                    if (isAllowedMore()) { // Check Allow ..?
                                        getImageFilePath(uri);
                                    }
                                    // Query to Crop Image...
//                        startCropImageActivity(uri);
                                }
                            } else {
                                showToast("Image not Found..!");
                            }
                        }
                );

        // Take Camera Image...
        launcherCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (tempAbsolutePath != null) {
                        if (isAllowedMore()) {
                            addImageFromFileAndCamera(tempAbsolutePath, true);
                        }
                    } else {
                        Log.e("ABS_PATH", "null");
                    }
                }
        );

        // Permission response of Storage...
        launcherPermission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                        isGranted -> {
                            if (isGranted) {
                                if (isStoragePermissionGranted()){
                                    getAllImages();
                                    setImageList();
                                }
                            } else {
                                // Explain to the user that the feature is unavailable because the
                                // features requires a permission that the user has denied. At the
                                // same time, respect the user's decision. Don't link to system
                                // settings in an effort to convince the user to change their
                                // decision.
                            }
                        });

        // Permission Response of Camera...
        launcherPermissionCamera =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                        isGranted -> {
                            if (isGranted) {
                                takePictureReqToCamera();
                            } else {
                                //
                            }
                        });


    }

    // ------------ Helper Methods -----------------------------------------------------------------
    // Get image file path
    public void getImageFilePath(Uri uri) {
        String absPath = StaticMethods.getPath((Activity) context, uri);
        if (absPath != null) {
            checkImage(absPath);
        } else {
            checkImage(String.valueOf(uri));
        }

//        Cursor cursor = context.getContentResolver().query( uri, projection, null, null, null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
//                cursor.moveToFirst();
//                if (absolutePathOfImage != null) {
//                    checkImage(absolutePathOfImage);
//                } else {
//                    checkImage(String.valueOf(uri));
//                }
//            }
//        }

    }

    private void showToast(String msg) {
        StaticMethods.showCustomToast(context, msg);
    }

    private void showSnackBar(String msg) {
        StaticMethods.showInfoSnake(selectImagesBinding.getRoot(), msg);
    }

    // Checked : Whether Allow to add more Images Or Not.?
    private boolean isAllowedMore() {
        if (!(selectedImageList.size() < maxAllow)) {
            if (maxAllow == 1) {
                showSnackBar("You have allowed only 1 Image to select.");
            } else showSnackBar("Max " + maxAllow + " Images Allowed!");

            return false;
        }
        return true;
    }

    public void setImageList() {
        adaptorSelectImagesOptions.onItemClicked(new SelectImageListener() {
            @Override
            public void onLongPressedItem(int position, boolean isChecked) {
                if (imageRequestCode != REQUEST_CODE_FOR_SINGLE_IMAGE) {
                    if (!isMultiSelectedOn) {
                        isMultiSelectedOn = true;
                        adaptorSelectImagesOptions.setMultiSelected(true);
                        setButtonMultiSelectUI();
                    }
                }
                onCheckedImage(position, isChecked);
            }

            @Override
            public void onSelectedImage(int position, boolean isChecked) {
                onCheckedImage(position, isChecked);
            }
        });

        adaptorSelectImagesOptions.notifyDataSetChanged();
        singleImageLink = imageList.size() > 0 ? imageList.get(0).getImage() : "";
        loadSelectedImage();
    }

    public void onCheckedImage(int position, boolean isChecked) {
        if (isMultiSelectedOn) {
            if (isChecked) {
                // Check before Selection : Max Allow
                if (isAllowedMore()) {
                    selectImage(position);
                }
            } else {
                unSelectImage(position);
            }
            setSelectedView(true);
        } else {
            setSelectedView(false);
            singleImageLink = imageList.get(position).getImage();
            Log.e("PATH", singleImageLink);
            loadSelectedImage();
        }
    }

    // Add image in selectedImageList
    public void selectImage(int position) {
        // Check before add new item in ArrayList;
        if (!selectedImageList.contains(imageList.get(position).getImage())) {
            selectedImageList.add(imageList.get(position).getImage());
            imageList.get(position).setSelected(true);
            adaptorSelectImagesOptions.notifyItemChanged(position);
            adaptorSelectedImages.notifyDataSetChanged();
            setSelectedImageListUI(selectedImageList.size());
        }
    }

    // Remove image from selectedImageList
    public void unSelectImage(int position) {
        for (int i = 0; i < selectedImageList.size(); i++) {
            if (imageList.get(position).getImage() != null) {
                if (selectedImageList.get(i).equals(imageList.get(position).getImage())) {
                    imageList.get(position).setSelected(false);
                    selectedImageList.remove(i);
                    adaptorSelectImagesOptions.notifyItemChanged(position);
                    adaptorSelectedImages.notifyDataSetChanged();
                    setSelectedImageListUI(selectedImageList.size());
                }
            }
        }
    }

    public void checkImage(String filePath) {
        // Check before adding a new image to ArrayList to avoid duplicate images
        if (!selectedImageList.contains(filePath)) {
            if (imageRequestCode == REQUEST_CODE_FOR_MULTI_IMAGE)
                for (int pos = 0; pos < imageList.size(); pos++) {
                    if (imageList.get(pos).getImage() != null && imageList.get(pos).getImage().equalsIgnoreCase(filePath)) {
//                            imageList.remove(pos);
                        imageList.get(pos).setSelected(true);
                        adaptorSelectImagesOptions.notifyItemChanged(pos);
                    }
                }
            addImageFromFileAndCamera(filePath, false);
        }
    }

    // add image in selectedImageList and imageList
    public void addImageFromFileAndCamera(String filePath, boolean isSelectFromCamera) {
        if (isMultiSelectedOn) {
            if (isSelectFromCamera) {
                ModelImage imageModel = new ModelImage();
                imageModel.setImage(filePath);
                imageModel.setSelected(true);
                imageList.add(0, imageModel);
                adaptorSelectImagesOptions.notifyItemInserted(0);
            }
            selectedImageList.add(filePath);
            adaptorSelectedImages.notifyDataSetChanged();
            setSelectedView(true);
            setSelectedImageListUI(selectedImageList.size());
        } else {
            singleImageLink = filePath;
            loadSelectedImage();
        }
    }

    // ----------- Device Contact -----------------------------------------------------------------

    public void queryToSelectImage() {
        // Request...
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
//        startActivityForResult( galleryIntent, GALLERY_CODE );
        launcherGallery.launch(galleryIntent);
    }

    public void queryToSelectMultiImages() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
//        startActivityForResult(galleryIntent, GALLERY_CODE);
        launcherGallery.launch(galleryIntent);
    }

    // get all images from external storage
    public void getAllImages() {
        imageList.clear();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            ModelImage ImageModel = new ModelImage();
            ImageModel.setImage(absolutePathOfImage);
            imageList.add(ImageModel);
        }
        cursor.close();
    }

    // start the image capture Intent
    public void takePictureReqToCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Continue only if the File was successfully created;
        File photoFile = createImageFile();
        if (photoFile != null) {
//            tempAbsolutePath = "file:" + photoFile.getAbsolutePath();
            tempAbsolutePath = photoFile.getAbsolutePath();
            Log.e("ABS_PATH", "PATH = " + tempAbsolutePath);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            launcherCamera.launch( cameraIntent );
        } else {
            Log.e("ABS_PATH", "photoFile = null");
        }
    }

    // ----------- User Permission -----------------------------------------------------------------

    public boolean isStoragePermissionGranted() {
        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (READ_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED && ACCESS_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            return true;
        } else if ( READ_EXTERNAL_STORAGE == PackageManager.PERMISSION_DENIED ){
            launcherPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            return false;
        }else if ( ACCESS_EXTERNAL_STORAGE == PackageManager.PERMISSION_DENIED ){
            launcherPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return false;
        } else
            if (shouldShowRequestPermissionRationale("")) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
//            showInContextUI(...);
        }
            return false;
    }

    private boolean isCameraPermissionOk(){
        int CAMERA = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA );
        if (CAMERA == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            return true;
        } else {
            launcherPermissionCamera.launch(Manifest.permission.CAMERA);
            return false;
        }
    }

//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
//
//        }
//    }

    // -------------------------------- Send Result ------------------------------------------------
//    public abstract void onAddSingle( int requestCode, @Nullable String imagePath,@Nullable Uri imageUri );
    @Override
    public void onSendResponse(@Nullable List<String> imageList, @Nullable String imageLink) {
        this.dismiss();
        parentListener.onSendResponse(imageList, imageLink);
    }

}

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */