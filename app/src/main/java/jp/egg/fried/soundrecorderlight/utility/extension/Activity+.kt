package jp.egg.fried.soundrecorderlight.utility.extension

import android.support.v7.app.AppCompatActivity


fun AppCompatActivity.setUpToolbar() {
    supportActionBar?.apply {
        setIcon(null)
        setDisplayHomeAsUpEnabled(false)

    }
}