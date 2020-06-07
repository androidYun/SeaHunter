package com.sea.custom.ui.mine

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.sea.custom.CustomMainActivity
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.ui.collection.CollectionActivity
import com.sea.custom.ui.custom.MineCustomActivity
import com.sea.custom.ui.membership.MembershipModeActivity
import com.sea.custom.ui.set.SetActivity
import com.sea.custom.ui.store.StoreListActivity
import kotlinx.android.synthetic.main.fragment_mine_layout.*
import com.xhs.baselibrary.base.BaseFragment
import com.xhs.baselibrary.utils.imageLoader.ImageLoader
import com.sea.publicmodule.presenter.user.UserInformContact
import com.sea.publicmodule.presenter.user.UserInformModel
import com.sea.publicmodule.presenter.user.UserInformPresenter
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.utils.PermissionsUtils

class MineFragment : BaseFragment(), UserInformContact.IUserInformView {

    private val mUserCenterPresenter by lazy {
        UserInformPresenter()
            .apply { attachView(this@MineFragment) }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_mine_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
        initToolbar(toolbar, "个人中心", false)
    }

    private fun initView() {

    }

    /**
     * Fragment中初始化Toolbar
     * @param toolbar
     * @param title 标题
     * @param isDisplayHomeAsUp 是否显示返回箭头
     */
    private fun initToolbar(toolbar: Toolbar?, title: String?, isDisplayHomeAsUp: Boolean) {
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        val actionBar: ActionBar? = appCompatActivity.supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp)
        }
    }

    private fun initData() {

    }

    override fun onResume() {
        super.onResume()
        mUserCenterPresenter.loadUserInform()
    }

    private fun initListener() {
        swipeLayout.setOnRefreshListener { mUserCenterPresenter.loadUserInform() }
        tvCollection.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    CollectionActivity::class.java
                )
            )
        }

        tvGoLook.setOnClickListener {
            startActivity(Intent(context, MembershipModeActivity::class.java))
        }
        tvCustomized.setOnClickListener {
            startActivity(Intent(context, MineCustomActivity::class.java))
        }
        tvAdmissionMode.setOnClickListener {
            startActivity(Intent(context, MembershipModeActivity::class.java))
        }
        tvAllStore.setOnClickListener {
            startActivity(Intent(context, StoreListActivity::class.java))
        }

        tvSet.setOnClickListener {
            startActivity(Intent(context, SetActivity::class.java))
        }
        tvContactOus.setOnClickListener {
            PermissionsUtils.getInstance()
                .chekPermissions(
                    activity,
                    permissions,
                    object : PermissionsUtils.IPermissionsResult {
                        override fun passPermissions() {
                            val intent = Intent(Intent.ACTION_CALL)
                            val data = Uri.parse("tel:18328331313")
                            intent.data = data
                            startActivity(intent)
                        }

                        override fun forbidPermissions() {
                            Toast.makeText(
                                context,
                                "您没有允许部分权限，可能会导致部分功能不能正常使用，如需正常使用  请允许权限",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Handler().postDelayed({
                                activity?.let {
                                    ActivityCompat.requestPermissions(
                                        activity!!,
                                        permissions,
                                        PermissionsUtils.getInstance().mRequestCode
                                    )
                                }
                            }, 500)
                        }
                    })
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(activity, requestCode, permissions, grantResults)
    }
    private val permissions = arrayOf(
        Manifest.permission.CALL_PHONE
    )

    override fun loadUserInformSuccess(userInformModel: UserInformModel) {
        UserInformSpUtils.setUserInformModel(userInformModel)
        tvUserName.text = userInformModel.nick_name
        tvVipLevel.text = userInformModel.group_name
        if(userInformModel.group_id==1){
            ivVip.setImageResource(R.mipmap.vip_ordinary)
        }else{
            ivVip.setImageResource(R.mipmap.vip_senior)

        }
        ImageLoader.loadCircleImageView(
            ivHead,
            Constants.baseUrl.plus(userInformModel.avatar.replace("//", "/"))
        )
        swipeLayout.isRefreshing = false
    }

    override fun loadUserInformFail(throwable: Throwable) {
        swipeLayout.isRefreshing = false
        handleError(throwable)
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = MineFragment().apply {
            arguments = Bundle().apply { }
        }
    }
}