package com.sea.custom.ui.member.detail

import android.app.AlertDialog
import android.os.Bundle
import com.sea.custom.R
import com.sea.custom.dialog.CustomServicesDialog
import com.sea.custom.em.ChannelEnum
import com.sea.custom.listener.ApplyMemberShipListener
import com.sea.custom.presenter.apply.ApplyMembershipContact
import com.sea.custom.presenter.apply.ApplyMembershipPresenter
import com.sea.custom.presenter.apply.NApplyMemberModel
import com.sea.custom.presenter.apply.NApplyMembershipReq
import com.sea.custom.presenter.channel.NChannelItem
import com.sea.custom.presenter.channel.detail.ChannelDetailContact
import com.sea.custom.presenter.channel.detail.ChannelDetailPresenter
import com.sea.custom.presenter.channel.detail.NChannelDetailModelReq
import com.sea.custom.ui.adapter.ShopBannerAdapter
import com.sea.custom.ui.club.DetailWebFragment
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.baselibrary.utils.ToastUtils
import com.xhs.baselibrary.utils.UIUtils
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_member_custom_detail.*

class MemberCustomDetailActivity : BaseActivity(), ChannelDetailContact.IChannelDetailView,
    ApplyMembershipContact.IApplyMembershipView {

    private var mChannelItem: NChannelItem? = null

    private val id by lazy { intent?.extras?.getInt(Channel_Item_id_key) ?: -1 }

    private val nApplyMembershipReq = NApplyMembershipReq()


    private val mApplyMembershipPresenter by lazy {
        ApplyMembershipPresenter().apply {
            attachView(
                this@MemberCustomDetailActivity
            )
        }
    }

    private val mChannelDetailPresenter by lazy { ChannelDetailPresenter().apply { attachView(this@MemberCustomDetailActivity) } }

    private lateinit var shopBannerAdapter: ShopBannerAdapter
    private val mBannerList = mutableListOf<String>()


    private var mApplyShipDialog: CustomServicesDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_custom_detail)
        initView()
        initData()
        initListener()
    }


    private fun initView() {
        when {
            UserInformSpUtils.getUserInformModel().group_id == 1 -> {
                ivCustomStatus.setImageResource(R.mipmap.vip_registered)
                tvCustomMember.setTextColor(UIUtils.getInstance().getColor(R.color.custom_theme_color))
            }
            UserInformSpUtils.getUserInformModel().group_id == 2 -> {
                ivCustomStatus.setImageResource(R.mipmap.vip_ordinary)
                tvCustomMember.setTextColor(UIUtils.getInstance().getColor(R.color.custom_theme_color))
            }
            UserInformSpUtils.getUserInformModel().group_id == 3 -> {
                ivCustomStatus.setImageResource(R.mipmap.vip_senior)
                tvCustomMember.setTextColor(UIUtils.getInstance().getColor(R.color.color_774e2a))
            }
        }
        shopBannerAdapter = ShopBannerAdapter(mBannerList, true)
        bannerView.addBannerLifecycleObserver(this).setAdapter(shopBannerAdapter)
            .setIndicator(CircleIndicator(this)).start()
    }

    private fun initData() {
        nApplyMembershipReq.channel_name = ChannelEnum.service.name
        supportFragmentManager.beginTransaction()
            .add(
                R.id.frameLayout,
                DetailWebFragment.getInstance(id ?: -1, ChannelEnum.service.name)
            )
            .commit()
        mChannelDetailPresenter.loadChannelDetail(
            NChannelDetailModelReq(
                channel_name = ChannelEnum.service.name,
                id = id
            )
        )
    }

    private fun initListener() {
        tvCustomized.setOnClickListener {
            if (mChannelItem?.group_id ?: 0 > UserInformSpUtils.getUserInformModel().group_id) {
                AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("由于您的等级不够，暂时不能定制，请联系门店升级。")
                    .setPositiveButton(
                        "确定"
                    ) { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
                return@setOnClickListener
            }
            nApplyMembershipReq.article_id = mChannelItem?.id ?: -1
            mApplyShipDialog = CustomServicesDialog(this, object : ApplyMemberShipListener {
                override fun applyMemberShipSuccess(nApplyMemberModel: NApplyMemberModel) {
                    nApplyMembershipReq.name = nApplyMemberModel.name
                    nApplyMembershipReq.phone = nApplyMemberModel.phone
                    nApplyMembershipReq.address = nApplyMemberModel.address
                    mApplyMembershipPresenter.loadApplyMembership(nApplyMembershipReq)
                }
            })
            mApplyShipDialog?.show()
        }
        swipeMemberCustomDetail.setOnRefreshListener {
            mChannelDetailPresenter.loadChannelDetail(
                NChannelDetailModelReq(
                    channel_name = ChannelEnum.service.name,
                    id = mChannelItem?.id ?: -1
                )
            )
        }
        ivBack.setOnClickListener { finish() }
    }

    override fun loadChannelDetailSuccess(content: NChannelItem) {
        this.mChannelItem = content
        tvTitle.text = mChannelItem?.title ?: ""
        mBannerList.clear()
        if (!content.albums.isNullOrEmpty()) {
            mBannerList.addAll(content.albums.map { it?.original_path ?: "" })
        } else if (!content.img_url.isNullOrBlank()) {
            mBannerList.add(content.img_url)
        }
        shopBannerAdapter.notifyDataSetChanged()
        swipeMemberCustomDetail.isRefreshing = false
    }

    override fun loadChannelDetailFail(throwable: Throwable) {
        handleError(throwable)
        swipeMemberCustomDetail.isRefreshing = false
    }

    override fun loadApplyMembershipSuccess() {
        ToastUtils.show("申请成功,等待审核")
        mApplyShipDialog?.dismiss()
        swipeMemberCustomDetail.isRefreshing = false
    }

    override fun loadApplyMembershipFail(throwable: Throwable) {
        handleError(throwable)
        swipeMemberCustomDetail.isRefreshing = false
    }


    override fun showLoading() {
        showProgressDialog()
    }

    override fun hideLoading() {
        hideProgressDialog()
    }

    companion object {
        private const val Channel_Item_id_key = "Channel_Item_id_key"
        fun getInstance(
            id: Int
        ) = Bundle().apply {
            putInt(Channel_Item_id_key, id)
        }
    }
}