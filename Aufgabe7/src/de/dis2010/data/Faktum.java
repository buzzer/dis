package de.dis2010.data;

public class Faktum {
	// Attribute
	private Integer id = -1;
	private Integer shopId = 0;
	private Integer artikelId = 0;
	private Integer zeitId = 0;
	private Integer verkauft = 0;
	private Double	preis = 0.;
	
	public Faktum() {
		// dummy
	}

	public Integer getIid() {
		return id;
	}

	public void setIid(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getArtikelId() {
		return artikelId;
	}

	public void setArtikelId(Integer artikelId) {
		this.artikelId = artikelId;
	}

	public Integer getZeitId() {
		return zeitId;
	}

	public void setZeitId(Integer zeitId) {
		this.zeitId = zeitId;
	}

	public Integer getVerkauft() {
		return verkauft;
	}

	public void setVerkauft(Integer verkauft) {
		this.verkauft = verkauft;
	}

	public Double getPreis() {
		return preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}


}
