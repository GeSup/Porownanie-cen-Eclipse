package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProduktyTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private List<Produkt> produkty = null;
	private String[] kolumny = { "Nr", "nazwa produktu", "cena", "waluta", "zdjêcie", "adres produktu" };

	public ProduktyTableModel() {

	}

	public ProduktyTableModel(ArrayList<Produkt> produkty) {
		super();
		this.produkty = produkty;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return kolumny.length;
	}

	@Override
	public int getRowCount() {
		if(produkty == null)return 0;
		// TODO Auto-generated method stub
		return produkty.size();
	}

	public String getColumnName(int kol) {
		return kolumny[kol];
	}

	@Override
	public Object getValueAt(int wiersz, int kol) {
		// TODO Auto-generated method stub
		Produkt produkt = (Produkt) this.produkty.get(wiersz);
		switch (kol) {
		case 0:
			return wiersz + 1;
		case 1:
			return produkt.getNazwa();
		case 2:
			return produkt.getCena();
		case 3:
			return produkt.getWaluta();
		case 4:
			return produkt.getObraz();
		case 5:
			return produkt.getUrl();

		}
		return null;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public Produkt getProdukt(int pos){
		return (Produkt)produkty.get(pos);
	}

	
}
