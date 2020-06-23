package com.sea.publicmodule.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class JudgeNestedScrollView extends NestedScrollView {
        private boolean isNeedScroll = true;
        private float xDistance, yDistance, xLast, yLast;

    public JudgeNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public JudgeNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JudgeNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDistance = yDistance = 0f;
                    xLast = ev.getX();
                    yLast = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();
                    xDistance += Math.abs(curX - xLast);
                    yDistance += Math.abs(curY - yLast);
                    xLast = curX;
                    yLast = curY;
                    if (xDistance > yDistance) {
                        return false;
                    }
                    return isNeedScroll;

            }
            return super.onInterceptTouchEvent(ev);
        }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
        public void setNeedScroll(boolean isNeedScroll) {
            this.isNeedScroll = isNeedScroll;
        }
}
