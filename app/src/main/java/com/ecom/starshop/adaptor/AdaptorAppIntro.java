package com.ecom.starshop.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import com.ecom.starshop.BR;
import com.ecom.starshop.R;
import com.ecom.starshop.model.ModelAppIntro;
import java.util.List;

import static com.ecom.starshop.BR.image;

public class AdaptorAppIntro extends PagerAdapter {

    private List<ModelAppIntro> itemList;

    public AdaptorAppIntro(List<ModelAppIntro> itemList) {
        this.itemList = itemList;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewDataBinding viewCategory = DataBindingUtil.inflate(LayoutInflater.from( container.getContext() ), R.layout.layout_app_intro_screen, container, false);
        viewCategory.setVariable( image, itemList.get(position).getImageRes() );
        viewCategory.setVariable( BR.title, itemList.get(position).getTitle() );
        viewCategory.setVariable( BR.body, itemList.get(position).getBody() );
        viewCategory.executePendingBindings();

        container.addView( viewCategory.getRoot() );
        return viewCategory.getRoot();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView( (View) object );
    }


}
