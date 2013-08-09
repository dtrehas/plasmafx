package com.arkasoft.plasma.platform.ios;

import org.robovm.cocoatouch.foundation.NSAutoreleasePool;
import org.robovm.cocoatouch.foundation.NSDictionary;
import org.robovm.cocoatouch.uikit.UIApplication;
import org.robovm.cocoatouch.uikit.UIApplicationDelegate;

public class IOSDemo extends UIApplicationDelegate.Adapter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean didFinishLaunching(UIApplication application,
			NSDictionary launchOptions) {

		Thread launchThread = new Thread() {
			@Override
			public void run() {
				// Application.launch(applicationClass);
			}
		};
		launchThread.setDaemon(true);
		launchThread.start();

		return true;
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("plasma.platform", "ios");
		
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(args, null, IOSDemo.class);
		pool.drain();
	}
}
