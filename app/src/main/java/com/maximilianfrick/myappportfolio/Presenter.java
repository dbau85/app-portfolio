package com.maximilianfrick.myappportfolio;

import android.content.Context;
import android.support.annotation.CallSuper;

public abstract class Presenter<V> {
   private Context context;
   private V view;

   @CallSuper
   protected void bindView(V view, Context context) {
      this.view = view;
      this.context = context;
   }

   public Context getContext() {
      return context;
   }

   public V getView() {
      return view;
   }

   @CallSuper
   protected void unbindView() {
      view = null;
   }
}
