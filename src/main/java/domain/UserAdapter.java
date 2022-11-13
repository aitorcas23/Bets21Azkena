package domain;

import javax.swing.table.AbstractTableModel;

import dataAccess.DataAccess;

public class UserAdapter extends AbstractTableModel {
	private Bezeroa bezeroa;
	
	public UserAdapter(Bezeroa bez) {
		this.bezeroa = bez;
	}
	@Override
	public int getRowCount() {
		return bezeroa.getApustuak().size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.getValueAt(rowIndex, columnIndex);
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex==0) {
			return "Event";
		}
		else if(columnIndex==1) {
			return "Question";
		}
		else if(columnIndex==2) {
			return "Event Date";
		}
		else {
			return "Bet(€)";
		}
	}

}
