package com.sunitech.myapp.dbquery;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.sunitech.myapp.helper.DBListener;

import static com.sunitech.myapp.helper.Constant.APP_DATABASE;
import static com.sunitech.myapp.helper.Constant.F_IS_DATABASE_SET;
import static com.sunitech.myapp.helper.Constant.PERMISSION;

public class DBQuery {

    // Check Whether Database is Set Or Not...!
    public static void checkIsDatabaseSet( DBListener.OnCheckDatabaseListener listener ){
        DBRef.getDocument( PERMISSION, APP_DATABASE )
                .addSnapshotListener((value, error) -> {
                    if (value.exists()){
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



}
