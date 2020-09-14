package com.salton123.soulove.splash

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.lxj.xpopup.XPopup
import com.salton123.feature.PermissionFeature
import com.salton123.soulove.common.Constants
import com.salton123.soulove.common.util.PermissionPageUtil

/**
 * User: newSalton@outlook.com
 * Date: 2019/8/6 17:46
 * ModifyTime: 17:46
 * Description:
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        object : PermissionFeature() {
            override fun onRequestFinish(isGranted: Boolean) {
                super.onRequestFinish(isGranted)
                if (isGranted) {
                    var path = Constants.Router.Fundation.MAIN
                    ARouter.getInstance()
                            .build(path)
                            .navigation(this@SplashActivity)
                } else {
                    XPopup.Builder(this@SplashActivity).dismissOnTouchOutside(false)
                            .dismissOnBackPressed(false)
                            .asConfirm("提示", "权限不足,请允许应用获取权限", {
                                PermissionPageUtil(this@SplashActivity).jumpPermissionPage()
                                AppUtils.exitApp()
                            }, {
                                AppUtils.exitApp()
                            })
                            .show()
                }
                finish()
            }

            override fun getPermissionArr(): Array<String> {
                return arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE)
            }
        }.onBindLogic()
    }

    override fun onBackPressed() {
    }
}
