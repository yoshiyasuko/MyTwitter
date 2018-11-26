package jp.egg.fried.soundrecorderlight.utility.extension

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import jp.egg.fried.soundrecorderlight.R

fun BottomNavigationView.show(show: Boolean, animation: Boolean, changeVisibility: Boolean) {
    val containerView = (parent as? ViewGroup)?.findViewById<View>(R.id.container_base)
    val navigationHeight = height

    if (animation) {
        val duration = duration(context)

        if (changeVisibility && show) {
            visibility = View.VISIBLE
        }

        // BottomNavigationViewの表示切り替え
        clearAnimation()
        animate().translationY(if (show) 0f else navigationHeight.toFloat()).duration = duration

        // containerViewのbottomMarginを調整する
        containerView?.also { view ->
            object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    val lp = view.layoutParams as FrameLayout.LayoutParams
                    lp.bottomMargin = (if (show) navigationHeight * interpolatedTime else navigationHeight * (1.0f - interpolatedTime)).toInt()
                    view.layoutParams = lp
                }
            }.also { anim ->
                anim.duration = duration
                if (changeVisibility && !show) {
                    anim.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationEnd(p0: Animation?) {
                            visibility = View.GONE
                        }
                        override fun onAnimationRepeat(p0: Animation?) = Unit
                        override fun onAnimationStart(p0: Animation?) = Unit
                    })
                }
                view.startAnimation(anim)
            }
        }

    } else {
        // BottomNavigationViewの表示切り替え
        translationY = if (show) 0f else navigationHeight.toFloat()

        // BottomNavigationViewを表示する
        containerView?.also { view ->
            val lp = view.layoutParams as FrameLayout.LayoutParams
            lp.bottomMargin = if (show) navigationHeight else 0
            view.layoutParams = lp
        }

        // visibilityを変更
        if (changeVisibility) {
            visibility = if (show) View.VISIBLE else View.GONE
        }
    }

}

private fun duration(context: Context): Long =
        context.resources.getInteger(R.integer.bottom_navigation_view_slide_duration).toLong()
