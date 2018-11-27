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

public class MatchPairAdaptor$MyHolderMatchPair_ViewBinding implements Unbinder {
  private MatchPairAdaptor.MyHolderMatchPair target;

  @UiThread
  public MatchPairAdaptor$MyHolderMatchPair_ViewBinding(MatchPairAdaptor.MyHolderMatchPair target,
      View source) {
    this.target = target;

    target.one = Utils.findRequiredViewAsType(source, R.id.tv_one, "field 'one'", TextView.class);
    target.two = Utils.findRequiredViewAsType(source, R.id.tv_two, "field 'two'", TextView.class);
    target.close = Utils.findRequiredViewAsType(source, R.id.iv_close, "field 'close'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MatchPairAdaptor.MyHolderMatchPair target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.one = null;
    target.two = null;
    target.close = null;
  }
}
