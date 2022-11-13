package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import domain.Admin;
import domain.Bezeroa;
import domain.UserAdapter;

import javax.swing.JTable;

public class AdapterProbaGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AdapterProbaGUI(Bezeroa bezeroa) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		AbstractTableModel adapterTable = new UserAdapter(bezeroa);
		System.out.println(adapterTable.getRowCount());
		table = new JTable();
		table.setBounds(10, 11, 416, 241);
		contentPane.add(table);
	}
}

