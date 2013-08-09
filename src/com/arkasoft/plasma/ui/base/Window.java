package com.arkasoft.plasma.ui.base;

import com.arkasoft.plasma.ui.StagePeer;
import com.arkasoft.plasma.ui.StagePeerListener;

public abstract class Window implements StagePeer {

   protected StagePeerListener listener = null;

   @Override
   public void setPeerListener(StagePeerListener listener) {
      this.listener = listener;
   }

}
