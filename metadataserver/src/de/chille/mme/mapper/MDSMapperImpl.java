package de.chille.mme.mapper;

import de.chille.api.mds.core.MDSModel;
import de.chille.api.mme.core.Mapping;
import de.chille.api.mme.mapper.MDSMapper;
import de.chille.mds.core.*;
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

	private static int counter = 0;

	public MDSMapperImpl(String id) {
		try {
			setId(id == null ? "" + counter++ : id);
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
	public static int getCounter() {
		return counter;
	}

	/**
	 * Sets the counter.
	 * @param counter The counter to set
	 */
	public static void setCounter(int counter) {
		MDSMapperImpl.counter = counter;
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

}