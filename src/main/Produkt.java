package main;

public class Produkt implements Comparable<Produkt> {
	
	private String url;
	private String obraz;
	private String nazwa;
	private Double cena;
	private String waluta;
	
	public Produkt(String url, String obraz, String nazwa, Double cena, String waluta){
		setUrl(url);
		setObraz(obraz);
		setNazwa(nazwa);
		setCena(cena);
		setWaluta(waluta);
	}
	
	
	@Override
	public int compareTo(Produkt arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null)
			return 1;
		return this.cena.compareTo(arg0.cena);
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public String getObraz(){
		return this.obraz;
	}
	
	public String getNazwa(){
		return this.nazwa;
	}
	
	public Double getCena(){
		return this.cena;
	}
	
	public String getWaluta(){
		return this.waluta;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setObraz(String obraz){
		this.obraz = obraz;
	}
	
	public void setNazwa(String nazwa){
		this.nazwa = nazwa;
	}
	
	public void setCena(Double cena){
		this.cena = cena;
	}
	
	public void setWaluta(String waluta){
		this.waluta = waluta;
	}

}
