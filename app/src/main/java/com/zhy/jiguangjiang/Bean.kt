package com.zhy.jiguangjiang

import androidx.annotation.ColorInt
import androidx.annotation.IdRes

data class Bean(
    var title:String,
    var desc:String,
     var imgId:Int,
    @ColorInt var color:Int
) {
}