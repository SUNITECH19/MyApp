package com.ecom.starshop.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ModelRootDataItem implements Parcelable {

    private  List<ModelHome> listData = new ArrayList<>();

    // Collection -> Document Information....

    // Indexing...
    private int index;

    private String id;
    private String imageIcon;
    private String title;
    private String subTitle;
    private boolean visibility;

    //----------------------------------------------------------------------------

    public ModelRootDataItem() {
    }

    protected ModelRootDataItem(Parcel in) {
        index = in.readInt();
        id = in.readString();
        imageIcon = in.readString();
        title = in.readString();
        subTitle = in.readString();
        visibility = in.readByte() != 0;
    }

    public static final Creator<ModelRootDataItem> CREATOR = new Creator<ModelRootDataItem>() {
        @Override
        public ModelRootDataItem createFromParcel(Parcel in) {
            return new ModelRootDataItem(in);
        }

        @Override
        public ModelRootDataItem[] newArray(int size) {
            return new ModelRootDataItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeString(id);
        dest.writeString(imageIcon);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeByte((byte) (visibility ? 1 : 0));
    }
    //----------------------------------------------------------------------------

    public List<ModelHome> getListData() {
        return listData;
    }

    public void setListData(List<ModelHome> listData) {
        this.listData = listData;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(String imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
