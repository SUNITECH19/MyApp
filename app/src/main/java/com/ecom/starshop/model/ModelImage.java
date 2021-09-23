package com.ecom.starshop.model;

public class ModelImage {

    String image;
    String title;
    int imageRes;
    boolean isSelected = false;

    public ModelImage(String image, String title, int imageRes, boolean isSelected) {
        this.image = image;
        this.title = title;
        this.imageRes = imageRes;
        this.isSelected = isSelected;
    }

    public ModelImage() {
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
