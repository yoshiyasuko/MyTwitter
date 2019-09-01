package jp.egg.fried.soundrecorderlight.presentation.navigation

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.presentation.view.navigation.NavigationFragment
import jp.egg.fried.soundrecorderlight.utility.extension.show
import java.io.Serializable


class NavigatorImpl(private val activity: AppCompatActivity)
    : Navigator() {

    //region: properties
    private val navigationFragments = mutableListOf<NavigationFragment>()
    private var currentTab: Tab? = null
    private val currentNavigationFragment: NavigationFragment? get() = currentTab?.let {
        navigationFragments[it.ordinal]
    }
    private var isStarted: Boolean = false
    private var isVisibleNavigationView: Boolean = true
    //endregion


    //region: initializer
    init {
        navigationFragments.add(NavigationFragment().setTab(Tab.FILE))
        navigationFragments.add(NavigationFragment().setTab(Tab.RECORD))
        navigationFragments.add(NavigationFragment().setTab(Tab.SETTING))
    }
    //endregion


    //region: override Navigator
    override val currentFragment: Fragment? get() = currentNavigationFragment?.currentFragment

    override var navigationView: BottomNavigationView? = null
        set(value) {
            field = value
            setupNavigationView()
        }

    override fun onCreate(context: Context) {
        // 以前起動していたFragmentを全て削除する
        val transaction = activity.supportFragmentManager.beginTransaction()
        activity.supportFragmentManager.fragments.forEach {
            transaction.remove(it)
        }
        transaction.commit()
    }

    override fun start() {
        if (isStarted) return

        isStarted = true

        // 一旦ファイルタブのFragmentをcurrentFragmentに載せる必要があるので、ファイル→録音へと遷移させる
        changeTabVisible(Tab.RECORD)
        changeNavigationFragment(Tab.FILE)
        changeNavigationFragment(Tab.RECORD)
    }

    override fun onBackKey(): Boolean {
        if (currentNavigationFragment?.onBackKey() == true) {
            return true
        }
        return popFragment(currentNavigationFragment?.getPopToBackStackName())
    }

    override fun changeTab(tab: Tab, popToRoot: Boolean, option: Serializable?) {
        changeTabVisible(tab)
        changeTabFragment(tab, popToRoot, option)
    }

    override fun pushFragment(fragment: Fragment, backStackName: String?, animation: Boolean) {
        currentNavigationFragment?.pushFragment(fragment, backStackName, animation)
    }

    override fun popFragment(backStackName: String?, animation: Boolean): Boolean {
        if (currentNavigationFragment?.popFragment(backStackName) == true) {
            return true
        }
        return false
    }

    override fun popToRoot() {
        currentNavigationFragment?.popToRoot()
    }

    override fun changeNavigationViewVisible(visible: Boolean) {
        val navigationView = navigationView ?: return
        if (isVisibleNavigationView != visible) {
            navigationView.show(visible, animation = true, changeVisibility = true)
            isVisibleNavigationView = visible
        }
    }
    //endregion


    //region: private methods
    @SuppressLint("RestrictedApi")
    private fun setupNavigationView() {

        // BottomNavigationViewのShiftingModeをonにする
        navigationView?.let { navigationView ->
            val menuView = navigationView.getChildAt(0) as BottomNavigationMenuView
            (0 until menuView.childCount).forEach {
                val itemView = menuView.getChildAt(it) as BottomNavigationItemView
                itemView.setShifting(true)
            }
        }

        // BottomNavigationItemをタップした時の処理をリスナにセットする
        navigationView?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_file -> {
                    changeTabFragment(Tab.FILE)
                    true
                }
                R.id.navigation_record -> {
                    changeTabFragment(Tab.RECORD)
                    true
                }
                R.id.navigation_setting -> {
                    changeTabFragment(Tab.SETTING)
                    true
                }
                else -> false
            }
        }
    }

    private fun changeTabFragment(tab: Tab, popToRoot: Boolean = false, option: Serializable? = null) {
        navigationView?.translationY = 0.0f

        val needPopToRoot = if (currentTab != tab) {
            changeNavigationFragment(tab, option)
            popToRoot
        } else {
            option?.let {
                currentNavigationFragment?.setFragmentOption(it)
            }
            true
        }

        if (needPopToRoot) {
            // タブのトップのFragmentに遷移する
            navigationFragments[tab.ordinal].popToRoot()
        }
    }

    private fun changeNavigationFragment(tab: Tab, option: Serializable? = null) {
        val newFragment = navigationFragments[tab.ordinal]
        val currentFragment = currentNavigationFragment
        val currentTab = this.currentTab

        currentFragment?.onBackStackPause()
        // オプションの設定
        option?.let {
            newFragment.setFragmentOption(it)
        }
        newFragment.onBackStackResume()

        // 新しいFragmentへの遷移処理
        val transaction = activity.supportFragmentManager.beginTransaction()
//        currentTab?.let {
//            when {
//                it.ordinal > tab.ordinal -> {
//                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
//                }
//                it.ordinal < tab.ordinal -> {
//                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
//                }
//                else -> Unit
//            }
//        }

        if (tab != currentTab && currentFragment != null) {
            transaction.hide(currentFragment)
        }

        if (newFragment.isAdded) {
            transaction.show(newFragment)
        } else {
            transaction.add(R.id.container, newFragment)
        }

        transaction.commit()

        this.currentTab = tab
    }

    private fun changeTabVisible(tab: Tab) {
        // BottomNavigationViewのタブの選択状態を切り替える
        navigationView?.let {
            val menu = it.menu
            val menuItem = menu.getItem(tab.ordinal)
            menuItem.setChecked(true)
        }
    }
    //endregion
}