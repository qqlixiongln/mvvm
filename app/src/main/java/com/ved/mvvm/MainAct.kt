package com.ved.mvvm

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.ved.framework.base.BaseActivity
import com.ved.mvvm.databinding.ActivityMain2Binding

class MainAct : BaseActivity<ActivityMain2Binding?, MainViewModel?>() {
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main2
    }
}