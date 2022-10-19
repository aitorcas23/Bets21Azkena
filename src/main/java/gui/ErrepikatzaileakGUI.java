package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businesslogic.BLFacade;
import domain.Bezeroa;
import domain.ErrepikatuakContainer;
import java.awt.event.MouseAdapter;

public class ErrepikatzaileakGUI extends JFrame {


	BezeroaGUI aurrekoa;
	private Bezeroa bezeroa; 
	private List<ErrepikatuakContainer> errepikatzaileak;
	private List<ErrepikatuakContainer> errepikapenak;
	
	private static final long serialVersionUID = 1L;

	private JLabel jlabelMsg;
	private JScrollPane scrollPaneJarraitzaileak = new JScrollPane();
	private JTable tableJarraitzaileak = new JTable();
	private DefaultTableModel tableModelJarraitzaileak;
	
	private JScrollPane scrollPaneJarraituak = new JScrollPane();
	private JTable tableJarraituak = new JTable();
	private DefaultTableModel tableModelJarraituak;

	private String[] columnNamesJarraitzaileak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("jarraitzaileak"), 
			"", 
	};
	private String[] columnNamesJarraituak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("jarraituak"), 
			"", 
	};

	
	public ErrepikatzaileakGUI(Bezeroa bezeroa, BezeroaGUI aurrekoa) {
		try {
			this.aurrekoa=aurrekoa;
			this.bezeroa=bezeroa;
			ErrepikapenaGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void ErrepikapenaGUIExekuzioa() {
		BLFacade facade = MainGUI.getBusinessLogic();
		bezeroa=facade.getBezeroa(bezeroa.getErabiltzaileIzena());

		this.getContentPane().setLayout(null);
		
		this.setSize(new Dimension(630, 470));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CheckFollowers"));
		
		jlabelMsg = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
		jlabelMsg.setBounds(43, 24, 493, 30);
		getContentPane().add(jlabelMsg);
		
		scrollPaneJarraitzaileak = new JScrollPane();
		
		scrollPaneJarraitzaileak.setBounds(new Rectangle(138, 273, 406, 116));
		scrollPaneJarraitzaileak.setBounds(19, 60, 585, 116);
		getContentPane().add(scrollPaneJarraitzaileak);
		scrollPaneJarraitzaileak.setVisible(false);
		
		scrollPaneJarraitzaileak.setViewportView(tableJarraitzaileak);
		tableModelJarraitzaileak = new DefaultTableModel(null, columnNamesJarraitzaileak);
		
		tableJarraitzaileak.setModel(tableModelJarraitzaileak);
		tableJarraitzaileak.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i=tableJarraitzaileak.rowAtPoint(e.getPoint());
				int j=tableJarraitzaileak.columnAtPoint(e.getPoint());
				eguneratu();
				if(j==1) {
					BLFacade facade=MainGUI.getBusinessLogic();
					facade.jarraitzeariUtzi(errepikatzaileak.get(i).getErrepikapena());
					eguneratu();
				}
				
			}
		});
		
		tableJarraitzaileak.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableJarraitzaileak.getColumnModel().getColumn(1).setPreferredWidth(120);

		
		scrollPaneJarraitzaileak.setVisible(true);
		tableModelJarraitzaileak.setDataVector(null, columnNamesJarraitzaileak);
		
		
		
		scrollPaneJarraituak = new JScrollPane();
		
		scrollPaneJarraituak.setBounds(new Rectangle(138, 435, 406, 116));
		scrollPaneJarraituak.setBounds(19, 220, 585, 116);
		getContentPane().add(scrollPaneJarraituak);
		scrollPaneJarraituak.setVisible(false);
		
		scrollPaneJarraituak.setViewportView(tableJarraituak);
		tableModelJarraituak = new DefaultTableModel(null, columnNamesJarraituak);
		
		tableJarraituak.setModel(tableModelJarraituak);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		btnNewButton.setBounds(22, 388, 117, 29);
		getContentPane().add(btnNewButton);
		tableJarraituak.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i=tableJarraituak.rowAtPoint(e.getPoint());
				int j=tableJarraituak.columnAtPoint(e.getPoint());
				if(j==1) {
					BLFacade facade=MainGUI.getBusinessLogic();					
					facade.jarraitzeariUtzi(errepikapenak.get(i).getErrepikapena());
					
				}
				eguneratu();
			}
		});
		
		tableJarraituak.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableJarraituak.getColumnModel().getColumn(1).setPreferredWidth(120);

		
		scrollPaneJarraituak.setVisible(true);
		tableModelJarraituak.setDataVector(null, columnNamesJarraituak);
		eguneratu();
		
		
	
		
	}
	
	private void back() {
		this.setVisible(false);
		BLFacade facade = MainGUI.getBusinessLogic();
		bezeroa=facade.getBezeroa(bezeroa.getErabiltzaileIzena());
		aurrekoa.setBezeroa(bezeroa);
		aurrekoa.setVisible(true);
	}
	private void eguneratu() {
		Vector<Object> row=null;
		BLFacade facade=MainGUI.getBusinessLogic();
		errepikatzaileak=facade.getErrepikatzaileak(bezeroa);
		int size=tableModelJarraitzaileak.getRowCount();
		if(size>0) {
			for(int i=0;i<size;i++) {
				tableModelJarraitzaileak.removeRow(0);
			}
		}	
		for(ErrepikatuakContainer e:errepikatzaileak) {
			row=new Vector<Object>();
			row.add(e.getErrepikapena().getNork());
			row.add(ResourceBundle.getBundle("Etiquetas").getString("JarraitzaileaEzabatu")); //$NON-NLS-1$ //$NON-NLS-2$
			tableModelJarraitzaileak.addRow(row);
		}
		
		
		errepikapenak=facade.getErrepikapenak(bezeroa);
		int size2=tableModelJarraituak.getRowCount();
		if(size2>0) {
			for(int i=0;i<size2;i++) {
				tableModelJarraituak.removeRow(0);
			}
		}	
		for(ErrepikatuakContainer e:errepikapenak) {
			row=new Vector<Object>();
			row.add(e.getErrepikapena().getNori());
			row.add(ResourceBundle.getBundle("Etiquetas").getString("JarraituaEzabatu")); //$NON-NLS-1$ //$NON-NLS-2$
			
			tableModelJarraituak.addRow(row);
		}
		
		
	}
}

