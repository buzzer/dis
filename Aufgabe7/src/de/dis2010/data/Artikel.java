package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.ResultSet;
import java.sql.Statement;

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

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductFamily() {
		return productFamily;
	}

	public void setProductFamily(String productFamily) {
		this.productFamily = productFamily;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public Double getPreis() {
		return preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}

	public void transfer() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String insertSQL = "insert into aufg7_artikel (name,preis,produktkat,productfam,produktgru) values (?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(insertSQL,
				Statement.RETURN_GENERATED_KEYS);

		// Setze Anfrageparameter und führe Anfrage aus
		pstmt.setString(1, this.getName());
		pstmt.setDouble(2, this.getPreis());
		pstmt.setString(3, this.getProductCategory());
		pstmt.setString(4, this.getProductFamily());
		pstmt.setString(5, this.getProductGroup());

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
