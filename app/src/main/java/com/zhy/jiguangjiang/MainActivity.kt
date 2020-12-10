package com.zhy.jiguangjiang

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PageAdapter
    private val handler = Handler(Looper.myLooper()!!)
    private val myRun = MyRun()
    private var current: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = mutableListOf<Bean>(
            Bean("QLOLOLOLO", "LOOLOLOLO~", R.mipmap.lol, Color.GREEN),
            Bean("LOL决赛", "英雄去超越~~LOL决赛~", R.mipmap.lol2, Color.BLUE),
            Bean("最屌寒冰射手", "艾希的箭能不能射中~", R.mipmap.lol3, Color.BLACK),
            Bean("英雄腕豪", "腕豪~~66666~", R.mipmap.lol5, Color.RED),
            Bean("太空极简", "极简设计~~9999~", R.mipmap.tk, Color.WHITE),
            Bean("红红火烈鸟", "红红火火!小小鸟~", R.mipmap.hln, Color.YELLOW),
            Bean("樱岛麻衣", "这不是樱岛麻衣~", R.mipmap.ydmayi, Color.CYAN)
        )
        adapter = PageAdapter(this, list)
        mViewPager.pageMargin = 15
        mViewPager.adapter = adapter
        mViewPager.offscreenPageLimit = list.size
        mViewPager.touchChange {
            println("touch y = $it")
        }
        mViewPager.setPageTransformer(false) { page, position ->
            if (position < -1 || position > 1) {
                page.setAlpha(0.3f);
            } else {
                //不透明->半透明
                if (position < 0) {//[0,-1]
                    val f = max(0.3f + (1 + position) * (1 - 0.3f),0f)
                    page.setAlpha(f)
                } else {//[1,0]
                    //半透明->不透明
                    val f = min(0.3f + (1 - position) * (1 - 0.3f),1f)
                    page.setAlpha(f)

                }
            }

        }
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            private var mViewPagerIndex = 0
            private var mState = 0

            override fun onPageScrollStateChanged(state: Int) {
                mState = state
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> {
                        mViewPagerIndex = mViewPager.currentItem
                        handler.removeCallbacks(myRun)
                        val view: View? = adapter.getView(current)
                        viewAnimate(view, false)
                    }
                    ViewPager.SCROLL_STATE_SETTLING -> {

                        myRun.setView(adapter, true)
                        handler.postDelayed(myRun, 500)
                    }
                    ViewPager.SCROLL_STATE_IDLE -> {
                        val view: View? = adapter.getView(current)
                        if ((current == adapter.count - 1) || current == 0) {
                            viewAnimate(view, true)
                        }
                    }
                }

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val count = mViewPager.childCount - 1
                val length: Float = (position + positionOffset) / count
//                println("fffffffff ${length}")
//                if (mViewPagerIndex == position && mViewPagerIndex == 0 && position == 0 && mState == 1) {
//                    if (positionOffsetPixels > 0 && mState == 1) {
//                        println("yumi 向左 ${position + 1} $positionOffset")
//                        setBgColor(
//                            adapter.list[current].color,
//                            adapter.list[position + 1].color,
//                            positionOffset
//                        )
//                    } else {
//                        if (mState == 1) {
//                            println("yumi 向右 ${position}")
//                        }
//
//                    }
//                } else if (mViewPagerIndex == position && mState == 1) {
//                    println("yumi 向左 ${position + 1} $positionOffset")
//                    setBgColor(
//                        adapter.list[current].color,
//                        adapter.list[position + 1].color,
//                        positionOffset
//                    )
//
//                } else if (mViewPagerIndex == mViewPager.childCount - 1 && mState == 1) {
//                    if (positionOffset > 0.9f) {
//                        println("yumi 向左 $mViewPagerIndex")
//
//
//                    } else {
//                        println("yumi 向右 $position")
//                        setBgColor(
//                            adapter.list[current].color,
//                            adapter.list[position].color,
//                            (1.0f - positionOffset)
//                        )
//                    }
//
//                } else {
//                    if (mState == 1) {
//                        println("yumi 向右 ${mViewPagerIndex - 1}")
//                        setBgColor(
//                            adapter.list[mViewPagerIndex].color,
//                            adapter.list[mViewPagerIndex - 1].color,
//                            (1.0f - positionOffset)
//                        )
//                    }
//                }
            }

            override fun onPageSelected(position: Int) {
                current = position;
//                mContainerLayout.setBackgroundColor(adapter.list[current].color)
            }

        })
        mViewPager.setCurrentItem(0, false)
    }

    inner class MyRun : Runnable {
        var flag = false
        var adapter: PageAdapter? = null
        fun setView(adapter: PageAdapter, flag: Boolean) {
            this.adapter = adapter
            this.flag = flag
        }

        override fun run() {
            val view: View? = adapter?.getView(current)
            viewAnimate(view, flag);
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(myRun)
        super.onDestroy()
    }

    fun viewAnimate(view: View?, flag: Boolean) {
        if (view != null) {
            if (isFinishing || isDestroyed) return
            view.starAnimate(flag)
        }
    }

    private fun setBgColor(formColor: Int, toColor: Int, offset: Float) {
        var tf = offset
        if (tf < 0) {
            tf = 1f
        }
        val argbEvaluator = ArgbEvaluator()
        val color = argbEvaluator.evaluate(tf, formColor, toColor) as Int
        mContainerLayout.setBackgroundColor(color)
    }


}