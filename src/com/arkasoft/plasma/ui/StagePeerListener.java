package com.arkasoft.plasma.ui;

public interface StagePeerListener {

   /**
    * The stage peer's location have changed.
    * 
    * @param x the new x
    * @param y the new y
    */
   void changedLocation(float x, float y);
   
   /**
    * The stage peer's size have changed.
    * 
    * @param width the new width
    * @param height the new height
    */
   void changedSize(float width, float height);
   
   // TODO void changedFocused(boolean focused, Focuscause cause);
   
   void changedIconified(boolean iconified);
   
   void changedMaximized(boolean maximized);
   
   void changedResizable(boolean resizable);
   
   void changedFullscreen(boolean fs);
   
   void closing();
   
   void closed();
   
   // TODO void focusUngrab();
   
}
