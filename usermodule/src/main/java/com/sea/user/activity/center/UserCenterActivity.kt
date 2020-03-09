package com.sea.user.activity.center

import android.os.Bundle
import com.sea.user.R
import com.xhs.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_user_center.*

/**
 * @author guiyun.li
 * 创建日期：2020/3/8 0008
 * 邮箱 xyz_6776@163.com
 * 描述：
 *
 */
class UserCenterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_center)
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.frameLayout,UserCenterFragment.getInstance())
        beginTransaction.commit()

    }
}