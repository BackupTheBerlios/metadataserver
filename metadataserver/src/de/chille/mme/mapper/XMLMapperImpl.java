package de.chille.mme.mapper;

import java.io.*;
import java.util.ArrayList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import de.chille.api.mds.core.MDSFile;
import de.chille.api.mds.core.MDSModel;
import de.chille.api.mme.mapper.XMLMapper;
import de.chille.mds.Util;
import de.chille.mds.core.MDSFileImpl;
import de.chille.mds.soap.MDSMapperBean;
import de.chille.mme.MMEGlobals;
import de.chille.mme.core.MetaMappingEngineException;

/**
 * @see XMIHandler
 * 
 * @author Thomas Chille
 */
public class XMLMapperImpl extends MDSMapperImpl implements XMLMapper {

	private ArrayList files;

	private MDSModel mdsModel;

	/**
	 * Constructor for XMLMapperImpl.
	 * @param id
	 */
	public XMLMapperImpl(String id) {
		super(id);
	}

	/**
	 * @see de.chille.api.mme.mapper.MDSMapper#map(MDSModel)
	 */
	public void map(MDSModel mdsModel) throws MetaMappingEngineException {
		this.mdsModel = mdsModel;
		try {
			// process stylesheet
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer tform =
				tFactory.newTransformer(
					new StreamSource(
						"file:/" + MMEGlobals.MAPPER_PATH
							+ "/mapper_"
							+ getId()
							+ "/transform.xsl"));
			String tmpPath = MMEGlobals.TEMP_PATH + Util.getUniqueID();
			tform.setParameter("outputPath", tmpPath);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			tform.transform(
				new StreamSource(
					new ByteArrayInputStream(
						mdsModel.getUmlFile().getContent().getBytes())),
				new StreamResult(outputStream));
			outputStream.close();
			// add generated files to model
			files = new ArrayList();
			setFiles(tmpPath, "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new MetaMappingEngineException(e.getMessage());
		}
	}

	/**
	 * add the generated files rekursiv as additional files to the model
	 * 
	 * @param path
	 * @param addPath
	 */
	private void setFiles(String path, String addPath) {
		File dir;
		if (addPath.equals("")) {
			dir = new File(path);
		} else {
			dir = new File(path + "/" + addPath);
		}
		File[] entries = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return true;
			}
		});
		MDSFile mdsFile;
		BufferedReader f;
		String name = "", content, line = "";
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].isDirectory()) {
				if (!addPath.equals("")) {
					addPath = "/";
				}
				setFiles(path, addPath + entries[i].getName());
				continue;
			}
			content = "";
			name = entries[i].getName();
			mdsFile = new MDSFileImpl();
			mdsFile.setName(Util.getUniqueID());
			String fullPath;
			if (!addPath.equals("")) {
				fullPath = addPath + "/" + name;
			} else {
				fullPath = name;
			}
			mdsFile.setPath(fullPath);
			//mdsFile.setPath(addPath + name);
			try {
				f =
					new BufferedReader(
						new FileReader(path + "/" + fullPath));
				while ((line = f.readLine()) != null) {
					content += line + "\n";
				}
				f.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			mdsFile.setContent(content);
			files.add(mdsFile);
		}
		mdsModel.setAdditionalFiles(files);
	}
	
		public MDSMapperBean exportBean() {
		MDSMapperBean bean = super.exportBean();
		bean.setType("xml");
		return bean;
	}
}