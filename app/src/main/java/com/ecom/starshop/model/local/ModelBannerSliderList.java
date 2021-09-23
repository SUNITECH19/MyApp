package com.ecom.starshop.model.local;

import com.ecom.starshop.model.ModelBanner;

import java.util.ArrayList;
import java.util.List;

public class ModelBannerSliderList {
    public String listID;
    public List<ModelBanner> modelBannerList = new ArrayList<>();

    public ModelBannerSliderList() {
    }

    public ModelBannerSliderList(String listID, List<ModelBanner> modelBannerList) {
        this.listID = listID;
        this.modelBannerList = modelBannerList;
    }

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public List<ModelBanner> getModelBannerList() {
        return modelBannerList;
    }

    public void setModelBannerList(List<ModelBanner> modelBannerList) {
        this.modelBannerList = modelBannerList;
    }
}
