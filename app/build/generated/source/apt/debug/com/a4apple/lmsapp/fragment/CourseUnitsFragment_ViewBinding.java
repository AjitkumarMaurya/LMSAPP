// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CourseUnitsFragment_ViewBinding implements Unbinder {
  private CourseUnitsFragment target;

  @UiThread
  public CourseUnitsFragment_ViewBinding(CourseUnitsFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.courseUnits_recycler, "field 'recyclerView'", ListView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.swipe_refresh_layout_unit, "field 'refreshLayout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CourseUnitsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.refreshLayout = null;
  }
}
