package com.sea.custom.ui.adapter.web

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xhs.baselibrary.ui.web.WebFragment

class WebViewPageAdapter(private val mList: List<WebViewItem>, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return WebFragment.getInstance(mList[position].webUrl)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mList[position].titleName
    }

    override fun getCount(): Int {
        return mList.size
    }
}

class WebViewItem(val titleName: String, val webUrl: String)