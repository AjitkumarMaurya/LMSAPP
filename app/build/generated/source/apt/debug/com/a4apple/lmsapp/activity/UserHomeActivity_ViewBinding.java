// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserHomeActivity_ViewBinding implements Unbinder {
  private UserHomeActivity target;

  private View view2131296647;

  private View view2131296417;

  @UiThread
  public UserHomeActivity_ViewBinding(UserHomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserHomeActivity_ViewBinding(final UserHomeActivity target, View source) {
    this.target = target;

    View view;
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'mViewPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.userProfile, "field 'profilepic' and method 'clickprofile'");
    target.profilepic = Utils.castView(view, R.id.userProfile, "field 'profilepic'", CircularImageView.class);
    view2131296647 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickprofile();
      }
    });
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_search, "method 'clickSearch'");
    view2131296417 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickSearch();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserHomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.profilepic = null;
    target.toolbar = null;
    target.tabLayout = null;

    view2131296647.setOnClickListener(null);
    view2131296647 = null;
    view2131296417.setOnClickListener(null);
    view2131296417 = null;
  }
}
