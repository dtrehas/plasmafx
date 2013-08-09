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

               // TODO
            }

            @Override
            public String getName() {
               return "scene";
            }
         };
      }

      return scene;
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
               // TODO
            }

            @Override
            public String getName() {
               return "visible";
            }
         };
      }
      return visible;
   }
   
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

   // *************************************************************************
   // *                                                                       *
   // * Methods for synchronizing state from this Node to its _peer.          *
   // *                                                                       *
   // *************************************************************************
   
   /**
    * The peer node.
    */
   private NodePeer _peer;
   
   @SuppressWarnings("unchecked")
   protected final <T extends NodePeer> T _peer_get() {
      if (_peer == null) {
         _peer = _peer_create();
      }
      return (T) _peer;
   }
   
   /**
    * 
    * @return
    */
   protected abstract NodePeer _peer_create();

   /**
    * 
    */
   protected final void _peer_sync() {
      // TODO call impl_updatePeer()
   }
   
   /**
    * 
    */
   protected void _peer_update() {
      // TODO Auto-generated method stub
      
   }

}
