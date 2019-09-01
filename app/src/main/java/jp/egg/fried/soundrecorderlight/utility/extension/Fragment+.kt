package jp.egg.fried.soundrecorderlight.utility.extension

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.presentation.navigation.Navigator


val Fragment.navigator: Navigator?
    get() {
        (this.activity as? Navigator.HasNavigator)?.let {
            return it.navigator
        }
        return null
    }

/**
 * @param titleRes ツールバーのタイトル
 * @param isEnableBackButton option:trueならナビゲーションで戻るが有効化
 * @param navigationIconRes option:ナビゲーションアイコンに適応するアイコン
 * @param navigationIconClickListener ナビアイコンをクリックした時のリスナー
 */
fun Fragment.updateToolbar(
    @StringRes titleRes: Int,
    isEnableBackButton: Boolean,
    @DrawableRes navigationIconRes: Int? = null,
    navigationIconClickListener: (() -> Unit)? = null
) {
    updateToolbar(
        getString(titleRes),
        isEnableBackButton,
        navigationIconRes,
        navigationIconClickListener
    )
}

/**
 * @param title ツールバーのタイトル
 * @param isEnableBackButton option:trueならナビゲーションで戻るが有効化
 * @param navigationIconRes option:ナビゲーションアイコンに適応するアイコン
 * @param navigationIconClickListener ナビアイコンをクリックした時のリスナー
 */
fun Fragment.updateToolbar(
    title: String,
    isEnableBackButton: Boolean,
    @DrawableRes navigationIconRes: Int? = null,
    navigationIconClickListener: (() -> Unit)? = null
) {

    (view?.findViewById<Toolbar?>(R.id.commonToolbar))?.let {
        if (navigationIconRes != null) it.navigationIcon =
            ContextCompat.getDrawable(requireActivity(), navigationIconRes)
        (activity as? AppCompatActivity)?.setSupportActionBar(it)
        it.setNavigationOnClickListener {
            navigationIconClickListener?.invoke()
        }
    }

    (view?.findViewById<Toolbar?>(R.id.toolbar))?.let {
        if (navigationIconRes != null) it.navigationIcon =
            ContextCompat.getDrawable(requireActivity(), navigationIconRes)
        (activity as? AppCompatActivity)?.setSupportActionBar(it)
        it.setNavigationOnClickListener {
            navigationIconClickListener?.invoke()
        }
    }

    updateToolbarTitle(title)
    if (isEnableBackButton) updateNavigationButton(isEnableBackButton)
}

private fun Fragment.updateToolbarTitle(title: String) {

    (activity as? AppCompatActivity)?.supportActionBar?.title = title
}

private fun Fragment.updateNavigationButton(isEnableBackButton: Boolean) {

    (activity as? AppCompatActivity)?.supportActionBar?.let {
        it.setDisplayHomeAsUpEnabled(isEnableBackButton)
        it.setHomeButtonEnabled(isEnableBackButton)
        it.setDisplayHomeAsUpEnabled(isEnableBackButton)
    }
}