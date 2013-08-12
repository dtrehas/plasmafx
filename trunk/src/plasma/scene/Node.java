package plasma.scene;

import com.arkasoft.plasma.ui.NodePeer;

import plasma.beans.Property;
import plasma.beans.ReadOnlyProperty;
import plasma.event.EventTarget;

/**
 * Base class for scene nodes. A scene graph is a set of tree data structures
 * where every item has zero or one parent, and each item is either a "leaf"
 * with zero sub-items or a "branch" with zero or more sub-items.
 */
public abstract class Node implements EventTarget/* , Stylable */{

   /**
    * The peer node.
    */
   private NodePeer peer;

   /**
    * The parent of this {@code Node}. If this {@code Node} has not been added
    * to the scene graph, then parent will be {@code null}.
    */
   private ReadOnlyProperty<Parent> parent;

   final void setParent(Parent value) {
      ((NodeReadOnlyProperty<Parent>) parentProperty()).setValue(value);
   }

   public final Parent getParent() {
      return parent == null ? null : parent.getValue();
   }

   public final ReadOnlyProperty<Parent> parentProperty() {
      if (parent == null) {
         parent = new NodeReadOnlyProperty<Parent>(null) {
            private Parent oldParent;

            @Override
            public void invalidated() {
               if (oldParent != null) {
                  // TODO oldParent.disabledProperty().removeListener()
                  // ...
               }
               // updateDisabled()
               // ...
               final Parent newParent = getValue();
               if (newParent != null) {
                  // TODO
               }

               oldParent = newParent;
            }

            @Override
            public String getName() {
               return "parent";
            }
         };
      }
      return parent;
   }

   /**
    * The {@link Scene} that this {@code Node} is part of. In the Node is not
    * part of a scene, then this variable will be null.
    */
   private ReadOnlyProperty<Scene> scene;

   final void setScene(Scene value) {
      ((NodeReadOnlyProperty<Scene>) sceneProperty()).setValue(value);
   }

   public final Scene getScene() {
      return scene == null ? null : scene.getValue();
   }

   public final ReadOnlyProperty<Scene> sceneProperty() {
      if (scene == null) {
         scene = new NodeReadOnlyProperty<Scene>(null) {
            private Scene oldScene;

            @Override
            protected void invalidated() {
               Scene newScene = getValue();

               sceneChanged(newScene, oldScene);
               // TODO impl_reapplyCSS

               // if (!impl_isDirtyEmpty()) {
               // TODO
               // }

               if (newScene == null && peer != null) {
                  peer.release();
               }

               if (getParent() == null) {
                  // if we are the root we need to handle scene change
                  // TODO
               }
            }

            @Override
            public String getName() {
               return "scene";
            }
         };
      }

      return scene;
   }

   void sceneChanged(Scene newScene, Scene oldScene) {}

   /**
    * The {@code id} of this {@code Node}.
    */
   private Property<String> id;

   public final void setId(String value) {
      idProperty().setValue(value);
   }

   public final String getId() {
      return id == null ? null : id.getValue();
   }

   public final Property<String> idProperty() {
      if (id == null) {
         id = new NodeProperty<String>(null) {
            @Override
            protected void invalidated() {
               // impl_reapplyCSS();
            }

            @Override
            public String getName() {
               return "id";
            }
         };
      }
      return id;
   }

   /**
    * Specifies whether this {@code Node} and any subnodes should be rendred as
    * aprt of the scene graph. A node may be visible and yet not be shown in the
    * rendred scene if, for instance, it is off the screen or obscured by
    * another Node. Invisible nodes never receive mouse events or keyboard focus
    * and never maintain keyboard focus when they become invisible.
    */
   private Property<Boolean> visible;

   public final Property<Boolean> visibleProperty() {
      if (visible == null) {
         visible = new NodeProperty<Boolean>(false) {
            @Override
            protected void invalidated() {
               // TODO impl_markDirty()
               updateTreeVisible();
               // if (getClip() != null
               // TODO
               // }
               if (getParent() != null) {
                  // notify the parent of the potential change in visibility
                  // of this node, since visibility affects bounds of the
                  // parent node
                  getParent().childVisibilityChanged(Node.this);
               }
            }

            @Override
            public String getName() {
               return "visible";
            }
         };
      }
      return visible;
   }

   // ----------------------------------------------------------------------
   // NodeProperty & NodeReadOnlyProperty
   // ----------------------------------------------------------------------

   /**
    * A node property.
    * 
    * @param <T>
    */
   protected abstract class NodeProperty<T> extends Property<T> {
      public NodeProperty(T value) {
         super(value);
      }

      @Override
      public Object getBean() {
         return Node.this;
      }
   }

   /**
    * A read only node property.
    * 
    * @param <T>
    */
   protected abstract class NodeReadOnlyProperty<T> extends ReadOnlyProperty<T> {
      public NodeReadOnlyProperty(T value) {
         super(value);
      }

      @Override
      protected void setValue(T value) {
         super.setValue(value);
      }

      @Override
      public Object getBean() {
         return Node.this;
      }
   }

   // -------------------------------------------------------------------------
   //
   // Methods for synchronizing state from this Node to its _peer.
   //
   // -------------------------------------------------------------------------

   @SuppressWarnings("unchecked")
   public final <T extends NodePeer> T impl_getPeer() {
      if (peer == null) {
         peer = impl_createPeer();
      }
      return (T) peer;
   }

   /**
    * 
    * @return
    */
   protected abstract NodePeer impl_createPeer();

   /**
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   protected final void impl_syncPeer() {
      // TODO call impl_updatePeer()
   }

   /**
    * 
    * @deprecated This is an internal API that is not intended for use and will
    *             be removed in the next version
    */
   protected void impl_updatePeer() {
      final NodePeer peer = impl_getPeer();

      // TODO
   }

}
