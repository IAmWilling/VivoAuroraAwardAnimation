package com.zhy.jiguangjiang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import com.zhy.jiguangjiang.starAnimate

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PageAdapter
    private val handler = Handler(Looper.myLooper()!!)
    private val myRun = MyRun()
    private var current: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = mutableListOf("a", "b", "c", "a", "b", "c", "a", "b", "c")
        adapter = PageAdapter(this, list)
        mViewPager.pageMargin = 15
        mViewPager.adapter = adapter
        mViewPager.offscreenPageLimit = list.size
        mViewPager.setPageTransformer(false) { page, position ->
            if (position < -1 || position > 1) {
                page.setAlpha(0.5f);
            } else {
                //不透明->半透明
                if (position < 0) {//[0,-1]
                    page.setAlpha(0.5f + (1 + position) * (1 - 0.5f));
                } else {//[1,0]
                    //半透明->不透明
                    page.setAlpha(0.5f + (1 - position) * (1 - 0.5f));
                }
            }

        }
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> {
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

            }

            override fun onPageSelected(position: Int) {
                current = position;

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


    class ZoomOutPageTransformer : ViewPager.PageTransformer {
        private  val MIN_SCALE = 0.85f
        private  val MIN_ALPHA = 0.5f
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }

}