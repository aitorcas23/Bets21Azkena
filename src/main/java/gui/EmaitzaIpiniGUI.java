package gui;

import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import businesslogic.BLFacade;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import configuration.UtilDate;
import domain.Event;
import domain.Pronostikoa;
import domain.Question;

public class EmaitzaIpiniGUI extends JFrame {
	LangileaGUI aurrekoa;
	
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelError = new JLabel();
	
	private List<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	private JComboBox<Question> jComboBoxQuestions;
	private DefaultComboBoxModel<Question> questionModel = new DefaultComboBoxModel<Question>();
	
	private JButton jButtonCreate;
	
	Event selectedEvent;
	Question selectedQuestion;
	Pronostikoa selectedPronostic;
	private final JLabel jLabelSucces = new JLabel();
	private final JLabel jLabelErrorDate = new JLabel();
	private final JComboBox<Pronostikoa> jComboBoxPronostics = new JComboBox<Pronostikoa>();
	private DefaultComboBoxModel<Pronostikoa> pronosticModel = new DefaultComboBoxModel<Pronostikoa>();
	
	public EmaitzaIpiniGUI(LangileaGUI aurrekoa) {
		try {
			this.aurrekoa=aurrekoa;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SetResultGUI"));
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				
				questionModel.removeAllElements();
				pronosticModel.removeAllElements();
				selectedEvent = ((domain.Event) jComboBoxEvents.getSelectedItem());
				
				if(selectedEvent!=null) {
					Collection<Question> questions = selectedEvent.getQuestions();
					for(Question q : questions) {
						questionModel.addElement(q);
					}
					if(questions.isEmpty()) {
						jButtonCreate.setEnabled(false);
					}
				}
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

		jLabelError.setBounds(new Rectangle(156, 246, 305, 20));
		jLabelError.setForeground(Color.red);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=(Vector<Date>) facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,(Vector<Date>) datesWithEventsCurrentMonth);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JLabel jLabelListOfQuestions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelListOfQuestions.setBounds(290, 109, 277, 14);
		getContentPane().add(jLabelListOfQuestions);
		
		jComboBoxQuestions = new JComboBox<Question>();
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				pronosticModel.removeAllElements();
				
				selectedQuestion = ((domain.Question) jComboBoxQuestions.getSelectedItem());
				if(selectedQuestion!=null) {
					Collection<Pronostikoa> pronostikoak = selectedQuestion.getPronostics();
					for(Pronostikoa p : pronostikoak) {
						pronosticModel.addElement(p);
					}
					if(pronostikoak.isEmpty()) {
						jButtonCreate.setEnabled(false);
					}
				}else {
					jButtonCreate.setEnabled(false);
				}
			}
		});
		jComboBoxQuestions.setModel(questionModel);
		jComboBoxQuestions.setBounds(275, 134, 250, 22);
		getContentPane().add(jComboBoxQuestions);
		
		jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI.jButtonCreate.text")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				if(selectedQuestion.getResult()!=null) {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("QuestionResult"));
				}else {
					Date eventDate = jCalendar.getDate();
					eventDate = UtilDate.trim(eventDate);
					Date today = new Date();
					if(eventDate.before(today)) {
						BLFacade facade = MainGUI.getBusinessLogic();
						facade.emaitzaIpini(selectedQuestion,selectedPronostic);
						selectedQuestion.setResult(selectedPronostic.getDeskripzioa());
						jLabelSucces.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccessResult"));
					}else {
						jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("EventNotFinished"));
					}
				}
			}
		});
		jButtonCreate.setBounds(new Rectangle(275, 275, 130, 30));
		jButtonCreate.setBounds(135, 275, 130, 30);
		jButtonCreate.setEnabled(false);
		getContentPane().add(jButtonCreate);
		jLabelSucces.setForeground(Color.GREEN);
		jLabelSucces.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelSucces.setBounds(167, 246, 305, 20);
		
		getContentPane().add(jLabelSucces);
		jLabelErrorDate.setForeground(Color.RED);
		jLabelErrorDate.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelErrorDate.setBounds(275, 161, 305, 20);
		
		getContentPane().add(jLabelErrorDate);
		jComboBoxPronostics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				
				selectedPronostic = ((domain.Pronostikoa) jComboBoxPronostics.getSelectedItem());
				if(selectedPronostic!=null) {
					jButtonCreate.setEnabled(true);
				}else {
					jButtonCreate.setEnabled(false);
				}
			}
		});
		
		jComboBoxPronostics.setBounds(275, 213, 250, 22);
		jComboBoxPronostics.setModel(pronosticModel);
		getContentPane().add(jComboBoxPronostics);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EmaitzaIpiniGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(290, 186, 140, 14);
		getContentPane().add(lblNewLabel);

		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
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
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);
						
						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,(Vector<Date>) datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

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