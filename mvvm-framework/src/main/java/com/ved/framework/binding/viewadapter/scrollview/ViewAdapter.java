package com.ved.framework.binding.viewadapter.scrollview;

import com.ved.framework.binding.command.BindingCommand;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.BindingAdapter;

/**
 * Created by ved on 2017/6/18.
 */
public final class ViewAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter({"onScrollChangeCommand"})
    public static void onScrollChangeCommand(final NestedScrollView nestedScrollView, final BindingCommand<NestScrollDataWrapper> onScrollChangeCommand) {
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrolved, scrollY, oldScrolved, oldScrollY) -> {
            if (onScrollChangeCommand != null) {
                onScrollChangeCommand.execute(new NestScrollDataWrapper(scrolved, scrollY, oldScrolved, oldScrollY));
            }
        });
    }

    public static class ScrollDataWrapper {
        public float scrolved;
        public float scrollY;

        public ScrollDataWrapper(float scrolved, float scrollY) {
            this.scrolved = scrolved;
            this.scrollY = scrollY;
        }
    }

    public static class NestScrollDataWrapper {
        public int scrolved;
        public int scrollY;
        public int oldScrolved;
        public int oldScrollY;

        public NestScrollDataWrapper(int scrolved, int scrollY, int oldScrolved, int oldScrollY) {
            this.scrolved = scrolved;
            this.scrollY = scrollY;
            this.oldScrolved = oldScrolved;
            this.oldScrollY = oldScrollY;
        }
    }
}
