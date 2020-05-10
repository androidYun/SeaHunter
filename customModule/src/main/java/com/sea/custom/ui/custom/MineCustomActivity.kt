package com.sea.custom.ui.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.xhs.baselibrary.base.BaseActivity
import com.sea.custom.R
import com.sea.custom.em.ChannelEnum
import com.sea.custom.ui.collection.introduce.DelicacyIntroduceFragment
import com.sea.custom.ui.collection.make.DelicacyMakeFragment
import com.sea.custom.utils.DeviceUtils
import kotlinx.android.synthetic.main.activity_mine_custom.*
import kotlinx.android.synthetic.main.include_tab_viewpage.*

class MineCustomActivity : BaseActivity(){

    private val mMineCollectionList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_collection)
        initView()
    }

    private fun initView() {
        mMineCollectionList.add("美食制作")
        mMineCollectionList.add("会员定制")
        viewPager.adapter = MineCollectionPageAdapter(mMineCollectionList, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }
}


class MineCollectionPageAdapter(private val mList: List<String>, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            MineCustomFragment.getInstance(ChannelEnum.food.name)
        } else {
            MineCustomFragment.getInstance(ChannelEnum.service.name)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }
}