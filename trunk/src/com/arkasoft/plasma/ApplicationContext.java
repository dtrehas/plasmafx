package com.arkasoft.plasma;

import java.util.concurrent.atomic.AtomicBoolean;

import plasma.application.Application;
import plasma.stage.Stage;
import plasma.stage.StageStyle;

import com.arkasoft.plasma.ui.NodePeer;
import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.StagePeer;

public abstract class ApplicationContext {

   protected static ApplicationContext instance = null;

   public static ApplicationContext getInstance() {
      if (instance != null) {
         return instance;
      }
      throw new IllegalStateException("ApplicationContext not initialized");
   }

   // Application reference
   private Application application = null;

   // Ensure that launchApplication method is only called once
   private AtomicBoolean launchCalled = new AtomicBoolean(false);

   protected final void launchApplication(final Class<? extends Application> applicationClass, final String... applicationArgs) {
      if (launchCalled.getAndSet(true)) {
         throw new IllegalStateException("Application launch must not be called more than once");
      }

      try {
         _launchApplication(applicationClass, applicationArgs);
      } catch (RuntimeException rte) {
         throw rte;
      } catch (Throwable t) {
         throw new RuntimeException(t);
      }
   }

   private void _launchApplication(Class<? extends Application> applicationClass, String[] applicationArgs) {
      // TODO startup properties

      // Create the display host...

      // Load the application
      try {
         application = applicationClass.newInstance();
      } catch (InstantiationException e) {
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }

      if (application != null) {
         callApplicationInit();
         syncExec(new Runnable() {
            @Override
            public void run() {
               try {
                  callApplicationStart();
               } finally {
                  callApplicationStop();
               }
            }
         });
      }
   }

   private void callApplicationInit() {
      try {
         // Call the application init method (on the Launcher thread)
         application.init();
      } catch (Throwable t) {
         System.err.println("Exception in Application init method");
         t.printStackTrace();
      }
   }

   protected abstract void runEventLoop();

   private void callApplicationStart() {
      try {
         // Create primary stage and call application start method
         final Stage primaryStage = new Stage(true);
         application.start(primaryStage);
         runEventLoop();
      } catch (Throwable t) {
         System.err.println("Exception in Application start method");
         t.printStackTrace();
      }
   }

   protected void callApplicationStop() {
      try {
         // Call Application stop method
         application.stop();
      } catch (Throwable t) {
         System.err.println("Exception in Application stop method");
         t.printStackTrace();
      }
   }

   /**
    * Causes the {@code run()} method of the runnable to be invoked by the
    * user-interface thread at the next reasonable opportunity. The caller of
    * this method continues to run in parallel, and is not notified when the
    * runnable has completed.
    * 
    * @param runnable
    *           code to run on the user-interface thread or {@code null}
    */
   protected abstract void runAsync(final Runnable runnable);

   /**
    * Causes the {@code run()} method of the runnable to be invoked by the
    * user-interface thread at the next reasonable opportunity. The thread which
    * calls this method is suspended until the runnable completes.
    * 
    * @param launchable
    *           code to run on the user-interface thread or {@code null}
    */
   protected abstract void syncExec(Runnable launchable);

   /**
    * 
    * @param style
    * @param primary
    * @return
    */
   public abstract StagePeer createStagePeer(StageStyle style, boolean primary);

   /**
    * 
    * @return
    */
   public abstract ScenePeer createScenePeer();

   /**
    * 
    * @return
    */
   public abstract NodePeer createGroupPeer();

   public boolean hasTouch() {
      return false;
   }

   public boolean hasMultiTouch() {
      return false;
   }

}
