package jp.egg.fried.soundrecorderlight.presentation.navigation

import android.content.Context
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import java.io.Serializable


abstract class Navigator {

    enum class Tab {
        FILE,
        RECORD,
        SETTING
    }

    companion object {
        const val ROOT_BACK_STACK_NAME = "ROOT_BACK_STACK_NAME"
    }

    abstract var navigationView: BottomNavigationView?
    abstract val currentFragment: Fragment?

    abstract fun onCreate(context: Context)
    abstract fun start()
    abstract fun onBackKey(): Boolean

    abstract fun changeTab(tab: Tab, popToRoot: Boolean = false, option: Serializable? = null)
    abstract fun pushFragment(fragment: Fragment, backStackName: String? = null, animation: Boolean = true)
    abstract fun popFragment(backStackName: String? = null, animation: Boolean = true): Boolean
    abstract fun popToRoot()

    abstract fun changeNavigationViewVisible(visible: Boolean)

    interface HasNavigator {
        val navigator: Navigator
    }

    interface NavigationChild {
        // バックキー時の動作を独自に実装する
        fun onBackKey(): Boolean = false
        // バックスタック名(他のFragmentからこのFragmentに戻る時に指定する名前)
        fun getBackStackName(): String? = null
        // BottomNavigationViewを表示するか
        fun getNavigationViewVisible() = true
        // バックキーを押した際の戻り先のバックスタック名(nullなら直前のFragment)
        fun getPopToBackStackName(): String? = null
        // バックスタックから復帰(表示される)時に呼び出される
        fun onBackStackResume() = Unit
        // バックスタックへ入る(非表示になる)時に呼び出される
        fun onBackStackPause()= Unit
        // 呼び出し元から渡されるオプション
        fun setNavigationOption(option: Serializable): Boolean = false
    }
}