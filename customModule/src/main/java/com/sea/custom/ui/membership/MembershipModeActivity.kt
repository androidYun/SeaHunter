package com.sea.custom.ui.membership

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sea.custom.R
import com.sea.custom.dialog.ApplyShipDialog
import com.sea.custom.em.ChannelEnum
import com.sea.custom.listener.ApplyMemberShipListener
import com.sea.custom.presenter.apply.ApplyMembershipContact
import com.sea.custom.presenter.apply.ApplyMembershipPresenter
import com.sea.custom.presenter.apply.NApplyMemberModel
import com.sea.custom.presenter.apply.NApplyMembershipReq
import com.sea.custom.ui.store.NStoreListModelReq
import com.sea.custom.ui.store.StoreListContact
import com.sea.custom.ui.store.StoreListItem
import com.sea.custom.ui.store.StoreListPresenter
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import com.sea.publicmodule.activity.DataPickerActivity
import kotlinx.android.synthetic.main.activity_member_ship_mode.*


class MembershipModeActivity : BaseActivity(), StoreListContact.IStoreListView,
    ApplyMembershipContact.IApplyMembershipView {

    private val mSelectStorePresenter by lazy { StoreListPresenter().apply { attachView(this@MembershipModeActivity) } }

    private val nStoreListModelReq = NStoreListModelReq()

    private val mApplyMembershipPresenter by lazy {
        ApplyMembershipPresenter().apply {
            attachView(
                this@MembershipModeActivity
            )
        }
    }


    private val nApplyMembershipReq = NApplyMembershipReq()

    private val mMembershipModeList = mutableListOf<StoreListItem>()

    private lateinit var mMembershipModeAdapter: MembershipModeAdapter

    private var mApplyShipDialog: ApplyShipDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_ship_mode)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        mMembershipModeAdapter = MembershipModeAdapter(mMembershipModeList)
        mMembershipModeAdapter.emptyView =
            emptyView
        rvMembershipMode.layoutManager = LinearLayoutManager(this)
        rvMembershipMode.adapter = mMembershipModeAdapter
    }

    private fun initData() {
        nApplyMembershipReq.channel_name = ChannelEnum.shop.name
        mSelectStorePresenter.loadStoreList(nStoreListModelReq)
    }

    private fun initListener() {
        swipeMembershipMode.setOnRefreshListener {
            mSelectStorePresenter.loadStoreList(nStoreListModelReq)
        }
        mMembershipModeAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tvMemberShipMode -> {
                    if (mMembershipModeList[position].is_join) {
                        return@setOnItemChildClickListener
                    }
                    nApplyMembershipReq.article_id = mMembershipModeList[position].id
                    nApplyMembershipReq.shop_id = 0
                    mApplyShipDialog = ApplyShipDialog(this, object : ApplyMemberShipListener {
                        override fun applyMemberShipSuccess(nApplyMemberModel: NApplyMemberModel) {
                            nApplyMembershipReq.name = nApplyMemberModel.name
                            nApplyMembershipReq.phone = nApplyMemberModel.phone
                            nApplyMembershipReq.webchat = nApplyMemberModel.webchat
                            nApplyMembershipReq.birthday = nApplyMemberModel.birthday
                            mApplyMembershipPresenter.loadApplyMembership(nApplyMembershipReq)
                        }
                    })
                    mApplyShipDialog?.show()
                }
            }
        }
    }

    override fun loadStoreListSuccess(mList: List<StoreListItem>, totalCount: Int) {
        mMembershipModeList.clear()
        mMembershipModeList.addAll(mList)
        mMembershipModeAdapter.notifyDataSetChanged()
        mMembershipModeAdapter.loadMoreComplete()
        swipeMembershipMode.isRefreshing = false
    }


    override fun loadStoreListFail(throwable: Throwable) {
        handleError(throwable)
        swipeMembershipMode.isRefreshing = false
        mMembershipModeAdapter.loadMoreComplete()
    }


    override fun loadApplyMembershipSuccess() {
        ToastUtils.show("申请成功,等待审核")
        mSelectStorePresenter.loadStoreList(nStoreListModelReq)
        mApplyShipDialog?.dismiss()
    }

    override fun loadApplyMembershipFail(throwable: Throwable) {
        handleError(throwable)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == DataPickerActivity.date_result_code) {
            val birthday = data?.getStringExtra(DataPickerActivity.date_data_key) ?: ""
            mApplyShipDialog?.setBirthDay(birthday)
        }
    }

    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        fun getInstance() = Bundle().apply { }
    }
}