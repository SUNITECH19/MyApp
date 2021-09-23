package com.ecom.starshop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.List;

public class ModelOrder implements Parcelable {

    private String index;

    private String orderID;

    private String orderBillAmount;
    private String orderAmount;
    private String orderShippingCharge;

    private Timestamp orderTimestamp;

    private String deliveryStatus;
    private String shippingID;

    private ModelAddress shippingAddress;
    private ModelShipping shippingUpdates;

    private List<OrderProductItem> productItemList;

    // ---------------------------------------------------------------------------------------------

    public ModelOrder() {
    }

    protected ModelOrder(Parcel in) {
        index = in.readString();
        orderID = in.readString();
        orderBillAmount = in.readString();
        orderAmount = in.readString();
        orderShippingCharge = in.readString();
        orderTimestamp = in.readParcelable( Timestamp.class.getClassLoader() );
        shippingAddress = in.readParcelable( ModelAddress.class.getClassLoader() );
        deliveryStatus = in.readString();
        shippingID = in.readString();
    }

    public static final Creator <ModelOrder> CREATOR = new Creator<ModelOrder>() {
        @Override
        public ModelOrder createFromParcel(Parcel in) {
            return new ModelOrder( in );
        }

        @Override
        public ModelOrder[] newArray(int size) {
            return new ModelOrder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( index );
        dest.writeString( orderID );
        dest.writeString( orderBillAmount );
        dest.writeString( orderAmount );
        dest.writeString( orderShippingCharge );
        dest.writeParcelable( orderTimestamp, flags );
        dest.writeParcelable( shippingAddress, flags );
        dest.writeString( deliveryStatus );
        dest.writeString( shippingID );
    }
    // ---------------------------------------------------------------------------------------------

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderBillAmount() {
        return orderBillAmount;
    }

    public void setOrderBillAmount(String orderBillAmount) {
        this.orderBillAmount = orderBillAmount;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderShippingCharge() {
        return orderShippingCharge;
    }

    public void setOrderShippingCharge(String orderShippingCharge) {
        this.orderShippingCharge = orderShippingCharge;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public ModelAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ModelAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getShippingID() {
        return shippingID;
    }

    public void setShippingID(String shippingID) {
        this.shippingID = shippingID;
    }

    public ModelShipping getShippingUpdates() {
        return shippingUpdates;
    }

    public void setShippingUpdates(ModelShipping shippingUpdates) {
        this.shippingUpdates = shippingUpdates;
    }

    public List <OrderProductItem> getProductItemList() {
        return productItemList;
    }

    public void setProductItemList(List <OrderProductItem> productItemList) {
        this.productItemList = productItemList;
    }


    // --- Order Products List ----------
    public static class OrderProductItem implements Parcelable{
        // To Differentiate and Use as ID
        private String productVariant;

        private String productName;
        private String productSellingPrice;
        private String productMrp;
        private String productWeight;
        // Optional...
        private String productColor;
        private String productSize;

        // Images
        private String productImage;


        public String getProductVariant() {
            return productVariant;
        }

        public void setProductVariant(String productVariant) {
            this.productVariant = productVariant;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductSellingPrice() {
            return productSellingPrice;
        }

        public void setProductSellingPrice(String productSellingPrice) {
            this.productSellingPrice = productSellingPrice;
        }

        public String getProductMrp() {
            return productMrp;
        }

        public void setProductMrp(String productMrp) {
            this.productMrp = productMrp;
        }

        public String getProductWeight() {
            return productWeight;
        }

        public void setProductWeight(String productWeight) {
            this.productWeight = productWeight;
        }

        public String getProductColor() {
            return productColor;
        }

        public void setProductColor(String productColor) {
            this.productColor = productColor;
        }

        public String getProductSize() {
            return productSize;
        }

        public void setProductSize(String productSize) {
            this.productSize = productSize;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        // -----------------------------------------------------------------------------------------

        public OrderProductItem() {
        }

        protected OrderProductItem(Parcel in) {
            productVariant = in.readString();
            productName = in.readString();
            productSellingPrice = in.readString();
            productMrp = in.readString();
            productWeight = in.readString();
            productColor = in.readString();
            productSize = in.readString();
            productImage = in.readString();
        }

        public static final Creator <OrderProductItem> CREATOR = new Creator <OrderProductItem>() {
            @Override
            public OrderProductItem createFromParcel(Parcel in) {
                return new OrderProductItem( in );
            }

            @Override
            public OrderProductItem[] newArray(int size) {
                return new OrderProductItem[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( productVariant );
            dest.writeString( productName );
            dest.writeString( productSellingPrice );
            dest.writeString( productMrp );
            dest.writeString( productWeight );
            dest.writeString( productColor );
            dest.writeString( productSize );
            dest.writeString( productImage );
        }

        // -----------------------------------------------------------------------------------------

    }


}