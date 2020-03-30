package com.sea.user.activity.main

import android.os.Bundle
import com.sea.user.R
import com.sea.user.activity.center.UserCenterFragment
import com.sea.user.activity.mall.SeaFoodMallFragment
import com.sea.user.activity.mall.car.ShopCarFragment
import com.sea.user.activity.mall.order.list.ShopOrderFragment
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.activity_sea_phone_mian.*


class SeaMainActivity : BaseActivity() {

    private lateinit var seaFoodMallFragment: SeaFoodMallFragment
    private lateinit var shopCarFragment: ShopCarFragment
    private lateinit var shopOrderFragment: ShopOrderFragment
    private lateinit var userCenterFragment: UserCenterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sea_phone_mian)
        initView()
        initListener()
    }

    private fun initListener() {
        rgpView.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rBtnSea -> {
                    replaceFragment(seaFoodMallFragment)
                }
                R.id.rBtnCar -> {
                    replaceFragment(shopCarFragment)
                }
                R.id.rBtnOrder -> {
                    replaceFragment(shopOrderFragment)
                }
                R.id.rBtnMine -> {
                    replaceFragment(userCenterFragment)
                }
            }

        }
    }

    /**
     * 替换Fragment
     * @param fragment
     */
    private fun replaceFragment(fragment: BaseFragment) {
        //1.得到FragmentManger
        val fm = supportFragmentManager
        //2.开启事务
        val transaction = fm.beginTransaction()
        //3.替换
        transaction.replace(R.id.frameLayout, fragment)
        //4.提交事务
        transaction.commit()
    }

    private fun initView() {
        seaFoodMallFragment = SeaFoodMallFragment.getInstance()
        shopCarFragment = ShopCarFragment.getInstance()
        shopOrderFragment = ShopOrderFragment.getInstance()
        userCenterFragment = UserCenterFragment.getInstance()
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frameLayout, seaFoodMallFragment)
        beginTransaction.commit()
    }
}