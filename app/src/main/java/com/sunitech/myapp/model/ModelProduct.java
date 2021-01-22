package com.sunitech.myapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.List;

public class ModelProduct implements Parcelable{

    // Used to sort data...
    private String index;

    private String productID;
    private List<ProductItem> productItems;

    private Timestamp addTime;
    private Timestamp lastUpdateTime;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public List <ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List <ProductItem> productItems) {
        this.productItems = productItems;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    // ---------------------------------------------------------------------------------------------

    public ModelProduct() {
    }

    protected ModelProduct(Parcel in) {
        index = in.readString();
        productID = in.readString();
        productItems = in.createTypedArrayList( ProductItem.CREATOR );
        addTime = in.readParcelable( Timestamp.class.getClassLoader() );
        lastUpdateTime = in.readParcelable( Timestamp.class.getClassLoader() );
    }

    public static final Creator <ModelProduct> CREATOR = new Creator <ModelProduct>() {
        @Override
        public ModelProduct createFromParcel(Parcel in) {
            return new ModelProduct( in );
        }

        @Override
        public ModelProduct[] newArray(int size) {
            return new ModelProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( index );
        dest.writeString( productID );
        dest.writeTypedList( productItems );
        dest.writeParcelable( addTime, flags );
        dest.writeParcelable( lastUpdateTime, flags );
    }

    // ---------------------------------------------------------------------------------------------


    // Helping Classes...
    public static class ProductItem implements Parcelable {
        // To Differentiate and Use as ID
        private String productVariant;

        private String productName;
        private String productSellingPrice;
        private String productMrp;
        private String productWeight;
        // Optional...
        private List <String> productColors; // May have multiple colors for single product...
        private String productSize;
        private String productDescription;

        // Images
        private List <String> productImages;

        // Offer List
        private List<ProductOffer> productOffers;

        // Specifications List
        private List<ProductSpecifications> productSpecifications;


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

        public List <String> getProductColor() {
            return productColors;
        }

        public void setProductColor(List <String> productColor) {
            this.productColors = productColor;
        }

        public String getProductSize() {
            return productSize;
        }

        public void setProductSize(String productSize) {
            this.productSize = productSize;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public List <String> getProductImages() {
            return productImages;
        }

        public void setProductImages(List <String> productImages) {
            this.productImages = productImages;
        }

        public List <ProductOffer> getProductOffers() {
            return productOffers;
        }

        public void setProductOffers(List <ProductOffer> productOffers) {
            this.productOffers = productOffers;
        }

        public List <ProductSpecifications> getProductSpecifications() {
            return productSpecifications;
        }

        public void setProductSpecifications(List <ProductSpecifications> productSpecifications) {
            this.productSpecifications = productSpecifications;
        }

        // -----------------------------------------------------------------------------------------

        public ProductItem() {
        }

        protected ProductItem(Parcel in) {
            productVariant = in.readString();
            productName = in.readString();
            productSellingPrice = in.readString();
            productMrp = in.readString();
            productWeight = in.readString();
            productColors = in.createStringArrayList();
            productSize = in.readString();
            productDescription = in.readString();
            productImages = in.createStringArrayList();
        }

        public static final Creator <ProductItem> CREATOR = new Creator <ProductItem>() {
            @Override
            public ProductItem createFromParcel(Parcel in) {
                return new ProductItem( in );
            }

            @Override
            public ProductItem[] newArray(int size) {
                return new ProductItem[size];
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
            dest.writeStringList( productColors );
            dest.writeString( productSize );
            dest.writeString( productDescription );
            dest.writeStringList( productImages );
        }

        // -----------------------------------------------------------------------------------------

    }
    public static class ProductOffer implements Parcelable{
        private String offerTitle;
        private String offerDescription;
        private String offerAbout;
        private int offerType;
        private String offerBenefit;

        public String getOfferTitle() {
            return offerTitle;
        }

        public void setOfferTitle(String offerTitle) {
            this.offerTitle = offerTitle;
        }

        public String getOfferDescription() {
            return offerDescription;
        }

        public void setOfferDescription(String offerDescription) {
            this.offerDescription = offerDescription;
        }

        public String getOfferAbout() {
            return offerAbout;
        }

        public void setOfferAbout(String offerAbout) {
            this.offerAbout = offerAbout;
        }

        public int getOfferType() {
            return offerType;
        }

        public void setOfferType(int offerType) {
            this.offerType = offerType;
        }

        public String getOfferBenefit() {
            return offerBenefit;
        }

        public void setOfferBenefit(String offerBenefit) {
            this.offerBenefit = offerBenefit;
        }

        // -----------------------------------------------------------------------------------------

        public ProductOffer() {
        }

        protected ProductOffer(Parcel in) {
            offerTitle = in.readString();
            offerDescription = in.readString();
            offerAbout = in.readString();
            offerType = in.readInt();
            offerBenefit = in.readString();
        }

        public static final Creator <ProductOffer> CREATOR = new Creator <ProductOffer>() {
            @Override
            public ProductOffer createFromParcel(Parcel in) {
                return new ProductOffer( in );
            }

            @Override
            public ProductOffer[] newArray(int size) {
                return new ProductOffer[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( offerTitle );
            dest.writeString( offerDescription );
            dest.writeString( offerAbout );
            dest.writeInt( offerType );
            dest.writeString( offerBenefit );
        }
        // -----------------------------------------------------------------------------------------

    }
    public static class ProductSpecifications implements Parcelable{
        private String title;
        private List<Specifications> specifications;

        public ProductSpecifications() {
        }

        protected ProductSpecifications(Parcel in) {
            title = in.readString();
        }

        public static final Creator <ProductSpecifications> CREATOR = new Creator <ProductSpecifications>() {
            @Override
            public ProductSpecifications createFromParcel(Parcel in) {
                return new ProductSpecifications( in );
            }

            @Override
            public ProductSpecifications[] newArray(int size) {
                return new ProductSpecifications[size];
            }
        };

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List <Specifications> getSpecifications() {
            return specifications;
        }

        public void setSpecifications(List<Specifications> specifications) {
            this.specifications = specifications;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString( title );
        }

        // Specifications Class...
        public static class Specifications implements Parcelable{
            private String subTitle;
            private String information;

            public Specifications() {
            }

            protected Specifications(Parcel in) {
                subTitle = in.readString();
                information = in.readString();
            }

            public static final Creator <Specifications> CREATOR = new Creator<Specifications>() {
                @Override
                public Specifications createFromParcel(Parcel in) {
                    return new Specifications( in );
                }

                @Override
                public Specifications[] newArray(int size) {
                    return new Specifications[size];
                }
            };

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getInformation() {
                return information;
            }

            public void setInformation(String information) {
                this.information = information;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString( subTitle );
                dest.writeString( information );
            }
        }
    }


}