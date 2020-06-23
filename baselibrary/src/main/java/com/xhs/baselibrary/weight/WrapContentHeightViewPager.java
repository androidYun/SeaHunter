package com.xhs.baselibrary.weight;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 14/08/2019.
 * description:
 */
public class WrapContentHeightViewPager extends ViewPager {
    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        width = widthMeasureSpec;
        if (getChildCount() > getCurrentItem()) {
            View child = getChildAt(getCurrentItem());
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    private int width = 0;
    public void onRefresh() {
        try {
            int height = 0;
            if (getChildCount() > getCurrentItem()) {
                View child = getChildAt(getCurrentItem());
                child.measure(width, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }

            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            layoutParams.height = heightMeasureSpec;
            this.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
