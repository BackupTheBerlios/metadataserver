package de.chille.mme.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import de.chille.api.mds.core.MDSModel;
import de.chille.api.mme.core.Mapping;
import de.chille.api.mme.core.MetaMappingEngine;
import de.chille.api.mme.mapper.MDSMapper;
import de.chille.mme.MMEGlobals;
import de.chille.mme.mapper.UnicodeMapperImpl;
import de.chille.mme.mapper.XMLMapperImpl;

/**
 * @see MetaMappingEngine
 * 
 * @author Thomas Chille
 */
public class MetaMappingEngineImpl implements MetaMappingEngine {

	/**
	 *alle auf dem Server vorhandenen Mapper
	 */
	private ArrayList mapperList = new ArrayList();

	/**
	 * Constructor for MetaMappingEngineImpl.
	 */
	public MetaMappingEngineImpl() {
		loadMapper();
	}

	/**
	 * @see MetaMappingEngine#registerMapper(Mapper)
	 */
	public void registerMapper(MDSMapper mapper)
		throws MetaMappingEngineException {
		String type = "unicode", mappingFileName = "parse.jj";
		if (mapper instanceof XMLMapperImpl) {
			type = "xml";
			mappingFileName = "transform.xsl";
		}
		File dir =
			new File(MMEGlobals.MAPPER_PATH + "/mapper_" + mapper.getId());
		File file = new File(dir + "/mapper.xml");
		File mappingFile = new File(dir + "/" + mappingFileName);
		try {
			if (!dir.exists() && !dir.mkdir()) {
				throw new MetaMappingEngineException("Fehler: MetaMappingEngine#registerMapper(Mapper)");
			}
			FileWriter fw;
			fw = new FileWriter(file);
			String doc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
			doc += "<mapper>\r\n";
			doc += "\t<type>" + type + "</type>\r\n";
			doc += "\t<name>" + mapper.getLabel() + "</name>\r\n";
			doc += "\t<from>" + mapper.getMapping().getFrom() + "</from>\r\n";
			doc += "\t<to>" + mapper.getMapping().getTo() + "</to>\r\n";
			// TODO: set datetime
			doc += "\t<datetime>"
				+ "0000-00-00 00:00:00:000"
				+ "</datetime>\r\n";
			doc += "</mapper>";
			fw.write(doc);
			fw.close();
			fw = new FileWriter(mappingFile);
			fw.write(mapper.getMappingFile().getContent());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mapper instanceof UnicodeMapperImpl) {
			((UnicodeMapperImpl)mapper).generateParser();
		}
		mapperList.add(mapper);
	}

	/**
	 * @see MetaMappingEngine#unregisterMapper(Mapper)
	 */
	public void unregisterMapper(MDSMapper mapper)
		throws MetaMappingEngineException {
		mapperList.remove(mapper);
	}

	/**
	 * @see MetaMappingEngine#map(MDSModel, Mapping)
	 */
	public void map(MDSModel mdsModel, Mapping mapping)
		throws MetaMappingEngineException {
		MDSMapper mdsMapper = getMDSMapperForMapping(mapping);
		if (mdsMapper != null)
			mdsMapper.map(mdsModel);
	}

	/**
	 * @see MetaMappingEngine#getMappings(String, String)
	 */
	public ArrayList getMappings(String from, String to)
		throws MetaMappingEngineException {

		ArrayList mappingList = new ArrayList();

		if (from == null && to == null) {
			for (int i = 0; i < mapperList.size(); i++) {
				Mapping currentMapping =
					((MDSMapper) mapperList.get(i)).getMapping();
				mappingList.add(currentMapping);
			}
			return mappingList;
		}

		if (from == null && to != null) {
			for (int i = 0; i < mapperList.size(); i++) {
				Mapping currentMapping =
					((MDSMapper) mapperList.get(i)).getMapping();
				String mappingTo = currentMapping.getTo();
				if (to.compareToIgnoreCase(mappingTo) == 0) {
					mappingList.add(currentMapping);
				}
			}
			return mappingList;
		}

		if (from != null && to == null) {
			for (int i = 0; i < mapperList.size(); i++) {
				Mapping currentMapping =
					((MDSMapper) mapperList.get(i)).getMapping();
				String mappingFrom = currentMapping.getFrom();
				if (from.compareToIgnoreCase(mappingFrom) == 0) {
					mappingList.add(currentMapping);
				}
			}
			return mappingList;
		}

		if (from != null && to != null) {
			for (int i = 0; i < mapperList.size(); i++) {
				Mapping currentMapping =
					((MDSMapper) mapperList.get(i)).getMapping();
				String mappingTo = currentMapping.getTo();
				String mappingFrom = currentMapping.getFrom();
				if (from.compareToIgnoreCase(mappingFrom) == 0) {
					mappingList.add(currentMapping);
				}
			}
			return mappingList;
		}
		return mappingList;
	}

	/**
	 * Method getMDSMapperForMapping.
	 * @param mapping
	 * @return MDSMapper
	 */
	public MDSMapper getMDSMapperForMapping(Mapping mapping) {
		for (int i = 0; i < mapperList.size(); i++) {
			MDSMapper mdsMapper = (MDSMapper) mapperList.get(i);
			Mapping mapp = mdsMapper.getMapping();
			if (mapp.equals(mapping))
				return mdsMapper;
		}
		return null;
	}

	private void loadMapper() {

		String name = "", id = "";
		MDSMapper mapper = null;
		int tmp = 0, max = 0;
		try {
			File dir = new File(MMEGlobals.MAPPER_PATH);
			File[] entries = dir.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return true;
				}
			});
			for (int i = 0; i < entries.length; i++) {
				name = entries[i].getName();
				if (!name.startsWith("mapper_")) {
					continue;
				}
				id = name.substring(7);
				tmp = Integer.parseInt(id);
				if (tmp > max) {
					max = tmp;
				}
				// mapper-properties aus mapper.xml parsen
				DocumentBuilderFactory dFactory =
					DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
				Document doc =
					dBuilder.parse(new File(dir + "/" + name + "/mapper.xml"));
				Element root = doc.getDocumentElement();
				NodeList nList = root.getChildNodes();
				for (int j = 0; j < nList.getLength(); j++) {
					if (nList.item(j).getNodeName().equals("type")) {
						if (nList
							.item(j)
							.getFirstChild()
							.getNodeValue()
							.equals("xml")) {
							mapper = new XMLMapperImpl(id);
						} else {
							mapper = new UnicodeMapperImpl(id);
						}
						mapper.setMapping(new MappingImpl());
					} else if (nList.item(j).getNodeName().equals("name")) {
						mapper.setLabel(
							nList.item(j).getFirstChild().getNodeValue());
					} else if (nList.item(j).getNodeName().equals("from")) {
						mapper.getMapping().setFrom(
							nList.item(j).getFirstChild().getNodeValue());
					} else if (nList.item(j).getNodeName().equals("to")) {
						mapper.getMapping().setTo(
							nList.item(j).getFirstChild().getNodeValue());
					}
				}
				mapperList.add(mapper);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mapper.setCounter(max);
	}
}