package com.sea.custom.ui.set

import android.content.Intent
import android.os.Bundle
import com.sea.custom.BuildConfig
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.publicmodule.presenter.version.CheckVersionContact
import com.sea.publicmodule.presenter.version.CheckVersionPresenter
import com.sea.publicmodule.presenter.version.NCheckVersionModelReq
import com.sea.publicmodule.presenter.version.VersionModel
import com.xhs.baselibrary.base.BaseActivity
import com.sea.publicmodule.router.RouterManager
import com.xhs.baselibrary.ui.update.UpdateActivity
import kotlinx.android.synthetic.main.activity_custom_set.*

class SetActivity : BaseActivity(), CheckVersionContact.ICheckVersionView {
    private val mCheckVersionPresenter by lazy {
        CheckVersionPresenter()
            .apply { attachView(this@SetActivity) }
    }

    private var versionModel: VersionModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_set)
        initListener()
        initData()
    }

    private fun initData() {
        mCheckVersionPresenter.loadCheckVersion(NCheckVersionModelReq(versionName = BuildConfig.VERSION_NAME))
    }

    private fun initListener() {
        tvModifyInform.setOnClickListener { RouterManager.seaRouter?.modifyUserInform() }
        tvModifyPassword.setOnClickListener { RouterManager.seaRouter?.modifyPassword() }
        tvModifyPhone.setOnClickListener { RouterManager.seaRouter?.modifyPhone() }
        tvModifyInform.setOnClickListener { RouterManager.seaRouter?.modifyUserInform() }
        tvBackLogin.setOnClickListener { RouterManager.seaRouter?.loginOut() }
        tvVersion.setOnClickListener {
            if (versionModel != null && BuildConfig.VERSION_NAME != versionModel?.version) {
                val baseUrl = versionModel?.android_url?.substringBeforeLast("/").plus("/")
                val apkName = versionModel?.android_url?.substringAfterLast("/") ?: ""
                if (baseUrl.isNullOrBlank() || apkName.isNullOrBlank()) {
                    return@setOnClickListener
                }
                startActivity(
                    Intent(
                        this,
                        UpdateActivity::class.java
                    ).apply {
                        putExtras(
                            UpdateActivity.setArgument(
                                "",
                                apkName,
                                baseUrl,
                                versionModel?.forced == 1
                            )
                        )
                    })
            }
        }
    }

    override fun loadCheckVersionSuccess(versionModel: VersionModel) {
        this.versionModel = versionModel
        if (BuildConfig.VERSION_NAME != versionModel.version) {
            tvVersion.text = "您有版本升级"
        } else {
            tvVersion.text = "已是最新版本"
        }
    }


    override fun loadCheckVersionFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }
}