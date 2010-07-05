package de.dis2010.data;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.DateFormat;
//import java.sql.Date;
//import java.util.Date.*;
//import java.text.DateFormat;

import de.dis2010.DB2ConnectionManager;

public class Zeit {
	private Integer id = -1;
	//private String Datum = null;
	private java.sql.Date Datum = null;
	private Integer Monat = 0;
	private Integer Quartal = 0;
	private Integer Jahr = 0;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer zeitId) {
		this.id = zeitId;
	}
	public String getDatum() {
		return Datum.toString();
	}
	public void setDatum(String datum) {
		Datum = java.sql.Date.valueOf(datum);
	}
	public Integer getMonat() {
		return Monat;
	}
	public void setMonat(Integer monat) {
		Monat = monat;
	}
	public Integer getQuartal() {
		return Quartal;
	}
	public void setQuartal(Integer quartal) {
		Quartal = quartal;
	}
	public Integer getJahr() {
		return Jahr;
	}
	public void setJahr(Integer jahr) {
		Jahr = jahr;
	}
	public void save() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		
		// Erzeuge Anfrage
		String selectSQL = "SELECT z.datum FROM aufg7_zeit z WHERE z.datum = ?";
		PreparedStatement pstmt1 = con.prepareStatement(selectSQL);
		
		System.out.println("Checking for date: "+this.Datum.toString());
		
		pstmt1.setDate(1, this.Datum);
		//java.sql.Date sqlDate = java.sql.Date.valueOf("2010-01-01");
		//pstmt1.setDate(1, sqlDate);
	
		// Führe Anfrage aus
		ResultSet rs1 = pstmt1.executeQuery();
		
		// Insert Date only if not alread present
		if (! rs1.next())
		{
			rs1.close();
			pstmt1.close();
			
			// Erzeuge Anfrage
			String insertSQL = "insert into aufg7_zeit (datum,monat,quartal,jahr) values ?,MONTH(?),1+TIMESTAMPDIFF(128,char(TIMESTAMP(?)-TIMESTAMP(DATE(concat('01.01.',char(YEAR(?))))))),YEAR(?))";
			PreparedStatement pstmt2 = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und führe Anfrage aus
			pstmt2.setDate(1, this.Datum);
			pstmt2.setDate(5, this.Datum);
			pstmt2.setDate(2, this.Datum);
			pstmt2.setDate(3, this.Datum);
			pstmt2.setDate(4, this.Datum);

			pstmt2.executeUpdate();

			// Hole die Id des engefügten Datensatzes
			ResultSet rs2 = pstmt2.getGeneratedKeys();
			if (rs2.next())	{	this.setId(rs2.getInt(1));	}

			rs2.close();
			
			pstmt2.close();
			
			
		} else {
			// Date alread in Zeit tabelle
			rs1.close();
			pstmt1.close();
		}
		
		
	}

}
