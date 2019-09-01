package jp.egg.fried.soundrecorderlight.utility.extension

import android.content.Context
import android.util.TypedValue


/**
 * Context Extensions
 *
 * Created by Yoshiyasu on 2019-09-01
 */


/**
 * DpをPx値に変換する
 *
 * @param dp 設定したいDpを指定する
 * @return 指定Dpに対応するPx値
 */
fun Context.convertDpToPx(dp: Float): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources.displayMetrics
    )