package com.zhy.jiguangjiang

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * View顶层扩展函数
 */
fun View.starAnimate(flag: Boolean) {
    if ((this is ConstraintLayout) && id == R.id.adapter_container) {
        val cardView = findViewById<CardView>(R.id.cardview).apply { cardElevation = 10f }
        val centerLayout = findViewById<LinearLayout>(R.id.center_container)
        val headerContainer = findViewById<CardView>(R.id.header_image_container).apply{cardElevation = 10f}
        val footerLayout = findViewById<View>(R.id.footer_container)

        cardView.animate().setDuration(300).scaleY(if (flag) 1f else 0.7f).setInterpolator(
            DecelerateInterpolator()
        )
            .start()
        centerLayout.animate().setDuration(300).translationY(if (flag) 0f else 100f)
            .setInterpolator(DecelerateInterpolator()).start()
        headerContainer.animate().setDuration(300).translationY(if (flag) 0f else 120f)
            .setInterpolator(DecelerateInterpolator()).start()
        footerLayout.animate().setDuration(200).alpha(if (flag) 1f else 0f)
            .translationY(if (flag) 0f else -100f)
            .setInterpolator(DecelerateInterpolator()).start()
    }
}