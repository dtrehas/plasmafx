package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.foundation.NSAutoreleasePool;
import org.robovm.cocoatouch.foundation.NSDictionary;
import org.robovm.cocoatouch.uikit.UIApplication;

import plasma.application.Application;
import plasma.scene.Group;
import plasma.scene.Scene;
import plasma.stage.Stage;

public class IOSDemo extends IOSApplicationDelegate {

   public static class MyApp extends Application {

      public void init() throws Exception {
         System.out.println(">>> init");
      }

      @Override
      public void start(Stage primaryStage) throws Exception {
         System.out.println(">>> start");
         Scene scene = new Scene(new Group());
         
         primaryStage.setScene(scene);
         primaryStage.show();
      }

      public void stop() throws Exception {
         System.out.println(">>> stop");
      };
   }

   @SuppressWarnings("rawtypes")
   @Override
   public boolean didFinishLaunching(UIApplication application,
         NSDictionary launchOptions) {
      super.didFinishLaunching(application, launchOptions);
      
//      NSRunLoop main = NSRunLoop.getMain();
//      NSRunLoop current = NSRunLoop.getCurrent();
//      System.out.println(main.getHandle() + " " + current.getHandle());

      Thread launchThread = new Thread() {
         @Override
         public void run() {
            IOSApplicationContext.launch(MyApp.class);
         }
      };
      launchThread.setDaemon(true);
      launchThread.start();

      return true;
   }

   public static void main(String[] args) throws Exception {
      System.setProperty("plasma.platform", "ios");

      NSAutoreleasePool pool = new NSAutoreleasePool();
      UIApplication.main(args, null, IOSDemo.class);
      pool.drain();
   }
}
