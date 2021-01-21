package com.sunitech.myapp.others;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.sunitech.myapp.activity.ActivityMain;
import com.sunitech.myapp.helper.ConnectionListener;

import java.util.regex.Pattern;

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


}
