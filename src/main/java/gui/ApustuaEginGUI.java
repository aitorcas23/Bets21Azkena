package gui;

import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businesslogic.BLFacade;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import configuration.UtilDate;
import domain.Bezeroa;
import domain.Event;
import domain.Pronostikoa;
import domain.Question;

public class ApustuaEginGUI extends JFrame {
	BezeroaGUI aurrekoa;
	Bezeroa bezeroa;
	
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel();
	private JLabel jLabelEventDate = new JLabel();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose = new JButton();
	private JLabel jLabelError = new JLabel();
	private JButton addButton;
	
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
	private JTextField textFieldBet;
	private final JLabel jLabelMinBet = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jLabelMsg = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	private JScrollPane scrollPaneApustua = new JScrollPane();
	private JTable tableApustua = new JTable();
	private DefaultTableModel tableModelApustua;
	
	private String[] columnNamesApustua = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Date"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("Result"),
			ResourceBundle.getBundle("Etiquetas").getString("Kuota")
	};
	
	private Integer pronosI;
	private Vector<Pronostikoa> pronostikoak = new Vector<Pronostikoa>();
	private JLabel pronosticAdded;
	private JButton quitButton;
	private final JLabel euro = new JLabel("\u20AC");
	private final JLabel TotalMinBet = new JLabel("");
	private final JLabel TotalFee = new JLabel("");
	private double minBetTotala;
	private double kuotaTotala;
	
	public ApustuaEginGUI(BezeroaGUI aurrekoa, Bezeroa bezeroa) {
		try {
			this.aurrekoa=aurrekoa;
			this.bezeroa=bezeroa;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//XD que cojones pq es tan largo
	
	private void jbInit() throws Exception {
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 600));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MakeBet")); //$NON-NLS-1$ //$NON-NLS-2$
		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				pronosticAdded.setText("");
				
				questionModel.removeAllElements();
				pronosticModel.removeAllElements();
				selectedEvent = ((domain.Event) jComboBoxEvents.getSelectedItem());
				
				if(selectedEvent!=null) {
					Collection<Question> questions = selectedEvent.getQuestions();
					for(Question q : questions) {
						questionModel.addElement(q);
					}
				}
			}
		});

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("ListEvents")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelListOfEvents.setBounds(new Rectangle(290, 21, 277, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonClose.setText(ResourceBundle.getBundle("Etiquetas").getString("jButtonClose")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonClose.setBounds(new Rectangle(300, 522, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelError.setBounds(new Rectangle(161, 491, 305, 20));
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
		
		JLabel jLabelListOfQuestions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("jLabelListOfQuestions")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelListOfQuestions.setBounds(290, 109, 277, 14);
		getContentPane().add(jLabelListOfQuestions);
		
		jComboBoxQuestions = new JComboBox<Question>();
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				pronosticModel.removeAllElements();
				pronosticAdded.setText("");
				
				selectedQuestion = ((domain.Question) jComboBoxQuestions.getSelectedItem());
				if(selectedQuestion!=null) {
					Collection<Pronostikoa> pronostikoak = selectedQuestion.getPronostics();
					for(Pronostikoa p : pronostikoak) {
						pronosticModel.addElement(p);
					}
					if(pronostikoak.isEmpty()) {
						addButton.setEnabled(false);
					}
				}else {
					addButton.setEnabled(false);
				}
			}
		});
		jComboBoxQuestions.setModel(questionModel);
		jComboBoxQuestions.setBounds(275, 134, 250, 22);
		getContentPane().add(jComboBoxQuestions);
		
		jButtonCreate=new JButton(ResourceBundle.getBundle("Etiquetas").getString("jButtonCreate")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				jLabelMsg.setText("");
				pronosticAdded.setText("");
				try {
					double bet = Double.valueOf(textFieldBet.getText());
					if(bet>bezeroa.getDirua()) {
						jLabelMsg.setForeground(Color.RED);
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("NoMoney"));
					}else {
						double minBet = getMinBet();
						if(bet<minBet) {
							jLabelMsg.setForeground(Color.RED);
							jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("NotMinBet")+" "+minBet+"�");
						}else{
							BLFacade facade = MainGUI.getBusinessLogic();
							bezeroa=facade.apustuaEgin(pronostikoak,bet,bezeroa);
							jLabelMsg.setForeground(Color.GREEN);
							jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccesBet"));
							scrollPaneApustua.setVisible(false);
							for(int i = 0; i<pronostikoak.size(); i++) {
								tableModelApustua.removeRow(0);
							}
							pronostikoak.removeAll(pronostikoak);
							jButtonCreate.setEnabled(false);
							TotalMinBet.setText("");
							TotalFee.setText("");
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("InvalidSyntax"));
				}
			}
		});
		jButtonCreate.setBounds(new Rectangle(275, 275, 130, 30));
		jButtonCreate.setBounds(135, 522, 130, 30);
		jButtonCreate.setEnabled(false);
		getContentPane().add(jButtonCreate);
		jLabelSucces.setForeground(Color.GREEN);
		jLabelSucces.setBounds(new Rectangle(175, 240, 305, 20));
		jLabelSucces.setBounds(237, 482, 305, 20);
		
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
				pronosticAdded.setText("");
				selectedPronostic = ((domain.Pronostikoa) jComboBoxPronostics.getSelectedItem());
				if(selectedPronostic!=null) {
					addButton.setEnabled(true);
					jLabelMinBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinBet")+" "+selectedQuestion.getBetMinimum());
				}else {
					addButton.setEnabled(false);
					jLabelMinBet.setText("");
				}
			}
		});
		
		jComboBoxPronostics.setBounds(275, 213, 250, 22);
		jComboBoxPronostics.setModel(pronosticModel);
		getContentPane().add(jComboBoxPronostics);
		
		JLabel lblNewLabelResult = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblNewLabelResult")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabelResult.setBounds(290, 186, 140, 14);
		getContentPane().add(lblNewLabelResult);
		
		textFieldBet = new JTextField();
		textFieldBet.setBounds(new Rectangle(100, 243, 60, 20));
		textFieldBet.setBounds(77, 482, 60, 20);
		getContentPane().add(textFieldBet);
		
		JLabel jLabelBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("jLabelBet")); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelBet.setBounds(10, 482, 68, 14);
		getContentPane().add(jLabelBet);
		jLabelMinBet.setBounds(275, 246, 292, 14);
		
		getContentPane().add(jLabelMinBet);
		jLabelMsg.setBounds(21, 504, 250, 18);
		
		getContentPane().add(jLabelMsg);
		
		scrollPaneApustua = new JScrollPane();
		scrollPaneApustua.setBounds(new Rectangle(138, 273, 406, 116));
		scrollPaneApustua.setBounds(21, 340, 559, 116);
		getContentPane().add(scrollPaneApustua);
		scrollPaneApustua.setVisible(false);
		
		scrollPaneApustua.setViewportView(tableApustua);
		tableModelApustua = new DefaultTableModel(null, columnNamesApustua);
		
		tableApustua.setModel(tableModelApustua);
		tableApustua.getColumnModel().getColumn(0).setPreferredWidth(45);
		tableApustua.getColumnModel().getColumn(1).setPreferredWidth(75);
		tableApustua.getColumnModel().getColumn(2).setPreferredWidth(155);
		tableApustua.getColumnModel().getColumn(3).setPreferredWidth(10);
		tableApustua.getColumnModel().getColumn(3).setPreferredWidth(10);
		
		tableApustua.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pronosI=tableApustua.getSelectedRow();
				quitButton.setEnabled(true);
			}
		});

		
		quitButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Quit")); //$NON-NLS-1$ //$NON-NLS-2$
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pronosticAdded.setText("");
				tableModelApustua.removeRow(pronosI);
				double kuotaZaharra = pronostikoak.get((int)pronosI).getKuota();
				pronostikoak.remove((int)pronosI);
				if(pronostikoak.isEmpty()) {
					TotalMinBet.setText("");
					TotalFee.setText("");
					scrollPaneApustua.setVisible(false);
					jButtonCreate.setEnabled(false);
				}else {
					minBetTotala=getMinBet();
					kuotaTotala=kuotaTotala/kuotaZaharra;
					TotalMinBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinBet")+" "+minBetTotala);
					TotalFee.setText(ResourceBundle.getBundle("Etiquetas").getString("TotalFee")+" "+kuotaTotala);
				}
				quitButton.setEnabled(false);
			}
		});
		quitButton.setBounds(135, 267, 118, 30);
		quitButton.setEnabled(false);
		getContentPane().add(quitButton);
		
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelMsg.setText("");
				pronosticAdded.setText("");
				if(!hartutaDago(selectedQuestion)) {
					Date eventDate = jCalendar.getDate();
					eventDate = UtilDate.trim(eventDate);
					Date today = new Date();
					if(eventDate.after(today)) {
						Vector<Object> row = new Vector<Object>();
						row.add(UtilDate.getString(jCalendar.getDate()));
						row.add(selectedEvent.getDescription());
						row.add(selectedQuestion.getQuestion());
						row.add(selectedPronostic.getDeskripzioa());
						row.add(selectedPronostic.getKuota());
						tableModelApustua.addRow(row);
						if(pronostikoak.isEmpty()) {
							scrollPaneApustua.setVisible(true);
							jButtonCreate.setEnabled(true);
							minBetTotala=selectedPronostic.getQuestion().getBetMinimum();
							kuotaTotala=selectedPronostic.getKuota();
						}else {
							if(selectedPronostic.getQuestion().getBetMinimum()>minBetTotala) {
								minBetTotala=selectedPronostic.getQuestion().getBetMinimum();
							}
							kuotaTotala=kuotaTotala*selectedPronostic.getKuota();
						}
						TotalMinBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MinBet")+" "+minBetTotala);
						TotalFee.setText(ResourceBundle.getBundle("Etiquetas").getString("TotalFee")+" "+kuotaTotala);
						pronostikoak.add(selectedPronostic);
					}else {
						pronosticAdded.setText(ResourceBundle.getBundle("Etiquetas").getString("Finished"));
					}
				}else {
					pronosticAdded.setText(ResourceBundle.getBundle("Etiquetas").getString("PronosticAlreadyAdded"));
				}
			}
		});
		addButton.setBounds(332, 267, 118, 30);
		addButton.setEnabled(false);
		getContentPane().add(addButton);
		
		pronosticAdded = new JLabel("");
		pronosticAdded.setForeground(Color.RED);
		pronosticAdded.setBounds(125, 308, 423, 14);
		getContentPane().add(pronosticAdded);
		euro.setBounds(144, 485, 46, 14);
		
		getContentPane().add(euro);
		TotalMinBet.setBounds(178, 466, 186, 14);
		
		getContentPane().add(TotalMinBet);
		TotalFee.setBounds(374, 467, 206, 14);
		
		getContentPane().add(TotalFee);

		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				jLabelError.setText("");
				jLabelErrorDate.setText("");
				jLabelSucces.setText("");
				pronosticAdded.setText("");

//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					
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
//
//						if (events.isEmpty())
////							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
////									+ ": " + dateformat1.format(calendarAct.getTime()));
//						else
//							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
//									+ dateformat1.format(calendarAct.getTime()));
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
		aurrekoa.setBezeroa(bezeroa);
		aurrekoa.setVisible(true);
	}
	
	public boolean hartutaDago(Question question) {
		for(Pronostikoa p : pronostikoak) {
			if(p.getQuestion().getQuestionNumber()==question.getQuestionNumber()) {
				return true;
			}
		}
		return false;
	}
	
	public double getMinBet() {
		double max = pronostikoak.get(0).getQuestion().getBetMinimum();
		for (Pronostikoa p : pronostikoak) {
			if(p.getQuestion().getBetMinimum()>max) {
				max=p.getQuestion().getBetMinimum();
			}
		}
		return max;
	}
}

