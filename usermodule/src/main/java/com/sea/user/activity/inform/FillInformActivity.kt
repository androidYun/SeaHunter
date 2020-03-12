package com.sea.user.activity.inform

import android.content.Intent
import android.os.Bundle
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.sea.user.R
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.password.ForgetPasswordActivity
import com.sea.user.activity.register.RegisterActivity
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.imageLoader.GlideEngine
import com.xhs.prison.model.NFillInformReq
import kotlinx.android.synthetic.main.activity_fill_inform.*

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 31/12/2019.
 * description:
 */
class FillInformActivity : BaseActivity(), FillInformContract.IFillInformView {

    private val fillInformPresenter by lazy {
        FillInformPresenter().apply {
            attachView(this@FillInformActivity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_inform)
        initView()
        initData()
        initListener()
    }


    private fun initView() {

    }

    private fun initData() {

    }

    private fun initListener() {

        tvConfirm.setOnClickListener {
            fillInformPresenter.loadFillInform(NFillInformReq())
        }
        tvAccountLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
        ivUploadHead.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropImageWideHigh(150, 150)// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .forResult(object : OnResultCallbackListener {
                    override fun onResult(result: MutableList<LocalMedia>?) {

                    }

                    override fun onCancel() {

                    }
                })
        }
    }

    override fun loadFillInformSuccess() {

    }

    override fun loadFillInformFail(throwable: Throwable) {

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