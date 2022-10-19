package gui;


import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import businesslogic.BLFacade;

import java.awt.*;
import java.awt.event.*;

import domain.ArretaElkarrizketa;
import domain.ArretaMezua;
import domain.Bezeroa;
import domain.BezeroaContainer;
import domain.BezeroartekoMezua;
import domain.Mezua;

public class PostontziaGUI extends JFrame {
	BezeroaGUI aurrekoa;
	private Bezeroa bezeroa; 
	private List<Mezua> mezuak;
	private int selectedRowInd;
	
	private static final long serialVersionUID = 1L;
	
	private Mezua selectedMezua;
	private BezeroartekoMezua selectedBezeroartekoMezua;
	private ArretaMezua selectedArretaMezua;
	private JScrollPane scrollPaneMezuak;
	private JTable tableMezua = new JTable();
	private DefaultTableModel tableModelMezuak;
	private JRadioButton bai;
	private JRadioButton ez;
	private JLabel labelEmaitza;
	private JLabel batetik;
	private JLabel euroko;
	private JLabel labelKuota;
	private JLabel labelIrabazia;
	private JLabel lblNewLabel;
	private JLabel nork;
	private JLabel mezua;
	private JLabel baldintzak;
	private JLabel hilabetean;
	private JLabel onartuGaldera;
	private JTextField emaitza;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton send;
	private JLabel invalid;
	private JLabel komisioa;
	private JButton remove;
	private String[] columnNamesMezuak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Date"), 
			ResourceBundle.getBundle("Etiquetas").getString("Who"), 
			ResourceBundle.getBundle("Etiquetas").getString("Subject"),
			ResourceBundle.getBundle("Etiquetas").getString("State"),
	};
	private JLabel bidalia;
	private JLabel postontziaHutsik;
	private JButton Eguneratu;
	private JTextArea textArea;
	private JButton erantzun;
	private JButton itzuliMezura;
	private JLabel jakinarazpenak;
	private JRadioButton bat;
	private JRadioButton bi;
	private JRadioButton hiru;
	private JRadioButton lau;
	private JRadioButton bost;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JLabel rate;
	private Vector<ArretaElkarrizketa> elkarrizketak = new Vector<ArretaElkarrizketa>();
	
	public PostontziaGUI(BezeroaGUI aurrekoa, Bezeroa bezeroa) {
		try {
			this.aurrekoa=aurrekoa;
			BLFacade facade = MainGUI.getBusinessLogic();
			BezeroaContainer x = facade.getBezeroaContainer(bezeroa);
			this.bezeroa=x.getBezeroa();
			elkarrizketak=x.getElkarrizketak();
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		
		tableModelMezuak = new DefaultTableModel(null, columnNamesMezuak);
		
		scrollPaneMezuak = new JScrollPane();
		scrollPaneMezuak.setBounds(new Rectangle(138, 273, 406, 116));
		scrollPaneMezuak.setBounds(19, 45, 585, 116);
		getContentPane().add(scrollPaneMezuak);
		scrollPaneMezuak.setVisible(false);
		scrollPaneMezuak.setViewportView(tableMezua);
		
		postontziaHutsik = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MailBoxEmpty")); //$NON-NLS-1$ //$NON-NLS-2$
		postontziaHutsik.setFont(postontziaHutsik.getFont().deriveFont(postontziaHutsik.getFont().getStyle() | Font.BOLD, postontziaHutsik.getFont().getSize() + 4f));
		postontziaHutsik.setBounds(193, 185, 377, 30);
		postontziaHutsik.setVisible(false);
		getContentPane().add(postontziaHutsik);
		
		eguneratuMezuak();
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(630, 455));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Mailbox"));
		
		scrollPaneMezuak = new JScrollPane();
		scrollPaneMezuak.setBounds(new Rectangle(138, 273, 406, 116));
		scrollPaneMezuak.setBounds(19, 45, 585, 116);
		getContentPane().add(scrollPaneMezuak);
		scrollPaneMezuak.setVisible(false);
		
		euroko = new JLabel("");
		euroko.setBounds(68, 241, 438, 14);
		getContentPane().add(euroko);
		
		labelKuota = new JLabel("");
		labelKuota.setBounds(215, 241, 137, 14);
		getContentPane().add(labelKuota);
		
		labelIrabazia = new JLabel("");
		labelIrabazia.setBounds(388, 241, 182, 14);
		getContentPane().add(labelIrabazia);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Mailbox")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(lblNewLabel.getFont().getStyle() | Font.BOLD, lblNewLabel.getFont().getSize() + 2f));
		lblNewLabel.setBounds(19, 11, 88, 30);
		getContentPane().add(lblNewLabel);
		
		JButton back = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				back();
			}
		});
		back.setBounds(19, 385, 89, 23);
		getContentPane().add(back);
		
		nork = new JLabel("");
		nork.setBounds(50, 172, 287, 14);
		getContentPane().add(nork);
		
		mezua = new JLabel("");
		mezua.setBounds(50, 197, 520, 14);
		getContentPane().add(mezua);
		
		baldintzak = new JLabel("");
		baldintzak.setBounds(50, 222, 237, 14);
		getContentPane().add(baldintzak);
		
		tableMezua.setModel(tableModelMezuak);
		
		hilabetean = new JLabel("");
		hilabetean.setBounds(68, 266, 438, 14);
		getContentPane().add(hilabetean);
		
		onartuGaldera = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DoYouAccept")); //$NON-NLS-1$ //$NON-NLS-2$
		onartuGaldera.setBounds(50, 291, 210, 14);
		getContentPane().add(onartuGaldera);
		onartuGaldera.setVisible(false);
		
		bai = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("Yes")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(bai);
		bai.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	invalid.setText("");
		    	emaitza.setText("");
		    	String mota = selectedBezeroartekoMezua.getMota();
		    	if(mota.equals("eskaera")) {
		    		emaitza.setBounds(302, 313, 77, 20);
			    	emaitza.setVisible(true);
			    	batetik.setText("/1");
			    	labelEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("YourBenefit"));
			    	send.setBounds(235, 353, 89, 23);
			    	send.setVisible(true);
			    	send.setEnabled(true);
		    	}else if (mota.equals("errepikatuak eskaera onartu")) {
		    		send.setBounds(235, 353, 89, 23);
		    		send.setVisible(true);
			    	send.setEnabled(true);
		    	}
		     }
		});
		bai.setBounds(36, 312, 69, 23);
		getContentPane().add(bai);
		bai.setVisible(false);
		
		ez = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("No")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(ez);
		ez.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	invalid.setText("");
		    	emaitza.setText("");
		    	String mota = selectedBezeroartekoMezua.getMota();
		    	if(mota.equals("eskaera")) {
		    		emaitza.setBounds(302, 313, 300, 20);
			    	emaitza.setVisible(true);
			    	batetik.setText("");
			    	labelEmaitza.setText(ResourceBundle.getBundle("Etiquetas").getString("Reason"));
			    	send.setBounds(235, 353, 89, 23);
			    	send.setVisible(true);
			    	send.setEnabled(true);
		    	}else if (mota.equals("errepikatuak eskaera onartu")) {
		    		send.setVisible(true);
			    	send.setEnabled(true);
		    	}
		     }
		});
		ez.setBounds(107, 312, 69, 23);
		getContentPane().add(ez);
		ez.setVisible(false);
		
		labelEmaitza = new JLabel("");
		labelEmaitza.setBounds(302, 291, 214, 14);
		getContentPane().add(labelEmaitza);
		
		emaitza = new JTextField();
		emaitza.setText("");
		emaitza.setBounds(302, 313, 77, 20);
		getContentPane().add(emaitza);
		emaitza.setColumns(10);
		emaitza.setVisible(false);
		
		batetik = new JLabel("");
		batetik.setFont(batetik.getFont().deriveFont(batetik.getFont().getStyle() | Font.BOLD, batetik.getFont().getSize() + 1f));
		batetik.setBounds(385, 316, 61, 14);
		getContentPane().add(batetik);
		
		send = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send")); //$NON-NLS-1$ //$NON-NLS-2$
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rate.setText("");
				jakinarazpenak.setText("");
				onartuGaldera.setVisible(false);
				bai.setVisible(false);
				ez.setVisible(false);
				getSelectedMezua();
				bai.setVisible(false);
				ez.setVisible(false);
				emaitza.setVisible(false);
		    	batetik.setText("");
		    	labelEmaitza.setText("");
		    	invalid.setText("");
				if(selectedMezua instanceof BezeroartekoMezua) {
					send.setEnabled(false);
					send.setVisible(false);
					String mota = selectedBezeroartekoMezua.getMota();
					bidalia.setVisible(true);
					remove.setBounds(332, 353, 89, 23);
					remove.setVisible(true);
					remove.setEnabled(true);
					if(mota.equals("eskaera")) {
						invalid.setText("");
						if(bai.isSelected()) {
							double onura=-1;
							try {
								onura = Double.valueOf(emaitza.getText());
							}catch(Exception e) {
								invalid.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
							}
							if(onura>=0 && onura < 1) {
								BLFacade facade = MainGUI.getBusinessLogic();
								String mezuOsoa = ResourceBundle.getBundle("Etiquetas").getString("IAccept")+onura*100+" "+ResourceBundle.getBundle("Etiquetas").getString("IAccept2");
								BezeroartekoMezua bamez1 = new BezeroartekoMezua(mezuOsoa, ResourceBundle.getBundle("Etiquetas").getString("Acceptance"), "errepikatuak eskaera onartu", selectedBezeroartekoMezua.getZenbatApostatu(), selectedBezeroartekoMezua.getHilabeteanZenbat(), onura, bezeroa, selectedBezeroartekoMezua.getIgorlea());
								bezeroa=facade.bidaliMezua(bezeroa, selectedBezeroartekoMezua.getIgorlea(), bamez1);
								bidalia.setVisible(true);
								send.setVisible(false);
								send.setEnabled(false);
								tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Answered"), selectedRowInd, 3);
								selectedBezeroartekoMezua.setIrakurrita(true);
								facade.mezuaIrakurri(selectedBezeroartekoMezua);
							}else {
								invalid.setText(ResourceBundle.getBundle("Etiquetas").getString("Invalid"));
								bidalia.setVisible(false);
								remove.setVisible(false);
								remove.setEnabled(false);
								send.setEnabled(true);
								send.setVisible(true);
							}
						}else {
							BLFacade facade = MainGUI.getBusinessLogic();
							String mezuOsoa = ResourceBundle.getBundle("Etiquetas").getString("NoAcceptRequest")+" "+emaitza.getText();
							BezeroartekoMezua bamez2 = new BezeroartekoMezua(mezuOsoa, ResourceBundle.getBundle("Etiquetas").getString("Denial"), "errepikatuak eskaera ukatu", -1, -1, -1, bezeroa, selectedBezeroartekoMezua.getIgorlea());
							bezeroa=facade.bidaliMezua(bezeroa, selectedBezeroartekoMezua.getIgorlea(), bamez2);
							tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Answered"), selectedRowInd, 3);
							selectedBezeroartekoMezua.setIrakurrita(true);
							facade.mezuaIrakurri(selectedBezeroartekoMezua);
						}
					}else if (mota.equals("errepikatuak eskaera onartu")) {
						if(bai.isSelected()) {
							BLFacade facade = MainGUI.getBusinessLogic();
							BezeroartekoMezua bamez3 = new BezeroartekoMezua(ResourceBundle.getBundle("Etiquetas").getString("StartRepeat"), ResourceBundle.getBundle("Etiquetas").getString("StartRepeating"), "errepikatzen hasi", selectedBezeroartekoMezua.getZenbatApostatu(), selectedBezeroartekoMezua.getHilabeteanZenbat(), selectedBezeroartekoMezua.getZenbatErrepikatuarentzat(), bezeroa, selectedBezeroartekoMezua.getIgorlea());
							bezeroa=facade.bidaliMezua(bezeroa, selectedBezeroartekoMezua.getIgorlea(), bamez3);
							tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Answered"), selectedRowInd, 3);
							selectedBezeroartekoMezua.setIrakurrita(true);
							facade.mezuaIrakurri(selectedBezeroartekoMezua);
							facade.errepikatu(bezeroa, selectedBezeroartekoMezua.getIgorlea(), selectedBezeroartekoMezua.getZenbatApostatu(), selectedBezeroartekoMezua.getHilabeteanZenbat(), selectedBezeroartekoMezua.getZenbatErrepikatuarentzat());
						}else {
							BLFacade facade = MainGUI.getBusinessLogic();
							if(selectedBezeroartekoMezua.getIgorlea().getKomisioAutomatikoa()==-1) {
								BezeroartekoMezua bamez4 = new BezeroartekoMezua(ResourceBundle.getBundle("Etiquetas").getString("IsNoAgreement"), ResourceBundle.getBundle("Etiquetas").getString("NoAgreement"), "akordiorik ez", -1, -1, -1, bezeroa, selectedBezeroartekoMezua.getIgorlea());	
								facade.bidaliMezua(bezeroa, selectedBezeroartekoMezua.getIgorlea(), bamez4);
							}
							tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Answered"), selectedRowInd, 3);
							selectedBezeroartekoMezua.setIrakurrita(true);
							facade.mezuaIrakurri(selectedBezeroartekoMezua);
						}
					}
				}else {
					if(!selectedArretaMezua.isAzkena()) {
						String mezuarenTestua = textArea.getText();
						if(mezuarenTestua.length()==0) {
							jakinarazpenak.setForeground(Color.RED);
							jakinarazpenak.setText(ResourceBundle.getBundle("Etiquetas").getString("MessageEmpty"));
						}else {
							jakinarazpenak.setForeground(Color.GREEN);
							jakinarazpenak.setText(ResourceBundle.getBundle("Etiquetas").getString("Sent"));
							send.setEnabled(false);
							send.setVisible(false);
							remove.setBounds(501, 517, 89, 23);
							remove.setVisible(true);
							remove.setEnabled(true);
							itzuliMezura.setVisible(false);
							itzuliMezura.setEnabled(false);
							BLFacade facade = MainGUI.getBusinessLogic();
							facade.arretaMezuaBidali(getElkarrizketa(selectedArretaMezua), mezuarenTestua, true);
							facade.mezuaIrakurri(selectedArretaMezua);
							selectedArretaMezua.setIrakurrita(true);
							tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Answered"), selectedRowInd, 3);
						}
					}else {
						Integer puntuazioa = 0;
						if(bat.isSelected()) {
							puntuazioa = 1;
						}else if(bi.isSelected()) {
							puntuazioa = 2;
						}else if(hiru.isSelected()) {
							puntuazioa = 3;
						}else if(lau.isSelected()) {
							puntuazioa = 4;
						}else if(bost.isSelected()) {
							puntuazioa = 5;
						}
						bat.setVisible(false);
						bi.setVisible(false);
						hiru.setVisible(false);
						lau.setVisible(false);
						bost.setVisible(false);
						rate.setText("");
						textArea.setEditable(false);
						BLFacade facade = MainGUI.getBusinessLogic();
						facade.gehituPuntuazioa(getElkarrizketa(selectedArretaMezua),puntuazioa);
						tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Answered"), selectedRowInd, 3);
						selectedArretaMezua.setIrakurrita(true);
						facade.mezuaIrakurri(selectedArretaMezua);
						send.setEnabled(false);
						send.setVisible(false);
					}
				}
			}
		});
		send.setBounds(233, 353, 89, 23);
		send.setVisible(false);
		getContentPane().add(send);
		
		invalid = new JLabel("\r\n");
		invalid.setBounds(434, 316, 46, 14);
		getContentPane().add(invalid);
		
		bidalia = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sent"));
		bidalia.setForeground(Color.GREEN);
		bidalia.setBounds(252, 357, 100, 14);
		bidalia.setVisible(false);
		getContentPane().add(bidalia);
		
		komisioa = new JLabel("");
		komisioa.setBounds(68, 291, 438, 14);
		getContentPane().add(komisioa);
		
		remove = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Remove"));
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bat.setVisible(false);
				bi.setVisible(false);
				hiru.setVisible(false);
				lau.setVisible(false);
				bost.setVisible(false);
				rate.setText("");
				jakinarazpenak.setText("");
				textArea.setText("");
				textArea.setVisible(false);
				nork.setText("");
				mezua.setText("");
				baldintzak.setText("");
				euroko.setText("");
				hilabetean.setText("");
				komisioa.setText("");
				onartuGaldera.setVisible(false);
				bai.setVisible(false);
				ez.setVisible(false);
				getSelectedMezua();
				bai.setVisible(false);
				ez.setVisible(false);
				send.setEnabled(false);
				send.setVisible(false);
				emaitza.setVisible(false);
				emaitza.setText("");
		    	batetik.setText("");
		    	labelEmaitza.setText("");
		    	invalid.setText("");
		    	remove.setEnabled(false);
				remove.setVisible(false);
				bidalia.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.removeMezua(selectedMezua);
				mezuak.remove(selectedMezua);
				eguneratuMezuak();
				tableMezua.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableMezua.getColumnModel().getColumn(1).setPreferredWidth(25);
				tableMezua.getColumnModel().getColumn(2).setPreferredWidth(120);
				tableMezua.getColumnModel().getColumn(3).setPreferredWidth(15);
			}
		});
		remove.setEnabled(false);
		remove.setVisible(false);
		remove.setBounds(332, 353, 89, 23);
		getContentPane().add(remove);
		
		Eguneratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Update")); //$NON-NLS-1$ //$NON-NLS-2$
		Eguneratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				BezeroaContainer x = facade.getBezeroaContainer(bezeroa);
				bezeroa=x.getBezeroa();
				elkarrizketak=x.getElkarrizketak();
				eguneratuMezuak();
				tableMezua.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableMezua.getColumnModel().getColumn(1).setPreferredWidth(25);
				tableMezua.getColumnModel().getColumn(2).setPreferredWidth(120);
				tableMezua.getColumnModel().getColumn(3).setPreferredWidth(15);
			}
		});
		Eguneratu.setBounds(467, 16, 137, 23);
		getContentPane().add(Eguneratu);
		
		erantzun = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Reply")); //$NON-NLS-1$ //$NON-NLS-2$
		erantzun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				textArea.setEditable(true);
				itzuliMezura.setEnabled(true);
				itzuliMezura.setVisible(true);
				erantzun.setVisible(false);
				erantzun.setEnabled(false);
				send.setBounds(252,517,89, 23);
				send.setVisible(true);
				send.setEnabled(true);
			}
		});
		erantzun.setBounds(501, 517, 89, 23);
		getContentPane().add(erantzun);
		erantzun.setVisible(false);
		erantzun.setEnabled(false);
		
		itzuliMezura = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BackToMessage")); //$NON-NLS-1$ //$NON-NLS-2$
		itzuliMezura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(selectedMezua.getMezua());
				textArea.setEditable(false);
				erantzun.setVisible(true);
				erantzun.setEnabled(true);
				itzuliMezura.setVisible(false);
				itzuliMezura.setEnabled(false);
			}
		});
		itzuliMezura.setBounds(442, 517, 148, 23);
		getContentPane().add(itzuliMezura);
		itzuliMezura.setEnabled(false);
		
		jakinarazpenak = new JLabel("");
		jakinarazpenak.setBounds(103, 491, 465, 14);
		getContentPane().add(jakinarazpenak);
		
		bat = new JRadioButton("");
		bat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send.setEnabled(true);
				send.setVisible(true);
			}
		});
		bat.setBackground(Color.WHITE);
		buttonGroup_1.add(bat);
		bat.setBounds(69, 415, 21, 23);
		getContentPane().add(bat);
		bat.setVisible(false);
		
		bi = new JRadioButton("");
		bi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send.setEnabled(true);
				send.setVisible(true);
			}
		});
		bi.setBackground(Color.WHITE);
		buttonGroup_1.add(bi);
		bi.setBounds(103, 415, 21, 23);
		getContentPane().add(bi);
		bi.setVisible(false);
		
		hiru = new JRadioButton("");
		hiru.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send.setEnabled(true);
				send.setVisible(true);
			}
		});
		hiru.setBackground(Color.WHITE);
		buttonGroup_1.add(hiru);
		hiru.setBounds(137, 415, 21, 23);
		getContentPane().add(hiru);
		hiru.setVisible(false);
		
		lau = new JRadioButton("");
		lau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send.setEnabled(true);
				send.setVisible(true);
			}
		});
		lau.setBackground(Color.WHITE);
		buttonGroup_1.add(lau);
		lau.setBounds(171, 415, 21, 23);
		getContentPane().add(lau);
		lau.setVisible(false);
		
		bost = new JRadioButton("");
		bost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send.setEnabled(true);
				send.setVisible(true);
			}
		});
		bost.setBackground(Color.WHITE);
		buttonGroup_1.add(bost);
		bost.setBounds(205, 415, 21, 23);
		getContentPane().add(bost);
		bost.setVisible(false);
		
		rate = new JLabel("");
		rate.setBounds(68, 389, 287, 14);
		getContentPane().add(rate);
		itzuliMezura.setVisible(false);
		
		textArea = new JTextArea();
		textArea.setText("");
		textArea.setVisible(false);
		textArea.setBounds(29, 172, 540, 300);
		getContentPane().add(textArea);
		
		tableMezua.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableMezua.getColumnModel().getColumn(1).setPreferredWidth(25);
		tableMezua.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableMezua.getColumnModel().getColumn(3).setPreferredWidth(15);
		tableMezua.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rate.setText("");
				jakinarazpenak.setText("");
				textArea.setText("");
				textArea.setVisible(false);
				nork.setText("");
				mezua.setText("");
				baldintzak.setText("");
				euroko.setText("");
				hilabetean.setText("");
				komisioa.setText("");
				onartuGaldera.setVisible(false);
				bai.setVisible(false);
				ez.setVisible(false);
				bat.setVisible(false);
				bi.setVisible(false);
				hiru.setVisible(false);
				lau.setVisible(false);
				bost.setVisible(false);
				send.setEnabled(false);
				send.setVisible(false);
				emaitza.setVisible(false);
				emaitza.setText("");
		    	batetik.setText("");
		    	labelEmaitza.setText("");
		    	invalid.setText("");
		    	remove.setEnabled(false);
				remove.setVisible(false);
				bidalia.setVisible(false);
				getSelectedMezua();
				if(selectedMezua instanceof BezeroartekoMezua) {
					back.setBounds(19, 385, 89, 23);
					aldatuTamaina(630, 455);
					String mota = selectedBezeroartekoMezua.getMota();
					if(mota.equals("eskaera")) {
						nork.setText(ResourceBundle.getBundle("Etiquetas").getString("Who")+": "+selectedBezeroartekoMezua.getIgorlea());
						mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Message")+" "+selectedBezeroartekoMezua.getMezua());
						baldintzak.setText(ResourceBundle.getBundle("Etiquetas").getString("Conditions"));
						euroko.setText(ResourceBundle.getBundle("Etiquetas").getString("EuroBet")+" "+selectedBezeroartekoMezua.getZenbatApostatu()+"€");
						hilabetean.setText("-"+ResourceBundle.getBundle("Etiquetas").getString("MaxMonth")+" "+selectedBezeroartekoMezua.getHilabeteanZenbat()+"€");
						if(!selectedBezeroartekoMezua.isIrakurrita()) {
							onartuGaldera.setBounds(50, 291, 210, 14);
							onartuGaldera.setVisible(true);
							bai.setBounds(36, 312, 69, 23);
							bai.setVisible(true);
							ez.setBounds(107, 312, 69, 23);
							ez.setVisible(true);
						}else {
							remove.setBounds(332, 353, 89, 23);
							remove.setEnabled(true);
							remove.setVisible(true);
						}
					}else if (mota.equals("errepikatuak eskaera ukatu")) {
						nork.setText(ResourceBundle.getBundle("Etiquetas").getString("Who")+": "+selectedBezeroartekoMezua.getIgorlea());
						mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Message")+" "+selectedBezeroartekoMezua.getMezua());
						tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Read"), selectedRowInd, 3);
						if(!selectedBezeroartekoMezua.isIrakurrita()) {
							BLFacade facade = MainGUI.getBusinessLogic();
							facade.mezuaIrakurri(selectedBezeroartekoMezua);
							selectedBezeroartekoMezua.setIrakurrita(true);
						}
						remove.setBounds(332, 353, 89, 23);
						remove.setEnabled(true);
						remove.setVisible(true);
					}else if (mota.equals("errepikatuak eskaera onartu")) {
						nork.setText(ResourceBundle.getBundle("Etiquetas").getString("Who")+": "+selectedBezeroartekoMezua.getIgorlea());
						mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Message")+" "+selectedBezeroartekoMezua.getMezua());
						baldintzak.setText(ResourceBundle.getBundle("Etiquetas").getString("Conditions"));
						euroko.setText(ResourceBundle.getBundle("Etiquetas").getString("EuroBet")+" "+selectedBezeroartekoMezua.getZenbatApostatu()+"€");
						hilabetean.setText("-"+ResourceBundle.getBundle("Etiquetas").getString("MaxMonth")+" "+selectedBezeroartekoMezua.getHilabeteanZenbat()+"€");
						komisioa.setText("-"+ResourceBundle.getBundle("Etiquetas").getString("Commission")+" %"+selectedBezeroartekoMezua.getZenbatErrepikatuarentzat()*100);
						if(!selectedBezeroartekoMezua.isIrakurrita()) {
							onartuGaldera.setBounds(225, 301, 210, 14);
							onartuGaldera.setVisible(true);
							bai.setBounds(211, 322, 69, 23);
							bai.setVisible(true);
							ez.setBounds(282, 322, 69, 23);
							ez.setVisible(true);
						}else {
							remove.setBounds(332, 353, 89, 23);
							remove.setEnabled(true);
							remove.setVisible(true);
						}
					}else if (mota.equals("errepikatzen hasi") || mota.equals("akordiorik ez")) {
						nork.setText(ResourceBundle.getBundle("Etiquetas").getString("Who")+": "+selectedBezeroartekoMezua.getIgorlea());
						mezua.setText(ResourceBundle.getBundle("Etiquetas").getString("Message")+" "+selectedBezeroartekoMezua.getMezua());
						tableModelMezuak.setValueAt(ResourceBundle.getBundle("Etiquetas").getString("Read"), selectedRowInd, 3);
						if(!selectedBezeroartekoMezua.isIrakurrita()) {
							selectedBezeroartekoMezua.setIrakurrita(true);
							BLFacade facade = MainGUI.getBusinessLogic();
							facade.mezuaIrakurri(selectedBezeroartekoMezua);
						}
						remove.setBounds(332, 353, 89, 23);
						remove.setEnabled(true);
						remove.setVisible(true);
					}
				}else {
					aldatuTamaina(630, 590);
					back.setBounds(19, 517, 89, 23);
					send.setBounds(233, 517, 89, 23);
					if(!selectedArretaMezua.isIrakurrita()) {
						erantzun.setVisible(true);
						erantzun.setEnabled(true);
					}else {
						erantzun.setVisible(false);
						erantzun.setEnabled(false);
						remove.setBounds(501, 517, 89, 23);
						remove.setVisible(true);
						remove.setEnabled(true);
					}
					textArea.setVisible(true);
					textArea.setEditable(false);
					if(selectedArretaMezua.isAzkena()) {
						textArea.setText(ResourceBundle.getBundle("Etiquetas").getString("FinalMessage"));
						send.setEnabled(false);
						send.setVisible(false);
						erantzun.setEnabled(false);
						erantzun.setVisible(false);
						if(!selectedArretaMezua.isIrakurrita()) {
							bat.setVisible(true);
							bi.setVisible(true);
							hiru.setVisible(true);
							lau.setVisible(true);
							bost.setVisible(true);
							rate.setText(ResourceBundle.getBundle("Etiquetas").getString("Rate"));
						}
					}else {
						textArea.setText(selectedArretaMezua.getMezua());
					}
					
				}
			}
		});
	}
	
	private void back() {
		this.setVisible(false);
		aurrekoa.setBezeroa(bezeroa);
		aurrekoa.setVisible(true);
	}
	
	private void getSelectedMezua() {
		selectedRowInd = tableMezua.getSelectedRow();
		selectedMezua=(Mezua)tableModelMezuak.getValueAt(selectedRowInd,0);
		if(selectedMezua!=null) {
			if(selectedMezua instanceof ArretaMezua) {
				selectedArretaMezua = (ArretaMezua) selectedMezua;
				selectedBezeroartekoMezua = null;
			}else {
				selectedBezeroartekoMezua = (BezeroartekoMezua) selectedMezua;
				selectedArretaMezua = null;
			}
		}else {
			selectedArretaMezua=null;
			selectedBezeroartekoMezua=null;
		}
	}
	
	private void eguneratuMezuak() {
		tableModelMezuak.setDataVector(null, columnNamesMezuak);
		BLFacade facade = MainGUI.getBusinessLogic();
		mezuak=facade.getMezuak(bezeroa);
		if(!mezuak.isEmpty()) {
			tableMezua.setVisible(true);
			scrollPaneMezuak.setVisible(true);
			postontziaHutsik.setVisible(false);
			for(Mezua m : mezuak) {
				Vector<Object> row = new Vector<Object>();
				row.add(m);
				if(m instanceof BezeroartekoMezua) {
					row.add(((BezeroartekoMezua)m).getIgorlea());
					row.add(((BezeroartekoMezua)m).getGaia());
					String mota = ((BezeroartekoMezua)m).getMota();
					String egoera = "";
					if(mota.equals("eskaera") || mota.equals("errepikatuak eskaera onartu")) {
						if(m.isIrakurrita()) {
							egoera=ResourceBundle.getBundle("Etiquetas").getString("Answered");
						}else {
							egoera=ResourceBundle.getBundle("Etiquetas").getString("Unanswered");
						}
					}else{
						if(m.isIrakurrita()) {
							egoera=ResourceBundle.getBundle("Etiquetas").getString("Read");
						}else {
							egoera=ResourceBundle.getBundle("Etiquetas").getString("Unread");
						}
					}
					row.add(egoera);
				}else {
					if(!((ArretaMezua)m).isAzkena()) {
						row.add(ResourceBundle.getBundle("Etiquetas").getString("Worker"));
					}else {
						row.add(ResourceBundle.getBundle("Etiquetas").getString("Admin"));
					}
					row.add(getElkarrizketa((ArretaMezua)m).getGaia());
					String egoera;
					if(m.isIrakurrita()) {
						egoera=ResourceBundle.getBundle("Etiquetas").getString("Answered");
					}else {
						egoera=ResourceBundle.getBundle("Etiquetas").getString("Unanswered");
					}
					System.out.println(egoera);
					row.add(egoera);
					System.out.println(row);
				}
				tableModelMezuak.addRow(row);
			}
		}else {
			tableMezua.setVisible(false);
			scrollPaneMezuak.setVisible(false);
			postontziaHutsik.setVisible(true);
		}
	}
	
	private void aldatuTamaina(int x, int y) {
		this.setSize(new Dimension(x, y));
	}
	
	private ArretaElkarrizketa getElkarrizketa(ArretaMezua m) {
		for(ArretaElkarrizketa ae : elkarrizketak) {
			for(ArretaMezua am : ae.getLangileakBidalitakoak()) {
				if(am.getIdentifikadorea()==m.getIdentifikadorea()) {
					return ae;
				}
			}
		}
		return null;
	}
}
