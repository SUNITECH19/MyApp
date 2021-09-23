package com.ecom.starshop.model.local;

import com.ecom.starshop.model.ModelProduct;

import java.util.ArrayList;
import java.util.List;

public class ModelProductList {

    private String listID;
    private List<ModelProduct> productList = new ArrayList<>();

    public ModelProductList() {
    }

    public ModelProductList(String listID, List<ModelProduct> productList) {
        this.listID = listID;
        this.productList = productList;
    }

    //----------------------------------------------------------------------------------------------

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public List<ModelProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<ModelProduct> productList) {
        this.productList = productList;
    }
}
