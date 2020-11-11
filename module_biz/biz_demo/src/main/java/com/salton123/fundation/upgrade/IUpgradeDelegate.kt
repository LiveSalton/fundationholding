package com.salton123.fundation.upgrade

import java.util.concurrent.CountDownLatch

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/11/11 16:45
 * ModifyTime: 16:45
 * Description:
 */
interface IUpgradeDelegate {
    fun requestFundCode(index: Int, latch: CountDownLatch)
    fun reqHoldingStocks(fCode: String, latch: CountDownLatch)
}