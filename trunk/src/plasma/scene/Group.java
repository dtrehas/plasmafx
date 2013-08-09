package plasma.scene;

import plasma.collections.ObservableList;

import com.arkasoft.plasma.ui.NodePeer;

/**
 * A {@code Group} node contains an {@link ObservableList} of children that are
 * rendered in order whenever this node is rendered.
 * <p>
 * A {@code Group} will take on the collective bounds of its children and is not
 * directly resizable.
 * <p>
 * Any transform, effect, or state applied to a {@code Group} will be applied to
 * all children of that group. Such transforms and effects will NOT be included
 * in this Group's layout bounds, however if transforms and effects are set
 * directly on children of this Group, those will be included in this Group's
 * layout bounds.
 * <p>
 * By default, a {@code Group} will "auto-size" its managed resizable children
 * to their preferred sizes during the layout pass to ensure that Regions and
 * Controls are sized properly as their state changes. If an application needs
 * to disable this auto-sizing behavior, then it should set
 * {@link #autoSizeChildren} to {@code false} and understand that if the
 * preferred size of the children change, they will not automatically resize (so
 * buyer beware!).
 */
public class Group extends Parent {

   /**
    * Constructs a group.
    */
   public Group() {
      super();
   }

   // TODO more constructors

   /**
    * Gets the list of children of this {@code Group}.
    * 
    * @return the list of children of this {@code Group}.
    */
   @Override
   public ObservableList<Node> getChildren() {
      return super.getChildren();
   }

}
