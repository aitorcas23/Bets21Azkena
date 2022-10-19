package gui;




import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import businesslogic.BLFacade;

import java.awt.*;
import java.awt.event.*;

import configuration.UtilDate;
import domain.Apustua;
import domain.Bezeroa;
import domain.PronostikoaContainer;
import exceptions.EventFinished;

public class ApostuaEzabatuGUI extends JFrame {
	BezeroaGUI aurrekoa;
	private Bezeroa bezeroa; 
	private Vector<Apustua> apustuak;
	
	private static final long serialVersionUID = 1L;

	private JLabel jlabelMsg;
	private JComboBox<Apustua> comboBox;
	private DefaultComboBoxModel<Apustua> apustuakModel = new DefaultComboBoxModel<Apustua>();
	
	private Apustua selectedApustua;
	private List<PronostikoaContainer> selectedPronostikoak = new Vector<PronostikoaContainer>();
	private JButton removeButton;
	private JScrollPane scrollPaneApustua = new JScrollPane();
	private JTable tableApustua = new JTable();
	private DefaultTableModel tableModelApustua;

	private String[] columnNamesApustua = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventDate"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("Result"),
			ResourceBundle.getBundle("Etiquetas").getString("Kuota"),
			ResourceBundle.getBundle("Etiquetas").getString("FinalResult")
	};
	private JLabel labelJokatua;
	private JLabel labelKuota;
	private JLabel labelIrabazia;
	private JLabel labeMsg;
	
	public ApostuaEzabatuGUI(BezeroaGUI aurrekoa, Bezeroa bezeroa) {
		try {
			this.aurrekoa=aurrekoa;
			this.bezeroa=bezeroa;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		BLFacade facade = MainGUI.getBusinessLogic();
		bezeroa=facade.getBezeroa(bezeroa.getErabiltzaileIzena());
		
		apustuak=bezeroa.getApustuak();

		this.getContentPane().setLayout(null);
		
		this.setSize(new Dimension(630, 470));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DeleteBetGUI"));
		
		jlabelMsg = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		jlabelMsg.setBounds(50, 23, 493, 30);
		getContentPane().add(jlabelMsg);
		
		scrollPaneApustua = new JScrollPane();
		scrollPaneApustua.setBounds(new Rectangle(138, 273, 406, 116));
		scrollPaneApustua.setBounds(19, 114, 585, 116);
		getContentPane().add(scrollPaneApustua);
		scrollPaneApustua.setVisible(false);
		
		scrollPaneApustua.setViewportView(tableApustua);
		tableModelApustua = new DefaultTableModel(null, columnNamesApustua);
		
		tableApustua.setModel(tableModelApustua);
		tableApustua.getColumnModel().getColumn(0).setPreferredWidth(45);
		tableApustua.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableApustua.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableApustua.getColumnModel().getColumn(3).setPreferredWidth(5);
		tableApustua.getColumnModel().getColumn(4).setPreferredWidth(5);
		tableApustua.getColumnModel().getColumn(5).setPreferredWidth(20);
		
		comboBox = new JComboBox<Apustua>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedApustua=((domain.Apustua) comboBox.getSelectedItem());
				tableApustua.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableApustua.getColumnModel().getColumn(1).setPreferredWidth(120);
				tableApustua.getColumnModel().getColumn(2).setPreferredWidth(120);
				tableApustua.getColumnModel().getColumn(3).setPreferredWidth(5);
				tableApustua.getColumnModel().getColumn(4).setPreferredWidth(5);
				tableApustua.getColumnModel().getColumn(5).setPreferredWidth(20);
				if(selectedApustua!=null) {
					removeButton.setVisible(true);
					removeButton.setEnabled(true);
					scrollPaneApustua.setVisible(true);
					tableModelApustua.setDataVector(null, columnNamesApustua);
					BLFacade facade = MainGUI.getBusinessLogic();
					selectedPronostikoak=facade.getPronostikoak(selectedApustua);
					Vector<Object> row;
					for(PronostikoaContainer p : selectedPronostikoak){
						row = new Vector<Object>();
						row.add(UtilDate.getString(p.getGertaera().getEventDate()));
						row.add(p.getGertaera());
						row.add(p.getQuestion());
						row.add(p.getPronostikoa().getDeskripzioa());
						row.add(p.getPronostikoa().getKuota());
						String emaitza = p.getQuestion().getResult();
						if(emaitza==null) {
							row.add("-");
						}else {
							row.add(emaitza);
						}		
						tableModelApustua.addRow(row);
					}
					labelJokatua.setText(ResourceBundle.getBundle("Etiquetas").getString("Played")+" "+selectedApustua.getKopurua());
					labelKuota.setText(ResourceBundle.getBundle("Etiquetas").getString("TotalFee")+" "+selectedApustua.getKuotaTotala());
					labelIrabazia.setText(ResourceBundle.getBundle("Etiquetas").getString("Potential")+" "+selectedApustua.getKopurua()*selectedApustua.getKuotaTotala());
					tableApustua.getColumnModel().getColumn(0).setPreferredWidth(25);
					tableApustua.getColumnModel().getColumn(1).setPreferredWidth(60);
					tableApustua.getColumnModel().getColumn(2).setPreferredWidth(150);
					tableApustua.getColumnModel().getColumn(3).setPreferredWidth(2);
					tableApustua.getColumnModel().getColumn(4).setPreferredWidth(2);
					tableApustua.getColumnModel().getColumn(5).setPreferredWidth(20);
				}else {
					jlabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("NoBets"));
					removeButton.setVisible(false);
					removeButton.setEnabled(false);
					labelJokatua.setText("");
					labelKuota.setText("");
					labelIrabazia.setText("");
				}
			}
		});
		comboBox.setModel(apustuakModel);
		comboBox.setBounds(166, 64, 238, 22);
		getContentPane().add(comboBox);
		if(apustuak.isEmpty()) {
			comboBox.setVisible(false);
		}
		
		removeButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Remove")); //$NON-NLS-1$ //$NON-NLS-2$
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labeMsg.setText("");
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					bezeroa=facade.deleteApustua(selectedApustua);
					labeMsg.setForeground(Color.GREEN);
					labeMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccessEvent"));
					apustuakModel.removeAllElements();
					apustuak=bezeroa.getApustuak();
					if(apustuak.isEmpty()) {
						scrollPaneApustua.setVisible(false);
						//jlabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("NoBets"));
						removeButton.setEnabled(false);
						removeButton.setVisible(false);
					}
					for(Apustua a:apustuak) {
						apustuakModel.addElement(a);
					}
					selectedApustua=((domain.Apustua) comboBox.getSelectedItem());
				}catch(EventFinished e){
					labeMsg.setForeground(Color.RED);
					labeMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("EventsFinished"));
				}
			}
		});
		removeButton.setBounds(454, 267, 89, 23);
		getContentPane().add(removeButton);
		removeButton.setVisible(false);
		removeButton.setEnabled(false);
		
		labelJokatua = new JLabel("");
		labelJokatua.setBounds(68, 241, 137, 14);
		getContentPane().add(labelJokatua);
		
		labelKuota = new JLabel("");
		labelKuota.setBounds(215, 241, 137, 14);
		getContentPane().add(labelKuota);
		
		labelIrabazia = new JLabel("");
		labelIrabazia.setBounds(388, 241, 182, 14);
		getContentPane().add(labelIrabazia);
		
		labeMsg = new JLabel("");
		labeMsg.setForeground(Color.RED);
		labeMsg.setBounds(78, 267, 366, 22);
		getContentPane().add(labeMsg);
		
		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				back();
			}
		});
		jButtonClose.setBounds(new Rectangle(300, 275, 130, 30));
		jButtonClose.setBounds(413, 341, 130, 30);
		getContentPane().add(jButtonClose);
		
		if(apustuak.isEmpty()) {
			jlabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("NoBets"));
		}else {
			jlabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ChooseBet"));
			for(Apustua a:apustuak) {
				apustuakModel.addElement(a);
			}
		}
		
	}
	
	private void back() {
		this.setVisible(false);
		aurrekoa.setVisible(true);
	}
}
