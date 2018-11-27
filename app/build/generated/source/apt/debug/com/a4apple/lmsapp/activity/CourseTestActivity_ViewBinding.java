// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CourseTestActivity_ViewBinding implements Unbinder {
  private CourseTestActivity target;

  private View view2131296414;

  @UiThread
  public CourseTestActivity_ViewBinding(CourseTestActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CourseTestActivity_ViewBinding(final CourseTestActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.courseIndex = Utils.findRequiredViewAsType(source, R.id.tv_course_test_num, "field 'courseIndex'", TextView.class);
    target.courseTimer = Utils.findRequiredViewAsType(source, R.id.tv_course_test_time_num, "field 'courseTimer'", TextView.class);
    target.ansBtn = Utils.findRequiredViewAsType(source, R.id.btn_ans_test, "field 'ansBtn'", Button.class);
    target.linearLayoutContainer = Utils.findRequiredViewAsType(source, R.id.container_test, "field 'linearLayoutContainer'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_back_home_tstart, "method 'clickback'");
    view2131296414 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickback();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CourseTestActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.courseIndex = null;
    target.courseTimer = null;
    target.ansBtn = null;
    target.linearLayoutContainer = null;

    view2131296414.setOnClickListener(null);
    view2131296414 = null;
  }
}
