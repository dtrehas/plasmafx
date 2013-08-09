package com.arkasoft.plasma.platform.ios;

import plasma.application.Application;
import plasma.stage.StageStyle;

import com.arkasoft.plasma.ApplicationContext;
import com.arkasoft.plasma.ui.NodePeer;
import com.arkasoft.plasma.ui.ScenePeer;
import com.arkasoft.plasma.ui.StagePeer;

public class IOSApplicationContext extends ApplicationContext {

	/**
	 * Utility method to make it easier to define {@code main} entry-points into
	 * applications.
	 * 
	 * @param applicationClass
	 *            the application class that is constructed and executed by the
	 *            launcher.
	 * @param applicationArgs
	 *            the command line arguments passed to the application.
	 */
	public static void launch(final Class<? extends Application> applicationClass, final String... applicationArgs) {
		assert ApplicationContext.instance == null : "ApplicationContext already initialized";
		IOSApplicationContext appContext = new IOSApplicationContext();
		ApplicationContext.instance = appContext;
		appContext.launchApplication(applicationClass, applicationArgs);
	}
	
	private IOSApplicationContext() {
		// nothing
	}

	@Override
	protected void runAsync(Runnable runnable) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void syncExec(Runnable launchable) {
		// TODO Auto-generated method stub

	}

	@Override
	public StagePeer createStagePeer(StageStyle style, boolean primary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScenePeer createScenePeer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodePeer createGroupPeer() {
		// TODO Auto-generated method stub
		return null;
	}
	
   @Override
   public boolean hasTouch() {
       return true;
   }

   @Override
   public boolean hasMultiTouch() {
       return true;
   }

}
