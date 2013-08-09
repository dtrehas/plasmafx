package com.arkasoft.plasma.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import plasma.stage.StageStyle;

import com.arkasoft.plasma.ui.base.Window;

public final class SWTWindow extends Window {

   private final Listener shellListener = new Listener() {
      public void handleEvent(Event event) {
         handleShellEvent(event);
      }
   };

   Shell shell;

   public SWTWindow(StageStyle style, boolean primary) {
      int bits = SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE;
      {
         bits |= SWT.CLOSE;
         bits |= SWT.MIN;
         bits |= SWT.MAX;
         // if ((mask & Window.RESIZEABLE) != 0) bits |= SWT.RESIZE;
         bits |= SWT.RESIZE;
      }

      shell = new Shell(Display.getDefault(), bits);

      int[] shellEvents = new int[] {
            SWT.Activate,
            SWT.Close,
            SWT.Deactivate,
            SWT.Iconify,
            SWT.Deiconify,
            SWT.Move,
            SWT.Resize,
            SWT.Dispose
      };

      for (int i = 0; i < shellEvents.length; i++) {
         shell.addListener(shellEvents[i], shellListener);
      }
      
      shell.setData(this);
   }

   private void handleShellEvent(Event event) {
      switch (event.type) {
      case SWT.Activate: {
         // notifyFocus(WindowEvent.FOCUS_GAINED);
         break;
      }
      case SWT.Deactivate: {
         /*
          * Feature in SWT. On the Mac, SWT sends a deactivate and then activate
          * to the parent shell when the user clicks with the left mouse button
          * in a child shell with the style SWT.NO_FOCUS. The fix is to avoid
          * FOCUS_LOST during grab.
          * 
          * NOTE: This is probably not the correct code because FX gets a
          * FOCUS_GAINED for the same stage when focus has not changed.
          */
         // if (SWTWindow.focsWindow != null) {
         // break;
         // }
         // notifyFocus(WindowEvent.FOCUS_LOST);
         break;
      }
      case SWT.Iconify: {
         if (listener != null) {
            listener.changedIconified(true);
         }
         break;
      }
      // TODO - implement maximize notification
      case SWT.Deiconify: {
         if (listener != null) {
            listener.changedIconified(false);
         }
         break;
      }
      case SWT.Move: {
         if (listener != null) {
            Rectangle rect = shell.getBounds();
            Rectangle trim = shell.computeTrim(0, 0, rect.width, rect.height);
            listener.changedLocation(rect.x - trim.x, rect.y - trim.y);
         }
         break;
      }
      case SWT.Resize: {
         Rectangle bounds = shell.getBounds();
         if (listener != null) {
            listener.changedSize(bounds.width, bounds.height);
         }
         // Rectangle rect = shell.getClientArea();
         // Control[] children = shell.getChildren();
         // for (int i = 0; i < children.length; i++) {
         // children[i].setBounds(rect);
         // }
         break;
      }
      case SWT.Close: {
         System.out.println("SWT.Close: " + listener);
         if (listener != null) {
            listener.closing();
            event.doit = true;
         } else {
            event.doit = false;
         }

         break;
      }
      case SWT.Dispose: {
         // Image oldImage = shell.getImage();
         // if (oldImage != null) {
         // shell.setImage(null);
         // oldImage.dispose();
         // }
         break;
      }
      }
   }

   @Override
   public void setTitle(String title) {
      shell.setText(title == null ? "" : title);
   }

   boolean isVisible() {
      return shell != null && shell.isVisible();
   }

   @Override
   public void setVisible(boolean value) {
      shell.setVisible(value);
   }

   @Override
   public void close() {
      shell.close();
   }

}
