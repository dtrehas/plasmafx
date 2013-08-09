package com.arkasoft.plasma.demo;

import com.arkasoft.plasma.ui.swt.SWTApplicationContext;

import plasma.application.Application;
import plasma.scene.Scene;
import plasma.stage.Stage;

public class Main extends Application {

   @Override
   public void init() throws Exception {
      System.out.println(">>> init");
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      System.out.println(">>> start: " + primaryStage);
      
      primaryStage.setTitle("PlasmaFX");
      primaryStage.show();
   }

   @Override
   public void stop() throws Exception {
      System.out.println(">>> stop");
   }

   public static void main(String[] args) throws Exception {
      SWTApplicationContext.launch(Main.class, args);
   }

}
