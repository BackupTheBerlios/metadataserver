package de.chille.mme.mapper;

import org.notdotnet.labrador.util.XMLWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.serialize.SerializerFactory;
import org.apache.xalan.serialize.Serializer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import java.io.FileOutputStream;
import java.io.*;

import de.chille.mme.MmeGlobals;
import de.chille.mme.core.MetaMappingEngineException;

import java.util.ArrayList;


/**
 * @author Christian Sterr
 *
 */
public class XMLMapperProzessor
{
	private String home = null;
	
	private String xmlMapperConfigPath = MmeGlobals.XML_MAPPER_CONFIG_PATH + MmeGlobals.XML_MAPPER_CONFIG_FILE_NAME;
	
	private boolean buildPackage = true;
    
	/**
	 * Method setBuildPackageSturckt.
	 * @param buildPackageSturckt
	 */
    public void setBuildPackage(boolean buildPackage){
    	this.buildPackage = buildPackage;
    }
    
	/**
	 * Method getBuildPackageSturckt.
	 * @return boolean
	 */
    public boolean getBuildPackage(){
    	return buildPackage;
    }

	/**
	 * Method setConfigXml.
	 * @param configXml
	 */
	public void setConfigXml(String configXml){
		this.xmlMapperConfigPath = configXml;
	}
	
	/**
	 * Method getConfigXml.
	 * @return String
	 */
	public String getConfigXml(){
		return xmlMapperConfigPath;
	}

	/**
	 * Method doMain.
	 * @param project
	 */
    public void doMain(String project)
    {
        try
        {
        	
            String filename = "";
			System.out.println("Prozessor: " + project + "!!!!!!!!!!!!!!!!!");
            /*
             * lade das Config File
             */
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();

			/*
			 * parse das Config File
			 */
            Document ds = dBuilder.parse(xmlMapperConfigPath);
            Element config = ds.getDocumentElement();
            NodeList ssList = config.getChildNodes();

            /*
             * das Stylesheet für die Phase1 
             */
            for (int i = 0; i < ssList.getLength(); i++)
            {
                String name = ssList.item(i).getNodeName();
                String scope = "";
                if (ssList.item(i).getAttributes()!=null && ssList.item(i).getAttributes().getNamedItem("scope") != null)
                        scope=ssList.item(i).getAttributes().getNamedItem("scope").getNodeValue();
                if ("stylesheet".equals(name) && "phase1".equals(scope))
                {
                    NodeList sschildList = ssList.item(i).getChildNodes();
                    for (int j = 0; j < sschildList.getLength(); j++)
                    {
                        String childname = sschildList.item(j).getNodeName();
                        if ("filename".equals(childname))
                        {
                            filename = sschildList.item(j).getFirstChild().getNodeValue();
                        }
                    }
                }
            }

            /*
             * das xmi File in ein leichter zu lesendes xml umwandeln
             */
            Document doc = ApplyPhase1StyleSheet(project, filename);
            System.out.println("ApplyPhase1StyleSheet projekt: " + project + " Fielname: " +filename );
            
            Element rootElement = doc.getDocumentElement();
            rootElement.normalize();

            //find all global_stylesheets
            // wird nicht gebraucht !
            // nur zum debugen um zu sehen wie das umgewandelte xmi aussieht
	        for (int i = 0; i < ssList.getLength(); i++)
            {
                String global_filename = "";
                String result = "";
                String name = ssList.item(i).getNodeName();
                String scope = "";
                if (ssList.item(i).getAttributes()!=null && ssList.item(i).getAttributes().getNamedItem("scope") != null)
                        scope=ssList.item(i).getAttributes().getNamedItem("scope").getNodeValue();
                if ("stylesheet".equals(name) && "global".equals(scope))
                {
                    NodeList sschildList = ssList.item(i).getChildNodes();
                    for (int j = 0; j < sschildList.getLength(); j++)
                    {
                        String childname = sschildList.item(j).getNodeName();
                        if ("filename".equals(childname))
                        {
                            global_filename = sschildList.item(j).getFirstChild().getNodeValue();
                        }
                        if ("result".equals(childname))
                        {
                            result = sschildList.item(j).getFirstChild().getNodeValue();
                        }
                    }

                    /*
                     * das stylesheet anwenden
                     */
                    ApplyStyleSheet(global_filename, home, result, rootElement);
                }
            }
            RecursePackages("", "", rootElement);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

	/**
	 * Method RecursePackages.
	 * @param pkg
	 * @param path
	 * @param e
	 */
    public void RecursePackages(String pkg, String path, Element e)
    {
    	System.out.println("recurse -> packetname: " + pkg + " paketpath: " + path);
        try
        {
            String packagename = pkg;
            String packagepath = path;
            String currentpackage = "";
            NodeList aList = e.getChildNodes();

            //find packages and create packagepath and packagename strings
            for (int j = 0; j < aList.getLength(); j++)
            {
                String name = aList.item(j).getNodeName();
                if ("package_name".equals(name))
                {
                    String nodeValue = aList.item(j).getFirstChild().getNodeValue();
                    if (!"".equals(packagename))
                    {
                        packagename = packagename + "." + nodeValue;
                        packagepath = packagepath + "/" + nodeValue;
                    }
                    else
                    {
                        packagename = nodeValue;
                        packagepath = nodeValue;
                    }

                    currentpackage = nodeValue;
                }

                //add the package_name to each class
                if ("class".equals(aList.item(j).getNodeName()))
                {
                    Element pkgElement = e.getOwnerDocument().createElement("package_name");
                    Node txtNode = e.getOwnerDocument().createTextNode(packagename);
                    pkgElement.appendChild(txtNode);
                    aList.item(j).appendChild(pkgElement);
                }
            }

            CheckConfig(packagename, packagepath, currentpackage, (Element) e.cloneNode(true), "package");

            //this is where we look for either another package and do a recursive call
            //otherwise look for a class and apply all class stylesheets
            for (int j = 0; j < aList.getLength(); j++)
            {
                String name = aList.item(j).getNodeName();
                if ("package".equals(name))
                    RecursePackages(packagename, packagepath, (Element) aList.item(j));

                if ("class".equals(name))
                    CheckConfig(packagename, packagepath, currentpackage, (Element) aList.item(j), "class");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

	/**
	 * Method CheckConfig.
	 * @param pkg
	 * @param path
	 * @param currentpackage
	 * @param e
	 * @param type
	 */
    public void CheckConfig(String pkg, String path, String currentpackage, Element e, String type)
    {
		System.out.println();
		System.out.println("CC -> Packname: " + pkg + " packPath: " + path + " currentpackage: " + currentpackage + " type: " + type);
		System.out.println();

        try
        {
            String packagename = pkg;
            String packagepath = path;

            /*
             * config File laden
             */
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document ds = dBuilder.parse(xmlMapperConfigPath);
            Element config = ds.getDocumentElement();
            NodeList ssList = config.getChildNodes();


            /*
             * für jeden Type die stylesheets anwenden
             */
            for (int i = 0; i < ssList.getLength(); i++)
            {
                String name = ssList.item(i).getNodeName();
                String scope = "class";
                if (ssList.item(i).getAttributes()!=null && ssList.item(i).getAttributes().getNamedItem("scope") != null)
                        scope=ssList.item(i).getAttributes().getNamedItem("scope").getNodeValue();
                if ("stylesheet".equals(name) && type.equals(scope))
                {
                    String filename = "";
                    String result_ext = "";
                    NodeList sschildList = ssList.item(i).getChildNodes();
                    for (int j = 0; j < sschildList.getLength(); j++)
                    {
                        String childname = sschildList.item(j).getNodeName();
                        if ("filename".equals(childname))
                            filename = sschildList.item(j).getFirstChild().getNodeValue();
                        if ("result".equals(childname))
                            result_ext = sschildList.item(j).getFirstChild().getNodeValue();
                        if ("package".equals(childname))
                        {
                        	// neu von ch
                        	if(sschildList.item(j).getFirstChild() != null){
                        	// alt weiter
	                            String stereotype = sschildList.item(j).getFirstChild().getNodeValue();
	                            if (stereotype.equals(currentpackage)){
	                            	System.out.println("CC -> applySyleSheet filename: " + filename + " home + packagepath: " + packagepath + " className + result_ext: " + getClassName(e,type) + "+" + result_ext );
	                            	if(this.getBuildPackage())
		                                ApplyStyleSheet(filename, home + packagepath,  getClassName(e,type) + result_ext, e);
		                            else
		                                ApplyStyleSheet(filename, home,  getClassName(e,type) + result_ext, e);		                            
	                            }
                        	}else{
                        		System.out.println("!!!!!!!! so is es !!!");
                        		if(this.getBuildPackage())
		                        	ApplyStyleSheet(filename, home + packagepath,  getClassName(e,type) + result_ext, e);
		                        else
		                        	ApplyStyleSheet(filename, home,  getClassName(e,type) + result_ext, e);		                        
                        	}
                        }
                    }
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

	/**
	 * Method ApplyPhase1StyleSheet.
	 * @param project das zu mappende XMI File
	 * @param filename des xsl Files
	 * @return Document
	 * @throws Exception
	 */
    public Document ApplyPhase1StyleSheet(String project, String filename) throws Exception
    {
        System.out.println("Applying Phase 1: project = " + project + " filename = " + filename);
        DOMResult dr = new DOMResult();
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(new StreamSource(filename));
        transformer.transform(new StreamSource(new StringBufferInputStream(project)), dr);
        return (Document) dr.getNode();
    }

	/**
	 * Method ApplyStyleSheet.
	 * @param styleSheetFileName
	 * @param resultPath
	 * @param resultFileName
	 * @param e
	 */
    public void ApplyStyleSheet(String styleSheetFileName, String resultPath, String resultFileName, Element e)
    {
        try
        {
            /*
             * hier werden die Paketstruckturen erzeugt
             */
            java.io.File f = new java.io.File(resultPath);
            f.mkdirs();

            resultFileName = resultPath + "/" + resultFileName;
            System.out.println("Applying Stylesheet: filename = " + styleSheetFileName + " resultname = " + resultFileName);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer tform = tFactory.newTransformer(new StreamSource(styleSheetFileName));
// debug start
			XMLWriter writer = new XMLWriter(e);
			System.out.println(writer.toString());
// debug stop
            tform.transform(new DOMSource(e), new StreamResult(new FileOutputStream(resultFileName)));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

	/**
	 * Method getClassName.
	 * @param e
	 * @param type
	 * @return String
	 */
    private String getClassName(Element e, String type)
    {
        String classname = "";

        //ignore the classname if applying to a package
        if (type.equals("package")) return "";

        NodeList aList = e.getChildNodes();
        for (int j = 0; j < aList.getLength(); j++)
        {
            String name = aList.item(j).getNodeName();
            if ("class_name".equals(name))
            {
                classname = aList.item(j).getFirstChild().getNodeValue();
                break;
            }
        }
        return classname;
    }

	/**
	 * Method startMapping.
	 * @param source das XMI File
	 * @param modelNr ein eindeutiger bezeichner für das Model
	 * @throws MetaMappingEngineException
	 */
    public ArrayList startMapping(String source, String modelNr) throws MetaMappingEngineException
    {
		String path = modelNr + "/src/";
    	
		if(source == null || source.compareTo("") == 0){
			System.out.println("Source == null");
			throw new MetaMappingEngineException("no input source to mapp");
		}
		
		/*
		 *  beseitige die möglichen alten Files aus dem Verzeichniss
		 */
        clearPath(path);
		
		
        this.home = path;
        this.doMain(source);
        System.out.println("!!alles ok!!");
        return searchForFilesIn(path, new ArrayList());
        
    }
    
	/**
	 * Method clearPath, beseitige die möglichen alten Files aus dem Verzeichniss.
	 * @param path
	 */
    private void clearPath(String path){
    	System.out.println("clearPath: " + path);
    	File parent = new File(path);
    	File[] directoryAndFileList = parent.listFiles();
    	if(directoryAndFileList != null){
    	for(int i=0; i<directoryAndFileList.length; i++){
			if(directoryAndFileList[i].isFile())    		
    			directoryAndFileList[i].delete();
			if(directoryAndFileList[i].isDirectory());
				if(!directoryAndFileList[i].delete()){
					clearPath(directoryAndFileList[i].getAbsolutePath());
					directoryAndFileList[i].delete();
				}
    		
    	}}
    }
    
	/**
	 * Method searchForFilesIn.
	 * @param path
	 * @param fileList
	 * @return ArrayList
	 */
    private ArrayList searchForFilesIn(String path, ArrayList fileList){

		File parent = new File(path);
		File[] directoryAndFileList = parent.listFiles();
		for(int i=0; i<directoryAndFileList.length; i++){
			if(directoryAndFileList[i].isFile())
				fileList.add(directoryAndFileList[i]);
			if(directoryAndFileList[i].isDirectory())
				searchForFilesIn(directoryAndFileList[i].getAbsolutePath(), fileList);
		}
    	return fileList;
    }
}
