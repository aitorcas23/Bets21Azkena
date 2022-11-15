package businesslogic;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businesslogic.BLFacade;
import businesslogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import gui.MainGUI;

public class BLFacadeFactory {

	public BLFacadeFactory() {
		
	}
	public  BLFacade createBLFacade(ConfigXML c) throws MalformedURLException {

		
			BLFacade  appFacadeInterface;

			if (c.isBusinessLogicLocal()) {
				//DataAccess da= new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				DataAccess da = new DataAccess(true);
				System.out.println("\n\nMondongo\n\n");
				return new BLFacadeImplementation(da); 
			}else{
				String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
				 
				//URL url = new URL("http://localhost:9999/ws/ruralHouses?wsdl");
				URL url = new URL(serviceName);

		 
		        //1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
//		        QName qname = new QName("http://businessLogic/", "FacadeImplementationWSService");
		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
		 
		        Service service = Service.create(url, qname);

		         appFacadeInterface = service.getPort(BLFacade.class);	
		         return appFacadeInterface;
			}
	}	 
}
