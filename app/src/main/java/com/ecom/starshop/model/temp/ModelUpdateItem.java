package com.ecom.starshop.model.temp;

import androidx.annotation.Nullable;

public class ModelUpdateItem {

    private int position;
    private int requestCode;

    @Nullable
    private String parentID;

    @Nullable
    private String currentID;

    @Nullable
    private String name;

    //--------------------------------------------------------------------------

    public ModelUpdateItem(int position, @Nullable String parentID, @Nullable String currentID, @Nullable String name) {
        this.position = position;
        this.parentID = parentID;
        this.currentID = currentID;
        this.name = name;
    }

    // Used For Index and ID...

    public ModelUpdateItem(int position, @Nullable String currentID) {
        this.position = position;
        this.currentID = currentID;
    }


    //--------------------------------------------------------------------------

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    @Nullable
    public String getParentID() {
        return parentID;
    }

    public void setParentID(@Nullable String parentID) {
        this.parentID = parentID;
    }

    @Nullable
    public String getCurrentID() {
        return currentID;
    }

    public void setCurrentID(@Nullable String currentID) {
        this.currentID = currentID;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
