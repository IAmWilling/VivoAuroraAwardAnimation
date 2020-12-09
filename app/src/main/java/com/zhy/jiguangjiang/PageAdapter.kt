package com.zhy.jiguangjiang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.zhy.jiguangjiang.starAnimate

class PageAdapter(val context: Context, val list: MutableList<String>) : PagerAdapter() {
    val mutableViewList = mutableListOf<View>()


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(context).inflate(R.layout.adapter_item, container, false)
        container.addView(view)
        if(position != 0) {
            view.starAnimate(false)
        }

        mutableViewList.add(view)
        return view
    }


    override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView((obj as View))
    }

    override fun getCount(): Int = list.size

    /**
     * 获取当前View
     */
    fun getView(index: Int): View = mutableViewList[index]
}