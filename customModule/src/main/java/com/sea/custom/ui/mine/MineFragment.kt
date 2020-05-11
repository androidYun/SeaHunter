package com.sea.custom.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.xhs.publicmodule.presenter.user.UserInformContact
import com.xhs.publicmodule.presenter.user.UserInformModel
import com.xhs.publicmodule.presenter.user.UserInformPresenter
import com.xhs.publicmodule.utils.sp.UserInformSpUtils

class MineFragment : BaseFragment(), UserInformContact.IUserInformView {

    private val mUserCenterPresenter by lazy { UserInformPresenter().apply { attachView(this@MineFragment) } }


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
            startActivity(Intent(context, CollectionActivity::class.java))
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
    }

    override fun loadUserInformSuccess(userInformModel: UserInformModel) {
        UserInformSpUtils.setUserInformModel(userInformModel)
        tvUserName.text = userInformModel.nick_name
        tvVipLevel.text = userInformModel.group_name
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