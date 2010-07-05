package de.dis2010.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.dis2010.DB2ConnectionManager;

public class DataCube {
	
	public DataCube () {
		//dummy
	}
	public void aggregatStadt () throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT s.stadt,s.region,s.land,sum(f.verkauft),sum(f.umsatz) FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by grouping sets (s.stadt,s.region,s.land)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		
		System.out.printf("| %15s | %30s | %15s | %10s | %16s |%n", "Stadt","Region","Land","Verkauft","Umsatz");

		while (rs.next()) {
			System.out.printf("| %15s | %30s | %15s | %10d | %16.2f |%n", 
				rs.getString("stadt"),
				rs.getString("region"),
				rs.getString("land"),
				rs.getInt("4"),
				rs.getDouble("5"));
		}

		rs.close();
		pstmt.close();
	}
	public void aggregatRegion () throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT s.region,s.land,sum(f.verkauft),sum(f.umsatz) FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by cube (s.region,s.land)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.toString());
		}

		rs.close();
		pstmt.close();
	}
	public void aggregatLand () throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT s.land,sum(f.verkauft),sum(f.umsatz) FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by cube (s.land)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.toString());
		}

		rs.close();
		pstmt.close();
	}

}
