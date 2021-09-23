package com.ecom.starshop.dbquery;

import android.annotation.SuppressLint;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBRef {

    private static FirebaseUser currentUser;

    private static FirebaseAuth firebaseAuth;
    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore firebaseFirestore;

    // Firebase Auth
    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

    // Get Current User...
    public static FirebaseUser getCurrentUser(){
        if (currentUser == null){
            currentUser = getFirebaseAuth().getCurrentUser();
        }
        return currentUser;
    }

    // Firestore
    public static FirebaseFirestore getFirebaseFirestore(){
        if (firebaseFirestore == null)
            firebaseFirestore = FirebaseFirestore.getInstance();
        return firebaseFirestore;
    }

    // Collection Ref...
    public static CollectionReference getCollection(String collection){
        return getFirebaseFirestore().collection( collection );
    }

    // Document Ref...
    public static DocumentReference getDocument(String collection, String document){
        return getFirebaseFirestore().collection( collection ).document( document );
    }

    // Get Document SnapShot
    public static DocumentSnapshot documentSnapshot(String collection, String document){
        return getFirebaseFirestore().collection(collection)
                .document( document )
                .get().getResult();
    }


}
