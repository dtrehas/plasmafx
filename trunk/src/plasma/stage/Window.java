package plasma.stage;

import plasma.beans.Property;
import plasma.beans.ReadOnlyProperty;
import plasma.event.EventTarget;
import plasma.scene.Scene;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.StagePeer;
import com.arkasoft.plasma.ui.StagePeerListener;

/**
 * A top level window within which a scene is hosted, and with which the user
 * interacts. A Window might be a {@link Stage}, {@link PopupWindow}, or other
 * such top level. A Window is used also for browser plug-in based deployments.
 */
public class Window implements EventTarget {

   protected Window() {
   }

   /**
    * The {@code Scene} to be rendered on this {@code Stage}. TODO
    */
   private ReadOnlyProperty<Scene> scene;

   protected void setScene(Scene value) {
      ((StageReadOnlyProperty<Scene>) sceneProperty()).setValue(value);
   }

   public final Scene getScene() {
      return scene == null ? null : scene.getValue();
   }

   public final ReadOnlyProperty<Scene> sceneProperty() {
      if (scene == null) {
         scene = new StageReadOnlyProperty<Scene>(null) {
            private Scene oldScene;

            @SuppressWarnings("deprecation")
            @Override
            protected void invalidated() {
               ApplicationContext.getInstance().checkUserThread();

               final Scene newScene = getValue();
               // First, detach scene peer from this window
               updatePeerScene(null);
               // Second, dispose scene peer
               if (oldScene != null) {
                  oldScene.impl_SetWindow(null);
                  // TODO StyleManager
               }
               if (newScene != null) {
                  final Window oldWindow = newScene.getWindow();
                  if (oldWindow != null) {
                     // if the new scene was previously set to a window
                     // we need to remove it from that window
                     oldWindow.setScene(null);
                  }

                  // Set the "window" on the new scene. This will also trigger
                  // scene's peer creation.
                  newScene.impl_SetWindow(Window.this);
                  // Set scene impl on stage impl
                  updatePeerScene(newScene.impl_getPeer());

                  if (isShowing()) {
                     // TODO

                     // if (!widthExplicit || !heightExplicit) {
                     // TODO
                     // }
                  }
               }

               oldScene = newScene;
            }

            @Override
            public String getName() {
               return "scene";
            }

            private void updatePeerScene(ScenePeer scenePeer) {
               if (impl_peer != null) {
                  // Set scene impl on stage impl
                  impl_peer.setScene(scenePeer);
               }
            }
         };
      }
      return scene;
   }

   /**
    * Whether or not this {@code Window} is showing (that is, open on the user's
    * system}.
    */
   private Property<Boolean> showing;

   private final void setShowing(boolean value) {
      // checkUserThread();
      ((Property<Boolean>) showingProperty()).setValue(value);
   }

   public final boolean isShowing() {
      return showing == null ? false : showing.getValue();
   }

   public final ReadOnlyProperty<Boolean> showingProperty() {
      if (showing == null) {
         showing = new StageProperty<Boolean>(false) {
            @Override
            protected void invalidated() {
               final boolean newVisible = getValue();

               if (newVisible) {
                  // TODO fire WINDOW_SHOWING
               } else {
                  // TODO fire WINDOW_HIDING
               }

               impl_visibleChanging(newVisible);

               if (newVisible) {
                  _hasBeenVisible = true;
                  // windowQueue.add(Window.this);
               } else {
                  // windowQueue.remove(Window.this);
               }

               if (impl_peer != null) {
                  if (newVisible) {
                     if (peerListener == null) {
                        // peerListener = ...
                        throw new IllegalStateException();
                     }

                     // Setup listener for changes coming back from peer
                     impl_peer.setPeerListener(peerListener);
                     // Register pulse listener
                     // TODO ...

                     if (getScene() != null) {
                        getScene().impl_initPeer();
                        impl_peer.setScene(getScene().impl_getPeer());
                        // getScene().impl_preferredSize();
                     }

                     // Set peer bounds
                     // if ((getScene() != null) && (!widthExplicit))

                     // set peer bounds before the window is shown
                     // applyBounds();

                     // TODO impl_peer.setOpacity();

                     impl_peer.setVisible(true);
                     // TODO fire WINDOW_SHOWN
                  } else {
                     impl_peer.setVisible(false);

                     // Call listener
                     // TODO fire WINDOW_HIDDEN

                     // init scene

                     // Remove listener for changes coming back from peer
                     impl_peer.setPeerListener(null);

                     // Notify peer
                     impl_peer.close();
                  }
               }

               impl_visibleChanged(newVisible);
            }

            @Override
            public String getName() {
               return "showing";
            }
         };
      }
      return showing;
   }

   // flag indicating whether this window has ever been made visible
   private boolean _hasBeenVisible = false;

   protected final boolean hasBeenVisible() {
      return _hasBeenVisible;
   }

   /**
    * This can be replaced by listening for the onShowing/onHiding events
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   protected void impl_visibleChanging(boolean value) {
      // TODO
   }

   /**
    * This can be replaced by listening for the onShown/onHidden events
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   protected void impl_visibleChanged(boolean value) {
      assert impl_peer != null;
      if (!value) {
         // TODO peerListener = null;
         impl_peer = null;
      }
   }

   /**
    * Attempts to show this Window by setting visibility to {@code true}.
    * 
    * @throws IllegalStateException
    *            if this method is called on a thread other than the Application
    *            Thread.
    */
   public final void show() {
      setShowing(true);
   }

   /**
    * Attempts to hide this Window by setting the visibility to {@code false}.
    * 
    * @throws IllegalStateException
    *            if this method is called on a thread other than the Application
    *            Thread.
    */
   public void close() {
      setShowing(false);
   }

   protected abstract class StageProperty<T> extends Property<T> {
      public StageProperty(T value) {
         super(value);
      }

      @Override
      public Object getBean() {
         return Window.this;
      }
   }

   protected abstract class StageReadOnlyProperty<T> extends ReadOnlyProperty<T> {
      public StageReadOnlyProperty(T value) {
         super(value);
      }

      @Override
      protected final void setValue(T value) {
         super.setValue(value);
      }

      @Override
      public Object getBean() {
         return Window.this;
      }
   }

   // -------------------------------------------------------------------------
   //
   // Methods for synchronizing state from this Node to its _peer.
   //
   // -------------------------------------------------------------------------

   /**
    * The peer of this Window.
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   protected StagePeer impl_peer = null;

   /**
    * Get Stage's peer.
    * <p>
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   public StagePeer impl_getPeer() {
      return impl_peer;
   }

   /**
    * 
    */
   protected StagePeerListener peerListener = null;

}
