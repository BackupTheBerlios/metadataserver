package de.chille.mme.mapper;

import java.io.File;
import de.chille.api.mme.mapper.XMLMapper;
import de.chille.mme.core.MetaMappingEngineException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import de.chille.mme.MmeGlobals;
import de.chille.api.mme.core.Mapping;
import de.chille.mme.core.MappingImpl;

import java.util.ArrayList;


/**
 * @see XMIHandler
 * 
 * @author Christian Sterr
 */
public class XMLMapperImpl extends MDSMapperImpl implements XMLMapper {

	/**
	 * soll dieser Mapper die Packetstrucktur nachbilden
	 */
	private boolean buildPackage = true;

	/**
	 * was kann dieser Mapper mappen
	 */
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
		this(configXml, null);
//		this.configXml = configXml;
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
		XMLMapperProzessor  trans = new XMLMapperProzessor ();
		trans.setConfigXml(configXml.getAbsolutePath());
		trans.setBuildPackage(this.getBuildPackage());
		ArrayList fileList;
		System.out.println("doMapping: " + xmi);
		if(outputPath != null){
			System.out.println("doMapping: outputPath: " + outputPath);			
			fileList = trans.startMapping(xmi ,outputPath);
		}else{
			System.out.println("doMapping: default: " + MmeGlobals.OUTPUT_PATH);			
			fileList = trans.startMapping(xmi, MmeGlobals.OUTPUT_PATH);
		}
		return fileList;
	}

	/**
	 * @see de.chille.api.de.chille.de.chille.mme.mapper.MDSMapper#setBuildPackage(boolean)
	 */
	public void setBuildPackage(boolean buildPackage){
		this.buildPackage = buildPackage;
	}


	/**
	 * @see de.chille.api.de.chille.de.chille.mme.mapper.MDSMapper#getBuildPackage()
	 */
	public boolean getBuildPackage(){
		return buildPackage;
	}

}