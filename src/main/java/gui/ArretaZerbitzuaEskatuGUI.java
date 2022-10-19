package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.ArretaElkarrizketa;
import domain.Bezeroa;
import domain.BezeroaContainer;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;

public class ArretaZerbitzuaEskatuGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Bezeroa bezeroa;
	private BezeroaContainer bezeroC;
	private BezeroaGUI lehengoa;
	private JButton send;
	private JTextField gaia;
	private JLabel mezuak;
	private boolean ziur = false;
	private JTextArea mezuaArea;
	private Vector<ArretaElkarrizketa> elkarrizketak = new Vector<ArretaElkarrizketa>();
	
	
	/**
	 * Launch the application.
	 */
	public ArretaZerbitzuaEskatuGUI(Bezeroa bezeroa, BezeroaGUI lehengoa) {
		try {
			this.lehengoa=lehengoa;
			this.bezeroa=bezeroa;
			ArretaZerbitzuaEskatuGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public void ArretaZerbitzuaEskatuGUIExekuzioa() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CustomerService"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		BLFacade facade=MainGUI.getBusinessLogic();
		bezeroC = facade.getBezeroaContainer(bezeroa);
		elkarrizketak = bezeroC.getElkarrizketak();
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atzera();
			}
		});
		btnClose.setBounds(509, 227, 89, 23);
		contentPane.add(btnClose);;
		
		send = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send")); //$NON-NLS-1$ //$NON-NLS-2$
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mezuak.setText("");
				String mezuarenGaia = gaia.getText();
				String mezuarenTestua = mezuaArea.getText();
				if(baduElkarrizketa()) {
					mezuak.setForeground(Color.RED);
					mezuak.setText(ResourceBundle.getBundle("Etiquetas").getString("AlreadyTalking"));
					ziur=false;
				}else if(mezuarenTestua.length()==0) {
					mezuak.setForeground(Color.RED);
					mezuak.setText(ResourceBundle.getBundle("Etiquetas").getString("MessageEmpty"));
					ziur=false;
				}else if(mezuarenGaia.length()==0 && !ziur) {
					mezuak.setForeground(Color.BLACK);
					mezuak.setText(ResourceBundle.getBundle("Etiquetas").getString("NoSubject"));
					ziur=true;
				}else {
					BLFacade facade=MainGUI.getBusinessLogic();
					ArretaElkarrizketa elkarrizketa = facade.arretaElkarrizketaSortu(bezeroa, mezuarenGaia, mezuarenTestua);
					elkarrizketak.add(elkarrizketa);
					mezuak.setForeground(Color.GREEN);
					mezuak.setText(ResourceBundle.getBundle("Etiquetas").getString("ReveiveAnswer"));
				}
			}
		});
		send.setBounds(33, 227, 89, 23);
		contentPane.add(send);
		
		JLabel gaiaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Subject")); //$NON-NLS-1$ //$NON-NLS-2$
		gaiaLabel.setFont(gaiaLabel.getFont().deriveFont(gaiaLabel.getFont().getStyle() | Font.BOLD, gaiaLabel.getFont().getSize() + 1f));
		gaiaLabel.setBounds(28, 11, 78, 23);
		contentPane.add(gaiaLabel);
		
		gaia = new JTextField();
		gaia.setText("");
		gaia.setBounds(95, 13, 420, 20);
		contentPane.add(gaia);
		gaia.setColumns(10);
		
		JLabel mezuaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Message")); //$NON-NLS-1$ //$NON-NLS-2$
		mezuaLabel.setFont(mezuaLabel.getFont().deriveFont(mezuaLabel.getFont().getStyle() | Font.BOLD, mezuaLabel.getFont().getSize() + 1f));
		mezuaLabel.setBounds(28, 44, 94, 23);
		contentPane.add(mezuaLabel);
		
		mezuak = new JLabel("");
		mezuak.setBounds(132, 231, 367, 14);
		contentPane.add(mezuak);
		
		mezuaArea = new JTextArea();
		mezuaArea.setText("");
		mezuaArea.setBounds(95, 45, 420, 173);
		contentPane.add(mezuaArea);
		
	}
	
	private boolean baduElkarrizketa() {
		for(ArretaElkarrizketa ae : elkarrizketak) {
			if(!ae.isAmaituta()) {
				return true;
			}
		}
		return false;
	}
	
	private void atzera() {
		this.setVisible(false);
		this.lehengoa.setBezeroa(bezeroa);
		this.lehengoa.setVisible(true);
	}
}
