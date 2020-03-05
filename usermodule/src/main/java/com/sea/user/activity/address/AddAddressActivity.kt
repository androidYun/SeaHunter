package com.sea.user.activity.address

import android.os.Bundle
import com.sea.user.R
import com.xhs.baselibrary.base.BaseActivity

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class AddAddressActivity : BaseActivity(), AddAddressContract.IAddAddressView {

    private val addaddressPresenter by lazy {
        AddAddressPresenter().apply {
            attachView(this@AddAddressActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        initView()
        initData()
    }


    private fun initView() {

    }

    private fun initData() {

    }


    override fun loadAddAddressSuccess() {

    }

    override fun loadAddAddressFail(throwable: Throwable) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {
        fun getInstance() = Bundle().apply {

        }
    }
}