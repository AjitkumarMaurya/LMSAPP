// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131296308;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.email = Utils.findRequiredViewAsType(source, R.id.etLoginUserEmail, "field 'email'", MaterialEditText.class);
    target.pass = Utils.findRequiredViewAsType(source, R.id.etLoginUserPassword, "field 'pass'", MaterialEditText.class);
    view = Utils.findRequiredView(source, R.id.btn_login, "method 'clicklogin'");
    view2131296308 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clicklogin();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.email = null;
    target.pass = null;

    view2131296308.setOnClickListener(null);
    view2131296308 = null;
  }
}
