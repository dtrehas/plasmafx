package plasma.application;

import plasma.stage.Stage;

/**
 * Application class from which applications extend.
 * <p>
 * 
 */
public abstract class Application {

   /**
    * The application initialization method.
    */
   public void init() throws Exception {
   }

   /**
    * The main entry point for all applications.
    * 
    * @param primaryStage
    *           the primary stage for this application, onto which the
    *           application scene can be set.
    */
   public abstract void start(Stage primaryStage) throws Exception;

   /**
    * This method is called when the application should stop, and provides a
    * convenient place to prepare for application exit and destroy resources.
    * 
    * @throws Exception
    */
   public void stop() throws Exception {
   };

}
