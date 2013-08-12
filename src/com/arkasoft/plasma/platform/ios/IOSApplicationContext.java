package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.foundation.NSRunLoop;

import plasma.application.Application;
import plasma.stage.StageStyle;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.GroupPeer;
import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.StagePeer;

public class IOSApplicationContext extends ApplicationContext {

   /**
    * Utility method to make it easier to define {@code main} entry-points into
    * applications.
    * 
    * @param applicationClass
    *           the application class that is constructed and executed by the
    *           launcher.
    * @param applicationArgs
    *           the command line arguments passed to the application.
    */
   public static void launch(final Class<? extends Application> applicationClass, final String... applicationArgs) {
      assert ApplicationContext.instance == null : "ApplicationContext already initialized";
      IOSApplicationContext appContext = new IOSApplicationContext();
      ApplicationContext.instance = appContext;
      appContext.launchApplication(applicationClass, applicationArgs);
   }

   private IOSWindow primaryStagePeer = null;

   private IOSApplicationContext() {
      // nothing
   }

   @Override
   public boolean isUserThread() {
      return NSRunLoop.getCurrent() == NSRunLoop.getMain();
   }

   @Override
   protected void runEventLoop() {
      if (primaryStagePeer != null && primaryStagePeer.isVisible()) {
         primaryStagePeer.window.makeKeyWindow();
      }
   }

   @Override
   protected final void callApplicationStop() {
      // do nothing
   }

   final void _callApplicationStop() {
      super.callApplicationStop();
   }

   @Override
   protected void invokeLater(Runnable runnable) {
      IOSRunnable r = new IOSRunnable(runnable);
      r.performOnMainThread(false);
   }

   @Override
   protected void invokeAndWait(Runnable runnable) {
      IOSRunnable r = new IOSRunnable(runnable);
      r.performOnMainThread(true);
   }

   @Override
   public StagePeer createStagePeer(StageStyle style, boolean primary) {
      IOSWindow window = new IOSWindow(style, primary);
      if (primary) {
         primaryStagePeer = window;
      }
      return window;
   }

   @Override
   public ScenePeer createScenePeer() {
      return new IOSScene();
   }

   @Override
   public GroupPeer createGroupPeer() {
      return new IOSView();
   }

   @Override
   public boolean hasTouch() {
      return true;
   }

   @Override
   public boolean hasMultiTouch() {
      return true;
   }

}
