package jp.egg.fried.soundrecorderlight.presentation.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.presentation.navigation.Navigator
import jp.egg.fried.soundrecorderlight.presentation.navigation.NavigatorImpl

class MainActivity : AppCompatActivity()
        , Navigator.HasNavigator{

    //region: override Navigator.HasNavigator
    override lateinit var navigator: Navigator
    //endregion


    //region: override AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Navigatorの取得と初期化
        navigator = NavigatorImpl(this)
        navigator.navigationView = findViewById(R.id.navigation)
        navigator.onCreate(applicationContext)
        navigator.start()
    }
    //endregion
}
