package plasma.scene.paint;

/**
 * The Color class is used to encapsulate colors in the default sRGB color
 * space.
 * 
 */
public class Color extends Paint {

   private static Color sWhite;

   /**
    * The color white with an RGB value of #FFFFFF.
    * 
    * @return
    */
   public static final Color white() {
      return sWhite != null ? sWhite : new Color();
   }

}
