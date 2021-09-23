package com.ecom.starshop.helper;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ecom.starshop.R;

public class BindMethods {

    @BindingAdapter("setVisibility")
    public static void setVisibility( View view, boolean isVisible ){
        if (isVisible){
            view.setVisibility(View.VISIBLE);
        }else view.setVisibility(View.GONE);
    }


    @BindingAdapter("setVisibility")
    public static void setVisibility( View view, String string ){
        if (string!=null && !string.equals("")){
            view.setVisibility(View.VISIBLE);
        }else view.setVisibility(View.GONE);
    }


    @BindingAdapter("setInvisibility")
    public static void setInvisibility( View view, String string ){
        if (string!=null && !string.equals("")){
            view.setVisibility(View.GONE);
        }else view.setVisibility(View.VISIBLE);
    }


    @BindingAdapter("setEyeIcon")
    public static void setEyeIcon( ImageView imageView, boolean isVisible ){
        if (isVisible) {
            imageView.setImageResource(R.drawable.ic_outline_visibility_off_24);
        } else {
            imageView.setImageResource(R.drawable.ic_outline_visibility_24);
        }
    }

    // ----------- Dialog Bind--------------------------------------------------------
    @BindingAdapter("setDialogIcon")
    public static void setDialogIcon( ImageView imageView, int type ){
        if (type == -1){
            return;
        }
        imageView.setImageResource( type );
    }

    @BindingAdapter("setDialogTitleColor")
    public static void setDialogTitleColor( TextView imageView, int type ){

    }


    // ------------- Select Image Picker --------------------------------------------------
    @BindingAdapter("setPickerImage")
    public static void setPickerImage( ImageView imageView, String string ){
        Glide.with(imageView.getContext())
                .load(string)
                .placeholder(R.color.colorGrayLight)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    @BindingAdapter("setImage")
    public static void setImage( ImageView imageView, String string ){
        Glide.with(imageView.getContext())
                .load(string)
                .placeholder(R.drawable.logo_placeholder)
                .into(imageView);
    }

    // Set Section Background, If Visibility is OFF ?
    @SuppressLint("UseCompatLoadingForDrawables")
    @BindingAdapter("setSectionBG")
    public static void setSectionBG( View view, boolean isVisible ){
        if (isVisible){
//            view.setBackgroundTintList(ColorStateList.valueOf(view.getContext().getResources().getColor(R.color.colorWhite)));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setForeground( null );
            }
        }else {
//            view.setBackgroundTintList(ColorStateList.valueOf(view.getContext().getResources().getColor(R.color.colorGrayLight)));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setForeground( view.getContext().getDrawable(R.drawable.back_design_curve_small_fill));
                view.setForegroundTintList(ColorStateList.valueOf(view.getContext().getColor(R.color.colorFadedLight)));
            }
        }
    }
    // Set View Background Code?
    @SuppressLint("UseCompatLoadingForDrawables")
    @BindingAdapter("setBackgroundTint")
    public static void setBackgroundTint( View view, String colorCode ){
        if (colorCode!=null){
            colorCode = colorCode.charAt(0)=='#'? colorCode : "#"+colorCode;

            if (colorCode.length() == 4 || colorCode.length() == 7 || colorCode.length() == 9){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    view.setBackground( view.getContext().getDrawable(R.drawable.back_design_curve_small_fill));
                    view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor( colorCode )));
                }
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                view.setBackground( view.getContext().getDrawable(R.drawable.back_design_curve_small_fill));
                view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor( "#" + "ffffff" )));
            }
        }
    }




}
