package com.ved.mvvm

import android.os.Bundle
import com.ved.framework.base.BaseActivity
import com.ved.mvvm.databinding.ActivityMainBinding

class MainActivity2 : BaseActivity<ActivityMainBinding?, MainViewModel?>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        super.initData()
    }
}