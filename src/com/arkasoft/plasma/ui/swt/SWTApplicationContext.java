package com.arkasoft.plasma.ui.swt;

import org.eclipse.swt.widgets.Display;

import plasma.application.Application;
import plasma.stage.StageStyle;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.NodePeer;
import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.StagePeer;

public class SWTApplicationContext extends ApplicationContext {

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
      SWTApplicationContext appContext = new SWTApplicationContext();
      ApplicationContext.instance = appContext;
      appContext.launchApplication(applicationClass, applicationArgs);
   }

   private SWTApplicationContext() {
      // nothing
   }

   @Override
   protected final void launchApplication(final Class<? extends Application> applicationClass, final String... applicationArgs) {
      super.launchApplication(applicationClass, applicationArgs);
   }

   private SWTWindow primaryStagePeer = null;
   
   @Override
   protected void runEventLoop() {
      if (primaryStagePeer != null && primaryStagePeer.isVisible()) {
         Display display = Display.getDefault();
         while (!primaryStagePeer.shell.isDisposed()) {
            if (!display.readAndDispatch()) {
               display.sleep();
            }
         }
      }
   }

   @Override
   protected void runAsync(final Runnable runnable) {
      Display display = Display.getDefault();
      display.asyncExec(runnable);
   }

   @Override
   protected void syncExec(final Runnable runnable) {
      Display display = Display.getDefault();
      display.syncExec(runnable);
   }

   @Override
   public StagePeer createStagePeer(StageStyle style, boolean primary) {
      SWTWindow stagePeer = new SWTWindow(style, primary);
      if (primary) {
         primaryStagePeer = stagePeer;
      }
      return stagePeer;
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

}
