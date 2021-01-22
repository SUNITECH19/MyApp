package com.sunitech.myapp.helper;

public interface Constant {

    String FRAGMENT_ID = "FRAGMENT_ID";

    int DIRECTION_LEFT = 11;
    int DIRECTION_RIGHT = 12;


    int AUTH_REQUEST_SIGN_IN = 1;
    int AUTH_REQUEST_SIGN_UP = 2;
    int AUTH_REQUEST_FORGET_PASS = 3;


    // Fragment
    int FRAGMENT_MAIN = 1;
    int FRAGMENT_HOME = 2;
    int FRAGMENT_FAVORITE = 3;
    int FRAGMENT_PROFILE = 4;
    int FRAGMENT_CHANGE_PASSWORD = 5;
    int FRAGMENT_ABOUT_MYSELF = 6;

    int FRAGMENT_UPDATE_ACCOUNT = 10;
    int FRAGMENT_UPDATE_ADDRESS = 11;

    int FRAGMENT_SHOW_MY_EDUCATION = 21;
    int FRAGMENT_SHOW_MY_AWARDS = 22;
    int FRAGMENT_SHOW_MY_DOCUMENTS = 23;

    int FRAGMENT_SHOW_URL_ABOUT_US = 24;
    int FRAGMENT_SHOW_URL_CONTACT_US = 25;
    int FRAGMENT_SHOW_URL_HELP = 26;
    int FRAGMENT_SHOW_URL_PRIVACY_POLICY = 27;
    int FRAGMENT_SHOW_URL_LEGAL = 28;

    int FRAGMENT_SHOW_REVIEWS = 29;

    //------------------------ DB Constant : Do not Change The Value ------------------------------
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



}
