package de.chille.mds.soap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;

import de.chille.api.mds.core.*;
import de.chille.api.mds.soap.SOAPService;
import de.chille.api.mme.mapper.MDSMapper;
import de.chille.mds.MDSGlobals;
import de.chille.mds.core.*;
import de.chille.mme.core.MappingImpl;
import de.chille.mme.core.MetaMappingEngineException;
import de.chille.mme.mapper.UnicodeMapperImpl;
import de.chille.mme.mapper.XMLMapperImpl;

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

		/*MDSMapperBean mapper = new MDSMapperBean();
		mapper.setFrom("java");
		mapper.setTo("mds");
		mapper.setLabel("javs2mds");
		mapper.setType("unicode");
		MDSFileBean file = new MDSFileBean();

		BufferedReader f;
		String line = null;
		String content = "";
		f = new BufferedReader(new FileReader("C:/Programme/eclipse/workspace/metadata.server/src/de/chille/mme/mapper/JavaParser.jj"));
		while ((line = f.readLine()) != null) {
			content += line + "\n";
		}
		f.close();
			
		file.setContent(content);
		mapper.setFile(file);
		System.out.println(registerMapper(mapper));*/
		
		convertModel("mds://server_0/repository_0_6/model_0_6_6", "java", "mds");
	}

	public static MDSModelBean convertModel(
		String href,
		String mapFrom,
		String mapTo)
		throws MetaMappingEngineException {
		
		MDSModelBean model = new MDSModelBean();
		try {
			model = MetaDataServerImpl
				.getInstance()
				.convertModel(
					new MDSHrefImpl(href),
					new MappingImpl(mapFrom, mapTo))
				.exportBean();
		} catch (MetaMappingEngineException e) {
			model.setLabel(e.getMessage());
			model.setHref(e.getSrc());
			model.setId("error");
		} catch (Exception e) {
			model.setLabel(e.toString());
			model.setHref("exception");
			model.setId("error");
		}
		return model;
	}

	public static String copyModel(String from, String to, String label) {
		try {
			return MetaDataServerImpl
				.getInstance()
				.copyModel(new MDSHrefImpl(from), new MDSHrefImpl(to), label)
				.getHrefString();
		} catch (Exception e) {
			MDSGlobals.log(e.getMessage());
			return null;
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

	public static String registerMapper(MDSMapperBean mapperBean) {
		metaDataServer = MetaDataServerImpl.getInstance();
		MDSMapper mapper;
		if (mapperBean.getType().equals("xml")) {
			mapper = new XMLMapperImpl(null);
		} else {
			mapper = new UnicodeMapperImpl(null);
		}
		mapper.importBean(mapperBean);
		return metaDataServer.registerMapper(mapper);
	}
}
