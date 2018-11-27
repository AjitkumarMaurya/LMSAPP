// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserProfileFragment_ViewBinding implements Unbinder {
  private UserProfileFragment target;

  private View view2131296318;

  private View view2131296309;

  private View view2131296333;

  @UiThread
  public UserProfileFragment_ViewBinding(final UserProfileFragment target, View source) {
    this.target = target;

    View view;
    target.profilepic = Utils.findRequiredViewAsType(source, R.id.userpic_dilog, "field 'profilepic'", CircularImageView.class);
    target.name = Utils.findRequiredViewAsType(source, R.id.tvname_dilog, "field 'name'", TextView.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.tvemail_dilog, "field 'email'", TextView.class);
    target.ldate = Utils.findRequiredViewAsType(source, R.id.tvldate_dilog, "field 'ldate'", TextView.class);
    target.bio = Utils.findRequiredViewAsType(source, R.id.tvdesc_dilog, "field 'bio'", TextView.class);
    target.cirtificate = Utils.findRequiredViewAsType(source, R.id.tv_compcirti_course, "field 'cirtificate'", TextView.class);
    target.incompCourse = Utils.findRequiredViewAsType(source, R.id.tv_incomp_course, "field 'incompCourse'", TextView.class);
    target.compCourse = Utils.findRequiredViewAsType(source, R.id.tv_comp_course, "field 'compCourse'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_setting_dialog, "method 'clicksetting'");
    view2131296318 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clicksetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_logout_dialog, "method 'clicklogout'");
    view2131296309 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clicklogout();
      }
    });
    view = Utils.findRequiredView(source, R.id.close_dialog, "method 'clickclose'");
    view2131296333 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickclose();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.profilepic = null;
    target.name = null;
    target.email = null;
    target.ldate = null;
    target.bio = null;
    target.cirtificate = null;
    target.incompCourse = null;
    target.compCourse = null;

    view2131296318.setOnClickListener(null);
    view2131296318 = null;
    view2131296309.setOnClickListener(null);
    view2131296309 = null;
    view2131296333.setOnClickListener(null);
    view2131296333 = null;
  }
}
