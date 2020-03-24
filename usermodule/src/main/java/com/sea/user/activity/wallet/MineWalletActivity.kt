package com.sea.user.activity.wallet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sea.user.R
import com.sea.user.activity.wallet.reflect.ReflectActivity
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_mine_wallet.*
import kotlinx.android.synthetic.main.include_tab_layout_view_pager.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 06/03/2020.
 * description:
 */
class MineWalletActivity : BaseActivity() {

    private val walletList = mutableListOf("余额明细", "提现明细")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_wallet)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        viewPage.adapter = BalanceViewPageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPage)
    }

    private fun initData() {

    }

    private fun initListener() {
        tvReflect.setOnClickListener {
            startActivity(Intent(this, ReflectActivity::class.java))
        }

    }

    inner class BalanceViewPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                WalletFragment.getInstance(WalletFragment.WALLET_BALANCE_CODE)
            } else {
                WalletFragment.getInstance(WalletFragment.WALLET_REFLECT_CODE)
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return walletList[position]
        }

        override fun getCount(): Int {
            return walletList.size
        }
    }
}