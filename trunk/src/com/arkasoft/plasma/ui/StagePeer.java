package com.arkasoft.plasma.ui;

public interface StagePeer {
   
   void setTitle(String title);
   
   void setPeerListener(StagePeerListener listener);
   
   void setVisible(boolean value);

   void close();
   
}
