package gui;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Langilea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class LangileaGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5173883962843347204L;
	private JPanel contentPane;
	private MainGUI lehengoMain;
	private Langilea langilea;

	public LangileaGUI(Langilea langilea, MainGUI lehengoMain) {
		try {
			this.lehengoMain=lehengoMain;
			this.langilea=langilea;
			langileaGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void langileaGUIExekuzioa() throws Exception{
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Worker"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Welcome")+" "+langilea.getErabiltzaileIzena()+"!"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(124, 23, 250, 29);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignOut"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saioaItxi();
			}
		});
		btnNewButton.setBounds(16, 390, 179, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCreateEvent = new JButton();
		btnCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiCreateEvent();
			}
		});
		btnCreateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		btnCreateEvent.setBounds(16, 49, 408, 66);
		contentPane.add(btnCreateEvent);
		
		JButton btnCreatePronostic = new JButton();
		btnCreatePronostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiCreatePronostic();
			}
		});
		btnCreatePronostic.setText(ResourceBundle.getBundle("Etiquetas").getString("CreatePronostic"));
		btnCreatePronostic.setBounds(16, 115, 408, 66);
		contentPane.add(btnCreatePronostic);
		
		JButton btnPutResult = new JButton();
		btnPutResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiEmaitzaIpini();
			}
		});
		btnPutResult.setText(ResourceBundle.getBundle("Etiquetas").getString("PutResult"));
		btnPutResult.setBounds(16, 182, 408, 66);
		contentPane.add(btnPutResult);
		
		JButton btnDeleteEvent = new JButton();
		btnDeleteEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiGertaeraEzabatu();
			}
		});
		btnDeleteEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
		btnDeleteEvent.setBounds(16, 249, 408, 66);
		contentPane.add(btnDeleteEvent);
		
		JButton btnDeleteEvent_1 = new JButton();
		btnDeleteEvent_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiArretaZerbitzuaEman();
			}
		});
		btnDeleteEvent_1.setText(ResourceBundle.getBundle("Etiquetas").getString("ProvideCustomer")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeleteEvent_1.setBounds(16, 315, 408, 66);
		contentPane.add(btnDeleteEvent_1);
	}
	
	/**
	 * Metodo honen bidez gertaera sortu erabilera kasuaren interfazea irekitzen da
	 */
	public void irekiCreateEvent() {
		this.setVisible(false);
		JFrame a = new CreateEventGUI(this);
		a.setVisible(true);
	}
	
	public void irekiCreatePronostic() {
		this.setVisible(false);
		JFrame a = new CreatePronosticGUI(this);
		a.setVisible(true);
	}
	
	public void irekiEmaitzaIpini() {
		this.setVisible(false);
		JFrame a = new EmaitzaIpiniGUI(this);
		a.setVisible(true);
	}
	
	public void irekiGertaeraEzabatu() {
		this.setVisible(false);
		JFrame a = new GertaeraEzabatuGUI(this);
		a.setVisible(true);
	}
	
	public void irekiArretaZerbitzuaEman() {
		this.setVisible(false);
		JFrame a = new ArretaZerbitzuaEmanGUI(this, langilea);
		a.setVisible(true);
	}
	
	public void setLangilea(Langilea l) {
		this.langilea=l;
	}
	
	/**
	 * Metodo honen bidez, saioa itxi eta hasierako menura joaten gara
	 */
	public void saioaItxi() {
		this.setVisible(false);
		this.lehengoMain.setVisible(true);
	}
}
