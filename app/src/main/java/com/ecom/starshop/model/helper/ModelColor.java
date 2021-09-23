package com.ecom.starshop.model.helper;

public class ModelColor {
    private String colorName;
    private String colorCode;

    public ModelColor(String colorName, String colorCode) {
        this.colorName = colorName;
        this.colorCode = colorCode;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
