package com.sea.user.activity.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sea.user.R
import com.sea.user.activity.wallet.WalletFragment
import com.xhs.baselibrary.base.BaseActivity

class MineOrderActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_order)
    }

    class BalanceViewPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                WalletFragment.getInstance(WalletFragment.WALLET_BALANCE_CODE)
            } else {
                WalletFragment.getInstance(WalletFragment.WALLET_REFLECT_CODE)
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}