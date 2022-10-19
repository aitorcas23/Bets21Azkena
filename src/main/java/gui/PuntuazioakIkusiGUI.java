package gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businesslogic.BLFacade;
import domain.Langilea;

public class PuntuazioakIkusiGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	AdminGUI aurrekoa;

	
	private JScrollPane scrollPaneBalorazioak = new JScrollPane();
	private JTable tableBalorazioak = new JTable();
	private DefaultTableModel tableModelBalorazioak;
	
	private String[] columnNamesJarraitzaileak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("jName"), 
			ResourceBundle.getBundle("Etiquetas").getString("jFName"),
			ResourceBundle.getBundle("Etiquetas").getString("jSName"),
			ResourceBundle.getBundle("Etiquetas").getString("jUsername"),
			"5",
			"4",
			"3",
			"2",
			"1",
			ResourceBundle.getBundle("Etiquetas").getString("kalifikazioa"),
			
	};
	
	public PuntuazioakIkusiGUI(AdminGUI aurrekoa) {
		try {
			this.aurrekoa=aurrekoa;
			PuntuazioakIkusiGUIExekuzioa();
			setLangileak();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void PuntuazioakIkusiGUIExekuzioa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		scrollPaneBalorazioak = new JScrollPane();
		
		scrollPaneBalorazioak.setBounds(new Rectangle(138, 273, 406, 316));
		scrollPaneBalorazioak.setBounds(19, 60, 705, 316);
		getContentPane().add(scrollPaneBalorazioak);
		scrollPaneBalorazioak.setVisible(false);
		
		scrollPaneBalorazioak.setViewportView(tableBalorazioak);
		tableModelBalorazioak = new DefaultTableModel(null, columnNamesJarraitzaileak);
		
		tableBalorazioak.setModel(tableModelBalorazioak);
		
		
		
		tableBalorazioak.getColumnModel().getColumn(0).setPreferredWidth(125);
		tableBalorazioak.getColumnModel().getColumn(1).setPreferredWidth(125);
		tableBalorazioak.getColumnModel().getColumn(2).setPreferredWidth(125);
		tableBalorazioak.getColumnModel().getColumn(3).setPreferredWidth(125);
		tableBalorazioak.getColumnModel().getColumn(4).setPreferredWidth(10);
		tableBalorazioak.getColumnModel().getColumn(5).setPreferredWidth(10);
		tableBalorazioak.getColumnModel().getColumn(6).setPreferredWidth(10);
		tableBalorazioak.getColumnModel().getColumn(7).setPreferredWidth(10);
		tableBalorazioak.getColumnModel().getColumn(8).setPreferredWidth(10);
		tableBalorazioak.getColumnModel().getColumn(9).setPreferredWidth(100);

		
		scrollPaneBalorazioak.setVisible(true);
		tableModelBalorazioak.setDataVector(null, columnNamesJarraitzaileak);
		

		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		btnNewButton.setBounds(22, 430, 117, 29);
		getContentPane().add(btnNewButton);
		
	}
	public void setLangileak() {
		Vector<Object> row=null;
		BLFacade facade=MainGUI.getBusinessLogic();
		List<Langilea> list=facade.getLangileak();
		int [] balorazioak;
		
		for(Langilea l: list) {
			if(l.getBalorazioak().length>0) {
				row=new Vector<Object>();
				balorazioak=l.getBalorazioak();
				row.add(0, l.getIzena());
				row.add(1, l.getAbizena1());
				row.add(2, l.getAbizena2());
				row.add(3, l.getErabiltzaileIzena());
				row.add(4,balorazioak[4]);
				row.add(5,balorazioak[3]);
				row.add(6,balorazioak[2]);
				row.add(7,balorazioak[1]);
				row.add(8,balorazioak[0]);
				row.add(9, l.batezBestekoa());
				tableModelBalorazioak.addRow(row);
			}else {
				row=new Vector<Object>();
				balorazioak=l.getBalorazioak();
				row.add(0, l.getIzena());
				row.add(1, l.getAbizena1());
				row.add(2, l.getAbizena2());
				row.add(3, l.getErabiltzaileIzena());
				row.add(4,0);
				row.add(5,0);
				row.add(6,0);
				row.add(7,0);
				row.add(8,0);
				row.add(9, 0);
				tableModelBalorazioak.addRow(row);
				
			}
			
		}
	}
	
	private void back() {
		this.setVisible(false);
		aurrekoa.setVisible(true);
	}

}
