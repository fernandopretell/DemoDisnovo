package com.limapps.base.utils

import android.app.Application
import android.media.AudioManager
import android.media.SoundPool
import com.limapps.base.R
import com.limapps.common.tryOrPrintException

object SoundApp {

    private val soundPool by lazy { SoundPool(6, AudioManager.STREAM_MUSIC, 0) }
    private var loaded = false
    private var drop: Int = 0

    init {
        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            LogUtil.e("onLoadComplete", "status $status sampleId$sampleId")
            loaded = true
        }
    }

    fun drop() {
        start(drop)
    }

    fun start(id: Int) {
        tryOrPrintException {
            if (loaded) {
                soundPool.play(id, 1f, 1f, 1, 0, 1f)
            }
        }
    }

    fun inject(application: Application) {
        drop = soundPool.load(application, R.raw.drop, 1)
    }
}
