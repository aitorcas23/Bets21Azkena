package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import configuration.UtilDate;
import domain.Bezeroa;
import domain.Pertsona;
import exceptions.UserAlreadyExist;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class RegisterGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -286120661524135679L;
	private JPanel contentPane;
	private JTextField name;
	private JTextField abizena_1;
	private JTextField abizena_2;
	private JPasswordField pas_1;
	private JPasswordField pas_2;
	private JTextField userName;
	private JTextField e_mail;
	private JTextField day;
	private JTextField year;
	private JTextField phoneNumber;
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> hilabeteak= new DefaultComboBoxModel<String>();
	private LoginGUI lehengoLogin;
	private MainGUI lehengoMain;
	private JLabel nameMezua;
	private JLabel pas_2Mezua;
	private JLabel abizena_Mezua;
	private JLabel pas_1Mezua;
	private JLabel abizena_2Mezua;
	private JLabel usernameMezua;
	private JLabel emailMezua;
	private JLabel telefonoaMezua;
	private JLabel lblNewLabel;
	private JLabel lblFirstSurname;
	private JLabel lblSecondSurname;
	private JLabel lblNewLabel_3;
	private JLabel lblYear;
	private JLabel lblPassword;
	private JLabel lblRepitePassword;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_1;
	private JLabel date_Mezua;
	private JLabel lblEmail;
	private JLabel lblNewLabel_2;
	private JLabel date_Mezua2;
	private JLabel lblDay;
	
	

	
	public RegisterGUI(MainGUI lehengoMain, LoginGUI lehengoLogin) {
		try {
			this.lehengoMain=lehengoMain;
			this.lehengoLogin=lehengoLogin;
			registerGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void registerGUIExekuzioa() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 100, 470, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		nameMezua = new JLabel("");
		nameMezua.setBounds(292, 37, 111, 14);
		nameMezua.setForeground(Color.RED);
		contentPane.add(nameMezua);
		
		pas_2Mezua = new JLabel("");
		pas_2Mezua.setBounds(292, 192, 116, 14);
		pas_2Mezua.setForeground(Color.RED);
		contentPane.add(pas_2Mezua);
		
		pas_1Mezua = new JLabel("");
		pas_1Mezua.setBounds(292, 161, 161, 14);
		pas_1Mezua.setForeground(Color.RED);
		contentPane.add(pas_1Mezua);
		
		abizena_Mezua = new JLabel("");
		abizena_Mezua.setBounds(292, 66, 116, 14);
		abizena_Mezua.setForeground(Color.RED);
		contentPane.add(abizena_Mezua);
		
		abizena_2Mezua = new JLabel("");
		abizena_2Mezua.setBounds(292, 99, 116, 14);
		abizena_2Mezua.setForeground(Color.RED);
		contentPane.add(abizena_2Mezua);
		
		usernameMezua = new JLabel("");
		usernameMezua.setBounds(292, 128, 161, 14);
		usernameMezua.setForeground(Color.RED);
		contentPane.add(usernameMezua);
		
		emailMezua = new JLabel("");
		emailMezua.setBounds(292, 223, 116, 14);
		emailMezua.setForeground(Color.RED);
		contentPane.add(emailMezua);
		
		telefonoaMezua = new JLabel("");
		telefonoaMezua.setBounds(292, 254, 116, 14);
		telefonoaMezua.setForeground(Color.RED);
		contentPane.add(telefonoaMezua);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(63, 37, 78, 14);
		contentPane.add(lblNewLabel);
		
		name = new JTextField();
		name.setBounds(176, 37, 111, 20);
		contentPane.add(name);
		name.setColumns(10);
		
		lblFirstSurname = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Abizena1")); //$NON-NLS-1$ //$NON-NLS-2$
		lblFirstSurname.setBounds(63, 66, 111, 14);
		contentPane.add(lblFirstSurname);
		
		abizena_1 = new JTextField();
		abizena_1.setBounds(176, 63, 111, 20);
		contentPane.add(abizena_1);
		abizena_1.setColumns(10);
		
		lblSecondSurname = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Abizena2")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSecondSurname.setBounds(63, 99, 111, 14);
		contentPane.add(lblSecondSurname);
		
		abizena_2 = new JTextField();
		abizena_2.setBounds(176, 96, 111, 20);
		contentPane.add(abizena_2);
		abizena_2.setColumns(10);
		
		lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPassword.setBounds(63, 161, 78, 14);
		contentPane.add(lblPassword);
		
		
		pas_1 = new JPasswordField();
		pas_1.setBounds(176, 158, 111, 20);
		contentPane.add(pas_1);
		
		lblRepitePassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password2")); //$NON-NLS-1$ //$NON-NLS-2$
		lblRepitePassword.setBounds(63, 192, 111, 14);
		contentPane.add(lblRepitePassword);
		
		pas_2 = new JPasswordField();
		pas_2.setBounds(176, 189, 111, 20);
		contentPane.add(pas_2);
		
		
		
		lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserName"));
		lblNewLabel_1.setBounds(63, 128, 78, 14);
		contentPane.add(lblNewLabel_1);
		
		userName = new JTextField();
		userName.setBounds(176, 127, 111, 20);
		contentPane.add(userName);
		userName.setColumns(10);
		
		lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(63, 223, 46, 14);
		contentPane.add(lblEmail);
		
		e_mail = new JTextField();
		e_mail.setBounds(176, 220, 111, 20);
		contentPane.add(e_mail);
		e_mail.setColumns(10);
		
		lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BirthDate")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_2.setBounds(198, 328, 155, 14);
		contentPane.add(lblNewLabel_2);
		
		lblDay = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Day")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDay.setBounds(63, 356, 46, 14);
		contentPane.add(lblDay);
		
		day = new JTextField();
		day.setBounds(102, 353, 27, 20);
		contentPane.add(day);
		day.setColumns(10);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(198, 353, 89, 20);
		comboBox.setModel(hilabeteak);
		contentPane.add(comboBox);
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Sectenber"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Novenber"));
		hilabeteak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Decenber"));
		
		
		
		lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Month")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_3.setBounds(146, 356, 64, 14);
		contentPane.add(lblNewLabel_3);
		
		lblYear = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Year")); //$NON-NLS-1$ //$NON-NLS-2$
		lblYear.setBounds(308, 356, 64, 14);
		contentPane.add(lblYear);
		
		year = new JTextField();
		year.setBounds(342, 353, 46, 20);
		contentPane.add(year);
		year.setColumns(10);
		
		lblNewLabel_4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PhoneNumber")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_4.setBounds(63, 254, 111, 14);
		contentPane.add(lblNewLabel_4);
		
	    date_Mezua = new JLabel("");
		date_Mezua.setBounds(63, 305, 325, 23);
		date_Mezua.setForeground(Color.RED);
		contentPane.add(date_Mezua);
		
		date_Mezua2 = new JLabel("");
		date_Mezua2.setBounds(63, 282, 340, 16);
		date_Mezua2.setForeground(Color.red);
		contentPane.add(date_Mezua2);
		
		phoneNumber = new JTextField();
		phoneNumber.setBounds(176, 251, 111, 20);
		contentPane.add(phoneNumber);
		phoneNumber.setColumns(10);
		
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("register"));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pas_1Mezua.setText("");
				nameMezua.setText("");
				pas_2Mezua.setText("");
				abizena_Mezua.setText("");
				abizena_2Mezua.setText("");
				usernameMezua.setText("");
				emailMezua.setText("");
				telefonoaMezua.setText("");
				date_Mezua.setText("");
				date_Mezua2.setText("");
				boolean pasa = check();		
				if(pasa) {
					String izena= name.getText();
					String abizena1=abizena_1.getText();
					String abizena2=abizena_2.getText();
					String erabiltzaileIzena=userName.getText();
					String pasahitza = new String(pas_1.getPassword());
					String emaila = e_mail.getText();
					String telefonoa = phoneNumber.getText();
					BLFacade facade = MainGUI.getBusinessLogic();
					Bezeroa bez = new Bezeroa(izena, abizena1, abizena2, erabiltzaileIzena, pasahitza, telefonoa, emaila, UtilDate.newDate(Integer.valueOf(year.getText()),hilabeteak.getIndexOf(hilabeteak.getSelectedItem()),Integer.valueOf(day.getText())));
					try {
						Pertsona pertsona = facade.register(bez, "bezeroa");
						loginBezeroa((Bezeroa)pertsona);
					}catch (UserAlreadyExist e) {
						usernameMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("AlredyUsed"));
					}
					
				}
			}
		});
		btnRegister.setBounds(172, 391, 115, 35);
		contentPane.add(btnRegister);		
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("jButtonClose"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bueltatuLogin();
			}
		});
		btnNewButton.setBounds(10, 515, 100, 35);
		contentPane.add(btnNewButton);
	}
	
	/**
	 * Metodo honen bidez sarrerako datuak zuzenak ote diren begiratzen da
	 * @return datuak zuzenak al diren
	 */
	public boolean check() {
		boolean pasa = true;
		Pattern pattern=Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather=pattern.matcher(e_mail.getText());
		if(mather.find()!=true) {
			emailMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
			pasa=false;
		}
		if(name.getText().isEmpty()) {
			nameMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		}
		if((new String(pas_1.getPassword())).length()<8) {
			pas_1Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("NotLong"));
			pasa=false;
		}
		if((new String(pas_1.getPassword())).isEmpty()) {
			pas_1Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		}
		if((new String(pas_2.getPassword())).isEmpty()) {
			pas_2Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		}
		if((new String(pas_1.getPassword())).compareTo((new String(pas_2.getPassword())))!=0) {
			pas_2Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("DiferentPas"));
			pasa=false;
		}
		if(abizena_1.getText().isEmpty()) {
			abizena_Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		}
		if(abizena_2.getText().isEmpty()) {
			abizena_2Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		}
		if(userName.getText().isEmpty()) {
			usernameMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		}else if(userName.getText().length()<4) {
			usernameMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("NotLong4"));
			pasa=false;
		}
		if(e_mail.getText().isEmpty()) {
			emailMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		}
		if(phoneNumber.getText().isEmpty()) {
			telefonoaMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Required"));
			pasa=false;
		} else if(phoneNumber.getText().length()!=9) {
			telefonoaMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
			pasa=false;
		} else {
			try {
				Integer.parseInt(phoneNumber.getText());
			}catch (Exception e) {
				telefonoaMezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
				pasa=false;
			}
		}
		try {
			Date gaur=new Date();
			Date jaiotzeData =UtilDate.newDate(Integer.valueOf(year.getText()),hilabeteak.getIndexOf(hilabeteak.getSelectedItem()),Integer.valueOf(day.getText()));
			int egunak=(int) ((gaur.getTime()-jaiotzeData.getTime())/(1000 * 60 * 60 * 24));
			if(egunak<6575) {
				date_Mezua2.setText(ResourceBundle.getBundle("Etiquetas").getString("NotAlowed1"));
				date_Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("NotAlowed2"));
				pasa=false;
			}				
		}catch(Exception e){
			pasa=false;
			date_Mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("DateNotAlowed"));
		}
		return pasa;
	}
	
	/**
	 * Metodo honen bidez, aurreko egoerara itzul gaitezke
	 */
	public void bueltatuLogin() {
		this.setVisible(false);
		this.lehengoLogin.setVisible(true);
	}
	
	/**
	 * Metodo honen bidez bezero baten interfazera sar gaitezke.
	 * @param bezeroa
	 */
	public void loginBezeroa(Bezeroa bezeroa) {
		this.setVisible(false);
		JFrame a = new BezeroaGUI(bezeroa,this.lehengoMain);
		a.setVisible(true);
	}
}