package com.arkasoft.plasma.ui;

public interface ScenePeer extends NodePeer {

   void entireSceneNeedsRepaint();

   void setRoot(NodePeer root);

}
