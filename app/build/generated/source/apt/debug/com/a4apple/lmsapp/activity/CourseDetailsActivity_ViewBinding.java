// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CourseDetailsActivity_ViewBinding implements Unbinder {
  private CourseDetailsActivity target;

  private View view2131296623;

  private View view2131296412;

  @UiThread
  public CourseDetailsActivity_ViewBinding(CourseDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CourseDetailsActivity_ViewBinding(final CourseDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.viewpager_desc = Utils.findRequiredViewAsType(source, R.id.viewpager_desc, "field 'viewpager_desc'", ViewPager.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.tab_layout_desc = Utils.findRequiredViewAsType(source, R.id.tab_layout_desc, "field 'tab_layout_desc'", TabLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_startCourse, "field 'tv_startCourse' and method 'click'");
    target.tv_startCourse = Utils.castView(view, R.id.tv_startCourse, "field 'tv_startCourse'", TextView.class);
    view2131296623 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click();
      }
    });
    target.unitDuration = Utils.findRequiredViewAsType(source, R.id.tv_unit_duration, "field 'unitDuration'", TextView.class);
    target.catNameTv = Utils.findRequiredViewAsType(source, R.id.tv_cdetail_cat_name, "field 'catNameTv'", TextView.class);
    target.courseNameTv = Utils.findRequiredViewAsType(source, R.id.tv_cdetail_course_name, "field 'courseNameTv'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_back_home_desc, "method 'clickback'");
    view2131296412 = view;
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
    CourseDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewpager_desc = null;
    target.toolbar = null;
    target.tab_layout_desc = null;
    target.tv_startCourse = null;
    target.unitDuration = null;
    target.catNameTv = null;
    target.courseNameTv = null;

    view2131296623.setOnClickListener(null);
    view2131296623 = null;
    view2131296412.setOnClickListener(null);
    view2131296412 = null;
  }
}
