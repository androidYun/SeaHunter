package com.xhs.baselibrary.pop

import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import org.jetbrains.annotations.NotNull

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 08/01/2020.
 * description:
 */
abstract class PopBaseViewHolder(private val viewLayout: View) {
    private val views = SparseArray<View>()
    fun setText(@IdRes id: Int, content: String) {
        var view = getView<TextView>(id)
        view.text = content
    }

    fun setTextColor(@IdRes id: Int, color: Int) {
        var view = getView<TextView>(id)
        view.setTextColor(color)
    }

    fun <T : View> getView(@IdRes viewId: Int): T {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = viewLayout.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }
}