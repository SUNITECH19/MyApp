package com.sunitech.myapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ModelHome implements Parcelable {

    private String index;

    private String layoutID;
    private int layoutType;
    private String layoutTitle;
    private String layoutSubTitle;
    private String layoutIcon;
    private boolean layoutVisibility;

    // To store data document ID.!
    private List <String> layoutItems;
    // Documents can be -
    // 1. Banner List ( Slider )
    // 2. Product List ( in different Design Type )
    // 3. Category List.. ( If Any.. )
    // 4. Sub-Category List.. ( If Any.. )

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
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

    public List <String> getLayoutItems() {
        return layoutItems;
    }

    public void setLayoutItems(List <String> layoutItems) {
        this.layoutItems = layoutItems;
    }

    //----------------------------------------------------------------------------------------------

    public ModelHome() {
    }

    protected ModelHome(Parcel in) {
        index = in.readString();
        layoutID = in.readString();
        layoutType = in.readInt();
        layoutTitle = in.readString();
        layoutSubTitle = in.readString();
        layoutIcon = in.readString();
        layoutVisibility = in.readByte() != 0;
        layoutItems = in.createStringArrayList();
    }

    public static final Creator <ModelHome> CREATOR = new Creator <ModelHome>() {
        @Override
        public ModelHome createFromParcel(Parcel in) {
            return new ModelHome( in );
        }

        @Override
        public ModelHome[] newArray(int size) {
            return new ModelHome[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( index );
        dest.writeString( layoutID );
        dest.writeInt( layoutType );
        dest.writeString( layoutTitle );
        dest.writeString( layoutSubTitle );
        dest.writeString( layoutIcon );
        dest.writeByte( (byte) (layoutVisibility ? 1 : 0) );
        dest.writeStringList( layoutItems );
    }
}