package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public void transfer() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String insertSQL = "insert into aufg7_shop (name,land,region,stadt) values (?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(insertSQL,
				Statement.RETURN_GENERATED_KEYS);

		// Setze Anfrageparameter und führe Anfrage aus
		pstmt.setString(1, this.getName());
		pstmt.setString(2, this.getLand());
		pstmt.setString(3, this.getRegion());
		pstmt.setString(4, this.getStadt());

		pstmt.executeUpdate();

		// Hole die Id des engefügten Datensatzes
		//ResultSet rs = pstmt.getGeneratedKeys();
		//if (rs.next())
		//{
			//this.setIid((rs.getInt(1));
		//}

		//rs.close();
		pstmt.close();
		
	}

}
