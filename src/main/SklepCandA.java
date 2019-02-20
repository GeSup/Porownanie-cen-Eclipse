package main;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SklepCandA {

	private String url;
	private String tmp = new String();
	private String wszystkie = new String();
	private List<Produkt> produktyCandA = new LinkedList<Produkt>();
	private Produkt cAndA;
	private Document doc;
	private Elements wyniki;
	private Element nast;
	private Skrypt edytor = new Skrypt();

	public SklepCandA() {

	}

	public void napelnij(String wyszukana) {

		try {
			url = new String(
					"http://www.c-and-a.com/webapp/wcs/stores/servlet/SearchDisplay?catalogId=10001&storeId=10158&langId=-22&searchTerm="
							+ wyszukana);

			do {
				doc = Jsoup.connect(url).get();
				tmp = doc.html();
				tmp = edytor.deleteAToB(tmp, "productlistDiv", "pagination", 0);
				wszystkie = wszystkie + tmp;
				nast = doc.select(".pagination .right").first();// nastêpne strony
				if (nast != null) {
					url = nast.attr("href");
				} else {
					url = "";
				}
			} while (!url.equals(""));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (wszystkie.indexOf("price priceOld") != -1) {
			wszystkie = edytor.deleteAToB(wszystkie, "price priceOld", "</span>", 1);
		}
		doc = Jsoup.parse(wszystkie);
		wyniki = doc.select("a,.top-rounded-product-image, .subnameline, .nameline, .priceline");
		
		
		for (int i = 0; i < wyniki.size(); i = i + 6) {
			
			cAndA = new Produkt(wyniki.get(0).attr("href"), "<html>"+ wyniki.get(i + 1).html()+"</html>",

					wyniki.get(i + 3).text() + " " + wyniki.get(i + 4).text(),
					Double.parseDouble(wyniki.get(i + 5).text().substring(0, wyniki.get(i + 5).text().indexOf(' '))
							.replace(',', '.')),
					wyniki.get(i + 5).text().substring(wyniki.get(i + 5).text().indexOf(' ') + 1));
			produktyCandA.add(cAndA);
		}
	}

	public List<Produkt> getProduktyCandA() {
		return this.produktyCandA;
	}
	
	public String getTmp(){
		return this.tmp;
	}

}
