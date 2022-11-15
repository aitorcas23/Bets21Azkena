package gui;


import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import businesslogic.BLFacade;

import java.awt.*;
import java.awt.event.*;

import domain.ArretaElkarrizketa;
import domain.ArretaMezua;
import domain.Bezeroa;
import domain.Langilea;

public class ArretaZerbitzuaEmanGUI extends JFrame {
	LangileaGUI aurrekoa;
	private Langilea langilea; 
	//private Vector<BezeroartekoMezuaContainer> mezuak;
	
	private static final long serialVersionUID = 1L;
	
	//private BezeroartekoMezuaContainer selectedMezua;
	private JScrollPane scrollPaneMezuak = new JScrollPane();
	private JTable tableMezua = new JTable();
	private DefaultTableModel tableModelMezuak;
	private int indMezua;
	private JLabel labelEmaitza;
	private JLabel nork;
	private JLabel baldintzak;
	private JLabel hilabetean;
	private JButton send;
	private JLabel invalid;
	private String[] columnNamesMezuak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Date"), 
			ResourceBundle.getBundle("Etiquetas").getString("Who"), 
	};
	private JLabel jakinarazpenak;
	private JComboBox<ArretaElkarrizketa> elkarrizketakConbo;
	private JLabel choose;
	private ArretaElkarrizketa selectedElkarrizketa;
	private JTextArea textArea; 
	private JLabel gaia;
	private ArretaMezua selectedMezua;
	private Vector<ArretaMezua> mezuak;
	private JButton erantzun;
	
	private DefaultComboBoxModel<ArretaElkarrizketa> elkarrizketak = new DefaultComboBoxModel<ArretaElkarrizketa>();
	JComboBox<Bezeroa> comboBox = new JComboBox<Bezeroa>();
	private JButton mezuraItzuli;
	private JLabel jakinarazpenak2;
	private JButton stop;
	private JButton bukatu;
	private JButton back;
	
	public ArretaZerbitzuaEmanGUI(LangileaGUI aurrekoa, Langilea langilea) {
		try {
			this.aurrekoa=aurrekoa;
			this.langilea=langilea;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		
		scrollPaneMezuak = new JScrollPane();
		scrollPaneMezuak.setBounds(new Rectangle(140, 273, 406, 116));
		scrollPaneMezuak.setBounds(141, 164, 357, 116);
		getContentPane().add(scrollPaneMezuak);
		scrollPaneMezuak.setVisible(false);
		
		scrollPaneMezuak.setViewportView(tableMezua);
		tableModelMezuak = new DefaultTableModel(null, columnNamesMezuak);
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(630, 730));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ProvideCustomer"));
		
		back = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				back();
			}
		});
		back.setBounds(10, 627, 89, 23);
		getContentPane().add(back);
		
		nork = new JLabel("");
		nork.setBounds(50, 172, 287, 14);
		getContentPane().add(nork);
		
		baldintzak = new JLabel("");
		baldintzak.setBounds(50, 222, 237, 14);
		getContentPane().add(baldintzak);
		
		tableMezua.setModel(tableModelMezuak);
		
		hilabetean = new JLabel("");
		hilabetean.setBounds(68, 266, 438, 14);
		getContentPane().add(hilabetean);
		
		labelEmaitza = new JLabel("");
		labelEmaitza.setBounds(302, 291, 214, 14);
		getContentPane().add(labelEmaitza);
		
		send = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Send")); //$NON-NLS-1$ //$NON-NLS-2$
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jakinarazpenak.setText("");
				jakinarazpenak2.setText("");
				mezuraItzuli.setVisible(false);
				mezuraItzuli.setEnabled(false);
				String mezuarenTestua = textArea.getText();
				if(mezuarenTestua.length()==0) {
					jakinarazpenak2.setForeground(Color.RED);
					jakinarazpenak2.setText(ResourceBundle.getBundle("Etiquetas").getString("MessageEmpty"));
				}else {
					BLFacade facade=MainGUI.getBusinessLogic();
					selectedElkarrizketa = facade.arretaMezuaBidali(selectedElkarrizketa, mezuarenTestua, false);
					jakinarazpenak2.setForeground(Color.GREEN);
					jakinarazpenak2.setText(ResourceBundle.getBundle("Etiquetas").getString("Sent"));
					textArea.setText(selectedMezua.getMezua());
					textArea.setEditable(false);
					send.setVisible(false);
					send.setEnabled(false);
					eguneratuMezuak();
				}
			}
		});
		send.setBounds(248, 627, 89, 23);
		send.setVisible(false);
		getContentPane().add(send);
		
		invalid = new JLabel("\r\n");
		invalid.setBounds(434, 316, 46, 14);
		getContentPane().add(invalid);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetCustomer")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jakinarazpenak.setText("");
				jakinarazpenak2.setText("");
				BLFacade facade=MainGUI.getBusinessLogic();
				ArretaElkarrizketa elkarrizketaBerria = facade.bezeroaEsleitu(langilea);
				jakinarazpenak.setText("");
				if(elkarrizketaBerria==null) {
					jakinarazpenak.setForeground(Color.BLACK);
					jakinarazpenak.setText(ResourceBundle.getBundle("Etiquetas").getString("NoAskingHelp"));
				}else {
					send.setVisible(false);
					send.setEnabled(false);
					erantzun.setVisible(false);
					erantzun.setEnabled(false);
					mezuraItzuli.setVisible(false);
					mezuraItzuli.setEnabled(false);
					textArea.setVisible(false);
					aldatuTamaina(630,327);
					back.setBounds(10, 257, 89, 23);
					choose.setVisible(true);
					elkarrizketakConbo.setVisible(true);	
					choose.setText(ResourceBundle.getBundle("Etiquetas").getString("ChooseCustomer"));
					elkarrizketak.addElement(elkarrizketaBerria);
					jakinarazpenak.setText(ResourceBundle.getBundle("Etiquetas").getString("CustomerAssigned"));
				}
			}
		});
		btnNewButton.setBounds(169, 11, 299, 31);
		getContentPane().add(btnNewButton);
		
		elkarrizketakConbo = new JComboBox<ArretaElkarrizketa>();
		elkarrizketakConbo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jakinarazpenak.setText("");
				jakinarazpenak2.setText("");
				selectedElkarrizketa=(ArretaElkarrizketa) elkarrizketakConbo.getSelectedItem();
				if(selectedElkarrizketa!=null) {
					BLFacade facade=MainGUI.getBusinessLogic();
					selectedElkarrizketa=facade.getArretaElkarrizketa(selectedElkarrizketa.getIdentifikadorea());
					bukatu.setVisible(true);
					bukatu.setEnabled(true);
					stop.setVisible(true);
					stop.setEnabled(true);
					send.setVisible(false);
					send.setEnabled(false);
					erantzun.setVisible(false);
					erantzun.setEnabled(false);
					mezuraItzuli.setVisible(false);
					mezuraItzuli.setEnabled(false);
					textArea.setVisible(false);
					aldatuTamaina(630,327);
					back.setBounds(10, 257, 89, 23);
				}else {
					tableMezua.setVisible(false);
					scrollPaneMezuak.setVisible(false);
					send.setVisible(false);
					send.setEnabled(false);
					erantzun.setVisible(false);
					erantzun.setEnabled(false);
					mezuraItzuli.setVisible(false);
					mezuraItzuli.setEnabled(false);
					textArea.setVisible(false);
					gaia.setText("");
					aldatuTamaina(630,108);
					back.setBounds(10, 15, 89, 23);
					bukatu.setVisible(false);
					bukatu.setEnabled(false);
					stop.setVisible(false);
					stop.setEnabled(false);
				}
				eguneratuMezuak();
			}
		});
		elkarrizketakConbo.setModel(elkarrizketak);
		elkarrizketakConbo.setVisible(false);
		elkarrizketakConbo.setBounds(223, 89, 257, 22);
		getContentPane().add(elkarrizketakConbo);
		
		choose = new JLabel("");
		choose.setFont(choose.getFont().deriveFont(choose.getFont().getStyle() | Font.BOLD));
		choose.setBounds(19, 93, 514, 14);
		getContentPane().add(choose);
		choose.setVisible(false);
		
		jakinarazpenak = new JLabel("");
		jakinarazpenak.setBounds(87, 53, 489, 14);
		getContentPane().add(jakinarazpenak);
		
		textArea = new JTextArea();
		textArea.setText("");
		textArea.setBounds(36, 291, 540, 300);
		textArea.setEditable(false);
		textArea.setVisible(false);
		getContentPane().add(textArea);
		
		gaia = new JLabel("");
		gaia.setFont(gaia.getFont().deriveFont(gaia.getFont().getStyle() | Font.BOLD, gaia.getFont().getSize() + 2f));
		gaia.setBounds(141, 118, 463, 38);
		getContentPane().add(gaia);
		
		erantzun = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Reply")); //$NON-NLS-1$ //$NON-NLS-2$
		erantzun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jakinarazpenak.setText("");
				jakinarazpenak2.setText("");
				textArea.setText("");
				erantzun.setVisible(false);
				erantzun.setEnabled(false);
				textArea.setEditable(true);
				mezuraItzuli.setVisible(true);
				mezuraItzuli.setEnabled(true);
				send.setVisible(true);
				send.setEnabled(true);
			}
		});
		erantzun.setBounds(491, 627, 113, 23);
		erantzun.setEnabled(false);
		erantzun.setVisible(false);
		getContentPane().add(erantzun);
		
		mezuraItzuli = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BackToMessage")); //$NON-NLS-1$ //$NON-NLS-2$
		mezuraItzuli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				erantzun.setVisible(true);
				erantzun.setEnabled(true);
				textArea.setEditable(false);
				textArea.setText(selectedMezua.getMezua());
				mezuraItzuli.setVisible(false);
				mezuraItzuli.setEnabled(false);
			}
		});
		mezuraItzuli.setBounds(460, 627, 144, 23);
		getContentPane().add(mezuraItzuli);
		mezuraItzuli.setEnabled(false);
		mezuraItzuli.setVisible(false);
		
		jakinarazpenak2 = new JLabel("");
		jakinarazpenak2.setBounds(141, 602, 357, 14);
		getContentPane().add(jakinarazpenak2);
		
		stop = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Stop")); //$NON-NLS-1$ //$NON-NLS-2$
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade=MainGUI.getBusinessLogic();
				facade.geldituElkarrizketa(selectedElkarrizketa);
				eguneratuElkarrizketak();
			}
		});
		stop.setBounds(515, 78, 89, 23);
		stop.setVisible(false);
		stop.setEnabled(false);
		getContentPane().add(stop);
		
		bukatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Finish")); //$NON-NLS-1$ //$NON-NLS-2$
		bukatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade=MainGUI.getBusinessLogic();
				facade.amaituElkarrizketa(selectedElkarrizketa);
				send.setVisible(false);
				send.setEnabled(false);
				erantzun.setVisible(false);
				erantzun.setEnabled(false);
				mezuraItzuli.setVisible(false);
				mezuraItzuli.setEnabled(false);
				textArea.setVisible(false);
				aldatuTamaina(630,327);
				back.setBounds(10, 257, 89, 23);
				eguneratuElkarrizketak();
			}
		});
		bukatu.setBounds(515, 110, 89, 23);
		bukatu.setVisible(false);
		bukatu.setEnabled(false);
		
		getContentPane().add(bukatu);
		
		tableMezua.getColumnModel().getColumn(0).setPreferredWidth(120);
		tableMezua.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableMezua.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				aldatuTamaina(630, 730);
				mezuraItzuli.setVisible(false);
				mezuraItzuli.setEnabled(false);
				back.setBounds(10, 627, 89, 23);
				jakinarazpenak.setText("");
				jakinarazpenak2.setText("");
				erantzun.setVisible(false);
				erantzun.setEnabled(false);
				indMezua = tableMezua.getSelectedRow();
				selectedMezua=(ArretaMezua)tableModelMezuak.getValueAt(indMezua,0);
				textArea.setVisible(true);
				textArea.setEditable(false);
				textArea.setText(selectedMezua.getMezua());
				if(selectedElkarrizketa.azkenaBezeroak() && indMezua==(mezuak.size()-1)) {
					erantzun.setVisible(true);
					erantzun.setEnabled(true);
				}
			}
		});
	
		eguneratuElkarrizketak();
		if(selectedElkarrizketa!=null && selectedMezua==null) {
			send.setVisible(false);
			send.setEnabled(false);
			erantzun.setVisible(false);
			erantzun.setEnabled(false);
			mezuraItzuli.setVisible(false);
			mezuraItzuli.setEnabled(false);
			textArea.setVisible(false);
			aldatuTamaina(630, 327);
			back.setBounds(10, 257, 89, 23);
		}
	}
	
	private void back() {
		this.setVisible(false);
		aurrekoa.setLangilea(langilea);
		aurrekoa.setVisible(true);
	}
	
	private void eguneratuElkarrizketak() {
		elkarrizketak.removeAllElements();
		BLFacade facade=MainGUI.getBusinessLogic();
		langilea = facade.getLangilea(langilea.getErabiltzaileIzena());
		Vector<ArretaElkarrizketa> elkarrizketaZerrenda = langilea.getArretaElkarrizketak();
		if(elkarrizketaZerrenda.isEmpty()) {
			tableMezua.setVisible(false);
			scrollPaneMezuak.setVisible(false);
			send.setVisible(false);
			send.setEnabled(false);
			erantzun.setVisible(false);
			erantzun.setEnabled(false);
			mezuraItzuli.setVisible(false);
			mezuraItzuli.setEnabled(false);
			textArea.setVisible(false);
			gaia.setText("");
			aldatuTamaina(630,108);
			back.setBounds(10, 15, 89, 23);
			choose.setVisible(false);
			elkarrizketakConbo.setVisible(false);	
			choose.setText(ResourceBundle.getBundle("Etiquetas").getString("NoCustomerAssigned"));
		}else {
			choose.setVisible(true);
			elkarrizketakConbo.setVisible(true);	
			choose.setText(ResourceBundle.getBundle("Etiquetas").getString("ChooseCustomer"));
		}
		for(ArretaElkarrizketa ae : elkarrizketaZerrenda) {
			elkarrizketak.addElement(ae);
		}
	}
	
	private void eguneratuMezuak() {
		if(selectedElkarrizketa!=null) {
			scrollPaneMezuak.setVisible(true);
			tableMezua.setVisible(true);
			tableModelMezuak.setDataVector(null, columnNamesMezuak);
			tableMezua.getColumnModel().getColumn(0).setPreferredWidth(120);
			tableMezua.getColumnModel().getColumn(1).setPreferredWidth(20);
			gaia.setText(ResourceBundle.getBundle("Etiquetas").getString("Subject")+": "+selectedElkarrizketa.getGaia());
			mezuak = selectedElkarrizketa.getMezuak();
			Vector<Object> row;
			boolean langileak = false;
			for(ArretaMezua am : mezuak) {
				row = new Vector<Object>();
				row.add(am);
				if(langileak) {
					row.add(ResourceBundle.getBundle("Etiquetas").getString("Worker"));
					langileak=false;
				}else {
					row.add(selectedElkarrizketa.getBezeroa());
					langileak=true;
				}
				row.add(am);
				tableModelMezuak.addRow(row);
			}
		}
		

	}
	
	private void aldatuTamaina(int x, int y) {
		this.setSize(new Dimension(x, y));
	}
}
