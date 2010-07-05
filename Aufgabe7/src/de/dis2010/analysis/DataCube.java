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
		
		String selectSQL = "SELECT z.jahr,z.quartal,s.stadt,sum(f.verkauft) as verkauft,sum(f.umsatz) as umsatz FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by rollup (z.jahr,z.quartal,s.stadt)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		
		System.out.printf("| %4s | %7s | %15s | %10s | %16s |%n", "Jahr","Quartal", "Stadt","Verkauft","Umsatz");

		while (rs.next()) {
			System.out.printf("| %4d | %7d | %15s |  %10d | %16.2f |%n", 
				rs.getInt("jahr"),
				rs.getInt("quartal"),
				rs.getString("stadt"),
				rs.getInt("verkauft"),
				rs.getDouble("umsatz"));
		}

		rs.close();
		pstmt.close();
	}
	public void aggregatRegion () throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT z.jahr,z.quartal,s.region,sum(f.verkauft) as verkauft,sum(f.umsatz) as umsatz FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by rollup (z.jahr,z.quartal,s.region)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		
		System.out.printf("| %4s | %7s | %30s | %10s | %16s |%n", "Jahr","Quartal", "Region","Verkauft","Umsatz");

		while (rs.next()) {
			System.out.printf("| %4d | %7d | %30s | %10d | %16.2f |%n", 
				rs.getInt("jahr"),
				rs.getInt("quartal"),
				rs.getString("region"),
				rs.getInt("verkauft"),
				rs.getDouble("umsatz"));
		}

		rs.close();
		pstmt.close();
	}

	public void aggregatMonat() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT z.jahr,z.monat,sum(f.verkauft) as verkauft,sum(f.umsatz) as umsatz FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by rollup (z.jahr,z.monat)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		
		System.out.printf("| %4s | %7s | %10s | %16s |%n", "Jahr","Monat", "Verkauft","Umsatz");

		while (rs.next()) {
			System.out.printf("| %4d | %7d |  %10d | %16.2f |%n", 
				rs.getInt("jahr"),
				rs.getInt("monat"),
				rs.getInt("verkauft"),
				rs.getDouble("umsatz"));
		}

		rs.close();
		pstmt.close();
		
	}
	public void aggregatPG() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT z.jahr,z.quartal,a.produktgru,sum(f.verkauft) as verkauft,sum(f.umsatz) as umsatz FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by rollup (z.jahr,z.quartal,a.produktgru)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		
		System.out.printf("| %4s | %7s | %20s | %10s | %16s |%n", "Jahr","Quartal","Produktgruppe", "Verkauft","Umsatz");

		while (rs.next()) {
			System.out.printf("| %4d | %7d | %20s | %10d | %16.2f |%n", 
				rs.getInt("jahr"),
				rs.getInt("quartal"),
				rs.getString("produktgru"),
				rs.getInt("verkauft"),
				rs.getDouble("umsatz"));
		}

		rs.close();
		pstmt.close();
		
	}
	public void aggregatPF() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT z.jahr,z.quartal,a.produktfam,sum(f.verkauft) as verkauft,sum(f.umsatz) as umsatz FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by rollup (z.jahr,z.quartal,a.produktfam)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		
		System.out.printf("| %4s | %7s | %20s | %10s | %16s |%n", "Jahr","Quartal","Produktfamilie", "Verkauft","Umsatz");

		while (rs.next()) {
			System.out.printf("| %4d | %7d | %20s | %10d | %16.2f |%n", 
				rs.getInt("jahr"),
				rs.getInt("quartal"),
				rs.getString("produktfam"),
				rs.getInt("verkauft"),
				rs.getDouble("umsatz"));
		}

		rs.close();
		pstmt.close();
		
	}
	public void aggregatPC() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		String selectSQL = "SELECT z.jahr,z.quartal,a.produktkat,sum(f.verkauft) as verkauft,sum(f.umsatz) as umsatz FROM aufg7_faktentabelle f,aufg7_shop s,aufg7_zeit z,aufg7_artikel a WHERE f.artikelid=a.id and f.shopid=s.id and f.zeitid=z.datum group by rollup (z.jahr,z.quartal,a.produktkat)";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
				
		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		
		System.out.printf("| %4s | %7s | %20s | %10s | %16s |%n", "Jahr","Quartal","Produktkategorie", "Verkauft","Umsatz");

		while (rs.next()) {
			System.out.printf("| %4d | %7d | %20s | %10d | %16.2f |%n", 
				rs.getInt("jahr"),
				rs.getInt("quartal"),
				rs.getString("produktkat"),
				rs.getInt("verkauft"),
				rs.getDouble("umsatz"));
		}

		rs.close();
		pstmt.close();
		
	}

}
