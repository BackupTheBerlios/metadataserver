package mme.mapper;

import java.io.File;
import api.mme.mapper.XMLMapper;
import mme.core.MetaMappingEngineException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import mme.MmeGlobals;
import api.mme.core.Mapping;
import mme.core.MappingImpl;

import java.util.ArrayList;


/**
 * @see XMIHandler
 * 
 * @author Christian Sterr
 */
public class XMLMapperImpl extends MDSMapperImpl implements XMLMapper {

	private Mapping mapping = null;

	/**
	 * der Pfad für die erstellten Files
	 *
	 */
	private String outputPath = null;

	/**
	 * der für diesen mapper verwendeten XSL-Stylesheets
	 */
	private File configXml = null;

	public XMLMapperImpl(File configXml){
		this(configXml, "");
		this.configXml = configXml;
	}

	/**
	 * Method XMLMapperImpl.
	 * @param stylesheet
	 * @param outputPath
	 */
	public XMLMapperImpl(File configXml, String outputPath){
		super.xmlMapper = this;
		this.configXml  = configXml ;
		this.outputPath = outputPath;
		String from = null;
		String to = null;
		try{
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			//config File laden und parsen!!
			Document doc = dBuilder.parse(configXml);
			Node mapping = doc.getElementsByTagName(MmeGlobals.XML_CONFIG_FROM_TO_TAG).item(0);
			NamedNodeMap attributes = mapping.getAttributes();
			Attr fromAttr = (Attr)attributes.getNamedItem(MmeGlobals.XML_CONFIG_FROM_ATTR);
			Attr toAttr = (Attr)attributes.getNamedItem(MmeGlobals.XML_CONFIG_TO_ATTR);
			from = fromAttr.getValue();
			to = toAttr.getValue();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		mapping = new MappingImpl();
		mapping.setFrom(from);
		mapping.setTo(to);
	}
	
	public Mapping getMapping(){
		return mapping;
	}
	
	/**
	 * Gets the stylesheets
	 * @return Returns a File[]
	 */
	public File getConfigXml() {
		return configXml ;
	}

	/**
	 * Sets the stylesheets
	 * @param stylesheets The stylesheets to set
	 */
	public void setConfigXml(File configXml ) {
		this.configXml  = configXml;
				
	}
	
	
	/**
	 * Method setOutputPath.
	 * @param outputPath
	 */
	public void setOutputPath(String outputPath){
		this.outputPath = outputPath;
	}	


	/**
	 * Method getOutputPath.
	 */
	public String getOutputPath(){
		return outputPath;
	}

	/**
	 * Method doMapping.
	 * @param xmlMapperConfigFile
	 * @param outputPath
	 * @throws MetaMappingEngineException
	 */
	public ArrayList doMapping(String xmi)throws MetaMappingEngineException{
		XmlMapperProzessor  trans = new XmlMapperProzessor ();
		trans.setConfigXml(configXml.getAbsolutePath());
		ArrayList fileList;
		if(outputPath != null)
			fileList = trans.startMapping(xmi ,outputPath);
		else
			fileList = trans.startMapping(xmi, MmeGlobals.OUTPUT_PATH);
		return fileList;
	}
	

}