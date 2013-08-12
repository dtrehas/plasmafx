package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.coregraphics.CGRect;
import org.robovm.cocoatouch.uikit.UIColor;
import org.robovm.cocoatouch.uikit.UIView;

import com.arkasoft.plasma.ui.GroupPeer;
import com.arkasoft.plasma.ui.base.View;

public class IOSView extends View implements GroupPeer {

   protected UIView view;
   
   public IOSView() {
      view = new UIView(new CGRect(0, 0, 100, 100));
      view.setBackgroundColor(UIColor.brownColor());
   }

   @Override
   public void dispose() {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void release() {
      // TODO Auto-generated method stub
      
   }

}
