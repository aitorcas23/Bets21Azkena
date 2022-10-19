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
import domain.Question;
import exceptions.PronosticAlreadyExist;

public class CreatePronosticGUI extends JFrame {
	LangileaGUI aurrekoa;

	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField jPronosticDescription = new JTextField();
	private JTextField jTextFieldFee = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();

	private List<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JComboBox<Question> jComboBoxQuestions;
	private DefaultComboBoxModel<Question> questionModel = new DefaultComboBoxModel<Question>();

	private JButton jButtonCreate;

	Event selectedEvent;
	Question selectedQuestion;
	private final JLabel jLabelSucces = new JLabel();
	private final JLabel jLabelErrorDescription = new JLabel();
	private final JLabel jLabelErrorDate = new JLabel();
	private final JLabel lblNewLabel = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("Description")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblNewLabel_1 = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("Fee")); //$NON-NLS-1$ //$NON-NLS-2$

	public CreatePronosticGUI(LangileaGUI aurrekoa) {
		try {
			this.aurrekoa = aurrekoa;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI"));
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelErrorDescription.setText("");
				jLabelSucces.setText("");

				questionModel.removeAllElements();
				selectedEvent = ((domain.Event) jComboBoxEvents.getSelectedItem());

				if (selectedEvent != null) {
					Collection<Question> questions = selectedEvent.getQuestions();
					for (Question q : questions) {
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
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jPronosticDescription.setBounds(new Rectangle(100, 211, 429, 20));
		jTextFieldFee.setBounds(new Rectangle(100, 243, 60, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonClose.setBounds(new Rectangle(300, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jPronosticDescription, null);
		this.getContentPane().add(jTextFieldFee, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
		System.out.println(jCalendar.getDate()+ "<------------------");
		paintDaysWithEvents(jCalendar, (Vector<Date>) datesWithEventsCurrentMonth);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);

		JLabel jLabelListOfQuestions = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("jLabelListOfQuestions")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelListOfQuestions.setBounds(290, 109, 277, 14);
		getContentPane().add(jLabelListOfQuestions);

		jComboBoxQuestions = new JComboBox<Question>();
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelErrorDescription.setText("");
				jLabelSucces.setText("");
				selectedQuestion = ((domain.Question) jComboBoxQuestions.getSelectedItem());
				if (selectedQuestion != null) {
					jButtonCreate.setEnabled(true);
				} else {
					jButtonCreate.setEnabled(false);
				}
			}
		});
		jComboBoxQuestions.setModel(questionModel);
		jComboBoxQuestions.setBounds(275, 134, 250, 22);
		getContentPane().add(jComboBoxQuestions);

		jButtonCreate = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("CreatePronosticGUI.jButtonCreate.text")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelErrorDescription.setText("");
				jLabelSucces.setText("");
				String description = jPronosticDescription.getText();
				double fee = 0;
				boolean feeEgokia = false;
				try {
					fee =Double.valueOf(jTextFieldFee.getText());
					if (fee > 1) {
						feeEgokia = true;
					}
				} catch (Exception e) {
				}
				if (feeEgokia) {
					if (description.length() > 0) {
						Date eventDate = jCalendar.getDate();
						eventDate = UtilDate.trim(eventDate);
						Date today = new Date();
						if (eventDate.after(today)) {
							try {
								BLFacade facade = MainGUI.getBusinessLogic();
								facade.createPronostic(selectedQuestion, description, fee);
								jLabelSucces.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccessPronostic"));
							} catch (PronosticAlreadyExist exp) {
								jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("PronosticExist"));
							}
						} else {
							jLabelErrorDate.setText(ResourceBundle.getBundle("Etiquetas").getString("DatePased"));
						}
					} else {
						jLabelErrorDescription.setText(ResourceBundle.getBundle("Etiquetas").getString("DescriptionEmpty"));
					}
				} else {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("InvalidFee"));
				}
			}
		});
		jButtonCreate.setBounds(new Rectangle(275, 275, 130, 30));
		jButtonCreate.setBounds(135, 275, 130, 30);
		getContentPane().add(jButtonCreate);
		jLabelSucces.setForeground(Color.GREEN);
		jLabelSucces.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelSucces.setBounds(175, 240, 305, 20);

		getContentPane().add(jLabelSucces);
		jLabelErrorDescription.setForeground(Color.RED);
		jLabelErrorDescription.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelErrorDescription.setBounds(275, 182, 305, 20);

		getContentPane().add(jLabelErrorDescription);
		jLabelErrorDate.setForeground(Color.RED);
		jLabelErrorDate.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelErrorDate.setBounds(275, 161, 305, 20);

		getContentPane().add(jLabelErrorDate);
		lblNewLabel.setBounds(10, 214, 80, 14);

		getContentPane().add(lblNewLabel);
		lblNewLabel_1.setBounds(10, 246, 46, 14);

		getContentPane().add(lblNewLabel_1);

		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelErrorDescription.setText("");
				jLabelSucces.setText("");
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: " + calendarAnt.getTime());
					System.out.println("calendarAct: " + calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente,
							// devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar.getDate());
					}

					paintDaysWithEvents(jCalendar, (Vector<Date>) datesWithEventsCurrentMonth);

					// Date firstDay = UtilDate.trim(new
					// Date(jCalendar.getCalendar().getTime().getTime()));
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

	public static void paintDaysWithEvents(JCalendar jCalendar, Vector<Date> datesWithEventsCurrentMonth) {
		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

//		if (Locale.getDefault().equals(new Locale("es")))
//			offset += 4;
//		else
			offset += 5;

		for (Date d : datesWithEventsCurrentMonth) {
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