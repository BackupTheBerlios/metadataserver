package de.chille.mds.soap;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.util.xml.QName;

import de.chille.api.mds.core.MDSAssociationEnd;
import de.chille.api.mds.core.MDSGeneralization;

public class SOAPClientImpl {

	static String endPoint = "http://localhost:9000/soap/servlet/rpcrouter";

	public static void main(String[] args) throws Exception {

		MDSClassBean mdsClass = new MDSClassBean();
		mdsClass.setLabel("neueklasse");
		String[] paramName = { "href", "class" };
		Class[] paramType = { String.class, MDSClassBean.class };
		Object[] paramValue = { "mds://server_0/repository_0_1/model_0_1_5", mdsClass };
		call("createClass", paramName, paramType, paramValue);
	}

	public static Object call(
		String methodName,
		String[] paramName,
		Class[] paramType,
		Object[] paramValue)
		throws Exception {

		SOAPMappingRegistry srm = new SOAPMappingRegistry();
		BeanSerializer beanSer = new BeanSerializer();

		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "repository"),
			MDSRepositoryBean.class,
			beanSer,
			beanSer);
		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "model"),
			MDSModelBean.class,
			beanSer,
			beanSer);
		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "file"),
			MDSFileBean.class,
			beanSer,
			beanSer);
		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "class"),
			MDSClassBean.class,
			beanSer,
			beanSer);
		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "association"),
			MDSAssociationBean.class,
			beanSer,
			beanSer);
		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "generalization"),
			MDSGeneralizationBean.class,
			beanSer,
			beanSer);
		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "associationend"),
			MDSAssociationEndBean.class,
			beanSer,
			beanSer);
		srm.mapTypes(
			Constants.NS_URI_SOAP_ENC,
			new QName("urn:mds", "class"),
			MDSClassBean.class,
			beanSer,
			beanSer);

		Call call = new Call();

		if (paramName != null) {
			Vector params = new Vector();
			for (int i = 0; i < paramName.length; i++) {
				params.addElement(
					new Parameter(
						paramName[i],
						paramType[i],
						paramValue[i],
						null));
			}
			call.setParams(params);
		}

		call.setSOAPMappingRegistry(srm);
		call.setTargetObjectURI("urn:MetaDataServerService");
		call.setMethodName(methodName);
		call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

		Response resp = null;

		try {
			URL url = new URL(endPoint);

			resp = call.invoke(url, "");
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (SOAPException e) {
			e.printStackTrace();
		}

		if (resp.generatedFault()) {
			Fault fault = resp.getFault();
			String ret = "Fehler: ";
			ret += "\tCode =" + fault.getFaultCode();
			ret += "\tString =" + fault.getFaultString();
			throw new Exception(ret);
		} else {
			try {
				Parameter param = resp.getReturnValue();
				return param.getValue();
			} catch (RuntimeException e) {
				return null;
			}
		}
	}
	/**
	 * Sets the endPoint.
	 * @param endPoint The endPoint to set
	 */
	public static void setEndPoint(String endPoint) {
		SOAPClientImpl.endPoint = endPoint;
	}

}
