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

public class MyCourseFragment_ViewBinding implements Unbinder {
  private MyCourseFragment target;

  private View view2131296416;

  @UiThread
  public MyCourseFragment_ViewBinding(final MyCourseFragment target, View source) {
    this.target = target;

    View view;
    target.myCourserecyclerView = Utils.findRequiredViewAsType(source, R.id.my_course_recycler, "field 'myCourserecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.iv_myCourse_noData, "field 'imageView' and method 'clickimg'");
    target.imageView = Utils.castView(view, R.id.iv_myCourse_noData, "field 'imageView'", ImageView.class);
    view2131296416 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickimg();
      }
    });
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe_container_my_course, "field 'refreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyCourseFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.myCourserecyclerView = null;
    target.imageView = null;
    target.refreshLayout = null;

    view2131296416.setOnClickListener(null);
    view2131296416 = null;
  }
}
