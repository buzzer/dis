package de.dis2010.extraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dis2010.DB2ConnectionManager;
import de.dis2010.data.Artikel;
import de.dis2010.data.Shop;
//import de.dis2010.data.Faktum;

public class Db2Parser {
	public void extractArticles() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String selectSQL = "select articleid,productgroupid,name,preis from db2inst1.articleid";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Artikel a = new Artikel();
			a.setIid(rs.getInt("articleid"));
			// Gets all product.. divisions
			a.setProductGroup(rs.getInt("productgroupid"));
			a.setName(rs.getString("name"));
			a.setPreis(rs.getDouble("preis"));
			
			// Save into Data Warehouse
			a.save();
			
			rs.close();
			pstmt.close();
		}
	}
	public void extractShops() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String selectSQL = "select shopid,stadtid,name from db2inst1.shopid";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Shop s = new Shop();
			s.setIid(rs.getInt("shopid"));
			s.setStadt(rs.getInt("stadtid"));
			s.setName(rs.getString("name"));

			// Save into Data Warehouse
			s.save();
			
			rs.close();
			pstmt.close();
		}
	}
		
}
