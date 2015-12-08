package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;

import javax.swing.SwingConstants;

public class Calendario extends JPanel {
	static JLabel lblMonth, lblYear;
	static JButton btnPrev, btnNext;
	static JTable tblCalendar;
	static JComboBox cmbYear;
	static DefaultTableModel mtblCalendar; // Table model
	static JScrollPane stblCalendar; // The scrollpane
	static int realYear, realMonth, realDay, currentYear, currentMonth;
	static int desativada = 4;
	static ArrayList<Integer> desativadas = new ArrayList<Integer>();

	public Calendario() {
		super(null);
		setBackground(Color.WHITE);
		lblYear = new JLabel("Ano:");
		cmbYear = new JComboBox();
		btnPrev = new JButton("");
		btnNext = new JButton("&gt;&gt;");
		mtblCalendar = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);

		// Set border
		this.setBorder(null);

		// Register action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());

		this.add(lblYear);
		this.add(cmbYear);
		this.add(btnPrev);
		this.add(btnNext);
		this.add(stblCalendar);

		// Set bounds
		this.setBounds(0, 0, 399, 376);
		lblYear.setBounds(10, 326, 80, 20);
		cmbYear.setBounds(50, 326, 80, 20);
		btnPrev.setBounds(0, 25, 50, 25);
		btnNext.setBounds(320, 25, 50, 25);
		stblCalendar.setBounds(0, 65, 370, 250);

		// Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
		realMonth = cal.get(GregorianCalendar.MONTH); // Get month
		realYear = cal.get(GregorianCalendar.YEAR); // Get year
		currentMonth = realMonth; // Match month and year
		currentYear = realYear;

		// Add headers
		String[] headers = { "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" }; // All
																				// headers
		for (int i = 0; i < 7; i++) {
			mtblCalendar.addColumn(headers[i]);
		}

		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); // Set
																			// background

		// No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		// Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblCalendar.setCellSelectionEnabled(true);

		// Set row/column count
		tblCalendar.setRowHeight(38);
		// preparar conrtoles
		lblMonth = new JLabel("January");
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonth.setBounds(147, 24, 73, 25);
		add(lblMonth);
		lblMonth.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 11));
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);

		// Populate table
		for (int i = realYear - 100; i <= realYear + 100; i++) {
			cmbYear.addItem(String.valueOf(i));
		}

		// Refresh calendar
		refreshCalendar(realMonth, realYear); // Refresh calendar

	}

	public JTable getCalendarTable() {
		return tblCalendar;
	}

	public static void refreshCalendar(int month, int year) {
		// Variaveis
		String[] months = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio",
				"Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro",
				"Dezembro" };
		int nod, som; // numero de dias, inicio do mes

		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear - 10) {
			btnPrev.setEnabled(false);
		} // Too early
		if (month == 11 && year >= realYear + 100) {
			btnNext.setEnabled(false);
		} // Too late
		lblMonth.setText(months[month]);
		cmbYear.setSelectedItem(String.valueOf(year)); // Select the correct
														// year in the combo box

		// Clear table
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				mtblCalendar.setValueAt(null, i, j);
			}
		}

		// Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Draw calendar
		for (int i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			mtblCalendar.setValueAt(i, row, column);
		}

		// Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0),
				new tblCalendarRenderer());

	}

	// inner classes
	static class tblCalendarRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean selected, boolean focused, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, selected,
					focused, row, column);

			// definir cores de selecionadas
			if (table.isCellSelected(row, column))
				setForeground(Color.red);
			else if (!table.isCellSelected(row, column)) { // od outros dias
				setBackground(new Color(255, 255, 255));
			}

			// definir cores de desativadas
			for (int i = 0; i < desativadas.size(); i++) {
				if (column == desativadas.get(i)) { // desatibvadas
					setBackground(new Color(255, 220, 220));
				}
			}

			if (value != null) {
				if ((Integer.parseInt(value.toString()) == realDay
						&& currentMonth == realMonth && currentYear == realYear)
						&& (!table.isCellSelected(row, column))) { // Today
					setBackground(new Color(220, 220, 255));
				}
			}

			setHorizontalAlignment(SwingConstants.CENTER);
			setBorder(null);
			setForeground(Color.black);
			return this;
		}

	}

	static class btnPrev_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 0) { // Back one year
				currentMonth = 11;
				currentYear -= 1;
			} else { // Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	static class btnNext_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (currentMonth == 11) { // Foward one year
				currentMonth = 0;
				currentYear += 1;
			} else { // Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}

	static class cmbYear_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (cmbYear.getSelectedItem() != null) {
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}

	public void desativar(int dia) {
		desativadas.add(dia);
		mtblCalendar.fireTableDataChanged();
	}

	public void ativarTudo() {
		desativadas.clear();
	}

	public void ativar(int dia) {
		if (desativadas.contains(dia)) {
			desativadas.remove(dia);
		}
	}

	public int getMes() {
		return currentMonth;
	}

	public int getAno() {
		return currentYear;
	}

	public boolean isIndisponivel(int c) {
		for (int i = 0; i < desativadas.size(); i++) {
			if (desativadas.get(i).equals(c))
				return true;
		}
		return false;
	}
}
