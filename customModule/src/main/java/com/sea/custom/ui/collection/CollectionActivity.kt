package com.sea.custom.ui.collection

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.sea.custom.R
import com.sea.custom.ui.adapter.web.WebViewPageAdapter
import com.sea.custom.ui.collection.introduce.DelicacyIntroduceFragment
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class CollectionActivity : BaseActivity() {

    private val mMineCollectionList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_collection)
        initView()
    }

    private fun initView() {
        viewPager.adapter = MineCollectionPageAdapter(mMineCollectionList, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }
}


class MineCollectionPageAdapter(private val mList: List<String>, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return DelicacyIntroduceFragment.getInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }
}