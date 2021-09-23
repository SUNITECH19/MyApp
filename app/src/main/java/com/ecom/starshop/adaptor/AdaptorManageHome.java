package com.ecom.starshop.adaptor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.ecom.starshop.ActivityWelcome;
import com.ecom.starshop.BR;
import com.ecom.starshop.R;
import com.ecom.starshop.dbquery.DBQuery;
import com.ecom.starshop.helper.ItemMoveCallback;
import com.ecom.starshop.helper.Listener;
import com.ecom.starshop.helper.StaticMethods;
import com.ecom.starshop.login.ActivityAuth;
import com.ecom.starshop.model.temp.ModelUpdateItem;
import com.ecom.starshop.myclass.DialogFragmentAddBanner;
import com.ecom.starshop.myclass.DialogFragmentAddSection;
import com.ecom.starshop.myclass.PopUpDialogEditTitle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ecom.starshop.helper.Constant.FRAGMENT_DIALOG;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_BANNER_AD;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_CATEGORY;
import static com.ecom.starshop.helper.Constant.LAYOUT_TYPE_PRODUCTS_GRID_4;
import static com.ecom.starshop.helper.DataList.getTempCategory;
import static com.ecom.starshop.helper.DataList.listManageHome;
import static com.ecom.starshop.helper.DataList.rootDataItemList;

public class AdaptorManageHome extends Adapter<RecyclerView.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract {

    private Listener.ManageHomeListener homeListener;
    private FragmentManager fragmentManager;

    // Temp Information..
    private int rootListIndex;
    private String rootParentID;
    private String rootParentTitle;
    private Context context;

    //  : Use this Constructor..
    public AdaptorManageHome(Context context, Listener.ManageHomeListener homeListener, FragmentManager fragmentManager, int rootListIndex, String rootParentID, String rootParentTitle) {
        this.context = context;
        this.homeListener = homeListener;
        this.fragmentManager = fragmentManager;
        this.rootListIndex = rootListIndex;
        this.rootParentID = rootParentID;
        this.rootParentTitle = rootParentTitle;
    }

    // Temp Use....
    private List<ModelUpdateItem> indexChangeList = new ArrayList<>();

    //-----------------------------------------------------

    @Override
    public int getItemViewType(int position) {
        return listManageHome.get(position).getLayoutType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_TYPE_CATEGORY:
                ViewDataBinding viewCategory = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_manage_home_category_section_item, parent, false);
                return new ViewHolderCategory(viewCategory);
            case LAYOUT_TYPE_PRODUCTS_GRID_4:
                ViewDataBinding viewProductGrid = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_manage_home_product_section, parent, false);
                return new ViewHolderProductGrid(viewProductGrid);
            case LAYOUT_TYPE_BANNER_AD:
                ViewDataBinding viewBanner = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_manage_home_bannner_section_item, parent, false);
                return new ViewHolderBannerAd(viewBanner);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (listManageHome.get(position).getLayoutType()) {
            case LAYOUT_TYPE_CATEGORY:
                ((ViewHolderCategory) holder).onBind( position );
                break;
            case LAYOUT_TYPE_PRODUCTS_GRID_4:
                ((ViewHolderProductGrid) holder).onBind( position );
                break;
            case LAYOUT_TYPE_BANNER_AD:
                ((ViewHolderBannerAd) holder).onBind( position );
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listManageHome.size();
    }

    // -------------------- Move - Drag And Drop ------------------

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap( listManageHome, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap( listManageHome, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(fromPosition);
        notifyItemChanged(toPosition);
    }

    @Override
    public void onRowSelected(RecyclerView.ViewHolder myViewHolder) {
        myViewHolder.itemView.setBackgroundColor( Color.GRAY );
    }

    @Override
    public void onRowClear(RecyclerView.ViewHolder myViewHolder, int oldPosition, int newPosition) {
        myViewHolder.itemView.setBackgroundColor( Color.WHITE );
        indexChangeList.clear();
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i <= newPosition; i++) {
                indexChangeList.add( new ModelUpdateItem( i, listManageHome.get(i).getLayoutID()));
            }
        } else {
            for (int i = oldPosition; i >= newPosition; i--) {
                indexChangeList.add( new ModelUpdateItem( i, listManageHome.get(i).getLayoutID()));
            }
        }

        /** Send Data Change Request to Server...! */
        DBQuery.queryToChangeIndex( indexChangeList, rootParentID );
    }

    // =========== ViewHolder : Category Section ================================================================
    public class ViewHolderCategory extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;
        private ImageView popUpOptions;
        private ImageView ivVisibilityBtn;

        private RecyclerView recyclerViewProductGrid;
        private AdaptorCategoryView adaptorCategoryView;

        public ViewHolderCategory(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            popUpOptions = (ImageView) viewDataBinding.getRoot().findViewById(R.id.imageViewMoreOptionsBtn);
            ivVisibilityBtn = (ImageView) viewDataBinding.getRoot().findViewById(R.id.imageViewVisibilityBtn);
            recyclerViewProductGrid = (RecyclerView) viewDataBinding.getRoot().findViewById(R.id.recyclerViewCategory);
        }

        private void onBind(int position ) {

            viewDataBinding.setVariable( com.ecom.starshop.BR.modelHome, listManageHome.get( position ));
            viewDataBinding.setVariable( BR.index, position );

            if (!listManageHome.get(position).isLayoutVisibility()) {
                ivVisibilityBtn.setImageResource(R.drawable.ic_outline_visibility_off_24);
            } else {
                ivVisibilityBtn.setImageResource(R.drawable.ic_outline_visibility_24);
            }

            // Set Adaptor... // listManageHome.get( position ).getCategoryList()
            adaptorCategoryView = new AdaptorCategoryView( getTempCategory(), 4 );
            recyclerViewProductGrid.setAdapter(adaptorCategoryView);
            adaptorCategoryView.notifyDataSetChanged();
            viewDataBinding.executePendingBindings();

            // Button Click Action ---------------------------
            popUpOptions.setOnClickListener(v -> {
                setPopUpOptions(viewDataBinding.getRoot().getContext(), popUpOptions, true, position );
            });

            ivVisibilityBtn.setOnClickListener(v -> {
                new SectionMenuOptionAction(position).queryToUpdateVisibility(ivVisibilityBtn);
            });
        }

    }

    // =========== ViewHolder : Product Grid Section ============================================================
    public class ViewHolderProductGrid extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;
        private ImageView popUpOptions;
        private ImageView ivVisibilityBtn;

        private RecyclerView recyclerViewProducts;
        private AdaptorProductView adaptorProductView;

        public ViewHolderProductGrid(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            popUpOptions = (ImageView) viewDataBinding.getRoot().findViewById(R.id.imageViewMoreOptionsBtn);
            ivVisibilityBtn = (ImageView) viewDataBinding.getRoot().findViewById(R.id.imageViewVisibilityBtn);
            recyclerViewProducts = (RecyclerView) viewDataBinding.getRoot().findViewById(R.id.recyclerViewProducts);
        }

        private void onBind(int position )  {

            viewDataBinding.setVariable( com.ecom.starshop.BR.modelHome, listManageHome.get( position ));
            viewDataBinding.setVariable( BR.index, position );
            viewDataBinding.executePendingBindings();
            if (!listManageHome.get(position).isLayoutVisibility()) {
                ivVisibilityBtn.setImageResource(R.drawable.ic_outline_visibility_off_24);
            } else {
                ivVisibilityBtn.setImageResource(R.drawable.ic_outline_visibility_24);
            }

            // Set Adaptor...
            adaptorProductView = new AdaptorProductView( listManageHome.get(position).getLayoutID() );
            recyclerViewProducts.setAdapter(adaptorProductView);
            adaptorProductView.notifyDataSetChanged();

            popUpOptions.setOnClickListener(v -> {
                setPopUpOptions(viewDataBinding.getRoot().getContext(), popUpOptions, true,  position );
            });
            ivVisibilityBtn.setOnClickListener(v -> {
                new SectionMenuOptionAction(position).queryToUpdateVisibility(ivVisibilityBtn);
            });
        }


    }

    // =========== ViewHolder : Banner Ad Section ===============================================================
    public class ViewHolderBannerAd extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;
        private ImageView popUpOptions;
        private ImageView ivVisibilityBtn;

        public ViewHolderBannerAd(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            popUpOptions = (ImageView) viewDataBinding.getRoot().findViewById(R.id.imageViewMoreOptionsBtn);
            ivVisibilityBtn = (ImageView) viewDataBinding.getRoot().findViewById(R.id.imageViewVisibilityBtn);
        }

        private void onBind(int position ) {

            viewDataBinding.setVariable( com.ecom.starshop.BR.modelHome, listManageHome.get( position ));
            viewDataBinding.setVariable( BR.index, position );
            viewDataBinding.executePendingBindings();
            if (!listManageHome.get(position).isLayoutVisibility()) {
                ivVisibilityBtn.setImageResource(R.drawable.ic_outline_visibility_off_24);
            } else {
                ivVisibilityBtn.setImageResource(R.drawable.ic_outline_visibility_24);
            }

            popUpOptions.setOnClickListener(v -> {
                setPopUpOptions(viewDataBinding.getRoot().getContext(), popUpOptions, false,  position );
            });
            ivVisibilityBtn.setOnClickListener(v -> {
                new SectionMenuOptionAction(position).queryToUpdateVisibility(ivVisibilityBtn);
            });

        }

    }


    // =-------------- PopUp Menu Options ---------------------------------------------------------------
    private void setPopUpOptions(Context context, View view, boolean showEdit, int position ) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item_popup, popupMenu.getMenu());
        popupMenu.getMenu().findItem(R.id.menu_edit).setVisible(showEdit);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_edit:
                    new SectionMenuOptionAction( position ).showEditTitleDialog();
                    break;
                case R.id.menu_delete:
//                    Log.e("CLICKED", "Delete Clicked");
                    new SectionMenuOptionAction( position ).onDeleteSectionQuery();
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    // ----- Helper Class : To Update The Section Info..........
    private class SectionMenuOptionAction implements Listener.SectionMenuActionListener {

        private int itemPosition;

        public SectionMenuOptionAction(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        // Show Dialog to Edit Title...
        public void showEditTitleDialog( ){
            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment prev = fragmentManager.findFragmentByTag(FRAGMENT_DIALOG);
            if (prev != null) {
                ft.remove(prev);
            }

            // Send Related Information...
            ModelUpdateItem updateItem = new ModelUpdateItem(
                    itemPosition,
                    rootParentID, // Sending Root Or Parent ID.
                    listManageHome.get(itemPosition).getLayoutID(), listManageHome.get(itemPosition).getLayoutTitle() );

            PopUpDialogEditTitle dialogFragment = new PopUpDialogEditTitle( this, updateItem );
            dialogFragment.show( fragmentManager, FRAGMENT_DIALOG );
        }

        public void onDeleteSectionQuery( ){
            // Create Dialog And Show...
            StaticMethods.getAlertDialog( context, "Do You want to Delete this section?", "You will delete this section forever!" )
                    .setPositiveButton("YES", (dialog, which) -> {
                        dialog.dismiss();
                        // Query To Delete...
                        DBQuery.queryToDeleteSection( this, rootParentID, listManageHome.get( itemPosition).getLayoutID() );
                    })
                    .setNegativeButton("NO", (dialog, which) -> dialog.dismiss()).show();
        }

        public void queryToUpdateVisibility( ImageView imageView ) {
            String title, msg;

            if (listManageHome.get(itemPosition).isLayoutVisibility()){
                title = "Hide / InVisible ?";
                msg = "Do You want to hide this section from users?";
            }else {
                title = "Show / Visible ?";
                msg = "Do You want to show this section to users?";
            }

            // Create Dialog And Show...
            StaticMethods.getAlertDialog( context, title, msg )
            .setPositiveButton("YES", (dialog, which) -> {
                dialog.dismiss();
                // Query To Hide...
                DBQuery.queryToChangeVisibilitySec( this, rootParentID, listManageHome.get( itemPosition).getLayoutID(), !listManageHome.get(itemPosition).isLayoutVisibility() );
            })
            .setNegativeButton("NO", (dialog, which) -> dialog.dismiss()).show();
        }

        @Override
        public void onUpdateTitle(boolean isSuccess,  @Nullable String newTitle ) {
            listManageHome.get( itemPosition ).setLayoutTitle( newTitle );
            AdaptorManageHome.this.notifyItemChanged( itemPosition );
        }

        @Override
        public void onDelete(boolean isSuccess) {
            homeListener.dismissDialog();
            if (isSuccess){
                listManageHome.remove( itemPosition );
                AdaptorManageHome.this.notifyItemRemoved( itemPosition );
                // To Update all elements...
                new CountDownTimer(1000, 1000) {
                    public void onTick(long millisUntilFinished) { }
                    public void onFinish() {
                        AdaptorManageHome.this.notifyDataSetChanged();
                    }
                }.start();
            }else
                homeListener.showToast( context.getString(R.string.something_went_wrong_with_sorry));
        }

        @Override
        public void onUpdateVisibility(boolean isSuccess) {
            homeListener.dismissDialog();
            if (isSuccess){
                if (listManageHome.get(itemPosition) != null){
                    listManageHome.get(itemPosition).setLayoutVisibility(!listManageHome.get(itemPosition).isLayoutVisibility());
                }
                AdaptorManageHome.this.notifyItemChanged( itemPosition );
            }else
                homeListener.showToast( context.getString(R.string.something_went_wrong_with_sorry));
        }

    }



}
