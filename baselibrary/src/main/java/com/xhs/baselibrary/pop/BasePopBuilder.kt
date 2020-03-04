package com.xhs.baselibrary.pop

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.xhs.baselibrary.utils.ScreenUtils
import java.lang.ref.WeakReference
import android.app.Activity
import android.view.WindowManager


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 08/01/2020.
 * description:
 */
abstract class BasePopBuilder<T : BasePopBuilder<T, K>, K : PopBaseViewHolder>(context: Context) {
    var width = ViewGroup.LayoutParams.MATCH_PARENT
    var height = ViewGroup.LayoutParams.WRAP_CONTENT
    val contextWeakReference: WeakReference<Context> = WeakReference(context)
    private var gravity = Gravity.CENTER
    private var view: View? = null

    private val popupWindow by lazy { PopupWindow() }

    init {
        popupWindow.isClippingEnabled = true
        ///< 设置背景颜色 半透明纯黑 【不设置，默认全屏有白边】
        popupWindow.setBackgroundDrawable(ColorDrawable(0x7f000000))
        setOutsideTouchable(true)
        view = LayoutInflater.from(contextWeakReference.get()).inflate(getInflateViewId(), null, false)
        popupWindow.setOnDismissListener { onDismiss() }
    }

    fun setWidth(width: Int): T {
        this.width = width
        return this as T
    }

    fun setGravity(gravity: Int): T {
        this.gravity = gravity
        return this as T
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * 屏幕透明度0.0-1.0 1表示完全不透明
     */
    fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = (contextWeakReference.get() as Activity).window
            .attributes
        lp.alpha = bgAlpha
        (contextWeakReference.get() as Activity).window.attributes = lp
    }

    fun setWidthPercentage(widthPercent: Float): T {
        this.width = (ScreenUtils.getScreenWidth() * widthPercent).toInt()
        return this as T
    }

    fun setHeightPercentage(heightPercent: Float): T {
        this.height = (ScreenUtils.getScreenHeight() * heightPercent).toInt()
        return this as T
    }

    abstract fun getInflateViewId(): Int

    fun getView(): View {
        if (view == null) {
            throw Exception("No pop view")
        }
        return view!!
    }

    /**
     * 弹窗消失回调
     * @param _onBaseListener
     * @return
     */
    fun setOnDismiss(_onBaseListener: OnEventListener.OnBaseListener?): T {
        if (null != popupWindow) {
            popupWindow.setOnDismissListener { _onBaseListener?.onDismiss() }
        }
        return this as T
    }


    open fun create(): T {
        popupWindow.width = width
        popupWindow.height = height
        popupWindow.contentView = view
        return this as T
    }

    /**
     * 设置背景颜色
     * @param hexColor
     * @return
     */
    fun setBackgroundDrawable(hexColor: Int): T {
        popupWindow.setBackgroundDrawable(ColorDrawable(hexColor))
        return this as T
    }

    /**
     * 设置Outside是否可点击
     * @param touchable
     * @return
     */
    fun setOutsideTouchable(touchable: Boolean): T {
        ///< 设置outside是否可点击
        popupWindow.isOutsideTouchable = touchable
        popupWindow.isFocusable = touchable
        return this as T
    }

    /**
     * 消失弹窗
     */
    fun dismiss() {
        if (null != popupWindow && popupWindow.isShowing) {
            popupWindow.dismiss()
        }
    }

    fun showAsDropDown(view: View) {
        if (!popupWindow.isShowing) {
            oShow()
            popupWindow.showAsDropDown(view)
        }
    }

    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        if (!popupWindow.isShowing) {
            oShow()
            popupWindow.showAtLocation(parent, gravity, x, y)
        }
    }


    private var mViewBaseHolder: PopBaseViewHolder? = null
    protected fun getViewHolder(): K {
        if (mViewBaseHolder == null) {
            mViewBaseHolder = createViewHolder()
        }
        return mViewBaseHolder as K
    }


    abstract fun createViewHolder(): PopBaseViewHolder

    abstract fun onDismiss(): T
    abstract fun oShow(): T

}