package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.coregraphics.CGRect;
import org.robovm.cocoatouch.uikit.UIColor;

import com.arkasoft.plasma.ui.GroupPeer;
import com.arkasoft.plasma.ui.NodePeer;
import com.arkasoft.plasma.ui.ScenePeer;

public class IOSScene extends IOSView implements ScenePeer {

   public IOSScene() {
      super();
      view.setBounds(new CGRect(0, 0, 200, 200));
      view.setBackgroundColor(UIColor.redColor());
   }

   @Override
   public void dispose() {
      // TODO Auto-generated method stub

   }

   @Override
   public void entireSceneNeedsRepaint() {
      throw new UnsupportedOperationException();
   }

   @Override
   public void release() {
      view = null;
   }

   private GroupPeer rootPeer;
   
   @Override
   public void setRoot(NodePeer rootPeer) {
      if (this.rootPeer != null) {
         throw new IllegalStateException();
      }
      
      if (rootPeer != null) {
         this.rootPeer = (GroupPeer) rootPeer;
         IOSView root = (IOSView) rootPeer;
         view.addSubview(root.view);
      } else if (this.rootPeer != null) {
         IOSView root = (IOSView) this.rootPeer;
         root.view.removeFromSuperview();
         this.rootPeer = null;
      }
   }

}
