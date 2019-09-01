package jp.egg.fried.soundrecorderlight.presentation.view.item.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.utility.extension.convertDpToPx


/**
 * SimpleItemDecoration
 *
 * RecyclerViewの各Itemへの
 *  ・マージンの設定
 *  ・境界線の描画
 * を行うItemDecoration
 *
 * @param topMarginDp 上側のマージン（dp）
 * @param bottomMarginDp 下側のマージン（dp）
 * @param startMarginDp 左側のマージン（dp）
 * @param endMarginDp 右側のマージン（dp）
 * @param dividerStroke 境界線の色と太さを指定する
 *
 * Created by Yoshiyasu on 2019-09-01
 */

class SimpleItemDecoration(
    private val topMarginDp: Int = 0,
    private val bottomMarginDp: Int = 0,
    private val startMarginDp: Int = 0,
    private val endMarginDp: Int = 0,
    private val dividerStroke: DividerStroke? = null
) : RecyclerView.ItemDecoration() {

    //region: override RecyclerView.ItemDecoration
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        // Itemにマージンを設定する
        with(outRect) {
            top = view.context.convertDpToPx(topMarginDp.toFloat()).toInt()
            bottom = view.context.convertDpToPx(bottomMarginDp.toFloat()).toInt()
            left = view.context.convertDpToPx(startMarginDp.toFloat()).toInt()
            right = view.context.convertDpToPx(endMarginDp.toFloat()).toInt()
        }
    }

    /**
     * マージンが0dpに設定された場合、onDraw()だと境界線がViewの後方に隠れてしまうので、onDrawOver()で描画する
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // 境界線を描画する
        dividerStroke?.let { stroke ->
            val dividerPaint = Paint().apply {
                color = ContextCompat.getColor(parent.context, stroke.colorResId)
                strokeWidth = parent.context.convertDpToPx(stroke.widthDp.toFloat())
            }

            val layoutManager = parent.layoutManager ?: return@let

            // 2個目のViewから上部に境界線を描画する
            (1 until parent.childCount).forEach { position ->
                val view = parent.getChildAt(position)

                val startX = view.context.convertDpToPx(stroke.startMarginDp.toFloat())
                val stopX = parent.right - view.context.convertDpToPx(stroke.endMarginDp.toFloat())
                val positionY = layoutManager.getDecoratedTop(view).toFloat()

                c.drawLine(startX, positionY, stopX, positionY, dividerPaint)
            }
        }

    }
    //endregion


    data class DividerStroke(
        val widthDp: Int = 1,
        val startMarginDp: Int = 0,
        val endMarginDp: Int = 0,
        @ColorRes val colorResId: Int = R.color.lightGray
    )

}