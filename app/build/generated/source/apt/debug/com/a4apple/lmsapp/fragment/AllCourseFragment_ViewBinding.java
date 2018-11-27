// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllCourseFragment_ViewBinding implements Unbinder {
  private AllCourseFragment target;

  private View view2131296409;

  @UiThread
  public AllCourseFragment_ViewBinding(final AllCourseFragment target, View source) {
    this.target = target;

    View view;
    target.allCourserecyclerView = Utils.findRequiredViewAsType(source, R.id.all_course_recycler, "field 'allCourserecyclerView'", RecyclerView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe_container_all_course, "field 'refreshLayout'", SwipeRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_allCourse_noData, "field 'imageView' and method 'clickimg'");
    target.imageView = Utils.castView(view, R.id.iv_allCourse_noData, "field 'imageView'", ImageView.class);
    view2131296409 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickimg();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AllCourseFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.allCourserecyclerView = null;
    target.refreshLayout = null;
    target.imageView = null;

    view2131296409.setOnClickListener(null);
    view2131296409 = null;
  }
}
