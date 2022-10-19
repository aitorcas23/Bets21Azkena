package businesslogic;

import javax.xml.ws.Endpoint;

import configuration.ConfigXML;

public class BusinessLogicServerAWS {
		
	public static void main(String args[]){

		String service;
		
		ConfigXML c=ConfigXML.getInstance();


		if (c.isBusinessLogicLocal()) {
			System.out.println("\nERROR, the business logic is configured as local");
		}
		else {
		try {
			
			if (!c.isDatabaseLocal()) {
				System.out.println("\nWARNING: Please be sure ObjectdbManagerServer is launched\n           in machine: "+c.getDatabaseNode()+" port: "+c.getDatabasePort()+"\n");	
			}

			service= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName();
				
			Endpoint.publish(service, new BLFacadeImplementation());

			System.out.println("\n\nRunning service at:\n\t" + service);
			System.out.println("\n\n\nPress button to exit this server... ");
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	 }
	}
}
