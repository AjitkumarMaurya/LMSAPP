// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StartCourseFragment_ViewBinding implements Unbinder {
  private StartCourseFragment target;

  @UiThread
  public StartCourseFragment_ViewBinding(StartCourseFragment target, View source) {
    this.target = target;

    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.start_course_container, "field 'linearLayout'", LinearLayout.class);
    target.rootlin = Utils.findRequiredViewAsType(source, R.id.lin6_lay, "field 'rootlin'", LinearLayout.class);
    target.nestedScrollView = Utils.findRequiredViewAsType(source, R.id.nestedView, "field 'nestedScrollView'", ScrollView.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_unit_title, "field 'tvTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StartCourseFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linearLayout = null;
    target.rootlin = null;
    target.nestedScrollView = null;
    target.tvTitle = null;
  }
}
