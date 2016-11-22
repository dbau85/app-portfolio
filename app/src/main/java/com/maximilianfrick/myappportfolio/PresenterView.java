package com.maximilianfrick.myappportfolio;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class PresenterView<V, P extends Presenter<V>> extends FrameLayout {

   private final Class<P> presenterClass;
   private P presenter;

   public PresenterView(Context context, AttributeSet attrs, int defStyleAttr,
         Class<P> presenterClass) {
      super(context, attrs, defStyleAttr);
      this.presenterClass = presenterClass;
      initialize(context);
   }

   public PresenterView(Context context, Class<P> presenterClass) {
      super(context);
      this.presenterClass = presenterClass;
      initialize(context);
   }

   public PresenterView(Context context, AttributeSet attrs, Class<P> presenterClass) {
      super(context, attrs);
      this.presenterClass = presenterClass;
      initialize(context);
   }

   public P getPresenter() {
      return presenter;
   }

   @CallSuper
   protected void initialize(Context context) {
      //...
   }

   @Override
   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      bindPresenter();
   }

   @Override
   protected void onDetachedFromWindow() {
      unbindPresenter();
      super.onDetachedFromWindow();
   }

   @Override
   protected final void onFinishInflate() {
      super.onFinishInflate();
      setupViews();
   }

   @CallSuper
   protected void setupViews() {

   }

   @SuppressWarnings ("unchecked")
   private void bindPresenter() {
      presenter = (P) PresenterFactory.newPresenter(presenterClass);
      presenter.bindView((V) this, getContext());
   }

   private void unbindPresenter() {
      presenter.unbindView();
   }
}
