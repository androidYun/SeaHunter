package com.xhs.publicmodule.activity.web

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.xhs.baselibrary.base.BaseActivity
import com.xhs.publicmodule.R

class WebActivity : BaseActivity() {

    private val webContent by lazy { intent?.extras?.getString(web_content_key) ?: "" }
    private val title by lazy { intent?.extras?.getString(title_key) ?: "" }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        replaceFragment(WebContentFragment.getInstance(webContent))
        setTitle(title)
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

    companion object {
        private const val web_content_key = "web_content_key"
        private const val title_key = "title_key"
        fun getInstance(webUrl: String = "", title: String = "") = Bundle().apply {
            putString(web_content_key, webUrl)
            putString(title_key, title)

        }
    }
}