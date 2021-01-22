package com.sunitech.myapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

public class ModelAddress implements Parcelable {

    private String houseNo;
    private String streetColony;
    private String city;
    private String state;
    private String landmark;
    private String pin;
    private GeoPoint geoPoint;

    // ---------------------------------------------------------------------------------------------
    public ModelAddress() {
    }

    protected ModelAddress(Parcel in) {
        houseNo = in.readString();
        streetColony = in.readString();
        city = in.readString();
        state = in.readString();
        landmark = in.readString();
        pin = in.readString();
    }

    public static final Creator <ModelAddress> CREATOR = new Creator <ModelAddress>() {
        @Override
        public ModelAddress createFromParcel(Parcel in) {
            return new ModelAddress( in );
        }

        @Override
        public ModelAddress[] newArray(int size) {
            return new ModelAddress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( houseNo );
        dest.writeString( streetColony );
        dest.writeString( city );
        dest.writeString( state );
        dest.writeString( landmark );
        dest.writeString( pin );
    }

    // ---------------------------------------------------------------------------------------------

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreetColony() {
        return streetColony;
    }

    public void setStreetColony(String streetColony) {
        this.streetColony = streetColony;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }


}