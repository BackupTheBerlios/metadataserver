package de.chille.mme.mapper;

import de.chille.api.mds.core.MDSFile;
import de.chille.api.mds.core.MDSModel;
import de.chille.api.mme.core.Mapping;
import de.chille.api.mme.mapper.MDSMapper;
import de.chille.mds.core.*;
import de.chille.mds.soap.MDSMapperBean;
import de.chille.mme.core.MappingImpl;
import de.chille.mme.core.MetaMappingEngineException;

/**
 * @see MDSMapper
 * 
 * @author Thomas Chille
 */
abstract class MDSMapperImpl extends MDSObjectImpl implements MDSMapper {

	/**
	 * das von diesem Mapper durchführbare Mapping
	 */
	private Mapping mapping = null;

	/**
	 * stylsheet oder grammarfile
	 */
	private MDSFile mappingFile = null;
	
	private static int counter = 0;

	public MDSMapperImpl(String id) {
		try {
			setId(id == null ? "" + ++counter : id);
			setHref(
				new MDSHrefImpl(
					MetaDataServerImpl.getInstance().getHref().getHrefString()
						+ "/mapper_"
						+ getId()));
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the mapping
	 * @return Returns a Mapping
	 */
	public Mapping getMapping() {
		return mapping;
	}

	/**
	 * Sets the mapping
	 * @param mapping The mapping to set
	 */
	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}
	/**
	 * Returns the counter.
	 * @return int
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Sets the counter.
	 * @param counter The counter to set
	 */
	public void setCounter(int newCounter) {
		counter = newCounter;
	}

	/**
	 * @see de.chille.api.mme.mapper.MDSMapper#map(MDSModel)
	 */
	public void map(MDSModel mdsModel) throws MetaMappingEngineException {
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getLabel()
			+ ": "
			+ getMapping().getFrom()
			+ " >> "
			+ getMapping().getTo();
	}

	/**
	 * Gets the mappingFile
	 * @return Returns a MDSFile
	 */
	public MDSFile getMappingFile() {
		return mappingFile;
	}

	/**
	 * Sets the mappingFile
	 * @param mappingFile The mappingFile to set
	 */
	public void setMappingFile(MDSFile mappingFile) {
		this.mappingFile = mappingFile;
	}

	public MDSMapperBean exportBean() {
		MDSMapperBean bean = new MDSMapperBean();
		bean.setLabel(this.getLabel());
		bean.setFrom(this.getMapping().getFrom());
		bean.setTo(this.getMapping().getTo());
		bean.setFile(this.getMappingFile().exportBean());
		return bean;
	}
	
	
	/**
	 * @see de.chille.api.mds.core.MDSFile#importBean(MDSFileBean)
	 */
	public void importBean(MDSMapperBean bean) {
		this.setLabel(bean.getLabel());
		Mapping mapping = new MappingImpl();
		mapping.setFrom(bean.getFrom());
		mapping.setTo(bean.getTo());
		this.setMapping(mapping);
		MDSFile file = new MDSFileImpl();
		file.importBean(bean.getFile());
		this.setMappingFile(file);
	}


}