package de.chille.mme;




/**
 * Globale Constanten für die Meta Mapping Engine
 * @author Christian Sterr
 * 
 */
public class MMEGlobals {

	public static final String MAPPER_PATH =
		"C:/Programme/eclipse/workspace/metadata.server/mapper/";
	
	public static final String TEMP_PATH =
		"C:/Programme/eclipse/workspace/metadata.server/tmp/";

	
	
	/**
	 * Default output Path
	 */
	public static final String OUTPUT_PATH = "/output/src";

	/**
	 * pfad zum XMLMapperConfig File
	 */
	public static final String XML_MAPPER_CONFIG_PATH = "src/de.chille.mme/config/XMLMapper/xsl-templates/java/";

	/**
	 * Name des Config Files
	 */
	public static final String XML_MAPPER_CONFIG_FILE_NAME = "XMLMapperConfig.xml";

	/**
	 * name des Tags im XMLMapperConfig File der die from und to Info hält
	 */
	public static final String XML_CONFIG_FROM_TO_TAG = "mapping";

	/**
	 * from attribute des FROM_TO_TAG
	 */
	public static final String XML_CONFIG_FROM_ATTR = "from";

	/**
	 * to attribute des FROM_TO_TAG
	 */
	public static final String XML_CONFIG_TO_ATTR = "to";
	
	/**
	 * Pfad in dem sich die unterverzeichnisse mit den
	 * transformer Confgig.xmls und die zugehörigen xslt's befinden
	 */
	public static final String XML_MAPPER_PFAD = "src/de.chille.mme/config/XMLMapper/xsl-templates/";
	
}
