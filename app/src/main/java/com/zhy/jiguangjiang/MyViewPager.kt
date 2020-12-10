package com.zhy.jiguangjiang

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class MyViewPager(context: Context, attributeSet: AttributeSet) : ViewPager(context, attributeSet) {
    var listener : (y:Float)-> Unit = {}
    private var mLastY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    mLastY = it.y
                    return false
                }
                MotionEvent.ACTION_MOVE -> {
                    val delaY = it.y - mLastY
                    if (delaY < 0) {
                        listener(it.y)
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    fun touchChange(block:(y:Float)->Unit){
        listener = block
    }

}