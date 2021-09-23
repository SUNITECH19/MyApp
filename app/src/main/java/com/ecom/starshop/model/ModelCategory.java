package com.ecom.starshop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class ModelCategory implements Parcelable{

    private int index;

    private String catID; // Category ID to reach or find the particular one
    private String catPath; // Path show the direction of category

    // Main Content
    private String catImageIcon;
    private String catTitle;
    private String catSubTitle;

    private boolean isVisible;

    // Banner Add time...
    private Timestamp addTime;

    //=---------------------------------------------------------------------------------------------


    public ModelCategory() {
    }

    protected ModelCategory(Parcel in) {
        index = in.readInt();
        catID = in.readString();
        catPath = in.readString();
        catImageIcon = in.readString();
        catTitle = in.readString();
        catSubTitle = in.readString();
        isVisible = in.readByte() != 0;
        addTime = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<ModelCategory> CREATOR = new Creator<ModelCategory>() {
        @Override
        public ModelCategory createFromParcel(Parcel in) {
            return new ModelCategory(in);
        }

        @Override
        public ModelCategory[] newArray(int size) {
            return new ModelCategory[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeString(catID);
        dest.writeString(catPath);
        dest.writeString(catImageIcon);
        dest.writeString(catTitle);
        dest.writeString(catSubTitle);
        dest.writeByte((byte) (isVisible ? 1 : 0));
        dest.writeParcelable(addTime, flags);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCatPath() {
        return catPath;
    }

    public void setCatPath(String catPath) {
        this.catPath = catPath;
    }

    public String getCatImageIcon() {
        return catImageIcon;
    }

    public void setCatImageIcon(String catImageIcon) {
        this.catImageIcon = catImageIcon;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getCatSubTitle() {
        return catSubTitle;
    }

    public void setCatSubTitle(String catSubTitle) {
        this.catSubTitle = catSubTitle;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
