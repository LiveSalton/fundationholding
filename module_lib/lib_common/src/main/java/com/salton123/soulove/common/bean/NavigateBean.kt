package com.salton123.soulove.common.bean

import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment

class NavigateBean @JvmOverloads constructor(
        var fragment: ISupportFragment? = null,
        var extraTransaction: ExtraTransaction? = null,
        @ISupportFragment.LaunchMode
        var launchMode: Int? = ISupportFragment.SINGLETASK
) {
}