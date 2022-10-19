package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.Bezeroa;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;

public class EzarpenakAldatuGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Bezeroa bezeroa;
	private BezeroaGUI lehengoa;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField balioa;
	private JLabel kontuMota;
	private JLabel komisioAutomatikoa;
	private JLabel komisioa;
	private JRadioButton pribatua;
	private JRadioButton publikoa;
	private JRadioButton bai;
	private JRadioButton ez;
	private JLabel batetik;
	private JButton gorde;
	private JButton editatu;
	private JLabel invalid;
	private JLabel invalid_1;
	private JLabel invalid_2;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private String mota;
	

	/**
	 * Launch the application.
	 */
	public EzarpenakAldatuGUI(Bezeroa bezeroa, BezeroaGUI lehengoa) {
		try {
			this.lehengoa=lehengoa;
			this.bezeroa=bezeroa;
			EzarpenakAldatuGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public void EzarpenakAldatuGUIExekuzioa() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Settings"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atzera();
			}
		});
		btnClose.setBounds(465, 113, 89, 23);
		contentPane.add(btnClose);
		
		if(bezeroa.isPublikoa()) {
			mota=ResourceBundle.getBundle("Etiquetas").getString("Public");
		}else {
			mota=ResourceBundle.getBundle("Etiquetas").getString("Private");
		}
		kontuMota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TypeAccount")+" "+mota); //$NON-NLS-1$ //$NON-NLS-2$
		kontuMota.setBounds(31, 33, 359, 14);
		contentPane.add(kontuMota);
		
		String automatikoa;
		double komisioAuto = bezeroa.getKomisioAutomatikoa();
		if(komisioAuto==-1) {
			automatikoa=ResourceBundle.getBundle("Etiquetas").getString("No");
		}else {
			automatikoa=ResourceBundle.getBundle("Etiquetas").getString("Yes");
		}
		komisioAutomatikoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AutomaticResponse")+" "+automatikoa); //$NON-NLS-1$ //$NON-NLS-2$
		komisioAutomatikoa.setBounds(31, 60, 359, 14);
		contentPane.add(komisioAutomatikoa);
		
		komisioa = new JLabel("");
		komisioa.setBounds(69, 88, 282, 14);
		contentPane.add(komisioa);
		if(komisioAuto!=-1) {
			komisioa.setText(ResourceBundle.getBundle("Etiquetas").getString("Commission")+" "+komisioAuto);
		}
		
		pribatua = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Private")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(pribatua);
		pribatua.setBounds(229, 30, 110, 23);
		contentPane.add(pribatua);
		pribatua.setVisible(false);
		
		publikoa = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Public")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(publikoa);
		publikoa.setBounds(341, 30, 100, 23);
		contentPane.add(publikoa);
		publikoa.setVisible(false);
		
		bai = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Yes")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup_1.add(bai);
		bai.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	balioa.setText("");
		    	komisioa.setText(ResourceBundle.getBundle("Etiquetas").getString("Commission"));
		    	balioa.setVisible(true);
				batetik.setVisible(true);
		    }
		});
		bai.setBounds(229, 57, 110, 23);
		contentPane.add(bai);
		bai.setVisible(false);
		
		ez = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("No")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup_1.add(ez);
		ez.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	komisioa.setText("");
		    	balioa.setVisible(false);
				batetik.setVisible(false);
		    }
		});
		ez.setBounds(341, 57, 101, 23);
		contentPane.add(ez);
		ez.setVisible(false);
		
		balioa = new JTextField();
		balioa.setText("");
		balioa.setBounds(194, 86, 47, 20);
		contentPane.add(balioa);
		balioa.setColumns(10);
		balioa.setVisible(false);
		
		batetik = new JLabel("/1");
		batetik.setFont(batetik.getFont().deriveFont(batetik.getFont().getStyle() | Font.BOLD, batetik.getFont().getSize() + 1f));
		batetik.setBounds(243, 88, 61, 14);
		contentPane.add(batetik);
		batetik.setVisible(false);
		
		gorde = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Save")); //$NON-NLS-1$ //$NON-NLS-2$
		gorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean ondo = true;
				double komisioBerria = -1;
				if(!pribatua.isSelected() && !publikoa.isSelected()) {
					invalid.setVisible(true);
					ondo=false;
				}
				if(!bai.isSelected() && !ez.isSelected()) {
					invalid_1.setVisible(true);
					ondo=false;
				}
				if(bai.isSelected()) {
					try {
						komisioBerria = Double.valueOf(balioa.getText());
						if(komisioBerria<0 || komisioBerria>=1) {
							invalid_2.setVisible(true);
							ondo=false;
						}
					}catch(Exception e) {
						invalid_2.setVisible(true);
						ondo=false;
					}
				}
				if(ondo) {
					if(publikoa.isSelected()) {
						mota=ResourceBundle.getBundle("Etiquetas").getString("Public");
					}else {
						mota=ResourceBundle.getBundle("Etiquetas").getString("Private");
					}
					BLFacade facade=MainGUI.getBusinessLogic();
					bezeroa = facade.eguneratuEzarpenak(bezeroa, komisioBerria, publikoa.isSelected());
					editatu.setVisible(true);
					editatu.setEnabled(true);
					kontuMota.setText(ResourceBundle.getBundle("Etiquetas").getString("TypeAccount")+" "+mota);
					String automatikoaDa;
					double komisioAutom = bezeroa.getKomisioAutomatikoa();
					if(komisioAutom==-1) {
						automatikoaDa=ResourceBundle.getBundle("Etiquetas").getString("No");
					}else {
						automatikoaDa=ResourceBundle.getBundle("Etiquetas").getString("Yes");
						komisioa.setText(ResourceBundle.getBundle("Etiquetas").getString("Commission")+" "+komisioAutom);
					}
					komisioAutomatikoa.setText(ResourceBundle.getBundle("Etiquetas").getString("AutomaticResponse")+" "+automatikoaDa);
					bai.setVisible(false);
					ez.setVisible(false);
					publikoa.setVisible(false);
					pribatua.setVisible(false);
					gorde.setVisible(false);
					gorde.setEnabled(false);
					balioa.setVisible(false);
					batetik.setVisible(false);
				}
			}
		});
		gorde.setBounds(31, 113, 89, 23);
		contentPane.add(gorde);
		gorde.setVisible(false);
		gorde.setEnabled(false);
		
		editatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Edit")); //$NON-NLS-1$ //$NON-NLS-2$
		editatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editatu.setVisible(false);
				editatu.setEnabled(false);
				kontuMota.setText(ResourceBundle.getBundle("Etiquetas").getString("TypeAccount"));
				komisioAutomatikoa.setText(ResourceBundle.getBundle("Etiquetas").getString("AutomaticResponse"));
				komisioa.setText("");
				bai.setVisible(true);
				ez.setVisible(true);
				publikoa.setVisible(true);
				pribatua.setVisible(true);
				gorde.setVisible(true);
				gorde.setEnabled(true);
			}
		});
		editatu.setBounds(253, 113, 89, 23);
		contentPane.add(editatu);
		
		invalid = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Invalid")); //$NON-NLS-1$ //$NON-NLS-2$
		invalid.setForeground(Color.RED);
		invalid.setBounds(455, 35, 72, 14);
		contentPane.add(invalid);
		invalid.setVisible(false);
		
		invalid_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Invalid")); //$NON-NLS-1$ //$NON-NLS-2$
		invalid_1.setForeground(Color.RED);
		invalid_1.setBounds(454, 60, 110, 14);
		contentPane.add(invalid_1);
		invalid_1.setVisible(false);
		
		invalid_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Invalid")); //$NON-NLS-1$ //$NON-NLS-2$
		invalid_2.setForeground(Color.RED);
		invalid_2.setBounds(289, 88, 120, 14);
		contentPane.add(invalid_2);
		invalid_2.setVisible(false);
	}
	
	public void atzera() {
		this.setVisible(false);
		this.lehengoa.setBezeroa(bezeroa);
		this.lehengoa.setVisible(true);
	}
}
