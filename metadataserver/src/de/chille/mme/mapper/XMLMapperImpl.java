package de.chille.mme.mapper;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;

import de.chille.api.mds.core.MDSModel;
import de.chille.api.mme.mapper.XMLMapper;
import de.chille.mme.MMEGlobals;
import de.chille.mme.core.MetaMappingEngineException;

/**
 * @see XMIHandler
 * 
 * @author Thomas Chille
 */
public class XMLMapperImpl extends MDSMapperImpl implements XMLMapper {

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
		System.out.println(mdsModel.getUmlFile().getContent());

		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer tform =
				tFactory.newTransformer(
					new StreamSource(
						MMEGlobals.MAPPER_PATH
							+ "mapper_"
							+ getId()
							+ "/transform.xsl"));
			tform.transform(
				new StreamSource(
					new ByteArrayInputStream(
						mdsModel.getUmlFile().getContent().getBytes())),
				new StreamResult(
					new FileOutputStream(
						MMEGlobals.TEMP_PATH + "result.java")));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}