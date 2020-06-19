package com.xhs.baselibrary.ui.update

import android.Manifest
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import com.xhs.baselibrary.R
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.SystemUtils
import com.xhs.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_base_version_update_layout.*
import java.io.File


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 10/04/2019.
 * description:
 */
class UpdateActivity : BaseActivity(), UpdateContact.UpdateView {

    private val apkName: String by lazy {
        intent?.extras?.getString(APK_NAME_KEY) ?: ""
    }
    private val apkUrl: String by lazy {
        intent?.extras?.getString(APK_URL_KEY) ?: ""
    }

    private val updateContent: String by lazy {
        intent?.extras?.getString(UPDATE_CONTENT_KEY) ?: ""
    }
    private val isForceUpdate by lazy { intent?.extras?.getBoolean(IS_FORCE_UPDATE_KEY) ?: false }

    private var isLoading = false

    private val updatePresenter by lazy {
        UpdatePresenter().apply {
            attachView(this@UpdateActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_version_update_layout)
        initView()
        initData()
        initListener()
    }


    private fun initData() {
        tvNewUpdate.text = updateContent
    }

    private fun initView() {
        if (isForceUpdate) {
            tvCancelUpdate.visibility = View.GONE
        }
        val supportActionBar = supportActionBar
        supportActionBar?.hide()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && (isForceUpdate || isLoading)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initListener() {
        tvCancelUpdate!!.setOnClickListener { finish() }

        tvOkUpdate!!.setOnClickListener {
            checkPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        if (isForceUpdate) {
            checkPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionsGranted(code: Int, permissions: Array<out String>?) {
        super.onPermissionsGranted(code, permissions)
        updatePresenter.updateApk(apkUrl, apkName)
    }

    override fun updateFinish(filePath: String) {
        if (!filePath.isNullOrEmpty() && File(filePath).exists() && SystemUtils.isAppInstalled(
                filePath
            )
        ) {
            SystemUtils.install(filePath)
            finish()
        } else {
            ToastUtils.show("下载失败")
            finish()
        }
        isLoading = false
    }

    override fun updateProgress(progress: Int) {
        runOnUiThread {
            progressBar.progress = progress
        }
    }

    override fun onStartDownload(length: Long) {
        runOnUiThread {
            rvUpdate.visibility = View.GONE
            lvProgress.visibility = View.VISIBLE
            tvCancelUpdate.visibility = View.GONE
            isLoading = true
        }
    }

    override fun updateFail(throwable: Throwable) {
        handleError(throwable)
        isLoading = false
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {

        private const val UPDATE_CONTENT_KEY = "UPDATE_CONTENT_KEY"

        private const val APK_URL_KEY = "APK_URL_KEY"

        private const val APK_NAME_KEY = "APK_NAME_KEY"

        private const val IS_FORCE_UPDATE_KEY = "IS_FORCE_UPDATE_KEY"

        fun setArgument(
            updateContent: String,
            apkName: String,
            apkUrl: String,
            isForceUpdate: Boolean
        ): Bundle {
            val bundle = Bundle()
            bundle.putString(UPDATE_CONTENT_KEY, updateContent)
            bundle.putString(APK_NAME_KEY, apkName)
            bundle.putString(APK_URL_KEY, apkUrl)
            bundle.putBoolean(IS_FORCE_UPDATE_KEY, isForceUpdate)
            return bundle
        }
    }
}
