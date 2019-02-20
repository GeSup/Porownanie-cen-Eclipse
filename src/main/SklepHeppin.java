package main;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SklepHeppin {

	private String url;
	private String tmp = new String();
	private String wszystkie = new String();
	private List<Produkt> produktyHeppin = new LinkedList<Produkt>();
	private Produkt heppin;
	private Document doc;
	private Elements wyniki;
	private Element nast;
	private Skrypt edytor = new Skrypt();

	public SklepHeppin() {

	}

	public void napelnij(String wyszukana) {
		
		try {
			url = new String(
					"http://heppin.com/_k-0.htm?str=katTEST&kat_id=0&filter=ShowAll--true&searchQuery2=" + wyszukana);
			do {

				doc = Jsoup.connect(url).get();
				tmp = doc.html();

				wszystkie = wszystkie + tmp;
				nast = doc.select(".pag-1").first();// nastêpne strony

				if (nast != null) {
					url = "http://heppin.com/" + nast.attr("href");
				} else {
					url = "";
				}

			} while (!url.equals(""));
		}catch (SocketTimeoutException e) {
			tmp = "Brak";
            e.printStackTrace();

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (wszystkie.indexOf("<del><span") != -1) {
			wszystkie = edytor.deleteAToB(wszystkie, "<del><span", "</span></del>", 1);
		}
		
		
		doc = Jsoup.parse(wszystkie);
		wyniki = doc.select(".product-name a, .main-img, .product-price");
		for (int i = 0; i < wyniki.size(); i = i + 3) {
			heppin = new Produkt("http://heppin.com/" + wyniki.get(i + 1).attr("href"),
					"<html>" + wyniki.get(i).html() + "</html>", wyniki.get(i + 1).text(),
					Double.parseDouble(wyniki.get(i + 2).text().replace(',', '.')), "pln");
			produktyHeppin.add(heppin);
		}

	}

	public String getTmp() {
		return this.tmp;
	}

	public List<Produkt> getProduktyHeppin() {
		return this.produktyHeppin;
	}

}
