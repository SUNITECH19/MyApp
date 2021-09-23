package com.ecom.starshop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

public class ModelBanner implements Parcelable {

    private int index; // document Index

    /**
     * Banner Type :
     * -> Product Promotion Banner, Web/Url Link banner, No Action Banner
     */
    private int bannerType;
    /**
     * bannerID : is used to get Banner ID
     * -> It can be used when we get similar banner Item in the list form
     * -> It can be used to calculate User Action.!
     */
    private String bannerID;
    /**
     * Action Report On : Yes,
     * Meaning : whenever user do any action on the banner our function will save records on our server.
     * No, Meaning we are not get user action report.
     */
    private boolean isActionReportOn;

    private String bannerImage;
    private String bannerClick;
    private String bannerExtraText;

    // Banner Add time...
    private Timestamp addTime;

    // ---------------------------------------------------------------------------------------------

    public ModelBanner() {
    }

    protected ModelBanner(Parcel in) {
        index = in.readInt();
        bannerID = in.readString();
        bannerType = in.readInt();
        isActionReportOn = in.readByte() != 0;
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
        dest.writeInt( index );
        dest.writeString( bannerID );
        dest.writeInt( bannerType );
        dest.writeByte( (byte) (isActionReportOn ? 1 : 0) );
        dest.writeString( bannerImage );
        dest.writeString( bannerClick );
        dest.writeString( bannerExtraText );
        dest.writeParcelable( addTime, flags );
    }

    // ---------------------------------------------------------------------------------------------

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
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

    public boolean isActionReportOn() {
        return isActionReportOn;
    }

    public void setActionReportOn(boolean actionReportOn) {
        isActionReportOn = actionReportOn;
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