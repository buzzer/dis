package de.dis2010.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class versichert {
	private Integer iid = -1;
	private Double Betrag = 0.;
	private Integer PersID = 0;
	private Integer VersUNid = 0;
	
	public versichert() {
		// dummy
	}
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public void setBetrag(Double betrag) {
		this.Betrag = betrag;
	}
	public double getBetrag() {
		return this.Betrag;
	}
	public void setPerson(Integer persID) {
		this.PersID = persID;		
	}
	public Integer getPerson() {
		return this.PersID;
	}
	public void setVersUN(Integer versUNid) {
		this.VersUNid = versUNid;
	}
	public Integer getVersUN() {
		return this.VersUNid;
	}

	public void save() throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Füge neues Element hinzu, wenn das Objekt noch keine ID hat.
		if (getIid() == -1) {
			// Achtung, hier wird noch ein Parameter mitgegeben,
			// damit später generierte IDs zurückgeliefert werden!
			String insertSQL = "INSERT INTO versichert (PersID,VersUNid,Betrag) VALUES (?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			// Setze Anfrageparameter und führe Anfrage aus
			pstmt.setInt(1, this.getPerson());
			pstmt.setInt(2, this.getVersUN());
			pstmt.setDouble(3, this.getBetrag());
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
			String updateSQL = "UPDATE versichert SET PersID = ?,VersUNid = ?,Betrag = ? WHERE LebVersID = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);

			// Setze Anfrage Parameter
			pstmt.setInt(1, this.getPerson());
			pstmt.setInt(2, this.getVersUN());
			pstmt.setDouble(3, this.getBetrag());
			pstmt.executeUpdate();

			pstmt.close();
		}
	}
	/**
	 * Lädt eine versichert via Id.
	 * 
	 * @param id
	 *            die Id des Datensatzes
	 * @return versichert
	 */
	public static versichert load(int iid) throws SQLException {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		// Erzeuge Anfrage
		String selectSQL = "SELECT LebVersID,PersID,VersUNid,Betrag FROM versichert WHERE LebVersID = ?";
		PreparedStatement pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, iid);

		// Führe Anfrage aus
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			versichert i = new versichert();
			i.setIid(rs.getInt("LebVersID"));
			i.setPerson(rs.getInt("PersID"));
			i.setVersUN(rs.getInt("VersUNid"));
			i.setBetrag(rs.getDouble("Betrag"));

			rs.close();
			pstmt.close();
			return i;
		} else {
			return null;
		}
	}
	
}
