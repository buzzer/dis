package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dis2010.DB2ConnectionManager;

public class Shop {
	// Attribute
	private Integer id = -1;
	private String name = "";
	private String land = "";
	private String region = "";
	private String stadt = "";
	
	public Shop() {
		// dummy
	}

	public Integer getIid() {
		return id;
	}

	public void setIid(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(Integer stadtId) throws SQLException {
				
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
	
		// Erzeuge Anfrage
		String selectSQL = "SELECT s.name,s.regionid FROM db2inst1.stadtid s WHERE s.stadtid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, stadtId);
	
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.stadt = rs.getString("name");
			this.setRegion(rs.getInt("regionid"));
			rs.close();
		}
		pstmt.close();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(Integer regionId) throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT r.name,r.landid FROM db2inst1.regionid r WHERE r.regionid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, regionId);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.region = rs.getString("name");
			this.setLand(rs.getInt("landid"));
			rs.close();
		}
		pstmt.close();
	}

	public String getLand() {
		return land;
	}

	public void setLand(Integer landId) throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
	
		// Erzeuge Anfrage
		String selectSQL = "SELECT l.name FROM db2inst1.landid l WHERE l.landid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, landId);
	
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.land = rs.getString("name");
			rs.close();
		}
		pstmt.close();
	}

	public void save() throws SQLException {
	
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String selectSQL = "SELECT s.id FROM aufg7_shop s WHERE s.id = ?";
		PreparedStatement pstmt1 = con.prepareStatement(selectSQL);
		pstmt1.setInt(1, this.id);
	
		// Führe Anfrage aus
		ResultSet rs1 = pstmt1.executeQuery();
		
		// Insert Date only if not alread present
		if (! rs1.next())
		{
			rs1.close();
			pstmt1.close();
		
			// Hole Verbindung
			//Connection con = DB2ConnectionManager.getInstance().getConnection();
			
			// Erzeuge Anfrage
			String insertSQL = "insert into aufg7_shop (id,name,land,region,stadt) values (?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(insertSQL);
				
			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setInt(1, this.getIid());
			pstmt.setString(2, this.getName());
			pstmt.setString(3, this.getLand());
			pstmt.setString(4, this.getRegion());
			pstmt.setString(5, this.getStadt());
	
			pstmt.executeUpdate();
	
			pstmt.close();
		} else {
			this.id = rs1.getInt("id");
			System.out.printf("Omit dublicate shop:\t| %4d | %30s | %15s | %22s | %20s |%n",
				this.id,
				this.name,
				this.stadt,
				this.region,
				this.land);
			rs1.close();
			pstmt1.close();
		}
		
	}

}
