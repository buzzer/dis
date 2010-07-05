package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2010.DB2ConnectionManager;

public class Faktum {
	// Attribute
	private Integer id = -1;
	private Integer shopId = 0;
	private Integer artikelId = 0;
	private java.sql.Date zeitId = null;
	private Integer verkauft = 0;
	private Double	umsatz = 0.;
	
	private Shop shop = null;
	private Artikel artikel = null;
	private Zeit zeit = null;
	
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

	public String getZeitId() {
		return zeitId.toString();
	}

	public void setZeitId(String zeitId) {
		this.zeitId = java.sql.Date.valueOf(zeitId);
	}

	public Integer getVerkauft() {
		return verkauft;
	}

	public void setVerkauft(Integer verkauft) {
		this.verkauft = verkauft;
	}

	public Double getUmsatz() {
		return umsatz;
	}

	public void setUmsatz(Double preis) {
		this.umsatz = preis;
	}

	public void setArtikel(String string) throws SQLException {
		
		//this.setArtikel(string);
		
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT a.id FROM aufg7_artikel a WHERE a.name = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setString(1, string);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.artikelId = rs.getInt("id");
			rs.close();
		}
		pstmt.close();
		
	}

	public void setShop(String string) throws SQLException {
		
		//this.setShop(string);
		
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT s.id FROM aufg7_shop s WHERE s.name = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setString(1, string);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.shopId = rs.getInt("id");
			rs.close();
		}
		pstmt.close();
	}

	public void setDatum(String string) throws SQLException {
		
		this.zeitId = java.sql.Date.valueOf(string);
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	public Zeit getZeit() {
		return zeit;
	}

	public void setZeit(Zeit zeit) {
		this.zeit = zeit;
	}

	public void save() throws SQLException {

		// Hole Verbindung
		Connection con1 = DB2ConnectionManager.getInstance().getConnection();
		
		// Check for duplicate entry..
		//String selectSQL = "SELECT a.id FROM aufg7_artikel a WHERE a.id = ?";
		String selectSQL = "SELECT shopId,artikelId,zeitId FROM aufg7_faktentabelle WHERE shopId = ? and artikelId = ? and zeitId = ?";
		PreparedStatement pstmt1 = con1.prepareStatement(selectSQL);
		
		pstmt1.setInt(1, this.shopId);
		pstmt1.setInt(2, this.artikelId);
		pstmt1.setDate(3, this.zeitId);
	
		// Führe Anfrage aus
		ResultSet rs1 = pstmt1.executeQuery();
		
		// Insert Date only if not alread present
		if (! rs1.next())
		{
			rs1.close();
			pstmt1.close();
		
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO aufg7_faktentabelle (shopId,artikelId,zeitId,verkauft,umsatz) VALUES (?,?,?,?,?)";
	
			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);
		
			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setInt(1, this.shopId);
			pstmt.setInt(2, this.artikelId);
			pstmt.setDate(3, this.zeitId);
			pstmt.setInt(4, this.verkauft);
			pstmt.setDouble(5, this.umsatz);
	
			pstmt.executeUpdate();
	
			// Hole die Id des engefügten Datensatzes
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next())	{	this.setIid(rs.getInt(1));	}
	
			rs.close();
			pstmt.close();
		} else {
			System.out.println("Omit dublicate faktum");
			rs1.close();
			pstmt1.close();
		}
	}
}
