package mme;




/**
 * Globale Constanten f�r die Meta Mapping Engine
 * @author Christian Sterr
 * 
 */
public class MmeGlobals {

	/**
	 * Default output Path
	 */
	public static final String OUTPUT_PATH = "/output/src";

	/**
	 * pfad zum XMLMapperConfig File
	 */
	public static final String XML_MAPPER_CONFIG_PATH = "src/mme/config/XMLMapper/xsl-templates/java/XMLMapperConfig.xml";

	/**
	 * Name des Config Files
	 */
	public static final String XML_MAPPER_CONFIG_FILE_NAME = "XMLMapperConfig.xml";

	/**
	 * name des Tags im XMLMapperConfig File der die from und to Info h�lt
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
	 * transformer Confgig.xmls und die zugeh�rigen xslt's befinden
	 */
	public static final String XML_MAPPER_PFAD = "src/mme/config/XMLMapper/xsl-templates/";
	
}
