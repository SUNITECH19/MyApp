package com.sunitech.myapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.List;

public class ModelShipping implements Parcelable {

    private String shippingByName;
    private String shippingByMobile;
    private String shippingID;

    private List<ShippingUpdate> shippingUpdates;

    public String getShippingByName() {
        return shippingByName;
    }

    public void setShippingByName(String shippingByName) {
        this.shippingByName = shippingByName;
    }

    public String getShippingByMobile() {
        return shippingByMobile;
    }

    public void setShippingByMobile(String shippingByMobile) {
        this.shippingByMobile = shippingByMobile;
    }

    public String getShippingID() {
        return shippingID;
    }

    public void setShippingID(String shippingID) {
        this.shippingID = shippingID;
    }

    public List <ShippingUpdate> getShippingUpdates() {
        return shippingUpdates;
    }

    public void setShippingUpdates(List <ShippingUpdate> shippingUpdates) {
        this.shippingUpdates = shippingUpdates;
    }


    // ---------------------------------------------------------------------------------------------

    public ModelShipping() {
    }

    protected ModelShipping(Parcel in) {
        shippingByName = in.readString();
        shippingByMobile = in.readString();
        shippingID = in.readString();
    }

    public static final Creator <ModelShipping> CREATOR = new Creator <ModelShipping>() {
        @Override
        public ModelShipping createFromParcel(Parcel in) {
            return new ModelShipping( in );
        }

        @Override
        public ModelShipping[] newArray(int size) {
            return new ModelShipping[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( shippingByName );
        dest.writeString( shippingByMobile );
        dest.writeString( shippingID );
    }


    // ---------------------------------------------------------------------------------------------

    public static class ShippingUpdate implements Parcelable{
        private String status;
        private Timestamp timestamp;
        private String updateInfo;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public String getUpdateInfo() {
            return updateInfo;
        }

        public void setUpdateInfo(String updateInfo) {
            this.updateInfo = updateInfo;
        }

        // -----------------------------------------------------------------------------------------
        public ShippingUpdate() {
        }

        protected ShippingUpdate(Parcel in) {
            status = in.readString();
            timestamp = in.readParcelable( Timestamp.class.getClassLoader() );
            updateInfo = in.readString();
        }

        public static final Creator <ShippingUpdate> CREATOR = new Creator<ShippingUpdate>() {
            @Override
            public ShippingUpdate createFromParcel(Parcel in) {
                return new ShippingUpdate( in );
            }

            @Override
            public ShippingUpdate[] newArray(int size) {
                return new ShippingUpdate[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( status );
            dest.writeParcelable( timestamp, flags );
            dest.writeString( updateInfo );
        }
    }


}