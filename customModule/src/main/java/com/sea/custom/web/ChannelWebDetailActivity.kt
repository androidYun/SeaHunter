package com.sea.custom.web

import android.os.Bundle
import com.sea.custom.R
import com.sea.custom.ui.club.DetailWebFragment
import com.xhs.baselibrary.base.BaseActivity

class ChannelWebDetailActivity : BaseActivity() {
    private val webId by lazy { intent?.extras?.getInt(web_detail_id_key) ?: -1 }

    private val channelName by lazy { intent?.extras?.getString(channel_name_key) ?: "" }

    private val title by lazy { intent?.extras?.getString(title_key) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel_web_detail)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, DetailWebFragment.getInstance(webId, channelName)).commit()
        setTitle(title)
    }

    companion object {

        private const val channel_name_key = "channel_name_key"
        private const val web_detail_id_key = "web_detail_id_key"
        private const val title_key = "title_key"
        fun getInstance(id: Int, channelName: String, title: String) = Bundle().apply {
            putString(channel_name_key, channelName)
            putInt(web_detail_id_key, id)
            putString(title_key, title)
        }
    }
}