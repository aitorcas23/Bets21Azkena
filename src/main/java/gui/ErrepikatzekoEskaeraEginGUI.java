package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.Bezeroa;
import domain.BezeroartekoMezua;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Font;

public class ErrepikatzekoEskaeraEginGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Bezeroa bezeroa;
	private BezeroaGUI lehengoa;
	private JTextField bilatzailea;
	private JLabel username;
	private JLabel apustuKop;
	private JLabel irabaziKop;
	private JLabel portzentaia;
	private JLabel jokatua;
	private JLabel irabazia;
	private JLabel etekina;
	private JComboBox<Bezeroa> comboBox;
	private DefaultComboBoxModel<Bezeroa> bezeroakModel = new DefaultComboBoxModel<Bezeroa>();
	private JButton search;
	private JLabel erabiltzailerikEz;
	private Bezeroa selectedBezeroa;
	private JTextField mezua;
	private JTextField zenbatekoApustua;
	private JLabel hilabeteanMax;
	private JTextField hilabetekoMax;
	private JLabel norentzat;
	private JLabel zenbatApostatu;
	private JLabel success;
	private JButton send;
	private JButton requestButton;
	private JLabel invalid1;
	private JLabel invalid2;
	
	/**
	 * Launch the application.
	 */
	public ErrepikatzekoEskaeraEginGUI(Bezeroa bezeroa, BezeroaGUI lehengoa) {
		try {
			this.lehengoa=lehengoa;
			this.bezeroa=bezeroa;
			ErrepikatzekoEskaeraEginGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public void ErrepikatzekoEskaeraEginGUIExekuzioa() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RequestToRepeat"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade=MainGUI.getBusinessLogic();
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atzera();
			}
		});
		btnClose.setBounds(509, 227, 89, 23);
		contentPane.add(btnClose);
		
		bilatzailea = new JTextField();
		bilatzailea.setBounds(117, 33, 260, 20);
		contentPane.add(bilatzailea);
		bilatzailea.setColumns(10);
		
		search = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Search")); //$NON-NLS-1$ //$NON-NLS-2$
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				success.setText("");
				mezua.setVisible(false);
				send.setVisible(false);
				send.setEnabled(false);
				norentzat.setText("");
				zenbatApostatu.setText("");
				hilabeteanMax.setText("");
				zenbatekoApustua.setVisible(false);
				hilabetekoMax.setVisible(false);
				invalid1.setText("");
				invalid2.setText("");
				erabiltzailerikEz.setText("");
				comboBox.setVisible(false);
				bezeroakModel.removeAllElements();
				String text = bilatzailea.getText();
				List<Bezeroa> bezeroak = facade.getBezeroak(text, bezeroa);
				if(bezeroak.isEmpty()) {
					erabiltzailerikEz.setText(ResourceBundle.getBundle("Etiquetas").getString("NoUserfound"));
					comboBox.setVisible(false);
				}else {
					comboBox.setVisible(true);
					for(Bezeroa b : bezeroak) {
						if(!b.getErabiltzaileIzena().equals(bezeroa.getErabiltzaileIzena())) {
							bezeroakModel.addElement(b);
						}
					}
				}
			}
		});
		search.setBounds(376, 33, 116, 20);
		contentPane.add(search);
		
		requestButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RequestToRepeat")); //$NON-NLS-1$ //$NON-NLS-2$
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				success.setText("");
				requestButton.setEnabled(false);
				requestButton.setVisible(false);
				username.setText("");
				apustuKop.setText("");
				irabaziKop.setText("");
				portzentaia.setText("");
				jokatua.setText("");
				irabazia.setText("");
				etekina.setText("");
				norentzat.setText("");
				mezua.setText("");
				mezua.setVisible(true);
				send.setVisible(true);
				send.setEnabled(true);
				norentzat.setText(ResourceBundle.getBundle("Etiquetas").getString("For")+" "+selectedBezeroa.getErabiltzaileIzena());
				zenbatApostatu.setText(ResourceBundle.getBundle("Etiquetas").getString("HowManyBet"));
				hilabeteanMax.setText(ResourceBundle.getBundle("Etiquetas").getString("MaxMonth"));
				zenbatekoApustua.setVisible(true);
				hilabetekoMax.setVisible(true);
			}
		});
		requestButton.setBounds(167, 185, 270, 23);
		contentPane.add(requestButton);
		requestButton.setVisible(false);
		requestButton.setEnabled(false);
		
		comboBox = new JComboBox<Bezeroa>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedBezeroa=((domain.Bezeroa) comboBox.getSelectedItem());
				
				if(selectedBezeroa!=null) {
					username.setText(ResourceBundle.getBundle("Etiquetas").getString("UserName")+" "+selectedBezeroa.getErabiltzaileIzena());
					apustuKop.setText(ResourceBundle.getBundle("Etiquetas").getString("BetsMade")+" "+selectedBezeroa.getJokatuak());
					irabaziKop.setText(ResourceBundle.getBundle("Etiquetas").getString("BetsWon")+" "+selectedBezeroa.getIrabaziak());
					if(selectedBezeroa.getJokatuak()!=0) {
						portzentaia.setText(ResourceBundle.getBundle("Etiquetas").getString("PercentageSuccess")+" % "+((double) selectedBezeroa.getIrabaziak() / (double) selectedBezeroa.getJokatuak() * 100.0));

					}else {
						portzentaia.setText(ResourceBundle.getBundle("Etiquetas").getString("PercentageSuccess")+" % "+0);

					}
					Vector<Double> x = selectedBezeroa.getEtekinInformazioa();
					jokatua.setText(ResourceBundle.getBundle("Etiquetas").getString("Played")+" "+x.get(0));
					irabazia.setText(ResourceBundle.getBundle("Etiquetas").getString("Win")+" "+x.get(1));
					etekina.setText(ResourceBundle.getBundle("Etiquetas").getString("Yield")+" "+x.get(2));
					requestButton.setEnabled(true);
					requestButton.setVisible(true);
				}
			}
		});
		comboBox.setModel(bezeroakModel);
		comboBox.setBounds(117, 53, 260, 22);
		contentPane.add(comboBox);
		comboBox.setVisible(false);
		
		username = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		username.setFont(username.getFont().deriveFont(username.getFont().getStyle() | Font.BOLD, username.getFont().getSize() + 2f));
		username.setBounds(50, 88, 180, 23);
		contentPane.add(username);
		
		apustuKop = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		apustuKop.setBounds(50, 122, 180, 14);
		contentPane.add(apustuKop);
		
		irabaziKop = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		irabaziKop.setBounds(227, 122, 197, 14);
		contentPane.add(irabaziKop);
		
		portzentaia = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		portzentaia.setBounds(412, 122, 186, 14);
		contentPane.add(portzentaia);
		
		jokatua = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		jokatua.setBounds(50, 160, 107, 14);
		contentPane.add(jokatua);
		
		irabazia = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		irabazia.setBounds(167, 160, 116, 14);
		contentPane.add(irabazia);
		
		etekina = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		etekina.setBounds(303, 160, 121, 14);
		contentPane.add(etekina);
		
		erabiltzailerikEz = new JLabel("");
		erabiltzailerikEz.setForeground(Color.RED);
		erabiltzailerikEz.setBounds(117, 58, 260, 14);
		contentPane.add(erabiltzailerikEz);
		
		mezua = new JTextField();
		mezua.setText("");
		mezua.setBounds(104, 118, 409, 29);
		contentPane.add(mezua);
		mezua.setColumns(10);
		mezua.setVisible(false);
		
		norentzat = new JLabel("");
		norentzat.setBounds(100, 83, 157, 14);
		contentPane.add(norentzat);
		
		zenbatApostatu = new JLabel("");
		zenbatApostatu.setBounds(10, 185, 315, 14);
		contentPane.add(zenbatApostatu);
		
		zenbatekoApustua = new JTextField();
		zenbatekoApustua.setText("");
		zenbatekoApustua.setBounds(93, 202, 86, 20);
		contentPane.add(zenbatekoApustua);
		zenbatekoApustua.setColumns(10);
		zenbatekoApustua.setVisible(false);
		
		
		hilabeteanMax = new JLabel("");
		hilabeteanMax.setBounds(357, 185, 241, 14);
		contentPane.add(hilabeteanMax);
		
		hilabetekoMax = new JTextField();
		hilabetekoMax.setText("");
		hilabetekoMax.setBounds(427, 202, 86, 20);
		contentPane.add(hilabetekoMax);
		hilabetekoMax.setColumns(10);
		hilabetekoMax.setVisible(false);;
		
		send = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send")); //$NON-NLS-1$ //$NON-NLS-2$
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				invalid1.setText("");
				invalid2.setText("");
				success.setText("");
				double apostatukoDena=-1, apustuaHilabetean=-1; 
				try {
					apostatukoDena = Double.valueOf(zenbatekoApustua.getText());
				}catch (Exception e) {
					invalid1.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
				}
				try {
					apustuaHilabetean = Double.valueOf(hilabetekoMax.getText());
				}catch (Exception e) {
					invalid2.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
				}
				if(apostatukoDena<=0) {
					invalid1.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
				}
				if(apustuaHilabetean<1) {
					invalid2.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid1"));
				}
				if(apostatukoDena>0 && apustuaHilabetean>=1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					if(selectedBezeroa.getKomisioAutomatikoa()==-1) {
						String mezuarenTestua = mezua.getText();
						BezeroartekoMezua bamez1 = new BezeroartekoMezua( mezuarenTestua, ResourceBundle.getBundle("Etiquetas").getString("RepeatRequest"), "eskaera", apostatukoDena, apustuaHilabetean, -1, bezeroa, selectedBezeroa);
						bezeroa=facade.bidaliMezua(bezeroa, selectedBezeroa, bamez1);
					}else {
						String mezuOsoa = ResourceBundle.getBundle("Etiquetas").getString("IAccept")+selectedBezeroa.getKomisioAutomatikoa()*100+" "+ResourceBundle.getBundle("Etiquetas").getString("IAccept2");
						BezeroartekoMezua bamez2 = new BezeroartekoMezua( mezuOsoa, ResourceBundle.getBundle("Etiquetas").getString("Acceptance"), "errepikatuak eskaera onartu", apostatukoDena, apustuaHilabetean, selectedBezeroa.getKomisioAutomatikoa(), selectedBezeroa, bezeroa);
						facade.bidaliMezua(selectedBezeroa, bezeroa, bamez2);
					}
					success.setText(ResourceBundle.getBundle("Etiquetas").getString("RequestSent"));
					bezeroakModel.removeElement(selectedBezeroa);
					if(bezeroakModel.getSize()==0) {
						erabiltzailerikEz.setText(ResourceBundle.getBundle("Etiquetas").getString("NoUserfound"));
						comboBox.setVisible(false);
					}
					mezua.setText("");
					mezua.setVisible(false);
					send.setEnabled(false);
					send.setVisible(false);
					norentzat.setText("");
					zenbatApostatu.setText("");
					hilabeteanMax.setText("");
					zenbatekoApustua.setVisible(false);
					hilabetekoMax.setVisible(false);
					zenbatekoApustua.setText("");
					hilabetekoMax.setText("");
				}
			}
		});
		send.setBounds(33, 227, 89, 23);
		contentPane.add(send);
		send.setEnabled(false);
		send.setVisible(false);
		
		success = new JLabel("");
		success.setForeground(Color.GREEN);
		success.setBounds(132, 231, 193, 14);
		contentPane.add(success);
		
		invalid1 = new JLabel("\r\n");
		invalid1.setForeground(Color.RED);
		invalid1.setBounds(189, 205, 73, 14);
		contentPane.add(invalid1);
		
		invalid2 = new JLabel("\r\n");
		invalid2.setForeground(Color.RED);
		invalid2.setBounds(519, 205, 89, 14);
		contentPane.add(invalid2);
	}
	
	public void atzera() {
		this.setVisible(false);
		this.lehengoa.setBezeroa(bezeroa);
		this.lehengoa.setVisible(true);
	}
}
