package plasma.stage;

/**
 * This enum defines the possible styles for a {@code Stage}.
 */
public enum StageStyle {

   /**
    * Defines a normal {@code Stage} style with a solid white background and
    * platform decorations.
    */
   DECORATED,

   /**
    * Defines a {@code Stage} style with a solid white background and no
    * decorations.
    */
   UNDECORATED,

   /**
    * Defines a {@code Stage} style with a transparent background and no
    * decorations.
    */
   TRANSPARENT,

   /**
    * Defines a {@code Stage} style with a solid white background and minimal
    * platform decorations used for a utility window.
    */
   UTILITY,

   /**
    * Defines a {@code Stage} style with platform decorations and eliminates the
    * border between client area and decorations. The client area background is
    * unified with the decorations.
    */
   UNIFIED

}
