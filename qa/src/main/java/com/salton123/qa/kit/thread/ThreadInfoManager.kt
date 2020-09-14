package com.salton123.qa.kit.thread

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/5/18 21:48
 * ModifyTime: 21:48
 * Description:
 */
class ThreadInfoManager {
    fun printAllThreads() {
        Thread.getAllStackTraces()
    }
}