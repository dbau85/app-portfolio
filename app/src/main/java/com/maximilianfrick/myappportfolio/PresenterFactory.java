package com.maximilianfrick.myappportfolio;

public class PresenterFactory {
   static Presenter<?> newPresenter(Class<?> presenterClass) {
      try {
         return (Presenter<?>) presenterClass.newInstance();
      } catch (Exception e) {
         throw new IllegalStateException(
               "presenter must have public default constructor: " + presenterClass.getName(), e);
      }
   }
}
