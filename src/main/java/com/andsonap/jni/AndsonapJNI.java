package com.andsonap.jni;

public class AndsonapJNI {
	static {
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
			System.loadLibrary("libandsonapJNI");
		} else if (System.getProperty("os.name").toLowerCase().startsWith("Mac")) {
			// System.load("/Users/simon/libandsonapJNI.jnilib");
			System.loadLibrary("libandsonapJNI.jnilib");
		} else {
			System.loadLibrary("andsonapJNI");
		}
	}

	public static native byte[] jniDESCEncode(byte[] plainBytes);

	public static native byte[] jniDESCDecode(byte[] encodeBytes);
}
