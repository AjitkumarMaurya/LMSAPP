// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CourseStartActivity_ViewBinding implements Unbinder {
  private CourseStartActivity target;

  private View view2131296411;

  @UiThread
  public CourseStartActivity_ViewBinding(CourseStartActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CourseStartActivity_ViewBinding(final CourseStartActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.btnNext = Utils.findRequiredViewAsType(source, R.id.btn_next_start_course, "field 'btnNext'", Button.class);
    target.relymain = Utils.findRequiredViewAsType(source, R.id.rely_main2, "field 'relymain'", RelativeLayout.class);
    target.tvnext = Utils.findRequiredViewAsType(source, R.id.tv_next_step_course, "field 'tvnext'", TextView.class);
    target.ivnext = Utils.findRequiredViewAsType(source, R.id.next_step_course, "field 'ivnext'", ImageView.class);
    target.courseNameTv = Utils.findRequiredViewAsType(source, R.id.tv_cstart_course_name, "field 'courseNameTv'", TextView.class);
    target.courseCatNameTv = Utils.findRequiredViewAsType(source, R.id.tv_cstart_cat_name, "field 'courseCatNameTv'", TextView.class);
    target.prev = Utils.findRequiredViewAsType(source, R.id.tv_prev_step_course, "field 'prev'", TextView.class);
    target.previv = Utils.findRequiredViewAsType(source, R.id.prev_step_course, "field 'previv'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.iv_back_home_cstart, "method 'clickback'");
    view2131296411 = view;
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
    CourseStartActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.btnNext = null;
    target.relymain = null;
    target.tvnext = null;
    target.ivnext = null;
    target.courseNameTv = null;
    target.courseCatNameTv = null;
    target.prev = null;
    target.previv = null;

    view2131296411.setOnClickListener(null);
    view2131296411 = null;
  }
}
