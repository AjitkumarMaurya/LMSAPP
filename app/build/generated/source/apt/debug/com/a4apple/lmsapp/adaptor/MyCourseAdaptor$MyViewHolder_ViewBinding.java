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

public class MyCourseAdaptor$MyViewHolder_ViewBinding implements Unbinder {
  private MyCourseAdaptor.MyViewHolder target;

  @UiThread
  public MyCourseAdaptor$MyViewHolder_ViewBinding(MyCourseAdaptor.MyViewHolder target,
      View source) {
    this.target = target;

    target.courseimg = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'courseimg'", ImageView.class);
    target.cname = Utils.findRequiredViewAsType(source, R.id.textViewCourseName, "field 'cname'", TextView.class);
    target.catname = Utils.findRequiredViewAsType(source, R.id.textViewCategory, "field 'catname'", TextView.class);
    target.status = Utils.findRequiredViewAsType(source, R.id.textViewCourseStatus, "field 'status'", TextView.class);
    target.courseId = Utils.findRequiredViewAsType(source, R.id.textViewCourseId, "field 'courseId'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyCourseAdaptor.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.courseimg = null;
    target.cname = null;
    target.catname = null;
    target.status = null;
    target.courseId = null;
  }
}
