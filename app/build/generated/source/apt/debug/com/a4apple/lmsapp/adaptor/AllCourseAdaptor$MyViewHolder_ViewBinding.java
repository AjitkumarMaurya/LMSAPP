// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.adaptor;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllCourseAdaptor$MyViewHolder_ViewBinding implements Unbinder {
  private AllCourseAdaptor.MyViewHolder target;

  @UiThread
  public AllCourseAdaptor$MyViewHolder_ViewBinding(AllCourseAdaptor.MyViewHolder target,
      View source) {
    this.target = target;

    target.courseimg = Utils.findRequiredViewAsType(source, R.id.course_imageView, "field 'courseimg'", ImageView.class);
    target.cname = Utils.findRequiredViewAsType(source, R.id.textViewCourseName, "field 'cname'", TextView.class);
    target.catname = Utils.findRequiredViewAsType(source, R.id.tv_Allcategory_name, "field 'catname'", TextView.class);
    target.enroll = Utils.findRequiredViewAsType(source, R.id.enroll, "field 'enroll'", TextView.class);
    target.courseId = Utils.findRequiredViewAsType(source, R.id.tv_courseId_all_course, "field 'courseId'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AllCourseAdaptor.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.courseimg = null;
    target.cname = null;
    target.catname = null;
    target.enroll = null;
    target.courseId = null;
  }
}
