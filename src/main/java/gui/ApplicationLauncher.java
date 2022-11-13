package gui;

import java.awt.Color;

import java.util.Locale;



import businesslogic.BLFacadeFactory;

import configuration.ConfigXML;


public class ApplicationLauncher { 
	
	
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();
	
		System.out.println(c.getLocale());
		
		Locale.setDefault(new Locale(c.getLocale()));
		
		System.out.println("Locale: "+Locale.getDefault());
		
		MainGUI a=new MainGUI();
		a.setVisible(true);
		
		


		try {
			BLFacadeFactory blFacade = new BLFacadeFactory();

			MainGUI.setBussinessLogic(blFacade.createBLFacade(c));

			
		}catch (Exception e) {
			a.jLabelSelectOption.setText("Error: "+e.toString());
			a.jLabelSelectOption.setForeground(Color.RED);	
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		//a.pack();


	}

}
