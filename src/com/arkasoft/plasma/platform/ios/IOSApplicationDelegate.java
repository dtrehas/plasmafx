package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.foundation.NSDictionary;
import org.robovm.cocoatouch.uikit.UIApplication;
import org.robovm.cocoatouch.uikit.UIApplicationDelegate;
import com.arkasoft.plasma.ApplicationContext;

public class IOSApplicationDelegate extends UIApplicationDelegate.Adapter {

   @Override
   public void didBecomeActive(UIApplication application) {
      System.out.println(">>> didBecomeActive");
   }

   @Override
   public void didEnterBackground(UIApplication application) {
      System.out.println(">>> didEnterBackground");
   }

   @SuppressWarnings("rawtypes")
   @Override
   public boolean didFinishLaunching(UIApplication application, NSDictionary launchOptions) {
      System.out.println(">>> didFinishLaunching: " + launchOptions);
      return true;
   }

   @Override
   public void didFinishLaunching(UIApplication application) {
      System.out.println(">>> didFinishLaunching");
   }

   @Override
   public void willEnterForeground(UIApplication application) {
      System.out.println(">>> willEnterForeground");
   }

   @Override
   @SuppressWarnings("rawtypes")
   public boolean willFinishLaunching(UIApplication application, NSDictionary launchOptions) {
      System.out.println(">>> willFinishLaunching: " + launchOptions);
      return true;
   }

   @Override
   public void willResignActive(UIApplication application) {
      System.out.println(">>> willResignActive");
   }

   @Override
   public void willTerminate(UIApplication application) {
      // check if mail loop
      System.out.println(">>> willTerminate");
      IOSApplicationContext appContext = (IOSApplicationContext) ApplicationContext.getInstance();
      appContext._callApplicationStop();
   }
}
