package jp.egg.fried.soundrecorderlight

import android.app.Application
import com.squareup.picasso.Picasso
import jp.egg.fried.soundrecorderlight.module.viewModelModule
import org.koin.android.ext.android.startKoin
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * MainApplication
 *
 * Created by Yoshiyasu on 2019-09-01
 */

class MainApplication : Application() {

    //region: override Application
    override fun onCreate() {
        super.onCreate()

        // DIの設定
        startKoin(
            this,
            listOf(
                viewModelModule
            )
        )

        // Picassoで画像をローディング可能にする
        Picasso.get().isLoggingEnabled = true

        // デフォルトフォントの設定
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NotoSansJP-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
    //endregion

}