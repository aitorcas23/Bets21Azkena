package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.toedter.calendar.JCalendar;

import businesslogic.BLFacade;
import configuration.UtilDate;
import domain.Event;

public class GertaeraEzabatuGUI extends JFrame {
	private static final long serialVersionUID = 1L;


	private LangileaGUI aurrekoa;


	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate")); //$NON-NLS-1$ //$NON-NLS-2$
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelError = new JLabel();
	
	private List<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	Event selectedEvent;
	private final JLabel jLabelErrorDate = new JLabel();
	
	private Date firstDay;
	private final JLabel jLabelMsg = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GertaeraEzabatuGUI.jLabelMsg.text")); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Launch the application.
	 */	
	public GertaeraEzabatuGUI(LangileaGUI aurrekoa) {
		try {
			this.aurrekoa=aurrekoa;
			gertaeraEzabatuGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	private void gertaeraEzabatuGUIExekuzioa() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RemoveEventGUI"));
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				selectedEvent = ((domain.Event) jComboBoxEvents.getSelectedItem());			
			}
		});

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 21, 277, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonClose.setBounds(new Rectangle(300, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelError.setBounds(new Rectangle(125, 244, 305, 20));
		jLabelError.setForeground(Color.red);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,(Vector<Date>) datesWithEventsCurrentMonth);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JButton btnDeleteEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Remove")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeleteEvent.setEnabled(false);
		btnDeleteEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				Date eventDate = jCalendar.getDate();
				eventDate = UtilDate.trim(eventDate);
				Date today = new Date();
				if(eventDate.after(today)) {
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.ezabatuGertaera(selectedEvent);
					jLabelMsg.setForeground(Color.GREEN);
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccessEvent"));
					/* Orain datorren kodea eventuen comboBox-a eguneratzen du */
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					List<Event> events = facade.getEvents(firstDay);

					if (events.isEmpty())
						jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
								+ ": " + dateformat1.format(calendarAct.getTime()));
					else
						jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
								+ dateformat1.format(calendarAct.getTime()));
					jComboBoxEvents.removeAllItems();
					System.out.println("Events " + events);

					for (domain.Event ev : events)
						modelEvents.addElement(ev);
					jComboBoxEvents.repaint();
				}else {
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("EventFinished"));
				}
			}
		});
		
		btnDeleteEvent.setBounds(80, 277, 156, 30);
		getContentPane().add(btnDeleteEvent);
		jLabelErrorDate.setForeground(Color.RED);
		jLabelErrorDate.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelErrorDate.setBounds(275, 161, 305, 20);
		
		getContentPane().add(jLabelErrorDate);
		jLabelMsg.setBounds(114, 240, 322, 24);
		
		getContentPane().add(jLabelMsg);

		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);
						
						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,(Vector<Date>) datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						List<Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							btnDeleteEvent.setEnabled(false);
						else
							btnDeleteEvent.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
	}

	
public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

//		if (Locale.getDefault().equals(new Locale("es")))
//			offset += 4;
//		else
			offset += 5;
		
	 	for (Date d:datesWithEventsCurrentMonth){
	 		calendar.setTime(d);
	 		System.out.println(d);
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		aurrekoa.setVisible(true);
	}
}
