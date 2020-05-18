package com.sea.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sea.custom.ui.club.ClubFragment
import com.sea.custom.ui.delicacy.DelicacyFragment
import com.sea.custom.ui.entertainment.EntertainmentFragment
import com.sea.custom.ui.make.DelicacyMakeFragment
import com.sea.custom.ui.mine.MineFragment
import com.xhs.baselibrary.base.BaseActivity
import com.sea.publicmodule.presenter.version.CheckVersionContact
import com.sea.publicmodule.presenter.version.CheckVersionPresenter
import com.sea.publicmodule.presenter.version.NCheckVersionModelReq
import com.sea.publicmodule.presenter.version.VersionModel
import kotlinx.android.synthetic.main.activity_custom_main.*

class CustomMainActivity : BaseActivity(), CheckVersionContact.ICheckVersionView {

    private lateinit var mClubFragment: ClubFragment
    private lateinit var mDelicacyFragment: DelicacyFragment
    private lateinit var mDelicacyMakeFragment: DelicacyMakeFragment
    private lateinit var mEntertainmentFragment: EntertainmentFragment
    private lateinit var mMineFragment: MineFragment

    private val mCheckVersionPresenter by lazy {
        CheckVersionPresenter()
            .apply { attachView(this@CustomMainActivity) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_main)
        initView()
        initData()
        initListener()
    }

    private fun initData() {
        mCheckVersionPresenter.loadCheckVersion(NCheckVersionModelReq(versionName = BuildConfig.VERSION_NAME))
    }

    private fun initListener() {
        rgpView.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rBtnClub -> {
                    replaceFragment(mClubFragment)
                }
                R.id.rBtnXiSha -> {
                    replaceFragment(mDelicacyFragment)
                }
                R.id.rBtnDelicacyMode -> {
                    replaceFragment(mDelicacyMakeFragment)
                }
                R.id.rBtnEntertainment -> {
                    replaceFragment(mEntertainmentFragment)
                }
                R.id.rBtnMine -> {
                    replaceFragment(mMineFragment)
                }
            }

        }
    }

    override fun loadCheckVersionSuccess(versionModel: VersionModel) {

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

    /**
     * 替换Fragment
     * @param fragment
     */
    private fun replaceFragment(fragment: Fragment) {
        //1.得到FragmentManger
        val fm = supportFragmentManager
        //2.开启事务
        val transaction = fm.beginTransaction()
        //3.替换
        transaction.replace(R.id.frameLayout, fragment)
        //4.提交事务
        transaction.commit()
    }

    private fun initView() {
        mClubFragment = ClubFragment.getInstance()
        mDelicacyFragment = DelicacyFragment.getInstance()
        mDelicacyMakeFragment = DelicacyMakeFragment.getInstance()
        mEntertainmentFragment = EntertainmentFragment.getInstance()
        mMineFragment = MineFragment.getInstance()
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frameLayout, mClubFragment)
        beginTransaction.commit()
    }
}