package plasma.scene;

import java.util.ArrayList;
import java.util.List;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.scene.LayoutFlags;
import com.arkasoft.plasma.ui.NodePeer;

import plasma.beans.ReadOnlyProperty;
import plasma.collections.ObservableCollections;
import plasma.collections.ObservableList;
import plasma.collections.ObservableListListener;

/**
 * The base class for all nodes that have children in the scene graph.
 * <p>
 * This class handles all hierarchical scene graph operations, including
 * adding/removing child nodes, marking branches dirty for layout and rendering,
 * picking, bounds calculations, and executing the layout pass on each pulse.
 */
public abstract class Parent extends Node {

   /**
    * A {@link ObservableList} of child {@code Node}s.
    */
   private final ObservableList<Node> children = ObservableCollections.observableList(new ArrayList<Node>());

   /**
    * Get the list of children of this {@code Parent}.
    * 
    * @return the list of children of this {@code Parent}.
    */
   protected ObservableList<Node> getChildren() {
      return children;
   }

   protected Parent() {
      children.addObservableListListener(new ObservableListListener<Node>() {
         @Override
         public void listElementsInserted(ObservableList<Node> list, int index, int length) {
            // TODO Auto-generated method stub

         }

         @Override
         public void listElementsRemoved(ObservableList<Node> list, int index, List<Node> oldElements) {
            // TODO Auto-generated method stub

         }

         @Override
         public void listElementReplaced(ObservableList<Node> list, int index, Node oldElement) {
            // TODO Auto-generated method stub

         }
      });
   }

   /**
    * Indicates that this Node and its subnodes requires a layout pass on the
    * next pulse.
    */
   private ReadOnlyProperty<Boolean> needsLayout;
   LayoutFlags layoutFlag = LayoutFlags.CLEAN;

   private void setLayoutFlag(LayoutFlags flag) {
      if (needsLayout != null) {
         ((NodeReadOnlyProperty<Boolean>) needsLayout).setValue(flag == LayoutFlags.NEEDS_LAYOUT);
      }
      layoutFlag = flag;
   }

   protected final void setNeedsLayout(boolean value) {
      if (value) {
         markDirtyLayout(true);
      } else if (layoutFlag == LayoutFlags.NEEDS_LAYOUT) {
         boolean hasBranch = false;
         for (Node child : children) {
            if (child instanceof Parent) {
               if (child instanceof Parent) {
                  Parent p = (Parent) child;
                  if (p.layoutFlag != LayoutFlags.CLEAN) {
                     hasBranch = true;
                     break;
                  }
               }
            }
         }
         setLayoutFlag(hasBranch ? LayoutFlags.DIRTY_BRANCH : LayoutFlags.CLEAN);
      }
   }

   private void markDirtyLayout(boolean b) {
      // TODO 
   }

   public final boolean isNeedsLayout() {
      return layoutFlag == LayoutFlags.NEEDS_LAYOUT;
   }

   public final ReadOnlyProperty<Boolean> needsLayoutProperty() {
      if (needsLayout == null) {
         needsLayout = new NodeReadOnlyProperty<Boolean>(true) {
            @Override
            protected void invalidated() {

            }

            @Override
            public String getName() {
               return "needsLayout";
            }
         };
      }
      return needsLayout;
   }
   
   @Override
   protected NodePeer _peer_create() {
      return ApplicationContext.getInstance().createGroupPeer();
   }
   
}
