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
	private Integer zeitId = 0;
	private Integer verkauft = 0;
	private Double	umsatz = 0.;
	
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

	public Double getUmsatz() {
		return umsatz;
	}

	public void setUmsatz(Double preis) {
		this.umsatz = preis;
	}

	public void setArtikel(String string) throws SQLException {
		
		this.setArtikel(string);
		
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
			this.artikelId = rs.getInt("a.id");
			rs.close();
		}
		pstmt.close();
		
	}

	public void setShop(String string) throws SQLException {
		
		this.setShop(string);
		
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
			this.shopId = rs.getInt("s.id");
			rs.close();
		}
		pstmt.close();
	}

	public void setDatum(String string) throws SQLException {
		
		this.setDatum(string);
		
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT z.id FROM aufg7_zeit z WHERE z.datum = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setString(1, string);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.zeitId = rs.getInt("z.id");
			rs.close();
		}
		pstmt.close();	
	}

	public void save() throws SQLException {

		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Achtung, hier wird noch ein Parameter mitgegeben,
		// damit später generierte IDs zurückgeliefert werden!
		String insertSQL = "INSERT INTO aufg7_faktentabelle (shopId,artikelId,zeitId,verkauft,umsatz) VALUES (?,?,?,?,?)";

		PreparedStatement pstmt = con.prepareStatement(insertSQL,
				Statement.RETURN_GENERATED_KEYS);

		// Setze Anfrageparameter und führe Anfrage aus
		pstmt.setInt(1, this.getShopId());
		pstmt.setInt(2, this.getArtikelId());
		pstmt.setInt(3, this.getZeitId());
		pstmt.setInt(4, this.getVerkauft());
		pstmt.setDouble(5, this.getUmsatz());

		pstmt.executeUpdate();

		// Hole die Id des engefügten Datensatzes
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next())	{	this.setIid(rs.getInt(1));	}

		rs.close();
		pstmt.close();
	}
}
