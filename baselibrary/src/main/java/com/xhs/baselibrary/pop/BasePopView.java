package com.xhs.baselibrary.pop;

import android.content.Context;
import android.view.View;

/*
*@Description: 自定义弹窗API + 包含点击事件回调
*@Author: hl
*@Time: 2019/2/12 14:38
*/
public class BasePopView {
    /**
     * 显示方位【基于控件】
     */
    public enum GRAVITY {
        LEFTTOP_TO_LEFTBOTTOM, LEFTTOP_TO_RIGHTBOTTOM,
        LEFTTOP_TO_LEFTTOP, LEFTTOP_TO_RIGHTTOP,
        RIGHTTOP_TO_LEFTBOTTOM, RIGHTTOP_TO_RIGHTBOTTOM,
        RIGHTTOP_TO_RIGHTTOP, RIGHTBOTTOM_TO_LEFTTOP,
        RIGHTBOTTOM_TO_RIGHTTOP, LEFTBOTTOM_TO_RIGHTTOP,
        LEFTBOTTOM_TO_LEFTTOP
    }

    /**
     * 简单上下左右中显示
     */
    public enum SIMPLE_GRAVITY {
        CENTER_IN_PARENT, FROM_BOTTOM, FROM_TOP,
        FROM_LEFT, FROM_RIGHT
    }

    /**
     * 显示动画
     * SCALE - 缩放
     * TRANSLATE - 平移
     * FOLD - 折叠
     */
    public enum ANIMATION {
        NONE, SCALE, TRANSLATE, FOLD
    }

}
