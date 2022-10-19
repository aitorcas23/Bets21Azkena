package gui;


import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import businesslogic.BLFacade;

import java.awt.*;
import java.awt.event.*;

import configuration.UtilDate;
import domain.Event;
import exceptions.EventAlreadyExist;


public class CreateEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField jEventDescription = new JTextField();
	private JCalendar jCalendar = new JCalendar();

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelError1 = new JLabel();
	private JLabel jLabelError2 = new JLabel();
	private JLabel jLabelSucces;
	
	private LangileaGUI aurrekoLangilea;
	private final JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Description")); //$NON-NLS-1$ //$NON-NLS-2$
	
	public CreateEventGUI(LangileaGUI aurrekoLangilea) {
		try {
			this.aurrekoLangilea=aurrekoLangilea;
			CreateEventGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void CreateEventGUIExekuzioa() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEventGUI"));
		jEventDescription.setBounds(new Rectangle(119, 211, 429, 18));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonClose.setBounds(new Rectangle(349, 276, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError1.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelError1.setForeground(Color.red);
		this.getContentPane().add(jLabelError1, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jEventDescription, null);

		this.getContentPane().add(jCalendar, null);	

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(25, 11, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError1.setText("");
				jLabelError2.setText("");
				jLabelSucces.setText("");
				
				String description = jEventDescription.getText();
				
				if(description.length()>0) {
					Date eventDate = jCalendar.getDate();
					eventDate = UtilDate.trim(eventDate);
					Date today = new Date();
					if(eventDate.after(today)) {
						try {
							BLFacade facade = MainGUI.getBusinessLogic();
							facade.createEvent(description,eventDate);
							jLabelSucces.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccesEvent"));
						}catch (EventAlreadyExist exp){
							jLabelError1.setText(ResourceBundle.getBundle("Etiquetas").getString("EventExist"));
						}
					}else {
						jLabelError2.setText(ResourceBundle.getBundle("Etiquetas").getString("DatePased"));
					}
				}else {
					jLabelError1.setText(ResourceBundle.getBundle("Etiquetas").getString("DescriptionEmpty"));
				}
				
			}
		});
		btnNewButton.setBounds(100, 276, 165, 30);
		getContentPane().add(btnNewButton);
		
		jLabelError2 = new JLabel();
		jLabelError2.setForeground(Color.RED);
		jLabelError2.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelError2.setBounds(283, 180, 305, 20);
		getContentPane().add(jLabelError2);
		
		jLabelSucces = new JLabel();
		jLabelSucces.setForeground(Color.GREEN);
		jLabelSucces.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelSucces.setBounds(164, 240, 305, 20);
		getContentPane().add(jLabelSucces);
		lblNewLabel.setBounds(25, 213, 90, 14);
		
		getContentPane().add(lblNewLabel);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.aurrekoLangilea.setVisible(true);
		this.setVisible(false);
	}
}