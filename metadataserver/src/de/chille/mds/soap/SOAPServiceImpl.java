package de.chille.mds.soap;

import de.chille.mds.MDSGlobals;
import de.chille.mds.core.MDSModelImpl;
import de.chille.mds.core.MDSRepositoryImpl;
import de.chille.mds.core.MetaDataServerImpl;

import de.chille.api.mds.core.MDSHref;
import de.chille.api.mds.core.MDSModel;
import de.chille.api.mds.core.MDSRepository;
import de.chille.api.mds.core.MetaDataServer;
import de.chille.api.mds.soap.SOAPService;

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

	public String test() {
		String ret = "";
		try {
			MDSGlobals.log("test");
			metaDataServer = MetaDataServerImpl.getInstance();
			MDSRepository rep = new MDSRepositoryImpl();
			//MDSHref rhref = metaDataServer.insertReposititory(rep);
			//rep.save();
			ret = metaDataServer.toString();
		} catch (Exception e) {
			MDSGlobals.log(e.getMessage());
		}
		return ret;
	}
}
