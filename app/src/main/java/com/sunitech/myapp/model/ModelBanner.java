package com.sunitech.myapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class ModelBanner implements Parcelable {

    private String index;

    private String bannerID;
    private int bannerType;
    private String bannerImage;
    private String bannerClick;
    private String bannerExtraText;

    // Banner Add time...
    private Timestamp addTime;

    // ---------------------------------------------------------------------------------------------

    public ModelBanner() {
    }

    protected ModelBanner(Parcel in) {
        index = in.readString();
        bannerID = in.readString();
        bannerType = in.readInt();
        bannerImage = in.readString();
        bannerClick = in.readString();
        bannerExtraText = in.readString();
        addTime = in.readParcelable( Timestamp.class.getClassLoader() );
    }

    public static final Parcelable.Creator<ModelBanner> CREATOR = new Creator <ModelBanner>() {
        @Override
        public ModelBanner createFromParcel(Parcel in) {
            return new ModelBanner( in );
        }

        @Override
        public ModelBanner[] newArray(int size) {
            return new ModelBanner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( index );
        dest.writeString( bannerID );
        dest.writeInt( bannerType );
        dest.writeString( bannerImage );
        dest.writeString( bannerClick );
        dest.writeString( bannerExtraText );
        dest.writeParcelable( addTime, flags );
    }

    // ---------------------------------------------------------------------------------------------

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getBannerID() {
        return bannerID;
    }

    public void setBannerID(String bannerID) {
        this.bannerID = bannerID;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getBannerClick() {
        return bannerClick;
    }

    public void setBannerClick(String bannerClick) {
        this.bannerClick = bannerClick;
    }

    public String getBannerExtraText() {
        return bannerExtraText;
    }

    public void setBannerExtraText(String bannerExtraText) {
        this.bannerExtraText = bannerExtraText;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }
}