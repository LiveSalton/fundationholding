//package com.salton123.fundation.upgrade
//
//import android.os.Handler
//import android.os.Message
//import com.salton123.fundation.FundInfoUpgradePage
//import java.lang.ref.WeakReference
//import java.util.concurrent.CountDownLatch
//
///**
// * User: wujinsheng1@yy.com
// * Date: 2020/11/11 11:20
// * ModifyTime: 11:20
// * Description:
// */
//internal class UpgradeHanlder(fundPage: FundInfoUpgradePage) : Handler() {
//    private var mPageRef = WeakReference<FundInfoUpgradePage>(fundPage)
//
//    companion object {
//        val MSG_WHAT_REQUEST_FUND_CODE = 0x100
//    }
//
//    private var mQueueLatch: CountDownLatch? = null
//    override fun handleMessage(msg: Message) {
//        super.handleMessage(msg)
//        when (msg.what) {
//            MSG_WHAT_REQUEST_FUND_CODE -> {
//                mQueueLatch = CountDownLatch(1)
//                mPageRef.get()?.mViewModel?.requestFundCode(msg.obj as Int, mQueueLatch!!)
//            }
//        }
//    }
//}