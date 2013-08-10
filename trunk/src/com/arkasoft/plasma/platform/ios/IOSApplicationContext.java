package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.uikit.UIApplication;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.Selector;
import org.robovm.rt.VM;

import plasma.application.Application;
import plasma.stage.StageStyle;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.NodePeer;
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
   public static void launch(IOSApplicationDelegate appDelegate, final Class<? extends Application> applicationClass, final String... applicationArgs) {
      assert ApplicationContext.instance == null : "ApplicationContext already initialized";
      IOSApplicationContext appContext = new IOSApplicationContext(appDelegate);
      ApplicationContext.instance = appContext;
      appContext.launchApplication(applicationClass, applicationArgs);
   }

   private final IOSApplicationDelegate appDelegate;

   private Thread dispatchThread;

   private IOSApplicationContext(IOSApplicationDelegate appDelegate) {
      this.appDelegate = appDelegate;
   }

   @Override
   protected void runEventLoop() {

   }

   @Override
   protected void runAsync(Runnable runnable) {
      IOSRunnable r = new IOSRunnable(runnable);
      r.performOnMainThread(false);
   }

   @Override
   protected void syncExec(Runnable runnable) {
      IOSRunnable r = new IOSRunnable(runnable);
      r.performOnMainThread(true);
   }

   @Override
   public StagePeer createStagePeer(StageStyle style, boolean primary) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ScenePeer createScenePeer() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public NodePeer createGroupPeer() {
      // TODO Auto-generated method stub
      return null;
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
