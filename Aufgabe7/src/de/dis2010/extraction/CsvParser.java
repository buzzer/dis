package de.dis2010.extraction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dis2010.DB2ConnectionManager;
import de.dis2010.data.Shop;

public class CsvParser {
	
	public CsvParser() {
		// dummy
	}
	
	public void extractZeiten() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String selectSQL = "select shopid,stadtid,name from db2inst1.shopid";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		//pstmt.setInt(1, iid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Shop s = new Shop();
			s.setIid(rs.getInt("shopid"));
			//TODO convert s.setStadt(rs.getInt("stadtid"));
			s.setName(rs.getString("name"));
			//s.setProductCategory(rs.getInt("BankID"));
			//s.setPersID(rs.getInt("PersID"));
			//s.setUNid(rs.getInt("UNid"));
			s.transfer();
			
			rs.close();
			pstmt.close();
			//return a;
		//} else {
			//return null;
		}
	}

}
