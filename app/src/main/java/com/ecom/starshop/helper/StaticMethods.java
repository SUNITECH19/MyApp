package com.ecom.starshop.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.ecom.starshop.R;
import com.ecom.starshop.activity.ActivityMain;
import com.ecom.starshop.myclass.MyOptionDialog;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class StaticMethods {

    public static void setConnectionListener( ConnectionListener connectionListener ){
        ActivityMain.connectionListener = connectionListener;
    }

    public static boolean isValidPhone(EditText editText){
        if (isFieldNotEmpty(editText)){
            String phone  = editText.getText().toString();
            if (phone.length()!=10){
                editText.setError("Not a Valid Phone!");
                return false;
            }else{
                return true;
            }
        }else {
            return false;
        }
    }
    ///////// Is Valid Email...
    public static boolean isValidEmail( EditText wReference ){
        String wEmail = wReference.getText().toString().trim();
        String emailRegex =
                "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        boolean bool = pat.matcher(wEmail).matches();

        if (TextUtils.isEmpty( wEmail )) {
            wReference.setError( "Please Enter Email! " );
            return false;
        } else if (!bool){
            wReference.setError( "Please Enter Valid Email! " );
            return false;
        }
        return true;
    }

    public static boolean isInputNumbers(String input){
        if (input == null )
            return false;
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (pattern.matcher(input).matches()) {
            return true;
        } else {
            return false;
        }
    }

    // Is valid Password..
    public static boolean isValidPassword(EditText editText1, EditText editText2){
        if (!isFieldNotEmpty(editText1) ) {
            return false;
        }else if (!isFieldNotEmpty(editText2)){
            return false;
        }else {
            String pass1 = editText1.getText().toString().trim();
            String pass2  = editText2.getText().toString().trim();
            if (pass1.equals(pass2)){
                return true;
            }else{
                editText1.setError("Not Matched!");
                editText2.setError("Not Matched!");
                return false;
            }
        }
    }

    public static boolean isFieldNotEmpty(EditText editText){
        if (TextUtils.isEmpty(editText.getText().toString())){
            editText.setError("Required!");
            return false;
        }else {
            return true;
        }
    }

//    public static void hideKeyboard(Context activity) {
//
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }

    public static void showCustomToast(Context context, String msg){
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // showing snack bar...
    public static void showInfoSnake(View layoutView, String message ){
        if (message != null && layoutView != null ){
            Snackbar.make( layoutView , message, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showActionSnake(View layoutView, String message ){
        // showing snack bar with Undo option...
        Snackbar snackbar = Snackbar.make( layoutView , message, Snackbar.LENGTH_LONG);
        snackbar.setAction( layoutView.getContext().getString(R.string.undo ), view -> {
            // undo is selected, restore the deleted item
        }).setActionTextColor( Color.YELLOW );
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                //  Log.e("DISN", "Dismiss!" + name +  deletedIndex );
            }

            @Override
            public void onShown(Snackbar snackbar) {

            }
        });
        snackbar.show();
    }

    public static ProgressDialog getProgressDialog(Context context, String msg){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false); // set cancelable to false
        if (msg != null){
            progressDialog.setMessage(msg); // set message
        }else {
            progressDialog.setMessage("Please wait..."); // set message
        }
        return progressDialog;
    }

    public static AlertDialog.Builder getAlertDialog(Context context, @NonNull String title, @Nullable String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setCancelable(true);
        if (msg != null)
            builder.setMessage( msg );
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        return builder;
    }

    public static void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public static void shareTextWithOthers(Context context, String title, String message){
        Intent inviteIntent = new Intent( Intent.ACTION_SEND );
        inviteIntent.setType("text/plain");
        inviteIntent.putExtra( Intent.EXTRA_SUBJECT, title );
        inviteIntent.putExtra( Intent.EXTRA_TEXT, message );
        context.startActivity( Intent.createChooser( inviteIntent,"Share By..." ) );
    }


    /// ------ Get File Access ----------------------------------------------------------------------------------
    public static File createImageFile() {
        File image = null;
        // Create an image file name
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + dateTime + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return image;
        }
        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
    }

    public static String getPath(Activity context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressLint("Recycle")
        Cursor cursor = context.getContentResolver().query( uri, projection, null, null, null);

//        Cursor cursor = context.managedQuery(uri, projection, null, null, null);

        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Uri getImageUri(Bitmap inImage, Context context ) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage( context.getContentResolver(), inImage, "Title", null);
//        MediaStore.Images.Media.getContentUri(  );
        return Uri.parse(path);
    }



    public static void getColorCode( String colorName ){

        Color color = new Color();


    }


}
