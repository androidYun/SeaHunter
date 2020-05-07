package com.sea.hunter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sea.custom.CustomMainActivity
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.main.SeaMainActivity
import com.sea.user.utils.sp.UserInformSpUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val phoneNumber = UserInformSpUtils.getPhoneNumber("")
        val password = UserInformSpUtils.getPassword("")
        if (phoneNumber.isNullOrBlank() || password.isNullOrBlank()) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            startActivity(Intent(this, CustomMainActivity::class.java))
        }
    }
}