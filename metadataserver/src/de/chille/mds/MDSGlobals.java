package de.chille.mds;

import java.io.*;

public class MDSGlobals {

	public static final String TEMP_PATH =
		"C:/Programme/eclipse/workspace/metadata.server/tmp/";

	public static final String RESOURCES_PATH =
		"C:/Programme/eclipse/workspace/metadata.server/resources/";

	public static final String REPOSITORIES_PATH =
		"C:/Programme/eclipse/workspace/metadata.server/repositories/";

	public static void log(String msg) {
		try {
			File file =
				new File("C:/Programme/eclipse/workspace/metadata.server/log.txt");
			FileWriter fw = new FileWriter(file);
			fw.write(msg);
			fw.close();
		} catch (IOException e) {
		}
	}
}
