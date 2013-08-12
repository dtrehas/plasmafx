package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.uikit.UIColor;
import org.robovm.cocoatouch.uikit.UIScreen;
import org.robovm.cocoatouch.uikit.UIView;
import org.robovm.cocoatouch.uikit.UIViewController;
import org.robovm.cocoatouch.uikit.UIWindow;

import plasma.stage.StageStyle;

import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.base.Window;

public class IOSWindow extends Window {

   private boolean resizable;

   UIWindow window;

   public IOSWindow(StageStyle style, boolean primary) {
      System.out.println("IOSWindow: " + primary);
      if (primary) {
         UIScreen screen = UIScreen.getMainScreen();
         window = new UIWindow(screen.getBounds());
         setResizable(false);

         UIView hostView = new UIView();
         hostView.setAutoresizesSubviews(false);
         window.addSubview(hostView);
         window.setRootViewController(new UIViewController() {

         });
         window.getRootViewController().setView(hostView);

         window.setBackgroundColor(UIColor.blueColor());
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public boolean isResizable() {
      return resizable;
   }

   public void setResizable(boolean resizable) {
      this.resizable = resizable;
   }

   @Override
   public void setTitle(String title) {
      window.getRootViewController().setTitle(title);
   }

   public boolean isVisible() {
      return window != null && !window.isHidden();
   }

   @Override
   public void setVisible(boolean value) {
      System.out.println("setVisible: " + value);
      window.setHidden(!value);
   }

   @Override
   public void close() {
      throw new UnsupportedOperationException();
   }

   private ScenePeer scenePeer;

   @Override
   public synchronized void setScene(ScenePeer scenePeer) {
      if (this.scenePeer != null) {
         throw new IllegalStateException();
      }

      if (scenePeer != null) {
         this.scenePeer = scenePeer;
         IOSScene scene = (IOSScene) scenePeer;
         window.getRootViewController().getView().addSubview(scene.view);
      } else if (this.scenePeer != null) {
         IOSScene scene = (IOSScene) this.scenePeer;
         scene.view.removeFromSuperview();
         this.scenePeer = null;
      }
   }
}
