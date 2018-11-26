package jp.egg.fried.soundrecorderlight.utility.extension

import android.support.v4.app.Fragment
import jp.egg.fried.soundrecorderlight.presentation.presenter.navigation.Navigator


val Fragment.navigator: Navigator?
    get() {
        (this.activity as? Navigator.HasNavigator)?.let {
            return it.navigator
        }
        return null
    }