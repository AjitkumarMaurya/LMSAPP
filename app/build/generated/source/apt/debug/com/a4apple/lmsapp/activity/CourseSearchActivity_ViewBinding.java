// Generated code from Butter Knife. Do not modify!
package com.a4apple.lmsapp.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.a4apple.lmsapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CourseSearchActivity_ViewBinding implements Unbinder {
  private CourseSearchActivity target;

  private View view2131296648;

  private View view2131296410;

  @UiThread
  public CourseSearchActivity_ViewBinding(CourseSearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CourseSearchActivity_ViewBinding(final CourseSearchActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar_search, "field 'toolbar'", Toolbar.class);
    target.relativeLayout = Utils.findRequiredViewAsType(source, R.id.rely, "field 'relativeLayout'", RelativeLayout.class);
    target.recySearchView = Utils.findRequiredViewAsType(source, R.id.search_recyclerview, "field 'recySearchView'", RecyclerView.class);
    target.searchText = Utils.findRequiredViewAsType(source, R.id.etsearch, "field 'searchText'", MaterialEditText.class);
    view = Utils.findRequiredView(source, R.id.userProfilesearch, "field 'profilepic' and method 'clickprofile'");
    target.profilepic = Utils.castView(view, R.id.userProfilesearch, "field 'profilepic'", CircularImageView.class);
    view2131296648 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickprofile();
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_back_home, "method 'clickBack'");
    view2131296410 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickBack();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CourseSearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.relativeLayout = null;
    target.recySearchView = null;
    target.searchText = null;
    target.profilepic = null;

    view2131296648.setOnClickListener(null);
    view2131296648 = null;
    view2131296410.setOnClickListener(null);
    view2131296410 = null;
  }
}
