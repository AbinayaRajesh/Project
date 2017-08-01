package com.codepath.myapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by emilylroth on 7/28/17.
 */

public class ImageAdapterSwipe extends PagerAdapter {
    Context context;

    public ImageAdapterSwipe(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
//        int padding = 0;
//        imageView.setPadding(0, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((ViewPager) container).addView(imageView, 0);
   //     Glide.with(context)
       //         .load(OptionsActivity.urls.get(position))
     //           .into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}

