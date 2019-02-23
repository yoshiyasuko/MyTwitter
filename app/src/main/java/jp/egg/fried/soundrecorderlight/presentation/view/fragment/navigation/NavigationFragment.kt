package jp.egg.fried.soundrecorderlight.presentation.view.fragment.navigation

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.databinding.FragmentNavigationBinding
import jp.egg.fried.soundrecorderlight.presentation.presenter.navigation.Navigator
import jp.egg.fried.soundrecorderlight.presentation.view.fragment.file.FileFragment
import jp.egg.fried.soundrecorderlight.presentation.view.fragment.record.RecordFragment
import jp.egg.fried.soundrecorderlight.presentation.view.fragment.setting.SettingFragment
import jp.egg.fried.soundrecorderlight.utility.extension.navigator
import java.io.Serializable


class NavigationFragment : Fragment() {

    companion object {
        const val KEY_FRAGMENT_OPTION = "FRAGMENT_OPTION"
        const val KEY_TAB = "KEY_TAB"
    }

    //region: property
    val currentFragment: Fragment?
        get() = if (childFragmentManager.fragments.isEmpty()) {
            null
        } else {
            childFragmentManager.fragments.first()
        }
    private val delayExecutionQueue: MutableList<Runnable> = mutableListOf()

    private lateinit var binding: FragmentNavigationBinding
    //endregion


    //region: override Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        childFragmentManager.addOnBackStackChangedListener {
            val fragment = childFragmentManager.fragments.last()
            onFragmentResume(fragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_navigation, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (currentFragment == null) {
            val fragment = createRootFragment()
            pushFragment(fragment, backStackName = Navigator.ROOT_BACK_STACK_NAME, animation = false)

            // 生成前に呼ばれたpushFragmentをここで実行する
            delayExecutionQueue.forEach {
                it.run()
            }
            delayExecutionQueue.clear()
        }
    }
    //endregion


    //region: public methods
    fun pushFragment(fragment: Fragment, backStackName: String?, animation: Boolean = true) {

        // NavigationFragmentがActivityにアタッチされてない時は、生成後に呼び出すキューに詰める
        if (!isAdded) {
            delayExecutionQueue.add(Runnable {
                pushFragment(fragment, backStackName, animation)
            })
            return
        }

        val usingBackStackName = if (fragment is Navigator.NavigationChild && backStackName == null) {
            fragment.getBackStackName()
        } else {
            backStackName
        }

        onFragmentPause(currentFragment)

        val transaction = childFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        // トランジションアニメーションは使わない
        fragment.enterTransition = null
        currentFragment?.enterTransition = null

        transaction.replace(R.id.navigation_child, fragment)
        transaction.addToBackStack(usingBackStackName)
        transaction.commit()
    }

    fun setTab(tab: Navigator.Tab): NavigationFragment {
        arguments = Bundle().apply {
            putSerializable(KEY_TAB, tab)
        }
        return this
    }

    fun onBackStackResume() {
        if (isAdded) {
            onFragmentResume(currentFragment)
        }
    }

    fun onBackStackPause() {
        if (isAdded) {
            onFragmentPause(currentFragment)
        }
    }

    fun setFragmentOption(option: Serializable) {
        arguments?.putSerializable(KEY_FRAGMENT_OPTION, option)
    }

    fun popToRoot() {
        if (isAdded) {
            childFragmentManager.popBackStack(Navigator.ROOT_BACK_STACK_NAME, 0)
        }
    }

    fun onBackKey(): Boolean {
        val fragment = currentFragment
        if (fragment is Navigator.NavigationChild) {
            return fragment.onBackKey()
        }
        return false
    }

    fun getPopToBackStackName(): String? {
        val fragment = currentFragment
        if (fragment is Navigator.NavigationChild) {
            return fragment.getPopToBackStackName()
        }
        return null
    }

    fun popFragment(backStackName: String? = null): Boolean {
        val backStackCount = childFragmentManager.backStackEntryCount
        if (backStackCount > 1) {
            onFragmentPause(currentFragment)
            childFragmentManager.popBackStack(backStackName, 0)
            return true
        }
        return false
    }
    //endregion


    //region: private methods
    private fun onFragmentResume(fragment: Fragment?) {
        val navigationViewVisible = if (fragment is Navigator.NavigationChild) {
            arguments?.getSerializable(KEY_FRAGMENT_OPTION)?.let {
                if (fragment.setNavigationOption(it)) {
                    arguments?.putSerializable(KEY_FRAGMENT_OPTION, null)
                }
            }
            fragment.onBackStackResume()
            fragment.getNavigationViewVisible()
        } else {
            true
        }
        navigator?.changeNavigationViewVisible(navigationViewVisible)
    }

    private fun onFragmentPause(fragment: Fragment?) {
        if (fragment is Navigator.NavigationChild) {
            fragment.onBackStackPause()
        }

        // キーボードを閉じる
        val activity = activity ?: return
        if (activity.currentFocus != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun createRootFragment(): Fragment {
        val value = arguments?.getSerializable(KEY_TAB)
        return when (value as Navigator.Tab) {
            Navigator.Tab.FILE      -> FileFragment()
            Navigator.Tab.SETTING   -> SettingFragment()
            else                    -> RecordFragment()
        }
    }
    //endregion
}