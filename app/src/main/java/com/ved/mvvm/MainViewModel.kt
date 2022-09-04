package com.ved.mvvm

import android.app.Application
import androidx.databinding.ObservableField
import com.ved.framework.base.BaseModel
import com.ved.framework.base.BaseViewModel
import com.ved.framework.binding.command.BindingAction
import com.ved.framework.binding.command.BindingCommand

class MainViewModel(application: Application) : BaseViewModel<BaseModel?>(application) {
    var title = ObservableField<String>("我的第一个页面")
    var backCommand = BindingCommand<Void>(BindingAction { finish() })
}