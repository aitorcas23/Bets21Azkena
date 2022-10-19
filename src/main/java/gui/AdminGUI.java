package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.Admin;

import javax.swing.JLabel;
import java.awt.Color;

public class AdminGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8301123738250509410L;
	private MainGUI lehengoMain;
	private Admin admin;
	private JLabel done;
	
	public AdminGUI(Admin admin, MainGUI lehengoMain) {
		try {
			this.lehengoMain=lehengoMain;
			this.admin=admin;
			adminGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Kodea simplifikatu
	
	public void adminGUIExekuzioa() throws Exception{
		String etiketa = "Etiquetas";
		JPanel contentPane;
		
		this.setTitle(ResourceBundle.getBundle(etiketa).getString("Admin")); //$NON-NLS-1$ //$NON-NLS-2$
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle(etiketa).getString("Welcome")+" "+admin.getErabiltzaileIzena()+"!"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(124, 23, 250, 29);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle(etiketa).getString("SignOut"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saioaItxi();
			}
		});
		btnNewButton.setBounds(10, 259, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCreateQuestion = new JButton();
		btnCreateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiCreateQuestion();
				done.setText("");
			}
		});
		btnCreateQuestion.setText(ResourceBundle.getBundle(etiketa).getString("CreateQuestion")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCreateQuestion.setBounds(10, 50, 408, 66);
		contentPane.add(btnCreateQuestion);
		
		JButton eguneratuHilabetekoak = new JButton();
		eguneratuHilabetekoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade=MainGUI.getBusinessLogic();
				facade.eguneratuErrepikapenak();
				done.setText(ResourceBundle.getBundle(etiketa).getString("Done"));
			}
		});
		eguneratuHilabetekoak.setText(ResourceBundle.getBundle(etiketa).getString("ResetMonth")); //$NON-NLS-1$ //$NON-NLS-2$
		eguneratuHilabetekoak.setBounds(10, 180, 408, 66);
		contentPane.add(eguneratuHilabetekoak);
		
		JButton puntuazioakIkusi = new JButton();
		puntuazioakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiPuntuazioakIkusi();
			}
		});
		puntuazioakIkusi.setText(ResourceBundle.getBundle(etiketa).getString("ViewWorker")); //$NON-NLS-1$ //$NON-NLS-2$
		puntuazioakIkusi.setBounds(10, 115, 408, 66);
		contentPane.add(puntuazioakIkusi);
		
		done = new JLabel("");
		done.setForeground(Color.GREEN);
		done.setBounds(136, 259, 146, 14);
		contentPane.add(done);
	}
	
	/**
	 * Metodo honen bidez, saioa itxi eta hasierako menura joaten gara
	 */
	public void saioaItxi() {
		this.setVisible(false);
		this.lehengoMain.setVisible(true);
	}
	
	public void irekiCreateQuestion() {
		this.setVisible(false);
		JFrame a = new CreateQuestionGUI(new Vector<domain.Event>(), this);
		a.setVisible(true);
	}
	
	public void irekiPuntuazioakIkusi() {
		this.setVisible(false);
		JFrame a = new PuntuazioakIkusiGUI(this);
		a.setVisible(true);
	}
}
