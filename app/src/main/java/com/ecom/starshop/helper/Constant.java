package com.ecom.starshop.helper;


/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */

public interface Constant {


    int DIRECTION_LEFT = 11;
    int DIRECTION_RIGHT = 12;


    int GALLERY_CODE = 1;
    int STORAGE_PERMISSION = 2;
    int REQUEST_IMAGE_CAPTURE = 3;
    int REQUEST_CODE_FOR_SINGLE_IMAGE = 11;
    int REQUEST_CODE_FOR_MULTI_IMAGE = 12;

    int AUTH_REQUEST_SIGN_IN = 1;
    int AUTH_REQUEST_SIGN_UP = 2;
    int AUTH_REQUEST_FORGET_PASS = 3;

    int DEGREE_0 = 0;
    int DEGREE_90 = 90;
    int DEGREE_180 = 180;
    int DEGREE_270 = 270;
    int DEGREE_360 = 360;


    // Fragment
    int FRAGMENT_INTRODUCE = 99;
    int FRAGMENT_MAIN = 1;
    int FRAGMENT_HOME = 2;
    int FRAGMENT_CHANGE_PASSWORD = 5;

    int FRAGMENT_SHOW_URL_ABOUT_US = 24;
    int FRAGMENT_SHOW_URL_CONTACT_US = 25;
    int FRAGMENT_SHOW_URL_HELP = 26;
    int FRAGMENT_SHOW_URL_PRIVACY_POLICY = 27;
    int FRAGMENT_SHOW_URL_LEGAL = 28;

    int FRAGMENT_SHOW_REVIEWS = 29;

    //----------------------- Local Uses  -----------------------------------------------------------
    String FRAGMENT_ID = "FRAGMENT_ID";
    String FRAGMENT_DIALOG = "FRAGMENT_DIALOG";

    // Local Data transformation...
    String DATA_KEY_BOOLEAN = "KEY_BOOLEAN";


    int REQUEST_CODE_UPDATE_TITLE = 1;
    int REQUEST_CODE_UPDATE_SUB_TITLE = 2;
    int REQUEST_CODE_DELETE = 3;
    int REQUEST_CODE_UPDATE_VISIBILITY = 4;
    int REQUEST_CODE_ADD_NEW_LAYOUT = 5;
    int REQUEST_CODE_ADD_NEW = 6;
    //----------------------- Local Uses -----------------------------------------------------------

    /** !! DO NOT CHANGE VALUES !! -------------------------------------------------------------- */
    int LAYOUT_TYPE_CATEGORY = 1;
    int LAYOUT_TYPE_SUB_CATEGORY = 2;
    int LAYOUT_TYPE_PRODUCTS_HORIZONTAL = 3;
    int LAYOUT_TYPE_PRODUCTS_GRID_4 = 4;
    int LAYOUT_TYPE_PRODUCTS_GRID_3 = 5;
    int LAYOUT_TYPE_BANNER_AD = 6;
    int LAYOUT_TYPE_IMAGE_BANNER_SLIDER = 7;
    int LAYOUT_TYPE_VIDEO = 8;

    // TYPE CODE --- Warning!! DO NOT CHANGE..!
    // <!--    1.Discount, 2.Free Delivery, 3.Delivery Discount, 4.Voucher Code, 5.Gift -->
    int OFFER_CODE_DISCOUNT = 1;
    int OFFER_CODE_FREE_DELIVERY = 2;
    int OFFER_CODE_DELIVERY_DISCOUNT = 3;
    int OFFER_CODE_VOUCHER_CODE = 4;
    int OFFER_CODE_FREE_GIFT = 5;

    /** !! DO NOT CHANGE VALUES !! -------------------------------------------------------------- */

    /**------------------------ DB Constant : Do not Change The Value ------------------------------ */
    // Collections...
    String PERMISSION = "PERMISSION";
    // DB Root Collection
    String HOME = "HOME";
    String PRODUCTS = "PRODUCTS";
    String BANNERS = "BANNERS";
    String SHOP = "SHOP";
    String ORDERS = "ORDERS";
    String O_TRACKING = "O_TRACKING";
    String USERS = "USERS";
    String ADMINS = "ADMINS";

    // DB Document Name
    String APP_DATABASE = "AppDatabase";
    String APP_VERSION = "AppVersion";
    String USERS_PHONE = "UsersPhone";

    // Fields...
    String F_IS_DATABASE_SET = "is_database_set";

    // KEY : Address ------------
    String KEY_ADD_H_NO = "houseNo";
    String KEY_ADD_COLONY = "streetColony";
    String KEY_ADD_CITY = "city";
    String KEY_ADD_STATE = "state";
    String KEY_ADD_LANDMARK = "landmark";
    String KEY_ADD_PIN = "pin";
    String KEY_ADD_GEO_POINT = "geoPoint";


    String KEY_INDEX = "index";

    // Key : LogIn Info...
    String KEY_PHONE = "phone";
    String KEY_MOBILE = "phone";
    String KEY_EMAIL = "email";
    String KEY_NAME = "name";
    String KEY_PROFILE = "profileImage";
    String KEY_AUTH = "authID";
    String KEY_ADDRESS = "address";
    String KEY_PIN_CODE = "pinCode";
    String KEY_PASSWORD = "password";


    // KEY : Order
    String KEY_ORDER_ID = "orderID";
    String KEY_ORDER_BILL_AMOUNT = "orderBillAmount";
    String KEY_ORDER_AMOUNT = "orderAmount";
    String KEY_ORDER_SHIPPING_CHARGE = "orderShippingCharge";
    String KEY_ORDER_TIMESTAMP = "orderTimestamp";
    String KEY_ORDER_DELIVERY_STATUS = "deliveryStatus";
    String KEY_ORDER_SHIPPING_ID = "shippingID";
    String KEY_ORDER_SHIPPING_ADDRESS = "shippingAddress";
    String KEY_ORDER_SHIPPING_UPDATES = "shippingUpdates";
    String KEY_ORDER_PRODUCT_ITEM_LIST = "productItemList";

    // Key : Home ------------------------
    String KEY_LAYOUT_ID = "layoutID";
    String KEY_LAYOUT_TYPE = "layoutType";
    String KEY_LAYOUT_TITLE = "layoutTitle";
    String KEY_LAYOUT_SUB_TITLE = "layoutSubTitle";
    String KEY_LAYOUT_ICON = "layoutIcon";
    String KEY_LAYOUT_VISIBILITY = "layoutVisibility";

    // Key - Banner -----------------
    String KEY_BANNER_ID = "bannerID";
    String KEY_BANNER_TYPE = "bannerType";
    String KEY_BANNER_IMAGE = "bannerImage";
    String KEY_BANNER_CLICK = "bannerClick";
    String KEY_BANNER_EXTRA_TEXT = "bannerExtraText";

    // Key - Product -----------------------------------------------------------
    String KEY_PRODUCT_ID = "productID";
    String KEY_PRODUCT_ITEMS = "productItems";
    String KEY_PRODUCT_ADD_TIME = "addTime";
    String KEY_PRODUCT_LAST_UPDATE_TIME = "lastUpdateTime";

    // Product Item...
    String KEY_PRODUCT_VARIANT = "productVariant";
    String KEY_PRODUCT_NAME = "productName";
    String KEY_PRODUCT_SELL_PRICE = "productSellingPrice";
    String KEY_PRODUCT_MRP_PRICE = "productMrp";
    String KEY_PRODUCT_WEIGHT = "productWeight";

    String KEY_PRODUCT_COLORS = "productColors";
    String KEY_PRODUCT_SIZE = "productSize";
    String KEY_PRODUCT_DESCRIPTION = "productDescription";

    String KEY_PRODUCT_IMAGES = "productImages";
    String KEY_PRODUCT_OFFERS = "productOffers";
    String KEY_PRODUCT_SPECIFICATIONS = "productSpecifications";

    // Key _ Product OFFERS
    String KEY_PRODUCT_OFFERS_TITLE = "offerTitle";
    String KEY_PRODUCT_OFFERS_DESCRIPTION = "offerDescription";
    String KEY_PRODUCT_OFFERS_ABOUT = "offerAbout";
    String KEY_PRODUCT_OFFERS_TYPE = "offerType";
    String KEY_PRODUCT_OFFERS_BENEFIT = "offerBenefit";

    // Key _ Product Specifications....
    String KEY_PRODUCT_SPECIFY_TITLE = "title";
    String KEY_PRODUCT_SPECIFY_DETAILS = "specifications";
    // Specification Sub title
    String KEY_PRODUCT_SPECIFY_SUB_TITLE = "subTitle";
    String KEY_PRODUCT_SPECIFY_SUB_DETAIL = "information";
    // Key - Product -----------------------------------------------------------

    // Key - Shipping ----------------------------
    String KEY_SHIPPING_BY_NAME = "shippingByName";
    String KEY_SHIPPING_BY_MOBILE = "shippingByMobile";
    String KEY_SHIPPING_ID = "shippingID";
    String KEY_SHIPPING_UPDATES = "shippingUpdates";
    // Shipping Updates...
    String KEY_SHIPPING_STATUS = "status";
    String KEY_SHIPPING_TIME = "timestamp";
    String KEY_SHIPPING_UPDATE_INFO = "updateInfo";



}
