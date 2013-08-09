package com.arkasoft.plasma;

import org.robovm.cocoatouch.uikit.UIApplicationDelegate;

public class CopyOfApplicationContext extends UIApplicationDelegate.Adapter {

	private static Thread sUserThread = null;

	protected static Thread getUserThread() {
		return sUserThread;
	}

	protected static void setUserThread(Thread t) {
		if (sUserThread != null) {
			throw new IllegalStateException("Error: Thread already initialized");
		}
		sUserThread = t;
	}
	
	public boolean isUserThread() {
		return Thread.currentThread() == sUserThread;
	}

	public static void runAndWait(Runnable runnable) {
		// TODO Auto-generated method stub

	}

	protected CopyOfApplicationContext() {
	}

}
