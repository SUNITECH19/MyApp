package com.ecom.starshop.helper;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.ecom.starshop.R;

import java.util.ArrayList;
import java.util.List;

public class DropDownItemSelectListener {
    private String selectedItem = null;
    private String selectedItemID = null;
    private Context context;
    private AutoCompleteTextView autoCompleteTextView;

    public DropDownItemSelectListener(Context context, AutoCompleteTextView autoCompleteTextView) {
        this.context = context;
        this.autoCompleteTextView = autoCompleteTextView;
    }
    public void addOptions(){
        List<String> cityList = new ArrayList<>();
        cityList.add( "Jabalpur ");
        cityList.add( "Sagar");
        cityList.add( "Hosangabad");
        cityList.add( "Ujjain");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( context, R.layout.layout_select_string_item_option_drop_down, cityList );

        (autoCompleteTextView).setAdapter( arrayAdapter );
        autoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // ignore
            }
        });
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSelectedItemID() {
        return selectedItemID;
    }

    public void setSelectedItemID(String selectedItemID) {
        this.selectedItemID = selectedItemID;
    }
}
