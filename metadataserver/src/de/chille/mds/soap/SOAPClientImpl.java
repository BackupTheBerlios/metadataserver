package de.chille.mds.soap;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.soap.Constants;
import org.apache.soap.Fault;
import org.apache.soap.SOAPException;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;

public class SOAPClientImpl {

	static String DEFAULT_ENDPOINT =
		"http://localhost:8080/soap/servlet/rpcrouter";

	public static void main(String[] args) throws Exception {
		System.out.println(call());
	}

	public static String call() {

		String endPoint = DEFAULT_ENDPOINT;
		String ret = "return";
		
		Call call = new Call();
		call.setTargetObjectURI("urn:MetaDataServerService");
		call.setMethodName("test");
		call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

		Response resp = null;
		
		try {
			URL url = new URL(endPoint);

			resp = call.invoke(url, "");
		} catch (MalformedURLException e) {
		} catch (SOAPException e) {
		}

		if (resp.generatedFault()) {
			Fault fault = resp.getFault();
			ret = "Fehler: ";
			ret += "\tCode =" + fault.getFaultCode();
			ret += "\tString =" + fault.getFaultString();
		} else {
			Parameter result = resp.getReturnValue();
			ret += result.getValue();
		}
		return ret;
	}
}
