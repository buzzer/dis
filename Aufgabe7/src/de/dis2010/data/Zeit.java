package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2010.DB2ConnectionManager;

public class Zeit {
	private Integer id = -1;
	private String Datum = null;
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
		return Datum;
	}
	public void setDatum(String datum) {
		Datum = datum;
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
		String insertSQL = "insert into aufg7_zeit (datum,monat,quartal,jahr) values (DATE(?),MONTH(?),1+TIMESTAMPDIFF(128,char(TIMESTAMP(?)-TIMESTAMP(DATE(concat('01.01.',char(YEAR(?))))))),YEAR(?))";
		PreparedStatement pstmt = con.prepareStatement(insertSQL,
				Statement.RETURN_GENERATED_KEYS);

		//TODO check if already in DB

		// Setze Anfrageparameter und führe Anfrage aus
		pstmt.setString(1, this.getDatum());
		pstmt.setString(2, this.getDatum());
		pstmt.setString(3, this.getDatum());
		pstmt.setString(4, this.getDatum());
		pstmt.setString(5, this.getDatum());

		pstmt.executeUpdate();

		// Hole die Id des engefügten Datensatzes
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next())	{	this.setId(rs.getInt(1));	}

		rs.close();
		
		pstmt.close();
		
	}

}
