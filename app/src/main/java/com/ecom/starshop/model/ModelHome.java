package com.ecom.starshop.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelHome implements Parcelable {

    private int index;

    private int layoutType; // layout Type

    private String layoutID; // document ID

    /**
     * layoutTitle : It is normally used to store title text.
     * So it can hold ->
     * 1. Title text ( For products layout )
     */
    private String layoutTitle; // Title
    private String layoutSubTitle; // Sub Title
    /**
     * layoutIcon : is used to hold any type of image or icon link, if needed!
     */
    private String layoutIcon; // Icon or Image
    /**
     * Visibility help You to reduce no. of documents reads and increase response time.
     */
    private boolean layoutVisibility; // To know whether it's Visible or not!

    /**
     * To hold strip banner ad info.
     */
    private ModelBanner bannerLayout;

    /** To store data document ID.! ( Such As Product's ID List ) */
    private List <String> layoutItems;
    /**
     * We have to define different List variable to hold banner Slider List, Category Item List, Sub-Category List
     */

    private List<ModelCategory> categoryList; // Category List + SubCategory List

    private List<ModelBanner> bannerList; // Banner Slider....

    // Documents can be -
    // 1. Banner List ( Slider ) -> bannerList
    // 2. Product List ( in different Design Type )
    // 3. Category List.. ( If Any.. ) -> categoryList
    // 4. Sub-Category List.. ( If Any.. ) -> categoryList

    //----------------------------------------------------------------------------------------------

    public ModelHome() {
    }

    public ModelHome(int layoutType) {
        this.layoutType = layoutType;
    }

    public ModelHome(int index, int layoutType, String layoutID, String layoutTitle, String layoutSubTitle, String layoutIcon, boolean layoutVisibility, ModelBanner bannerLayout, List<String> layoutItems, List<ModelCategory> categoryList, List<ModelBanner> bannerList) {
        this.index = index;
        this.layoutType = layoutType;
        this.layoutID = layoutID;
        this.layoutTitle = layoutTitle;
        this.layoutSubTitle = layoutSubTitle;
        this.layoutIcon = layoutIcon;
        this.layoutVisibility = layoutVisibility;
        this.bannerLayout = bannerLayout;
        this.layoutItems = layoutItems;
        this.categoryList = categoryList;
        this.bannerList = bannerList;
    }

    public ModelHome(int index, int layoutType, String layoutID, String layoutTitle, String layoutSubTitle, String layoutIcon, boolean layoutVisibility, List<String> layoutItems) {
        this.index = index;
        this.layoutType = layoutType;
        this.layoutID = layoutID;
        this.layoutTitle = layoutTitle;
        this.layoutSubTitle = layoutSubTitle;
        this.layoutIcon = layoutIcon;
        this.layoutVisibility = layoutVisibility;
        this.layoutItems = layoutItems;
    }


    protected ModelHome(Parcel in) {
        index = in.readInt();
        layoutType = in.readInt();
        layoutID = in.readString();
        layoutTitle = in.readString();
        layoutSubTitle = in.readString();
        layoutIcon = in.readString();
        layoutVisibility = in.readByte() != 0;
        bannerLayout = in.readParcelable(ModelBanner.class.getClassLoader());
        layoutItems = in.createStringArrayList();
        categoryList = in.createTypedArrayList(ModelCategory.CREATOR);
        bannerList = in.createTypedArrayList(ModelBanner.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeInt(layoutType);
        dest.writeString(layoutID);
        dest.writeString(layoutTitle);
        dest.writeString(layoutSubTitle);
        dest.writeString(layoutIcon);
        dest.writeByte((byte) (layoutVisibility ? 1 : 0));
        dest.writeParcelable(bannerLayout, flags);
        dest.writeStringList(layoutItems);
        dest.writeTypedList(categoryList);
        dest.writeTypedList(bannerList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelHome> CREATOR = new Creator<ModelHome>() {
        @Override
        public ModelHome createFromParcel(Parcel in) {
            return new ModelHome(in);
        }

        @Override
        public ModelHome[] newArray(int size) {
            return new ModelHome[size];
        }
    };

    //----------------------------------------------------------------------------------------------

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(String layoutID) {
        this.layoutID = layoutID;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public String getLayoutTitle() {
        return layoutTitle;
    }

    public void setLayoutTitle(String layoutTitle) {
        this.layoutTitle = layoutTitle;
    }

    public String getLayoutSubTitle() {
        return layoutSubTitle;
    }

    public void setLayoutSubTitle(String layoutSubTitle) {
        this.layoutSubTitle = layoutSubTitle;
    }

    public String getLayoutIcon() {
        return layoutIcon;
    }

    public void setLayoutIcon(String layoutIcon) {
        this.layoutIcon = layoutIcon;
    }

    public boolean isLayoutVisibility() {
        return layoutVisibility;
    }

    public void setLayoutVisibility(boolean layoutVisibility) {
        this.layoutVisibility = layoutVisibility;
    }

    public ModelBanner getBannerLayout() {
        return bannerLayout;
    }

    public void setBannerLayout(ModelBanner bannerLayout) {
        this.bannerLayout = bannerLayout;
    }

    public List <String> getLayoutItems() {
        return layoutItems;
    }

    public void setLayoutItems(List <String> layoutItems) {
        this.layoutItems = layoutItems;
    }

    public List<ModelCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ModelCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ModelBanner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<ModelBanner> bannerList) {
        this.bannerList = bannerList;
    }




}