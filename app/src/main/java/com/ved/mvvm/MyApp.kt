package com.ved.mvvm

import com.ved.framework.base.BaseApplication
import java.io.File

class MyApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        FILE_DIR = File(filesDir, "tbs").absolutePath + File.separator
    }

    companion object {
        var FILE_DIR: String? = null
    }
}