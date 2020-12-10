package com.zhy.jiguangjiang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.zhy.jiguangjiang.starAnimate

class PageAdapter(val context: Context, val list: MutableList<Bean>) : PagerAdapter() {
    val mutableViewList = mutableListOf<View>()


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(context).inflate(R.layout.adapter_item, container, false)
        container.addView(view)
        if(position != 0) {
            view.starAnimate(false)
        }
        initView(view,position)
        mutableViewList.add(view)
        return view
    }


    override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        mutableViewList.removeAt(position)
        container.removeView((obj as View))
    }

    override fun getCount(): Int = list.size

    /**
     * 获取当前View
     */
    fun getView(index: Int): View = mutableViewList[index]

    private fun initView(view:View,position:Int){
        val bean = list[position]
        view.findViewById<ImageView>(R.id.header_image).apply { setImageResource(bean.imgId) }
        view.findViewById<ImageView>(R.id.icon).apply { setImageResource(bean.imgId) }
        view.findViewById<TextView>(R.id.textView).apply { text = bean.title }
        view.findViewById<TextView>(R.id.desc).apply { text = bean.desc }
    }
}