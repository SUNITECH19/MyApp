package com.ecom.starshop.helper;

import com.ecom.starshop.model.helper.ModelColor;

import java.util.ArrayList;
import java.util.List;

public class MyColor {

    public static final List<ModelColor> colorList = new ArrayList<>();
    public static final List<String> colorNameList = new ArrayList<>();


    // Instance Assignment Block : Colors...
    {
//        colorList.clear();
        colorList.add( new ModelColor("Black", "000000") );
        colorList.add( new ModelColor("White", "FFFFFF") );
        colorList.add( new ModelColor("Silver", "C0C0C0") );
        colorList.add( new ModelColor("Gray", "808080") );

        colorList.add(new ModelColor("Dark Gray", "A9A9A9"));
        colorList.add(new ModelColor("Red", "FF0000"));
        colorList.add(new ModelColor("Yellow", "FFFF00"));
        colorList.add(new ModelColor("Green", "008000"));
        colorList.add(new ModelColor("Blue", "0000FF"));
        colorList.add(new ModelColor("Orange", "FFA500"));
        colorList.add(new ModelColor("Cyan", "00FFFF"));
        colorList.add(new ModelColor("Magenta", "FF00FF"));
        colorList.add(new ModelColor("Pink", "FFC0CB"));
        colorList.add(new ModelColor("Aqua", "00FFFF"));
        colorList.add(new ModelColor("Lime", "00FF00"));
        colorList.add(new ModelColor("Navy Blue", "000080"));
        colorList.add(new ModelColor("Purple", "800080"));
        colorList.add(new ModelColor("Tomato", "FF6347"));
        colorList.add(new ModelColor("Violet", "EE82EE"));
        colorList.add(new ModelColor("Sky Blue", "87CEEB"));
        colorList.add(new ModelColor("Gold", "FFD700"));
        colorList.add(new ModelColor("Khaki", "F0E68C"));
        colorList.add(new ModelColor("Chocolate", "D2691E"));
        colorList.add(new ModelColor("Brown", "A52A2A"));

        colorList.add(new ModelColor("Transparent", "00000000"));

        for (ModelColor color: colorList){
            colorNameList.add( color.getColorName() );
        }
    }

    public static String getColorCode(String colorName){
        String coloCode = null;
        for (ModelColor color : colorList){
            if (color.getColorName().trim().equals( colorName.trim() )){
                coloCode = color.getColorCode();
                break;
            }
        }
        return coloCode;
    }

    public static String getColorName(String colorCode){
        if (colorCode.startsWith("#")){
            colorCode = colorCode.substring(1);
        }
        String coloName = null;
        for (ModelColor color : colorList){
            if (color.getColorCode().trim().equals( colorCode.toUpperCase().trim() )){
                coloName = color.getColorName();
                break;
            }
        }
        return coloName;
    }

    public static List<String> colorNameList(){
        return colorNameList;
    }


}
