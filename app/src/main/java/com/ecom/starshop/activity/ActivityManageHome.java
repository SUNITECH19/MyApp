package com.ecom.starshop.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.ecom.starshop.R;
import com.ecom.starshop.adaptor.AdaptorManageHome;
import com.ecom.starshop.databinding.ActivityManageHomeBinding;
import com.ecom.starshop.fragment.addproduct.DialogFragmentAddProduct;
import com.ecom.starshop.helper.DialogListener;
import com.ecom.starshop.helper.ItemMoveCallback;
import com.ecom.starshop.helper.Listener;
import com.ecom.starshop.model.ModelBanner;
import com.ecom.starshop.model.ModelHome;
import com.ecom.starshop.model.ModelProduct;
import com.ecom.starshop.model.local.ModelProductList;
import com.ecom.starshop.myclass.DialogFragmentAddBanner;
import com.ecom.starshop.myclass.DialogFragmentAddSection;
import com.ecom.starshop.myclass.MyOptionDialog;

import java.util.ArrayList;
import java.util.List;

import static com.ecom.starshop.helper.Constant.FRAGMENT_DIALOG;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_BANNER_AD;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_CATEGORY;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_IMAGE_BANNER_SLIDER;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_PRODUCTS_GRID_4;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_PRODUCTS_HORIZONTAL;
import static com.ecom.starshop.helper.DataList.getTempCategory;
import static com.ecom.starshop.helper.DataList.getTempProductList;
import static com.ecom.starshop.helper.DataList.listManageHome;
import static com.ecom.starshop.helper.DataList.rootListProducts;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class ActivityManageHome extends AppCompatActivity implements Listener.ManageHomeListener {
    private final int SHOW_DIALOG_TO_CHOOSE_FRAGMENT = 1;
    private final int SHOW_DIALOG_TO_ADD_BANNER = 2;
    private final int SHOW_DIALOG_TO_ADD_PRODUCT = 3;// TEMP ;; TODO : REMPOVE

    private ActivityManageHomeBinding manageHomeBinding;
    private AdaptorManageHome adaptorManageHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manageHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_manage_home);
//        manageHomeBinding.executePendingBindings();

        setToolbar();
        onButtonAction();
        setAdaptorManageHome();

    }

    private void setToolbar() {
        try {
            setSupportActionBar(manageHomeBinding.includeToolBar.appToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Home");
        } catch (NullPointerException ignored) {
        }
    }

    private void onButtonAction() {
        // Add Section....
        manageHomeBinding.addSectionButton.setOnClickListener(v -> {
            showFragmentDialog( SHOW_DIALOG_TO_CHOOSE_FRAGMENT );
        });

    }

    private void setAdaptorManageHome(){

        // TODO : Remove
        // Temporary List...
        // product List..
        rootListProducts.add( new ModelProductList( "ID_2", getTempProductList() ));

        listManageHome.add( new ModelHome( 0, LAYOUT_TYPE_BANNER_AD, "ID_1", "", null, "", false,
                null ) );

        listManageHome.add( new ModelHome( 1, LAYOUT_TYPE_PRODUCTS_GRID_4, "ID_2", "My Products", null, "", false,
                null ) );

        listManageHome.add( new ModelHome( 2, LAYOUT_TYPE_BANNER_AD, "ID_3", "", null, "", true,
                null ) );

        listManageHome.add( new ModelHome( 3, LAYOUT_TYPE_CATEGORY, "ID_4",
                "My Category", null, "", true,
                null, null, getTempCategory(), null
        ) );

        /*
         this.index = index;
        this.layoutType = layoutType;
        this.layoutID = layoutID;

        this.layoutTitle = layoutTitle;
        this.layoutSubTitle = layoutSubTitle;
        this.layoutIcon = layoutIcon;
        this.layoutVisibility = layoutVisibility;

        this.bannerLayout = bannerLayout;
        this.layoutItems = layoutItems;
        this.categoryList = categoryList;
        this.bannerList = bannerList;
         */

        adaptorManageHome = new AdaptorManageHome( this, this, getSupportFragmentManager(), 0, "", "" );

        ItemTouchHelper.Callback callback = new ItemMoveCallback(adaptorManageHome);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView( manageHomeBinding.recyclerViewHomeSections );

        manageHomeBinding.recyclerViewHomeSections.setAdapter( adaptorManageHome );
    }

    // Dialog For Show Add Section
    private void showFragmentDialog( int  DialogCode ){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }

        DialogFragment dialogFragment = null;

        switch (DialogCode ){
            case SHOW_DIALOG_TO_CHOOSE_FRAGMENT:
                dialogFragment = new DialogFragmentAddSection( this );
                break;
            case SHOW_DIALOG_TO_ADD_BANNER:
                dialogFragment = new DialogFragmentAddBanner( this );
                break;
            case SHOW_DIALOG_TO_ADD_PRODUCT:
                dialogFragment = DialogFragmentAddProduct.getInstance( null, null );
                break;
        }

//        DialogFragmentSelectImages selectBank = new DialogFragmentSelectImages( REQUEST_CODE_FOR_MULTI_IMAGE, manageHomeBinding.getRoot().getContext());
        if (dialogFragment != null ){
            dialogFragment.show( fragmentManager, FRAGMENT_DIALOG );
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == android.R.id.home ){
            onBackPressed( );
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onAddSectionQuery( int addSectionCode) {
        switch (addSectionCode){
            case LAYOUT_TYPE_BANNER_AD:
                showFragmentDialog( SHOW_DIALOG_TO_ADD_BANNER );
                break;
            case LAYOUT_TYPE_IMAGE_BANNER_SLIDER:
            case LAYOUT_TYPE_PRODUCTS_GRID_4:
            case LAYOUT_TYPE_PRODUCTS_HORIZONTAL:
            default:
                break;
        }
    }

    // On Success Update Banner on Server
    @Override
    public void onAddBanner(boolean isSuccess, ModelBanner modelBanner) {

    }

    @Override
    public void showDialog() {

        MyOptionDialog myOptionDialog = new MyOptionDialog( this, "Do you want to log out?", "If you log out>>>" );

        myOptionDialog.onCreateDialog( new DialogListener.OnClickListener() {
            @Override
            public void onPositiveAction(DialogListener dialog, int id) {
                // On Positive Button Action...
                Log.e("DIALOG", "Positive");
                dialog.dismiss();
            }

            @Override
            public void onNegativeAction(DialogListener dialog, int id) {
                // On Negative Button Action...
                Log.e("DIALOG", "Negative");
                dialog.dismiss();
            }
        });

        myOptionDialog.show();

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }




}