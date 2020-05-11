package com.sea.custom.ui.set

import android.os.Bundle
import com.sea.custom.R
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.publicmodule.router.RouterManager
import kotlinx.android.synthetic.main.activity_custom_set.*

class SetActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_set)
        initListener()
    }

    private fun initListener() {
        tvModifyInform.setOnClickListener { RouterManager.seaRouter?.modifyUserInform() }
        tvModifyPassword.setOnClickListener { RouterManager.seaRouter?.modifyPassword() }
        tvModifyPhone.setOnClickListener { RouterManager.seaRouter?.modifyPhone() }
        tvModifyInform.setOnClickListener { RouterManager.seaRouter?.modifyUserInform() }
    }
}