package de.chille.mds;

import java.util.Random;

public final class Util {

	private static Util defaultInstance = null;

	private static Random random = null;

	public synchronized static Util getInstance() {
		if (Util.defaultInstance == null) {
			Util.defaultInstance = new Util();
		}
		return Util.defaultInstance;
	}

	public static String getUniqueID() {
		if (Util.random == null) {
			Util.random = new Random();
		}
		return "uid_"
			+ System.currentTimeMillis()
			+ "_"
			+ Math.abs(random.nextInt());
	}

}
