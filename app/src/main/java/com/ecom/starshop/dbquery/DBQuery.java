package com.ecom.starshop.dbquery;

import android.util.Log;

import com.ecom.starshop.helper.DBListener;
import com.ecom.starshop.helper.Listener;
import com.ecom.starshop.model.ModelBanner;
import com.ecom.starshop.model.temp.ModelUpdateItem;

import java.util.List;

import static com.ecom.starshop.helper.Constant.APP_DATABASE;
import static com.ecom.starshop.helper.Constant.F_IS_DATABASE_SET;
import static com.ecom.starshop.helper.Constant.PERMISSION;

public class DBQuery {

    // Check Whether Database is Set Or Not...!
    public static void checkIsDatabaseSet( DBListener.OnCheckDatabaseListener listener ){
        DBRef.getDocument( PERMISSION, APP_DATABASE )
                .addSnapshotListener((value, error) -> {
                    if (value != null && value.exists()){
                        // Exist...
                        if (value.contains(F_IS_DATABASE_SET)){ // Contain...
                            if (value.getBoolean(F_IS_DATABASE_SET)){
                                // Database Set Already
                                listener.onCheckDatabaseSet( "Success!", true );
                            }else{
                                // Database not set... Return False
                                listener.onCheckDatabaseSet( "Database not set yet!", false );
                            }
                        }else {
                            // Database ref not found! Return False
                            listener.onCheckDatabaseSet( "Database reference not found !!", false );
                        }
                    }else{
                        // Not Exist... Return False
                        listener.onCheckDatabaseSet( "Database not Exist...!!", false );
                    }
                });
    }

    // Add Banner Query
    public static void queryToAddBanner(Listener.OnUploadBannerListener listener, ModelBanner modelBanner ){
        // TODO :
        listener.onAddBanner( false , modelBanner );
    }

    /** ---------------- START : Section Or Layout Query --------------------------------------------------------- */

    // Query To Change Title Of A Section .........
    public static void queryToChangeSectionTitle(Listener.OnUpdateTitleListener listener, String sectionParentID, String sectionID, String titleText ){
        listener.onUpdateResponse( true, titleText );

    }

    // Query To Delete Section...
    public static void queryToDeleteSection( Listener.SectionMenuActionListener listener, String sectionParentID, String sectionID){
        listener.onDelete( true );
    }

    // Query To Change Visibility of a Section...
    public static void queryToChangeVisibilitySec( Listener.SectionMenuActionListener listener, String sectionParentID, String sectionID, boolean visibility ){
        listener.onUpdateVisibility( true );
    }

    // Query To Change Index of Section...
    public static void queryToChangeIndex( List<ModelUpdateItem> indexChangeList, String sectionParentID ){
        // TODO : Write Code to Run Task in Background!
        for (ModelUpdateItem item : indexChangeList){
            Log.e("INDEX_CHANGE", "LIST INDEX: " + item.getPosition() + " And ID: " + item.getCurrentID() );
        }

    }

    /** ---------------- END : Section Or Layout Query --------------------------------------------------------- */


}
