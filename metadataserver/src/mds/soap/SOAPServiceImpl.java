package mds.soap;

import api.mds.core.MetaDataServer;
import api.mds.soap.SOAPService;

/**
 * @see SOAPService
 * 
 * @author Thomas Chille
 */
public class SOAPServiceImpl implements SOAPService {
	
	/**
	 * der SOAPServer kennt den MDS
	 */
	private MetaDataServer metaDataServer = null;
		
	/**
	 * Gets the metaDataServer
	 * @return Returns a MetaDataServer
	 */
	public MetaDataServer getMetaDataServer() {
		return metaDataServer;
	}
	
	/**
	 * Sets the metaDataServer
	 * @param metaDataServer The metaDataServer to set
	 */
	public void setMetaDataServer(MetaDataServer metaDataServer) {
		this.metaDataServer = metaDataServer;
	}
}

