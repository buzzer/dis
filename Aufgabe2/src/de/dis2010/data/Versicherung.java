package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Versicherung {
	private Double betrag = 0.;
	
	public Versicherung() {
		// dummy
	}

	public void setBetrag(Double betrag) {
		this.betrag = betrag;
	}

	/**
	 * Speichert das Unternehmen in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Füge neues Element hinzu, wenn das Objekt noch keine ID hat.
		if (getIid() == -1) {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO versichert(Betrag) VALUES (?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setDouble(1, getBetrag());
			pstmt.executeUpdate();

			// Hole die Id des engefügten Datensatzes
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				setIid(rs.getInt(1));
			}

			rs.close();
			pstmt.close();
		} else {
			// Falls schon eine ID vorhanden ist, mache ein Update...
			String updateSQL = "UPDATE versichert SET Betrag = ? WHERE LebVersID = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);

			// Setze Anfrage Parameter
			pstmt.setDouble(1, getBetrag());
			pstmt.setInt(2, getIid());
			pstmt.executeUpdate();

			pstmt.close();
		}
	}

	private Double getBetrag() {
		return this.betrag;
	}

	private void setIid(int int1) {
		// TODO Auto-generated method stub
		
	}

	private int getIid() {
		// TODO Auto-generated method stub
		return 0;
	}
}
