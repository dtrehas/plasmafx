package plasma.stage;

import java.util.concurrent.atomic.AtomicBoolean;

import plasma.beans.Property;
import plasma.scene.Scene;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.StagePeerListener;

/**
 * The {@code Stage} class is the top level container. The primary Stage is
 * constructed by the platform. Additional Stage objects may be constructed by
 * the application.
 */
public class Stage extends Window {

   // Ensure that launchApplication method is only called once
   private static AtomicBoolean primaryStage = new AtomicBoolean(false);

   private final class StagePeerListenerImpl implements StagePeerListener {
      @Override
      public void changedLocation(float x, float y) {
         System.out.println(">>> changedLocation: " + x + ", " + y);
      }

      @Override
      public void changedSize(float width, float height) {
         System.out.println(">>> changedSize: " + width + ", " + height);
      }

      @Override
      public void changedIconified(boolean iconified) {
         System.out.println(">>> changedIconified: " + iconified);
      }

      @Override
      public void changedMaximized(boolean maximized) {
         System.out.println(">>> changedMaximized: " + maximized);
      }

      @Override
      public void changedResizable(boolean resizable) {
         System.out.println(">>> changedResizable: " + resizable);
      }

      @Override
      public void changedFullscreen(boolean fs) {
         System.out.println(">>> changedFullscreen: " + fs);
      }

      @Override
      public void closing() {
         System.out.println(">>> closing");
         close();
      }

      @Override
      public void closed() {
         System.out.println(">>> closed");
      }
   };

   public Stage() {
      this(StageStyle.DECORATED);
   }

   public Stage(StageStyle style) {
      this(style, false);
   }

   public Stage(boolean primaryStage) {
      this(StageStyle.DECORATED, primaryStage);
   }

   public Stage(StageStyle style, boolean primaryStage) {
      super();

      if (primaryStage) {
         if (Stage.primaryStage.getAndSet(true)) {
            throw new IllegalStateException("The primary Stage is constructed by the platform.");
         }
         initPrimary(primaryStage);
      }

      // ApplicationContext.getInstance().checkUserThread();

      initStyle(style);
   }

   @Override
   public final void setScene(Scene value) {
      super.setScene(value);
   }

   /**
    * 
    */
   private boolean primary; // default is set in constructor

   private void initPrimary(boolean primary) {
      this.primary = primary;
   }

   /**
    * Returns whether this stage is the primary stage.
    * 
    * @return {@code true} if this stage is the primary stage for the
    *         application.
    */
   boolean isPrimary() {
      return primary;
   }

   /**
    * 
    */
   private StageStyle style; // default is set in constructor

   private void initStyle(StageStyle style) {
      // TODO hasBeenVisible
      this.style = style;
   }

   /**
    * Retrieves the style attribute for this stage.
    * 
    * @return the stage style.
    */
   public final StageStyle getStyle() {
      return style;
   }

   /**
    * 
    */
   private Window owner = null;

   private void initOwner(Window owner) {
      // TODO hasBeenVisible

      if (isPrimary()) {
         throw new IllegalStateException("Cannot set owner for the primary stage");
      }

      this.owner = owner;
      // TODO setup scene
   }

   /**
    * Retrieves the owner Window for this stage, or null for an unowned stage.
    * 
    * @return the owner Window.
    */
   public final Window getOwner() {
      return owner;
   }

   /**
    * Defines the title of the {@code Stage}.
    */
   private Property<String> title;

   public final void setTitle(String value) {
      titleProperty().setValue(value);
   }

   public final String getTitle() {
      return title == null ? null : title.getValue();
   }

   public final Property<String> titleProperty() {
      if (title == null) {
         title = new StageProperty<String>(null) {
            @Override
            protected void invalidated() {
               if (impl_peer != null) {
                  impl_peer.setTitle(getValue());
               }
            }

            @Override
            public String getName() {
               return "title";
            }
         };
      }
      return title;
   }

   /**
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   @Override
   protected void impl_visibleChanging(boolean value) {
      super.impl_visibleChanging(value);
      if (value && (impl_peer == null)) {
         // Setup the peer
         // TODO

         impl_peer = ApplicationContext.getInstance().createStagePeer(getStyle(), isPrimary() /* TODO */);
         if (peerListener == null) {
            peerListener = new StagePeerListenerImpl();
         }
      }
   }

   @SuppressWarnings("deprecation")
   @Override
   protected void impl_visibleChanged(boolean value) {
      super.impl_visibleChanged(value);

      if (value) {
         // finish initialization
         impl_peer.setTitle(getTitle());

      } else {

      }
   }

}
