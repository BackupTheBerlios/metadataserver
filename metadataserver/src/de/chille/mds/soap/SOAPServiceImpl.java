package de.chille.mds.soap;

import java.util.Iterator;
import java.util.Vector;

import de.chille.api.mds.core.*;
import de.chille.api.mds.soap.SOAPService;
import de.chille.mds.MDSGlobals;
import de.chille.mds.core.*;
import de.chille.mme.core.MappingImpl;

/**
 * @see SOAPService
 * 
 * @author Thomas Chille
 */
public class SOAPServiceImpl implements SOAPService {

	/**
	 * der SOAPServer kennt den MDS
	 */
	private static MetaDataServer metaDataServer;

	public static void main(String[] args) throws Exception {
		/*
				MDSClassBean end1 = new MDSClassBean();
				end1.setLabel("class005");
				end1.setHref("mds://server_0/repository_0_2/model_0_2_0");
				end1.setId("0_2_0_1");
		
				MDSClassBean end2 = new MDSClassBean();
				end2.setLabel("class006");
				end2.setHref("mds://server_0/repository_0_2/model_0_2_0");
				end2.setId("0_2_0_0");
		
				MDSAssociationEndBean bean1 = new MDSAssociationEndBean();
				bean1.setMdsClass(end1);
				bean1.setAggregation(1);
		
				MDSAssociationEndBean bean2 = new MDSAssociationEndBean();
				bean2.setMdsClass(end2);
				bean2.setAggregation(2);
		
				Vector ends = new Vector();
				ends.add(bean1);
				ends.add(bean2);
		
				MDSAssociationBean bean = new MDSAssociationBean();
				bean.setAssociationEnds(ends);
				bean.setLabel("assososososso");*/
		validateModel("mds://server_0/repository_0_1/model_0_1_0", 0);
	}

	public void test(String href) {
		try {
			MetaDataServerImpl.getInstance().convertModel(
			new MDSHrefImpl(href),
			new MappingImpl("mds", "java"));
		} catch (Exception e) {
			MDSGlobals.log(e.getMessage());
		}
	}

	public Vector query() {
		metaDataServer = MetaDataServerImpl.getInstance();
		Vector repositoryBeans = new Vector();
		Iterator iter = metaDataServer.getRepositories().iterator();
		while (iter.hasNext()) {
			repositoryBeans.add(((MDSRepositoryImpl) iter.next()).exportBean());
		}
		return repositoryBeans;
	}

	public Vector getServerInfo() {
		metaDataServer = MetaDataServerImpl.getInstance();
		Vector info = new Vector();
		info.add(metaDataServer.getId());
		info.add(metaDataServer.getLabel());
		info.add(metaDataServer.getHref().getHrefString());
		return info;
	}

	public static MDSRepositoryBean createRepository(MDSRepositoryBean bean) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSRepository mdsRepository = new MDSRepositoryImpl();
		mdsRepository.importBean(bean);
		metaDataServer.insertReposititory(mdsRepository);
		return mdsRepository.exportBean();
	}

	public static MDSModelBean createModel(String href, MDSModelBean bean) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSModel mdsModel = new MDSModelImpl();
		mdsModel.importBean(bean);
		try {
			metaDataServer.insertModel(new MDSHrefImpl(href), mdsModel);

		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return mdsModel.exportBean();
	}

	public static MDSClassBean createClass(String href, MDSClassBean bean) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSClass mdsClass = new MDSClassImpl();
		mdsClass.importBean(bean);
		try {
			metaDataServer.insertElement(new MDSHrefImpl(href), mdsClass);

		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return mdsClass.exportBean();
	}

	public static void updateElement(MDSObjectBean bean) {
		try {
			MDSHref mdshref = new MDSHrefImpl(bean.getHref());
			if (bean instanceof MDSClassBean) {
				(
					(MDSClassImpl) MetaDataServerImpl
						.getInstance()
						.getRepositoryByHref(mdshref)
						.getModelByHref(mdshref)
						.getElementById(mdshref))
						.importBean(
					(MDSClassBean) bean);
				MetaDataServerImpl
					.getInstance()
					.getRepositoryByHref(mdshref)
					.getModelByHref(mdshref)
					.touch();
			} else if (bean instanceof MDSAssociationBean) {
				(
					(MDSAssociationImpl) MetaDataServerImpl
						.getInstance()
						.getRepositoryByHref(mdshref)
						.getModelByHref(mdshref)
						.getElementById(mdshref))
						.importBean(
					(MDSAssociationBean) bean);
				MetaDataServerImpl
					.getInstance()
					.getRepositoryByHref(mdshref)
					.getModelByHref(mdshref)
					.touch();
			} else if (bean instanceof MDSGeneralizationBean) {
				(
					(MDSGeneralizationImpl) MetaDataServerImpl
						.getInstance()
						.getRepositoryByHref(mdshref)
						.getModelByHref(mdshref)
						.getElementById(mdshref))
						.importBean(
					(MDSGeneralizationBean) bean);
				MetaDataServerImpl
					.getInstance()
					.getRepositoryByHref(mdshref)
					.getModelByHref(mdshref)
					.touch();
			} else if (bean instanceof MDSRepositoryBean) {
				MetaDataServerImpl.getInstance().getRepositoryByHref(
					mdshref).importBean(
					(MDSRepositoryBean) bean);
			} else if (bean instanceof MDSModelBean) {
				MetaDataServerImpl
					.getInstance()
					.getRepositoryByHref(mdshref)
					.getModelByHref(mdshref)
					.importBean((MDSModelBean) bean);
				MetaDataServerImpl
					.getInstance()
					.getRepositoryByHref(mdshref)
					.getModelByHref(mdshref)
					.touch();
			}
			MetaDataServerImpl.getInstance().save();
			//return true;
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
			//return false;
		} catch (MDSCoreException e) {
			e.printStackTrace();
			//return true;
		}
	}

	public static MDSGeneralizationBean createGeneralization(
		String href,
		MDSGeneralizationBean bean) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSGeneralization mdsGeneralization = new MDSGeneralizationImpl();
		mdsGeneralization.importBean(bean);
		try {
			metaDataServer.insertElement(
				new MDSHrefImpl(href),
				mdsGeneralization);

		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return mdsGeneralization.exportBean();
	}

	public static MDSAssociationBean createAssociation(
		String href,
		MDSAssociationBean bean) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSAssociation mdsAssociation = new MDSAssociationImpl();
		mdsAssociation.importBean(bean);
		try {
			metaDataServer.insertElement(new MDSHrefImpl(href), mdsAssociation);

		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return mdsAssociation.exportBean();
	}

	public static Vector validateModel(String href, int type) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSHref mdsHref = null;
		try {
			return metaDataServer.validateModel(new MDSHrefImpl(href), type);
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Vector delete(String href, boolean confirm) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSHref mdsHref = null;
		Vector result = new Vector();
		try {
			mdsHref = new MDSHrefImpl(href);
		} catch (MDSHrefFormatException e) {
			e.printStackTrace();
		}
		try {
			// element
			mdsHref.getElementId();
			result = metaDataServer.removeElement(mdsHref, confirm);
		} catch (MDSHrefFormatException e) {
			// model
			try {
				mdsHref.getModelId();
				result = metaDataServer.removeModel(mdsHref, confirm);
			} catch (MDSHrefFormatException e1) {
				// repository
				result = metaDataServer.removeRepository(mdsHref, confirm);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
