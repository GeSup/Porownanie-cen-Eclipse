package main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JTable;

public class Main {

	private JFrame frame;
	private String szukana;
	private JTextField txtrWyszukanie = new JTextField();
	private JTable table;
	private ArrayList<Produkt> produkty = new ArrayList<Produkt>();
	private ProduktyTableModel tableModel;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(250, 145));
		scrollPane.setMinimumSize(new Dimension(10, 10));
		tabbedPane.addTab("Wyniki", null, scrollPane, null);

		table = new JTable(tableModel = new ProduktyTableModel()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				int realColumnIndex = convertColumnIndexToModel(colIndex);
				switch (realColumnIndex) {
				case 1:
					tip = getValueAt(rowIndex, colIndex).toString();
					break;
				case 4:
					tip = getValueAt(rowIndex, colIndex).toString();
					break;
				case 5:
					tip = "<html>Kliknij dwa razy aby przejœæ na stronê produktu: <br />"
							+ getValueAt(rowIndex, colIndex).toString() + "</html>";
					break;
				}
				return tip;
			}

		};

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					int col = table.getSelectedColumn();
					if (col == 5) {
						try {
							URI url = new URI(tableModel.getValueAt(row, col).toString());
							openLink(url);
						} catch (URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		setColumnWidth(10, 200, 10, 10, 100, 200);

		scrollPane.setViewportView(table);

		txtrWyszukanie.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					wyswietlanie();

				}
			}
		});

		JButton btnWyszukaj = new JButton("wyszukaj");
		btnWyszukaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wyswietlanie();

			}
		});

		lblStatus = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(41)
								.addComponent(txtrWyszukanie, GroupLayout.PREFERRED_SIZE, 189,
										GroupLayout.PREFERRED_SIZE)
								.addGap(26).addComponent(btnWyszukaj).addGap(44)
								.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(btnWyszukaj)
								.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtrWyszukanie, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
				.addContainerGap()));
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 564,
										Short.MAX_VALUE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE).addContainerGap()));
		frame.getContentPane().setLayout(groupLayout);
	}

	private void wyswietlanie() {
		this.produkty.clear();
		szukana = txtrWyszukanie.getText();
		Skrypt skrypt = new Skrypt(szukana);
		lblStatus.setText(skrypt.getZrodlo());
		this.produkty = skrypt.getProdukty();
		table.setModel(tableModel = new ProduktyTableModel(produkty));
		setColumnWidth(10, 200, 10, 10, 100, 200);
		tableModel.fireTableDataChanged();

	}

	private static void openLink(URI url) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}

	private void setColumnWidth(int col1, int col2, int col3, int col4, int col5, int col6) {
		table.getColumnModel().getColumn(0).setPreferredWidth(col1);
		table.getColumnModel().getColumn(1).setPreferredWidth(col2);
		table.getColumnModel().getColumn(2).setPreferredWidth(col3);
		table.getColumnModel().getColumn(3).setPreferredWidth(col4);
		table.getColumnModel().getColumn(4).setPreferredWidth(col5);
		table.getColumnModel().getColumn(5).setPreferredWidth(col6);
	}
}
