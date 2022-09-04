package com.ved.framework.binding.viewadapter.viewgroup;

/**
 * Created by ved on 2017/6/15.
 */

import androidx.databinding.ViewDataBinding;

public interface IBindingItemViewModel<V extends ViewDataBinding> {
    void injecDataBinding(V binding);
}
