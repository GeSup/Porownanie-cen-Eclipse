package main;

import java.util.ArrayList;
import java.util.Collections;

public class Skrypt {

	private String zrodlo = new String();
	private ArrayList<Produkt> produkty = new ArrayList<Produkt>();
	private SklepCandA sklepCandA;
	private SklepHeppin sklepHeppin;

	public Skrypt() {

	}

	public Skrypt(String wyszukana) {

		sklepCandA = new SklepCandA();
		sklepCandA.napelnij(wyszukana);
		if (sklepCandA.getTmp().equals("Brak")) {
			zrodlo = zrodlo + "Brak produktu w sklepie C and A. ";
		}
		produkty.addAll(sklepCandA.getProduktyCandA());

		sklepHeppin = new SklepHeppin();
		sklepHeppin.napelnij(wyszukana);
		if (sklepHeppin.getTmp().equals("Brak")) {
			zrodlo = zrodlo + "Brak produktu w sklepie Heppin. Podano inne propozycje: ";
		}
		produkty.addAll(sklepHeppin.getProduktyHeppin());

		Collections.sort(produkty);

	}

	public String getZrodlo() {
		return zrodlo;
	}

	public ArrayList<Produkt> getProdukty() {
		return this.produkty;
	}

	// usuwanie ka¿dego punktu
	public String delete(String podstawa, String punkt) {
		int posP, posK;
		for (posP = podstawa.indexOf(punkt); posP != -1;) {
			posK = posP + punkt.length();
			podstawa = podstawa.substring(0, posP) + podstawa.substring(posK, podstawa.length());
		}
		return podstawa;
	}

	// usuwanie do punktu
	public String deleteTo(String podstawa, String punkt) {
		int pos;
		pos = podstawa.indexOf(punkt);
		if (pos != -1) {
			podstawa = podstawa.substring(pos);

		} else {
			podstawa = "Brak szukanego produktu.";
			System.out.println("Brak szukanego Stringa.");
		}
		return podstawa;
	}

	// usuwanie 0 = od pocz¹tku do punktu A i od punktu B do koñca, 1 = usuwanie
	// od punktu A do punktu B
	public String deleteAToB(String podstawa, String punktA, String punktB, int zmiana) {
		int posA, posB;
		posA = podstawa.indexOf(punktA);
		posB = podstawa.indexOf(punktB, posA) + punktB.length();
		if ((posA != -1) && (posB != -1)) {
			switch (zmiana) {
			case 0:
				podstawa = podstawa.substring(posA, posB);
				break;
			case 1:
				podstawa = podstawa.substring(0, posA) + podstawa.substring(posB, podstawa.length());
				break;
			default:
				podstawa = "B³¹d programisty.";
				System.out.println("nie ma takiego prze³¹ncznika, podaj 1 lub 0");
				break;
			}
		} else {
			if (posA == -1) {
				podstawa = "Brak";
				System.out.println("brak posA ");
			}
			if (posB == -1) {
				podstawa = "Brak";
				System.out.println("brak posB ");
			}
		}
		return podstawa;
	}

}
