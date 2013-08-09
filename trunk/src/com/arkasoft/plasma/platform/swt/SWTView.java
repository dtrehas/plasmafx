package com.arkasoft.plasma.platform.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.arkasoft.plasma.ui.base.View;

public class SWTView extends View {

   static Shell hiddenShell;

   private final Listener canvasListener = new Listener() {
      @Override
      public void handleEvent(Event event) {
         handleCanvasEvent(event);
      }
   };

   Canvas canvas;

   public SWTView() {
      super();

      if (hiddenShell == null) {
         hiddenShell = new Shell(Display.getDefault(), SWT.SHELL_TRIM);
         Display.getDefault().disposeExec(new Runnable() {
            public void run() {
               hiddenShell.dispose();
               hiddenShell = null;
            }
         });
      }

      int bits = SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE | SWT.LEFT_TO_RIGHT; // SWT.NO_FOCUS;

      if (SWT.getPlatform().equals("cocoa")) {
         GLData data = new GLData();
         data.doubleBuffer = true;
         canvas = new GLCanvas(hiddenShell, bits, data);
      } else {
         canvas = new Canvas(hiddenShell, bits);
      }

      int[] events = new int[] {
            SWT.KeyDown,
            SWT.KeyUp,
            SWT.MouseDown,
            SWT.MouseMove,
            SWT.MouseEnter,
            SWT.MouseExit,
            SWT.MouseHorizontalWheel,
            SWT.MouseVerticalWheel,
            SWT.MenuDetect,
            SWT.Paint,
            SWT.Resize
      };

      for (int i = 0; i < events.length; ++i) {
         canvas.addListener(events[i], canvasListener);
      }

      // TODO - refactor to drop target creation in a better place
      // dropTarget = SWTClipboard.createDropTarget(canvas);

      canvas.setData(this);
   }

   private void handleCanvasEvent(Event event) {
      switch (event.type) {
      case SWT.KeyDown:
         break;
      case SWT.KeyUp:
         break;
      case SWT.MouseDown:
         break;
      case SWT.MouseMove:
         break;
      case SWT.MouseEnter:
         break;
      case SWT.MouseExit:
         break;
      case SWT.MouseHorizontalWheel:
         break;
      case SWT.MouseVerticalWheel:
         break;
      case SWT.MenuDetect:
         break;
      case SWT.Paint:
         break;
      case SWT.Resize:
         break;
      }
   }

}
