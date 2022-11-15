package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Bezeroa;

public class BezeroaGUI extends JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = 6458065498915389023L;
	private JPanel contentPane;
	private MainGUI lehengoMain;
	private Bezeroa bezeroa;

	public BezeroaGUI(Bezeroa bezeroa, MainGUI lehengoMain) {
		try {
			this.lehengoMain = lehengoMain;
			this.bezeroa=bezeroa;
			bezeroaGUIExekuzioa();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bezeroaGUIExekuzioa() throws Exception {
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Customer"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Welcome")+" "+bezeroa.getErabiltzaileIzena()+"!"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(123, 0, 250, 29);
		contentPane.add(lblNewLabel);

		JButton SignOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignOut")); //$NON-NLS-1$ //$NON-NLS-2$
		SignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saioaItxi();
			}
		});
		SignOut.setBounds(16, 427, 140, 23);
		contentPane.add(SignOut);
		
		JButton btnMakeABet = new JButton();
		btnMakeABet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiApustuaEgin();
			}
		});
		btnMakeABet.setText(ResourceBundle.getBundle("Etiquetas").getString("btnMakeABet")); //$NON-NLS-1$ //$NON-NLS-2$
		btnMakeABet.setBounds(16, 25, 408, 40);
		contentPane.add(btnMakeABet);
		
		JButton btnDeleteABet = new JButton();
		btnDeleteABet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiApostuaEzabatu();
			}
		});
		btnDeleteABet.setText(ResourceBundle.getBundle("Etiquetas").getString("btnDeleteABet")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeleteABet.setBounds(16, 64, 408, 40);
		contentPane.add(btnDeleteABet);
		
		JButton btnDirua = new JButton();
		btnDirua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiDiruaSartu();
			}
		});
		btnDirua.setText(ResourceBundle.getBundle("Etiquetas").getString("btnDirua")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDirua.setBounds(16, 103, 408, 40);
		contentPane.add(btnDirua);
		
		JButton btnDiruMugimenduakIkusi = new JButton();
		btnDiruMugimenduakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiMugimenduakIkusi();
			}
		});
		btnDiruMugimenduakIkusi.setText(ResourceBundle.getBundle("Etiquetas").getString("btnDiruMugimenduakIkusi")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDiruMugimenduakIkusi.setBounds(16, 142, 408, 40);
		contentPane.add(btnDiruMugimenduakIkusi);
		
		JButton btnQueryQuestions = new JButton();
		btnQueryQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		btnQueryQuestions.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries")); //$NON-NLS-1$ //$NON-NLS-2$
		btnQueryQuestions.setBounds(16, 181, 408, 40);
		contentPane.add(btnQueryQuestions);
		
		JButton requestRepeat = new JButton();
		requestRepeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiErrepikatzekoEskaeraEgin();
			}
		});
		requestRepeat.setText(ResourceBundle.getBundle("Etiquetas").getString("RequestToRepeat")); //$NON-NLS-1$ //$NON-NLS-2$
		requestRepeat.setBounds(16, 220, 408, 40);
		contentPane.add(requestRepeat);
		
		JButton postontzia = new JButton();
		postontzia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiPostontzia();
			}
		});
		postontzia.setText(ResourceBundle.getBundle("Etiquetas").getString("Mailbox")); //$NON-NLS-1$ //$NON-NLS-2$
		postontzia.setBounds(16, 298, 408, 40);
		contentPane.add(postontzia);
		
		JButton ezarpenak = new JButton();
		ezarpenak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiEzarpenak();
			}
		});
		ezarpenak.setText(ResourceBundle.getBundle("Etiquetas").getString("Settings")); //$NON-NLS-1$ //$NON-NLS-2$
		ezarpenak.setBounds(16, 337, 408, 40);
		contentPane.add(ezarpenak);
		
		JButton arretaZerbitzua = new JButton();
		arretaZerbitzua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiArretaZerbitzua();
			}
		});
		arretaZerbitzua.setText(ResourceBundle.getBundle("Etiquetas").getString("CustomerService")); //$NON-NLS-1$ //$NON-NLS-2$
		arretaZerbitzua.setBounds(16, 376, 408, 40);
		contentPane.add(arretaZerbitzua);
		
		JButton jarraitzaileak = new JButton();
		jarraitzaileak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				irekiErrepikapena();
			}
		});
		jarraitzaileak.setText(ResourceBundle.getBundle("Etiquetas").getString("CheckFollowers")); //$NON-NLS-1$ //$NON-NLS-2$
		jarraitzaileak.setBounds(16, 259, 408, 40);
		contentPane.add(jarraitzaileak);
	}

	/**
	 * Metodo honen bidez, saioa itxi eta hasierako menura joaten gara
	 */
	public void saioaItxi() {
		this.setVisible(false);
		this.lehengoMain.setVisible(true);
	}
	
	public void irekiApustuaEgin() {
		this.setVisible(false);
		JFrame a = new ApustuaEginGUI(this, bezeroa);
		a.setVisible(true);
	}
	
	public void irekiApostuaEzabatu() {
		this.setVisible(false);
		JFrame a = new ApostuaEzabatuGUI(this, bezeroa);
		a.setVisible(true);
	}
	
	public void irekiDiruaSartu() {
		this.setVisible(false);
		JFrame a = new DiruaSartuGUI(bezeroa, this);
		a.setVisible(true);
	}
	
	public void irekiMugimenduakIkusi() {
		this.setVisible(false);
		JFrame a = new MugimenduakIkusiGUI(bezeroa, this);
		a.setVisible(true);
	}
	
	public void irekiErrepikatzekoEskaeraEgin() {
		this.setVisible(false);
		JFrame a = new ErrepikatzekoEskaeraEginGUI(bezeroa, this);
		a.setVisible(true);
	}
	
	public void irekiPostontzia() {
		this.setVisible(false);
		JFrame a = new PostontziaGUI(this, bezeroa);
		a.setVisible(true);
	}
	
	public void irekiEzarpenak() {
		this.setVisible(false);
		JFrame a = new EzarpenakAldatuGUI(bezeroa, this);
		a.setVisible(true);
	}

	public void irekiArretaZerbitzua() {
		this.setVisible(false);
		JFrame a = new ArretaZerbitzuaEskatuGUI(bezeroa, this);
		a.setVisible(true);
	}
	
	public void irekiErrepikapena() {
	 	 this.setVisible(false);
	 	 JFrame a = new ErrepikatzaileakGUI(bezeroa, this);
	 	 a.setVisible(true);
	}
	
	public void setBezeroa(Bezeroa b) {
		this.bezeroa=b;
	}
}
