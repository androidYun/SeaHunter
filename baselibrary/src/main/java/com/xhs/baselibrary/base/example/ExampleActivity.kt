package com.xhs.baselibrary.base.example

import android.os.Bundle
import com.xhs.baselibrary.base.BaseActivity

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 29/04/2019.
 * description:
 */
class ExampleActivity : BaseActivity(), ExampleContract.ExampleView {

    private val examplePresenter by lazy {
        ExamplePresenter().apply {
            attachView(this@ExampleActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        examplePresenter.goLogin("13014688217", "123456")
    }

    override fun loadLogin() {

    }

    override fun loadLoginFail(throwable: Throwable) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
