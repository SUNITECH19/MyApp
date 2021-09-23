package com.ecom.starshop.helper;

import com.ecom.starshop.R;
import com.ecom.starshop.model.ModelCategory;
import com.ecom.starshop.model.ModelHome;
import com.ecom.starshop.model.ModelProduct;
import com.ecom.starshop.model.ModelRootDataItem;
import com.ecom.starshop.model.local.ModelBannerSliderList;
import com.ecom.starshop.model.local.ModelProductList;

import java.util.ArrayList;
import java.util.List;

public class DataList {

    public static List<ModelHome> listManageHome = new ArrayList<>();

    public static List<ModelRootDataItem> rootDataItemList = new ArrayList<>();

    //----------------------------------------------------------------------------------------------

    // -- Root List of All Products ---
    public static List<ModelProductList> rootListProducts = new ArrayList<>();





    /**
     * Below You can check testing methods....---------------------------------------------------------------------------------------------------
     */

    public static final String TESTING_IMAGE_LINK_1 = "https://cdn.stopgrab.com/product/e-box/2020/sep/sDLWJA8zoQEKWw2QsFq9KMjCWC2mTcn4KNOEa8yR.jpeg";
    public static final String TESTING_IMAGE_LINK_2 = "https://www.cut2sizemetals.com/images/products/mobile/asq-6061-T6511.jpg";

    // Temporary... For Testing Purpose
    public static List<ModelProduct> getTempProductList( ){
        List<ModelProduct> productList = new ArrayList<>();

        // Sample Product Item --------------------------------------------------------------------
        ModelProduct product = new ModelProduct();

        product.setIndex( "0" );
        product.setProductID("28912012");

        List <ModelProduct.ProductItem> productItems = new ArrayList<>();

        /*
        public ProductItem(String productVariant, String productName, String productSellingPrice, String productMrp, String productWeight, List<String> productColors, String productSize, String productDescription, List<String> productImages, List<ProductOffer> productOffers, List<ProductSpecifications> productSpecifications) {
            this.productVariant = productVariant;
            this.productName = productName;
            this.productSellingPrice = productSellingPrice;
            this.productMrp = productMrp;
            this.productWeight = productWeight;
            this.productColors = productColors;
            this.productSize = productSize;
            this.productDescription = productDescription;
            this.productImages = productImages;
            this.productOffers = productOffers;
            this.productSpecifications = productSpecifications;
        }
         */

        ModelProduct.ProductItem productItem = new ModelProduct.ProductItem();
        productItem.setProductVariant( "0");
        productItem.setProductName( "Sample Product Name");
        productItem.setProductSellingPrice("212.23");
        productItem.setProductMrp("232.83");
        productItem.setProductDescription("This product is Awesome and very popular in the current tradition. Today is good offer to buy this product, Do tap on buy now button to get it now.");

        List<String> imageList = new ArrayList<>();
        imageList.add( TESTING_IMAGE_LINK_2 );
        imageList.add( TESTING_IMAGE_LINK_1 );

        productItem.setProductImages( imageList );

        // Add 2 Time same item..: You can add multiple variants in single product ID.
        productItems.add( productItem );
        productItems.add( productItem );
        // Add Products Items List...
        product.setProductItems( productItems );

        // Sample Product Item --------------------------------------------------------------------

        productList.add( product );
        productList.add( product );
        productList.add( product );
        productList.add( product );

        return productList;
    }

    // Temporary... For Testing Purpose
    public static List<ModelCategory> getTempCategory( ){
        List<ModelCategory> categoryList = new ArrayList<>();

        ModelCategory category1 = new ModelCategory();
        category1.setCatTitle( "Category One ");
        category1.setCatImageIcon( TESTING_IMAGE_LINK_1 );

        ModelCategory category2 = new ModelCategory();
        category2.setCatTitle( "Category Two ");
        category2.setCatImageIcon( TESTING_IMAGE_LINK_2 );

        categoryList.add( category1 );
        categoryList.add( category2 );
        categoryList.add( category2 );
        categoryList.add( category1 );
//        categoryList.add( category2 );

        return categoryList;
    }




}
