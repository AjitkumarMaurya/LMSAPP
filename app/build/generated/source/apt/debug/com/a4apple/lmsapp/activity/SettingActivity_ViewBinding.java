// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding implements Unbinder {
  private SettingActivity target;

  private View view2131296549;

  private View view2131296413;

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingActivity_ViewBinding(final SettingActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_setting, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.switch1, "field 'aSwitch' and method 'onCheckedChanged'");
    target.aSwitch = Utils.castView(view, R.id.switch1, "field 'aSwitch'", Switch.class);
    view2131296549 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(Utils.castParam(p0, "onCheckedChanged", 0, "onCheckedChanged", 0, Switch.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_back_home_setting, "method 'clickback'");
    view2131296413 = view;
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
    SettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.aSwitch = null;

    ((CompoundButton) view2131296549).setOnCheckedChangeListener(null);
    view2131296549 = null;
    view2131296413.setOnClickListener(null);
    view2131296413 = null;
  }
}
