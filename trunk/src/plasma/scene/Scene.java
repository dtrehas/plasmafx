package plasma.scene;

import plasma.beans.Property;
import plasma.beans.ReadOnlyProperty;
import plasma.event.EventTarget;
import plasma.scene.paint.Color;
import plasma.scene.paint.Paint;
import plasma.stage.Window;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.StagePeer;

/**
 * The {@code Scene} class is the container for all content in a scene graph.
 */
public class Scene implements EventTarget {

   private double widthSetByUser = -1D;
   private double heightSetByUser = -1D;
   private boolean sizeInitialized = false;

   /**
    * Creates a {@code Scene} for a specific root {@code Node}.
    * 
    * @param root
    *           The root node of the scene graph TODO
    */
   public Scene(Parent root) {
      this(root, -1, -1, Color.white(), false, false);
   }

   /**
    * Creates a {@code Scene} for a specific root {@code Node} with a specific
    * size.
    * 
    * @param root
    *           The root node of the scene graph
    * @param width
    *           The width of the scene
    * @param height
    *           The height of the scene
    */
   public Scene(Parent root, double width, double height) {
      this(root, width, height, Color.white(), false, false);
   }

   /**
    * Creates a {@code Scene} for a specific root {@code Node} with a fill.
    * 
    * @param root
    *           The root node of the scene graph
    * @param fill
    *           The fill
    */
   public Scene(Parent root, Paint fill) {
      this(root, -1, -1, fill, false, false);
   }

   /**
    * Creates a {@code Scene} for a specific root {@code Node} with a specific
    * size and fill.
    * 
    * @param root
    *           The root node of the scene graph
    * @param width
    *           The width of the scene
    * @param height
    *           The height of the scene
    * @param fill
    *           The fill
    */
   public Scene(Parent root, double width, double height, Paint fill) {
      this(root, width, height, fill, false, false);
   }

   /**
    * Creates a {@code Scene} for a specific root {@code Node}, with a dimension
    * of width and height, specifies whether a depth buffer is created for this
    * scene and specifies whether scene anti-aliasing is requested. size.
    * 
    * @param root
    *           The root node of the scene graph
    * @param width
    *           The width of the scene
    * @param height
    *           The height of the scene
    * @param fill
    *           The fill
    * @param depthBuffer
    *           The depth buffer flag
    * @param antiAliasing
    *           The scene anti-aliasing flag
    */
   public Scene(Parent root, double width, double height, Paint fill, boolean depthBuffer, boolean antiAliasing) {
      if (root == null) {
         throw new NullPointerException("Root cannot be null");
      }

      ApplicationContext.getInstance().checkUserThread();
      setRoot(root);
      if (width >= 0D) {
         widthSetByUser = width;
         setWidth(width);
      }
      if (height >= 0D) {
         heightSetByUser = height;
         setHeight(height);
      }
      sizeInitialized = (widthSetByUser >= 0 && heightSetByUser >= 0);

      // TODO setup mouse handler
      // TODO setup click handler

      // initialized = true

      // setFill(fill);
   }

   /**
    * Defines the root {@code Node} of the scene graph.
    * <p>
    * If a {@code Group} is used as the root, the contents of the scene graph
    * will be clipped by the scene's width and height and changes to the scene's
    * size (if user resizes the stage) will not alter the layout of the scene
    * graph. If a resizable node (layout {@code Region} or {@code Control}) is
    * set as the root, then the root's size will track the scene's size, causing
    * the contents to be relayed out as necessary.
    */
   private Property<Parent> root;

   public final void setRoot(Parent value) {
      rootProperty().setValue(value);
   }

   public final Parent getRoot() {
      return root == null ? null : root.getValue();
   }

   public final Property<Parent> rootProperty() {
      if (root == null) {
         root = new SceneProperty<Parent>(null) {
            private Parent oldRoot;

            @Override
            protected void invalidated() {
               Parent _value = getValue();

               if (_value == null) {
                  if (isBound()) unbind();
                  throw new NullPointerException("Scene's root cannot be null");
               }

               if (_value.getParent() != null) {
                  if (isBound()) unbind();
                  throw new IllegalArgumentException(_value + "is already inside a scene-graph and cannot be set as root");
               }

               // if (_value.getClipParent() != null) {
               // TODO
               // }

               if (_value.getScene() != null && _value.getScene().getRoot() == _value && _value.getScene() != Scene.this) {
                  if (isBound()) unbind();
                  throw new IllegalArgumentException(_value + "is already set as root of another scene");
               }

               if (oldRoot != null) {
                  oldRoot.setScene(null);
                  // oldRoot.setImpl_traversalEngine(null);
               }
               oldRoot = _value;
               // if (_value.getImpl_traversalEngine() == null) {
               // TODO
               // }
               // _value.getStyleClass().add(0, "root");
               _value.setScene(Scene.this);
               // markDirty(DirtyBits.ROOT_DIRTY);
               // TODO _value.resize(getWidth(), getHeight()); // maybe no-op if
               // root is not resizable
               // TODO _value.requestLayout();
            }

            @Override
            public String getName() {
               return "root";
            }
         };
      }
      return root;
   }

   void setNeedsRepaint() {
      if (this.impl_peer != null) {
         impl_peer.entireSceneNeedsRepaint();
      }
   }

   /**
    * The {@code Window} for this {@code Scene}.
    */
   private ReadOnlyProperty<Window> window;

   private void setWindow(Window value) {
      ((SceneReadOnlyProperty<Window>) windowProperty()).setValue(value);
   }

   public final Window getWindow() {
      return window == null ? null : window.getValue();
   }

   public final ReadOnlyProperty<Window> windowProperty() {
      if (window == null) {
         window = new SceneReadOnlyProperty<Window>(null) {
            private Window oldWindow;

            @Override
            public void invalidated() {
               final Window newWindow = getValue();
               // TODO key handler
               if (oldWindow != null) {
                  impl_disposePeer();
               } else {
                  impl_initPeer();
               }
               // TODO
               oldWindow = newWindow;
            }

            @Override
            public String getName() {
               // TODO Auto-generated method stub
               return null;
            }
         };
      }
      return window;
   }

   /**
    * The width of this {@code Scene}.
    */
   private ReadOnlyProperty<Double> width;

   private void setWidth(double value) {
      ((SceneReadOnlyProperty<Double>) widthProperty()).setValue(value);
   }

   public final double getWidth() {
      return width == null ? 0D : width.getValue();
   }

   public final ReadOnlyProperty<Double> widthProperty() {
      if (width == null) {
         width = new SceneReadOnlyProperty<Double>(0D) {
            @Override
            protected void invalidated() {
               // TODO final Parent _root = getRoot();

            }

            @Override
            public String getName() {
               return "width";
            }
         };
      }
      return width;
   }

   /**
    * The height of this {@code Scene}.
    */
   private ReadOnlyProperty<Double> height;

   private void setHeight(double value) {
      ((SceneReadOnlyProperty<Double>) heightProperty()).setValue(value);
   }

   public final double getHeight() {
      return height == null ? 0D : height.getValue();
   }

   public final ReadOnlyProperty<Double> heightProperty() {
      if (height == null) {
         height = new SceneReadOnlyProperty<Double>(0D) {
            @Override
            protected void invalidated() {
               // TODO final Parent _root = getRoot();

            }

            @Override
            public String getName() {
               return "height";
            }
         };
      }
      return height;
   }

   /**
    * A node property.
    * 
    * @param <T>
    */
   protected abstract class SceneProperty<T> extends Property<T> {
      public SceneProperty(T value) {
         super(value);
      }

      @Override
      public Object getBean() {
         return Scene.this;
      }
   }

   /**
    * A read only node property.
    * 
    * @param <T>
    */
   protected abstract class SceneReadOnlyProperty<T> extends ReadOnlyProperty<T> {
      public SceneReadOnlyProperty(T value) {
         super(value);
      }

      @Override
      protected void setValue(T value) {
         super.setValue(value);
      }

      @Override
      public Object getBean() {
         return Scene.this;
      }
   }

   // -------------------------------------------------------------------------
   //
   // Methods for synchronizing state from this Node to its _peer.
   //
   // -------------------------------------------------------------------------

   /**
    * The peer of this scene.
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   private ScenePeer impl_peer;

   /**
    * Get Scene's peer
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   @Deprecated
   public ScenePeer impl_getPeer() {
      return impl_peer;
   }

   /**
    * 
    * @deprecated This is an internal API that is not indented for use and will
    *             be removed in the next version
    */
   public void impl_initPeer() {
      assert impl_peer == null;

      Window window = getWindow();
      // impl_initPeer() is only called from Window, either when the window
      // is being shown, or the window scene is being changed. In any case
      // this scene's window cannot be null.
      assert window != null;

      StagePeer windowPeer = window.impl_getPeer();
      if (windowPeer == null) {
         // This is fine, the window is not visible, inpl_inirPeer() will
         // be called again later, when the window is being shown.
         return;
      }

      impl_peer = ApplicationContext.getInstance().createScenePeer();
      // set listener
      // set paint listener
      System.out.println("------------------1");
      impl_peer.setRoot(getRoot().impl_getPeer());
      System.out.println("------------------2");
      // TODO impl_peer.setFillPaint(getFill());
      // TODO impl_peer.setCamera()
      // TODO impl_peer.markDirty();

      // TODO add scene pulse listener
   }

   // @Override
   public void impl_disposePeer() {
      if (impl_peer == null) {
         // This is fine, the window is either not shown yet and there is no
         // need in disposing scene peer, or is hidden and impl_disposePeer()
         // has already been called.
         return;
      }

      // TODO
      impl_peer.dispose();
      impl_peer = null;
   }

   /**
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   public void impl_SetWindow(Window value) {
      setWindow(value);
   }

}
