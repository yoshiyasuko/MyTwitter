package jp.egg.fried.soundrecorderlight.utility.extension

import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.setUpToolbar() {
    supportActionBar?.apply {
        setIcon(null)
        setDisplayHomeAsUpEnabled(false)

    }
}