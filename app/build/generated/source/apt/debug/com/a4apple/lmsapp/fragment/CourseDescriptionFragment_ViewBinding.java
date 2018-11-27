// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CourseDescriptionFragment_ViewBinding implements Unbinder {
  private CourseDescriptionFragment target;

  @UiThread
  public CourseDescriptionFragment_ViewBinding(CourseDescriptionFragment target, View source) {
    this.target = target;

    target.courseDesc = Utils.findRequiredViewAsType(source, R.id.tv_course_description, "field 'courseDesc'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CourseDescriptionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.courseDesc = null;
  }
}
