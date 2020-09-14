package com.salton123.bmob.bean

import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.datatype.BmobGeoPoint

data class User(
        var nickname: String? = "",
        var country: String? = "",
//        var score: String? = "",
        var age: Int? = 0,
        var gender: Int? = 0,
        var address: BmobGeoPoint? = BmobGeoPoint(),
        var province: String? = "",
        var city: String? = "",
        var avatar: BmobFile? = BmobFile()
) : BmobUser()