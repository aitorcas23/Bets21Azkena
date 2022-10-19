package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.BLFacade;
import domain.Bezeroa;
import domain.Mugimendua;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class MugimenduakIkusiGUI extends JFrame {
	
	private Bezeroa bezeroa;
	private BezeroaGUI aurrekoa;
	private static final long serialVersionUID = 1L;
	private double jokatua;
	private double irabazia;
	private double etekina;
	private JLabel jLabelJokatua;
	private JLabel jLabelIrabazia;
	private JLabel jLabelEtekina;
	private JLabel jLabelSaldoa;

	private JPanel contentPane;
	
	public MugimenduakIkusiGUI(Bezeroa bezeroa, BezeroaGUI aurrekoa) {
		try {
			this.aurrekoa=aurrekoa;
			this.bezeroa=bezeroa;
			jokatua=0;
			irabazia=0;
			etekina=0;
			mugimenduakIkusiGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	private void mugimenduakIkusiGUIExekuzioa() {
		
		BLFacade facade = MainGUI.getBusinessLogic();
		bezeroa=facade.getBezeroa(bezeroa.getErabiltzaileIzena());
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("SeeMovesGUI")); //$NON-NLS-1$ //$NON-NLS-2$
		setBounds(100, 100, 850, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblMugimenduak = new JLabel();
		lblMugimenduak.setText(ResourceBundle.getBundle("Etiquetas").getString("lblMugimenduak")); //$NON-NLS-1$ //$NON-NLS-2$
		lblMugimenduak.setBounds(10, 27, 250, 29);
		contentPane.add(lblMugimenduak);
		
		JScrollPane scrollPane = new JScrollPane();
		
		DefaultListModel<String> lista = new DefaultListModel<String>();
		
		Vector<Mugimendua> bezeroMugimenduak = bezeroa.getMugimenduak(); 
		if (bezeroMugimenduak.isEmpty()) {
			lista.addElement(ResourceBundle.getBundle("Etiquetas").getString("NoMoves"));
		}else {
			for(Mugimendua mugimendua : bezeroa.getMugimenduak()) {
				lista.addElement(mugimendua.toString());
				if(mugimendua.getMota().equals("irabazi")) {
					irabazia+=mugimendua.getAldaketa();
				}else if(mugimendua.getMota().equals("bueltatu")) {
					jokatua-=mugimendua.getAldaketa();
				}else if(mugimendua.getMota().equals("jokatu")){
					jokatua-=mugimendua.getAldaketa();
				}
			}
			etekina=irabazia-jokatua;
		}
		
		scrollPane.setBounds(10, 62, 814, 192);
		contentPane.add(scrollPane);
		
		JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setModel(lista);
		
		if(!bezeroMugimenduak.isEmpty()) {
			list.setCellRenderer( new listaBackground() );
		}	
		
		JButton JButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		JButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atzera();
			}
		});
		JButtonClose.setBounds(654, 353, 89, 23);
		contentPane.add(JButtonClose);
		
		JButton bntEtekina = new JButton(ResourceBundle.getBundle("Etiquetas").getString("bntEtekina")); //$NON-NLS-1$ //$NON-NLS-2$
		bntEtekina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelJokatua.setText(ResourceBundle.getBundle("Etiquetas").getString("Play")+": "+jokatua);
				jLabelIrabazia.setText(ResourceBundle.getBundle("Etiquetas").getString("Win")+": "+irabazia);
				if(etekina<0) {
					jLabelEtekina.setForeground(Color.RED);
				}else {
					jLabelEtekina.setForeground(Color.GREEN);
				}
				jLabelEtekina.setText(ResourceBundle.getBundle("Etiquetas").getString("Yield")+": "+etekina);
				jLabelSaldoa.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrentBalance")+": "+bezeroa.getDirua());
			}
		});
		bntEtekina.setBounds(318, 265, 179, 23);
		contentPane.add(bntEtekina);
		
		jLabelJokatua = new JLabel("\r\n");
		jLabelJokatua.setBounds(173, 295, 181, 23);
		contentPane.add(jLabelJokatua);
		
		jLabelIrabazia = new JLabel("\r\n");
		jLabelIrabazia.setBounds(513, 295, 179, 23);
		contentPane.add(jLabelIrabazia);
		
		jLabelEtekina = new JLabel("\r\n");
		jLabelEtekina.setBounds(339, 321, 179, 23);
		contentPane.add(jLabelEtekina);
		
		jLabelSaldoa = new JLabel("\r\n");
		jLabelSaldoa.setBounds(222, 353, 179, 23);
		contentPane.add(jLabelSaldoa);
		
		

	}
	
	private static class listaBackground extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
            String[] parts = value.toString().split(" ");
            if ( Double.valueOf(parts[parts.length-2]) >= 0 ) {
                c.setBackground( Color.gray );
                c.setForeground(Color.green);
            }
            else {
                c.setBackground( Color.white );
                c.setForeground(Color.red);
            }
            return c;
        }
	}
	
	public void atzera() {
		aurrekoa.setBezeroa(bezeroa);
		this.setVisible(false);
		aurrekoa.setVisible(true);
	}
}
