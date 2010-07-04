package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dis2010.DB2ConnectionManager;

public class Artikel {
	// Attribute
	private Integer id = -1;
	private String name = "";
	private String productCategory = "";
	private String productFamily = "";
	private String productGroup = "";
	private Double	preis = 0.;
	
	public Artikel() {
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

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Integer productCategoryId) throws SQLException {
		
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT c.name FROM db2inst1.productcategoryid c WHERE c.productcategoryid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, productCategoryId);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.productCategory = rs.getString("c.name");
			rs.close();
		}
		pstmt.close();
	}

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(Integer productFamilyId) throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT f.name FROM db2inst1.productfamilyid f WHERE f.productfamilyid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, productFamilyId);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.productFamily = rs.getString("f.name");
			rs.close();
		}
		pstmt.close();
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(Integer productGroupId) throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT g.name FROM db2inst1.productgroupid g WHERE g.productgroupid = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, productGroupId);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			this.productGroup = rs.getString("g.name");
			rs.close();
		}
		pstmt.close();
	}

	public Double getPreis() {
		return preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}

	public void save() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String insertSQL = "insert into aufg7_artikel (id,name,preis,produktkat,productfam,produktgru) values (?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(insertSQL);

		// Setze Parameter und führe Kommando aus
		pstmt.setInt(1, this.getIid());
		pstmt.setString(2, this.getName());
		pstmt.setDouble(2, this.getPreis());
		pstmt.setString(3, this.getProductCategory());
		pstmt.setString(4, this.getProductFamily());
		pstmt.setString(5, this.getProductGroup());

		pstmt.executeUpdate();

		pstmt.close();
	}
}
