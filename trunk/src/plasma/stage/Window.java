package plasma.stage;

import com.arkasoft.plasma.ui.StagePeer;
import com.arkasoft.plasma.ui.StagePeerListener;

import plasma.beans.Property;
import plasma.beans.ReadOnlyProperty;
import plasma.event.EventTarget;

/**
 * A top level window within which a scene is hosted, and with which the user
 * interacts. A Window might be a {@link Stage}, {@link PopupWindow}, or other
 * such top level. A Window is used also for browser plug-in based deployments.
 */
public class Window implements EventTarget {

   protected Window() {
   }
   
   /**
    * The peer of this Window.
    */
   protected StagePeer _peer = null;
   
   /**
    * 
    */
   protected StagePeerListener peerListener = null;

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

               _visibleChanging(newVisible);
               
               if (newVisible) {
                  _hasBeenVisible = true;
                  // windowQueue.add(Window.this);
               } else {
                  // windowQueue.remove(Window.this);
               }

               if (_peer != null) {
                  if (newVisible) {
                     // TODO peerListener

                     // Setup listener for changes coming back from peer
                     _peer.setPeerListener(peerListener);
                     // Register pulse listener
                     // TODO ...

                     // TODO init scene

                     _peer.setVisible(true);
                     // TODO fire WINDOW_SHOWN
                  }  else {
                     _peer.setVisible(false);
                     
                     // Call listener
                     // TODO fire WINDOW_HIDDEN
                     
                     // init scene
                     
                     // Remove listener for changes coming back from peer
                     _peer.setPeerListener(null);
                     
                     // Notify peer
                     _peer.close();
                  }
               }

               _visibleChanged(newVisible);
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
   
   protected void _visibleChanging(boolean value) {
      // TODO 
   }
   
   protected void _visibleChanged(boolean value) {
      assert _peer != null;
      if (!value) {
         // TODO peerListener = null;
         _peer = null;
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

}
