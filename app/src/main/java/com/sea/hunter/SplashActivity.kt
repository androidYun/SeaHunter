package com.sea.hunter

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sea.custom.CustomMainActivity
import com.sea.publicmodule.utils.sp.UserInformSpUtils
import com.sea.user.activity.login.LoginActivity
import com.sea.user.activity.login.version.VersionLoginActivity
import com.xhs.baselibrary.utils.PermissionsUtils

class SplashActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        PermissionsUtils.showSystemSetting = true
        PermissionsUtils.getInstance().chekPermissions(this, permissions, object : PermissionsUtils.IPermissionsResult {
            override fun passPermissions() {
                val phoneNumber = UserInformSpUtils.getPhoneNumber("")
                val password = UserInformSpUtils.getPassword("")
                Handler().postDelayed({
                    if (phoneNumber.isNullOrBlank()) {
                        startActivity(Intent(this@SplashActivity, VersionLoginActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashActivity, CustomMainActivity::class.java))
                    }
                    finish()
                }, 1000)

            }

            override fun forbidPermissions() {
                Toast.makeText(this@SplashActivity, "您没有允许部分权限，可能会导致部分功能不能正常使用，如需正常使用  请允许权限", Toast.LENGTH_SHORT)
                    .show()
                Handler().postDelayed({
                    ActivityCompat.requestPermissions(
                        this@SplashActivity,
                        permissions,
                        PermissionsUtils.getInstance().mRequestCode
                    )
                }, 500)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }
}