package mds.core;

import java.util.*;
import mme.core.*;

/**
 * @see MetaDataServer
 * 
 * @author Thomas Chille
 */
public class MetaDataServerImpl implements MetaDataServer 
{
	/**
	 * @see MetaDataServer#createReposititory(String)
	 */
	public String createReposititory(String label) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#deleteRepository(String)
	 */
	public boolean deleteRepository(String href) 
	{
		return false;
	}

	/**
	 * @see MetaDataServer#renameRepository(String, String)
	 */
	public boolean renameRepository(String href, String label) 
	{
		return false;
	}

	/**
	 * @see MetaDataServer#queryRepository(String, String)
	 */
	public ArrayList queryRepository(String href, String query) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#insertModel(String, MDSModel)
	 */
	public String insertModel(String href, MDSModel mdsModel) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#removeModel(String)
	 */
	public boolean removeModel(String href) 
	{
		return false;
	}

	/**
	 * @see MetaDataServer#moveModel(String, String)
	 */
	public String moveModel(String from, String to) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#copyModel(String, String, String)
	 */
	public String copyModel(String from, String to, String label) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#renameModel(String, String)
	 */
	public boolean renameModel(String href, String label) 
	{
		return false;
	}

	/**
	 * @see MetaDataServer#getModelVersions(String)
	 */
	public ArrayList getModelVersions(String href) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#restoreModel(String, String)
	 */
	public boolean restoreModel(String href, String version) 
	{
		return false;
	}

	/**
	 * @see MetaDataServer#insertElement(String, MDSElement)
	 */
	public String insertElement(String href, MDSElement mdsElement) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#removeElement(String)
	 */
	public boolean removeElement(String href) 
	{
		return false;
	}

	/**
	 * @see MetaDataServer#moveElement(String, String)
	 */
	public String moveElement(String from, String to) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#copyElement(String, String, String)
	 */
	public String copyElement(String from, String to, String label) {
		return null;
	}

	/**
	 * @see MetaDataServer#validateModel(String, String)
	 */
	public ArrayList validateModel(String href, String validateType) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#importModel(String, MDSModel, Mapping)
	 */
	public String importModel(String label, MDSModel mdsModel, Mapping mapping) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#exportModel(String, Mapping)
	 */
	public MDSModel exportModel(String href, Mapping mapping) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#registerMapping(MappingResource)
	 */
	public Mapping registerMapping(MappingResource mappingResource) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#unregisterMapping(Mapping)
	 */
	public boolean unregisterMapping(Mapping mapping) 
	{
		return false;
	}

	/**
	 * @see MetaDataServer#getMappings(String, String)
	 */
	public ArrayList getMappings(String from, String to) 
	{
		return null;
	}

	/**
	 * @see MetaDataServer#convertModel(String, Mapping, String)
	 */
	public String convertModel(String href, Mapping mapping, String label) 
	{
		return null;
	}
}

