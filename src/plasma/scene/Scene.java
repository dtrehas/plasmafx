package plasma.scene;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.StagePeer;

import plasma.event.EventTarget;
import plasma.stage.Window;

/**
 * The {@code Scene} class is the container for all content in a scene graph.
 */
public class Scene implements EventTarget {

   public Scene(Parent root) {

   }

   /**
    * The peer of this scene.
    */
   private ScenePeer _peer = null;

   private void _peer_init(Window window, StagePeer windowPeer) {
      assert _peer == null;
      assert window != null;

      if (windowPeer == null) {
         return;
      }
      
      _peer = ApplicationContext.getInstance().createScenePeer();
   }

}
